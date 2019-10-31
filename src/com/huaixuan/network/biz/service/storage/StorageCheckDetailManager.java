package com.huaixuan.network.biz.service.storage;

import java.util.List;

import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;

/**
 * StorageCheckDetailManager
 * @version 3.2.0
 */
public interface StorageCheckDetailManager {
    /**
     *
     * @param storageCheckDetail
     */
    public void addStorageCheckDetail(StorageCheckDetail storageCheckDetail);

    /**
     *
     * @param storageCheckDetail
     */
    public void editStorageCheckDetail(StorageCheckDetail storageCheckDetail);

    /**
     *
     * @param storageCheckDetailId
     */
    public void removeStorageCheckDetail(Long storageCheckDetailId);

    /**
     *
     * @param storageCheckDetailId
     * @return
     */
    public StorageCheckDetail getStorageCheckDetail(Long storageCheckDetailId);

    /**
     *
     * @return
     */
    public List<StorageCheckDetail> getStorageCheckDetails();
}
