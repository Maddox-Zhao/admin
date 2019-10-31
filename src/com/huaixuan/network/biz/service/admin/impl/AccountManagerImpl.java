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
 * �˻���ز���
 *
 * @author guoyj
 * @version $Id: AccountManagerImpl.java,v 0.1 2010-6-5 ����14:25:33 $
 */
@Service("accountManagerAdmin")
public class AccountManagerImpl implements AccountManager {

	protected final Log log = LogFactory.getLog(getClass());

	// public final static String accountNoBegin = "11185"; // �˻���ͷ��5λ���涨��11185��ͷ
	//
	// public final static String accountPublicType = "1"; // �˻�����,�Թ�
	//
	// public final static String accountPrivateType = "2"; // �˻�����,��˽
	//
	// public final static String accountIneerType = "3"; // �˻�����,�ڲ��˻�
	//
	// public final static String accountSubTransType = "01"; // �˻�������,�����˻�
	//
	// public final static String accountSubAuctionType = "02"; // �˻�������,��֤���˻�
	//
	// public final static String accountSysAccountName = "888888"; // �˻������ߣ�Ĭ����6��8
	@Autowired
	public AccountDao accountDaoAdmin; // �˻�dao

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
	// * �����ڲ��˻�
	// *
	// * @param accountType
	// * �˻����� 3�����ڲ��˻�
	// * @param accountSubType
	// * �˻������� 01:�����˻���02:��֤���˻�,03:Ӷ���˻���04:�����˻�
	// * @param accountName
	// * �˻�������
	// * @param memo
	// * �˻���ע
	// * @return
	// */
	// public boolean addInnerWebAccount(String accountType, String accountSubType,
	// String accountName, String memo) {
	//
	// //�ڲ�����ͳһ���û�ID��Ϊ����߲��ҵ�����
	// Long userId = Constants.INNER_ACCOUNT_USER_ID; // �õ��û����seqֵ��Ҳ����Ҫ��ӵ�userId����
	// //modify over
	// Account accountu = new Account();
	// accountu.setAccountNo(getAccountNO(accountNoBegin, accountIneerType, accountSubType,
	// getAccountNO(String.valueOf(userId)))); // �����˻���
	// accountu.setUserId(userId); // �����û�id
	// accountu.setAccountType(Long.parseLong(accountType)); // �����˻�����
	// accountu.setAccountSubType(accountSubType); // �����˻�������
	// accountu.setBalance(Long.valueOf(0)); // �����˻����
	// accountu.setFreezeAmount(Long.valueOf(0));// ���ö�����
	// accountu.setEnabledStatus("T");// �����û�״̬ö�����ͣ��տ�ʼע����T-����״̬
	// accountu.setCreator(accountName); // �����û�������
	// accountu.setModifier(accountName); // �����û��޸���
	// accountu.setMemo(memo); // �����˻���ע
	// addAccount(accountu); // �����ڲ��˻�
	// return true;
	//
	// }
	//
	// /**
	// * �õ��û����seqֵ
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
	// * �����˻�����Ҫ�Ǵ����̼��˻��͹˿��˻����û�ע��ʱ�ĵ��ýӿ�
	// * (˵��������Ŀǰ�̼�ֻ��һ�������̼��˺�ֱ�����ݿ��ʼ������)
	// *
	// * @param userId
	// * �û�id
	// * @param userType
	// * �û�����
	// * @return boolean
	// */
	// public boolean addFrontWebAccount(Long userId, String userType) {
	//
	// Account accountu = new Account();
	//
	// accountu.setUserId(userId);// �����û�id
	// accountu.setBalance(Long.valueOf(0)); // �����˻����
	// accountu.setFreezeAmount(Long.valueOf(0));// ���ö�����
	// accountu.setEnabledStatus("T");// �����û�״̬ö�����ͣ��տ�ʼע����T-����״̬
	// accountu.setCreator(accountSysAccountName); // �����û�������
	// accountu.setModifier(accountSysAccountName); // �����û��޸���
	// accountu.setMemo(""); // �����˻���ע
	//
	//
	// if (EnumUserType.NORMAL_USER.getKey().equals(userType) || EnumUserType.AGENT_USER.getKey().equals(userType)) //
	// �������ע��Ļ�,��Ҫע��2���˻���һ���ǽ����˻���һ���Ǳ�֤���˻�
	// {
	// /** *******************����˻�---�����˻�*************************************** */
	// accountu.setAccountNo(getAccountNO(accountNoBegin, accountPrivateType,
	// accountSubTransType, String.valueOf(userId))); // �����˻���
	// accountu.setAccountType(Long.parseLong(accountPrivateType)); // �����˻�����
	// accountu.setAccountSubType(accountSubTransType); // �����˻�������
	// addAccount(accountu); // ������ҽ����˻�
	// /** ********************************************************************** */
	//
	// /** *******************����˻�---��֤���˻�*************************************** */
	// accountu.setAccountNo(getAccountNO(accountNoBegin, accountPrivateType,
	// accountSubAuctionType, String.valueOf(userId))); // �����˻���
	// accountu.setAccountType(Long.parseLong(accountPrivateType)); // �����˻�����
	// accountu.setAccountSubType(accountSubAuctionType); // �����˻�������
	// addAccount(accountu); // ������ұ�֤���˻�
	// /** ********************************************************************** */
	// } else
	// //���򴫹������û����Ͳ�ƥ��ֱ�ӷ���false
	// return false;
	//
	// return true;
	//
	// }
	//
	// /**
	// * �����û��˺ţ��û��˺�=11185+�˻�����+������+�û�Id
	// *
	// * @param accountNoBegin
	// * �˻���ͷ��5λ���涨��11185��ͷ
	// * @param accountType
	// * �˻����ͣ��涨��һλ
	// * @param accountSubType
	// * �˻������ͣ��涨����λ
	// * @param userId
	// * �û�Id,�涨��12λ������12λ�Ļ���˲���
	// * @return String
	// */
	// public String getAccountNO(String accountNoBegin, String accountType, String accountSubType,
	// String userId) {
	//
	// return accountNoBegin + accountType + accountSubType + getAccountNO(userId);
	// }
	//
	// /**
	// * �����û�userId,userIdΪ12λ���������12λ�Ļ�����˲���
	// *
	// * @param userId
	// * @return
	// */
	// public String getAccountNO(String userId) {
	//
	// String str_userId = String.valueOf(userId);
	// String str_zero = "0"; // ��˲���
	// if (str_userId.length() < 12) // ����û�id��С��12λ�Ļ�
	// {
	// for (int i = 0; i < 12 - str_userId.length() - 1; i++)
	// // ���ѭ������
	// str_zero = "0" + str_zero; // Ҫ����ĸ���
	// str_userId = str_zero + str_userId; // ������µ�userId
	//
	// }
	//
	// return str_userId;
	//
	// }
	//
	// /**
	// * account��ҳ
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
	// * �����˻�
	// *
	// * @param account
	// */
	// public void addAccount(Account accountDao) {
	// //������
	// String checkSign = balanceMd5.getBalanceMd5Value(accountDao.getUserId(), accountDao
	// .getBalance());
	// accountDao.setCheckSign(checkSign);
	//
	// this.accountDao.addAccount(accountDao);
	//
	// }
	//
	// /**
	// * �õ������˺ţ�����List��װ����
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
	// * �ж����ڲ��˻�������Ƿ��ظ����
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
	// * ���ݲ�ѯ����ȡ�ÿͻ�account
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
	// * �����ʻ��Ų鿴�˻����䶯��ˮ
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
	// * ���������鿴�˻����䶯��ˮ
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
	// * �����ʻ��Ų鿴�˻���֤��䶯��ˮ
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
	// * ���ʻ����ж���/�ⶳ֮��Ĳ���
	// * @param account
	// */
	// public void doProcessAccount(Account account) {
	// //����ʻ�״̬��Ϊ����Ҫ��Ŀ���ʻ�����
	// if (account.getEnabledStatus() != null
	// && account.getEnabledStatus().equals(EnumAccountStatus.FREEZEN_ACCOUNT.getCode())) {
	// //����Ӧ�ʻ�������������ϵ�
	// //withdrawSearchManager.WrongWithdraw(account.getAccountNo());
	// }
	// this.accountDao.doProcessAccount(account);
	// }
	//
	// /**
	// * ͨ��accountNo���account
	// * @param AccountNo
	// * @return
	// */
	// public Account getAccountByAccountNo(String AccountNo) {
	// return this.accountDao.getAccountByAccountNo(AccountNo);
	// }
	//
	// /**
	// * ����ҳ������Ľ����ʻ����ж���/�ⶳ�Ĳ���
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
	 * ��ȡ��ҽ����˻�
	 *
	 * @param userId
	 * @return
	 */
	public Account getBuyerTransAccount(long userId) {
		return getAccountByUidAndType(userId, EnumSubAccount.TO_TRANS);
	}

	/**
	 * ��ȡ��ұ�֤���˻�
	 *
	 * @param userId
	 * @return
	 */

	public Account getBuyerAuctionAccount(long userId) {
		return getAccountByUidAndType(userId, EnumSubAccount.TO_AUCTION);
	}

	// /**
	// * ��ȡ���ҽ����˻�(���ж���̻�ʱ)
	// * @param userId
	// * @return
	// */
	// public Account getSellerTransAccount(long userId) {
	// return this.getAccountByUidAndType(userId, EnumSubAccount.TO_TRANS);
	// }
	//
	// public Money getSecurityBalance(String accountNo) {
	// //modified by zhengsb 2010-3-10 �ع�
	// // Money securityMoney = new Money();
	// // //��ȡ�����Ͷ�����
	// // long freezeBalance = accountDao.sumTotleFreezeAmountByType(accountNo,
	// // EnumFreezeType.SECURITY_FREEZE.getFreezeReason());
	// // //��ȡ�����ͽⶳ���
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
	// //��ȡ�����Ͷ�����
	// long freezeBalance = accountDao.sumTotleFreezeAmountByType(accountNo, freezeType
	// .getFreezeReason());
	// //��ȡ�����ͽⶳ���
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
	// * �޸��˻���ʼ�����
	// * @param datestart
	// * @return
	// */
	// public String doInitializeAccount(String datestart, String dateEnd) {
	// String resultString = "";
	// List<Account> accountList = accountDao.getAccountCountByCreate(datestart, dateEnd); //�õ�����������˻�
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
	// * �˻�����޸�
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
