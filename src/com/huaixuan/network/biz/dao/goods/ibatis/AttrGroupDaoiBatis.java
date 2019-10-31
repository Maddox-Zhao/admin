package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.AttrGroupDao;
import com.huaixuan.network.biz.domain.goods.AttrGroup;

@Repository("attrGroupDao")
public class AttrGroupDaoiBatis implements AttrGroupDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public void addAttrGroup(AttrGroup attrGroup) throws Exception {
        this.sqlMapClient.insert("addAttrGroup", attrGroup);
    }

    public int editAttrGroup(AttrGroup attrGroup) throws Exception {
        return this.sqlMapClient.update("editAttrGroup", attrGroup);
    }

    public int removeAttrGroup(Long attrGroupId) throws Exception {
        return this.sqlMapClient.delete("removeAttrGroup", attrGroupId);
    }

    public void removeAttrGroupAll() throws Exception {

        this.sqlMapClient.delete("removeAttrGroupAll");
    }

    public AttrGroup getAttrGroup(Long attrGroupId) throws Exception {
        return (AttrGroup) this.sqlMapClient.queryForObject("getAttrGroup",
            attrGroupId);
    }

    public List<AttrGroup> getAttrGroups() throws Exception {
        return this.sqlMapClient.queryForList("getAttrGroups", null);
    }

    public AttrGroup getAttrGroupByName(String attrGroupName) throws Exception {
        return (AttrGroup) this.sqlMapClient.queryForObject("getAttrGroupByName",
            attrGroupName);
    }

}
