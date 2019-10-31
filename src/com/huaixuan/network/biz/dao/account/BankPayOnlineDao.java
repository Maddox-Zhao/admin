package com.huaixuan.network.biz.dao.account;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;

/**
 * ���ж������ݲ����ӿ�
 * 
 * @version $Id: BankPayOnlineDao.java,v 0.1 2009-8-7 ����03:02:18  Exp $
 */
public interface BankPayOnlineDao {
    //    /**
    //     * �������ж���
    //     * 
    //     * @param baseBankPayOnline
    //     * 
    //     */
    //    public void addBankPayOnline(BaseBankPayOnline baseBankPayOnline);
    //
    //    /**
    //     * ������ˮ��ɾ�����ж�
    //     * 
    //     * @param id
    //     * 
    //     */
    //    public void removeBankPayOnlineById(Long id);
    //
    /**
     * �༭���ж���
     * 
     * @param baseBankPayOnline
     * 
     */
    public void editBankPayOnline(BaseBankPayOnline baseBankPayOnline);

    //
    //    /**
    //     * �༭���ж����Ĳ�ѯ������check user
    //     * @param baseBankPayOnline
    //     */
    //    public void editBankPayOnlineForQueryService(BaseBankPayOnline baseBankPayOnline);
    //
    //    /**
    //     * ��ȡ���ж�����ѯ��¼
    //     * @param maxRow
    //     * @return
    //     */
    //    public List<BaseBankPayOnline> getBankPayOnlinesForRecovery(Integer maxRow);
    //
    //    /**
    //     * ������ˮ�Ż�ȡ���ж����Ő_
    //     * 
    //     * @param id
    //     * @return BaseBankPayOnline
    //     * 
    //     */
    //    public BaseBankPayOnline getBankPayOnlineById(Long id);
    //
    //    /**
    //     * �������ж����Ż�ȡ���ж����Ő_
    //     * 
    //     * @param bankBillNo
    //     * @return
    //     */
    //    @Deprecated
    //    public BaseBankPayOnline getBankPayOnlineByBankBillNo(String bankBillNo);
    //
    //    /**
    //     * �����������ͺ����ж����Ż�ȡΨһ���ж�����Ϣ.�÷�������֤ȡ�õļ�¼��Ψһ��,���ȡ���������׳��쎊
    //     * @param bankType
    //     * @param bankBillNo
    //     * @return
    //     */
    //    public BaseBankPayOnline getUniqueBankpayOnline(String bankType, String bankBillNo);
    //
    //    /**
    //     * �����������ͺ����ж����Ż�ȡΨһ���ж�����Ϣ.�÷�������֤ȡ�õļ�¼��Ψһ��,���ȡ���������׳��쎊
    //     * @param bankType
    //     * @param bankBillNo
    //     * @param enumPaymentType
    //     * @return
    //     */
    //    public BaseBankPayOnline getUniqueBankPayOnlineAndLock(String bankType, String bankBillNo,
    //                                                           EnumPaymentType enumPaymentType);
    //
    //    /**
    //     * �����ڲ������Ż�ȡ���ж����Ő_
    //     * 
    //     * @param innerBillNo
    //     * @return
    //     */
    //
    //    public BaseBankPayOnline getBankPayOnlineByInnerBillNo(String innerBillNo);
    //
    //    /**
    //     * ���ݶ�������ʱ���ȡ���ж����б�
    //     * 
    //     * @param innerDate
    //     * @return
    //     */
    //    public List<BaseBankPayOnline> getBankPayOnlineByInnerDate(String innerDate);
    //
    //    /**
    //     * ����8λ���к����������ж�����
    //     * 
    //     * @return Long
    //     */
    //    public Long getBankBillNoSeq();

    /**
     * ��������������������������֧������
     * @return
     */
    public List<BaseBankPayOnline> searchBankPayOnlineListByCondition(Map<String, String> searchMap);

    public Integer searchBankPayOnlineListByConditionCount(Map<String, String> searchMap);

}
