/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-9
 */
package com.huaixuan.network.biz.service.remote.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.remote.TaobaoApplyDao;
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;
import com.huaixuan.network.biz.enums.EnumInterfaceApplyStatus;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;

/**
 * @author shengyong
 * @version $Id: TaobaoInterfaceApplyManagerImpl.java,v 0.1 2011-3-9 ÉÏÎç11:36:32 shengyong Exp $
 */
@Service("taobaoInterfaceApplyManager")
public class TaobaoInterfaceApplyManagerImpl implements TaobaoInterfaceApplyManager {

    protected final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    TaobaoApplyDao      taobaoApplyDao;

    /* @model: */
    public void addInterfaceApply(TaobaoApply taobaoApplyDao) {
        log.info("TaobaoApplyManagerImpl.addTaobaoApply method");
        try {
            TaobaoApply TaobaoApply = this.getInterfaceApplyByUserId(taobaoApplyDao.getUserId(),
                EnumInterfaceType.TAOBAO.getKey());
            if (TaobaoApply != null) {
                this.taobaoApplyDao.editTaobaoApply(taobaoApplyDao);
            } else {
                taobaoApplyDao.setStatus(EnumInterfaceApplyStatus.NEW.getKey());
                this.taobaoApplyDao.addTaobaoApply(taobaoApplyDao);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editInterfaceApply(TaobaoApply TaobaoApply) {
        log.info("TaobaoApplyManagerImpl.editTaobaoApply method");
        try {
            this.taobaoApplyDao.editTaobaoApply(TaobaoApply);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void editGmtSync(TaobaoApply TaobaoApply) {
        log.info("TaobaoApplyManagerImpl.editGmtSync method");
        try {
            this.taobaoApplyDao.editGmtSync(TaobaoApply);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeInterfaceApply(Long TaobaoApplyId) {
        log.info("TaobaoApplyManagerImpl.removeTaobaoApply method");
        try {
            this.taobaoApplyDao.removeTaobaoApply(TaobaoApplyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public TaobaoApply getInterfaceApply(Long TaobaoApplyId) {
        log.info("TaobaoApplyManagerImpl.getTaobaoApply method");
        try {
            return this.taobaoApplyDao.getTaobaoApply(TaobaoApplyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public TaobaoApply getInterfaceApplyByUserId(Long userId, String type) {
        log.info("TaobaoApplyManagerImpl.getTaobaoApplyByUserId method");
        try {
            return this.taobaoApplyDao.getTaobaoApplyByUserId(userId, type);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<TaobaoApply> getInterfaceApplys() {
        log.info("TaobaoApplyManagerImpl.getTaobaoApplys method");
        try {
            return this.taobaoApplyDao.getTaobaoApplys();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public int getCountByMap(Map parMap) {
        log.info("TaobaoApplyManagerImpl.getCountByMap method");
        try {
            return taobaoApplyDao.getCountByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public List<TaobaoApply> getListByMap(Map parMap) {
        log.info("TaobaoApplyManagerImpl.getListByMap method");
        try {
            return this.taobaoApplyDao.getListByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void editInterfaceApplyStatus(Long id, String status, String memo) {
        log.info("TaobaoApplyManagerImpl.editTaobaoApply method");
        try {
            this.taobaoApplyDao.editTaobaoApplyStatus(id, status, memo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void modifyOwnExpressIdById(Long id, Long ownExpressId) {
        log.info("TaobaoApplyManagerImpl.modifyOwnExpressIdById method");
        try {
            this.taobaoApplyDao.modifyOwnExpressIdById(id, ownExpressId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    public void modifyInterfaceExpressCodeById(Long id, String interfaceExpressCode) {
        log.info("TaobaoApplyManagerImpl.modifyInterfaceExpressCodeById method");
        try {
            this.taobaoApplyDao.modifyInterfaceExpressCodeById(id, interfaceExpressCode);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}