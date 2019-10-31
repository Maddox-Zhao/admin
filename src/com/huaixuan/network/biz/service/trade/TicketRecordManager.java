package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TicketRecord;

public interface TicketRecordManager {
    /* @interface model: 添加一条TicketRecord记录 */
    public void addTicketRecord(TicketRecord ticketRecord);

    /* @interface model: 更新一条TicketRecord记录 */
    public void editTicketRecord(TicketRecord ticketRecord);

    /* @interface model: 删除一条TicketRecord记录 */
    public void removeTicketRecord(Long ticketRecordId);

    /* @interface model: 查询一个TicketRecord结果集,返回TicketRecord对象 */
    public TicketRecord getTicketRecord(Long ticketRecordId);

    /* @interface model: 查询所有TicketRecord结果集,返回TicketRecord对象的集合 */
    public List<TicketRecord> getTicketRecords();
}
