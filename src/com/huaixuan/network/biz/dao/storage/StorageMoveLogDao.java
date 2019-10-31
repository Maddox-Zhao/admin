package com.huaixuan.network.biz.dao.storage;

 import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StorageMoveLog;
import com.huaixuan.network.biz.query.QueryPage;

 /**
  * @version 3.2.0
  */
 public interface StorageMoveLogDao {
 	void addStorageMoveLog(StorageMoveLog storageMoveLog) throws Exception;

 	void editStorageMoveLog(StorageMoveLog storageMoveLog) throws Exception;
 	
 	void removeStorageMoveLog(Long storageMoveLogId) throws Exception;

 	StorageMoveLog getStorageMoveLog(Long storageMoveLogId) throws Exception;

 	QueryPage getStorageMoveLogsByMap(Map parMap, int currentPage, int pageSize);

 	int getStorageMoveLogsCountByMap(Map<String, String> parMap);

 	int getBorrowedMoveLogsCountByMap(Map<String, String> parMap);

 	/*
     * ��������ͳ���ƶ��ƶ���Ʒ������
     * @param moveCode
     * @return
     */
    int sumMoveNumByMap(Map<String, String> parMap)throws Exception;

    /*
     * ����������ѯ�ƿ���������б�
     * @param moveCode
     * @return
     */
    QueryPage getMoreDetailByMap(Map<String, String> parMap, int currentPage, int pageSize, boolean isPage)throws Exception;

 	/*
     * �����ƿ�/����Ų�ѯ�ƿ�/����¼����
     * @param moveCode
     * @return
     */
    QueryPage getStorageMoveLogsByMoveCode(Map parMap, int currentPage, int pageSize) throws Exception;

    /*
     * �����ƿ�/����Ų�ѯ�ƿ�/����¼��
     * @param moveCode
     * @return
     */
    int getStorageMoveLogsCountByMoveCode(Map<String, String> parMap) throws Exception;

    int getMoveOrdersCountByMap(Map<String, String> parMap) throws Exception;

    QueryPage getMoveOrdersDetailByMap(Map parMap, int currentPage, int pageSize) throws Exception;

    /**
     * ���������õ������ѹ黹�����ܺ�
     * @param parMap
     * @return
     */
    public StorageMoveLog getSumNumByMap(Map<String, String> parMap) throws Exception;
 }
