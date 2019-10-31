package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.WholesaleApplyDetailDao;
import com.huaixuan.network.biz.domain.trade.WholesaleApplyDetail;

@Repository("wholesaleApplyDetailDao")
public class WholesaleApplyDetailDaoiBatis implements WholesaleApplyDetailDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail) throws Exception {
        this.sqlMapClient.insert("addWholesaleApplyDetail", wholesaleApplyDetail);
    }

    @Override
    public void editWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail)
                                                                                   throws Exception {
        this.sqlMapClient.update("editWholesaleApplyDetail", wholesaleApplyDetail);
    }

    @Override
    public void removeWholesaleApplyDetail(Long wholesaleApplyDetailId) throws Exception {
        this.sqlMapClient.delete("removeWholesaleApplyDetail", wholesaleApplyDetailId);
    }

    @Override
    public WholesaleApplyDetail getWholesaleApplyDetail(Long wholesaleApplyDetailId)
                                                                                    throws Exception {
        return (WholesaleApplyDetail) this.sqlMapClient.queryForObject("getWholesaleApplyDetail",
            wholesaleApplyDetailId);
    }

    @Override
    public List<WholesaleApplyDetail> getWholesaleApplyDetails() throws Exception {
        return this.sqlMapClient.queryForList("getWholesaleApplyDetails", null);
    }

    @Override
    public List<WholesaleApplyDetail> getWholesaleApplyDetailsByApplyId(Long wholesaleApplyId)
                                                                                              throws Exception {
        return this.sqlMapClient
            .queryForList("getWholesaleApplyDetailsByApplyId", wholesaleApplyId);
    }
}
