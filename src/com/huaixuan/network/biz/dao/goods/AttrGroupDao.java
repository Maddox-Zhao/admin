package com.huaixuan.network.biz.dao.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.AttrGroup;

public interface AttrGroupDao {
    /* @interface model: AttrGroup */
    void addAttrGroup(AttrGroup attrGroup) throws Exception;

    /* @interface model: AttrGroup */
    int editAttrGroup(AttrGroup attrGroup) throws Exception;

    /* @interface model: AttrGroup */
    int removeAttrGroup(Long attrGroupId) throws Exception;

    void removeAttrGroupAll() throws Exception;

    /* @interface model: AttrGroup,AttrGroup */
    AttrGroup getAttrGroup(Long attrGroupId) throws Exception;

    AttrGroup getAttrGroupByName(String attrGroupName) throws Exception;

    /* @interface model: AttrGroup,AttrGroup */
    List<AttrGroup> getAttrGroups() throws Exception;
}
