package com.huaixuan.network.biz.service.admin;

import com.huaixuan.network.biz.domain.account.Account;


/**
 * guoyj
 */
public interface AccountManager {

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
	// String accountName, String memo);
	//
	// /**
	// * �����˻�����Ҫ�Ǵ����̼��˻��͹˿��˻����û�ע��ʱ�ĵ��ýӿ�
	// *
	// * @param userId
	// * �û�id
	// * @param userType
	// * �û�����
	// * @return boolean
	// */
	// public boolean addFrontWebAccount(Long userId, String userType);
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
	// String userId);
	//
	// /**
	// * �����û�userId,userIdΪ12λ���������12λ�Ļ�����˲���
	// *
	// * @param userId
	// * @return
	// */
	// public String getAccountNO(String userId);
	//
	// /**
	// * �õ��û����seqֵ
	// * @return
	// */
	// public Long getUserSeq();
	//
	// /**
	// * account��ҳ
	// * @param currentPage
	// * @param pageSize
	// * @return
	// */
	// public PageUtil<Account> getAccountPage(int currentPage, int pageSize);
	//
	// /**
	// * �����˻�
	// *
	// * @param account
	// */
	// public void addAccount(Account account);
	//
	// /**
	// * �õ������˺ţ�����List��װ����
	// *
	// * @return
	// */
	// public List<Account> getAccounts();
	//
	// /**
	// * �ж����ڲ��˻�������Ƿ��ظ����
	// * @param map
	// * @return
	// */
	// public boolean checkAddSubAccount(Map map);
	//
	// /**
	// * ������������ȡ�ÿͻ��˺�
	// * @return
	// */
	// public PageUtil<Account> getAccountsByCondition(Map<String, String> searchMap, Page page);
	//
	// /**
	// * �����ʻ��Ų鿴�˻����䶯��ˮ
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getAccountLogsByAccountNo(Map<String, String> searchMap, Page page);
	//
	// /**
	// * �����ʻ��Ų鿴�˻���֤��䶯��ˮ
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getBalanceLogsListByAccountNo(Map<String, String> searchMap, Page page);
	//
	// /**
	// * ���������鿴�˻����䶯��ˮ
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getAccountLogsByUserId(Map<String, String> searchMap, Page page);
	//
	// /**
	// * ���ʻ����ж���/�ⶳ֮��Ĳ���
	// * @param account
	// */
	// public void doProcessAccount(Account account);

	/**
	 * ��ȡ��ҽ����˻�
	 * 
	 * @param userId
	 * @return
	 */
	public Account getBuyerTransAccount(long userId);
	// /**
	// * ��ȡ��ұ�֤���˻�
	// * @param userId
	// * @return
	// */
	// public Account getBuyerAuctionAccount(long userId);
	//
	// /**
	// * ��ȡ���ҽ����˻�(���ж���̻�ʱ)
	// * @param userId
	// * @return
	// */
	// public Account getSellerTransAccount(long userId);
	//
	//
	//
	// /**
	// * ��ȡ�û�ĳһ���͵��˻�(�տ���߸���)
	// * @param userId
	// * @return
	// */
	// public Account getAccountByUidAndType(long userId, EnumSubAccount accoutType);
	//
	// /**
	// * ͨ��accountNo���account
	// * @param AccountNo
	// * @return
	// */
	// public Account getAccountByAccountNo(String AccountNo);
	//
	// /**
	// * ����ҳ������Ľ����ʻ����ж���/�ⶳ�Ĳ���
	// * @param req
	// * @return
	// */
	// public AccountTransResult doBalanceReq(FreezeBalanceReq req);
	//
	// /**
	// * ��ȡ��֤�����
	// * @param accountNo
	// * @return
	// */
	// public Money getSecurityBalance(String accountNo);
	//
	// /**
	// * �޸��˻���ʼ�����
	// * @param datestart
	// * @param dateEnd
	// * @return
	// */
	// public String doInitializeAccount(String datestart, String dateEnd);
	//
	// /**
	// * �˻�����޸�
	// * @param accountNo
	// * @param balance
	// * @return
	// */
	// public String doUpdateAccount(String accountNo, String balance);
	//
	//
	//
	// /**
	// * ȡ��������֤�����
	// * @param accountNo
	// * @param type
	// * @return
	// */
	// public Money getAuctionSecurityBalance(String accountNo);

}
