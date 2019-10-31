package com.huaixuan.network.biz.dao.account;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.account.Account;
import com.hundsun.itrans.domain.base.AccountLogDO;

/**
 * guoyj
 */
public interface AccountDao {

	/**
	 * �õ�С��ĳ������ʱ��������˅�
	 *
	 * @param datestart
	 * @return
	 */
	public List<Account> getAccountCountByCreate(String dateStart,
			String dateEnd);

	/**
	 * �õ��û����seq�x
	 *
	 * @return
	 * @throws Exception
	 */
	public Long getUserSeq();

	/**
	 * �õ�SEQ_PAYMENT_OFFLINE�x
	 *
	 * @return
	 * @throws Exception
	 */
	public Long getPayMentOffLineSeq();

	/**
	 * �õ��˻���count�x
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getAccountCount();

	/**
	 * �õ�ĳ��˾�˻����˻�����
	 *
	 * @return
	 */
	public Integer getCompayAccountCount(String accountNo);

	/**
	 * �õ��ڲ��˻�ĳ��������
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getSubAccountCount(Map map);

	/**
	 * account��ҳ
	 *
	 * @param currentPage
	 * @param pageSize
	 * @param totalCount
	 * @return
	 * @throws Exception
	 */
	public List<Account> getAccountPage(int currentPage, int pageSize,
			int totalCount);

	/**
	 * �����˻�
	 *
	 * @param account
	 * @throws Exception
	 */
	void addAccount(Account account);

	/**
	 * �޸��˻�
	 *
	 * @param account
	 * @throws Exception
	 */
	void editAccount(Account account);

	/**
	 * ɾ���˻�
	 *
	 * @param accountId
	 * @throws Exception
	 */
	void removeAccount(Long accountId);

	/**
	 * �����˻��ŵõ��˺�ʵֶ
	 *
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	Account getAccount(Long accountId);

	/**
	 * �õ������˺ţ�����List��װ����
	 *
	 * @return
	 * @throws Exception
	 */
	List<Account> getAccounts();

	/**
	 * ���ݲ�ѯ����ȡ�ÿͻ�account
	 *
	 * @param searchCondition
	 * @param page
	 * @return
	 */
	List<Account> getAccountsByCondition(Map<String, String> searchCondition);

	/**
	 * �����ʻ��Ų鿴�˻����䶯����
	 *
	 * @param acount
	 * @param page
	 * @return page
	 */
	public List<Account> getAccountLogsByAccountNo(Map<String, String> searchMap);

	/**
	 * ���������鿴�˻����䶯��ˮ
	 *
	 * @param acount
	 * @param page
	 * @return page
	 */
	public List<Account> getAccountLogsByUserId(Map<String, String> searchMap);

	/**
	 * �����ʻ��Ų鿴�˻���֤��䶯��ˮ
	 *
	 * @param acount
	 * @param page
	 * @return page
	 */
	public List<Account> getBalanceLogsListByAccountNo(
			Map<String, String> searchMap);

	/**
	 * ���ʻ����ж���/�ⶳ֮��Ĳف�
	 *
	 * @param account
	 */
	public void doProcessAccount(Account account);

	/**
	 * ����uid��ȡ��Ӧ���͵��ˑ�
	 *
	 * @param uid
	 * @param subType
	 * @return
	 */
	public Account getAccountByUidAndSubtype(long uid, String subType);

	/**
	 * ͨ��accountNo���account
	 *
	 * @param AccountNo
	 * @return
	 */
	public Account getAccountByAccountNo(String AccountNo);

	public long sumTotleFreezeAmountByType(String accountNo, String freezeReason);

	public long sumTotleUnFreezeAmountByType(String accountNo,
			String freezeReason);

	/**
	 * ����Ūʼʱ��ͽ���ʱ��ȡ���б�
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<AccountLogDO> getByTransDate(Map<String, String> searchMap);

	/**
	 * �����ⲿ������ȡ������
	 *
	 * @param outOrderNo
	 * @return
	 */
	public List<AccountLogDO> getByOutOrderNo(String outOrderNo);

	Integer getAccountsByConditionCount(Map map);

	public Integer getAccountLogsByAccountNoCount(Map<String, String> searchMap);

	Integer getAccountLogsByUserIdCount(Map map);

	Integer getBalanceLogsListByAccountNoCount(Map map);
}
