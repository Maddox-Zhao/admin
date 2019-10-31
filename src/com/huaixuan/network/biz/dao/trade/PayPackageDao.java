package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.PayPackage;

public interface PayPackageDao {

    Long getPayPackageSequences() throws Exception;

    void addPayPackage(PayPackage payPackage) throws Exception;

    void editPayPackage(PayPackage payPackage) throws Exception;

    void removePayPackage(Long payPackageId) throws Exception;

    PayPackage getPayPackage(Long payPackageId) throws Exception;

    List<PayPackage> getPayPackages() throws Exception;

    List<PayPackage> getPayPackagesByPId(Long payPackageId) throws Exception;

    List<PayPackage> getDistinctTidPayPackagesByPId(Long payPackageId);
}
