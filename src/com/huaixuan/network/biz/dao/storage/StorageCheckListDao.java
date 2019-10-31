package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StorageCheckList;
import com.huaixuan.network.biz.domain.storage.query.StorageCheckQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface StorageCheckListDao {
    /* @interface model: ���һ��StorageCheckList��¼ */
    void addStorageCheckList(StorageCheckList storageCheckList) throws Exception;

    /* @interface model: ����һ��StorageCheckList��¼ */
    void editStorageCheckList(StorageCheckList storageCheckList) throws Exception;

    /* @interface model: ɾ��һ��StorageCheckList��¼ */
    void removeStorageCheckList(Long storageCheckListId) throws Exception;

    /* @interface model: ��ѯһ��StorageCheckList�������StorageCheckList���� */
    StorageCheckList getStorageCheckList(Long storageCheckListId) throws Exception;

    /* @interface model: ��ѯ����StorageCheckList�������StorageCheckList����ļ���*/
    List<StorageCheckList> getStorageCheckLists() throws Exception;

    @SuppressWarnings("unchecked")
    int getStorageCheckListsByCountParameterMap(Map parameterMap);

    @SuppressWarnings("unchecked")
    QueryPage getStorageCheckListsByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize, boolean isPage);

    @SuppressWarnings("unchecked")
    List<StorageCheckList> getAllStorageCheckListsByParameterMap(Map parameterMap);
    
    List<StorageCheckList> getStorageCheckListsByParameterMap(Map parameterMap);
}
