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
     * �ϼ���Ŀ������Ŀ��һ
     * @param catAttrRel
     * @return
     * @throws Exception
     */
    int getSortOrderOfTheCatRel(CatAttrRel catAttrRel) throws Exception;

    /**
     * ��ȡ��Ŀ��������Ϣ
     * @param catCode
     * @return
     * @throws Exception
     */
    List<CatAttrRel> getAttributeOfTheCategoty(String catCode) throws Exception;

    /**
     * ����һ����Ч��������Ϣ��������˳��
     * @param sourceID
     * @throws Exception
     */
    void exchangeSortOrderBefore(long sourceID) throws Exception;

    /**
     * ����һ����Ч��������Ϣ��������˳��
     * @param sourceID
     * @throws Exception
     */
    void exchangeSortOrderAfter(long sourceID) throws Exception;
    
    /**
     * ������Ŀ�ҹ���������Ϣ
     * @param catCode
     * @return
     */
    CatAttrRel getCatAttrRelByCondition(String catCode,String attrCode);

}
