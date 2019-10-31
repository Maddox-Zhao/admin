package com.huaixuan.network.biz.dao.counter;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.counter.DailyReport;

/**
 * 
 * @author
 * @version $Id: DailyReportDao.java,v 0.1 2010-6-7 ����2:28:25  Exp $
 */
public interface DailyReportDao {
    
    /**
     * �������ͺ�ʱ��ȡ������
     * @param searchMap
     * @return
     */
    public List <DailyReport> getDailyReportByTypeAndDate(Map<String,String> searchMap);
    
    /**
     * ������ep_daily_report �����������
     * @param dailyReport
     * @return
     */
    public int dailyReportBatchInsert(List<DailyReport> dailyReport);
    
    /**
     * ȡ�Ñ����û����ͽ�����������ʱ���ǰ���ʻ�����Ԥ
     * @return
     */
    public List<Map> getUserTypeAccountBalance();
    
    public Map getCommissionNoAccountBalance();
}
