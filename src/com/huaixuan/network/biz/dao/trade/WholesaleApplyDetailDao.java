package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.WholesaleApplyDetail;

public interface WholesaleApplyDetailDao {

    void addWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail) throws Exception;

    void editWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail) throws Exception;

    void removeWholesaleApplyDetail(Long wholesaleApplyDetailId) throws Exception;

    WholesaleApplyDetail getWholesaleApplyDetail(Long wholesaleApplyDetailId) throws Exception;

    List<WholesaleApplyDetail> getWholesaleApplyDetails() throws Exception;

    List<WholesaleApplyDetail> getWholesaleApplyDetailsByApplyId(Long wholesaleApplyId)
                                                                                       throws Exception;
}
