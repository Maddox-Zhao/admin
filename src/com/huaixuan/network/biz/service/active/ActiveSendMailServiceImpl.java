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
 * @version ����ʱ�䣺2012-3-27 ����05:12:07
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
	 * ������ӵĲ�Ʒ
	 */
	public void sendDLMail()
	{
		sendMailToDlGoodsInfo();
	}
	
	/**
	 * �ػ���ӵĲ�Ʒ
	 */
	public void sendHKMail()
	{
		sendMailToHKGoodsInfo();
	}
	
	/**
	 * ͬһ�ͻ���ͬ�۵�
	 */
	public void notSamePrice()
	{
		toOneCustomerSameGoodsIDNotSamePrice();
	}

	
	
	/**
	 * �����ʼ� ��Ҫ���ݰ������Ʒ��ͬһ���ͻ��в�ͬ�ļ۸��ʱ�򣬰������Ʒ����Ϣ���͸���Ӧ�ĸ����� ���壺<br/>
	 * �Կͻ���22<br/>
	 * ��ƷIDΪ��4296137253242<br/>
	 * �ͺţ�114096 ���ʣ�V0015 ��ɫ:2510<br/>
	 * 
	 * �ڻ���Ͷ���1 ��ǮΪ:1081.0<br/>
	 * �ڻ���Ͷ���2 ��ǮΪ:1080.0<br/>
	 * ------------------------------------------------<br/>
	 */
	private void toOneCustomerSameGoodsIDNotSamePrice()
	{

		List<Long> customerIds = sendMailDao.getAllCustomerId(); 
		Map<String, List<String>> sendMap = new HashMap<String, List<String>>();// ���ڴ洢��Ҫ�����ʼ��ĵ�ַ
		for (Long id : customerIds)
		{
			if (id != null)
			{
				// ���ڴ����ͬ��Ʒ��Ӧ�Ĳ�ͬ��Ǯ��Map��ֵ�ŵ���set��set�ŵ���MoveframeGoods��������Ҫ��д����hashCode��equals������productId,area,discountPrice���ж϶����Ƿ���ͬ
				Map<Long, Set<MoveframeProduct>> productIdSameAndPriceNotSameMap = new HashMap<Long, Set<MoveframeProduct>>();
				List<Long> moveFrameIds = sendMailDao.getMoveFrameIdsByCustomerId(id);
				for(Long  moveFrameId : moveFrameIds)
				{
					List<MoveframeProduct> moveframeGoods = sendMailDao.getMoveFrameGoodsInfoByMoveFrameId(moveFrameId); // �õ��ÿͻ����еĻ��Ʒ
					for (MoveframeProduct mf : moveframeGoods)
					{
						//MoveframeGoods ��д��equals��hashCode��������ͬһProductIdͬһ�����в�ͬ�۸��ʱ����ܼ���set������
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
						// ƴ����Ҫ���͵���Ϣ
						StringBuffer str = new StringBuffer();
						str.append("�Կͻ���" + id + "\n");
						Product product = productDao.getProductById(key);
						str.append("��ƷIDΪ��" + key + "\r\n");
						str.append("�ͺţ�" + product.getType() + "\t���ʣ�"
								+ product.getMaterial() + "\t��ɫ:"
								+ product.getColor() + "\r\n");
						String s = "";
						for (MoveframeProduct value : values)
						{
							String moveframeName = sendMailDao.getMoveFrameById(value.getMoveframeId()).getName();
							s += "�ڻ��" + moveframeName + "     ";
							s += "��ǮΪ:" + value.getDiscountPrice() + "\n";
							emails.add(sendMailDao.getHeaderEmail(value.getMoveframeId()));
						}
						str.append(s + "\n");
						str.append("-----------------------------------\n");
						
						for(String email : emails)
						{
							// �洢��Ҫ�����ʼ�����ϵ����Ϣ
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
		// �����ʼ�
		Set<Entry<String, List<String>>> set = sendMap.entrySet();
		Iterator<Entry<String, List<String>>> it = set.iterator();
		while (it.hasNext())
		{
			String email = it.next().getKey();
			simpleMailMessage.setTo(email);
			simpleMailMessage.setSubject("��ͬһ�ͻ�ͬһ��ƷID�ڲ�ͬ����в�ͬ�ļ۸�");
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
	 * ��ǰʱ��ǰһ�촴���Ļ��Ϣ���͸���Ӧ��Ա��
	 */
	private void getYesterDayCreateMoveFrame()
	{

		List<MoveFrameInfo> yesterDayMoveframeInfo = sendMailDao.getYesterDayNewCreateMoveFrames(new Integer(1)); //�õ����촴�������л

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Map<String, List<String>> sendMap = new HashMap<String, List<String>>();// ���ڴ洢��Ҫ�����ʼ��ĵ�ַ
		for (MoveFrameInfo m : yesterDayMoveframeInfo)
		{
			StringBuffer str = new StringBuffer();// ����ƴ������͵���Ϣ
			str.append(m.getAuthor());
			str.append("�����˻��");
			str.append(m.getMoveFrameName());
			str.append("\r\n");
			if (null != m.getDepName() && !"".equals(m.getDepName()))
			{
				str.append("�û�����ڣ�" + m.getDepName());
			}
			str.append("\r\n");
			str.append("���ʼʱ��Ϊ��" + sdf.format(m.getGmtStart()) + "\r\n");
			str.append("�����ʱ��Ϊ��" + sdf.format(m.getGmtEnd()));
			str.append("\r\n");
			str.append("�û�µĿͻ���:\n");
			// ͨ�����ID�õ��û����������Ա����email�Ϳͻ�����
			List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(m.getMoveFrameId());
			int i = 1; // ���ͻ�����ʱ�����ڻ���
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
				// �洢��Ҫ�����ʼ�����ϵ����Ϣ
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
		
		sendMailDao.updateLogStatus(1); //����״̬Ϊ�Դ���
		
		new SendMoveFrameThread(sendMap,mailEngine,"�����Ļ�б�").run();
		
	}
 
	/**
	 * ��ǰʱ��ǰһ���޸Ĺ��Ļ����Ϣ
	 */
	private void getYesterDayModifyMoveFrame()
	{
		List<MoveFrameInfo> yesterDayModifyMoveFrameInfo = sendMailDao.getYesterDayModifyMoveFrames(new Integer(2)); // �õ������޸ĵ����л
		Map<Long, List<String>> sendInfoMap = new HashMap<Long, List<String>>();//���ڴ洢��Ҫ���͵���Ϣ�Ͷ�Ӧ���ID
		// �洢�����ʼ��ĵ�ַ����Ϣ
		Map<String, List<String>> sendMap = new HashMap<String, List<String>>();

		// ѭ���õ�����Ӧ���޸���Ϣ
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

		// ѭ��ƴ����Ҫ���͵��ַ������洢��sendMap��
		Set<Entry<Long, List<String>>> entry = sendInfoMap.entrySet();
		Iterator<Entry<Long, List<String>>> it = entry.iterator();
		while (it.hasNext())
		{
			Entry<Long, List<String>> keyAndValue = it.next();
			Long moveFrameId = keyAndValue.getKey();
			String moveFrameName = sendMailDao.getMoveFrameById(moveFrameId).getName();
			StringBuffer str = new StringBuffer();
			str.append("��� " + moveFrameName + "  ���������޸�:\n");
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
							str.append("�޸Ļ������:" + names[0] + "  Ϊ  " + names[1] + "\r\n");
						}
						if((i+1) == 2)
						{
							String[] names = strs[i].split("->");
							str.append("���ڻ���������ţ�" + names[1] + "\r\n");
						}
						if((i+1) == 3)
						{
							String[] names = strs[i].split("->");
							if(names.length >= 2)
								str.append("����Ա����" + names[1] + "\r\n");
							else
								str.append("����Ա������"  + "\r\n");
						}
						if((i+1) == 4)
						{
							String[] names = strs[i].split("->");
							if(names.length >= 2)
								str.append("���пͻ���" + names[1] + "\r\n");
							else
								str.append("���пͻ�����" + "\r\n");
						}
						if((i+1) == 5)
						{
							String[] names = strs[i].split("->");
							str.append("�޸Ŀ�ʼʱ�䣺" + names[0] + " Ϊ" + names[1] + "\r\n");
						}
						if((i+1) == 6)
						{
							String[] names = strs[i].split("->");
							str.append("�޸Ľ���ʱ�䣺" + names[0] + " Ϊ" + names[1] + "\r\n");
						}
						if((i+1) == 7)
						{
							String[] names = strs[i].split("->");
							if(names.length >= 2)
							{
								
								str.append("�����޸Ŀͻ����ͣ�"  + (names[1].equals("1")?"���ۿͻ�":(names[1].equals("2")?"�����ͻ�":(names[1].equals("3")?"�����ͻ�":"��"))) + "\r\n");
							}
							else
							{
								str.append("�޸Ŀͻ����ͣ���" + "\r\n");
							}
						}
					}
				}
			
			}// forѭ�����

			str.append("--------------------------------------------------------------\n");
			// ͨ�����ID�õ��û����������Ա����email
			List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(moveFrameId);

			// ����Ҫ���͵���Ϣ�Ͷ�Ӧ��Email��Ӧ����
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
		} // whileѭ�����
		
		sendMailDao.updateLogStatus(2); //����״̬Ϊ�Դ���
		new SendMoveFrameThread(sendMap, mailEngine, "�޸ĵĻ�б�").run();
		
	}


	/**
	 * �ػ���Ӳ�Ʒ����Ҫ���͵���Ϣ
	 */
	@SuppressWarnings("unchecked")
	private void sendMailToHKGoodsInfo()
	{
		Map<String, List<SendMailInfo>> sendMap = new HashMap<String, List<SendMailInfo>>();// ���ڴ洢��Ҫ�����ʼ��ĵ�ַ������
		
		Map parMap = new HashMap();
		List<MoveFrameLog> notDealListAdd = sendMailDao.getMoveFrameLogByType(3);
		List<MoveframeProduct> addProductsList = new ArrayList<MoveframeProduct>();
		
		for(MoveFrameLog log : notDealListAdd)
		{
			if(log.getArea().intValue() == 1) //�����Ĳ�Ʒ����Ҫ����
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
			//����״̬Ϊ�Դ���
			parMap.put("productId",Long.parseLong(log.getNote()));
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type", log.getType());
			sendMailDao.updateLogDealStatus(parMap);
					
		}
		
		List<MoveFrameLog> notDealListDel= sendMailDao.getMoveFrameLogByType(4);//������
		for(MoveFrameLog log : notDealListDel)
		{
			if(log.getArea().intValue() == 1) //�����Ĳ�Ʒ����Ҫ����
				continue;
			MoveframeProduct mf = new MoveframeProduct();
			mf.setMoveframeId(log.getMoveframeId());
			Product product = productDao.getProductById(Long.valueOf(log.getNote()));
			mf.setGoodsId(product.getGoodsId());
			addProductsList.remove(mf);  //��������޳�������
			//����״̬Ϊ�Դ���
			parMap.put("productId",Long.parseLong(log.getNote()));
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type", log.getType());
			sendMailDao.updateLogDealStatus(parMap);				
		}
		
		
		Map<Long,Set<Long>> goodsIdAndMoveFrameId = new HashMap<Long, Set<Long>>();//�޳�goodsId,moveFrameId ����ͬ����
		
		removeRepelat(addProductsList, goodsIdAndMoveFrameId, 3); //ȥ��goodsId,moveFrameId��ͬ����
		
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
					sendToUser.setDiscountPrice(mgList.get(0).getDiscountPrice()); //�ػݼ�
					sendToUser.setHkPrice(goods.getHkhxPrice()); //lussomoda��		
					// ͨ�����ID�õ��û����������Ա���͹˿͵�email
					List<MoveFrameInfo> emailAndCustomerList = sendMailDao.getEmailAndCustomerName(moveFrameId);
					emailTolAddInfo(emailAndCustomerList,sendMap,sendToUser);
				}
			}
		}
		
		
		//�����̷߳����ʼ�
		new SendMailThread(sendMap,mailEngine,"����ػ���Ӳ�Ʒ�б�","/HKAddInfo.vm").run();
		
		
	}
	
	/**
	 * ��ӵĴ�����Ʒ
	 */
	@SuppressWarnings("unchecked")
	private void sendMailToDlGoodsInfo()
	{
		//�õ�δ�������ӵĲ�Ʒ
		Map<String, List<SendMailInfo>> sendMap = new HashMap<String, List<SendMailInfo>>();// ���ڴ洢��Ҫ�����ʼ��ĵ�ַ������
		List<MoveFrameLog> notDealListAdd = sendMailDao.getMoveFrameLogByType(3);
		List<MoveframeProduct> addProductsList = new ArrayList<MoveframeProduct>();
		List<MoveframeProduct> addProductsListCopy = new ArrayList<MoveframeProduct>();
		Map parMap = new HashMap();
		
		for(MoveFrameLog log : notDealListAdd)
		{
			if(log.getArea().intValue() == 2) //�ػݵĲ�Ʒ����Ҫ����
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
			//����״̬Ϊ�Դ���
			parMap.put("productId", log.getNote());
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type", 3);
			sendMailDao.updateLogDealStatus(parMap);
					*/
		}
		
		//�õ�δ�����ɾ���Ĳ�Ʒ
		List<MoveFrameLog> notDealListDel= sendMailDao.getMoveFrameLogByType(4);
		List<MoveframeProduct> delProductsList = new ArrayList<MoveframeProduct>();
		for(MoveFrameLog log : notDealListDel)
		{
			if(log.getArea().intValue() == 2) //�ػݵĲ�Ʒ����Ҫ����
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
			
			
			//����״̬Ϊ�Դ���
			parMap.put("productId", log.getNote());
			parMap.put("moveFrameId", log.getMoveframeId());
			parMap.put("type",4);
			sendMailDao.updateLogDealStatus(parMap);
			*/
		}
		
		
		/*
		 * �޳���Ӻ�������
		 */
		if(addProductsList != null)
			addProductsList.removeAll(delProductsList);
		if(delProductsList != null)
			delProductsList.removeAll(addProductsListCopy);
		
	
		//���´�����ӵĲ�Ʒ��״̬
		List<MoveFrameLog> addLogs = sendMailDao.getMoveFrameLogByType(3);
		List<MoveFrameLog> delLogs = sendMailDao.getMoveFrameLogByType(4);
		delLogs.addAll(addLogs);
		
		for(MoveFrameLog ml : delLogs)
		{
			if(ml.getArea().equals(1)) //������ӵĴ�����Ʒ״̬
			{
				parMap.put("productId", ml.getNote());
				parMap.put("moveFrameId", ml.getMoveframeId());
				parMap.put("type", ml.getType());
				sendMailDao.updateLogDealStatus(parMap);
			}
			
			if(ml.getArea().equals(2)) //���¼���ɾ���Ĵ�����Ʒ״̬
			{
				parMap.put("productId", ml.getNote());
				parMap.put("moveFrameId", ml.getMoveframeId());
				parMap.put("type",4);
				sendMailDao.updateLogDealStatus(parMap);
			}
		}
		 
		
		Map<Long,Set<Long>> addGoodsIdAndMoveFrameId = new HashMap<Long, Set<Long>>();//�洢�޳�goodsId,moveFrameId����ͬ����
		Map<Long,Set<Long>> delGoodsIdAndMoveFrameId = new HashMap<Long, Set<Long>>();//�洢�޳�goodsId,moveFrameId ����ͬ����
		
		Map<MoveframeGoods,Map<Long,Integer>> addGoodsIdToInstanceId = new HashMap<MoveframeGoods, Map<Long,Integer>>();//�洢��ӵ�goodsId��Ӧ�ĳߴ�
		Map<MoveframeGoods,Map<Long,Integer>> delGoodsIdToInstanceId = new HashMap<MoveframeGoods, Map<Long,Integer>>();//�洢��ӵ�goodsId��Ӧ�ĳߴ�
		
		
		
		
		Map<MoveframeGoods,Integer> goodsIdAddNum = new HashMap<MoveframeGoods, Integer>(); //�洢���goodsId�Ĳ�Ʒ����˶��ٴ�
		Map<MoveframeGoods,Integer> goodsDelAddNum = new HashMap<MoveframeGoods, Integer>();//�洢���goodsId�Ĳ�Ʒɾ���˶��ٴ�
		
		removeRepelat(addProductsList, addGoodsIdAndMoveFrameId, 3,addGoodsIdToInstanceId,goodsIdAddNum); 
		removeRepelat(delProductsList, delGoodsIdAndMoveFrameId, 4,delGoodsIdToInstanceId,goodsDelAddNum);

		
		List<MoveframeGoods> allChangeMoveFrameGoods = new ArrayList<MoveframeGoods>(); //�洢���иı�����Ĳ�Ʒ

		//��ӵĲ�Ʒ
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
					moveframeGoods.setType(1);//����
					Integer addNum = goodsIdAddNum.get(moveframeGoods); //���ӵĴ���
					if(moveframeGoods.getGoodsNum().intValue() == addNum.intValue())
					{
						moveframeGoods.setHasStock(1);//���޵���
					}
					else
					{
						moveframeGoods.setHasStock(2);//���Ӳ�Ʒ
					}
					allChangeMoveFrameGoods.add(moveframeGoods);
				}
			}
		}
		
		
		
		//ɾ���������Ĳ�Ʒ
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
					moveframeGoods.setType(2);//ɾ����
					if(moveframeGoods.getGoodsNum() == 0)
						moveframeGoods.setHasStock(0);//���е���
					else
						moveframeGoods.setHasStock(3);//����
					allChangeMoveFrameGoods.add(moveframeGoods);
				}
			}
		}
		
		
		//ƴ����Ҫ���͵���Ϣ
		for(MoveframeGoods all : allChangeMoveFrameGoods)
		{
			MoveFrame mo = sendMailDao.getMoveFrameById(all.getMoveframeId());
			if(mo.getStatus().equals("closed")) //�رյĻ����Ҫ����
				continue;
			Goods g = goodsDao.getGoods(all.getGoodsId());
			SendMailInfo sendToUser = new SendMailInfo();
			sendToUser.setType(g.getType());
			sendToUser.setMaterial(g.getMaterial());
			sendToUser.setColor(g.getColor());
			sendToUser.setBrand(EnumBrandType.toMap().get(g.getBrandId()+""));
			sendToUser.setSeries(EnumSeriesType.toMap().get(g.getIdSeries()+""));
			sendToUser.setDiscountPrice(all.getDiscountPrice());//������
			sendToUser.setIdProduct(g.getProductId()+"");
			sendToUser.setMiddleImg(g.getImgMiddle());
			sendToUser.setGoodsId(g.getId());
			sendToUser.setHasStock(all.getHasStock()); //�Ƿ�Ϊ������ɾ��
			sendToUser.setGoodsNum(all.getGoodsNum());
			sendToUser.setHxPrice(g.getGoodsPrice());//���ϼ�
			sendToUser.setMoveFrameId(all.getMoveframeId());
			sendToUser.setMoveframeName(mo.getName());
			
			Set<Long> instanceIds = null;
			//����ߴ�
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

			// ͨ�����ID�õ��û����������Ա���͹˿͵�email
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
				if(sendMailInfo.getUserType().equals(1))//�ڲ��û�
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
				else if(sendMailInfo.getUserType().equals(2)) //�ͻ�
				{
					toCustomer.put(email, list);
				}
			}
		}
		
		//�����̷߳����ʼ����ͻ�
		new SendMailThread(toCustomer,mailEngine,"������Ʒ���ı��б�","/goodsNumberInfo.vm").run();
		
		//�����̷߳����ʼ����ڲ��û�
		new SendDLMailToUserThread(toUser,mailEngine,"��½��Ʒ���ı��б�","/dlGoodsNumberChange.vm").run();
			
	}
	
	/**
	 * ������Ʒ�۸�ı��б�
	 */
	private void getYesterDayModifyMoveFrameGoods()
	{		
		List<MoveFrameInfo> priceChangeList = sendMailDao.getYesterDayModifyMoveFrames(new Integer(5)); //���м۸�ı���Ĳ�Ʒ
		Map<String, List<SendMailInfo>> sendMap = new HashMap<String, List<SendMailInfo>>();// ���ڴ洢��Ҫ�����ʼ��ĵ�ַ
		for (MoveFrameInfo m : priceChangeList)
		{
			if(m.getArea().equals(2)) //�ػݵļ�Ǯ�ı䲻�÷�
				continue;
			SendMailInfo sendToUser = new SendMailInfo();
			int k = 1; // ���ڲ�ѭ����˫����ѭ����ʱ��Ž���
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
			
			// ͨ�����ID�õ��û����������Ա����email
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
					sendInfo.setUserType(1); //�Ȳ��Ñ�
					if (null == sendMap.get(mc.getEmail()))
					{					
						List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //�����Ҫ���͵�����
						list.add(sendInfo);
						sendMap.put(mc.getEmail(), list);
					}
					else
					{	
						List<SendMailInfo> union = sendMap.get(mc.getEmail());
						SendMailInfo removeTmp = null;
						for(SendMailInfo s : union)
						{
							//�ҵ�ͬ���Ʒ
							if(s.getGoodsId().equals(sendInfo.getGoodsId()))
							{
								removeTmp = s;
								break;
							}
						}
						if(removeTmp != null)
							sendMap.get(mc.getEmail()).remove(removeTmp); //�޳�ͬ���Ʒ
						sendMap.get(mc.getEmail()).add(sendInfo);
					}	
				}
				if (StringUtil.isNotBlank(mc.getCustomerEmail()) && isEmail(mc.getCustomerEmail()))
				{
					//�۸�ı�֮��2 3 ���û���
					if("e".equals(sendInfo.getUserRank()) || "s".equals(sendInfo.getUserRank()) )
					{
						sendInfo.setUserRank(mc.getUserRank()); //�����û��ȼ�
						sendInfo.setUserType(2); //�͑�
						if (null == sendMap.get(mc.getCustomerEmail()))
						{
							List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //�����Ҫ���͵�����
							list.add(sendInfo);
							sendMap.put(mc.getCustomerEmail(), list);
						}
						else
						{
							List<SendMailInfo> union = sendMap.get(mc.getCustomerEmail());
							SendMailInfo removeTmp = null;
							for(SendMailInfo s : union)
							{
								//�ҵ�ͬ���Ʒ
								if(s.getGoodsId().equals(sendInfo.getGoodsId()))
								{
									removeTmp = s;
									break;
								}
							}
							if(removeTmp != null)
								sendMap.get(mc.getCustomerEmail()).remove(removeTmp); //�޳�ͬ���Ʒ
							sendMap.get(mc.getCustomerEmail()).add(sendInfo);
						}
					}
				}
			}
		}
		
		sendMailDao.updateLogStatus(5); //����״̬Ϊ�Դ���

		
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
				if(sendMailInfo.getUserType().equals(1))//�ڲ��û�
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
				else if(sendMailInfo.getUserType().equals(2)) //�ͻ�
				{
					toCustomer.put(email, list);
				}
			}
		}
		
		
		new SendMailThread(toCustomer,mailEngine,"������Ʒ�۸�ı��б�","/goodsPriceModify.vm").run();		//�����̷߳��ʼ�
		
		new SendDLMailToUserThread(toUser,mailEngine,"��½��Ʒ�۸�ı��б�","/goodsPriceModify2User.vm").run();
		
	}

	private void removeRepelat(List<MoveframeProduct> productsList,Map<Long,Set<Long>> goodsIdAndMoveFrameId,Integer type)
	{
		for(MoveframeProduct mp : productsList)
		{
			//�޳�goodsId ��moveFrameId��ͬ��
			if(goodsIdAndMoveFrameId.get(mp.getMoveframeId()) == null)
			{
				Set<Long> list = new  HashSet<Long>(); //�洢goodsId��ͬ�Ĳ���Ҫ���´���
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
			//�޳�goodsId ��moveFrameId��ͬ��
			if(goodsIdAndMoveFrameId.get(mp.getMoveframeId()) == null)
			{
				Set<Long> list = new  HashSet<Long>(); //�洢goodsId��ͬ�Ĳ���Ҫ���´���
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
			
			//��GoodsId�ߴ�
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

			//��GoodsId����
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
				sendInfo.setUserType(1); //�Ȳ��Ñ�
				if (null == sendMap.get(mc.getEmail()))
				{					
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //�����Ҫ���͵�����
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
				sendInfo.setUserRank(mc.getUserRank()); //�����û��ȼ�
				sendInfo.setUserType(2); //�͑�
				if (null == sendMap.get(mc.getCustomerEmail()))
				{
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //�����Ҫ���͵�����
					list.add(sendInfo);
					sendMap.put(mc.getCustomerEmail(), list);
				}
				else
				{
					List<SendMailInfo> union = sendMap.get(mc.getCustomerEmail());
					SendMailInfo removeTmp = null;
					for(SendMailInfo s : union)
					{
						//�ҵ�ͬ���Ʒ
						if(s.getGoodsId().equals(sendInfo.getGoodsId()))
						{
							removeTmp = s;
							break;
						}
					}
					if(removeTmp != null)
						sendMap.get(mc.getCustomerEmail()).remove(removeTmp); //�޳�ͬ���Ʒ
					sendMap.get(mc.getCustomerEmail()).add(sendInfo);
				}
			}
		}
		
	}
	
	
	//�۸�ı䡢���ڲ�Ա��ҲҪ�س��ظ���GoodsId
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
				sendInfo.setUserType(1); //�Ȳ��Ñ�
				if (null == sendMap.get(mc.getEmail()))
				{					
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //�����Ҫ���͵�����
					list.add(sendInfo);
					sendMap.put(mc.getEmail(), list);
				}
				else
				{	
					List<SendMailInfo> union = sendMap.get(mc.getEmail());
					SendMailInfo removeTmp = null;
					for(SendMailInfo s : union)
					{
						//�ҵ�ͬ���Ʒ
						if(s.getGoodsId().equals(sendInfo.getGoodsId()))
						{
							removeTmp = s;
							break;
						}
					}
					if(removeTmp != null)
						sendMap.get(mc.getEmail()).remove(removeTmp); //�޳�ͬ���Ʒ
					sendMap.get(mc.getEmail()).add(sendInfo);
				}	
			}
			if (StringUtil.isNotBlank(mc.getCustomerEmail()) && isEmail(mc.getCustomerEmail()))
			{
				sendInfo.setUserRank(mc.getUserRank()); //�����û��ȼ�
				sendInfo.setUserType(2); //�͑�
				if (null == sendMap.get(mc.getCustomerEmail()))
				{
					List<SendMailInfo> list = new ArrayList<SendMailInfo>(); //�����Ҫ���͵�����
					list.add(sendInfo);
					sendMap.put(mc.getCustomerEmail(), list);
				}
				else
				{
					List<SendMailInfo> union = sendMap.get(mc.getCustomerEmail());
					SendMailInfo removeTmp = null;
					for(SendMailInfo s : union)
					{
						//�ҵ�ͬ���Ʒ
						if(s.getGoodsId().equals(sendInfo.getGoodsId()))
						{
							removeTmp = s;
							break;
						}
					}
					if(removeTmp != null)
						sendMap.get(mc.getCustomerEmail()).remove(removeTmp); //�޳�ͬ���Ʒ
					sendMap.get(mc.getCustomerEmail()).add(sendInfo);
				}
			}
		}
		
	}
	
	

	/**
	 * 
	 * @param email ����֤��Email
	 * @return  
	 */
	private boolean isEmail(String email)
	{
		 String regex ="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, email);
	}

	/**
	* @param regex
	*            ������ʽ�ַ���
	* @param str
	*            Ҫƥ����ַ���
	* @return ���str ���� regex��������ʽ��ʽ,����true, ���򷵻� false;
	*/
	private  boolean match(String regex, String str)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
}
