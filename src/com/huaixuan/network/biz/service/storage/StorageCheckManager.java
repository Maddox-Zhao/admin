package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageCheck;
import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;
import com.huaixuan.network.biz.domain.storage.query.StorageCheckQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 锟斤拷锟斤拷锟皆讹拷锟斤拷锟(bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface StorageCheckManager {
    /**
     *
     * @param storageCheck
     * @return
     */
    public Long addStorageCheck(StorageCheck storageCheck);

    /**
     *
     * @param storageCheck
     */
    public void editStorageCheck(StorageCheck storageCheck);

    /**
     *
     * @param storageCheckId
     */
    public void removeStorageCheck(Long storageCheckId);

    /**
     *
     * @param storageCheckId
     * @return
     */
    public StorageCheck getStorageCheck(Long storageCheckId);

    /**
     *
     * @return
     */
    public List<StorageCheck> getStorageChecks();

    /**
     * 盘点总记录表
     * @param parameterMap
     * @param page
     * @return
     */

    public QueryPage getStorageChecksByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize);

    /**
     *
     * @param parameterMap
     * @return
     */
    public int getStorageChecksByCountParameterMap(Map parameterMap);

    /**
     * 盘点产品明细列表
     * @param parameterMap
     * @param page
     * @return
     */
    public QueryPage getStorageCheckListsByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize, boolean isPage);

    /**
     *
     * @param parameterMap
     * @return
     */
    public int getStorageCheckListsByCountParameterMap(Map parameterMap);

    /**
     * 库存表的查询
     * @param parameterMap
     * @return
     */
    public List<Storage> getStorageByParameterMap(Map parameterMap);

    /**
     *
     * @param parameterMap
     * @return
     */
    public List<Storage> getStorageAndCheckDetailByParameterMap(Map parameterMap);

    /**
     *
     * @param parameterMap
     */
    public void updateStorage(Map parameterMap);

    /**
     *
     * @param parameterMap
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public Map finishStoreCheck(Map parameterMap);

    /**
     * 完成盘盈操作
     * @param parameterMap
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public Map finishZeroStorageCheck(Map parameterMap);

    /**
     * 盘点明细的查询
     * @param parameterMap
     * @return
     */
    public int getStorageCheckDetailByCountParameterMap(Map parameterMap);

    /**
     *
     * @param parameterMap
     * @param page
     * @return
     */
    public QueryPage getStorageCheckDetailByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize, boolean isPage);

    /**
     * 生成盘点明细
     * @param locId
     * @param goodsInsId
     * @param checkId
     */
    public void addStorCheckDetail(long locId, long goodsInsId, long checkId);

    /**
     * 根据条件统计盘点明细数量
     * @param parameterMap
     * @return
     */
    public List<StorageCheckDetail> getCheckDetailCountByMap(Map parameterMap);

    public List<StockAge> getStockAgeAnalysisDataListsByParameterMap(Map parMap);
}
