package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.CategoryDao;
import com.huaixuan.network.biz.domain.goods.Category;


@Repository("categoryDao")
public class CategoryDaoiBatis implements CategoryDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public void addCategory(Category category) throws Exception {
        this.sqlMapClient.insert("addCategory", category);
    }

    public void editCategory(Category category) throws Exception {
        this.sqlMapClient.update("editCategory", category);
    }

    public void editCategoryIsShow(Category category) throws Exception{
    	this.sqlMapClient.update("editCategoryIsShow", category);
    }

    public void removeCategory(String catCode) throws Exception {
        this.sqlMapClient.update("removeCategory", catCode);
    }

    public Category getCategory(Long categoryId) throws Exception {
        return (Category) this.sqlMapClient.queryForObject("getCategory", categoryId);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getCategorys() throws Exception {
        return this.sqlMapClient.queryForList("getCategorys", null);
    }

    public Category getCategoryByName(Category category) throws Exception {
        return (Category) this.sqlMapClient.queryForObject("getCategoryByName",
        		category);
    }

    public void removeCategoryAll() throws Exception {
        this.sqlMapClient.delete("removeCategoryAll");
    }

    @SuppressWarnings("unchecked")
    public List<Category> getCateGorysOfTheParent(String parentCode) throws Exception {
        return this.sqlMapClient.queryForList("getChildOfTheCategory", parentCode);
    }

    public void editChildCount(String catCode) throws Exception {
        this.sqlMapClient.update("AddChildCountOFTheCategory", catCode);
    }

    public Category getBeforeCategory(Category category) throws Exception {
        return (Category) this.sqlMapClient.queryForObject("getLastCategoryInfo",
            category);
    }

    public Category getNextCategory(Category category) throws Exception {
        return (Category) this.sqlMapClient.queryForObject("getNextCategoryInfo",
            category);
    }

    public Category getCategoryByCode(String catCode) throws Exception {
        return (Category) this.sqlMapClient.queryForObject("getCategoryByCode",
            catCode);
    }

    public int getCategoryOfTheSameName(Category category) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "checkEditNameAvaiOrNotForCategory", category);
    }

    public int getChildCountOfTheCategory(Category category) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getChildCountOfTheCategory", category);
    }

    public int getGoodsAcountRelatedToCategory(String catCode) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getGoodsAcountRelatedToCategory", catCode);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getCategoryInfoByDepth(int depth) throws Exception {
        return this.sqlMapClient.queryForList("getCategoryByDepth", depth);
    }

    public int getMaxDepthOfTheCategory() throws Exception {
        return (Integer) this.sqlMapClient.queryForObject("getMaxDepth");
    }

    public void changeLeafStatus(Category category) throws Exception {
        this.sqlMapClient.update("changeLeafStatus", category);
    }

    public void changeSearchStatus(Category category) {
        this.sqlMapClient.update("changeSearchStatus", category);
    }

    public List<Category> getCategoryList() {
        return this.sqlMapClient.queryForList("getCategoryList");
    }

    public List<Category> getCategoryNotShown(List codes) {
        Map m = new HashMap();
        m.put("codes", codes);
        return this.sqlMapClient.queryForList("getCategoryNotShow", m);
    }

    public List<Category> getCategoryNotShownByOrder(List codes) {
    	Map m = new HashMap();
    	m.put("codes", codes);
		return this.sqlMapClient.queryForList("getCategoryNotShownByOrder", m);
	}

	public List<String> getCategoryNotShownId() {
        return this.sqlMapClient.queryForList("getCategoryNotShownId");
    }

    public List<Category> getCategoryListByCode(String categoryCode){
    	return this.sqlMapClient.queryForList("getCategoryListByCode",categoryCode);
    }

    public List<Category> getCategoryListByParentCode(Category category){
    	return this.sqlMapClient.queryForList("getCategoryListByParentCode", category);
    }

	public List<Category> getRightChildInfoOfTheParent(String parentCode) throws Exception {
		return this.sqlMapClient.queryForList("getRightChildInfoOfTheParent", parentCode);
	}

	public Category getCategoryByCatCode(String catCode) {
		// TODO Auto-generated method stub
		return (Category)this.sqlMapClient.queryForObject("getCategoryByCatCode",catCode);
	}

	@Override
	public List<Category> getCategoryListByCategory(Category category) {
		return sqlMapClient.queryForList("getCategoryListByCategory", category);
	}
}
