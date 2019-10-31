package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.WholesaleApplyDetail;

public interface WholesaleApplyDetailManager {

    void addWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail);

    void editWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail);

    void removeWholesaleApplyDetail(Long wholesaleApplyDetailId);

    WholesaleApplyDetail getWholesaleApplyDetail(Long wholesaleApplyDetailId);

    public List<WholesaleApplyDetail> getWholesaleApplyDetails();

    List<WholesaleApplyDetail> getWholesaleApplyDetailsByApplyId(Long wholesaleApplyId);
}
