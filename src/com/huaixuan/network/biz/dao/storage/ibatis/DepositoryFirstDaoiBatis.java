package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;

@Service("depositoryFirstDao")
public class DepositoryFirstDaoiBatis implements DepositoryFirstDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	private Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	public List<DepositoryFirst> getDepositoryFirstListByIds(List<Long> ids) {
		Map<String, List<Long>> param = new HashMap<String, List<Long>>();
		param.put("ids", ids);
		return sqlMapClientTemplate.queryForList("getDepositoryFirstListByIds", param);
	}

	@SuppressWarnings("unchecked")
	public List<DepositoryFirst> getDepositoryFirstList() {
		return sqlMapClientTemplate.queryForList("getDepositoryFirstListByIds", null);
	}

	public DepositoryFirst getDepositoryById(Long id) {
		return (DepositoryFirst) sqlMapClientTemplate.queryForObject("getDepositoryById", id);
	}

	public DepositoryFirst getDepositoryByName(String depFirstName) {
		return (DepositoryFirst) sqlMapClientTemplate.queryForObject("getDepositoryFirstByName", depFirstName);
	}

	public Long insertDepositoryFirst(DepositoryFirst depositryFirst) {
		Long obj = (Long) sqlMapClientTemplate.insert("addDepositoryFirst", depositryFirst);
		return obj;
	}

	public void updateDepositoryFirst(DepositoryFirst depositryFirst) {
		sqlMapClientTemplate.update("editDepositoryFirst", depositryFirst);
	}

	@SuppressWarnings("unchecked")
	public List<DepositoryFirst> getDepositoryFirstListByParMap(Map<String, ?> parMap) {
		if (log.isDebugEnabled()) {
			log.debug("DepositoryFirstDaoiBatis getDepositoryFirstListByParMap");
		}
		return sqlMapClientTemplate.queryForList("getDepositoryFirstListByParMap", parMap);
	}

}
