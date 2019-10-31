package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.MailtaskDao;
import com.huaixuan.network.biz.domain.user.MailTask;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("mailtaskDao")
public class MailtaskDaoiBatis implements MailtaskDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addMailTask(MailTask mailTask) throws Exception {
        this.sqlMapClient.insert("addMailTask", mailTask);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getMailTaskList(Map parMap, int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(new MailTask());
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient.queryForObject("getMailTaskListCount", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("getMailTaskList", parMap));
        }
        return queryPage;
    }

    @Override
    public void updateMailTask(MailTask mailTask) {
        this.sqlMapClient.update("updateMailTask", mailTask);
    }
}
