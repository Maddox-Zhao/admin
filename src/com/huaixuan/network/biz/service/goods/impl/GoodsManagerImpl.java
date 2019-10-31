package com.huaixuan.network.biz.service.goods.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.dao.goods.CategoryDao;
import com.huaixuan.network.biz.dao.goods.GoodsAttrDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsGalleryDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.domain.Comments;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsAttr;
import com.huaixuan.network.biz.domain.goods.GoodsGallery;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsRule;
import com.huaixuan.network.biz.domain.goods.GoodsWholsale;
import com.huaixuan.network.biz.domain.goods.Unit;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.shop.Activity;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.enums.EnumGoodsStatus;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.GoodsRuleManager;
import com.huaixuan.network.biz.service.goods.GoodsWholsaleManager;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.goods.WebSiteEmailService;
import com.huaixuan.network.biz.service.hy.ProductService;
import com.huaixuan.network.biz.service.stock.AvailableStockService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.EmallStringUtil;
import com.huaixuan.network.common.util.ImgUtils;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("goodsManager")
public class GoodsManagerImpl implements GoodsManager {

	protected Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private GoodsDao goodsDao;

	@Autowired
	private UploadUtil uploadUtil;

	@Autowired
	private GoodsGalleryDao goodsGalleryDao;

	@Autowired
	private CategoryDao categoryDao;

	// @Autowired
	// private CabinetDao cabinetDao;

	// @Autowired
	// private CommentsManager commentsManager;

	@Autowired
	private GoodsAttrDao goodsAttrDao;

	@Autowired
	private CatAttrRelDao catAttrRelDao;

	@Autowired
	private GoodsInstanceDao goodsInstanceDao;

	@Autowired
	private AttributeManager attrManager;

	@Autowired
	private AvailableStockDao availableStockDao;

	@Autowired
	private GoodsRuleManager goodsRuleManager;

	@Autowired
	private GoodsWholsaleManager goodsWholsaleManager;

	@Autowired
	private WebSiteEmailService webSiteEmailService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	
	@Autowired
	private AvailableStockService availableStockService;

	@Autowired
	private UserDao userDao;

	private String equ = "=";
	private String colon = ":";
	private String semicolon = ";";

	private @Value("${file.upload.dir}")
	String upload;

	public List<AvailableStock> getAvailableStockGroupByGoodsId(Long goodsId) {
		return availableStockDao.getAvailableStockGroupByGoodsId(goodsId);
	}

	public void addGoods(Goods goodsDao) {
		log.info("GoodsManagerImpl.addGoods method");
		this.goodsDao.addGoods(goodsDao);

	}

	/* @model: */
	public void editGoods(Goods goods) {
		log.info("GoodsManagerImpl.editGoods method");
		try {
			this.goodsDao.editGoods(goods);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/* @model: */
	public void removeGoods(Long goodsId) {
		log.info("GoodsManagerImpl.removeGoods method");
		try {
			this.goodsDao.removeGoods(goodsId);
		} catch (Exception e) {
			log.error(e.getMessage());
			// throw new ManagerException(e);
		}
	}

	/* @model: */
	public Goods getGoods(Long goodsId) {
		return goodsDao.getGoods(goodsId);
	}

	public Goods getClientGoodsExist(Map pramas) {
		return goodsDao.getClientGoodsExist(pramas);
	}
	
	/* @model: */
	public List<Goods> getGoodss() {
		log.info("GoodsManagerImpl.getGoodss method");
		try {
			return this.goodsDao.getGoodss();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	public List<Goods> getGoodsByIds(List ids) {
		log.info("GoodsManagerImpl.getGoodss method");
		try {
			return this.goodsDao.getGoodsByIds(ids);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/*
	 * 
	 * @see com.hundsun.bible.facade.goods.GoodsManager#publishGoodsByExcel(com.hundsun.bible.domain.model.Goods,
	 * java.io.File, java.lang.String)
	 */
	public boolean publishGoodsByExcel(List<Object> goodsList) {
		log.info("GoodsManagerImpl.getGoodss method");
		try {
			for (int temp = 0; temp < goodsList.size(); temp++) {
				Goods goods = (Goods) goodsList.get(temp);
				List<File> imagesList = new ArrayList<File>();
				List<String> imagesFileName = new ArrayList<String>();
				// File[] images=null;
				// String[] imagesFileName=null;
				if (StringUtil.isNotBlank(goods.getOriginalImg())) {
					String[] img = goods.getOriginalImg().split(";");
					for (int imgTemp = 0; imgTemp < img.length; imgTemp++) {
						File tempFile = new File(img[imgTemp]);
						imagesList.add(tempFile);
						imagesFileName.add(tempFile.getName());
					}
				}
				long id = this.goodsDao.addGoods(goods);

				// addAgentGoodsRelation(goods, id);
				// //added by  2009/11/12 end

				if (null != imagesList && imagesList.size() > 0) {
					String goodsPicPath = "goods" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
					String realPathPre = uploadUtil.getRealUpload() + Constants.FILE_SEP
							+ uploadUtil.getUploadRootPath();
					for (int i = 0; i < imagesList.size(); i++) {
						String fileName = uploadUtil.upload(imagesList.get(i), imagesFileName.get(i), goodsPicPath);
						GoodsGallery goodsGallery = reduceImg(realPathPre, goodsPicPath, fileName);
						goodsGallery.setGoodsId(id);
						int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGallery.getGoodsId());
						goodsGallery.setSort(maxSort + 1);
						goodsGalleryDao.addGoodsGallery(goodsGallery);
						if (i == 0) {
							goods.setOriginalImg(goodsPicPath + Constants.FILE_SEP + fileName);
							goods.setImgLarge(goodsGallery.getImgLarge());
							goods.setImgMiddle(goodsGallery.getImgMiddle());
							goods.setImgSmall(goodsGallery.getImgSmall());
							goodsDao.editGoods(goods);
						}
					}

				}

				if (goods.getChoose() != null && goods.getChoose().length() != 0) {
					String[] choose = goods.getChoose().split("\\+");
					GoodsAttr goodsAttr = null;
					for (int i = 0; i < choose.length; i++) {
						if (choose[i] != null) {
							String[] goodsAttrString = choose[i].split("\\*");
							for (int j = 2; j < goodsAttrString.length; j++) {
								goodsAttr = null;
								// log.info("goodsAttrString.length==>"+
								// goodsAttrString.length);
								goodsAttr = new GoodsAttr();
								goodsAttr.setGoodsId(id);
								goodsAttr.setAttrCode(goodsAttrString[0]);
								goodsAttr.setAttrName(goodsAttrString[1]);
								goodsAttr.setAttrValue(goodsAttrString[j]);
								goodsAttr.setPrice(0.0);
								goodsAttr.setIsUse(0);
								goodsAttrDao.addGoodsAttr(goodsAttr);
							}
						}
					}
					List<GoodsInstance> goodsInstanceList = addGoodsInstance(choose, goods, id);
					if (null != goodsInstanceList && goodsInstanceList.size() > 0)
						goodsInstanceDao.insertBatch(goodsInstanceList);
				}
				if (createBlankGoodsInstance(goods)) {
					GoodsInstance gi = new GoodsInstance(goods);
					this.goodsInstanceDao.createGoodsInstance(gi);
				}
			}

			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	//getGoodsListByConditionWithPage
	//getGoodsListByConditionWithPage
	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getGoodsListByConditionWithPage(Goods goods, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
		int count = goodsDao.getGoodsListByConditionWithPageCount(pramas);

		if (count > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
			List<Goods> goodsList = goodsDao.getGoodsListByConditionWithPage(pramas);
			if (goodsList != null && goodsList.size() > 0) {
				queryPage.setItems(goodsList);
			}
		}
		return queryPage;
	}
	

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> getGoodsListByConditionWithPage(Goods goods) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
		List<Goods> goodsList = goodsDao.getGoodsListByConditionWithPage(pramas);
		return goodsList;
	}

	
	/*
	 * 
	 */
	@Override
	public QueryPage getGoodsListByConditionWithPageByList(Goods goods,int currPage, int pageSize, List<Long> list,List<String> codeList) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
        pramas.put("hasListGoodsId", "yes");
        pramas.put("list", list);
        if(StringUtils.isNotBlank(goods.getThreeList()) || StringUtils.isNotBlank(goods.getTwoList()) || StringUtils.isNotBlank(goods.getOneList())){
			pramas.put("hasListcode", "yes");
		}
        pramas.put("codeList", codeList);
		int count = goodsDao.getGoodsListByConditionWithPageCount(pramas);

		if (count > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
           
			List<Goods> goodsList = goodsDao.getGoodsListByConditionWithPage(pramas);
			if (goodsList != null && goodsList.size() > 0) {
				queryPage.setItems(goodsList);
			}
		}
		return queryPage;
	}
	
	
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.goods.GoodsManager#getGoodsListByConditionWithPageBycodeList(com.huaixuan.network.biz.domain.goods.Goods, int, int, java.util.List)
	 */
	@Override
	public QueryPage getGoodsListByConditionWithPageBycodeList(Goods goods,int currPage, int pageSize, List<String> codeList) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
		if(StringUtils.isNotBlank(goods.getThreeList()) || StringUtils.isNotBlank(goods.getTwoList()) || StringUtils.isNotBlank(goods.getOneList())){
			pramas.put("hasListcode", "yes");
		}
		 
        pramas.put("codeList", codeList);
		int count = goodsDao.getGoodsListByConditionWithPageCount(pramas);

		if (count > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
			List<Goods> goodsList = goodsDao.getGoodsListByConditionWithPage(pramas);
			if (goodsList != null && goodsList.size() > 0) {
				queryPage.setItems(goodsList);
			}
		}
		return queryPage;
	}

	
	
	
	
	/**
     *
     */
	public boolean testGoodsInstance() {
		try {
			List<Goods> goodsList = this.getGoodss();
			for (Goods goods : goodsList) {
				// String attrValue=goods.getAttrValue();
				// String catCode=goods.getCatCode();
				Long goodsId = goods.getId();
				List<GoodsAttr> goodsAttrList = goodsAttrDao.getGoodsAttrByGoodSId(goodsId);
				boolean flag = createBlankGoodsInstance(goods);

				if (!flag) {
					StringBuffer tempAttr = new StringBuffer();
					for (GoodsAttr goodsAttr : goodsAttrList) {
						if (tempAttr.length() < 1 || tempAttr.toString().indexOf(goodsAttr.getAttrCode()) == -1) {
							if (tempAttr.length() == 0) {
								tempAttr.append(goodsAttr.getAttrCode()).append("*").append(goodsAttr.getAttrName());
								tempAttr.append("*").append(goodsAttr.getAttrValue());
							} else {
								tempAttr.append("+");
								tempAttr.append(goodsAttr.getAttrCode()).append("*").append(goodsAttr.getAttrName());
								tempAttr.append("*").append(goodsAttr.getAttrValue());
							}
						} else {
							tempAttr.append("*").append(goodsAttr.getAttrValue());
						}
					}
					if (tempAttr.length() > 0) {
						goods.setChoose(tempAttr.toString());
						String[] choose = goods.getChoose().split("\\+");
						List<GoodsInstance> goodsInstanceList = addGoodsInstance(choose, goods, goods.getId());
						if (null != goodsInstanceList && goodsInstanceList.size() > 0)
							goodsInstanceDao.insertBatch(goodsInstanceList);
					}
				} else {
					GoodsInstance gi = new GoodsInstance(goods);
					this.goodsInstanceDao.createGoodsInstance(gi);
				}

				// if (createBlankGoodsInstance(goods)) {
				// GoodsInstance gi = new GoodsInstance(goods);
				// this.goodsInstanceDao.createGoodsInstance(gi);
				// }

				// String attributeCode=goods.getCatCode();
				// Attribute attribute= (Attribute) attrManager.getAttributeByAttrCode(attributeCode);

			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * @param goodsAttr
	 * @return
	 * @throws Exception
	 */
	public List<GoodsInstance> addGoodsInstance(String[] attr, Goods goods, long goodsId) throws Exception {
		List<GoodsInstance> goodsInstanceList = new ArrayList<GoodsInstance>();
		int len = attr.length;
		String value = null, attrDesc = null, codeTemp = null;// instanceName=null
		int lenTemp = len;
		goods.setTitle(goods.getTitle().replaceAll("\\\r\\\n", "").trim());
		goods.setGoodsSn(goods.getGoodsSn().replaceAll("\\\r\\\n", "").trim());

		GoodsInstance goodsinstacne = new GoodsInstance(goods);
		goodsinstacne.setGoodsId(goodsId);
		goodsinstacne.setGoodsUnit(goods.getGoodsUnit());
		goodsinstacne.setMarketPrice(goods.getMarketPrice());
		goodsinstacne.setSellPrice(goods.getGoodsPrice());
		goodsinstacne.setSalesProPrice(goods.getSalesProPrice());
		// goodsinstacne.setWholesalePrice(goods.getWholesalePrice());
		goodsinstacne.setCatCode(goods.getCatCode());
		goodsinstacne.setIsPresent("0");// 
		String goodsAttr = "";
		String goodsAttrValue = "";
		String desc = "";
		String attrvale = "";
		if (StringUtil.isNotBlank(goods.getAttrDesc())) {
			goodsAttr = goods.getAttrDesc().replaceAll(colon, equ);
			goodsAttrValue = goods.getAttrValue().replaceAll(colon, equ);
			String[] values = goodsAttrValue.split(semicolon);
			String[] tempAttr = goodsAttr.split(semicolon);
			for (int j = 0; j < values.length; j++) {
				if (StringUtil.isNotBlank(values[j])) {
					String[] tempValue = values[j].split(equ);
					if (tempValue.length == 2) {
						Attribute attribute = attrManager.getAttributeByAttrCode(tempValue[0]);
						if (null != attribute && attribute.getIsInstance() == 1) {
							attrvale = attrvale + values[j] + semicolon;
							desc = desc + tempAttr[j] + semicolon;
						}
					}
				}
			}
		}

		int temp = 1;
		if (len == 1) {
			String[] aa = attr[0].split("\\*");
			for (int j = 2; j < aa.length; j++) {
				value = aa[0] + equ + aa[j] + semicolon;
				attrDesc = aa[1] + equ + aa[j] + semicolon;
				// instanceName=aa[j];
				GoodsInstance goodsinstacetemp = goodsinstacne.clone();
				goodsinstacetemp.setAttrs(attrvale + value);
				goodsinstacetemp.setAttrDesc(desc + attrDesc);
				if (temp < 10) {
					codeTemp = goods.getGoodsSn() + "0" + temp;
				} else {
					codeTemp = goods.getGoodsSn() + temp;
				}
				goodsinstacetemp.setCode(codeTemp);
				goodsinstacetemp.setInstanceName(goods.getTitle().replaceAll("\\\r\\\n", "").trim());
				// goodsinstacetemp.setInstanceName(goods.getTitle().replaceAll("\\\r\\\n",
				// "").trim()+"["+instanceName+"]");
				goodsInstanceList.add(goodsinstacetemp);
				temp++;
			}
		} else {
			String[] aa = attr[0].split("\\*");
			for (int j = 2; j < aa.length; j++) {
				value = aa[0] + equ + aa[j] + semicolon;
				attrDesc = aa[1] + equ + aa[j] + semicolon;
				// instanceName=aa[j];
				GoodsInstance goodsinstacetemp = goodsinstacne.clone();
				goodsinstacetemp.setAttrs(value);
				goodsinstacetemp.setAttrDesc(attrDesc);
				goodsinstacetemp.setInstanceName(goods.getTitle());
				goodsInstanceList.add(goodsinstacetemp);
			}
			while (lenTemp > 1) {// 循锟斤拷锟斤拷锟斤拷锟斤拷拼装锟斤拷锟斤拷list
				List<GoodsInstance> tempList = new ArrayList<GoodsInstance>();
				lenTemp--;
				for (int li = 0; li < goodsInstanceList.size(); li++) {
					String[] ss = attr[len - lenTemp].split("\\*");
					for (int ee = 2; ee < ss.length; ee++) {
						String attrValue = goodsInstanceList.get(li).getAttrs() + ss[0] + equ + ss[ee] + semicolon;// 拼锟斤拷锟斤拷值
						String attrDescs = goodsInstanceList.get(li).getAttrDesc() + ss[1] + equ + ss[ee] + semicolon;// 拼锟斤拷锟斤拷锟斤拷锟斤拷
						// String instanceNames=goodsInstanceList.get(li).getInstanceName()+ss[ee];//实锟斤拷锟斤拷
						GoodsInstance goodsinstacetemp = goodsinstacne.clone();
						// 锟斤拷code锟斤拷锟斤拷锟斤拷锟斤拷}位锟斤拷
						if (temp < 10) {// 锟斤拷小锟斤拷10时锟斤拷前锟芥补0
							codeTemp = goods.getGoodsSn() + "0" + temp;
						} else {
							codeTemp = goods.getGoodsSn() + temp;
						}
						goodsinstacetemp.setCode(codeTemp);
						if (lenTemp > 1) {//
							goodsinstacetemp.setAttrs(attrValue);
							goodsinstacetemp.setAttrDesc(attrDescs);
							goodsinstacetemp.setInstanceName(goods.getTitle());
						} else {
							goodsinstacetemp.setAttrs(attrvale + attrValue);
							goodsinstacetemp.setAttrDesc(desc + attrDescs);
							// 循锟斤拷锟斤拷锟斤拷锟津将诧拷品锟斤拷锟狡醋帮拷锟絒]锟斤拷锟斤拷式
							goodsinstacetemp.setInstanceName(goods.getTitle());
						}
						tempList.add(goodsinstacetemp);
						temp++;
					}
				}
				goodsInstanceList.clear();
				goodsInstanceList = tempList;
				temp = 1;
			}
		}
		return goodsInstanceList;
	}

	public boolean publishGoods(Goods goods, File[] images, String[] imagesFileName, Map<Integer, List<String>> levels) {
		log.info("GoodsManagerImpl.getGoodss method");
		try {
			goods.setTitle(StringUtil.replace(StringUtil.replace(goods.getTitle(), "(", "["), ")", "]"));
			long id = this.goodsDao.addGoods(goods);

			if (goods.getIsWholesale().equals("y")) {
				List<String> levelOne = levels.get(1);
				if (levelOne != null) {
					GoodsWholsale goodsWholsale = new GoodsWholsale();
					goodsWholsale.setGoodsId(goods.getId());
					goodsWholsale.setWholesaleLevel(1);
					goodsWholsale.setStartNum(Long.parseLong(levelOne.get(0)));
					goodsWholsale.setEndNum(Long.parseLong(levelOne.get(1)));
					goodsWholsale.setWholesalePrice(Double.parseDouble(levelOne.get(2)));
					goodsWholsaleManager.addGoodsWholsale(goodsWholsale);
				}
				List<String> levelTwo = levels.get(2);
				if (levelTwo != null) {
					GoodsWholsale goodsWholsale = new GoodsWholsale();
					goodsWholsale.setGoodsId(goods.getId());
					goodsWholsale.setWholesaleLevel(2);
					goodsWholsale.setStartNum(Long.parseLong(levelTwo.get(0)));
					goodsWholsale.setEndNum(Long.parseLong(levelTwo.get(1)));
					goodsWholsale.setWholesalePrice(Double.parseDouble(levelTwo.get(2)));
					goodsWholsaleManager.addGoodsWholsale(goodsWholsale);
				}
				List<String> levelThree = levels.get(3);
				if (levelThree != null) {
					GoodsWholsale goodsWholsale = new GoodsWholsale();
					goodsWholsale.setGoodsId(goods.getId());
					goodsWholsale.setWholesaleLevel(3);
					goodsWholsale.setStartNum(Long.parseLong(levelThree.get(0)));
					goodsWholsale.setEndNum(Long.parseLong(levelThree.get(1)));
					goodsWholsale.setWholesalePrice(Double.parseDouble(levelThree.get(2)));
					goodsWholsaleManager.addGoodsWholsale(goodsWholsale);
				}
			}
			
			if (images != null) {
				String goodsPicPath = "goods" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
				String realPathPre = uploadUtil.getRealUpload() + Constants.FILE_SEP + uploadUtil.getUploadRootPath();
				for (int i = 0; i < images.length; i++) {
					String fileName = uploadUtil.upload(images[i], imagesFileName[i], goodsPicPath);

					GoodsGallery goodsGallery = reduceImg(realPathPre, goodsPicPath, fileName);
					goodsGallery.setGoodsId(goods.getId());
					int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGallery.getGoodsId());
					goodsGallery.setSort(maxSort + 1);// 锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷
					goodsGalleryDao.addGoodsGallery(goodsGallery);
					if (i == 0) {
						goods.setOriginalImg(goodsPicPath + Constants.FILE_SEP + fileName);
						goods.setImgLarge(goodsGallery.getImgLarge());
						goods.setImgMiddle(goodsGallery.getImgMiddle());
						goods.setImgSmall(goodsGallery.getImgSmall());
						goodsDao.editGoods(goods);
					}
				}
			}
			if (goods.getChoose() != null && goods.getChoose().length() != 0) {
				String[] choose = goods.getChoose().split("\\+");
				GoodsAttr goodsAttr = null;
				for (int i = 0; i < choose.length; i++) {
					if (choose[i] != null) {
						String[] goodsAttrString = choose[i].split("\\*");
						for (int j = 2; j < goodsAttrString.length; j++) {
							goodsAttr = null;
							// log.info("goodsAttrString.length==>"+
							// goodsAttrString.length);
							goodsAttr = new GoodsAttr();
							goodsAttr.setGoodsId(id);
							goodsAttr.setAttrCode(goodsAttrString[0]);
							goodsAttr.setAttrName(goodsAttrString[1]);
							goodsAttr.setAttrValue(goodsAttrString[j]);
							goodsAttr.setPrice(0.0);
							goodsAttr.setIsUse(0);
							goodsAttrDao.addGoodsAttr(goodsAttr);
						}

					}
				}
				List<GoodsInstance> goodsInstanceList = addGoodsInstance(choose, goods, id);
				if (null != goodsInstanceList && goodsInstanceList.size() > 0)
					goodsInstanceDao.insertBatch(goodsInstanceList);
			}
			if (createBlankGoodsInstance(goods)) {
				GoodsInstance gi = new GoodsInstance(goods);
				this.goodsInstanceDao.createGoodsInstance(gi);
			}

			// addAgentGoodsRelation(goods, id);
			// //added by  2009/11/12 end
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public boolean newPublishGoods(Goods goods, List<MultipartFile> files) {
		try {
			goods.setTitle(StringUtil.replace(StringUtil.replace(goods.getTitle(), "(", "["), ")", "]"));
			long id = this.goodsDao.addGoods(goods);

			if (files != null && files.size() > 0) {
				String goodsPicPath = "goods" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
				String realPathPre = upload;
				int i = 0;
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file, goodsPicPath);
						GoodsGallery goodsGallery = reduceImg(realPathPre, goodsPicPath, fileName);
						goodsGallery.setGoodsId(goods.getId());
						int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGallery.getGoodsId());
						goodsGallery.setSort(maxSort + 1);
						goodsGalleryDao.addGoodsGallery(goodsGallery);
						if (i++ == 0) {
							goods.setOriginalImg(goodsPicPath + Constants.FILE_SEP + fileName);
							goods.setImgLarge(goodsGallery.getImgLarge());
							goods.setImgMiddle(goodsGallery.getImgMiddle());
							goods.setImgSmall(goodsGallery.getImgSmall());
							goodsDao.editGoods(goods);
						}
					}
				}
			}

			if (goods.getChoose() != null && goods.getChoose().length() != 0) {
				String[] choose = goods.getChoose().split("\\+");
				GoodsAttr goodsAttr = null;
				for (int i = 0; i < choose.length; i++) {
					if (choose[i] != null) {
						String[] goodsAttrString = choose[i].split("\\*");
						for (int j = 2; j < goodsAttrString.length; j++) {
							goodsAttr = null;
							// log.info("goodsAttrString.length==>"+
							// goodsAttrString.length);
							goodsAttr = new GoodsAttr();
							goodsAttr.setGoodsId(id);
							goodsAttr.setAttrCode(goodsAttrString[0]);
							goodsAttr.setAttrName(goodsAttrString[1]);
							goodsAttr.setAttrValue(goodsAttrString[j]);
							goodsAttr.setPrice(0.0);
							goodsAttr.setIsUse(0);
							goodsAttrDao.addGoodsAttr(goodsAttr);
						}

					}
				}
				List<GoodsInstance> goodsInstanceList = addGoodsInstance(choose, goods, id);
				if (null != goodsInstanceList && goodsInstanceList.size() > 0)
					goodsInstanceDao.insertBatch(goodsInstanceList);
			}
			if (createBlankGoodsInstance(goods)) {
				GoodsInstance gi = new GoodsInstance(goods);
				this.goodsInstanceDao.createGoodsInstance(gi);
			}

			// addAgentGoodsRelation(goods, id);
			// //added by chenyan 2009/11/12 end
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	@Override
	@Transactional
	public boolean newClientToBrowser(Goods goods, File file,String fileFileName) {
		try {
			goods.setTitle(StringUtil.replace(StringUtil.replace(goods.getTitle(), "(", "["), ")", "]"));
			long id = this.goodsDao.addGoods(goods);
			long instanceId = 0;

			if (file != null ) {
				String goodsPicPath = "goods" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
				String realPathPre = upload;
				int i = 0;
				
				String fileName = uploadUtil.newFileUpload(file,fileFileName,goodsPicPath);
				GoodsGallery goodsGallery = reduceImg(realPathPre, goodsPicPath, fileName);
				goodsGallery.setGoodsId(goods.getId());
				int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGallery.getGoodsId());
				goodsGallery.setSort(maxSort + 1);
				goodsGalleryDao.addGoodsGallery(goodsGallery);
				if (i++ == 0) {
					goods.setOriginalImg(goodsPicPath + Constants.FILE_SEP + fileName);
					goods.setImgLarge(goodsGallery.getImgLarge());
					goods.setImgMiddle(goodsGallery.getImgMiddle());
					goods.setImgSmall(goodsGallery.getImgSmall());
					goodsDao.editGoods(goods);
				}
			}

			if (goods.getChoose() != null && goods.getChoose().length() != 0) {
				String[] choose = goods.getChoose().split("\\+");
				GoodsAttr goodsAttr = null;
				for (int i = 0; i < choose.length; i++) {
					if (choose[i] != null) {
						String[] goodsAttrString = choose[i].split("\\*");
						for (int j = 2; j < goodsAttrString.length; j++) {
							goodsAttr = null;
							// log.info("goodsAttrString.length==>"+
							// goodsAttrString.length);
							goodsAttr = new GoodsAttr();
							goodsAttr.setGoodsId(id);
							goodsAttr.setAttrCode(goodsAttrString[0]);
							goodsAttr.setAttrName(goodsAttrString[1]);
							goodsAttr.setAttrValue(goodsAttrString[j]);
							goodsAttr.setPrice(0.0);
							goodsAttr.setIsUse(0);
							goodsAttrDao.addGoodsAttr(goodsAttr);
						}

					}
				}
				List<GoodsInstance> goodsInstanceList = addGoodsInstance(choose, goods, id);
				
				if (null != goodsInstanceList && goodsInstanceList.size() > 0){
					//设置库存
					for(GoodsInstance goodsInstance:goodsInstanceList){
						this.goodsInstanceDao.createGoodsInstance(goodsInstance);
						addAvailableStockTo(goodsInstance,goods);
						instanceId = goodsInstance.getId().longValue();
					}
//					goodsInstanceDao.insertBatch(goodsInstanceList);
				}
			}
			if (createBlankGoodsInstance(goods)) {
				GoodsInstance gi = new GoodsInstance(goods);
				this.goodsInstanceDao.createGoodsInstance(gi);
				addAvailableStockTo(gi,goods);
				instanceId = gi.getId().longValue();
			}

			Map<String,Object> pramas = new HashMap<String,Object>();
			pramas.put("idProduct", goods.getProductId());
			pramas.put("goodsId", id);
			pramas.put("instanceId", instanceId);
			
			productService.updateProductToBrowser(pramas);
			
			// addAgentGoodsRelation(goods, id);
			// //added by chenyan 2009/11/12 end
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	@Override
	@Transactional
	public boolean editGoodsAddGoodsInstance(Goods goods) {
		try {
			long instanceId = 0;
			if (goods.getChoose() != null && goods.getChoose().length() != 0) {
				String[] choose = goods.getChoose().split("\\+");
				GoodsAttr goodsAttr = null;
				for (int i = 0; i < choose.length; i++) {
					if (choose[i] != null) {
						String[] goodsAttrString = choose[i].split("\\*");
						for (int j = 2; j < goodsAttrString.length; j++) {
							goodsAttr = null;
							// log.info("goodsAttrString.length==>"+
							// goodsAttrString.length);
							goodsAttr = new GoodsAttr();
							goodsAttr.setGoodsId(goods.getId());
							goodsAttr.setAttrCode(goodsAttrString[0]);
							goodsAttr.setAttrName(goodsAttrString[1]);
							goodsAttr.setAttrValue(goodsAttrString[j]);
							goodsAttr.setPrice(0.0);
							goodsAttr.setIsUse(0);
							goodsAttrDao.addGoodsAttr(goodsAttr);
						}

					}
				}
				List<GoodsInstance> goodsInstanceList = addGoodsInstance(choose, goods, goods.getId());
				
				if (null != goodsInstanceList && goodsInstanceList.size() > 0){
					for(GoodsInstance goodsInstance:goodsInstanceList){
						this.goodsInstanceDao.createGoodsInstance(goodsInstance);
						addAvailableStockTo(goodsInstance,goods);
						instanceId = goodsInstance.getId().longValue();
					}
				}
			}
			if (createBlankGoodsInstance(goods)) {
				GoodsInstance gi = new GoodsInstance(goods);
				this.goodsInstanceDao.createGoodsInstance(gi);
				addAvailableStockTo(gi,goods);
				instanceId = gi.getId().longValue();
			}
			
			Map<String,Object> pramas = new HashMap<String,Object>();
			pramas.put("idProduct", goods.getProductId());
			pramas.put("goodsId", goods.getId());
			pramas.put("instanceId", instanceId);
			
			productService.updateProductToBrowser(pramas);
			
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	public void addAvailableStockTo(GoodsInstance goodsInstance,Goods goods){
		AvailableStock availableStock = new AvailableStock();
		availableStock.setGoodsId(goodsInstance.getGoodsId());
		availableStock.setGoodsInstanceId(goodsInstance.getId());
		availableStock.setGoodsNumber(goodsInstance.getExistNum().longValue());
		availableStock.setSiteId(goods.getSiteId());
		availableStockDao.addClientAvailableStock(availableStock);
	}

	private boolean createBlankGoodsInstance(Goods goods) {
		String catCode = goods.getCatCode();
		if (catCode == null) {
			return true;
		}
		List<AttributeDTO> atts = this.catAttrRelDao.getAttributeDTOByCatCode(catCode);
		for (AttributeDTO a : atts) {
			if (a.isInstance()) {
				return false;
			}
		}
		return true;
	}

	private GoodsGallery reduceImg(String realPathRre, String orgImgPath, String orgImgFileName) {

		GoodsGallery goodsGallery = new GoodsGallery();
		try {
			String org_img = orgImgPath + Constants.FILE_SEP + orgImgFileName;
			
			String large_img = orgImgPath + Constants.FILE_SEP + orgImgFileName.replace(".", "_l.");
			
			String middle_img = large_img.replace("_l", "_m");
			
			String samll_img = large_img.replace("_l", "_s");
			
			ImgUtils.reduceImg(realPathRre + Constants.FILE_SEP + org_img,
					realPathRre + Constants.FILE_SEP + large_img, 600, 610);
			ImgUtils.reduceImg(realPathRre + Constants.FILE_SEP + org_img, realPathRre + Constants.FILE_SEP
					+ middle_img, 150, 155);
			ImgUtils.reduceImg(realPathRre + Constants.FILE_SEP + org_img,
					realPathRre + Constants.FILE_SEP + samll_img, 40, 50);
			
			goodsGallery.setImgLarge(large_img);
			goodsGallery.setImgMiddle(middle_img);
			goodsGallery.setImgSmall(samll_img);
			goodsGallery.setImgOriginal(org_img);

		} catch (Exception e) {
			log.error(e);
			throw new ManagerException(e);
		}
		return goodsGallery;
	}

	public void setUploadUtil(UploadUtil uploadUtil) {
		this.uploadUtil = uploadUtil;
	}

	public void setGoodsGalleryDao(GoodsGalleryDao goodsGalleryDao) {
		this.goodsGalleryDao = goodsGalleryDao;
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public boolean listingGoods(Long id) {
		try {
			Goods goods = goodsDao.getGoods(id);
			if (goods != null) {
				if ((!goods.getIsWholesale().equals("y")) && (goods.getGoodsNumber() <= 0)) {
					return false;
				}
			} else {
				return false;
			}
			Date gmtListing = new Date();
			Date gmtDelisting = DateUtil.getDate(gmtListing, 36500);
			goods.setGoodsStatus(EnumGoodsStatus.ON_SALE.getKey());
			goods.setGmtListing(gmtListing);
			goods.setGmtDelisting(gmtDelisting);
			goodsDao.editGoods(goods);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	public void autoListing() {
		try {
			goodsDao.autoListing();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public boolean deListingGoods(Long id) {
		try {
			Goods goods = goodsDao.getGoods(id);
			if (goods == null || goods.getGoodsNumber() < 0)
				return false;
			goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
			goodsDao.editGoods(goods);
			return true;
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}

	// public PageUtil<Goods> searchGoods(Goods goods, int currentPage, int pageSize) throws Exception {
	// int count = this.goodsDao.getGoodsCountDynamic(goods);
	// return this.goodsDao.getGoodsDynamic(goods, currentPage, pageSize, count);
	// }

	public Map<String, List<Category>> getCategotyMenu(String catCode) throws Exception {

		/*
		 * String parentCode = null; int depth = 0; if (StringUtil.isEmpty(catCode) || catCode.equalsIgnoreCase("-1")) {
		 * parentCode = "-1"; depth = 1; } else { parentCode =
		 * this.categoryDao.getCategoryByCode(catCode).getParentCode(); depth =
		 * this.categoryDao.getCategoryByCode(catCode).getDepth(); } List<Category> parent =
		 * this.categoryDao.getCateGorysOfTheParent(parentCode); List<Category> child =
		 * this.categoryDao.getCategoryInfoByDepth(depth + 1); Map<String, List<Category>> map = new HashMap<String,
		 * List<Category>>(); map.put("parent", parent); map.put("child", child); return map;
		 */
		String parentCode = null;
		int depth = 0;
		if (StringUtil.isEmpty(catCode) || catCode.equalsIgnoreCase("-1")) {
			parentCode = "-1";
			depth = 1;
		} else {
			// parentCode =
			// this.categoryDao.getCategoryByCode(catCode).getParentCode();
			depth = this.categoryDao.getCategoryByCode(catCode).getDepth();
			depth += 1;
		}
		List<Category> parent = this.categoryDao.getCateGorysOfTheParent(catCode);
		List<Category> child = this.categoryDao.getCategoryInfoByDepth(depth + 1);
		BeanComparator comparator = new BeanComparator("isLeaf", new ComparableComparator());
		Collections.sort(parent, comparator);
		Collections.sort(child, comparator);

		Map<String, List<Category>> map = new HashMap<String, List<Category>>();
		map.put("parent", parent);
		map.put("child", child);
		return map;
	}

	public boolean removeGoodsGallery(String goodsGalleryId) {
		try {
			GoodsGallery goodsGallery = goodsGalleryDao.getGoodsGallery(new Long(goodsGalleryId));
			if (goodsGallery != null) {
				Goods goods = goodsDao.getGoods(goodsGallery.getGoodsId());
				boolean flag = removeAllFile(goodsGallery);
				this.goodsGalleryDao.updateGoodsGallerysSortByGoodsId(goodsGallery);// 锟斤拷锟斤拷图片锟斤拷锟斤拷
				goodsGalleryDao.removeGoodsGallery(goodsGallery.getId());
				if (goods.getOriginalImg().equals(goodsGallery.getImgOriginal())) {
					Map parameterMap = new HashMap();
					parameterMap.put("goodsId", goodsGallery.getGoodsId());
					List<GoodsGallery> list = goodsGalleryDao.getGoodsGallerysByParameterMap(parameterMap);
					if (list != null && list.size() > 0) {
						goods.setImgLarge(list.get(0).getImgLarge());
						goods.setImgMiddle(list.get(0).getImgMiddle());
						goods.setImgSmall(list.get(0).getImgSmall());
						goods.setOriginalImg(list.get(0).getImgOriginal());
					} else {
						goods.setImgLarge("");
						goods.setImgMiddle("");
						goods.setImgSmall("");
						goods.setOriginalImg("");
					}
					goodsDao.editGoods(goods);
				}
				return true;

			}
			return false;
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}

	private boolean removeAllFile(GoodsGallery goodsGallery) {

		boolean flag1 = uploadUtil.deleteFile(getFilePath(goodsGallery.getImgLarge()),
				getFileName(goodsGallery.getImgLarge()));
		boolean flag2 = uploadUtil.deleteFile(getFilePath(goodsGallery.getImgMiddle()),
				getFileName(goodsGallery.getImgMiddle()));
		boolean flag3 = uploadUtil.deleteFile(getFilePath(goodsGallery.getImgSmall()),
				getFileName(goodsGallery.getImgSmall()));
		boolean flag4 = uploadUtil.deleteFile(getFilePath(goodsGallery.getImgOriginal()),
				getFileName(goodsGallery.getImgOriginal()));

		return flag1 && flag2 && flag3 & flag4;
	}

	private String getFilePath(String filePath) {

		if (filePath.lastIndexOf(File.separator) > 0) {
			return filePath.substring(0, filePath.lastIndexOf(File.separator));
		} else {
			return "";
		}
	}

	private String getFileName(String filePath) {
		if (filePath.lastIndexOf(File.separator) > 0) {
			return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
		} else {
			return filePath;
		}

	}

	@Transactional
	public Map<String, String> updateGoods(Goods goods, List<MultipartFile> files) {
		log.info("GoodsManagerImpl.updateGoods method");
		Map<String, String> mapStr = new HashMap<String, String>();
		String isSendWebSite = "";

		try {
			Goods goodsDB = this.goodsDao.getGoods(goods.getId());

			// 锟斤拷锟斤拷锟睫革拷锟斤拷品title,锟斤拷同锟斤拷锟睫革拷锟斤拷锟斤拷锟斤拷锟叫诧拷品title zhangwy
			if (!goodsDB.getTitle().equals(goods.getTitle().trim())) {
				List<GoodsInstance> goodsInstanceList = goodsInstanceDao.findGoodsInstancesByGoodsId(goodsDB.getId());
				if (goodsInstanceList != null) {
					for (GoodsInstance tmp : goodsInstanceList) {
						tmp.setInstanceName(goods.getTitle().trim());
						goodsInstanceDao.updateGoodsInstance(tmp);
					}
				}
			}

			if (StringUtil.isNotBlank(goodsDB.getIsAgent()) && goodsDB.getIsAgent().equals("y")) {
				// 锟洁辑锟斤拷锟斤拷品前锟斤拷锟角达拷锟斤拷锟斤拷品
				if (StringUtil.isNotBlank(goods.getIsAgent()) && goods.getIsAgent().equals("n")) {
					isSendWebSite = "success";
				}

			}

			if (goods.getCheckflag().equals("0") && goods.getIsPaipai().equals("y")) {
				goodsDB.setPaipaiClassId(goodsDB.getPaipaiClassId());
				goodsDB.setPaipaiAttr(goodsDB.getPaipaiAttr());
			} else if (goods.getCheckflag().equals("1")) {
				goodsDB.setPaipaiClassId(goods.getPaipaiClassId());
				goodsDB.setPaipaiAttr(goods.getPaipaiAttr());
			} else if (goods.getIsPaipai().equals("n")) {
				goodsDB.setPaipaiClassId(null);
				goodsDB.setPaipaiAttr("");
			}

			// start - 锟皆憋拷锟斤拷j
			if (goods.getCheckflagTaobao().equals("0") && goods.getIsTaobao().equals("y")) {
				goodsDB.setTaobaoClassId(goodsDB.getTaobaoClassId());
				goodsDB.setTaobaoAttr(goodsDB.getTaobaoAttr());
				goodsDB.setTaobaoSkuProp(goodsDB.getTaobaoSkuProp());
				goodsDB.setTaobaoSkuPropName(goodsDB.getTaobaoSkuPropName());
				goodsDB.setTaobaoInputStr(goodsDB.getTaobaoInputStr());
				goodsDB.setTaobaoInputPids(goodsDB.getTaobaoInputPids());
			} else if (goods.getCheckflagTaobao().equals("1")) {
				goodsDB.setTaobaoClassId(goods.getTaobaoClassId());
				goodsDB.setTaobaoAttr(goods.getTaobaoAttr());
				goodsDB.setTaobaoSkuProp(goods.getTaobaoSkuProp());
				goodsDB.setTaobaoSkuPropName(goods.getTaobaoSkuPropName());
				goodsDB.setTaobaoInputStr(goods.getTaobaoInputStr());
				goodsDB.setTaobaoInputPids(goods.getTaobaoInputPids());
			} else if (goods.getIsTaobao().equals("n")) {
				goodsDB.setTaobaoClassId(null);
				goodsDB.setTaobaoAttr("");
				goodsDB.setTaobaoSkuProp("");
				goodsDB.setTaobaoSkuPropName("");
				goodsDB.setTaobaoInputStr("");
				goodsDB.setTaobaoInputPids("");
			}
			// end - 锟皆憋拷锟斤拷j

			goodsDB.setAttrDesc(goods.getAttrDesc());
			goodsDB.setAttrValue(goods.getAttrValue());
			goodsDB.setBrandId(goods.getBrandId());
			goodsDB.setGoodsDesc(goods.getGoodsDesc());
			goodsDB.setGoodsNumber(goods.getGoodsNumber());
			goodsDB.setGoodsPrice(goods.getGoodsPrice());
			goodsDB.setGoodsSn(goods.getGoodsSn());
			goodsDB.setGoodsWeight(goods.getGoodsWeight());
			goodsDB.setMarketPrice(goods.getMarketPrice());
			goodsDB.setTitle(goods.getTitle());
			goodsDB.setSalesProPrice(goods.getSalesProPrice());

			goodsDB.setChoose(goods.getChoose());
			goodsDB.setGoodsUnit(goods.getGoodsUnit());
			goodsDB.setIsAgent(goods.getIsAgent());
			goodsDB.setAgentPrice(goods.getAgentPrice());
			goodsDB.setWholesalePrice(goods.getWholesalePrice());
			goodsDB.setIsWholesale(goods.getIsWholesale());
			goodsDB.setGoodsItem(goods.getGoodsItem());
			goodsDB.setIsPaipai(goods.getIsPaipai());
			goodsDB.setIsTaobao(goods.getIsTaobao());

			this.goodsDao.editGoods(goodsDB);

			if (files != null && files.size() > 0) {
				String goodsPicPath = "goods" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
				String realPathPre = upload;
				int i = 0;
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file, goodsPicPath);
						GoodsGallery goodsGallery = reduceImg(realPathPre, goodsPicPath, fileName);
						goodsGallery.setGoodsId(goodsDB.getId());
						int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGallery.getGoodsId());
						goodsGallery.setSort(maxSort + 1);// 锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷
						goodsGalleryDao.addGoodsGallery(goodsGallery);
						if (i++ == 0 && StringUtil.isEmpty(goodsDB.getOriginalImg())) {
							goodsDB.setOriginalImg(goodsPicPath + Constants.FILE_SEP + fileName);
							goodsDB.setImgLarge(goodsGallery.getImgLarge());
							goodsDB.setImgMiddle(goodsGallery.getImgMiddle());
							goodsDB.setImgSmall(goodsGallery.getImgSmall());
							goodsDao.editGoods(goodsDB);
						}
					}
				}
			}

			goodsAttrDao.removeGoodsAttrByGoods(goodsDB.getId());

			if (goodsDB.getChoose() != null && goodsDB.getChoose().length() != 0) {
				String[] choose = goodsDB.getChoose().split("\\+");
				GoodsAttr goodsAttr = null;
				for (int i = 0; i < choose.length; i++) {

					if (choose[i] != null) {
						String[] goodsAttrString = choose[i].split("\\*");
						for (int j = 2; j < goodsAttrString.length; j++) {
							goodsAttr = null;
							goodsAttr = new GoodsAttr();
							goodsAttr.setGoodsId(goodsDB.getId());
							goodsAttr.setAttrCode(goodsAttrString[0]);
							goodsAttr.setAttrName(goodsAttrString[1]);
							goodsAttr.setAttrValue(goodsAttrString[j]);
							goodsAttr.setPrice(0.0);
							goodsAttrDao.addGoodsAttr(goodsAttr);
						}

					}
				}
			}

			goodsInstanceDao.updateGoodsUnit(goodsDB.getId(), goodsDB.getGoodsUnit());
			// modify by lincf 锟斤拷锟斤拷锟教城价革拷锟斤拷锟叫筹拷锟斤拷同锟斤拷
			Map map = new HashMap();
			map.put("goodsId", goods.getId());
			map.put("sellPrice", goods.getGoodsPrice());
			map.put("marketPrice", goods.getMarketPrice());
			this.goodsInstanceDao.updateGoodsInstanceByMap(map);
			mapStr.put("isSendWebSite", isSendWebSite);
			mapStr.put("isSuccess", "success");
			return mapStr;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	public boolean updateGoodsStatus(String id, String status) {
		log.info("GoodsManagerImpl.updateGoodsStatus method");
		try {
			Goods goods = goodsDao.getGoods(new Long(id));
			goods.setGoodsStatus(status);
			goodsDao.editGoods(goods);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.shop.CabinetManager#getCabAndGoodsInfo()
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCabAndGoodsInfo() throws Exception {
		List<Cabinet> listOfCab = new ArrayList<Cabinet>();
		List<Goods> listGoods = new ArrayList<Goods>();
		Map map = new HashMap<String, Object>();
		// listOfCab = cabinetDao.getCabinetForSort();
		// listGoods = goodsDao.getGoodsAllForCabinet();
		// map.put("listOfCab", listOfCab);
		// map.put("listGoods", listGoods);
		return map;
	}

	public List<Goods> getNeedDelisting() throws Exception {
		log.info("GoodsManagerImpl.getNeedDelisting method");
		try {
			return goodsDao.getNeedDelisting();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 */
	public long countAllGoodsClickNum() {
		return goodsDao.countAllGoodsClickNum();
	}

	/**
	 * 
	 * @param goodsId
	 * @param comments
	 * @see com.hundsun.bible.facade.goods.GoodsManager#saveComments(java.lang.Long,
	 *      com.hundsun.bible.domain.model.Comments)
	 */
	public void saveComments(Long goodsId, Comments comments) {
		Goods goods = this.getGoods(goodsId);
		// commentsManager.addComments(comments);
		updateGoodsRank(goods);
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷
	 */
	public void updateGoodsRank(Goods goods) {
		int i = 0;
		double c = 0;
		// List<Comments> commentsList = commentsManager.getAllCommentsByGoodsId(goods.getId());
		// if (null != commentsList && commentsList.size() > 0) {
		// for (Comments comments : commentsList) {
		// c += comments.getCommentRank();
		// i++;
		// }
		// }
		double rank = 0;
		if (i != 0) {
			rank = DoubleUtil.div(c, i, 2);
		}
		goods.setGoodsRank(rank);
		this.editGoods(goods);
	}

	public boolean updateCutPrice(Long id) {
		try {
			Goods goods = goodsDao.getGoods(id);
			if (goods == null || goods.getGoodsNumber() <= 0)
				return false;
			goods.setIsCutprice(EnumGoodsStatus.CUT_PRICE.getValue());
			goods.setGmtCutprice(new Date());
			goodsDao.editGoods(goods);
			// if (goods.getIsAgent().equalsIgnoreCase("y")) {
			// this.dodisagent(id);
			// deleted by chenyan 2010/06/24 start 取锟斤拷锟斤拷群锟斤拷锟绞硷拷锟斤拷锟斤拷
			// //锟斤拷锟绞硷拷
			// boolean emailFlag = dodisAgentGoods(id, "");
			// if (!emailFlag) {
			// return false;
			// }
			// deleted by chenyan 2010/06/24 end

			// List<GoodsAgent> goodsAgentList=goodsAgentManager.getGoodsAgentList(map);

			// }
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	public boolean updateCanelCutPrice(Long id) {
		try {
			Goods goods = goodsDao.getGoods(id);
			if (goods == null || goods.getGoodsNumber() <= 0)
				return false;
			goods.setIsCutprice(EnumGoodsStatus.CANEL_CUT_PRICE.getValue());
			goods.setGmtCutprice(new Date());
			goodsDao.editGoods(goods);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	public GoodsAttrDao getGoodsAttrDao() {
		return goodsAttrDao;
	}

	public void setGoodsAttrDao(GoodsAttrDao goodsAttrDao) {
		this.goodsAttrDao = goodsAttrDao;
	}

	public Goods addGoodsAttr(Goods good) {
		try {
			List<GoodsAttr> goodsAttrList = goodsAttrDao.getGoodsAttrByGoodSId(good.getId());
			Map<String, List<String>> mapCode = new HashMap<String, List<String>>();
			Map<String, List<String>> mapValue = new HashMap<String, List<String>>();
			StringBuffer attrValue = new StringBuffer();
			StringBuffer attrDesc = new StringBuffer();
			if (null != good.getAttrValue()) {
				attrValue.append(good.getAttrValue());
				attrDesc.append(good.getAttrDesc());
			}
			String code = null;
			for (GoodsAttr goodsAttr : goodsAttrList) {
				if (code == null) {
					code = goodsAttr.getAttrCode();
					attrValue.append(goodsAttr.getAttrCode() + ":");
					attrDesc.append(goodsAttr.getAttrName() + ":");
				} else {
					if (!code.equalsIgnoreCase(goodsAttr.getAttrCode())) {
						code = goodsAttr.getAttrCode();
						attrValue.append(";");
						attrDesc.append(";");
						attrValue.append(goodsAttr.getAttrCode() + ":");
						attrDesc.append(goodsAttr.getAttrName() + ":");
					} else {
						attrValue.append("-");
						attrDesc.append("-");
					}
				}

				attrValue.append(convertStr(goodsAttr.getAttrValue()));
				attrDesc.append(convertStr(goodsAttr.getAttrValue()));
			}

			attrValue.append(";");
			attrDesc.append(";");
			good.setAttrValue(attrValue.toString());
			good.setAttrDesc(attrDesc.toString());
			return good;
		} catch (Exception e) {
			log.error(e);
			return good;
		}
	}

	private String convertStr(String paramValue) {
		if (paramValue == null)
			return null;
		String temp = paramValue.trim();
		temp = EmallStringUtil.escapeGoodsAttrFilter(temp);
		return temp;

	}

	public List<Unit> findAllUnits() {
		return this.goodsDao.findAllUnits();
	}

	public boolean checkGoodsCode(Goods goods) {
		String code = goods.getGoodsSn();
		if (StringUtils.isBlank(code)) {
			return true;
		}
		List<Goods> find = this.goodsDao.findGoodsByCode(code);
		Long id = goods.getId();
		if (id == null || id == 0) {
			return find == null || find.isEmpty();
		}
		for (Goods g : find) {
			if (!(g.getId() == id)) {
				return false;
			}
		}
		return true;
	}

	public AttributeManager getAttrManager() {
		return attrManager;
	}

	public void setAttrManager(AttributeManager attrManager) {
		this.attrManager = attrManager;
	}

	public int updateGoodsGoodsNumberById(Long goodsId, Long amount,boolean tag) {
		log.info("GoodsManagerImpl.updateGoodsGoodsNumberById method");
		try {
			return goodsDao.updateGoodsGoodsNumberById(goodsId, amount,tag);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	
	public int updateGoodsHKGoodsNumberById(Long goodsId, Long amount,boolean tag) {
		log.info("GoodsManagerImpl.updateGoodsHKGoodsNumberById method");
		try {
			return goodsDao.updateGoodsHKGoodsNumberById(goodsId, amount,tag);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	
	public int updateGoodsNumberZero() {
		log.info("GoodsManagerImpl.updateGoodsNumberZero method");
		try {
			return goodsDao.updateGoodsNumberZero();
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.bible.facade.goods.GoodsManager#updateGoodsType(java.lang.String, java.lang.String)
	 */
	public boolean updateGoodsType(Long id, String type) {
		try {
			Goods goods = goodsDao.getGoods(id);
			if (goods == null || goods.getGoodsNumber() <= 0)
				return false;
			goods.setIsCutprice(type);
			goods.setGmtCutprice(new Date());
			goodsDao.editGoods(goods);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	public List<Goods> getGoodsByGoodsIds(List ids) {
		try {
			return goodsDao.getGoodsByGoodsIds(ids);
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public int updateSaleNumberById(Long goodsId, Long amount) {
		log.info("GoodsManagerImpl.updateSaleNumberById method");
		try {
			return goodsDao.updateSaleNumberById(goodsId, amount);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	
	public int updateSaleNumberOneOrThree(Long goodsId,int one, int three) {
		log.info("GoodsManagerImpl.updateSaleNumberById method");
		try {
			return goodsDao.updateSaleNumberOneOrThree(goodsId,one, three);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	

	public boolean doagent(Map map) {
		long id = (Long) map.get("id");
		Goods goods = null;
		try {
			goods = goodsDao.getGoods(id);
		} catch (Exception e) {

			log.error(e.getMessage());
		}
		if (goods != null && "1".equals(goods.getIsCutprice())) {
			return false;
		} else {
			goodsDao.doagent(map);
			return true;
		}
	}

	public void dodisagent(long id) {
		goodsDao.dodisagent(id);

	}

	/**
	 * @deprecated
	 */
	// public boolean dodisAgentGoods(long id, String ctx) {
	// boolean flag = true;
	// Goods goods = new Goods();
	// goods = this.getGoods(id);
	// ArrayList list = (ArrayList) this.userDao.getAgentUsers(id);
	// User user;
	// //modified by chenyan 2009/12/10 锟睫革拷为锟届步锟斤拷锟斤拷锟绞硷拷 start
	// if (list != null) {
	// List<MailData> mailDataList = new ArrayList<MailData>();
	// for (int i = 0; i < list.size(); i++) {
	// Map map = new HashMap();
	// user = (User) list.get(i);
	// map.put("user", user);
	// map.put("goods", goods);
	// map.put("ctx", ctx);
	//
	// MailData mailData = new MailData();
	// mailData.setToAddress(user.getEmail());
	// mailData.setSubject(getText("ios.agent.cancel.mail.title"));
	// mailData.setMailId("email-template/good-agent-fail.vm");
	// mailData.setData(map);
	// mailDataList.add(mailData);
	// }
	// try {
	// AsynmailEngineImpl asynmailEngineImpl = new AsynmailEngineImpl();
	// asynmailEngineImpl.setThreadPool(threadPool);
	// asynmailEngineImpl.setMailDataList(mailDataList);
	// asynmailEngineImpl.setMailEngine(mailEngine);
	// asynmailEngineImpl.start();
	// } catch (Exception e) {
	// log.error("锟届步锟绞硷拷锟斤拷锟斤拷失锟斤拷:" + e);
	// flag = false;
	// }
	// }
	// //modified by chenyan 2009/12/10 end
	// return flag;
	// }
	// public GoodsAgentManager getGoodsAgentManager() {
	// return goodsAgentManager;
	// }
	//
	// public void setGoodsAgentManager(GoodsAgentManager goodsAgentManager) {
	// this.goodsAgentManager = goodsAgentManager;
	// }
	public boolean doAgentGoods(Map map) {
		long id = (Long) map.get("id");
		try {
			Goods goods = goodsDao.getGoods(id);
			if (goods == null || goods.getGoodsNumber() <= 0)
				return false;
			if (goods != null && "1".equals(goods.getIsCutprice())) {
				return false;
			}
			goods.setGmtModify(new Date());
			goodsDao.doagent(map);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	/**
	 */
	public boolean doCanelAgentGoods(Map maps) {
		// List goodsIds = (List) maps.get("goodsIds");
		// boolean flag = true;
		// User user;
		// //一锟斤拷锟矫伙拷锟斤拷锟斤拷应一锟斤拷锟斤拷品map
		// Map<Long, HashMap<Long, Long>> map = new HashMap<Long, HashMap<Long, Long>>();
		// HashMap<Long, Long> userMap = null;
		// if (goodsIds == null) {
		// return false;
		// }
		// for (int goodsId = 0; goodsId < goodsIds.size(); goodsId++) {
		// try {
		// Long id = Long.parseLong((String) goodsIds.get(goodsId));
		// Goods goods = goodsDao.getGoods(id);
		// ArrayList list = (ArrayList) this.userDao.getAgentUsers(id);
		// if (list != null) {
		// for (int i = 0; i < list.size(); i++) {
		// if (map != null) {
		// userMap = map.get(((User) list.get(i)).getId());
		// }
		// if (userMap == null) {
		// userMap = new HashMap<Long, Long>();
		// }
		// user = (User) list.get(i);
		// userMap.put(id, id);
		// map.put(user.getId(), userMap);
		// userMap = null;
		// }
		// }
		//
		// goods.setGmtModify(new Date());
		// goodsDao.dodisagent(id);
		// } catch (Exception e) {
		// log.error(e);
		// return false;
		// }
		// }
		// Set ks = map.keySet();//
		// Iterator it = ks.iterator();
		// //modified by chenyan 2009/12/10 锟睫革拷为锟届步锟斤拷锟斤拷锟绞硷拷 start
		// List<MailData> mailDataList = new ArrayList<MailData>();
		// while (it.hasNext()) {
		// Long id = (Long) it.next();
		// user = this.userDao.getUserById(id);
		// Map goodsMap = map.get(id);
		// Set gds = goodsMap.keySet();
		// Iterator its = gds.iterator();
		// List goods = new ArrayList();
		// //锟剿讹拷应锟斤拷锟斤拷品map
		// while (its.hasNext()) {
		// Goods good = new Goods();
		// try {
		// long goodsid = (Long) its.next();
		// long tempid = (Long) goodsMap.get(goodsid);
		// good = this.goodsDao.getGoods(goodsid);
		// } catch (Exception e) {
		// log.error(e.getMessage());
		// }
		// goods.add(good);
		// }
		// Map temp = new HashMap();
		// temp.put("ctx", maps.get("ctx"));
		// temp.put("user", user);
		// temp.put("goods", goods);
		//
		// MailData mailData = new MailData();
		// mailData.setToAddress(user.getEmail());
		// mailData.setSubject(getText("ios.agent.cancel.mail.title"));
		// mailData.setMailId("email-template/good-agent-fail.vm");
		// mailData.setData(temp);
		// mailDataList.add(mailData);
		// }
		//
		// try {
		// AsynmailEngineImpl asynmailEngineImpl = new AsynmailEngineImpl();
		// asynmailEngineImpl.setThreadPool(threadPool);
		// asynmailEngineImpl.setMailDataList(mailDataList);
		// asynmailEngineImpl.setMailEngine(mailEngine);
		// asynmailEngineImpl.start();
		// } catch (Exception e) {
		// log.error("锟届步锟绞硷拷锟斤拷锟斤拷失锟斤拷:" + e);
		// }
		// //modified by chenyan 2009/12/10 end
		// return flag;

		List goodsIds = (List) maps.get("goodsIds");
		boolean flag = true;
		if (goodsIds == null || goodsIds.size() == 0) {
			return false;
		}
		List<Goods> goodsList = goodsDao.getGoodsByIds(goodsIds);
		if (goodsList == null || goodsList.size() == 0) {
			return false;
		}

		// 锟斤拷锟斤拷锟杰碉拷锟斤拷品锟斤拷锟斤拷头锟斤拷投锟斤拷锟斤拷映锟戒，然锟斤拷一锟斤拷锟斤拷(HASHTABEL锟斤拷锟斤拷一锟斤拷一)
		Map<Goods, List<User>> maptotal = new HashMap<Goods, List<User>>();
		for (Goods tmp : goodsList) {
			// 锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷取锟斤拷
			List<User> userlist = userDao.getAgentUsers(tmp.getId());
			maptotal.put(tmp, userlist);
			tmp.setGmtModify(new Date());
			goodsDao.dodisagent(tmp.getId());
		}

		// deleted by chenyan 2010/06/24 start 取锟斤拷锟斤拷群锟斤拷锟绞硷拷锟斤拷锟斤拷
		// //锟节讹拷锟斤拷:锟斤拷锟斤拷锟绞硷拷
		// if (maptotal != null) {
		// List<MailData> mailDataList = new ArrayList<MailData>();
		// Iterator it = maptotal.entrySet().iterator();
		// while(it.hasNext()){
		// Map.Entry<Goods, List<User>> tempMap = (Map.Entry<Goods, List<User>>)it.next();
		// List<User> tempuserList = tempMap.getValue();
		// for(User tempUser: tempuserList){
		// Map map = new HashMap();
		// map.put("user", tempUser);
		// map.put("goods", tempMap.getKey());
		// map.put("ctx", maps.get("ctx"));
		//
		// MailData mailData = new MailData();
		// mailData.setToAddress(tempUser.getEmail());
		// mailData.setSubject(getText("ios.agent.cancel.mail.title"));
		// mailData.setMailId("email-template/good-agent-fail.vm");
		// mailData.setData(map);
		// mailDataList.add(mailData);
		// }
		//
		// }
		// try {
		// AsynmailEngineImpl asynmailEngineImpl = new AsynmailEngineImpl();
		// asynmailEngineImpl.setThreadPool(threadPool);
		// asynmailEngineImpl.setMailDataList(mailDataList);
		// asynmailEngineImpl.setMailEngine(mailEngine);
		// asynmailEngineImpl.start();
		// } catch (Exception e) {
		// log.error("锟届步锟绞硷拷锟斤拷锟斤拷失锟斤拷:" + e);
		// flag = false;
		// }
		// }
		// deleted by chenyan 2010/06/24 end

		return flag;
	}

	public boolean isCutPriceGoods(long id) {
		boolean flag = false;
		Goods goods = null;
		try {
			goods = this.goodsDao.getGoods(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (goods != null && "1".equals(goods.getIsCutprice())) {
			flag = true;
		}
		return flag;
	}

	public List<Long> listAgentGoodsId() {
		log.info("GoodsManagerImpl.listAgentGoodsId method");
		try {
			return this.goodsDao.listAgentGoodsId();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public void updateAgentPrice(Map<String, Object> paramMap) {
		this.goodsDao.updateAgentPrice(paramMap);
	}

	/**
	 * 
	 * @param goods
	 *            Goods 锟斤拷品锟斤拷息
	 * @param id
	 *            Long 锟斤拷品ID
	 * @author chenyan 2009/11/16 取锟斤拷锟皆讹拷锟斤拷jchenyan 2010/04/15
	 */
	// public void addAgentGoodsRelation(Goods goods, Long id) {
	// if (goods != null && StringUtil.isNotBlank(goods.getIsAgent())
	// && goods.getIsAgent().equals("y")) {
	// goodsAgentManager.insertBatchAgentUserForGoodsId(id);
	// // //取锟斤拷之前锟斤拷锟斤拷通锟斤拷拇锟斤拷锟斤拷员
	// // List<Long> pastUserIdList = goodsAgentManager.getPastUserIdForAddGoodsRelation();
	// // if (pastUserIdList != null && pastUserIdList.size() > 0) {
	// // for (Long userId : pastUserIdList) {
	// // GoodsAgent goodsAgentTmp = new GoodsAgent();
	// // goodsAgentTmp.setGmtCreate(new Date());
	// // goodsAgentTmp.setGmtModify(new Date());
	// // goodsAgentTmp.setUserId(userId);
	// // goodsAgentTmp.setGoodsId(id);
	// // goodsAgentTmp.setStatus("y");
	// // //锟斤拷庸锟絡锟斤拷系
	// // if (goodsAgentManager.editStatusByUserIdGoodsId(goodsAgentTmp) <= 0) {
	// // //锟斤拷锟矫伙拷懈锟斤拷碌锟斤拷锟捷ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	// // goodsAgentManager.insertGoodsAgent(goodsAgentTmp);
	// // }
	// // }
	// // }
	// //modified by chenyan 20091204 end
	// }
	// }
	public AvailableStockDao getAvailableStockDao() {
		return availableStockDao;
	}

	public void setAvailableStockDao(AvailableStockDao availableStockDao) {
		this.availableStockDao = availableStockDao;
	}

	// public List<Goods> getAgentGoodsByData(Map<String, String> parMap, Page page) {
	// return this.goodsDao.getAgentGoodsByData(parMap,page);
	// }

	// public List<Goods> getPaipaiGoodsByData(Map<String, String> parMap,
	// Page page) {
	// return this.goodsDao.getPaipaiGoodsByData(parMap,page);
	// }

	/**
	 * 锟斤拷锟絞oodsSn锟斤拷询goods实锟斤拷
	 * 
	 * @param goodsSn
	 * @return
	 */
	public Goods getGoodsByCode(String goodsSn) {
		if (StringUtils.isBlank(goodsSn)) {
			return null;
		}
		List<Goods> find = this.goodsDao.findGoodsByCode(goodsSn);

		for (Goods g : find) {
			if (!StringUtils.isBlank(g.getIdString())) {
				return g;
			}
		}
		return null;
	}

	/* begin add by shenzh Oct 25, 2010 */
	// public List<Goods> getTaobaoGoodsByData(Map<String, String> parMap, Page page) {
	// return this.goodsDao.getTaobaoGoodsByData(parMap,page);
	// }

	public int getTaobaoGoodsByDataCount(Map<String, String> parMap) {
		return this.goodsDao.getTaobaoGoodsByDataCount(parMap);
	}

	/* end by shenzh Oct 25, 2010 */

	public Goods getExpressGoods(Long goodsId) {
		return this.goodsDao.getExpressGoods(goodsId);
	}

	public Goods getGoodsByGoodsSn(String goodsSn) {
		log.info("GoodsManagerImpl.editGoods method");
		try {
			return this.goodsDao.getGoodsByGoodsSn(goodsSn);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int updateGoodsBill(List<Long> gids, Long bid) {
		return goodsDao.updateGoodsBill(gids, bid);
	}
	
	@Override
	@Transactional
	public int checkGoodsNum(Map<String,Object> parMap) {
		
//		System.out.println("00000000000");
		
//		if(!listGoodsId.contains("38780")){
//		String goodsd ="38780";
//		listGoodsId.add(goodsd);                       
//		int one = this.GetOneMothSalNum(goodsd); //某个goodsId一个月内的销售总量
//		int three = this.GetThreeMothSalNum(goodsd); //某个goodsId一个月内的销售总量
//		this.updateSaleNumberOneOrThree(Long.valueOf("38780"),one,three);
//    }
		try{
			List<String> listGoodsId = new  ArrayList<String>();
			//清空款式表
			this.updateGoodsNumberZero();
			//清空款式尺寸表
			goodsInstanceManager.updateGoodsInstanceNumberZero();
			//清空站点表
			availableStockService.updateAvaiStoEsNumZero();
			//清空活动框款式库存
			goodsDao.updateHxMoveFrameGoodsNumZoro();
			//清空活动框款式尺寸库存
			goodsDao.updateHxMoveFrameInstanceNumZero();
			
			

			List<Product> productList = productService.getProducttoCheckNum(parMap);
			int number = 0;
			
			for(Product product:productList){
				
				//System.out.println(product.getIdProduct().toString());
				
				//存在，商品库存先增加1
				//2016年4月6日 18:26:44   || product.getIdStatus().longValue()==2 去掉运输在途
    			if(product.getIdStatus().longValue()==1){
    				this.updateGoodsGoodsNumberById(product.getGoodsId(), 1L,false);
    				goodsInstanceManager.updateGoodsInstanceExistNumById(product.getInstanceId(),1L);
    				Map<String,Object> aspra = new HashMap<String,Object>();
    				aspra.put("siteId", product.getIdLocation());
    				aspra.put("goodsInstanceId", product.getInstanceId());
    				aspra.put("goodsNumber", 1);
    				//songfy 20150903发生调货站点不存在情况下要新增库存站点
    				AvailableStock availableStock = availableStockService.getAvailableStockByPramas(aspra);
    				if(availableStock != null){
    					availableStockService.updateAvaiStoEsNumByPramas(aspra);
    				}else{
    					AvailableStock availableStockIn = new AvailableStock();
    					availableStockIn.setGoodsId(product.getGoodsId());
    					availableStockIn.setGoodsInstanceId(product.getInstanceId());
    					availableStockIn.setSiteId(product.getIdLocation());
    					availableStockIn.setGoodsNumber(1L);
    					availableStockIn.setGoodsSaleNumber(0L);
    					availableStockIn.setGoodsVirtualNumber(0L);
    					availableStockService.addhxAvaiStoByPramas(availableStockIn);
    				}
    				
    				
    				//更新hx_moveframe_goods的库存
    				aspra.put("productId", product.getIdProduct());
    				goodsDao.updateHxMoveFrameGoodsNum(aspra);
    				
    				//更新hx_moveframe_goods的库存
    				goodsDao.updateHxMoveFrameInstanceNum(aspra);
    				
    			}
    			//存在，如果是香港库存，香港库存增加1
    			//product.getIdStatus()==2  排除運輸在途
    			//product.getIdLocation() 添加104
    			if((product.getIdStatus().longValue()==1)&& 
    					(product.getIdLocation().longValue()==103 || product.getIdLocation().longValue()==104)){
    				this.updateGoodsHKGoodsNumberById(product.getGoodsId(), 1L,false);
    				goodsInstanceManager.updateGoodsInstanceHKExistNumById(product.getInstanceId(),1L);
    			}
    			//存在状态为已售，商品已售库存增加1
    			if(product.getIdStatus().longValue()==4){
    			
    				this.updateSaleNumberById(product.getGoodsId(), 1L);
    				goodsInstanceManager.updateGoodsInstanceSaleNumberById(product.getInstanceId(), 1L);
    				
    				Map<String,Object> aspra = new HashMap<String,Object>();
    				aspra.put("siteId", product.getIdLocation());
    				aspra.put("goodsInstanceId", product.getInstanceId());
    				aspra.put("goodsSaleNumber", 1);
    				availableStockService.updateAvaiStoEsNumByPramas(aspra);
    				
    				//更新hx_moveframe_goods的已售库存
    				aspra.put("productId", product.getIdProduct());
    				goodsDao.updateHxMoveFrameGoodsSellNum(aspra);
    				
    				//更新hx_moveframe_goods的已售库存
    				goodsDao.updateHxMoveFrameInstanceSellNum(aspra);
    			}
    			
    			//将某个goodsId的月内销售总量和三月内的销售总量，放进emall_goods表
//    			if(!listGoodsId.contains(product.getGoodsId()+"")){
//    				String goodsd =product.getGoodsId()+"";
//					listGoodsId.add(goodsd);                       
//    				int one = this.GetOneMothSalNum(goodsd); //某个goodsId一个月内的销售总量
//    				int three = this.GetThreeMothSalNum(goodsd); //某个goodsId一个月内的销售总量
//      				this.updateSaleNumberOneOrThree(product.getGoodsId(),one,three);
//				}
//    			number = number+1;
    			
    			
			}
			//清空字段sal_number和hk_th_number的值
	         updateSalNumberZero();
	         
			//查询出三个月内的销售的所有产品
			List<Product> goodsIdProductList = productService.selectBySelDate();
//			System.out.println(goodsIdProductList.size()+"=======goodsIdProductList");
			for(Product goodsIdProduct:goodsIdProductList){
				if(!listGoodsId.contains(goodsIdProduct.getGoodsId()+"")){
					String goodsd =goodsIdProduct.getGoodsId()+"";
					listGoodsId.add(goodsd);                        
					int one = GetOneMothSalNum(goodsd); //某个goodsId一个月内的销售总量
					int three = GetThreeMothSalNum(goodsd); //某个goodsId三个月内的销售总量
					updateSaleNumberOneOrThree(goodsIdProduct.getGoodsId(),one,three);
				}
			
		   }
//			System.out.println(listGoodsId.size()+"=+++++++++++++++");
			return number;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	public int updateSalNumberZero() {
		log.info("GoodsManagerImpl.updateSalNumberZero method");
		try {
			return goodsDao.updateSalNumberZero();
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	
	
	@Override
	@Transactional
	public boolean updateGoodsPicture(Goods goods, File file,String fileFileName,int num) {
		try {
			if (file != null ) {
				String piclink = goods.getOriginalImg();
				int number = piclink.lastIndexOf("/");
				String goodsPicPath = piclink.substring(0, number);
				String realPathPre = upload;
				
				String fileName = uploadUtil.newFileUpload(file,fileFileName,goodsPicPath);
				GoodsGallery goodsGallery= reduceImg(realPathPre, goodsPicPath, fileName);
				
				goodsGallery.setGoodsId(goods.getId());
				int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGallery.getGoodsId());
				goodsGallery.setSort(maxSort + 1);
				if(num>1 && maxSort<6){
					goodsGalleryDao.addGoodsGallery(goodsGallery);
				}
				if (maxSort == 1) {
//					goods.setOriginalImg(goodsPicPath + Constants.FILE_SEP + fileName);
//					goods.setImgLarge(goodsGallery.getImgLarge());
//					goods.setImgMiddle(goodsGallery.getImgMiddle());
//					goods.setImgSmall(goodsGallery.getImgSmall());
					goods.setIsGmtCutprice("yes");
					goodsDao.editGoods(goods);
				}
			}
			
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public int updatesalesproprice(Goods goods) {
		
		return goodsDao.updatesalesproprice(goods);
	}
	
	@Override
	public QueryPage getGoodsActivityByCnditionWithPage(Activity activity,int currPage, int pageSize){
		QueryPage queryPage = new QueryPage(activity);
		Map pramas = queryPage.getParameters();

		int count = goodsDao.getGoodsActivityByConditionWithPageCounts(pramas);

		if (count > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);
			
			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<Goods> goodsList = goodsDao.getGoodsActivityByConditionWithPages(pramas);
			if (goodsList != null && goodsList.size() > 0) {
				queryPage.setItems(goodsList);
			}
		}
		
		return queryPage;
		
	}
//	int in = this.GetOneMothSalNum("38760");
	public int GetOneMothSalNum(String goodsId){
        //24天*24小时*60分*60秒*1000毫秒(最多减24天)
		Date date =new Date(new Date().getTime() - 24*24*60*60*1000-6*24*60*60*1000); //24天+6天(某个GoodsId一个月内的数量销售量)
//		Date dateT =new Date(new Date().getTime() - 24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-19*24*60*60*1000); //(某个GoodsId三个月内的数量销售量)
		//System.out.println(sdf.format(date));
		Goods goods = new Goods();
		goods.setGmtModify(date);
		Long id = Long.parseLong(goodsId);
		
		goods.setId(id);
		int num =0;
		try {
		num = goodsDao.GetSalNumInOneMoth(goods);
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		return num;
}
	
	public int GetThreeMothSalNum(String goodsId){
        //24天*24小时*60分*60秒*1000毫秒(最多减24天)
//		Date date =new Date(new Date().getTime() - 24*24*60*60*1000-6*24*60*60*1000); //24天+6天(某个GoodsId一个月内的数量销售量)
		Date date =new Date(new Date().getTime() - 24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-20*24*60*60*1000); //(某个GoodsId三个月内的数量销售量)
//		Date date =new Date(new Date().getTime() - 24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-24*24*60*60*1000-13*24*60*60*1000); //(某个GoodsId6个月内的数量销售量)
		//System.out.println(sdf.format(date));
		Goods goods = new Goods();
		goods.setGmtModify(date);
		Long id = Long.parseLong(goodsId);
		
		goods.setId(id);
		int num =0;
		try {
		num = goodsDao.GetSalNumInOneMoth(goods);
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		return num;
}


	@Override
	public int updateCatcod(Goods goods) {
		String catCode=null;
		Long goodsId = goods.getId();
		String oneList = goods.getOneList();
		String twoList = goods.getTwoList();
		String threeList = goods.getThreeList();
		if(threeList!=null && !threeList.equals("")){
			catCode=threeList;
		}else if(twoList!=null && !twoList.equals("")){
			catCode=twoList;
		}else {
			catCode=oneList;
		}
		if(goodsId!=null && catCode!=null && catCode!=""){
			int updateCatcodDao = goodsDao.updateCatcodDao(goodsId,catCode);
			return updateCatcodDao;
		}
		return 0;
	}


	/* 
	 * 新导出
	 */
	@Override
	public List<Goods> getGoodsListByConditionWithPageByListExport(Goods goods,List<Long> list, List<String> codeList) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
		pramas.put("hasListGoodsId", "yes");
        pramas.put("list", list);
        if(StringUtils.isNotBlank(goods.getThreeList()) || StringUtils.isNotBlank(goods.getTwoList()) || StringUtils.isNotBlank(goods.getOneList())){
			pramas.put("hasListcode", "yes");
		}
        pramas.put("codeList", codeList);
		List<Goods> goodsList = goodsDao.getGoodsListByConditionWithPage(pramas);
		return goodsList;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.goods.GoodsManager#getGoodsListByConditionWithPageByCodeListExport(com.huaixuan.network.biz.domain.goods.Goods, java.util.List)
	 */
	@Override
	public List<Goods> getGoodsListByConditionWithPageByCodeListExport(
			Goods goods, List<String> codeList) {
		
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
        if(StringUtils.isNotBlank(goods.getThreeList()) || StringUtils.isNotBlank(goods.getTwoList()) || StringUtils.isNotBlank(goods.getOneList())){
			pramas.put("hasListcode", "yes");
		}
        pramas.put("codeList", codeList);
		List<Goods> goodsList = goodsDao.getGoodsListByConditionWithPage(pramas);
		return goodsList;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.goods.GoodsManager#updateEmallGoods(java.util.Map)
	 */
	@Override
	public void updateEmallGoods(Map<String, List<String>> keyMap) {
		 //从map集合中得到一个整体，就是其中的一个键和一个list集合
		 Set<Entry<String,List<String>>> keySet= keyMap.entrySet();
		 Iterator<Entry<String,List<String>>> it = keySet.iterator();
		 //将map中的每个键值对一个一个拿出来
		 while(it.hasNext())
		 { 
			 Entry<String, List<String>> entry = it.next();
			 //拿出一对中的键
			 String sikuSku = entry.getKey();
			 //拿出一对中的值
			 List<String> ourList = entry.getValue();
			  Goods g = new Goods();
			  g.setType(ourList.get(0));      //型号
			  g.setMaterial(ourList.get(1));  //材质
			  g.setColor(ourList.get(2));     //颜色
			  g.setGoodsWeightStr(ourList.get(3)); //重量
			  if(StringUtils.isNotBlank(ourList.get(4))){ //商品名称
				  g.setTitle(ourList.get(4));      
			  }
			  g.setMaterialdes(ourList.get(5));  //材质描述
			  g.setColordes(ourList.get(6));    //颜色描述
			  g.setOrigin(ourList.get(7));		//产地	 
			  g.setSecurityTC(ourList.get(8));   //安全技术类别
			  g.setImplementationS(ourList.get(9));  //执行标准
			 
			  goodsDao.updateEmallGoodsWeight(g);

		 }
		
	}
		
	


	

}
