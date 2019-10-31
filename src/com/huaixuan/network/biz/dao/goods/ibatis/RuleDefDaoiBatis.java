package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.RuleDefDao;
import com.huaixuan.network.biz.domain.goods.RuleDef;

@Repository("ruleDefDao")
public class RuleDefDaoiBatis implements RuleDefDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    /* @model: */
    public void addRuleDef(RuleDef ruleDef) throws Exception {
        this.sqlMapClient.insert("addRuleDef", ruleDef);
    }

    /* @model: */
    public void editRuleDef(RuleDef ruleDef) throws Exception {
        this.sqlMapClient.update("editRuleDef", ruleDef);
    }

    /* @model: */
    public void removeRuleDef(Long ruleDefId) throws Exception {
        this.sqlMapClient.delete("removeRuleDef", ruleDefId);
    }

    /* @model: */
    public RuleDef getRuleDef(Long ruleDefId) throws Exception {
        return (RuleDef) this.sqlMapClient.queryForObject("getRuleDef", ruleDefId);
    }

    /* @model: */
    public List<RuleDef> getRuleDefs() throws Exception {
        return this.sqlMapClient.queryForList("getRuleDefs", null);
    }

    public RuleDef getRuleDefByCode(String code) throws Exception {
        return (RuleDef) this.sqlMapClient.queryForObject("getRuleDefByCode", code);
    }
}
