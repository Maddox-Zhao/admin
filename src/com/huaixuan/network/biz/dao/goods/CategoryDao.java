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
     * 按销量排序 by shlin
     * @param list
     * @return
     */
    List<Category> getCategoryNotShownByOrder(List list);

     /**根据类目代码查询他的叶子结点
     * @param categoryCode
     * @return
     */
    List<Category> getCategoryListByCode(String categoryCode);

    List<Category> getCategoryListByParentCode(Category category);

    void editCategoryIsShow(Category category) throws Exception;

    /**
     * 获取非删除的类目
     * @return
     * @throws Exception
     */
    List<Category> getRightChildInfoOfTheParent(String parentCode) throws Exception;

    Category getCategoryByCatCode(String catCode);
    
    List<Category> getCategoryListByCategory(Category category);

}
