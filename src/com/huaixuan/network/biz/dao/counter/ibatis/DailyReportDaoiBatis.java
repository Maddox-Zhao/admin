package com.huaixuan.network.biz.dao.counter.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.DailyReportDao;
import com.huaixuan.network.biz.domain.counter.DailyReport;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @version $Id: DailyReportDaoiBatis.java,v 0.1 2010-6-7 下午2:28:25  Exp $
 */
@Service("dailyReportDao")
public class DailyReportDaoiBatis implements DailyReportDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    protected final Log          log = LogFactory.getLog(this.getClass());

    /**
     * 根据类型和时间取得数捄
     * @param searchMap
     * @return
     * @see com.hundsun.bible.dao.counter.DailyReportDao#getDailyReportByTypeAndDate(java.util.Map)
     */
    public List<DailyReport> getDailyReportByTypeAndDate(Map<String, String> searchMap) {
        return this.sqlMapClientTemplate.queryForList("getDailyReportByTypeAndDate", searchMap);
    }

    /**
     * 批量径ep_daily_report 表里插入数据
     * @param dailyReport
     * @return
     * @see com.hundsun.bible.dao.counter.DailyReportDao#dailyReportBatchInsert(java.util.List)
     */
    public int dailyReportBatchInsert(List<DailyReport> dailyReport) {
        return this.executeBatchOperation("dailyReport-insert", dailyReport, "insert");
    }

    private int executeBatchOperation(final String statementName, final List parameterList,
                                      final String flag) {
        Long exectuteSucValue = null;
        exectuteSucValue = (Long) sqlMapClientTemplate.execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                Long states = Long.valueOf(0);
                try {
                    executor.startBatch();
                    for (int i = 0; i < parameterList.size(); i++) {
                        if (flag.equals("update")) {
                            executor.update(statementName, parameterList.get(i));
                        } else if (flag.equals("insert")) {
                            executor.insert(statementName, parameterList.get(i));
                        } else if (flag.equals("delete")) {
                            executor.delete(statementName, parameterList.get(i));
                        }
                    }

                    executor.executeBatch();
                } catch (Exception e) {
                    states = Long.valueOf(-1);
                    log.error(e);
                }
                return states;

            }
        });
        if (exectuteSucValue.intValue() == -1) {
            throw new RuntimeException();
        }
        return parameterList.size();
    }

    /**
     * 取得有用户类型截至方法调用时间点前的帐户总余预
     * @return
     * @see com.hundsun.bible.dao.counter.DailyReportDao#getUserTypeAccountBalance()
     */
    public List<Map> getUserTypeAccountBalance() {
        return this.sqlMapClientTemplate.queryForList("getUserTypeAccountBalance");
    }

    public Map getCommissionNoAccountBalance() {
        return (Map) this.sqlMapClientTemplate.queryForObject("getCommissionNoAccountBalance");
    }
}
