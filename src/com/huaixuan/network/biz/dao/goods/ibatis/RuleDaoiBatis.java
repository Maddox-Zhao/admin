package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.RuleDao;
import com.huaixuan.network.biz.domain.goods.Rule;


@Repository("ruleDao")
public class RuleDaoiBatis implements RuleDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    /* @model: */
    public Long addRule(Rule rule) throws Exception {
        Long id = (Long) this.sqlMapClient.insert("addRule", rule);
        return id;
    }

    /* @model: */
    public void editRule(Rule rule) throws Exception {
        this.sqlMapClient.update("editRule", rule);
    }

    /* @model: */
    public void removeRule(Long ruleId) throws Exception {
        this.sqlMapClient.delete("removeRule", ruleId);
    }

    /* @model: */
    public Rule getRule(Long ruleId) throws Exception {
        return (Rule) this.sqlMapClient.queryForObject("getRule", ruleId);
    }

    /* @model: */
    public List<Rule> getRules() throws Exception {
        return this.sqlMapClient.queryForList("getRules", null);
    }

    public List<Rule> getRulesByPromationId(Long promationId) {

        return this.sqlMapClient.queryForList("getRulesByPromationId", promationId);

    }

    public boolean removeRuleByPromationId(Long promationId) {
        return this.sqlMapClient.delete("removeRuleByPromationId", promationId) > 0 ? true
            : false;
    }

	public Rule getGiveRule(Map parMap) {
		return (Rule)this.sqlMapClient.queryForObject("getGiveRule", parMap);
	}

	public List<Rule> getSaleGiveRuleList(Long promationId) {
		return this.sqlMapClient.queryForList("getSaleGiveRuleList", promationId);
	}

}
