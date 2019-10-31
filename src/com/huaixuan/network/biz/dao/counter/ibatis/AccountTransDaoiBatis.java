package com.huaixuan.network.biz.dao.counter.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.AccountTransDao;
import com.huaixuan.network.biz.domain.TransLogApp;
import com.huaixuan.network.biz.domain.counter.TransApp;
import com.hundsun.itrans.domain.base.TransLogDO;

@Service("accountTransDao")
public class AccountTransDaoiBatis implements AccountTransDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	/**
	 * ����������ˮIDȡ�øñ�������ˮ������Ϣ
	 *
	 * @param accountLogId
	 * @return
	 * @see com.hundsun.bible.dao.acctrans.TransLogDao#getTransLogByAccountLogId(long)
	 */
	@Override
	public TransLogDO getTransLogByAccountLogId(long accountLogId) {
		return (TransLogDO) this.sqlMapClientTemplate.queryForObject(
				"transLog-getTransLog-byAccountLogId-query", accountLogId);
	}

	/**
	 * ����transLogIdȡ��transLog
	 *
	 * @param transLogId
	 * @return
	 * @see com.hundsun.bible.dao.acctrans.TransLogDao#getTransLogById(long)
	 */
	@Override
	public TransLogDO getTransLogById(long transLogId) {
		return (TransLogDO) this.sqlMapClientTemplate.queryForObject(
				"transLog-getTransLog-byId-query", transLogId);
	}

	@Override
	public Long addTransApp(TransApp transApp) {
		Long id = (Long) this.sqlMapClientTemplate.insert("addTransApp",
				transApp);
		return id;
	}

	/**
	 * ͳ����������������
	 *
	 * @param parMap
	 * @return
	 */
	@Override
	public int getManagerTransAppCount(Map parMap) {
		Integer count = (Integer) this.sqlMapClientTemplate.queryForObject(
				"getManagerTransAppCount", parMap);
		return count == null ? 0 : count.intValue();
	}

	/**
	 * �����������б�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	// public List<TransApp> getManagerTransApp(Map parMap, Page page) {
	// if (page == null) {
	// return this.sqlMapClientTemplate.queryForList("getManagerTransApp",
	// parMap);
	// } else {
	// return this.findQueryPage("getManagerTransApp", parMap, page);
	// }
	// }
	@Override
	public TransApp getTransAppInfo(Map parMap) {
		return (TransApp) this.sqlMapClientTemplate.queryForObject(
				"getTransAppInfo", parMap);
	}

	@Override
	public int updateTransAppInfo(Map parMap) {
		return this.sqlMapClientTemplate.update("updateTransAppInfo", parMap);
	}

	@Override
	public TransLogApp getTransLogAppInfo(Map parMap) {
		return (TransLogApp) this.sqlMapClientTemplate.queryForObject(
				"getTransLogAppInfo", parMap);
	}

	@Override
	public long addTransLogApp(TransLogApp transLogApp) {
		Long transLogId = (Long) this.sqlMapClientTemplate.insert(
				"addTransLogApp", transLogApp);
		return transLogId == null ? 0 : transLogId.longValue();
	}

	/**
	 * ͳ�����������������
	 *
	 * @param parMap
	 * @return
	 */
	@Override
	public int getManagerTransLogAppCount(Map parMap) {
		Integer count = (Integer) this.sqlMapClientTemplate.queryForObject(
				"getManagerTransLogAppCount", parMap);
		return count == null ? 0 : count.intValue();
	}

	/**
	 * ������������б�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	// public List<TransLogApp> getManagerTransLogApp(Map parMap, Page page) {
	// if (page == null) {
	// return this.sqlMapClientTemplate.queryForList("getManagerTransLogApp",
	// parMap);
	// } else {
	// return this.findQueryPage("getManagerTransLogApp", parMap, page);
	// }
	// }
	@Override
	public int updateTransLogAppInfo(Map parMap) {
		return this.sqlMapClientTemplate
				.update("updateTransLogAppInfo", parMap);
	}

	@Override
	public List<TransApp> getManagerTransApp(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getManagerTransApp",
				parMap);
	}

	@Override
	public List<TransLogApp> getManagerTransLogApp(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getManagerTransLogApp",
				parMap);
	}
}
