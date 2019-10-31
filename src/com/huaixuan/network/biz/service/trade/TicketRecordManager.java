package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TicketRecord;

public interface TicketRecordManager {
    /* @interface model: ���һ��TicketRecord��¼ */
    public void addTicketRecord(TicketRecord ticketRecord);

    /* @interface model: ����һ��TicketRecord��¼ */
    public void editTicketRecord(TicketRecord ticketRecord);

    /* @interface model: ɾ��һ��TicketRecord��¼ */
    public void removeTicketRecord(Long ticketRecordId);

    /* @interface model: ��ѯһ��TicketRecord�����,����TicketRecord���� */
    public TicketRecord getTicketRecord(Long ticketRecordId);

    /* @interface model: ��ѯ����TicketRecord�����,����TicketRecord����ļ��� */
    public List<TicketRecord> getTicketRecords();
}
