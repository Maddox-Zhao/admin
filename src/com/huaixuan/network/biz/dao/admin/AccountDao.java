package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.account.Account;

/**
 * guoyj
 */
public interface AccountDao extends PayAccountGenericDao<Account, Long> {
	// /**
	// * �õ�С��ĳ������ʱ��������˺�
	// * @param datestart
	// * @return
	// */
	// public List<Account> getAccountCountByCreate(String dateStart, String
	// dateEnd);
	//
	// /**
	// * �õ��û����seqֵ
	// *
	// * @return
	// * @throws Exception
	// */
	// public Long getUserSeq();
	//
	// /**
	// * �õ�SEQ_PAYMENT_OFFLINEֵ
	// *
	// * @return
	// * @throws Exception
	// */
	// public Long getPayMentOffLineSeq();
	//
	// /**
	// * �õ��˻���countֵ
	// * @return
	// * @throws Exception
	// */
	// public Integer getAccountCount();
	//
	// /**
	// * �õ�ĳ��˾�˻����˻�����
	// * @return
	// */
	// public Integer getCompayAccountCount(String accountNo);
	//
	// /**
	// * �õ��ڲ��˻�ĳ��������
	// * @param map
	// * @return
	// * @throws Exception
	// */
	// public Integer getSubAccountCount(Map map);
	//
	// /**
	// * account��ҳ
	// * @param currentPage
	// * @param pageSize
	// * @param totalCount
	// * @return
	// * @throws Exception
	// */
	// public PageUtil<Account> getAccountPage(int currentPage, int pageSize,
	// int totalCount);
	//
	// /**
	// * �����˻�
	// *
	// * @param account
	// * @throws Exception
	// */
	// void addAccount(Account account);
	//
	// /**
	// * �޸��˻�
	// *
	// * @param account
	// * @throws Exception
	// */
	// void editAccount(Account account);
	//
	// /**
	// * ɾ���˻�
	// *
	// * @param accountId
	// * @throws Exception
	// */
	// void removeAccount(Long accountId);
	//
	// /**
	// * �����˻��ŵõ��˺�ʵ��
	// *
	// * @param accountId
	// * @return
	// * @throws Exception
	// */
	// Account getAccount(Long accountId);
	//
	// /**
	// * �õ������˺ţ�����List��װ����
	// *
	// * @return
	// * @throws Exception
	// */
	// List<Account> getAccounts();
	//
	// /**
	// * ���ݲ�ѯ����ȡ�ÿͻ�account
	// * @param searchCondition
	// * @param page
	// * @return
	// */
	// PageUtil<Account> getAccountsByCondition(Map<String, String>
	// searchCondition, Page page);

	// /**
	// * �����ʻ��Ų鿴�˻����䶯��ˮ
	// *
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public List<Map> getAccountLogsByAccountNo(Map<String, String>
	// searchMap);

	//
	// /**
	// * ���������鿴�˻����䶯��ˮ
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getAccountLogsByUserId(Map<String, String>
	// searchMap, Page page);
	//
	// /**
	// * �����ʻ��Ų鿴�˻���֤��䶯��ˮ
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getBalanceLogsListByAccountNo(Map<String, String>
	// searchMap, Page page);
	//
	// /**
	// * ���ʻ����ж���/�ⶳ֮��Ĳ���
	// * @param account
	// */
	// public void doProcessAccount(Account account);

	/**
	 * ����uid��ȡ��Ӧ���͵��˻�
	 *
	 * @param uid
	 * @param subType
	 * @return
	 */
	public Account getAccountByUidAndSubtype(long uid, String subType);

	// /**
	// * ͨ��accountNo���account
	// * @param AccountNo
	// * @return
	// */
	// public Account getAccountByAccountNo(String AccountNo);
	//
	// public long sumTotleFreezeAmountByType(String accountNo, String
	// freezeReason);
	//
	// public long sumTotleUnFreezeAmountByType(String accountNo, String
	// freezeReason);
	//
	// /**
	// * ���ݿ�ʼʱ��ͽ���ʱ��ȡ���б�
	// * @param beginDate
	// * @param endDate
	// * @return
	// */
	// public PageUtil<AccountLogDO> getByTransDate(String beginDate, String
	// endDate, Page page);
	//
	// /**
	// * �����ⲿ������ȡ���б�
	// * @param outOrderNo
	// * @return
	// */
	// public List<AccountLogDO> getByOutOrderNo(String outOrderNo);
}
