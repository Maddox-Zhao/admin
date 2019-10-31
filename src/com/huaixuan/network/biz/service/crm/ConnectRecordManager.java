package com.huaixuan.network.biz.service.crm;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.crm.ConnectRecord;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ά����¼
 * @author chenyan 2010/08/04
 *
 */
public interface ConnectRecordManager {

    /**
     * ���ԤԼ���߹�ͨ��¼
     * @param connectRecord ConnectRecord
     * @return Long
     * @author chenyan 2010/08/05
     */
    Long addConnectRecord(ConnectRecord connectRecord);

    /**
     * ����ѡ���������ѯ�����������
     * @param parMap Map
     * @return int
     * @author chenyan 2010/08/05
     */
    int listConnectRecordByParameterCount(Map parMap);

    /**
     * ����ѡ����������м���
     * @param parMap Map
     * @param page Page
     * @return List
     * @author chenyan 2010/08/05
     */
    @SuppressWarnings("unchecked")
    QueryPage listConnectRecordByParameter(Map parMap, int currentPage, int pageSize);

    /**
     * ����Idȡ��ԤԼ���߹�ͨ��¼
     * @param id Long
     * @return ConnectRecord
     * @author chenyan 2010/08/05
     */
    ConnectRecord getConnectRecordById(Long id);

    /**
     * ����Id����ԤԼ���߹�ͨ��¼
     * @param connectRecord ConnectRecord
     * @return int
     * @author chenyan 2010/08/05
     */
    int updateConnectRecordById(ConnectRecord connectRecord);

    /**
     * ����Id����ԤԼ���߹�ͨ��¼��״̬
     * @param connectRecord ConnectRecord
     * @return int
     * @author chenyan 2010/08/05
     */
    int updateConnectRecordStatusById(ConnectRecord connectRecord);

    /**
     * �����û�ID��ѯ��¼
     * @param parMap Map
     * @return List
     * @author chenyan 2010/09/09
     */
    List<ConnectRecord> ListConnectRecordByUserId(Map parMap);
}
