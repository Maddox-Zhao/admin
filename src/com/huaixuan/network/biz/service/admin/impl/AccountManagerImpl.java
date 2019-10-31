package com.huaixuan.network.biz.service.admin.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AccountDao;
import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.service.admin.AccountManager;
import com.hundsun.itrans.biz.domain.Enum.EnumSubAccount;

/**
 * 账户相关操作
 *
 * @author guoyj
 * @version $Id: AccountManagerImpl.java,v 0.1 2010-6-5 下午14:25:33 $
 */
@Service("accountManagerAdmin")
public class AccountManagerImpl implements AccountManager {

	protected final Log log = LogFactory.getLog(getClass());

	// public final static String accountNoBegin = "11185"; // 账户开头是5位，规定是11185开头
	//
	// public final static String accountPublicType = "1"; // 账户类型,对公
	//
	// public final static String accountPrivateType = "2"; // 账户类型,对私
	//
	// public final static String accountIneerType = "3"; // 账户类型,内部账户
	//
	// public final static String accountSubTransType = "01"; // 账户子类型,交易账户
	//
	// public final static String accountSubAuctionType = "02"; // 账户子类型,保证金账户
	//
	// public final static String accountSysAccountName = "888888"; // 账户创建者，默认是6个8
	@Autowired
	public AccountDao accountDaoAdmin; // 账户dao

	// public AccountTransManager accountTransManager;
	//
	//
	//
	// private BalanceMd5 balanceMd5;
	//
	// public void setAccountDao(AccountDao accountDao) {
	// this.accountDao = accountDao;
	// }
	//
	// /**
	// * 创建内部账户
	// *
	// * @param accountType
	// * 账户类型 3表是内部账户
	// * @param accountSubType
	// * 账户子类型 01:交易账户，02:保证金账户,03:佣金账户，04:返点账户
	// * @param accountName
	// * 账户创建者
	// * @param memo
	// * 账户备注
	// * @return
	// */
	// public boolean addInnerWebAccount(String accountType, String accountSubType,
	// String accountName, String memo) {
	//
	// //内部户用统一的用户ID，为了提高查找的性能
	// Long userId = Constants.INNER_ACCOUNT_USER_ID; // 得到用户表的seq值，也就是要添加的userId户名
	// //modify over
	// Account accountu = new Account();
	// accountu.setAccountNo(getAccountNO(accountNoBegin, accountIneerType, accountSubType,
	// getAccountNO(String.valueOf(userId)))); // 设置账户号
	// accountu.setUserId(userId); // 设置用户id
	// accountu.setAccountType(Long.parseLong(accountType)); // 设置账户类型
	// accountu.setAccountSubType(accountSubType); // 设置账户子类型
	// accountu.setBalance(Long.valueOf(0)); // 设置账户余额
	// accountu.setFreezeAmount(Long.valueOf(0));// 设置冻结金额
	// accountu.setEnabledStatus("T");// 设置用户状态枚举类型，刚开始注册是T-正常状态
	// accountu.setCreator(accountName); // 设置用户创建者
	// accountu.setModifier(accountName); // 设置用户修改者
	// accountu.setMemo(memo); // 设置账户备注
	// addAccount(accountu); // 创建内部账户
	// return true;
	//
	// }
	//
	// /**
	// * 得到用户表的seq值
	// *
	// * @return
	// */
	// public Long getUserSeq() {
	//
	// return accountDao.getUserSeq();
	//
	// }
	//
	// /**
	// * 创建账户，主要是创建商家账户和顾客账户，用户注册时的调用接口
	// * (说明：由于目前商家只有一个，故商家账号直接数据库初始化即可)
	// *
	// * @param userId
	// * 用户id
	// * @param userType
	// * 用户类型
	// * @return boolean
	// */
	// public boolean addFrontWebAccount(Long userId, String userType) {
	//
	// Account accountu = new Account();
	//
	// accountu.setUserId(userId);// 设置用户id
	// accountu.setBalance(Long.valueOf(0)); // 设置账户余额
	// accountu.setFreezeAmount(Long.valueOf(0));// 设置冻结金额
	// accountu.setEnabledStatus("T");// 设置用户状态枚举类型，刚开始注册是T-正常状态
	// accountu.setCreator(accountSysAccountName); // 设置用户创建者
	// accountu.setModifier(accountSysAccountName); // 设置用户修改者
	// accountu.setMemo(""); // 设置账户备注
	//
	//
	// if (EnumUserType.NORMAL_USER.getKey().equals(userType) || EnumUserType.AGENT_USER.getKey().equals(userType)) //
	// 若是买家注册的话,就要注册2个账户，一个是交易账户，一个是保证金账户
	// {
	// /** *******************买家账户---交易账户*************************************** */
	// accountu.setAccountNo(getAccountNO(accountNoBegin, accountPrivateType,
	// accountSubTransType, String.valueOf(userId))); // 设置账户号
	// accountu.setAccountType(Long.parseLong(accountPrivateType)); // 设置账户类型
	// accountu.setAccountSubType(accountSubTransType); // 设置账户子类型
	// addAccount(accountu); // 创建买家交易账户
	// /** ********************************************************************** */
	//
	// /** *******************买家账户---保证金账户*************************************** */
	// accountu.setAccountNo(getAccountNO(accountNoBegin, accountPrivateType,
	// accountSubAuctionType, String.valueOf(userId))); // 设置账户号
	// accountu.setAccountType(Long.parseLong(accountPrivateType)); // 设置账户类型
	// accountu.setAccountSubType(accountSubAuctionType); // 设置账户子类型
	// addAccount(accountu); // 创建买家保证金账户
	// /** ********************************************************************** */
	// } else
	// //否则传过来的用户类型不匹配直接返回false
	// return false;
	//
	// return true;
	//
	// }
	//
	// /**
	// * 返回用户账号，用户账号=11185+账户类型+子类型+用户Id
	// *
	// * @param accountNoBegin
	// * 账户开头是5位，规定是11185开头
	// * @param accountType
	// * 账户类型，规定是一位
	// * @param accountSubType
	// * 账户子类型，规定是两位
	// * @param userId
	// * 用户Id,规定是12位，不够12位的话左端补零
	// * @return String
	// */
	// public String getAccountNO(String accountNoBegin, String accountType, String accountSubType,
	// String userId) {
	//
	// return accountNoBegin + accountType + accountSubType + getAccountNO(userId);
	// }
	//
	// /**
	// * 返回用户userId,userId为12位，如果不足12位的话就左端补零
	// *
	// * @param userId
	// * @return
	// */
	// public String getAccountNO(String userId) {
	//
	// String str_userId = String.valueOf(userId);
	// String str_zero = "0"; // 左端补零
	// if (str_userId.length() < 12) // 如果用户id是小于12位的话
	// {
	// for (int i = 0; i < 12 - str_userId.length() - 1; i++)
	// // 左端循环补零
	// str_zero = "0" + str_zero; // 要补零的个数
	// str_userId = str_zero + str_userId; // 构造的新的userId
	//
	// }
	//
	// return str_userId;
	//
	// }
	//
	// /**
	// * account分页
	// * @param currentPage
	// * @param pageSize
	// * @return
	// */
	// public PageUtil<Account> getAccountPage(int currentPage, int pageSize) {
	//
	// Integer count = accountDao.getAccountCount();
	// return this.accountDao.getAccountPage(currentPage, pageSize, count);
	//
	// }
	//
	// /**
	// * 创建账户
	// *
	// * @param account
	// */
	// public void addAccount(Account accountDao) {
	// //余额加密
	// String checkSign = balanceMd5.getBalanceMd5Value(accountDao.getUserId(), accountDao
	// .getBalance());
	// accountDao.setCheckSign(checkSign);
	//
	// this.accountDao.addAccount(accountDao);
	//
	// }
	//
	// /**
	// * 得到所有账号，返回List封装对象
	// *
	// * @return
	// */
	// public List<Account> getAccounts() {
	//
	// return this.accountDao.getAccounts();
	//
	// }
	//
	// /**
	// * 判断其内部账户子类别是否重复添加
	// * @param map
	// * @return
	// */
	// public boolean checkAddSubAccount(Map map) {
	//
	// return accountDao.getSubAccountCount(map) > 0;
	//
	// }
	//
	// /**
	// * 根据查询条件取得客户account
	// * @param searchMap
	// * @param page
	// * @return
	// * @see com.hundsun.bible.facade.account.AccountManager#getAccountsByCondition(java.util.Map,
	// com.hundsun.bible.Page)
	// */
	// public PageUtil<Account> getAccountsByCondition(Map<String, String> searchMap, Page page) {
	// return this.accountDao.getAccountsByCondition(searchMap, page);
	// }
	//
	// /**
	// * 根据帐户号查看账户金额变动流水
	// * @param searchMap
	// * @param page
	// * @return
	// * @see com.hundsun.bible.facade.account.AccountManager#getAccountLogsByAccountNo(Account acount,Page page)
	// */
	// public PageUtil<Map> getAccountLogsByAccountNo(Map<String, String> searchMap, Page page) {
	// return this.accountDao.getAccountLogsByAccountNo(searchMap, page);
	// }
	//
	// /**
	// * 根据条件查看账户金额变动流水
	// * @param searchMap
	// * @param page
	// * @return
	// * @see com.hundsun.bible.facade.account.AccountManager#getAccountLogsByCondition(java.util.Map,
	// com.hundsun.bible.Page)
	// */
	// public PageUtil<Map> getAccountLogsByUserId(Map<String, String> searchMap, Page page) {
	// return this.accountDao.getAccountLogsByUserId(searchMap, page);
	// }
	//
	// /**
	// * 根据帐户号查看账户保证金变动流水
	// * @param searchMap
	// * @param page
	// * @return
	// * @see com.hundsun.bible.facade.account.AccountManager#getBalanceLogsListByAccountNo(java.util.Map,
	// com.hundsun.bible.Page)
	// */
	// public PageUtil<Map> getBalanceLogsListByAccountNo(Map<String, String> searchMap, Page page) {
	// return this.accountDao.getBalanceLogsListByAccountNo(searchMap, page);
	// }
	//
	// /**
	// * 对帐户进行冻结/解冻之类的操作
	// * @param account
	// */
	// public void doProcessAccount(Account account) {
	// //如果帐户状态不为空且要将目标帐户冻结
	// if (account.getEnabledStatus() != null
	// && account.getEnabledStatus().equals(EnumAccountStatus.FREEZEN_ACCOUNT.getCode())) {
	// //将对应帐户的提款申请作废掉
	// //withdrawSearchManager.WrongWithdraw(account.getAccountNo());
	// }
	// this.accountDao.doProcessAccount(account);
	// }
	//
	// /**
	// * 通过accountNo获得account
	// * @param AccountNo
	// * @return
	// */
	// public Account getAccountByAccountNo(String AccountNo) {
	// return this.accountDao.getAccountByAccountNo(AccountNo);
	// }
	//
	// /**
	// * 根据页面输入的金额对帐户进行冻结/解冻的操作
	// * @param req
	// * @return
	// */
	// public AccountTransResult doBalanceReq(FreezeBalanceReq req) {
	// return accountTransManager.execute(req);
	// }

	public Account getAccountByUidAndType(long userId, EnumSubAccount accoutType) {
		if (accoutType == null) {
			return null;
		}
		return accountDaoAdmin.getAccountByUidAndSubtype(userId, accoutType.getKey());
	}

	/**
	 * 获取买家交易账户
	 *
	 * @param userId
	 * @return
	 */
	public Account getBuyerTransAccount(long userId) {
		return getAccountByUidAndType(userId, EnumSubAccount.TO_TRANS);
	}

	/**
	 * 获取买家保证金账户
	 *
	 * @param userId
	 * @return
	 */

	public Account getBuyerAuctionAccount(long userId) {
		return getAccountByUidAndType(userId, EnumSubAccount.TO_AUCTION);
	}

	// /**
	// * 获取卖家交易账户(当有多个商户时)
	// * @param userId
	// * @return
	// */
	// public Account getSellerTransAccount(long userId) {
	// return this.getAccountByUidAndType(userId, EnumSubAccount.TO_TRANS);
	// }
	//
	// public Money getSecurityBalance(String accountNo) {
	// //modified by zhengsb 2010-3-10 重构
	// // Money securityMoney = new Money();
	// // //获取该类型冻结金额
	// // long freezeBalance = accountDao.sumTotleFreezeAmountByType(accountNo,
	// // EnumFreezeType.SECURITY_FREEZE.getFreezeReason());
	// // //获取该类型解冻金额
	// // long unFreezeBalance = accountDao.sumTotleUnFreezeAmountByType(accountNo,
	// // EnumFreezeType.SECURITY_UN_FREEZE.getFreezeReason());
	// // long balance = freezeBalance - unFreezeBalance;
	// // securityMoney.setCent(balance);
	// // return securityMoney;
	// //modified over
	// return getSecurityBalanceByType(accountNo, EnumFreezeType.SECURITY_FREEZE,
	// EnumFreezeType.SECURITY_UN_FREEZE);
	// }
	//
	// private Money getSecurityBalanceByType(String accountNo, EnumFreezeType freezeType,
	// EnumFreezeType unfreezeType) {
	// Money securityMoney = new Money();
	// //获取该类型冻结金额
	// long freezeBalance = accountDao.sumTotleFreezeAmountByType(accountNo, freezeType
	// .getFreezeReason());
	// //获取该类型解冻金额
	// long unFreezeBalance = accountDao.sumTotleUnFreezeAmountByType(accountNo, unfreezeType
	// .getFreezeReason());
	// long balance = freezeBalance - unFreezeBalance;
	// securityMoney.setCent(balance);
	// return securityMoney;
	// }
	//
	// public AccountTransManager getAccountTransManager() {
	// return accountTransManager;
	// }
	//
	// public void setAccountTransManager(AccountTransManager accountTransManager) {
	// this.accountTransManager = accountTransManager;
	// }
	//
	//
	//
	//
	//
	// public void setBalanceMd5(BalanceMd5 balanceMd5) {
	// this.balanceMd5 = balanceMd5;
	// }
	//
	// /**
	// * 修改账户初始化金额
	// * @param datestart
	// * @return
	// */
	// public String doInitializeAccount(String datestart, String dateEnd) {
	// String resultString = "";
	// List<Account> accountList = accountDao.getAccountCountByCreate(datestart, dateEnd); //得到所需的所有账户
	// if (accountList == null || accountList.size() <= 0) {
	// return "noAccountFind";
	// }
	//
	// for (Account account : accountList) {
	//
	// resultString = doUpdateAccount(account.getAccountNo(), "0");
	// }
	//
	// return resultString;
	// }
	//
	// /**
	// * 账户金额修改
	// * @param accountNo
	// * @param balance
	// * @return
	// */
	// public String doUpdateAccount(String accountNo, String balance) {
	// AccountTransReq req = new AccountTransReq();
	// req.setSubTransCode(EnumSubTransCode.TXCODE_BALANCE_ENCRYPT);
	// req.setAmount(new Money(balance));
	// req.setAccountNo(accountNo);
	// AccountTransResult result = accountTransManager.execute(req);
	// return result.getCode();
	// }
	//
	//
	// public Money getAuctionSecurityBalance(String accountNo) {
	// return getSecurityBalanceByType(accountNo, EnumFreezeType.AUCTION_FREEZE,
	// EnumFreezeType.AUCTION_UN_FREEZE);
	// }

}
