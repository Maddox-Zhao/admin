package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TicketRecord;

public interface TicketRecordDao {

    /* @interface model: 添加一条TicketRecord记录 */ 
    void addTicketRecord(TicketRecord ticketRecord) throws Exception; 
  
    /* @interface model: 更新一条TicketRecord记录 */ 
    void editTicketRecord(TicketRecord ticketRecord) throws Exception; 
  
    /* @interface model: 删除一条TicketRecord记录 */ 
    void removeTicketRecord(Long ticketRecordId) throws Exception; 
  
    /* @interface model: 查询一个TicketRecord结果集,返回TicketRecord对象 */ 
    TicketRecord getTicketRecord(Long ticketRecordId) throws Exception; 
  
    /* @interface model: 查询所有TicketRecord结果集,返回TicketRecord对象的集合 */ 
    List <TicketRecord> getTicketRecords() throws Exception; 
}
