package com.huaixuan.network.biz.service.storage;

import java.util.List;

import com.huaixuan.network.biz.domain.storage.StorageCheckList;

/**
 * StorageCheckListManager
 * @version 3.2.0
 */
public interface StorageCheckListManager  {
    /**
     *
     * @param storageCheckList
     */
    public void addStorageCheckList(StorageCheckList storageCheckList);

    /**
     *
     * @param storageCheckList
     */
    public void editStorageCheckList(StorageCheckList storageCheckList);

    /**
     *
     * @param storageCheckListId
     */
    public void removeStorageCheckList(Long storageCheckListId);

    /**
     *
     * @param storageCheckListId
     * @return
     */
    public StorageCheckList getStorageCheckList(Long storageCheckListId);

    /**
     *
     * @return
     */
    public List<StorageCheckList> getStorageCheckLists();
}
