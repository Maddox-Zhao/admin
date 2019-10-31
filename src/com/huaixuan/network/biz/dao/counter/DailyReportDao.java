package com.huaixuan.network.biz.dao.counter;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.counter.DailyReport;

/**
 * 
 * @author
 * @version $Id: DailyReportDao.java,v 0.1 2010-6-7 下午2:28:25  Exp $
 */
public interface DailyReportDao {
    
    /**
     * 根据类型和时间取得数捄
     * @param searchMap
     * @return
     */
    public List <DailyReport> getDailyReportByTypeAndDate(Map<String,String> searchMap);
    
    /**
     * 批量径ep_daily_report 表里插入数据
     * @param dailyReport
     * @return
     */
    public int dailyReportBatchInsert(List<DailyReport> dailyReport);
    
    /**
     * 取得扄有用户类型截至方法调用时间点前的帐户总余预
     * @return
     */
    public List<Map> getUserTypeAccountBalance();
    
    public Map getCommissionNoAccountBalance();
}
