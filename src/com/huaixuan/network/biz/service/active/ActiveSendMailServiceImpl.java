package com.huaixuan.network.biz.service.active;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.active.MoveFrameProductDao;
import com.huaixuan.network.biz.dao.active.SendMailDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.hy.ProductDao;
import com.huaixuan.network.biz.domain.active.MoveFrame;
import com.huaixuan.network.biz.domain.active.MoveFrameInfo;
import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeGoods;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.SendMailInfo;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.service.user.MailEngine;
import com.hundsun.common.lang.StringUtil;

/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-27 下午05:12:07
 * 
 */

@Service("activeSendMailService")
public class ActiveSendMailServiceImpl implements ActiveSendMailService
{

	@Autowired
	private SendMailDao sendMailDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private MailEngine mailEngine;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsInstanceDao goodsInstanceDao;
	
	@Autowired
	private MoveFrameProductDao moveFrameProductDao;
	
	@Override
	public void sendMail()
	{
		getYesterDayCreateMoveFrame();
		getYesterDayModifyMoveFrame();
		getYesterDayModifyMoveFrameGoods();
	}
	
	/**
	 * 代销添加的产品
	 */
	public void sendDLMail()
	{
		sendMailToDlGoodsInfo();
	}
	
	/**
	 * 特惠添加的产品
	 */
	public void sendHKMail()
	{
		sendMailToHKGoodsInfo();
	}
	
	/**
	 * 同一客户不同价的
	 */
	public void notSamePrice()
	{
		toOneCustomerSameGoodsIDNotSamePrice();
	}

	
	
	/**
	 * 发送邮件 主要内容包括活动产品对同一个客户有不同的价格的时候，把这个产品的信息发送给对应的负责人 样板：<br/>
	 * 对客户：22<br/>
	 * 产品ID为：4296137253242<br/>
	 * 型号：114096 材质：V0015 颜色:2510<br/>
	 * 
	 * 在活动框：劳动节1 价钱为:1081.0<br/>
	 * 在活动框：劳动节2 价钱为:1080.0<br/>
	 * ------------------------------------------------<br/>
	 */
	private void toOneCustomerSameGoodsIDNotSamePrice()
	{

		List<Long> customerIds = sendMailDao.getAllCustomerId(); 
		Map<String, List<String>> sendMap = new HashMap<String, List<String>>();// 用于存储需要发送邮件的地址
		for (Long id : customerIds)
		{
			if (id != null)
			{
				// 用于存放相同产品对应的不同价钱，Map的值放的是set，set放的是MoveframeGoods，所以需要重写它的hashCode和equals，根据productId,area,discountPrice来判断对象是否相同
				Map<Long, Set<MoveframeProduct>> productIdSameAndPriceNotSameMap = new HashMap<Long, Set<MoveframeProduct>>();
				List<Long> moveFrameIds = sendMailDao.getMoveFrameIdsByCustomerId(id);
				for(Long  moveFrameId : moveFrameIds)
				{
					List<MoveframeProduct> moveframeGoods = sendMailDao.getMoveFrameGoodsInfoByMoveFrameId(moveFrameId); // 得到该客户所有的活动产品
					for (MoveframeProduct mf : moveframeGoods)
					{
						//MoveframeGoods 从写了equals和hashCode方法，当同一ProductId同一区域有不同价格的时候才能加入set集合中
						if (null == productIdSameAndPriceNotSameMap.get(mf.getProductId()))
						{
							Set<MoveframeProduct> set = new HashSet<MoveframeProduct>();
							set.add(mf);
							productIdSameAndPriceNotSameMap.put(mf.getProductId(),set);
						}
						else
						{
							productIdSameAndPriceNotSameMap.get(mf.getProductId()).add(mf);
						}
					}
				}

				Set<Long> keys = productIdSameAndPriceNotSameMap.keySet();
				Iterator<Long> it = keys.iterator();

				while (it.hasNext())
				{
					Long key = it.next();
					
					Set<MoveframeProduct> values = productIdSameAndPriceNotSameMap.get(key);
					Set<String> emails = new HashSet<String>();
					if(values.size() >= 2)
					{
						// 拼接需要发送的信息
						StringBuffer str = new StringBuffer();
						str.append("对客户：" + id + "\n");
						Product product = productDao.getProductById(key);
						str.append("产品ID为：" + key + "\r\n");
						str.append("型号：" + product.getType() + "\t材质："
								+ product.getMaterial() + "\t颜色:"
								+ product.getColor() + "\r\n");
						String s = "";
						for (MoveframeProduct value : values)
						{
							String moveframeName = sendMailDao.getMoveFrameById(value.getMoveframeId()).getName();
							s += "在活动框：" + moveframeName + "     ";
							s += "价钱为:" + value.getDiscountPrice() + "\n";
							emails.add(sendMailDao.getHeaderEmail(value.getMoveframeId()));
						}
						str.append(s + "\n");
						str.append("-----------------------------------\n");
						
						for(String email : emails)
						{
							// 存储需要发送邮件的联系人信息
							if (StringUtil.isNotBlank(email) && isEmail(email))
							{
								if (null == sendMap.get(email))
								{
									List<String> list = new ArrayList<String>();
									list.add(str.toString());
									sendMap.put(email, list);
								}
								else if(isEmail(email))
								{
									sendMap.get(email).add(str.toString());
								}
							}
						}						
					}
				}
				
			}

		}
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// 发送邮件
		Set<Entry<String, List<String>>> set = sendMap.entrySet();
		Iterator<Entry<String, List<String>>> it = set.iterator();
		while (it.hasNext())
		{
			String email = it.next().getKey();
			simpleMailMessage.setTo(email);
			simpleMailMessage.setSubject("对同一客户同一产品ID在不同活动中有不同的价格");
			List<String> list = sendMap.get(email);
			StringBuffer st = new StringBuffer();
			for (String s : list)
			{
				st.append(s);
			}
			simpleMailMessage.setText(st.toString());
			mailEngine.send(simpleMailMessage);
		}

	}

	/**
	 * 当前时间前一天创建的活动信息发送给对应的员工
	 */
	private void getYesterDayCreateMoveFrame()
	{

		List<MoveFrameInfo> yesterDayMoveframeInfo = sendMailDao.getYesterDayNewCreateMoveFrames(new Integer(1)); //得到昨天创建的所有活动

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Map<String, List<String>> sendMap = new HashMap<String, List<String>>();// 用于存储需要发送邮件的地址
		for (MoveFrameInfo m : yesterDayMoveframeInfo)
		{
			StringBuffer str = new StringBuffer();// 用于拼接最后发送的信息
			str.append(m.getAuthor());
			str.append("创建了活动框：");
			str.append(m.getMoveFrameName());
			str.append("\r\n");
			if (null != m.getDepName() && !"".equals(m.getDepName()))
			{
				str.append("该活动框属于：" + m.getDepName());
			}
			str.append("\r\n");
			str.append("活动开始时间为：" + sdf.format(m.getGmtStart()) + "\r\n");
			str.append("活动结束时间为：" + sdf.format(m.getGmtEnd()));
			str.append("\r\n");
			str.append("该活动下的客户有:\n");
			// 通过活动框ID得到该活动框下面所属员工的email和客户姓名
			List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(m.getMoveFrameId());
			int i = 1; // 当客户过多时，用于换行
			for (MoveFrameInfo mc : emailAndCustomerList)
			{

				if (StringUtil.isNotBlank(mc.getCustomerName()))
				{
					if (i == 15)
					{
						str.append("...........");
						break;
					}
					str.append(mc.getCustomerName() + "   ");
					i++;
				}
			}
			str.append("\r\n---------------------------------------\r\n");
			for (MoveFrameInfo mc : emailAndCustomerList)
			{
				// 存储需要发送邮件的联系人信息
				if (StringUtil.isNotBlank(mc.getEmail()))
				{
					if (null == sendMap.get(mc.getEmail()) && isEmail(mc.getEmail()))
					{
						List<String> list = new ArrayList<String>();
						list.add(str.toString());
						sendMap.put(mc.getEmail(), list);
					}
					else if(isEmail(mc.getEmail()))
					{
						sendMap.get(mc.getEmail()).add(str.toString());
					}
				}
			}
		}
		
		sendMailDao.updateLogStatus(1); //更新状态为以处理
		
		new SendMoveFrameThread(sendMap,mailEngine,"创建的活动列表").run();
		
	}
 
	/**
	 * 当前时间前一天修改过的活动框信息
	 */
	private void getYesterDayModifyMoveFrame()
	{
		List<MoveFrameInfo> yesterDayModifyMoveFrameInfo = sendMailDao.getYesterDayModifyMoveFrames(new Integer(2)); // 得到昨天修改的所有活动
		Map<Long, List<String>> sendInfoMap = new HashMap<Long, List<String>>();//用于存储需要发送的信息和对应活动框ID
		// 存储发送邮件的地址和信息
		Map<String, List<String>> sendMap = new HashMap<String, List<String>>();

		// 循环得到活动框对应的修改信息
		for (MoveFrameInfo mfi : yesterDayModifyMoveFrameInfo)
		{
			if (null == sendInfoMap.get(mfi.getMoveFrameId()))
			{
				List<String> list = new ArrayList<String>();
				list.add(mfi.getNote());
				sendInfoMap.put(mfi.getMoveFrameId(), list);
			}
			else
			{
				sendInfoMap.get(mfi.getMoveFrameId()).add(mfi.getNote());
			}
		}

		// 循环拼接需要发送的字符串并存储到sendMap中
		Set<Entry<Long, List<String>>> entry = sendInfoMap.entrySet();
		Iterator<Entry<Long, List<String>>> it = entry.iterator();
		while (it.hasNext())
		{
			Entry<Long, List<String>> keyAndValue = it.next();
			Long moveFrameId = keyAndValue.getKey();
			String moveFrameName = sendMailDao.getMoveFrameById(moveFrameId).getName();
			StringBuffer str = new StringBuffer();
			str.append("活动框 " + moveFrameName + "  做了以下修改:\n");
			List<String> listString = keyAndValue.getValue();
			for (String note : listString)
			{
				String[] strs = note.split("#");
				
				for(int i =0;i<strs.length;i++)
				{
					if(null != strs[i] && !"".equals(strs[i]) )
					{
						if((i+1) == 1)
						{
							String[] names = strs[i].split("->");
							str.append("修改活动框名称:" + names[0] + "  为  " + names[1] + "\r\n");
						}
						if((i+1) == 2)
						{
							String[] names = strs[i].split("->");
							str.append("现在活动框所属部门：" + names[1] + "\r\n");
						}
						if((i+1) == 3)
						{
							String[] names = strs[i].split("->");
							if(names.length >= 2)
								str.append("现有员工：" + names[1] + "\r\n");
							else
								str.append("现有员工：无"  + "\r\n");
						}
						if((i+1) == 4)
						{
							String[] names = strs[i].split("->");
							if(names.length >= 2)
								str.append("现有客户：" + names[1] + "\r\n");
							else
								str.append("现有客户：无" + "\r\n");
						}
						if((i+1) == 5)
						{
							String[] names = strs[i].split("->");
							str.append("修改开始时间：" + names[0] + " 为" + names[1] + "\r\n");
						}
						if((i+1) == 6)
						{
							String[] names = strs[i].split("->");
							str.append("修改结束时间：" + names[0] + " 为" + names[1] + "\r\n");
						}
						if((i+1) == 7)
						{
							String[] names = strs[i].split("->");
							if(names.length >= 2)
							{
								
								str.append("现在修改客户类型："  + (names[1].equals("1")?"零售客户":(names[1].equals("2")?"批发客户":(names[1].equals("3")?"代销客户":"无"))) + "\r\n");
							}
							else
							{
								str.append("修改客户类型：无" + "\r\n");
							}
						}
					}
				}
			
			}// for循环完毕

			str.append("--------------------------------------------------------------\n");
			// 通过活动框ID得到该活动框下面所属员工的email
			List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(moveFrameId);

			// 把需要发送的信息和对应的Email对应起来
			for (MoveFrameInfo m : emailAndCustomerList)
			{
				if (null != m.getEmail() && !"".equals(m.getEmail()) && isEmail(m.getEmail()))
				{
					if (null == sendMap.get(m.getEmail()))
					{
						List<String> list = new ArrayList<String>();
						list.add(str.toString());
						sendMap.put(m.getEmail(), list);
					}
					else
					{
						sendMap.get(m.getEmail()).add(str.toString());
					}
				}
			}
		} // while循环完毕
		
		sendMailDao.updateLogStatus(2); //更新状态为以处理
		new SendMoveFrameThread(sendMap, mailEngine, "修改的活动列表").run();
		
	}


	/**
	 * 特惠添加产品，需要发送的信息
	 */
	@SuppressWarnings("unchecked")
	private void sendMailToHKGoodsInfo()
	{
		Map<String, List<SendMailInfo>> sendMap = new HashMap<String, List<SendMailInfo>>();// 用于存储需要发送邮件的地址和内容
		
		Map parMap = new HashMap();
		List<MoveFrameLog> notDealListAdd = sendMailDao.getMoveFrameLogByType(3);
		List<MoveframeProduct> addProductsList = new ArrayList<MoveframeProduct>();
		
		for(MoveFrameLog log : notDealListAdd)
		{
			if(log.getArea().intValue() == 1) //代销的产品不需要发送
				continue;
			parMap.put("productId", Long.parseLong(log.getNote()));
			parMap.put("moveframeId", log.getMoveframeId());
			List<MoveframeProduct> list = moveFrameProductDao.getMoveFrameProductsByPorductIdAndMfId(parMap);
			if(list !=null && list.size() > 0)
			{
				MoveframeProduct p = list.get(0);
				p.setArea(log.getArea());
				addProductsList.add(p);
			}
			//更新状态为以处理
			parMap.put("productId",Long.parseLong(log.getNote()));
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type", log.getType());
			sendMailDao.updateLogDealStatus(parMap);
					
		}
		
		List<MoveFrameLog> notDealListDel= sendMailDao.getMoveFrameLogByType(4);//卖掉的
		for(MoveFrameLog log : notDealListDel)
		{
			if(log.getArea().intValue() == 1) //代销的产品不需要发送
				continue;
			MoveframeProduct mf = new MoveframeProduct();
			mf.setMoveframeId(log.getMoveframeId());
			Product product = productDao.getProductById(Long.valueOf(log.getNote()));
			mf.setGoodsId(product.getGoodsId());
			addProductsList.remove(mf);  //从添加中剔除卖掉的
			//更新状态为以处理
			parMap.put("productId",Long.parseLong(log.getNote()));
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type", log.getType());
			sendMailDao.updateLogDealStatus(parMap);				
		}
		
		
		Map<Long,Set<Long>> goodsIdAndMoveFrameId = new HashMap<Long, Set<Long>>();//剔除goodsId,moveFrameId 都相同的项
		
		removeRepelat(addProductsList, goodsIdAndMoveFrameId, 3); //去除goodsId,moveFrameId相同的项
		
		Set<Long> ketSet = goodsIdAndMoveFrameId.keySet();
		Iterator<Long> moveFrameIds = ketSet.iterator();
		while(moveFrameIds.hasNext())
		{
			Long moveFrameId = moveFrameIds.next();
			Set<Long> goodIds = goodsIdAndMoveFrameId.get(moveFrameId);
			for(Long goodsId : goodIds)
			{
				parMap.put("goodsId", goodsId);
				parMap.put("moveFrameId", moveFrameId);
				List<MoveframeGoods> mgList = moveFrameProductDao.getMoveFrameGoodsByGoodsIdAndMfId(parMap);
				if(mgList != null && mgList.size() > 0)
				{
					Goods goods = goodsDao.getGoods(goodsId);
					SendMailInfo sendToUser = new SendMailInfo();
					sendToUser.setMiddleImg(goods.getImgMiddle());
					sendToUser.setBrand(EnumBrandType.toMap().get(goods.getBrandId()+""));
					sendToUser.setSeries(EnumSeriesType.toMap().get(goods.getIdSeries()+""));
					sendToUser.setGoodsId(goods.getId());
					sendToUser.setType(goods.getType());
					sendToUser.setMaterial(goods.getMaterial());
					sendToUser.setColor(goods.getColor());
					sendToUser.setDiscountPrice(mgList.get(0).getDiscountPrice()); //特惠价
					sendToUser.setHkPrice(goods.getHkhxPrice()); //lussomoda价		
					// 通过活动框ID得到该活动框下面所属员工和顾客的email
					List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(moveFrameId);
					emailTolAddInfo(emailAndCustomerList,sendMap,sendToUser);
				}
			}
		}
		
		
		//启动线程发送邮件
		new SendMailThread(sendMap,mailEngine,"香港特惠添加产品列表","/HKAddInfo.vm").run();
		
		
	}
	
	/**
	 * 添加的代销产品
	 */
	@SuppressWarnings("unchecked")
	private void sendMailToDlGoodsInfo()
	{
		//得到未处理的添加的产品
		Map<String, List<SendMailInfo>> sendMap = new HashMap<String, List<SendMailInfo>>();// 用于存储需要发送邮件的地址和内容
		List<MoveFrameLog> notDealListAdd = sendMailDao.getMoveFrameLogByType(3);
		List<MoveframeProduct> addProductsList = new ArrayList<MoveframeProduct>();
		List<MoveframeProduct> addProductsListCopy = new ArrayList<MoveframeProduct>();
		Map parMap = new HashMap();
		
		for(MoveFrameLog log : notDealListAdd)
		{
			if(log.getArea().intValue() == 2) //特惠的产品不需要发送
				continue;
			parMap.put("productId", Long.parseLong(log.getNote()));
			parMap.put("moveframeId", log.getMoveframeId());
			List<MoveframeProduct> list = moveFrameProductDao.getMoveFrameProductsByPorductIdAndMfId(parMap);
			if(list !=null && list.size() > 0)
			{
				MoveframeProduct p = list.get(0);
				p.setArea(log.getArea());
				addProductsList.add(p);
				addProductsListCopy.add(p);
			}
			/*
			//更新状态为以处理
			parMap.put("productId", log.getNote());
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type", 3);
			sendMailDao.updateLogDealStatus(parMap);
					*/
		}
		
		//得到未处理的删除的产品
		List<MoveFrameLog> notDealListDel= sendMailDao.getMoveFrameLogByType(4);
		List<MoveframeProduct> delProductsList = new ArrayList<MoveframeProduct>();
		for(MoveFrameLog log : notDealListDel)
		{
			if(log.getArea().intValue() == 2) //特惠的产品不需要发送
				continue;
			parMap.put("productId", Long.parseLong(log.getNote()));
			parMap.put("moveframeId", log.getMoveframeId());
			
			List<MoveframeProduct> list = moveFrameProductDao.getMoveFrameProductsByPorductIdAndMfId(parMap);
			if(list !=null && list.size() > 0)
			{
				MoveframeProduct p = list.get(0);
				p.setArea(log.getArea());
				delProductsList.add(p);
			}
			/*
			Product product = productDao.getProductById(Long.valueOf(log.getNote()));
			MoveframeProduct mp = new MoveframeProduct();
			mp.setMoveframeId(log.getMoveframeId());
			mp.setGoodsId(product.getGoodsId());
			mp.setArea(log.getArea());
			delProductsList.add(mp);		
			
			
			//更新状态为以处理
			parMap.put("productId", log.getNote());
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type",4);
			sendMailDao.updateLogDealStatus(parMap);
			*/
		}
		
		
		/*
		 * 剔除添加后卖掉的
		 */
		if(addProductsList != null)
			addProductsList.removeAll(delProductsList);
		if(delProductsList != null)
			delProductsList.removeAll(addProductsListCopy);
		
	
		//更新代销添加的产品的状态
		List<MoveFrameLog> addLogs = sendMailDao.getMoveFrameLogByType(3);
		List<MoveFrameLog> delLogs = sendMailDao.getMoveFrameLogByType(4);
		delLogs.addAll(addLogs);
		
		for(MoveFrameLog ml : delLogs)
		{
			if(ml.getArea().equals(1)) //更新添加的代销产品状态
			{
				parMap.put("productId", ml.getNote());
				parMap.put("moveFrameId", ml.getMoveframeId());
				parMap.put("type", ml.getType());
				sendMailDao.updateLogDealStatus(parMap);
			}
			
			if(ml.getArea().equals(2)) //更新减少删除的代销产品状态
			{
				parMap.put("productId", ml.getNote());
				parMap.put("moveFrameId", ml.getMoveframeId());
				parMap.put("type",4);
				sendMailDao.updateLogDealStatus(parMap);
			}
		}
		 
		
		Map<Long,Set<Long>> addGoodsIdAndMoveFrameId = new HashMap<Long, Set<Long>>();//存储剔除goodsId,moveFrameId都相同的项
		Map<Long,Set<Long>> delGoodsIdAndMoveFrameId = new HashMap<Long, Set<Long>>();//存储剔除goodsId,moveFrameId 都相同的项
		
		Map<MoveframeGoods,Map<Long,Integer>> addGoodsIdToInstanceId = new HashMap<MoveframeGoods, Map<Long,Integer>>();//存储添加的goodsId对应的尺寸
		Map<MoveframeGoods,Map<Long,Integer>> delGoodsIdToInstanceId = new HashMap<MoveframeGoods, Map<Long,Integer>>();//存储添加的goodsId对应的尺寸
		
		
		
		
		Map<MoveframeGoods,Integer> goodsIdAddNum = new HashMap<MoveframeGoods, Integer>(); //存储这个goodsId的产品添加了多少次
		Map<MoveframeGoods,Integer> goodsDelAddNum = new HashMap<MoveframeGoods, Integer>();//存储这个goodsId的产品删除了多少次
		
		removeRepelat(addProductsList, addGoodsIdAndMoveFrameId, 3,addGoodsIdToInstanceId,goodsIdAddNum); 
		removeRepelat(delProductsList, delGoodsIdAndMoveFrameId, 4,delGoodsIdToInstanceId,goodsDelAddNum);

		
		List<MoveframeGoods> allChangeMoveFrameGoods = new ArrayList<MoveframeGoods>(); //存储所有改变过库存的产品

		//添加的产品
		Set<Long> addKetSet = addGoodsIdAndMoveFrameId.keySet();
		Iterator<Long> moveFrameIds = addKetSet.iterator();
		while(moveFrameIds.hasNext())
		{
			Long moveFrameId = moveFrameIds.next();
			Set<Long> goodIds = addGoodsIdAndMoveFrameId.get(moveFrameId);
			for(Long goodsId : goodIds)
			{
				parMap.put("goodsId", goodsId);
				parMap.put("moveFrameId", moveFrameId);
				List<MoveframeGoods> mgList = moveFrameProductDao.getMoveFrameGoodsByGoodsIdAndMfId(parMap);
				if(mgList != null && mgList.size() > 0)
				{
					MoveframeGoods moveframeGoods = mgList.get(0);
					moveframeGoods.setType(1);//增加
					Integer addNum = goodsIdAddNum.get(moveframeGoods); //增加的次数
					if(moveframeGoods.getGoodsNum().intValue() == addNum.intValue())
					{
						moveframeGoods.setHasStock(1);//从无到有
					}
					else
					{
						moveframeGoods.setHasStock(2);//增加产品
					}
					allChangeMoveFrameGoods.add(moveframeGoods);
				}
			}
		}
		
		
		
		//删除或卖掉的产品
		Set<Long> delKetSet = delGoodsIdAndMoveFrameId.keySet();
		Iterator<Long> delmoveFrameIds = delKetSet.iterator();
		while(delmoveFrameIds.hasNext())
		{
			Long moveFrameId = delmoveFrameIds.next();
			Set<Long> goodIds = delGoodsIdAndMoveFrameId.get(moveFrameId);
			for(Long goodsId : goodIds)
			{
				parMap.put("goodsId", goodsId);
				parMap.put("moveFrameId", moveFrameId);
				List<MoveframeGoods> mgList = moveFrameProductDao.getMoveFrameGoodsByGoodsIdAndMfId(parMap);
				if(mgList != null && mgList.size() > 0)
				{
					MoveframeGoods moveframeGoods = mgList.get(0);
					moveframeGoods.setType(2);//删除的
					if(moveframeGoods.getGoodsNum() == 0)
						moveframeGoods.setHasStock(0);//从有到无
					else
						moveframeGoods.setHasStock(3);//减少
					allChangeMoveFrameGoods.add(moveframeGoods);
				}
			}
		}
		
		
		//拼接需要发送的信息
		for(MoveframeGoods all : allChangeMoveFrameGoods)
		{
			MoveFrame mo = sendMailDao.getMoveFrameById(all.getMoveframeId());
			if(mo.getStatus().equals("closed")) //关闭的活动不需要发送
				continue;
			Goods g = goodsDao.getGoods(all.getGoodsId());
			SendMailInfo sendToUser = new SendMailInfo();
			sendToUser.setType(g.getType());
			sendToUser.setMaterial(g.getMaterial());
			sendToUser.setColor(g.getColor());
			sendToUser.setBrand(EnumBrandType.toMap().get(g.getBrandId()+""));
			sendToUser.setSeries(EnumSeriesType.toMap().get(g.getIdSeries()+""));
			sendToUser.setDiscountPrice(all.getDiscountPrice());//代销价
			sendToUser.setIdProduct(g.getProductId()+"");
			sendToUser.setMiddleImg(g.getImgMiddle());
			sendToUser.setGoodsId(g.getId());
			sendToUser.setHasStock(all.getHasStock()); //是否为新增或删除
			sendToUser.setGoodsNum(all.getGoodsNum());
			sendToUser.setHxPrice(g.getGoodsPrice());//尚上价
			sendToUser.setMoveFrameId(all.getMoveframeId());
			sendToUser.setMoveframeName(mo.getName());
			
			Set<Long> instanceIds = null;
			//处理尺寸
			if(all.getType().equals(1))
			{
				instanceIds = addGoodsIdToInstanceId.get(all).keySet();
			}
			else
			{
				instanceIds = delGoodsIdToInstanceId.get(all).keySet();
			}
			
			for(Long instanceId : instanceIds)
			{
				
				GoodsInstance instance = goodsInstanceDao.getInstance(instanceId);
				if(all.getType().equals(1))
				{
						Integer num = addGoodsIdToInstanceId.get(all).get(instanceId);
						sendToUser.getStock().put(instance.getSize(),num);
				}
				else
				{
						Integer num = delGoodsIdToInstanceId.get(all).get(instanceId);
						sendToUser.getStock().put(instance.getSize(),num);
				}
			}

			// 通过活动框ID得到该活动框下面所属员工和顾客的email
			List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(all.getMoveframeId());
			
			emailTolAddInfo(emailAndCustomerList,sendMap,sendToUser);
		
		}
		
		Map<String,List<SendMailInfo>> toCustomer = new  HashMap<String, List<SendMailInfo>>();
		Map<String,Map<String,List<SendMailInfo>>> toUser = new  HashMap<String, Map<String,List<SendMailInfo>>>();
		
		
		Set<String> emails = sendMap.keySet();
		Iterator<String> emailIt = emails.iterator();
		while(emailIt.hasNext())
		{
			String email = emailIt.next();
			if(!isEmail(email))
				continue;
			List<SendMailInfo> list = sendMap.get(email);
			Map<String,List<SendMailInfo>> map = new  HashMap<String, List<SendMailInfo>>(); 
			for(SendMailInfo sendMailInfo : list)
			{
				if(sendMailInfo.getUserType().equals(1))//内部用户
				{
					if(map.get(sendMailInfo.getMoveframeName()) == null)
					{
						List<SendMailInfo> ls = new ArrayList<SendMailInfo>();
						ls.add(sendMailInfo);
						map.put(sendMailInfo.getMoveframeName(), ls);
					}
					else
					{
						map.get(sendMailInfo.getMoveframeName()).add(sendMailInfo);
					}
					toUser.put(email, map);
				}
				else if(sendMailInfo.getUserType().equals(2)) //客户
				{
					toCustomer.put(email, list);
				}
			}
		}
		
		//启动线程发送邮件给客户
		new SendMailThread(toCustomer,mailEngine,"代销产品库存改变列表","/goodsNumberInfo.vm").run();
		
		//启动线程发送邮件给内部用户
		new SendDLMailToUserThread(toUser,mailEngine,"大陆产品库存改变列表","/dlGoodsNumberChange.vm").run();
			
	}
	
	/**
	 * 代销产品价格改变列表
	 */
	private void getYesterDayModifyMoveFrameGoods()
	{		
		List<MoveFrameInfo> priceChangeList = sendMailDao.getYesterDayModifyMoveFrames(new Integer(5)); //所有价格改变过的产品
		Map<String, List<SendMailInfo>> sendMap = new HashMap<String, List<SendMailInfo>>();// 用于存储需要发送邮件的地址
		for (MoveFrameInfo m : priceChangeList)
		{
			if(m.getArea().equals(2)) //特惠的价钱改变不用发
				continue;
			SendMailInfo sendToUser = new SendMailInfo();
			int k = 1; // 当内层循环第双数次循环的时候才进入
			String note = m.getNote();
			String[] strs = note.split("#");
			for (String s : strs)
			{
				if ((k % 2) != 0)
				{
					Product product = productDao.getProductById(Long.parseLong(s));
					if (product != null)
					{
						sendToUser.setIdProduct(product.getIdProduct()+"");
						sendToUser.setType(product.getType());
						sendToUser.setMaterial(product.getMaterial());
						sendToUser.setColor(product.getColor());
						sendToUser.setMoveframeName(m.getMoveFrameName());
						Goods g =goodsDao.getGoods(product.getGoodsId());
						sendToUser.setGoodsId(g.getId());
						sendToUser.setMiddleImg(g.getImgMiddle());
						sendToUser.setBrand(EnumBrandType.toMap().get(product.getIdBrand()+""));
						sendToUser.setSeries(EnumSeriesType.toMap().get(product.getIdSeries()+""));
					}
				}
				else
				{
					int t = 1;
					String p = "";
					String[] price = s.split("->");
					for (String tmp : price)
					{
						if ((t % 2) == 0)
						{
							sendToUser.setBeforPrice(p);
							sendToUser.setNowPrice(tmp);
						}
						p = tmp;
						t++;
					}
				}
				k++;
			}
			
			// 通过活动框ID得到该活动框下面所属员工的email
			List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(m.getMoveFrameId());
			
			
			for (MoveFrameInfo mc : emailAndCustomerList)
			{
				SendMailInfo sendInfo = null;
				try
				{
					 sendInfo = (SendMailInfo)sendToUser.clone();
				}
				catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}
				if(StringUtil.isNotBlank(mc.getEmail())  && isEmail(mc.getEmail()))
				{
					sendInfo.setUserType(1); //內部用戶
					if (null == sendMap.get(mc.getEmail()))
					{					
						List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //添加需要发送的内容
						list.add(sendInfo);
						sendMap.put(mc.getEmail(), list);
					}
					else
					{	
						List<SendMailInfo> union = sendMap.get(mc.getEmail());
						SendMailInfo removeTmp = null;
						for(SendMailInfo s : union)
						{
							//找到同款产品
							if(s.getGoodsId().equals(sendInfo.getGoodsId()))
							{
								removeTmp = s;
								break;
							}
						}
						if(removeTmp != null)
							sendMap.get(mc.getEmail()).remove(removeTmp); //剔除同款产品
						sendMap.get(mc.getEmail()).add(sendInfo);
					}	
				}
				if (StringUtil.isNotBlank(mc.getCustomerEmail()) && isEmail(mc.getCustomerEmail()))
				{
					//价格改变之给2 3 级用户发
					if("e".equals(sendInfo.getUserRank()) || "s".equals(sendInfo.getUserRank()) )
					{
						sendInfo.setUserRank(mc.getUserRank()); //设置用户等级
						sendInfo.setUserType(2); //客戶
						if (null == sendMap.get(mc.getCustomerEmail()))
						{
							List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //添加需要发送的内容
							list.add(sendInfo);
							sendMap.put(mc.getCustomerEmail(), list);
						}
						else
						{
							List<SendMailInfo> union = sendMap.get(mc.getCustomerEmail());
							SendMailInfo removeTmp = null;
							for(SendMailInfo s : union)
							{
								//找到同款产品
								if(s.getGoodsId().equals(sendInfo.getGoodsId()))
								{
									removeTmp = s;
									break;
								}
							}
							if(removeTmp != null)
								sendMap.get(mc.getCustomerEmail()).remove(removeTmp); //剔除同款产品
							sendMap.get(mc.getCustomerEmail()).add(sendInfo);
						}
					}
				}
			}
		}
		
		sendMailDao.updateLogStatus(5); //更新状态为以处理

		
		Map<String,List<SendMailInfo>> toCustomer = new  HashMap<String, List<SendMailInfo>>();
		Map<String,Map<String,List<SendMailInfo>>> toUser = new  HashMap<String, Map<String,List<SendMailInfo>>>();
		
		
		Set<String> emails = sendMap.keySet();
		Iterator<String> emailIt = emails.iterator();
		while(emailIt.hasNext())
		{
			String email = emailIt.next();
			if(!isEmail(email))
				continue;
			List<SendMailInfo> list = sendMap.get(email);
			Map<String,List<SendMailInfo>> map = new  HashMap<String, List<SendMailInfo>>(); 
			for(SendMailInfo sendMailInfo : list)
			{
				if(sendMailInfo.getUserType().equals(1))//内部用户
				{
					if(map.get(sendMailInfo.getMoveframeName()) == null)
					{
						List<SendMailInfo> ls = new ArrayList<SendMailInfo>();
						ls.add(sendMailInfo);
						map.put(sendMailInfo.getMoveframeName(), ls);
					}
					else
					{
						map.get(sendMailInfo.getMoveframeName()).add(sendMailInfo);
					}
					toUser.put(email, map);
				}
				else if(sendMailInfo.getUserType().equals(2)) //客户
				{
					toCustomer.put(email, list);
				}
			}
		}
		
		
		new SendMailThread(toCustomer,mailEngine,"代销产品价格改变列表","/goodsPriceModify.vm").run();		//启动线程发邮件
		
		new SendDLMailToUserThread(toUser,mailEngine,"大陆产品价格改变列表","/goodsPriceModify2User.vm").run();
		
	}

	private void removeRepelat(List<MoveframeProduct> productsList,Map<Long,Set<Long>> goodsIdAndMoveFrameId,Integer type)
	{
		for(MoveframeProduct mp : productsList)
		{
			//剔除goodsId 和moveFrameId相同的
			if(goodsIdAndMoveFrameId.get(mp.getMoveframeId()) == null)
			{
				Set<Long> list = new  HashSet<Long>(); //存储goodsId相同的不需要重新存入
				list.add(mp.getGoodsId());
				goodsIdAndMoveFrameId.put(mp.getMoveframeId(), list);
			}
			else
			{
				goodsIdAndMoveFrameId.get(mp.getMoveframeId()).add(mp.getGoodsId());
			}
		}
		
	}
	
	
	private void removeRepelat(List<MoveframeProduct> productsList,Map<Long,Set<Long>> goodsIdAndMoveFrameId,Integer type,Map<MoveframeGoods,Map<Long,Integer>> goodsIdToInstanceId,Map<MoveframeGoods,Integer> goodsIdNum)
	{
		for(MoveframeProduct mp : productsList)
		{
			//剔除goodsId 和moveFrameId相同的
			if(goodsIdAndMoveFrameId.get(mp.getMoveframeId()) == null)
			{
				Set<Long> list = new  HashSet<Long>(); //存储goodsId相同的不需要重新存入
				list.add(mp.getGoodsId());
				goodsIdAndMoveFrameId.put(mp.getMoveframeId(), list);
			}
			else
			{
				goodsIdAndMoveFrameId.get(mp.getMoveframeId()).add(mp.getGoodsId());
			}
			
			
			MoveframeGoods mf = new MoveframeGoods();
			mf.setMoveframeId(mp.getMoveframeId());
			mf.setGoodsId(mp.getGoodsId());
			
			//该GoodsId尺寸
			if(goodsIdToInstanceId.get(mf) == null)
			{
				Map<Long,Integer> map = new HashMap<Long,Integer>();
				map.put(mp.getInstanceId(), 1);
				goodsIdToInstanceId.put(mf, map);
			}
			else
			{
				Map<Long,Integer> map = goodsIdToInstanceId.get(mf);
				
				if(map.get(mp.getInstanceId()) == null)
					map.put(mp.getInstanceId(), 1);
				else
				{	
					Integer num = map.get(mp.getInstanceId());
					num += 1;
					map.put(mp.getInstanceId(), num);
				}
			}

			//该GoodsId次数
			if(goodsIdNum.get(mf) == null)
			{
				goodsIdNum.put(mf, 1);
			}
			else
			{
				Integer num = goodsIdNum.get(mf);
				num += 1;
				goodsIdNum.put(mf, num);
			}

		}
		
	}

	
	
	private void emailTolAddInfo(List<MoveFrameInfo> emailAndCustomerList,Map<String, List<SendMailInfo>> sendMap,SendMailInfo sendToUser)
	{	
		
		for (MoveFrameInfo mc : emailAndCustomerList)
		{
			SendMailInfo sendInfo = null;
			try
			{
				 sendInfo = (SendMailInfo)sendToUser.clone();
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			if(StringUtil.isNotBlank(mc.getEmail())  && isEmail(mc.getEmail()))
			{
				sendInfo.setUserType(1); //內部用戶
				if (null == sendMap.get(mc.getEmail()))
				{					
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //添加需要发送的内容
					list.add(sendInfo);
					sendMap.put(mc.getEmail(), list);
				}
				else
				{	

					sendMap.get(mc.getEmail()).add(sendInfo);
				}	
			}
			if (StringUtil.isNotBlank(mc.getCustomerEmail()) && isEmail(mc.getCustomerEmail()))
			{
				sendInfo.setUserRank(mc.getUserRank()); //设置用户等级
				sendInfo.setUserType(2); //客戶
				if (null == sendMap.get(mc.getCustomerEmail()))
				{
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //添加需要发送的内容
					list.add(sendInfo);
					sendMap.put(mc.getCustomerEmail(), list);
				}
				else
				{
					List<SendMailInfo> union = sendMap.get(mc.getCustomerEmail());
					SendMailInfo removeTmp = null;
					for(SendMailInfo s : union)
					{
						//找到同款产品
						if(s.getGoodsId().equals(sendInfo.getGoodsId()))
						{
							removeTmp = s;
							break;
						}
					}
					if(removeTmp != null)
						sendMap.get(mc.getCustomerEmail()).remove(removeTmp); //剔除同款产品
					sendMap.get(mc.getCustomerEmail()).add(sendInfo);
				}
			}
		}
		
	}
	
	
	//价格改变、发内部员工也要特出重复的GoodsId
	private void emailTolAddInfo(List<MoveFrameInfo> emailAndCustomerList,Map<String, List<SendMailInfo>> sendMap,SendMailInfo sendToUser,Integer type)
	{	
		
		for (MoveFrameInfo mc : emailAndCustomerList)
		{
			SendMailInfo sendInfo = null;
			try
			{
				 sendInfo = (SendMailInfo)sendToUser.clone();
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			if(StringUtil.isNotBlank(mc.getEmail())  && isEmail(mc.getEmail()))
			{
				sendInfo.setUserType(1); //內部用戶
				if (null == sendMap.get(mc.getEmail()))
				{					
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //添加需要发送的内容
					list.add(sendInfo);
					sendMap.put(mc.getEmail(), list);
				}
				else
				{	
					List<SendMailInfo> union = sendMap.get(mc.getEmail());
					SendMailInfo removeTmp = null;
					for(SendMailInfo s : union)
					{
						//找到同款产品
						if(s.getGoodsId().equals(sendInfo.getGoodsId()))
						{
							removeTmp = s;
							break;
						}
					}
					if(removeTmp != null)
						sendMap.get(mc.getEmail()).remove(removeTmp); //剔除同款产品
					sendMap.get(mc.getEmail()).add(sendInfo);
				}	
			}
			if (StringUtil.isNotBlank(mc.getCustomerEmail()) && isEmail(mc.getCustomerEmail()))
			{
				sendInfo.setUserRank(mc.getUserRank()); //设置用户等级
				sendInfo.setUserType(2); //客戶
				if (null == sendMap.get(mc.getCustomerEmail()))
				{
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //添加需要发送的内容
					list.add(sendInfo);
					sendMap.put(mc.getCustomerEmail(), list);
				}
				else
				{
					List<SendMailInfo> union = sendMap.get(mc.getCustomerEmail());
					SendMailInfo removeTmp = null;
					for(SendMailInfo s : union)
					{
						//找到同款产品
						if(s.getGoodsId().equals(sendInfo.getGoodsId()))
						{
							removeTmp = s;
							break;
						}
					}
					if(removeTmp != null)
						sendMap.get(mc.getCustomerEmail()).remove(removeTmp); //剔除同款产品
					sendMap.get(mc.getCustomerEmail()).add(sendInfo);
				}
			}
		}
		
	}
	
	

	/**
	 * 
	 * @param email 待验证的Email
	 * @return  
	 */
	private boolean isEmail(String email)
	{
		 String regex ="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, email);
	}

	/**
	* @param regex
	*            正则表达式字符串
	* @param str
	*            要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	private  boolean match(String regex, String str)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
}
