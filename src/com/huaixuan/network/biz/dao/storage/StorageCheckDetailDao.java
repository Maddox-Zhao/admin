package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface StorageCheckDetailDao  {
    /* @interface model: ���һ��StorageCheckDetail��¼ */
    void addStorageCheckDetail(StorageCheckDetail storageCheckDetail) throws Exception;

    /* @interface model: ����һ��StorageCheckDetail��¼ */
    void editStorageCheckDetail(StorageCheckDetail storageCheckDetail) throws Exception;

    /* @interface model: ɾ��һ��StorageCheckDetail��¼ */
    void removeStorageCheckDetail(Long storageCheckDetailId) throws Exception;

    /* @interface model: ��ѯһ��StorageCheckDetail�������StorageCheckDetail���� */
    StorageCheckDetail getStorageCheckDetail(Long storageCheckDetailId) throws Exception;

    /* @interface model: ��ѯ����StorageCheckDetail�������StorageCheckDetail����ļ���*/
    List<StorageCheckDetail> getStorageCheckDetails() throws Exception;

    @SuppressWarnings("unchecked")
    QueryPage getStorageCheckDetailsByParameterMap(Map parameterMap, int currentPage, int pageSize, boolean isPage);

    @SuppressWarnings("unchecked")
    List<StorageCheckDetail> getAllStorageCheckDetailsByParameterMap(Map parameterMap);

    @SuppressWarnings("unchecked")
    int getStorageCheckDetailsCountByParameterMap(Map parameterMap);

    @SuppressWarnings("unchecked")
    int sumStorageCheckDetailByParameterMap(Map parameterMap);

    @SuppressWarnings("unchecked")
    List<StorageCheckDetail> getCheckDetailCountByMap(Map parameterMap);

    @SuppressWarnings("unchecked")
    List<StockAge> getStockAgeAnalysisDataListsByParameterMap(Map parMap);
    
    List<StorageCheckDetail> getStorageCheckDetailsByParameterMap(Map parameterMap);
}
