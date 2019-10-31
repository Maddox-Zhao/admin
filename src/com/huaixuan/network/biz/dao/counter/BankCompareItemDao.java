package com.huaixuan.network.biz.dao.counter;

import java.util.List;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;


/**  
 * @version 3.2.0  
 */
public interface BankCompareItemDao{

    /**
     * ɾ�����ж����ļ���ǰ������
     *
     */
    void delBeforeFiveDaysBankCompareItem();

    /**
     * ������Ӷ����ļ�������ϸ�������Ӧ��listu
     * @param listu
     */
    void addBeankCompareItemList(List listu);

    /**
     * ������ж����ļ�������ϸ
     * @param bankCompareItemu
     */
    void addBankCompareItem(BankCompareItem bankCompareItemu);

    /**
     * �༭���ж����ļ�������ϸ
     * @param bankCompareItemu
     */
    void editBankCompareItem(BankCompareItem bankCompareItemu);

    /**
     * ����idɾ����Ӧ��ϸ
     * @param id
     */
    void removeBankCompareItem(Long id);

    /**
     *����id�õ���Ӧ��ϸ 
     * @param id
     * @return
     */
    BankCompareItem getBankCompareItem(Long id);

    /**
     * �õ���ϸlist
     * @return
     */
    List<BankCompareItem> getBankCompareItems();
}
