package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.CatAttrRel;

@Repository("catAttrRelDao")
public class CatAttrRelDaoiBatis implements CatAttrRelDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public void addCatAttrRel(CatAttrRel catAttrRel) throws Exception {
        this.sqlMapClient.insert("addCatAttrRel", catAttrRel);
    }

    public void editCatAttrRel(CatAttrRel catAttrRel) throws Exception {
        this.sqlMapClient.update("editCatAttrRel", catAttrRel);
    }

    public void removeCatAttrRel(Long catAttrRelId) throws Exception {
        this.sqlMapClient.update("removeCatAttrRel", catAttrRelId);
    }

    public CatAttrRel getCatAttrRel(Long catAttrRelId) throws Exception {
        return (CatAttrRel) this.sqlMapClient.queryForObject("getCatAttrRel",
            catAttrRelId);
    }

    public List<CatAttrRel> getCatAttrRels() throws Exception {
        return this.sqlMapClient.queryForList("getCatAttrRels", null);
    }

    public CatAttrRel getCatAttrRelByName(String showName) throws Exception {
        return (CatAttrRel) this.sqlMapClient.queryForObject("getCatAttrRelByName",
            showName);
    }

    public void removeCatAttrRelAll() throws Exception {
        this.sqlMapClient.delete("removeCatAttrRelAll");
    }

    public int getRelAcountOfTheCategory(CatAttrRel catAttrRel) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject("getRelAcountOfTheCategory",
            catAttrRel);
    }

    public List<CatAttrRel> getAttributeOfTheCategory(String catCode) throws Exception {
        return this.sqlMapClient
            .queryForList("getAttributeInfoOfTheCategory", catCode);
    }

    public CatAttrRel getLastRecord(CatAttrRel catAttrRel) throws Exception {
        return (CatAttrRel) this.sqlMapClient.queryForObject("getBeforeRecord",
            catAttrRel);
    }

    public CatAttrRel getNextRecord(CatAttrRel catAttrRel) throws Exception {
        return (CatAttrRel) this.sqlMapClient.queryForObject("getAfterRecord",
            catAttrRel);
    }

    public void editSortOrder(CatAttrRel catAttrRel) throws Exception {
        this.sqlMapClient.update("modifyTheSortOrder", catAttrRel);
    }

    public List<AttributeDTO> getAttributeDTOByCatCode(String catCode) {
        String[] catCodes = catCode.split("\\.");
        List catCodeList = new java.util.ArrayList();
        String tempCode = catCodes[0];
        catCodeList.add(tempCode);
        for (int i = 1; i < catCodes.length; i++) {
            tempCode = tempCode + "." + catCodes[i];
            catCodeList.add(tempCode);
        }

        return this.sqlMapClient.queryForList("getAttributeDTOByCatCode", catCodeList);
    }

    public void removeCatAttrRelByCatCode(String catCode) throws Exception {

        this.sqlMapClient.update("removeCatAttrRelByVatCode", catCode);
    }

    public int countInfoByAttrCode(String attrCode) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getCatAttrRelCountByAttrCode", attrCode);
    }

	public CatAttrRel getCatAttrRelByCondition(Map parMap) {
		return (CatAttrRel)this.sqlMapClient.queryForObject("getCatAttrRelByCondition",parMap);
	}
}
