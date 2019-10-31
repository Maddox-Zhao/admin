package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AccountDao;
import com.huaixuan.network.biz.domain.account.Account;

/**
 */
@Service("accountDaoAdmin")
public class AccountDaoiBatis extends PayAccountGenericDaoiBatis<Account, Long> implements AccountDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	// public AccountDaoiBatis() {
	// super(Account.class);
	// }
	//
	// /**
	// * �õ��˻���countֵ
	// * @return
	// * @throws Exception
	// */
	// public Integer getAccountCount() {
	//
	// return (Integer) sqlMapClientTemplate.queryForObject("getAccountCount");
	// }
	//
	// /**
	// * �õ��ڲ��˻�ĳ��������
	// * @param map
	// * @return
	// * @throws Exception
	// */
	// public Integer getSubAccountCount(Map map) {
	// return (Integer) sqlMapClientTemplate.queryForObject("getSubAccountCount", map);
	// }
	//
	// /**
	// * �õ�ĳ��˾�˻����˻�����
	// * @return
	// */
	// public Integer getCompayAccountCount(String accountNo) {
	// return (Integer) sqlMapClientTemplate.queryForObject("getCompayAccountCount",
	// accountNo);
	//
	// }
	//
	// /**
	// * account��ҳ
	// * @param currentPage
	// * @param pageSize
	// * @param totalCount
	// * @return
	// * @throws Exception
	// */
	// public PageUtil<Account> getAccountPage(int currentPage, int pageSize, int totalCount) {
	// Page page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(totalCount);
	// page.setCurrentPage(currentPage);
	//
	// List<Account> list = sqlMapClientTemplate.queryForList("getAccounts", "",
	// page.getPageStartRow() - 1, page.getPageSize());
	// return new PageUtil<Account>(list, page);
	// }
	//
	// /**
	// * �õ��û����seqֵ
	// *
	// * @return
	// * @throws Exception
	// */
	// public Long getUserSeq() {
	// return (Long) sqlMapClientTemplate.queryForObject("getUsersSeq");
	//
	// }
	//
	// /**
	// * �����˻�
	// *
	// * @param account
	// * @throws Exception
	// */
	// public void addAccount(Account account) {
	// sqlMapClientTemplate.insert("addAccount", account);
	//
	// }
	//
	// /**
	// * �޸��˻�
	// *
	// * @param account
	// * @throws Exception
	// */
	// public void editAccount(Account account) {
	// sqlMapClientTemplate.update("editAccount", account);
	// }
	//
	// /**
	// * ɾ���˻�
	// *
	// * @param accountId
	// * @throws Exception
	// */
	// public void removeAccount(Long accountId) {
	// sqlMapClientTemplate.delete("removeAccount", accountId);
	// }
	//
	// /**
	// * �����˻��ŵõ��˺�ʵ��
	// *
	// * @param accountId
	// * @return
	// * @throws Exception
	// */
	// public Account getAccount(Long accountId) {
	// return (Account) sqlMapClientTemplate.queryForObject("getAccount", accountId);
	// }
	//
	// /**
	// * �õ������˺ţ�����List��װ����
	// *
	// * @return
	// * @throws Exception
	// */
	// public List<Account> getAccounts() {
	// return sqlMapClientTemplate.queryForList("getAccounts", null);
	// }
	//
	// /**
	// * ���ݲ�ѯ����ȡ�ÿͻ�account
	// * @param searchCondition
	// * @param page
	// * @return
	// */
	// public PageUtil<Account> getAccountsByCondition(Map<String, String> searchCondition, Page page) {
	// List<Account> list = this.findQueryPage("account-searchAccountByCondition-query",
	// "account-searchAccountByCondition-count", searchCondition, page);
	// return new PageUtil(list, page);
	// }
	//
	// /**
	// * �����ʻ��Ų鿴�˻����䶯��ˮ
	// * @param acount
	// * @param page
	// * @return
	// * @see
	// com.hundsun.bible.dao.account.AccountDao#getAccountLogsByAccountNo(com.hundsun.bible.domain.model.account.Account,
	// com.hundsun.bible.Page)
	// */
	// public PageUtil<Map> getAccountLogsByAccountNo(Map<String, String> searchMap, Page page) {
	// List<Account> list = this.findQueryPage("accountLog-searchLogByAccountNo-query",
	// "accountLog-searchLogByAccountNo-count", searchMap, page);
	// return new PageUtil(list, page);
	// }
	//
	// /**
	// * �����ʻ��Ų鿴�˻����䶯��ˮ
	// * @param searchMap
	// * @param page
	// * @return
	// * @see com.hundsun.bible.dao.account.AccountDao#getAccountLogsByCondition(java.util.Map, com.hundsun.bible.Page)
	// */
	// public PageUtil<Map> getAccountLogsByUserId(Map<String, String> searchMap, Page page) {
	// List<Account> list = this.findQueryPage("accountLog-searchLogByAccountNo-query",
	// "accountLog-searchLogByAccountNo-count", searchMap, page);
	// return new PageUtil(list, page);
	// }
	//
	// /**
	// * �����ʻ��Ų鿴�˻���֤��䶯��ˮ
	// * @param searchMap
	// * @param page
	// * @return
	// * @see com.hundsun.bible.dao.account.AccountDao#getBalanceLogsListByAccountNo(java.util.Map,
	// com.hundsun.bible.Page)
	// */
	// public PageUtil<Map> getBalanceLogsListByAccountNo(Map<String, String> searchMap, Page page) {
	// List<Account> list = this.findQueryPage("freezeAdminBalanceLog-searchLogByAccountNo-query",
	// "freezeAdminBalanceLog-searchLogByAccountNo-count", searchMap, page);
	// return new PageUtil(list, page);
	// }
	//
	// /**
	// * ���ʻ����ж���/�ⶳ֮��Ĳ���
	// * @param account
	// */
	// public void doProcessAccount(Account account) {
	// sqlMapClientTemplate.update("account-processAccount-update", account);
	// }
	//
	// /**
	// * ͨ��accountNo���account
	// * @param AccountNo
	// * @return
	// */
	// public Account getAccountByAccountNo(String AccountNo) {
	// return (Account) sqlMapClientTemplate.queryForObject(
	// "account-getAccountByAccountNo-query", AccountNo);
	// }

	public Account getAccountByUidAndSubtype(long uid, String subType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", uid);
		param.put("accountSubType", subType);
		return (Account) sqlMapClientTemplate.queryForObject("getAccountByUidAndType", param);
	}

	// public long sumTotleFreezeAmountByType(String accountNo, String freezeReason) {
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("accountNo", accountNo);
	// param.put("actionType", "F");
	// param.put("actionReason", freezeReason);
	// Long totalAmout = (Long) sqlMapClientTemplate.queryForObject(
	// "sumAccountFreezeBalance", param);
	// if (totalAmout != null) {
	// return totalAmout.longValue();
	// }
	// return 0;
	// }
	//
	// public long sumTotleUnFreezeAmountByType(String accountNo, String freezeReason) {
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("accountNo", accountNo);
	// param.put("actionType", "W");
	// param.put("actionReason", freezeReason);
	// Long totalAmout = (Long) sqlMapClientTemplate.queryForObject(
	// "sumAccountFreezeBalance", param);
	// if (totalAmout != null) {
	// return totalAmout.longValue();
	// }
	// return 0;
	// }
	//
	// /**
	// * �õ�SEQ_PAYMENT_OFFLINEֵ
	// *
	// * @return
	// * @throws Exception
	// */
	// public Long getPayMentOffLineSeq() {
	// return (Long) sqlMapClientTemplate.queryForObject("getPayMentOffLineSeq");
	// }
	//
	// /**
	// *
	// * @param outOrderNo
	// * @return
	// * @see com.hundsun.bible.dao.acctrans.AccountLogDao#getByOutOrderNo(java.lang.String)
	// */
	// public List<AccountLogDO> getByOutOrderNo(String outOrderNo) {
	// Map map = new HashMap();
	// map.put("transOutOrderNo", outOrderNo);
	// return (List<AccountLogDO>) sqlMapClientTemplate.queryForList(
	// "getBalanceChangeReportByOutOrderNo", map);
	// }
	//
	// /**
	// *
	// * @param beginDate
	// * @param endDate
	// * @return
	// * @see com.hundsun.bible.dao.acctrans.AccountLogDao#getByTransDate(java.lang.String, java.lang.String)
	// */
	// public PageUtil<AccountLogDO> getByTransDate(String beginDate, String endDate, Page page) {
	// Map map = new HashMap();
	// map.put("beginDate", beginDate);
	// map.put("endDate", endDate);
	// //ȡ��count
	// Integer count = (Integer) sqlMapClientTemplate.queryForObject(
	// "getBalanceChangeReportByDateCount", map);
	// if (null == page) {
	// page = new Page();
	// }
	// page.setTotalRowsAmount(count);
	// map.put("startRow", page.getPageStartRow());
	// map.put("endRow", page.getPageEndRow());
	// //ȡ��list
	// List temp = sqlMapClientTemplate
	// .queryForList("getBalanceChangeReportByDate", map);
	// List<AccountLogDO> list = (List<AccountLogDO>) temp;
	// return new PageUtil(list, page);
	// }
	//
	// /**
	// * �õ�С��ĳ������ʱ��������˺�
	// * @param datestart
	// * @return
	// */
	// public List<Account> getAccountCountByCreate(String dateStart, String dateEnd) {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("datestart", dateStart + " 00:00:00");
	// map.put("dateEnd", dateStart + " 23:59:59");
	// return (List<Account>) sqlMapClientTemplate.queryForList(
	// "getAccountCountByCreate", map);
	// }
}
