package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageMoveLog;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 锟斤拷锟斤拷锟皆讹拷锟斤拷锟(bibleUtil auto code generation)
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
     * 根据移库/外借编号查询移库/外借记录集合
     * @param moveCode
     * @return
     */
    public QueryPage getStorageMoveLogsByMoveCode(Map<String, String> parMap, int currentPage, int pageSize);

    /**
     * 根据移库/外借编号查询移库/外借记录数
     * @param moveCode
     * @return
     */
    public int getStorageMoveLogsCountByMoveCode(Map<String, String> parMap);

    /**
     * 根据条件统计移动移动商品总数量
     * @param moveCode
     * @return
     */
    public int sumMoveNumByMap(Map<String, String> parMap);

    /**
     * 根据条件查询移库外借详情列表
     * @param moveCode
     * @return
     */
    public QueryPage getMoreDetailByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize, boolean isPage);

    /**
     * 移库调拨操作
     * @param storageList 库存记录集合
     * @param newdepfirstId  新一级仓库ID
     * @param newDepId  新仓库ID
     * @param newLocId  新库位ID
     * @param moveNum  移动数量
     * @param user  操作人
     * @param actionType  操作类型(1：移库；2：调拨)
     * @param memo 备注
     * @return
     */
    @Transactional
    public Map moveToOtherLoc(List<Storage> storageList, Long newdepfirstId, Long newDepId, Long newLocId,
                              String[] moveNum, String user, String actionType,String memo);

    /**
     * 回调操作
     * @param storageMoveLogList  要回调的移库记录集合
     * @return
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public Map backToStorage(List<StorageMoveLog> storageMoveLogList, String user);

    public Integer getMoveOrdersByMapCount(Map<String, String> parMap);

    public QueryPage getMoveOrdersDetailByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize);

    /**
     * 根据条件得到外借或已归还数量总和
     * @param parMap
     * @return
     */
    public StorageMoveLog getSumNumByMap(MoveStorageQuery moveStorageQuery);
}
