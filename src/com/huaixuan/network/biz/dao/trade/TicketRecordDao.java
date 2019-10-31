package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TicketRecord;

public interface TicketRecordDao {

    /* @interface model: ���һ��TicketRecord��¼ */ 
    void addTicketRecord(TicketRecord ticketRecord) throws Exception; 
  
    /* @interface model: ����һ��TicketRecord��¼ */ 
    void editTicketRecord(TicketRecord ticketRecord) throws Exception; 
  
    /* @interface model: ɾ��һ��TicketRecord��¼ */ 
    void removeTicketRecord(Long ticketRecordId) throws Exception; 
  
    /* @interface model: ��ѯһ��TicketRecord�����,����TicketRecord���� */ 
    TicketRecord getTicketRecord(Long ticketRecordId) throws Exception; 
  
    /* @interface model: ��ѯ����TicketRecord�����,����TicketRecord����ļ��� */ 
    List <TicketRecord> getTicketRecords() throws Exception; 
}
