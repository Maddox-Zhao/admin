package com.huaixuan.network.biz.service.account.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.account.AccountDao;
import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.enums.EnumAccountStatus;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.account.AccountManager;
import com.hundsun.bible.acctrans.util.BalanceMd5;
import com.hundsun.itrans.biz.domain.Enum.EnumFreezeType;
import com.hundsun.itrans.biz.domain.Enum.EnumSubAccount;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;
import com.hundsun.itrans.biz.manager.AccountTransManager;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.biz.model.FreezeBalanceReq;
import com.hundsun.itrans.common.util.Money;

/**
 * 
 *
 * @author guoyj
 * @version $Id: AccountManagerImpl.java,v 0.1 2010-6-5 14:25:33 $
 */
@Service("accountManager")
public class AccountManagerImpl implements AccountManager {

	protected final Log log = LogFactory.getLog(getClass());

	public final static String accountNoBegin = "11185"; // 账户�??头是5位，规定�??185�??�??

	public final static String accountPublicType = "1"; // 账户类型,对公

	public final static String accountPrivateType = "2"; // 账户类型,对�F

	public final static String accountIneerType = "3"; // 账户类型,内部账户

	public final static String accountSubTransType = "01"; // 账户子类�??,交易账户

	public final static String accountSubAuctionType = "02"; // 账户子类�??,保证金账�??

	public final static String accountSysAccountName = "888888"; // 账户创建者，默认�????

	@Autowired
	AccountDao accountDao; // 账户dao

	@Autowired
	AccountTransManager accountTransManager;

	private BalanceMd5 balanceMd5;

	/**
	 * 创建内部账户
	 *
	 * @param accountType
	 *            账户类型 3表是内部账户
	 * @param accountSubType
	 *            账户子类�?? 01:交易账户�??:保证金账�??,03:佣金账户�??:返点账户
	 * @param accountName
	 *            账户创建�??
	 * @param memo
	 *            账户备注
	 * @return
	 */
	@Override
	public boolean addInnerWebAccount(String accountType,
			String accountSubType, String accountName, String memo) {

		// 内部户用统一的用户ID，为了提高查找的性能
		Long userId = Constants.INNER_ACCOUNT_USER_ID; // 得到用户表的seq值，也就是�a添加的userId户名
		// modify over
		Account accountu = new Account();
		accountu.setAccountNo(getAccountNO(accountNoBegin, accountIneerType,
				accountSubType, getAccountNO(String.valueOf(userId)))); // 设置账户�??
		accountu.setUserId(userId); // 设置用户id
		accountu.setAccountType(Long.parseLong(accountType)); // 设置账户类型
		accountu.setAccountSubType(accountSubType); // 设置账户子类�??
		accountu.setBalance(Long.valueOf(0)); // 设置账户余额
		accountu.setFreezeAmount(Long.valueOf(0));// 设置冻结金额
		accountu.setEnabledStatus("T");// 设置用户状�??�枚举类型，刚开始注册是T-正常状�????
		accountu.setCreator(accountName); // 设置用户创建�??
		accountu.setModifier(accountName); // 设置用户修改�??
		accountu.setMemo(memo); // 设置账户备注
		addAccount(accountu); // 创建内部账户
		return true;

	}

	/**
	 * 得到用户表的seq�??
	 *
	 * @return
	 */
	@Override
	public Long getUserSeq() {

		return accountDao.getUserSeq();

	}

	/**
	 * 创建账户，主要是创建商家账户和顾客账户，用户注册时的调用接口
	 * (说明：由于目前商家只有一个，故商家账号直接数据库初�P化即�??)
	 *
	 * @param userId
	 *            用户id
	 * @param userType
	 *            用户类型
	 * @return boolean
	 */
	@Override
	public boolean addFrontWebAccount(Long userId, String userType) {

		Account accountu = new Account();

		accountu.setUserId(userId);// 设置用户id
		accountu.setBalance(Long.valueOf(0)); // 设置账户余额
		accountu.setFreezeAmount(Long.valueOf(0));// 设置冻结金额
		accountu.setEnabledStatus("T");// 设置用户状�??�枚举类型，刚开始注册是T-正常状�????
		accountu.setCreator(accountSysAccountName); // 设置用户创建�??
		accountu.setModifier(accountSysAccountName); // 设置用户修改�??
		accountu.setMemo(""); // 设置账户备注

		if (EnumUserType.NORMAL_USER.getKey().equals(userType)
				|| EnumUserType.AGENT_USER.getKey().equals(userType)) // 若是买家注册的话,就�a注册2个账户，�??个是交易账户，一个是保证金账�??
		{
			/**
			 * *******************买家账户---交易账户*******************************
			 * ********
			 */
			accountu.setAccountNo(getAccountNO(accountNoBegin,
					accountPrivateType, accountSubTransType,
					String.valueOf(userId))); // 设置账户�??
			accountu.setAccountType(Long.parseLong(accountPrivateType)); // 设置账户类型
			accountu.setAccountSubType(accountSubTransType); // 设置账户子类�??
			addAccount(accountu); // 创建买家交易账户
			/**
			 * *****************************************************************
			 * *****
			 */

			/**
			 * *******************买家账户---保证金账�??*****************************
			 * **********
			 */
			accountu.setAccountNo(getAccountNO(accountNoBegin,
					accountPrivateType, accountSubAuctionType,
					String.valueOf(userId))); // 设置账户�??
			accountu.setAccountType(Long.parseLong(accountPrivateType)); // 设置账户类型
			accountu.setAccountSubType(accountSubAuctionType); // 设置账户子类�??
			addAccount(accountu); // 创建买家保证金账�??
			/**
			 * *****************************************************************
			 * *****
			 */
		} else
			// 否则传过来的用户类型不匹配直接返回false
			return false;

		return true;

	}

	/**
	 * 返回用户账号，用户账�??11185+账户类型+子类�??+用户Id
	 *
	 * @param accountNoBegin
	 *            账户�??头是5位，规定�??185�??�??
	 * @param accountType
	 *            账户类型，�I定是�??�??
	 * @param accountSubType
	 *            账户子类型，规定是两�??
	 * @param userId
	 *            用户Id,规定�??位，不夓12位的话左端补�??
	 * @return String
	 */
	@Override
	public String getAccountNO(String accountNoBegin, String accountType,
			String accountSubType, String userId) {

		return accountNoBegin + accountType + accountSubType
				+ getAccountNO(userId);
	}

	/**
	 * 返回用户userId,userId�??位，如果不足12位的话就左端补零
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public String getAccountNO(String userId) {

		String str_userId = String.valueOf(userId);
		String str_zero = "0"; // 左端补零
		if (str_userId.length() < 12) // 如果用户id是小�??位的�??
		{
			for (int i = 0; i < 12 - str_userId.length() - 1; i++)
				// 左端循环补零
				str_zero = "0" + str_zero; // 要补零的个数
			str_userId = str_zero + str_userId; // 构�??�的新的userId

		}

		return str_userId;

	}

	/**
	 * account分页
	 *
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public QueryPage getAccountPage(int currentPage, int pageSize) {
		Account acc = new Account();
		QueryPage queryPage = new QueryPage(acc);
		Map pramas = queryPage.getParameters();
		Integer count = accountDao.getAccountCount();

		if (count != null && count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currentPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<Account> accList = accountDao.getAccountPage(count,
					currentPage, pageSize);
			if (accList != null && accList.size() > 0) {
				queryPage.setItems(accList);
			}
		}
		return queryPage;
	}

	/**
	 * 创建账户
	 *
	 * @param account
	 */
	@Override
	public void addAccount(Account accountDao) {
		// 余额加密
		String checkSign = balanceMd5.getBalanceMd5Value(
				accountDao.getUserId(), accountDao.getBalance());
		accountDao.setCheckSign(checkSign);

		this.accountDao.addAccount(accountDao);

	}

	/**
	 * 得到�??有账号，返回List封�x对象
	 *
	 * @return
	 */
	@Override
	public List<Account> getAccounts() {

		return this.accountDao.getAccounts();

	}

	/**
	 * 判断其内部账户子类别是否重夁添加
	 *
	 * @param map
	 * @return
	 */
	@Override
	public boolean checkAddSubAccount(Map map) {

		return accountDao.getSubAccountCount(map) > 0;

	}

	/**
	 * 根据查询条件取得客户account
	 *
	 * @param searchMap
	 * @param page
	 * @return
	 * @see com.hundsun.bible.facade.account.AccountManager#getAccountsByCondition(java.util.Map,
	 *      com.hundsun.bible.Page)
	 */
	@Override
	public QueryPage getAccountsByCondition(Map searchMap,
			int currentPage, int pageSize) {

		Account acc = new Account();
		QueryPage queryPage = new QueryPage(acc);
		Integer count = accountDao.getAccountsByConditionCount(searchMap);

		if (count != null && count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currentPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			searchMap.put("startRow",queryPage.getPageFristItem());
			searchMap.put("endRow", queryPage.getPageLastItem());

			List<Account> accList = accountDao
					.getAccountsByCondition(searchMap);
			if (accList != null && accList.size() > 0) {
				queryPage.setItems(accList);
			}
		}
		return queryPage;
	}

	/**
	 * 根据帐户号查看账户金额变动流�??
	 *
	 * @param searchMap
	 * @param page
	 * @return
	 */
	@Override
	public QueryPage getAccountLogsByAccountNo(Map searchMap,
			int currentPage, int pageSize) {
		Account acc = new Account();
		QueryPage queryPage = new QueryPage(acc);
		Integer count = accountDao.getAccountLogsByAccountNoCount(searchMap);

		if (count != null && count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currentPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			searchMap.put("startRow",queryPage.getPageFristItem());
			searchMap.put("endRow", queryPage.getPageLastItem());

			List<Account> accList = accountDao
					.getAccountLogsByAccountNo(searchMap);
			if (accList != null && accList.size() > 0) {
				queryPage.setItems(accList);
			}
		}
		return queryPage;
	}

	/**
	 * 根据条件查看账户金额变动流水
	 *
	 * @param searchMap
	 * @param page
	 * @return
	 * @see com.hundsun.bible.facade.account.AccountManager#getAccountLogsByCondition(java.util.Map,
	 *      com.hundsun.bible.Page)
	 */
	@Override
	public QueryPage getAccountLogsByUserId(Map searchMap,
			int currentPage, int pageSize) {
		Account acc = new Account();
		QueryPage queryPage = new QueryPage(acc);
		Integer count = accountDao.getAccountLogsByUserIdCount(searchMap);

		if (count != null && count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currentPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			searchMap.put("startRow",queryPage.getPageFristItem());
			searchMap.put("endRow", queryPage.getPageLastItem());

			List<Account> accList = accountDao
					.getAccountLogsByUserId(searchMap);
			if (accList != null && accList.size() > 0) {
				queryPage.setItems(accList);
			}
		}
		return queryPage;
	}

	/**
	 * 根据帐户号查看账户保证金变动流水
	 *
	 * @param searchMap
	 * @param page
	 * @return
	 * @see com.hundsun.bible.facade.account.AccountManager#getBalanceLogsListByAccountNo(java.util.Map,
	 *      com.hundsun.bible.Page)
	 */
	@Override
	public QueryPage getBalanceLogsListByAccountNo(
			Map searchMap, int currentPage, int pageSize) {
		Account acc = new Account();
		QueryPage queryPage = new QueryPage(acc);
		Integer count = accountDao
				.getBalanceLogsListByAccountNoCount(searchMap);

		if (count != null && count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currentPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			searchMap.put("startRow",queryPage.getPageFristItem());
			searchMap.put("endRow", queryPage.getPageLastItem());

			List<Account> accList = accountDao
					.getBalanceLogsListByAccountNo(searchMap);
			if (accList != null && accList.size() > 0) {
				queryPage.setItems(accList);
			}
		}
		return queryPage;
	}

	/**
	 * 对帐户进行冻�??/解冻之类的操�??
	 *
	 * @param account
	 */
	@Override
	public void doProcessAccount(Account account) {
		// 如果帐户状�??�不为空且�a将目标帐户冻�??
		if (account.getEnabledStatus() != null
				&& account.getEnabledStatus().equals(
						EnumAccountStatus.FREEZEN_ACCOUNT.getCode())) {
			// 将对应帐户的提款申请作废�??
			// withdrawSearchManager.WrongWithdraw(account.getAccountNo());
		}
		this.accountDao.doProcessAccount(account);
	}

	/**
	 * 通过accountNo获得account
	 *
	 * @param AccountNo
	 * @return
	 */
	@Override
	public Account getAccountByAccountNo(String AccountNo) {
		return this.accountDao.getAccountByAccountNo(AccountNo);
	}

	/**
	 * 根据页面输入的金额对帐户进行冻结/解冻的操�??
	 *
	 * @param req
	 * @return
	 */
	@Override
	public AccountTransResult doBalanceReq(FreezeBalanceReq req) {
		return accountTransManager.execute(req);
	}

	@Override
	public Account getAccountByUidAndType(long userId, EnumSubAccount accoutType) {
		if (accoutType == null) {
			return null;
		}
		return accountDao
				.getAccountByUidAndSubtype(userId, accoutType.getKey());
	}

	/**
	 * 获取买家交易账户
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Account getBuyerTransAccount(long userId) {
		return this.getAccountByUidAndType(userId, EnumSubAccount.TO_TRANS);
	}

	/**
	 * 获取买家保证金账�??
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Account getBuyerAuctionAccount(long userId) {
		return this.getAccountByUidAndType(userId, EnumSubAccount.TO_AUCTION);
	}

	/**
	 * 获取卖家交易账户(当有多个商户�??)
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Account getSellerTransAccount(long userId) {
		return this.getAccountByUidAndType(userId, EnumSubAccount.TO_TRANS);
	}

	@Override
	public Money getSecurityBalance(String accountNo) {
		return getSecurityBalanceByType(accountNo,
				EnumFreezeType.SECURITY_FREEZE,
				EnumFreezeType.SECURITY_UN_FREEZE);
	}

	private Money getSecurityBalanceByType(String accountNo,
			EnumFreezeType freezeType, EnumFreezeType unfreezeType) {
		Money securityMoney = new Money();
		// 获取该类型冻结金�??
		long freezeBalance = accountDao.sumTotleFreezeAmountByType(accountNo,
				freezeType.getFreezeReason());
		// 获取该类型解冻金�??
		long unFreezeBalance = accountDao.sumTotleUnFreezeAmountByType(
				accountNo, unfreezeType.getFreezeReason());
		long balance = freezeBalance - unFreezeBalance;
		securityMoney.setCent(balance);
		return securityMoney;
	}

	/**
	 * 修改账户初�P化金�??
	 *
	 * @param datestart
	 * @return
	 */
	@Override
	public String doInitializeAccount(String datestart, String dateEnd) {
		String resultString = "";
		List<Account> accountList = accountDao.getAccountCountByCreate(
				datestart, dateEnd); // 得到�??�??的所有账�??
		if (accountList == null || accountList.size() <= 0) {
			return "noAccountFind";
		}

		for (Account account : accountList) {

			resultString = doUpdateAccount(account.getAccountNo(), "0");
		}

		return resultString;
	}

	/**
	 * 账户金额修改
	 *
	 * @param accountNo
	 * @param balance
	 * @return
	 */
	@Override
	public String doUpdateAccount(String accountNo, String balance) {
		AccountTransReq req = new AccountTransReq();
		req.setSubTransCode(EnumSubTransCode.TXCODE_BALANCE_ENCRYPT);
		req.setAmount(new Money(balance));
		req.setAccountNo(accountNo);
		AccountTransResult result = accountTransManager.execute(req);
		return result.getCode();
	}

	@Override
	public Money getAuctionSecurityBalance(String accountNo) {
		return getSecurityBalanceByType(accountNo,
				EnumFreezeType.AUCTION_FREEZE, EnumFreezeType.AUCTION_UN_FREEZE);
	}

}
