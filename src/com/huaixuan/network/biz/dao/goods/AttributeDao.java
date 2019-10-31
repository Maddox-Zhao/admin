package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.query.QueryPage;

public interface AttributeDao  {
    /* @interface model: Attribute */
    void addAttribute(Attribute attribute) throws Exception;

    /* @interface model: Attribute */
    void editAttribute(Attribute attribute) throws Exception;

    /* @interface model: Attribute */
    void removeAttribute(Long attributeId) throws Exception;

    void removeAll() throws Exception;

    /* @interface model: Attribute,Attribute */
    Attribute getAttribute(Long attributeId) throws Exception;

    Attribute getAttributeByName(String attributeName);

    /**¸ù¾Ýattr_code²éÑ¯
     * @param attrCode
     * @return
     * @throws Exception
     */
    Attribute getAttributeByBuberChoose(String attrCode);


    int getAttributeByCode(String attributeCode) throws Exception;

    /* @interface model: Attribute,Attribute */
    List<Attribute> getAttributes(String catCode) throws Exception;

//    PageUtil<Attribute> getAttributesAll(int currentPage, final int pageSize, int totalCount)
//                                                                                             throws Exception;

    int getAllrAllCount();

    int checkNameAvailable(Attribute attribute) throws Exception;

//    PageUtil<Attribute> getAttributesByName(Map map, int currentPage, final int pageSize,
//                                            int totalCount) throws Exception;

    int getAttributeByNameCount(Map map) throws Exception;
    
    List<Attribute> getAttributesByName(Map map) throws Exception;
    
    List<Attribute> getAttributesAll(Map parMap);
}
