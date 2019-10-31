package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.TicketRecordDao;
import com.huaixuan.network.biz.domain.trade.TicketRecord;
import com.huaixuan.network.biz.service.trade.TicketRecordManager;

@Service("ticketRecordManager")
public class TicketRecordManagerImpl implements TicketRecordManager {

    protected Log           log = LogFactory.getLog(this.getClass());

    @Autowired
    private TicketRecordDao ticketRecordDao;

    /* @model: */
    @Override
    public void addTicketRecord(TicketRecord ticketRecordDao) {
        log.info("TicketRecordManagerImpl.addTicketRecord method");
        try {
            this.ticketRecordDao.addTicketRecord(ticketRecordDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    @Override
    public void editTicketRecord(TicketRecord ticketRecord) {
        log.info("TicketRecordManagerImpl.editTicketRecord method");
        try {
            this.ticketRecordDao.editTicketRecord(ticketRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    @Override
    public void removeTicketRecord(Long ticketRecordId) {
        log.info("TicketRecordManagerImpl.removeTicketRecord method");
        try {
            this.ticketRecordDao.removeTicketRecord(ticketRecordId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    @Override
    public TicketRecord getTicketRecord(Long ticketRecordId) {
        log.info("TicketRecordManagerImpl.getTicketRecord method");
        try {
            return this.ticketRecordDao.getTicketRecord(ticketRecordId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    @Override
    public List<TicketRecord> getTicketRecords() {
        log.info("TicketRecordManagerImpl.getTicketRecords method");
        try {
            return this.ticketRecordDao.getTicketRecords();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
