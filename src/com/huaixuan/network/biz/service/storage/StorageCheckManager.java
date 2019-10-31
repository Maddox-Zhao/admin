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
 * �����Զ����(bibleUtil auto code generation)
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
     * �̵��ܼ�¼��
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
     * �̵��Ʒ��ϸ�б�
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
     * ����Ĳ�ѯ��
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
     * �����ӯ����
     * @param parameterMap
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public Map finishZeroStorageCheck(Map parameterMap);

    /**
     * �̵���ϸ�Ĳ�ѯ
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
     * �����̵���ϸ
     * @param locId
     * @param goodsInsId
     * @param checkId
     */
    public void addStorCheckDetail(long locId, long goodsInsId, long checkId);

    /**
     * ��������ͳ���̵���ϸ����
     * @param parameterMap
     * @return
     */
    public List<StorageCheckDetail> getCheckDetailCountByMap(Map parameterMap);

    public List<StockAge> getStockAgeAnalysisDataListsByParameterMap(Map parMap);
}
