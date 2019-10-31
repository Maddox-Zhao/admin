package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Brand;

public interface CategoryManager {

	/**
	 * ��ʱ�������
	 */
	public void quartzCategory();

	/**
	 * ͨ��id�õ�
	 */
	public Category getCategory(Long categoryId);

	/**
	 * ���Category
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
	 * ��ȡ����Ŀ����Ϣ
	 * 
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	public List<Category> getChildInfoOfTheParent(String parentCode) throws Exception;

	/**
	 * ��ȡǰһ����Ŀ����Ϣ
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Category getBeforeCategory(Category category) throws Exception;

	/**
	 * ��ȡ��һ����Ŀ����Ϣ
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Category getNextCategory(Category category) throws Exception;

	/**
	 * ͨ����Ŀcode��ȡ��Ŀȫ��
	 * 
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	String getCatFullNameByCatcode(String catCode) throws Exception;

	/**
	 * ͨ����Ŀcode��ȡ��Ŀȫ��(��css�Լ�����)
	 * 
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	String getCatFullNameByCatcode(String catCode, String head, String tail, String brandId, String... action)
			throws Exception;

	/**
	 * ͨ����Ŀcode��ȡ��Ŀȫ��
	 * 
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	String getCatFullNameByCatcodeSimple(String catCode, String connString);

	/**
	 * �����Ŀ�����Ƿ����
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	boolean chackNameAvailableOrNot(Category category) throws Exception;

	/**
	 * �ֲ����ʾ��Ŀ����Ϣ
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
	 * ������Ŀ�����ѯ����Ҷ�ӽ��
	 * 
	 * @param categoryCode
	 * @return
	 */
	List<Category> getCategoryListByCode(String categoryCode);

	List<Category> getCategoryListByParentCode(Category category);

	/**
	 * ��ȡ����Ŀ����Ϣ
	 * 
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	public List<Category> getRightChildInfoOfTheParent(String parentCode) throws Exception;

	/**
	 * ���ݹ�������Ʒ��
	 * 
	 * @param catCode
	 * @return
	 */
	List<Brand> getBrandsByAllCatCode(String catCode);

	/**
	 * ����Ʒ������Ŀ
	 * 
	 * @param brandId
	 * @return
	 */
	List<Category> getCategoryByBrandId(Long brandId);

	public Category getCategoryByCatCode(String catCode);

	/**
	 * ���ݶ�������Ŀ
	 * 
	 * @param category
	 * @return
	 */
	public List<Category> getCategoryListByCategory(Category category);
}
