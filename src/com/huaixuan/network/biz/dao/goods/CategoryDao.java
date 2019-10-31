package com.huaixuan.network.biz.dao.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.Category;


public interface CategoryDao {
    /* @interface model: Category */
    void addCategory(Category category) throws Exception;

    /* @interface model: Category */
    void editCategory(Category category) throws Exception;

    /* @interface model: Category */
    void removeCategory(String catCode) throws Exception;

    void removeCategoryAll() throws Exception;

    void changeLeafStatus(Category category) throws Exception;

    /* @interface model: Category,Category */
    Category getCategory(Long categoryId) throws Exception;

    Category getCategoryByName(Category category) throws Exception;

    Category getCategoryByCode(String catCode) throws Exception;

    /* @interface model: Category,Category */
    List<Category> getCategorys() throws Exception;

    List<Category> getCateGorysOfTheParent(String parentCode) throws Exception;

    void editChildCount(String catCode) throws Exception;

    Category getBeforeCategory(Category category) throws Exception;

    Category getNextCategory(Category category) throws Exception;

    int getCategoryOfTheSameName(Category category) throws Exception;

    int getChildCountOfTheCategory(Category category) throws Exception;

    int getGoodsAcountRelatedToCategory(String catCode) throws Exception;

    int getMaxDepthOfTheCategory() throws Exception;

    List<Category> getCategoryInfoByDepth(int depth) throws Exception;

    void changeSearchStatus(Category category);

    List<Category> getCategoryList();

    List<String> getCategoryNotShownId();

    List<Category> getCategoryNotShown(List list);

    /**
     * ���������� by shlin
     * @param list
     * @return
     */
    List<Category> getCategoryNotShownByOrder(List list);

     /**������Ŀ�����ѯ����Ҷ�ӽ��
     * @param categoryCode
     * @return
     */
    List<Category> getCategoryListByCode(String categoryCode);

    List<Category> getCategoryListByParentCode(Category category);

    void editCategoryIsShow(Category category) throws Exception;

    /**
     * ��ȡ��ɾ������Ŀ
     * @return
     * @throws Exception
     */
    List<Category> getRightChildInfoOfTheParent(String parentCode) throws Exception;

    Category getCategoryByCatCode(String catCode);
    
    List<Category> getCategoryListByCategory(Category category);

}
