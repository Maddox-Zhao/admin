package com.huaixuan.network.biz.service.account;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.query.QueryPage;
import com.hundsun.itrans.biz.domain.Enum.EnumSubAccount;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.biz.model.FreezeBalanceReq;
import com.hundsun.itrans.common.util.Money;

/**
 * 
 */
public interface AccountManager {

    /**
     * �����ڲ��˻�
     *
     * @param accountType
     *            �˻����� 3�����ڲ��˻�
     * @param accountSubType
     *            �˻������� 01:�����˻���02:��֤���˻�,03:Ӷ���˻���04:�����˻�
     * @param accountName
     *              �˻�������
     * @param memo
     *            �˻���ע
     * @return
     */
    public boolean addInnerWebAccount(String accountType, String accountSubType,
                                      String accountName, String memo);

    /**
     * �����˻�����Ҫ�Ǵ����̼��˻��͹˿��˻����û�ע��ʱ�ĵ��ýӿ�
     *
     * @param userId
     *            �û�id
     * @param userType
     *            �û�����
     * @return boolean
     */
    public boolean addFrontWebAccount(Long userId, String userType);

    /**
     * �����û��˺ţ��û��˺�=11185+�˻�����+������+�û�Id
     *
     * @param accountNoBegin
     *            �˻���ͷ��5λ���涨��11185��ͷ
     * @param accountType
     *            �˻����ͣ��涨��һλ
     * @param accountSubType
     *            �˻������ͣ��涨����λ
     * @param userId
     *            �û�Id,�涨��12λ������12λ�Ļ���˲���
     * @return String
     */
    public String getAccountNO(String accountNoBegin, String accountType, String accountSubType,
                               String userId);

    /**
     * �����û�userId,userIdΪ12λ���������12λ�Ļ�����˲���
     *
     * @param userId
     * @return
     */
    public String getAccountNO(String userId);

    /**
     * �õ��û����seqֵ
     * @return
     */
    public Long getUserSeq();

    /**
     * account��ҳ
     * @param currentPage
     * @param pageSize
     * @return
     */
    public QueryPage getAccountPage(int currentPage, int pageSize);

    /**
     * �����˻�
     *
     * @param account
     */
    public void addAccount(Account account);

    /**
     * �õ������˺ţ�����List��װ����
     *
     * @return
     */
    public List<Account> getAccounts();

    /**
     * �ж����ڲ��˻�������Ƿ��ظ����
     * @param map
     * @return
     */
    public boolean checkAddSubAccount(Map map);

    /**
     * ������������ȡ�ÿͻ��˺�
     * @return
     */
    public QueryPage getAccountsByCondition(Map searchMap, int currentPage,
                                            int pageSize);

    /**
     * �����ʻ��Ų鿴�˻����䶯��ˮ
     * @param acount
     * @param page
     * @return page
     */
    public QueryPage getAccountLogsByAccountNo(Map searchMap, int currentPage,
                                               int pageSize);

    /**
     * �����ʻ��Ų鿴�˻���֤��䶯��ˮ
     * @param acount
     * @param page
     * @return page
     */
    public QueryPage getBalanceLogsListByAccountNo(Map searchMap, int currentPage,
                                                   int pageSize);

    /**
     * ���������鿴�˻����䶯��ˮ
     * @param acount
     * @param page
     * @return page
     */
    public QueryPage getAccountLogsByUserId(Map searchMap, int currentPage,
                                            int pageSize);

    /**
     * ���ʻ����ж���/�ⶳ֮��Ĳ���
     * @param account
     */
    public void doProcessAccount(Account account);

    /**
     * ��ȡ��ҽ����˻�
     * @param userId
     * @return
     */
    public Account getBuyerTransAccount(long userId);

    /**
     * ��ȡ��ұ�֤���˻�
     * @param userId
     * @return
     */
    public Account getBuyerAuctionAccount(long userId);

    /**
     * ��ȡ���ҽ����˻�(���ж���̻�ʱ)
     * @param userId
     * @return
     */
    public Account getSellerTransAccount(long userId);

    /**
     * ��ȡ�û�ĳһ���͵��˻�(�տ���߸���)
     * @param userId
     * @return
     */
    public Account getAccountByUidAndType(long userId, EnumSubAccount accoutType);

    /**
     * ͨ��accountNo���account
     * @param AccountNo
     * @return
     */
    public Account getAccountByAccountNo(String AccountNo);

    /**
     * ����ҳ������Ľ����ʻ����ж���/�ⶳ�Ĳ���
     * @param req
     * @return
     */
    public AccountTransResult doBalanceReq(FreezeBalanceReq req);

    /**
     * ��ȡ��֤�����
     * @param accountNo
     * @return
     */
    public Money getSecurityBalance(String accountNo);

    /**
     *  �޸��˻���ʼ�����
     * @param datestart
     * @param dateEnd
     * @return
     */
    public String doInitializeAccount(String datestart, String dateEnd);

    /**
     * �˻�����޸�
     * @param accountNo
     * @param balance
     * @return
     */
    public String doUpdateAccount(String accountNo, String balance);

    /**
     * ȡ��������֤�����
     * @param accountNo
     * @param type
     * @return
     */
    public Money getAuctionSecurityBalance(String accountNo);

}
