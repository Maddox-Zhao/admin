package com.huaixuan.network.biz.service.goods.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.dao.goods.CategoryDao;
import com.huaixuan.network.biz.dao.shop.BrandCategoryDao;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandCategory;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.EmallStringUtil;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("categoryManager")
public class CategoryManagerImpl implements CategoryManager {

	protected Log log = LogFactory.getLog(this.getClass());

	private Map<String, Category> map_cata = null;

	private Map<Long, String> map_code = null;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private CatAttrRelDao catAttrRelDao;

	@Autowired
	private GoodsManager goodsManager;

	@Autowired
	private BrandService brandService;

	@Autowired
	private BrandCategoryDao brandCategoryDao;

	private static final String CONNSTRING = ">>";

	private Random random = new Random(System.currentTimeMillis());

	private void loadCategory() {
		try {
			Map<String, Category> temp = new HashMap<String, Category>();
			Map<Long, String> temp_code = new HashMap<Long, String>();

			List<Category> list = categoryDao.getCategorys();

			String str = new String();

			for (Category entity : list) {
				temp.put(String.valueOf(entity.getCatCode()), entity);
				temp_code.put(entity.getId(), entity.getCatCode());
			}

			for (Category entity : list) {
				str = setParent(entity, temp, str);
			}

			if (StringUtil.isNotBlank(str)) {
				log.error("找类目父节点失败(IDS=[" + str + "])");
			}

			this.map_cata = temp;
			this.map_code = temp_code;

			temp = null;
			temp_code = null;

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void getCategory() {
		if (map_cata == null || map_code == null) {
			loadCategory();
		}
	}

	// 设置父节点
	private String setParent(Category entity, Map<String, Category> map, String str) {
		if (entity.getDepth() != 0) {
			if (map.get(entity.getParentCode()) != null) {
				map.get(entity.getParentCode()).addChild(entity);
			} else {
				str = str + entity.getParentCode() + ",";
				log.error("找类目父节点失败(IDS=[" + str + "])");
			}
		}
		return str;
	}

	public void clearCategory() {
		map_cata = null;
		map_code = null;
	}

	/**
	 * 给任务调度的方法
	 */
	public void quartzCategory() {
		int k = random.nextInt(10);
		try {
			Thread.currentThread();
			Thread.sleep(500 * k);
		} catch (Exception e) {
			log.error(e);
		}
		clearCategory();
	}

	/*******************************************************************************/
	/**
	 * 根据ID得到类目
	 */
	public Category getCategory(Long id) {
		getCategory();
		if (id != null) {
			return map_cata.get(map_code.get(id));
		}
		return null;
	}

	/**
	 * ͨ通过父类目的catCode 和 孩子数量来自动生成子类目的catCode
	 *
	 * @param parentCode
	 * @param childNum
	 * @return
	 */
	private String generateCode(String parentCode, int childNum) {
		StringBuilder sb = new StringBuilder();
		if (!parentCode.equalsIgnoreCase("-1")) {
			sb.append(parentCode);
			sb.append(".");
		}
		if (childNum < 9) {
			sb.append("00");
			sb.append(String.valueOf(childNum + 1));
		}
		if (childNum >= 9 && childNum < 99) {
			sb.append("0");
			sb.append(String.valueOf(childNum + 1));
		}
		if (childNum >= 99) {
			sb.append(String.valueOf(childNum + 1));
		}

		return sb.toString();
	}

	/**
	 * 父类目的深度+1
	 *
	 * @param parentDepth
	 * @return
	 */
	private int generateDepth(int parentDepth) {
		return parentDepth + 1;
	}

	/**
	 * 新生成的类目序号是根据其父类目的子节点个数来确定的,如果其父类目有n个子节点，则它的序号为n+1
	 *
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	private int genarateSortOrder(String parentCode) throws Exception {
		Category category = new Category();
		category.setParentCode(parentCode);
		int childCount = this.categoryDao.getChildCountOfTheCategory(category);
		return childCount + 1;

	}

	/**
	 * 获取类目的最大深度
	 *
	 * @return
	 * @throws Exception
	 */
	private int getMAxDepth() throws Exception {
		return this.categoryDao.getMaxDepthOfTheCategory();
	}

	// /**
	// * 以深度作为依据获取类目（这个方法现在没用了已经）
	// *
	// * @param depth
	// * @return
	// * @throws Exception
	// * @see
	// com.hundsun.bible.facade.goods.CategoryManager#getCategoryByDepth(int)
	// */
	// public Map<String, List<Category>> getCategoryByDepth() throws Exception
	// {
	// Map<String, List<Category>> map = new HashMap<String, List<Category>>();
	// for (int i = 1; i <= getMAxDepth(); i++) {
	// map.put("CategotyLayer" + String.valueOf(i), this.categoryDao
	// .getCategoryInfoByDepth(i));
	// }
	// return map;
	// }

	/**
	 * 新增类目
	 *
	 * @param category
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#addCategory(com.hundsun.bible.domain.model.Category)
	 */
	public boolean addCategory(Category category) throws Exception {
		log.info("CategoryManagerImpl.addCategory method");
		clearCategory();
		boolean flag = false;
		try {
			if (chackNameAvailableOrNot(category)) {
				Category categoryParent = this.categoryDao.getCategoryByCode(category.getParentCode());
				int parentDepth = categoryParent.getDepth();
				int parentChildNums = categoryParent.getChildNums();
				// 生成cat_code 是根据父类目的catCode 和 父类目的孩子数拼装起来的字符串
				category.setCatCode(generateCode(category.getParentCode(), parentChildNums));
				category.setChildNums(0);
				category.setDepth(generateDepth(parentDepth));
				category.setSortOrder(genarateSortOrder(category.getParentCode()));
				this.categoryDao.addCategory(category);
				// 如果父类目原来是根类目（Is_Leaf=1）则把它变成0
				if (categoryParent.getIsLeaf() == 1) {
					Category cat = new Category();
					cat.setCatCode(category.getParentCode());
					cat.setIsLeaf(0);
					this.categoryDao.changeLeafStatus(cat);
				}
				// 将该类目的父类目的孩子数增加1
				AddChildCountOfTheCategory(category.getParentCode());
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 修改类目
	 *
	 * @param category
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#editCategory(com.hundsun.bible.domain.model.Category)
	 */
	public Map<Boolean, String> editCategory(Category category) throws Exception {
		log.info("CategoryManagerImpl.editCategory method");
		Map<Boolean, String> map = new HashMap<Boolean, String>();
		boolean flag = false;
		if (chackNameAvailableOrNot(category)) {
			// 判断是否修改了isShow标记
			Category catTmp = this.getCategory(category.getId());
			if (catTmp.getIsShow() != category.getIsShow()) {// 修改了
				// 判断是否有叶子结点，如果有则叶子结点一起隐藏
				if (category.getIsShow() == 0) {
					int relatedCount = this.categoryDao.getGoodsAcountRelatedToCategory(category.getCatCode());
					int is_leaf = category.getIsLeaf();
					if (relatedCount == 0 && is_leaf == 1) {
						this.categoryDao.editCategoryIsShow(category);
						flag = true;
						map.put(flag, null);
					} else {
						map.put(flag, "商品类目隐藏错误");
						return map;
					}
					// List<Category> catList=this.getCategoryListByParentCode(category);
					// for(Category catObj:catList){
					// catObj.setIsShow(0);
					// this.categoryDao.editCategory(catObj);
					// }
				} else {// 显示时要判断他的上级类目是否显示，如果没有显示则更新
					Category pareTmp = this.getCateInfoByCatCode(category.getParentCode());
					if (pareTmp.getIsShow() == 0) {
						map.put(flag, "商品上级类目不显示");
						return map;
					}
				}
			}

			this.categoryDao.editCategory(category);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("catCode", category.getCatCode());
			paramMap.put("agio", category.getCommonAgentAgio());
			goodsManager.updateAgentPrice(paramMap);

			flag = true;
			map.put(flag, null);
		} else {
			map.put(flag, "该商品类目已经存在");
		}
		clearCategory();
		return map;
	}

	/**
	 *  删除类目
	 *
	 * @param catCode
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#removeCategory(java.lang.String)
	 */
	public boolean removeCategory(String catCode,Category category) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("removeCategory(Long) - start"); //$NON-NLS-1$
		}
		boolean flag = false;
		int relatedCount = this.categoryDao.getGoodsAcountRelatedToCategory(catCode);
		int is_leaf = category.getIsLeaf();
		if (relatedCount == 0 && is_leaf == 1) {
			this.categoryDao.removeCategory(catCode);
			this.catAttrRelDao.removeCatAttrRelByCatCode(catCode);
			clearCategory();
			flag = true;
		}
		// 检查父类目的，如果删除这个类目之后，父类目的子节点为0，则将父类目的is_leaf置为1
		Category cat = new Category();
		cat.setParentCode(category.getParentCode());
		int childOfTheParent = this.categoryDao.getChildCountOfTheCategory(cat);
		if (childOfTheParent == 0) {
			cat.setIsLeaf(1);
			cat.setCatCode(category.getParentCode());
			this.categoryDao.changeLeafStatus(cat);
		}
		if (log.isDebugEnabled()) {
			log.debug("removeCategory(Long) - end"); //$NON-NLS-1$
		}
		return flag;
	}

	public boolean displayCategory(String catCode) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("displayCategory(Long) - start"); //$NON-NLS-1$
		}
		boolean flag = false;
		Category category = this.categoryDao.getCategoryByCode(catCode);
		int relatedCount = this.categoryDao.getGoodsAcountRelatedToCategory(catCode);
		int is_leaf = category.getIsLeaf();
		if (relatedCount == 0 && is_leaf == 1) {
			this.categoryDao.removeCategory(catCode);
			this.catAttrRelDao.removeCatAttrRelByCatCode(catCode);
			clearCategory();
			flag = true;
		}
		// 检查父类目的，如果删除这个类目之后，父类目的子节点为0，则将父类目的is_leaf置为1
		Category cat = new Category();
		cat.setParentCode(category.getParentCode());
		int childOfTheParent = this.categoryDao.getChildCountOfTheCategory(cat);
		if (childOfTheParent == 0) {
			cat.setIsLeaf(1);
			cat.setCatCode(category.getParentCode());
			this.categoryDao.changeLeafStatus(cat);
		}
		if (log.isDebugEnabled()) {
			log.debug("removeCategory(Long) - end"); //$NON-NLS-1$
		}
		return flag;
	}

	public List<Category> getCategorys(boolean isView) {
		getCategory();
		List<Category> resultList = null;
		Set<Category> set = new TreeSet<Category>(new Comparator<Category>() {
			public int compare(Category o1, Category o2) {
				if (o1.getId() == o2.getId())
					return 0;
				if (o1.getDepth() != o2.getDepth())
					return o1.getDepth() - o2.getDepth();
				if (o1.getSortOrder() != o2.getSortOrder())
					return o1.getSortOrder() - o2.getSortOrder();
				return -1;
			}
		});
		for (String key : map_cata.keySet()) {
			set.add(map_cata.get(key));
		}
		if (set.isEmpty()) {
			return null;
		} else {
			resultList = new ArrayList<Category>(set);
			if (!isView) {
				List<Category> list = new ArrayList<Category>();
				for (Category obj : resultList) {
					if (obj.getIsShow() == 1) {
						list.add(obj);
					}
				}
				resultList = list;
			}
		}
		return resultList;

	}

	/**
	 * 获取子类目的信息
	 *
	 * @param parentCode
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#getChildInfoOfTheParent(java.lang.String)
	 */
	public List<Category> getChildInfoOfTheParent(String parentCode) throws Exception {
		getCategory();
		List<Category> list = new ArrayList<Category>();
		for (Category entity : map_cata.get(parentCode).getChidrens()) {
			list.add(entity);
		}
		return list;
	}

	/**
	 * 增加类目的孩子数量
	 *
	 * @param catCode
	 * @throws Exception
	 */
	private void AddChildCountOfTheCategory(String catCode) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("AddChildCountOfTheCategory(String) - start"); //$NON-NLS-1$
		}

		clearCategory();
		this.categoryDao.editChildCount(catCode);

		if (log.isDebugEnabled()) {
			log.debug("AddChildCountOfTheCategory(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * 查询并列（同一个parent）的前一个类目的信息
	 *
	 * @param category
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#getBeforeCategory(com.hundsun.bible.domain.model.Category)
	 */
	public Category getBeforeCategory(Category category) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("getBeforeCategory(Category) - start"); //$NON-NLS-1$
		}
		Category category1 = new Category();
		category1 = this.categoryDao.getBeforeCategory(category);
		return category1;
	}

	/**
	 * 查询并列（同一个parent）的后一个类目的信息
	 * @param category
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#getNextCategory(com.hundsun.bible.domain.model.Category)
	 */
	public Category getNextCategory(Category category) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("getNextCategory(Category) - start"); //$NON-NLS-1$
		}
		Category category1 = new Category();
		category1 = this.categoryDao.getNextCategory(category);
		return category1;
	}

	/**
	 *获取类目的全路径（纯文字效果 不带css 不带链接）
	 *
	 * @param catCode
	 * @param connString
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#getCatFullNameByCatcodeSimple(java.lang.String,
	 *      java.lang.String)
	 */
	public String getCatFullNameByCatcodeSimple(String catCode, String connString) {
		if (EmallStringUtil.isBlank(catCode)) {
			return "";
		}
		getCategory();
		StringBuffer sb = new StringBuffer();
		Category category = map_cata.get(catCode);
		Stack<Category> stack = new Stack<Category>();
		if (category != null) {
			while (category.getParent() != null) {
				stack.push(category);
				category = category.getParent();
			}
			if(stack.size() > 0){
				sb.append(stack.pop().getCatName());
				while (!stack.empty()) {
					String catName = stack.pop().getCatName();
					if (catName.length() < 12) {
						sb.append(connString).append(catName);
					} else {
						sb.append(connString).append(catName.substring(0, 12)).append("...");
					}
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public String getCatFullNameByCatcode(String catCode, String head, String tail, String brandId, String... action)
			throws Exception {
		List<Category> list = new ArrayList<Category>();
		List<String> listAll = new ArrayList<String>();
		Category categoryParent = new Category();
		Category categoryChild = new Category();
		// jinxin by 2010-0919
		// String actionType = (String) ActionContext.getContext().get("actionType");
		// actionType =(actionType==null)?"daixiao":actionType;
		String actionType = "daixiao";
		// jinxin by 2010-0919
		if (StringUtil.isEmpty(catCode)) {
			catCode = "-1";
		}
		if (null != categoryDao.getCategoryByCode(catCode)) {
			categoryChild = categoryDao.getCategoryByCode(catCode);
			if (!categoryChild.getCatCode().equalsIgnoreCase("-1")) {
				list.add(categoryChild);
			}
			// 循环遍历，直到循环到根类目
			while (!categoryChild.getParentCode().equalsIgnoreCase("-1")) {
				categoryParent = categoryDao.getCategoryByCode(categoryChild.getParentCode());
				if (null != categoryParent) {
					list.add(categoryParent);
					categoryChild = categoryParent;
				} else {
					categoryChild.setParentCode("-1");
				}
			}
			String hrefAction = null;
			// css效果
			if (null != action && action.length > 0) {
				hrefAction = action[0];
				// jinxx 0917 start
			} else if (actionType.equals("daixiao")) {
				hrefAction = "/index_daixiao.html";
			} else {
				hrefAction = "/index_pifa.html";
			}
			// jinxx 0917 end
			listAll.add("<li><a href=" + hrefAction + ">");
			listAll.add(head);
			listAll.add("</a></li>");
			Brand brand = null;
			if (list.size() > 0) {
				if (StringUtil.isNotBlank(brandId)) {
					brand = brandService.getBrand(Long.parseLong(brandId));
				}
			}

			for (int i = list.size() - 1; i >= 0; i--) {
				// jinxx 0917 start
				listAll.add("<li><a href='/" + actionType);
				if (brand != null) {
					listAll.add("Brands/");
					listAll.add(list.get(i).getCatCode() + "_");
					listAll.add(((Long) brand.getId()).toString());
				} else {
					listAll.add("CatCode/");
					listAll.add(list.get(i).getCatCode());
				}
				listAll.add(".html'>");
				// jinxx 0917 end
				// listAll.add("<li><a href='");
				// // listAll.add(hrefAction + "?catCode=");
				// listAll.add(list.get(i).getCatCode());
				// listAll.add(".html");
				// if(null!=brand){
				// listAll.add("&&brand_id=");
				// listAll.add(((Long)brand.getId()).toString());
				// }
				// listAll.add(".html'>");
				// 限制类目名称12个字符，多余的字符以省略号代替
				if (list.get(i).getCatName().length() > 12) {
					listAll.add(list.get(i).getCatName().substring(0, 12));
					// 加上连接符号
					if (i > 0)
						listAll.add(" &gt");
				} else {
					listAll.add(list.get(i).getCatName());
					// 加上连接符号
					if (i > 0)
						listAll.add(" &gt");
				}
				listAll.add("</a>");
				listAll.add("</li>");

			}
			if (null != brand) {
				listAll.add(" &gt");
				listAll.add(brand.getBrandName());
			}
			if (StringUtil.isNotEmpty(tail)) {
				listAll.add("<li>");
				listAll.add(tail);
				listAll.add("</li>");
			}
			// listAll.add("<div class='clearing'>");
			// listAll.add("</div>");
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < listAll.size(); i++) {
			sb.append(listAll.get(i));
		}
		return sb.toString();
	}

	public String getCatFullNameByCatcode(String catCode) throws Exception {
		return getCatFullNameByCatcodeSimple(catCode, CONNSTRING);
	}

	// 检查该类目名称是否可用
	public boolean chackNameAvailableOrNot(Category category) throws Exception {
		boolean flag = false;
		try {
			int count = categoryDao.getCategoryOfTheSameName(category);
			if (count == 0) {
				flag = true;
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				throw e;
			}
		}
		return flag;
	}

	public Category getCateInfoByCatCode(String cateCode) throws Exception {
		getCategory();
		return map_cata.get(cateCode);
	}

	public Category getCateInfoByCatName(Category category) throws Exception {
		return this.categoryDao.getCategoryByName(category);
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategoryForGuide() {
		List<String> catCodes = this.categoryDao.getCategoryNotShownId();
		List<Category> categoryNotShow = new ArrayList<Category>();
		if (catCodes.size() != 0) {
			categoryNotShow = this.categoryDao.getCategoryNotShown(catCodes);
		} else {
			categoryNotShow = this.categoryDao.getCategoryNotShown(null);
		}
		/*
		 * @SuppressWarnings("unused") List<Category> list = this.categoryDao.getCategoryList();
		 */
		return categoryNotShow;
	}

	/**
	 * 获取非删除子类目的信息
	 *
	 * @param parentCode
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.CategoryManager#getRightChildInfoOfTheParent(java.lang.String)
	 */
	public List<Category> getRightChildInfoOfTheParent(String parentCode) throws Exception {
		log.info("CategoryManagerImpl getRightChildInfoOfTheParent method");
		try {
			return this.categoryDao.getRightChildInfoOfTheParent(parentCode);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<Brand> getBrandsByAllCatCode(String catCode) {
		log.info("BrandManagerImpl.getBrandsByAllCatCode method");
		List<Brand> brandlist = new ArrayList<Brand>();
		;
		List<BrandCategory> brandListTemp = brandCategoryDao.getBrandByCateGory(catCode);
		if (brandListTemp != null && brandListTemp.size() > 0) {
			for (BrandCategory tmp : brandListTemp) {
				try {
					Brand brand = this.brandService.getBrand(tmp.getBrandId());
					if (brand != null) {
						brandlist.add(brand);
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		return brandlist;
	}

	public List<Category> getCategoryByBrandId(Long brandId) {
		log.info("BrandManagerImpl.getCategoryByBrandId method");
		List<Category> catList = new ArrayList<Category>();
		;
		List<BrandCategory> catListTemp = brandCategoryDao.getCategoryBybrandId(brandId);
		if (catListTemp != null && catListTemp.size() > 0) {
			for (BrandCategory tmp : catListTemp) {
				try {
					Category category = this.categoryDao.getCategoryByCatCode(tmp.getCatCode());
					if (category != null) {
						catList.add(category);
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		return catList;
	}

	public Category getCategoryByCatCode(String catCode) {
		return this.categoryDao.getCategoryByCatCode(catCode);
	}

	public void setCatAttrRelDao(CatAttrRelDao catAttrRelDao) {
		this.catAttrRelDao = catAttrRelDao;
	}

	public List<Category> getCatInfoByDepth(int depth) throws Exception {
		return this.categoryDao.getCategoryInfoByDepth(depth);
	}

	public List<Category> getCategoryListByCode(String categoryCode) {
		return this.categoryDao.getCategoryListByCode(categoryCode);
	}

	public List<Category> getCategoryListByParentCode(Category category) {
		return this.categoryDao.getCategoryListByParentCode(category);
	}

	@Override
	public List<Category> getCategoryListByCategory(Category category) {
		return this.categoryDao.getCategoryListByCategory(category);
	}
}
