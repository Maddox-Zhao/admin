package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageMoveLog;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����(bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface StorageMoveLogManager {
    /**
     *
     * @param storageMoveLog
     */
    public void addStorageMoveLog(StorageMoveLog storageMoveLog);

    /**
     *
     * @param storageMoveLog
     */
    public void editStorageMoveLog(StorageMoveLog storageMoveLog);

    /**
     *
     * @param storageMoveLogId
     */
    public void removeStorageMoveLog(Long storageMoveLogId);

    /**
     *
     * @param storageMoveLogId
     * @return
     */
    public StorageMoveLog getStorageMoveLog(Long storageMoveLogId);

    /**
     *
     * @param parMap
     * @param page
     * @return
     */
    public QueryPage getStorageMoveLogsByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize);

    /**
     *
     * @param parMap
     * @return
     */
    public int getStorageMoveLogsCountByMap(Map<String, String> parMap);

    /**
     * �����ƿ�/����Ų�ѯ�ƿ�/����¼����
     * @param moveCode
     * @return
     */
    public QueryPage getStorageMoveLogsByMoveCode(Map<String, String> parMap, int currentPage, int pageSize);

    /**
     * �����ƿ�/����Ų�ѯ�ƿ�/����¼��
     * @param moveCode
     * @return
     */
    public int getStorageMoveLogsCountByMoveCode(Map<String, String> parMap);

    /**
     * ��������ͳ���ƶ��ƶ���Ʒ������
     * @param moveCode
     * @return
     */
    public int sumMoveNumByMap(Map<String, String> parMap);

    /**
     * ����������ѯ�ƿ���������б�
     * @param moveCode
     * @return
     */
    public QueryPage getMoreDetailByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize, boolean isPage);

    /**
     * �ƿ��������
     * @param storageList ����¼����
     * @param newdepfirstId  ��һ���ֿ�ID
     * @param newDepId  �²ֿ�ID
     * @param newLocId  �¿�λID
     * @param moveNum  �ƶ�����
     * @param user  ������
     * @param actionType  ��������(1���ƿ⣻2������)
     * @param memo ��ע
     * @return
     */
    @Transactional
    public Map moveToOtherLoc(List<Storage> storageList, Long newdepfirstId, Long newDepId, Long newLocId,
                              String[] moveNum, String user, String actionType,String memo);

    /**
     * �ص�����
     * @param storageMoveLogList  Ҫ�ص����ƿ��¼����
     * @return
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public Map backToStorage(List<StorageMoveLog> storageMoveLogList, String user);

    public Integer getMoveOrdersByMapCount(Map<String, String> parMap);

    public QueryPage getMoveOrdersDetailByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize);

    /**
     * ���������õ������ѹ黹�����ܺ�
     * @param parMap
     * @return
     */
    public StorageMoveLog getSumNumByMap(MoveStorageQuery moveStorageQuery);
}
