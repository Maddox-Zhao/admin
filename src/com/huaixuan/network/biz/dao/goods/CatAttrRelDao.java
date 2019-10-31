package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.CatAttrRel;

public interface CatAttrRelDao  {
    /* @interface model: CatAttrRel */
    void addCatAttrRel(CatAttrRel catAttrRel) throws Exception;

    /* @interface model: CatAttrRel */
    void editCatAttrRel(CatAttrRel catAttrRel) throws Exception;

    /* @interface model: CatAttrRel */
    void removeCatAttrRel(Long catAttrRelId) throws Exception;

    void removeCatAttrRelByCatCode(String catCode) throws Exception;

    void removeCatAttrRelAll() throws Exception;

    /* @interface model: CatAttrRel,CatAttrRel */
    CatAttrRel getCatAttrRel(Long catAttrRelId) throws Exception;

    CatAttrRel getCatAttrRelByName(String showName) throws Exception;

    /* @interface model: CatAttrRel,CatAttrRel */
    List<CatAttrRel> getCatAttrRels() throws Exception;

    int getRelAcountOfTheCategory(CatAttrRel catAttrRel) throws Exception;

    List<CatAttrRel> getAttributeOfTheCategory(String catCode) throws Exception;

    CatAttrRel getLastRecord(CatAttrRel catAttrRel) throws Exception;

    CatAttrRel getNextRecord(CatAttrRel catAttrRel) throws Exception;

    void editSortOrder(CatAttrRel catAttrRel) throws Exception;

    /**
     * 根据类目代码，获取类目下所有属性集合(包括继承的属性)，如果同名，获取最近类目下的属性
     * @param catCode
     * @return
     * @throws DaoException
     */
    List<AttributeDTO> getAttributeDTOByCatCode(String catCode);

    int countInfoByAttrCode(String attrCode) throws Exception;
    
    CatAttrRel getCatAttrRelByCondition(Map parMap);

}
