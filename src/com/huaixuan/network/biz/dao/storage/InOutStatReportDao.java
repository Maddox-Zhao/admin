package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.InOutStatReport;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����(bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface InOutStatReportDao {
    /* @interface model: ���һ��InOutStatReport��¼ */
    void addInOutStatReport(InOutStatReport inOutStatReport) throws Exception;

    /* @interface model: ����һ��InOutStatReport��¼ */
    void editInOutStatReport(InOutStatReport inOutStatReport) throws Exception;

    /* @interface model: ɾ��һ��InOutStatReport��¼ */
    void removeInOutStatReport(Long inOutStatReportId) throws Exception;

    /* @interface model: ��ѯһ��InOutStatReport���,����InOutStatReport���� */
    InOutStatReport getInOutStatReport(Long inOutStatReportId) throws Exception;

    /* @interface model: ��ѯ����InOutStatReport���,����InOutStatReport����ļ��� */
    List<InOutStatReport> getInOutStatReports() throws Exception;

    int getInOutStatReportCountByMap(Map parMap);

    List<InOutStatReport> getInOutStatReportListByMap(Map parMap);

    QueryPage getInOutStatReportListByMap(Map parMap, int currentPage, int pageSize);
}
