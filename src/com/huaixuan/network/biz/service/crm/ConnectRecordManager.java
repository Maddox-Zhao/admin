package com.huaixuan.network.biz.service.crm;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.crm.ConnectRecord;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 维护记录
 * @author chenyan 2010/08/04
 *
 */
public interface ConnectRecordManager {

    /**
     * 添加预约或者沟通记录
     * @param connectRecord ConnectRecord
     * @return Long
     * @author chenyan 2010/08/05
     */
    Long addConnectRecord(ConnectRecord connectRecord);

    /**
     * 根据选择的条件查询检索结果总数
     * @param parMap Map
     * @return int
     * @author chenyan 2010/08/05
     */
    int listConnectRecordByParameterCount(Map parMap);

    /**
     * 根据选择的条件进行检索
     * @param parMap Map
     * @param page Page
     * @return List
     * @author chenyan 2010/08/05
     */
    @SuppressWarnings("unchecked")
    QueryPage listConnectRecordByParameter(Map parMap, int currentPage, int pageSize);

    /**
     * 根据Id取得预约或者沟通记录
     * @param id Long
     * @return ConnectRecord
     * @author chenyan 2010/08/05
     */
    ConnectRecord getConnectRecordById(Long id);

    /**
     * 根据Id更新预约或者沟通记录
     * @param connectRecord ConnectRecord
     * @return int
     * @author chenyan 2010/08/05
     */
    int updateConnectRecordById(ConnectRecord connectRecord);

    /**
     * 根据Id更新预约或者沟通记录的状态
     * @param connectRecord ConnectRecord
     * @return int
     * @author chenyan 2010/08/05
     */
    int updateConnectRecordStatusById(ConnectRecord connectRecord);

    /**
     * 根据用户ID查询记录
     * @param parMap Map
     * @return List
     * @author chenyan 2010/09/09
     */
    List<ConnectRecord> ListConnectRecordByUserId(Map parMap);
}
