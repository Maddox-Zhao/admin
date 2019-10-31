package com.huaixuan.network.biz.dao.account;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;

/**
 * 银行订单数据操作接口
 * 
 * @version $Id: BankPayOnlineDao.java,v 0.1 2009-8-7 下午03:02:18  Exp $
 */
public interface BankPayOnlineDao {
    //    /**
    //     * 新增银行订单
    //     * 
    //     * @param baseBankPayOnline
    //     * 
    //     */
    //    public void addBankPayOnline(BaseBankPayOnline baseBankPayOnline);
    //
    //    /**
    //     * 根据流水号删除银行订
    //     * 
    //     * @param id
    //     * 
    //     */
    //    public void removeBankPayOnlineById(Long id);
    //
    /**
     * 编辑银行订单
     * 
     * @param baseBankPayOnline
     * 
     */
    public void editBankPayOnline(BaseBankPayOnline baseBankPayOnline);

    //
    //    /**
    //     * 编辑银行订单的查询次数和check user
    //     * @param baseBankPayOnline
    //     */
    //    public void editBankPayOnlineForQueryService(BaseBankPayOnline baseBankPayOnline);
    //
    //    /**
    //     * 获取银行订单查询记录
    //     * @param maxRow
    //     * @return
    //     */
    //    public List<BaseBankPayOnline> getBankPayOnlinesForRecovery(Integer maxRow);
    //
    //    /**
    //     * 根据流水号获取银行订单信恄
    //     * 
    //     * @param id
    //     * @return BaseBankPayOnline
    //     * 
    //     */
    //    public BaseBankPayOnline getBankPayOnlineById(Long id);
    //
    //    /**
    //     * 根据银行订单号获取银行订单信恄
    //     * 
    //     * @param bankBillNo
    //     * @return
    //     */
    //    @Deprecated
    //    public BaseBankPayOnline getBankPayOnlineByBankBillNo(String bankBillNo);
    //
    //    /**
    //     * 根据银行类型和银行订单号获取唯一银行订单信息.该方法不保证取得的记录是唯一的,如果取出多条则抛出异帄
    //     * @param bankType
    //     * @param bankBillNo
    //     * @return
    //     */
    //    public BaseBankPayOnline getUniqueBankpayOnline(String bankType, String bankBillNo);
    //
    //    /**
    //     * 根据银行类型和银行订单号获取唯一银行订单信息.该方法不保证取得的记录是唯一的,如果取出多条则抛出异帄
    //     * @param bankType
    //     * @param bankBillNo
    //     * @param enumPaymentType
    //     * @return
    //     */
    //    public BaseBankPayOnline getUniqueBankPayOnlineAndLock(String bankType, String bankBillNo,
    //                                                           EnumPaymentType enumPaymentType);
    //
    //    /**
    //     * 根据内部订单号获取银行订单信恄
    //     * 
    //     * @param innerBillNo
    //     * @return
    //     */
    //
    //    public BaseBankPayOnline getBankPayOnlineByInnerBillNo(String innerBillNo);
    //
    //    /**
    //     * 根据订单创建时间获取银行订单列表
    //     * 
    //     * @param innerDate
    //     * @return
    //     */
    //    public List<BaseBankPayOnline> getBankPayOnlineByInnerDate(String innerDate);
    //
    //    /**
    //     * 生成8位序列号来产生银行订单号
    //     * 
    //     * @return Long
    //     */
    //    public Long getBankBillNoSeq();

    /**
     * 根据搜索条件查找满足条件的支付流氄
     * @return
     */
    public List<BaseBankPayOnline> searchBankPayOnlineListByCondition(Map<String, String> searchMap);

    public Integer searchBankPayOnlineListByConditionCount(Map<String, String> searchMap);

}
