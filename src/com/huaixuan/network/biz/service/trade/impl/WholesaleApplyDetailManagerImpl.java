package com.huaixuan.network.biz.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.WholesaleApplyDetailDao;
import com.huaixuan.network.biz.domain.trade.WholesaleApplyDetail;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.trade.WholesaleApplyDetailManager;

@Service("wholesaleApplyDetailManager")
public class WholesaleApplyDetailManagerImpl implements WholesaleApplyDetailManager {

    protected Log                   log = LogFactory.getLog(this.getClass());

    @Autowired
    private WholesaleApplyDetailDao wholesaleApplyDetailDao;
    @Autowired
    private CategoryManager         categoryManager;
    @Autowired
    private AttributeManager        attributeManager;

    @Override
    public void addWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetailDao) {
        log.info("WholesaleApplyDetailManagerImpl.addWholesaleApplyDetail method");
        try {
            this.wholesaleApplyDetailDao.addWholesaleApplyDetail(wholesaleApplyDetailDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editWholesaleApplyDetail(WholesaleApplyDetail wholesaleApplyDetail) {
        log.info("WholesaleApplyDetailManagerImpl.editWholesaleApplyDetail method");
        try {
            this.wholesaleApplyDetailDao.editWholesaleApplyDetail(wholesaleApplyDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void removeWholesaleApplyDetail(Long wholesaleApplyDetailId) {
        log.info("WholesaleApplyDetailManagerImpl.removeWholesaleApplyDetail method");
        try {
            this.wholesaleApplyDetailDao.removeWholesaleApplyDetail(wholesaleApplyDetailId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public WholesaleApplyDetail getWholesaleApplyDetail(Long wholesaleApplyDetailId) {
        log.info("WholesaleApplyDetailManagerImpl.getWholesaleApplyDetail method");
        try {
            return this.wholesaleApplyDetailDao.getWholesaleApplyDetail(wholesaleApplyDetailId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<WholesaleApplyDetail> getWholesaleApplyDetails() {
        log.info("WholesaleApplyDetailManagerImpl.getWholesaleApplyDetails method");
        try {
            return this.wholesaleApplyDetailDao.getWholesaleApplyDetails();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<WholesaleApplyDetail> getWholesaleApplyDetailsByApplyId(Long wholesaleApplyId) {
        log.info("WholesaleApplyDetailManagerImpl.getWholesaleApplyDetailsByApplyId method");
        List<WholesaleApplyDetail> resultList = new ArrayList<WholesaleApplyDetail>();
        try {
            List<WholesaleApplyDetail> list = this.wholesaleApplyDetailDao
                .getWholesaleApplyDetailsByApplyId(wholesaleApplyId);
            if (list != null && list.size() > 0) {
                for (WholesaleApplyDetail detail : list) {
                    detail.setCatCode(categoryManager.getCatFullNameByCatcodeSimple(detail
                        .getCatCode(), ">"));
                    detail.setAttrs(attributeManager.getFullAttributeStringByAttrs(detail
                        .getAttrs()));
                    resultList.add(detail);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultList;
    }
}
