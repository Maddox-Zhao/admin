package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Brand;

public interface CategoryManager {

	/**
	 * 定时任务调用
	 */
	public void quartzCategory();

	/**
	 * 通过id得到
	 */
	public Category getCategory(Long categoryId);

	/**
	 * 清空Category
	 */
	public void clearCategory();

	/* @interface model: Category */
	public boolean addCategory(Category category) throws Exception;

	/* @interface model: Category */
	public Map<Boolean, String> editCategory(Category category) throws Exception;

	/* @interface model: Category */
	public boolean removeCategory(String catCode,Category category) throws Exception;

	/* @interface model: Category,Category */
	public List<Category> getCategorys(boolean isView);

	/**
	 * 获取子类目的信息
	 * 
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	public List<Category> getChildInfoOfTheParent(String parentCode) throws Exception;

	/**
	 * 获取前一个类目的信息
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Category getBeforeCategory(Category category) throws Exception;

	/**
	 * 获取后一个类目的信息
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Category getNextCategory(Category category) throws Exception;

	/**
	 * 通过类目code获取类目全名
	 * 
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	String getCatFullNameByCatcode(String catCode) throws Exception;

	/**
	 * 通过类目code获取类目全名(含css以及链接)
	 * 
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	String getCatFullNameByCatcode(String catCode, String head, String tail, String brandId, String... action)
			throws Exception;

	/**
	 * 通过类目code获取类目全名
	 * 
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	String getCatFullNameByCatcodeSimple(String catCode, String connString);

	/**
	 * 检查类目名称是否可用
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	boolean chackNameAvailableOrNot(Category category) throws Exception;

	/**
	 * 分层次显示类目的信息
	 * 
	 * @return
	 * @throws Exception
	 */
	// Map<String, List<Category>> getCategoryByDepth() throws Exception;

	Category getCateInfoByCatCode(String cateCode) throws Exception;

	Category getCateInfoByCatName(Category category) throws Exception;

	List<Category> getCategoryForGuide();

	List<Category> getCatInfoByDepth(int depth) throws Exception;

	/**
	 * 根据类目代码查询他的叶子结点
	 * 
	 * @param categoryCode
	 * @return
	 */
	List<Category> getCategoryListByCode(String categoryCode);

	List<Category> getCategoryListByParentCode(Category category);

	/**
	 * 获取子类目的信息
	 * 
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	public List<Category> getRightChildInfoOfTheParent(String parentCode) throws Exception;

	/**
	 * 根据关联表找品牌
	 * 
	 * @param catCode
	 * @return
	 */
	List<Brand> getBrandsByAllCatCode(String catCode);

	/**
	 * 根据品牌找类目
	 * 
	 * @param brandId
	 * @return
	 */
	List<Category> getCategoryByBrandId(Long brandId);

	public Category getCategoryByCatCode(String catCode);

	/**
	 * 根据对象找类目
	 * 
	 * @param category
	 * @return
	 */
	public List<Category> getCategoryListByCategory(Category category);
}
