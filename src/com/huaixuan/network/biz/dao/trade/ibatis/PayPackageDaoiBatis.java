package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.PayPackageDao;
import com.huaixuan.network.biz.domain.trade.PayPackage;

@Repository("payPackageDao")
public class PayPackageDaoiBatis implements PayPackageDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public Long getPayPackageSequences() throws Exception {
        return (Long) this.sqlMapClient.queryForObject("getPayPackageSequences");
    }

    @Override
    public void addPayPackage(PayPackage payPackage) throws Exception {
        this.sqlMapClient.insert("addPayPackage", payPackage);
    }

    @Override
    public void editPayPackage(PayPackage payPackage) throws Exception {
        this.sqlMapClient.update("editPayPackage", payPackage);
    }

    @Override
    public void removePayPackage(Long payPackageId) throws Exception {
        this.sqlMapClient.delete("removePayPackage", payPackageId);
    }

    @Override
    public PayPackage getPayPackage(Long payPackageId) throws Exception {
        return (PayPackage) this.sqlMapClient.queryForObject("getPayPackage", payPackageId);
    }

    @Override
    public List<PayPackage> getPayPackages() throws Exception {
        return this.sqlMapClient.queryForList("getPayPackages", null);
    }

    @Override
    public List<PayPackage> getPayPackagesByPId(Long payPackageId) throws Exception {
        return this.sqlMapClient.queryForList("getPayPackagesByPId", payPackageId);
    }

    @Override
    public List<PayPackage> getDistinctTidPayPackagesByPId(Long payPackageId) {
        return this.sqlMapClient.queryForList("getDistinctTidPayPackagesByPId", payPackageId);
    }
}
