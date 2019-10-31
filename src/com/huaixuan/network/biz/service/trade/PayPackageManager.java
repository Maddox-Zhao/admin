package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.PayPackage;

public interface PayPackageManager {

    Long getPayPackageSequences();

    void addPayPackage(PayPackage payPackage);

    void editPayPackage(PayPackage payPackage);

    void removePayPackage(Long payPackageId);

    PayPackage getPayPackage(Long payPackageId);

    List<PayPackage> getPayPackages();

    List<PayPackage> getPayPackagesByPId(Long payPackageId);

    List<PayPackage> getDistinctTidPayPackagesByPId(Long payPackageId);
}
