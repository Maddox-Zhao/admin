package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.TicketRecordDao;
import com.huaixuan.network.biz.domain.trade.TicketRecord;

@Repository("ticketRecordDao")
public class TicketRecordDaoiBatis implements TicketRecordDao{

    @Autowired
    private SqlMapClientTemplate sqlMapClient;
    
    /* @model: ���һ��TicketRecord��¼ */
    @Override
    public void addTicketRecord(TicketRecord ticketRecord) throws Exception {
        this.sqlMapClient.insert("addTicketRecord", ticketRecord);
    }

    /* @model: ����һ��TicketRecord��¼ */
    @Override
    public void editTicketRecord(TicketRecord ticketRecord) throws Exception {
        this.sqlMapClient.update("editTicketRecord", ticketRecord);
    }

    /* @model: ɾ��һ��TicketRecord��¼ */
    @Override
    public void removeTicketRecord(Long ticketRecordId) throws Exception {
        this.sqlMapClient.delete("removeTicketRecord", ticketRecordId);
    }

    /* @model: ��ѯһ��TicketRecord�����,����TicketRecord���� */
    @Override
    public TicketRecord getTicketRecord(Long ticketRecordId) throws Exception {
        return (TicketRecord) this.sqlMapClient.queryForObject("getTicketRecord",
            ticketRecordId);
    }

    
    /* @model: ��ѯ����TicketRecord�����,����TicketRecord����ļ��� */
    @Override
    public List<TicketRecord> getTicketRecords() throws Exception {
        return this.sqlMapClient.queryForList("getTicketRecords", null);
    }
}
