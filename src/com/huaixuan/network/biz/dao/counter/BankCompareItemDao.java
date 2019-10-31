package com.huaixuan.network.biz.dao.counter;

import java.util.List;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;


/**  
 * @version 3.2.0  
 */
public interface BankCompareItemDao{

    /**
     * 删除银行对账文件以前的数据
     *
     */
    void delBeforeFiveDaysBankCompareItem();

    /**
     * 批量添加对帐文件导入明细表，插入对应的listu
     * @param listu
     */
    void addBeankCompareItemList(List listu);

    /**
     * 添加银行对账文件导入明细
     * @param bankCompareItemu
     */
    void addBankCompareItem(BankCompareItem bankCompareItemu);

    /**
     * 编辑银行对账文件导入明细
     * @param bankCompareItemu
     */
    void editBankCompareItem(BankCompareItem bankCompareItemu);

    /**
     * 根据id删除对应明细
     * @param id
     */
    void removeBankCompareItem(Long id);

    /**
     *根据id得到对应明细 
     * @param id
     * @return
     */
    BankCompareItem getBankCompareItem(Long id);

    /**
     * 得到明细list
     * @return
     */
    List<BankCompareItem> getBankCompareItems();
}
