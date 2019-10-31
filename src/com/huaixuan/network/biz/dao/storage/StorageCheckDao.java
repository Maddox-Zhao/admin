package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StorageCheck;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ�����(bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface StorageCheckDao {
    /* @interface model: ���һ��StorageCheck��¼ */
    Long addStorageCheck(StorageCheck storageCheck) throws Exception;

    /* @interface model: ����һ��StorageCheck��¼ */
    void editStorageCheck(StorageCheck storageCheck) throws Exception;

    /* @interface model: ɾ��һ��StorageCheck��¼ */
    void removeStorageCheck(Long storageCheckId) throws Exception;

    /* @interface model: ��ѯһ��StorageCheck����,����StorageCheck���� */
    StorageCheck getStorageCheck(Long storageCheckId) throws Exception;

    /* @interface model: ��ѯ����StorageCheck����,����StorageCheck����ļ���� */
    List<StorageCheck> getStorageChecks() throws Exception;

    @SuppressWarnings("unchecked")
    int getStorageChecksByCountParameterMap(Map parameterMap);

    @SuppressWarnings("unchecked")
    QueryPage getStorageChecksByParameterMap(Map parameterMap, int currentPage, int pageSize);

    /**
     * 根据盘点ID取得仓库数据
     * @param checkId Long
     * @return Depository
     * @author chenyan 2010/03/18
     */
    Long getDepFirstIdByCheckId(Long checkId);
}
