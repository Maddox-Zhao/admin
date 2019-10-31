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
	 * 得到小于某个创建时间的所有账叄
	 *
	 * @param datestart
	 * @return
	 */
	public List<Account> getAccountCountByCreate(String dateStart,
			String dateEnd);

	/**
	 * 得到用户表的seq倄
	 *
	 * @return
	 * @throws Exception
	 */
	public Long getUserSeq();

	/**
	 * 得到SEQ_PAYMENT_OFFLINE倄
	 *
	 * @return
	 * @throws Exception
	 */
	public Long getPayMentOffLineSeq();

	/**
	 * 得到账户表count倄
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getAccountCount();

	/**
	 * 得到某公司账户的账户个数
	 *
	 * @return
	 */
	public Integer getCompayAccountCount(String accountNo);

	/**
	 * 得到内部账户某子类别个数
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getSubAccountCount(Map map);

	/**
	 * account分页
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
	 * 创建账户
	 *
	 * @param account
	 * @throws Exception
	 */
	void addAccount(Account account);

	/**
	 * 修改账户
	 *
	 * @param account
	 * @throws Exception
	 */
	void editAccount(Account account);

	/**
	 * 删除账户
	 *
	 * @param accountId
	 * @throws Exception
	 */
	void removeAccount(Long accountId);

	/**
	 * 根据账户号得到账号实侄
	 *
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	Account getAccount(Long accountId);

	/**
	 * 得到扄有账号，返回List封装对象
	 *
	 * @return
	 * @throws Exception
	 */
	List<Account> getAccounts();

	/**
	 * 根据查询条件取得客户account
	 *
	 * @param searchCondition
	 * @param page
	 * @return
	 */
	List<Account> getAccountsByCondition(Map<String, String> searchCondition);

	/**
	 * 根据帐户号查看账户金额变动流氄
	 *
	 * @param acount
	 * @param page
	 * @return page
	 */
	public List<Account> getAccountLogsByAccountNo(Map<String, String> searchMap);

	/**
	 * 根据条件查看账户金额变动流水
	 *
	 * @param acount
	 * @param page
	 * @return page
	 */
	public List<Account> getAccountLogsByUserId(Map<String, String> searchMap);

	/**
	 * 根据帐户号查看账户保证金变动流水
	 *
	 * @param acount
	 * @param page
	 * @return page
	 */
	public List<Account> getBalanceLogsListByAccountNo(
			Map<String, String> searchMap);

	/**
	 * 对帐户进行冻组/解冻之类的操佄
	 *
	 * @param account
	 */
	public void doProcessAccount(Account account);

	/**
	 * 根据uid获取相应类型的账戄
	 *
	 * @param uid
	 * @param subType
	 * @return
	 */
	public Account getAccountByUidAndSubtype(long uid, String subType);

	/**
	 * 通过accountNo获得account
	 *
	 * @param AccountNo
	 * @return
	 */
	public Account getAccountByAccountNo(String AccountNo);

	public long sumTotleFreezeAmountByType(String accountNo, String freezeReason);

	public long sumTotleUnFreezeAmountByType(String accountNo,
			String freezeReason);

	/**
	 * 根据弄始时间和结束时间取得列表
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<AccountLogDO> getByTransDate(Map<String, String> searchMap);

	/**
	 * 根据外部订单号取得列衄
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
