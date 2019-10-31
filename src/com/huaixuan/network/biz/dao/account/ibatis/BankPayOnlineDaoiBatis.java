package com.huaixuan.network.biz.dao.account.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.account.BankPayOnlineDao;
import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;

/**
 * @version $Id: BankPayOnlineDaoiBatis.java,v 0.1 2010-6-7 上午11:26:51 g
 *          Exp $
 */
@Service("bankPayOnlineDao")
public class BankPayOnlineDaoiBatis implements BankPayOnlineDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    //    public void addBankPayOnline(BaseBankPayOnline baseBankPayOnline) {
    //        getSqlMapClientTemplate().insert("addBankPayOnline", baseBankPayOnline);
    //    }
    //
    public void editBankPayOnline(BaseBankPayOnline baseBankPayOnline) {
        this.sqlMapClientTemplate.update("editBankPayOnlineById", baseBankPayOnline);
    }

    //
    //    public List<BaseBankPayOnline> getBankPayOnlinesForRecovery(Integer maxRow) {
    //        Map<String, String> map = new HashMap<String, String>();
    //        if (null != maxRow) {
    //            map.put("maxRow", maxRow.toString());
    //        }
    //        return getSqlMapClientTemplate().queryForList("getBankPayOnlinesForRecovery", map);
    //    }
    //
    //    public BaseBankPayOnline getBankPayOnlineById(Long id) {
    //        return (BaseBankPayOnline) getSqlMapClientTemplate().queryForObject("getBankPayOnlineById",
    //            id);
    //    }
    //
    //    public BaseBankPayOnline getBankPayOnlineByBankBillNo(String bankBillNo) {
    //        return (BaseBankPayOnline) getSqlMapClientTemplate().queryForObject(
    //            "getBankPayOnlineByBankBillNo", bankBillNo);
    //    }
    //
    //    public BaseBankPayOnline getUniqueBankpayOnline(String bankType, String bankBillNo) {
    //        Map<String, String> map = new HashMap<String, String>();
    //        map.put("bankType", bankType);
    //        map.put("bankBillNo", bankBillNo);
    //        return (BaseBankPayOnline) getSqlMapClientTemplate().queryForObject(
    //            "getUniqueBankPayOnline", map);
    //    }
    //
    //    public BaseBankPayOnline getUniqueBankPayOnlineAndLock(String bankType, String bankBillNo,
    //                                                           EnumPaymentType enumPaymentType) {
    //        Map<String, String> map = new HashMap<String, String>();
    //        map.put("bankType", bankType);
    //        map.put("bankBillNo", bankBillNo);
    //        map.put("paymentType", enumPaymentType.getName());
    //        return (BaseBankPayOnline) getSqlMapClientTemplate().queryForObject(
    //            "getUniqueBankPayOnlineAndLock", map);
    //    }
    //
    //    public BaseBankPayOnline getBankPayOnlineByInnerBillNo(String innerBillNo) {
    //        return (BaseBankPayOnline) getSqlMapClientTemplate().queryForObject(
    //            "getBankPayOnlineByInnerBillNo", innerBillNo);
    //    }
    //
    //    public List<BaseBankPayOnline> getBankPayOnlineByInnerDate(String innerDate) {
    //        return getSqlMapClientTemplate().queryForList("getBankPayOnlinesByInnerDate", innerDate);
    //    }
    //
    //    public Long getBankBillNoSeq() {
    //        return (Long) getSqlMapClientTemplate().queryForObject("getBankBillNoSeq");
    //    }
    //
    //    public void removeBankPayOnlineById(Long id) {
    //        getSqlMapClientTemplate().delete("removeBankPayOnlineById", id);
    //    }

    /**
     * 根据搜索条件查找满足条件的支付流
     * @param searchMap
     * @param page
     * @return
     * @see com.hundsun.bible.dao.payment.BankPayOnlineDao#searchBankPayOnlineListByCondition(java.util.Map, com.hundsun.bible.Page)
     */
    public List<BaseBankPayOnline> searchBankPayOnlineListByCondition(Map<String, String> searchMap) {
        List<BaseBankPayOnline> list = this.sqlMapClientTemplate.queryForList(
            "basebankpayonline-searchBasebankpayonlineByCondition-query", searchMap);
        return list;
    }

    @Override
    public Integer searchBankPayOnlineListByConditionCount(Map map) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "basebankpayonline-searchBasebankpayonlineByCondition-count", map);
    }

    //    public void editBankPayOnlineForQueryService(BaseBankPayOnline baseBankPayOnline) {
    //        if (null != baseBankPayOnline) {
    //            getSqlMapClientTemplate().update("editBankPayOnlineForQueryService", baseBankPayOnline);
    //        }
    //    }
}
