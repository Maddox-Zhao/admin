package com.huaixuan.network.biz.service.crm.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.crm.ConnectRecordDao;
import com.huaixuan.network.biz.domain.crm.ConnectRecord;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.crm.ConnectRecordManager;

/**
 * Î¬»¤¼ÇÂ¼
 * @author chenyan 2010/08/04
 *
 */
@Service("connectRecordManager")
public class ConnectRecordManagerImpl implements ConnectRecordManager {

    protected Log            log = LogFactory.getLog(this.getClass());

    @Autowired
    private ConnectRecordDao connectRecordDao;

    @Override
    public Long addConnectRecord(ConnectRecord connectRecord) {
        try {
            return connectRecordDao.addConnectRecord(connectRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0L;
    }

    @Override
    public ConnectRecord getConnectRecordById(Long id) {
        try {
            return connectRecordDao.getConnectRecordById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public QueryPage listConnectRecordByParameter(Map parMap, int currentPage, int pageSize) {
        try {
            return connectRecordDao.listConnectRecordByParameter(parMap, currentPage, pageSize);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public int listConnectRecordByParameterCount(Map parMap) {
        try {
            return connectRecordDao.listConnectRecordByParameterCount(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateConnectRecordById(ConnectRecord connectRecord) {
        try {
            return connectRecordDao.updateConnectRecordById(connectRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateConnectRecordStatusById(ConnectRecord connectRecord) {
        try {
            return connectRecordDao.updateConnectRecordStatusById(connectRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<ConnectRecord> ListConnectRecordByUserId(Map parMap) {
        try {
            return connectRecordDao.ListConnectRecordByUserId(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
