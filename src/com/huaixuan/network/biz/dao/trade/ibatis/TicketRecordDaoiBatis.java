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
    
    /* @model: 添加一条TicketRecord记录 */
    @Override
    public void addTicketRecord(TicketRecord ticketRecord) throws Exception {
        this.sqlMapClient.insert("addTicketRecord", ticketRecord);
    }

    /* @model: 更新一条TicketRecord记录 */
    @Override
    public void editTicketRecord(TicketRecord ticketRecord) throws Exception {
        this.sqlMapClient.update("editTicketRecord", ticketRecord);
    }

    /* @model: 删除一条TicketRecord记录 */
    @Override
    public void removeTicketRecord(Long ticketRecordId) throws Exception {
        this.sqlMapClient.delete("removeTicketRecord", ticketRecordId);
    }

    /* @model: 查询一个TicketRecord结果集,返回TicketRecord对象 */
    @Override
    public TicketRecord getTicketRecord(Long ticketRecordId) throws Exception {
        return (TicketRecord) this.sqlMapClient.queryForObject("getTicketRecord",
            ticketRecordId);
    }

    
    /* @model: 查询所有TicketRecord结果集,返回TicketRecord对象的集合 */
    @Override
    public List<TicketRecord> getTicketRecords() throws Exception {
        return this.sqlMapClient.queryForList("getTicketRecords", null);
    }
}
