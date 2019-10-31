package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.PayPackageDao;
import com.huaixuan.network.biz.domain.trade.PayPackage;
import com.huaixuan.network.biz.service.trade.PayPackageManager;

@Service("payPackageManager")
public class PayPackageManagerImpl implements PayPackageManager {

    protected Log        log = LogFactory.getLog(this.getClass());

    @Autowired
    public PayPackageDao payPackageDao;

    @Override
    public Long getPayPackageSequences() {
        log.info("PayPackageManagerImpl.getPayPackageSequences method");
        try {
            return this.payPackageDao.getPayPackageSequences();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void addPayPackage(PayPackage payPackageDao) {
        log.info("PayPackageManagerImpl.addPayPackage method");
        try {
            this.payPackageDao.addPayPackage(payPackageDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editPayPackage(PayPackage payPackage) {
        log.info("PayPackageManagerImpl.editPayPackage method");
        try {
            this.payPackageDao.editPayPackage(payPackage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void removePayPackage(Long payPackageId) {
        log.info("PayPackageManagerImpl.removePayPackage method");
        try {
            this.payPackageDao.removePayPackage(payPackageId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public PayPackage getPayPackage(Long payPackageId) {
        log.info("PayPackageManagerImpl.getPayPackage method");
        try {
            return this.payPackageDao.getPayPackage(payPackageId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PayPackage> getPayPackages() {
        log.info("PayPackageManagerImpl.getPayPackages method");
        try {
            return this.payPackageDao.getPayPackages();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PayPackage> getPayPackagesByPId(Long payPackageId) {
        log.info("PayPackageManagerImpl.getPayPackagesByPId method");
        try {
            return this.payPackageDao.getPayPackagesByPId(payPackageId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PayPackage> getDistinctTidPayPackagesByPId(Long payPackageId) {
        log.info("PayPackageManagerImpl.getDistinctTidPayPackagesByPId method");
        try {
            return this.payPackageDao.getDistinctTidPayPackagesByPId(payPackageId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
