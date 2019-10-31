package com.huaixuan.network.biz.service.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.CatAttrRel;


public interface CatAttrRelManager {
	

    public void addCatAttrRel(CatAttrRel catAttrRel);

    public void editCatAttrRel(CatAttrRel catAttrRel);

    public void removeCatAttrRel(Long catAttrRelId);

    public CatAttrRel getCatAttrRel(Long catAttrRelId);

    public List<CatAttrRel> getCatAttrRels();

    /**
     * 上级类目的子类目加一
     * @param catAttrRel
     * @return
     * @throws Exception
     */
    int getSortOrderOfTheCatRel(CatAttrRel catAttrRel) throws Exception;

    /**
     * 获取类目的属性信息
     * @param catCode
     * @return
     * @throws Exception
     */
    List<CatAttrRel> getAttributeOfTheCategoty(String catCode) throws Exception;

    /**
     * 和上一条有效的属性信息交换排列顺序
     * @param sourceID
     * @throws Exception
     */
    void exchangeSortOrderBefore(long sourceID) throws Exception;

    /**
     * 和下一条有效的属性信息交换排列顺序
     * @param sourceID
     * @throws Exception
     */
    void exchangeSortOrderAfter(long sourceID) throws Exception;
    
    /**
     * 根据类目找关联属性信息
     * @param catCode
     * @return
     */
    CatAttrRel getCatAttrRelByCondition(String catCode,String attrCode);

}
