package com.huaixuan.network.biz.service.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.query.InterfaceApplyQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**  
 * �����Զ����(bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public interface InterfaceApplyManager {
    /* @interface model: ���һ��InterfaceApply��¼ */
    public void addInterfaceApply(InterfaceApply interfaceApply);

    /* @interface model: ����һ��InterfaceApply��¼ */
    public void editInterfaceApply(InterfaceApply interfaceApply);

    /* @interface model: ����һ��InterfaceApply��¼ */
    public void editGmtSync(InterfaceApply interfaceApply);

    /* @interface model: ɾ��һ��InterfaceApply��¼ */
    public void removeInterfaceApply(Long interfaceApplyId);

    /* @interface model: ��ѯһ��InterfaceApply���,����InterfaceApply���� */
    public InterfaceApply getInterfaceApply(Long interfaceApplyId);

    public InterfaceApply getInterfaceApplyByUserId(Long userId, String type);

    /* @interface model: ��ѯ����InterfaceApply���,����InterfaceApply����ļ��� */
    public List<InterfaceApply> getInterfaceApplys();

    /**
     * 
     * @param parameterMap
     * @param page
     * @return
     */
    public List<InterfaceApply> getListByMap(Map parMap);

    QueryPage getListByMap(InterfaceApplyQuery query, int currPage, int pageSize);

    /**
     * 
     * @param parMap
     * @return
     */
    public int getCountByMap(Map parMap);

    /**
     * 修改接入申请状�
     * @param id
     * @param status
     */
    public void editInterfaceApplyStatus(Long id, String status);
}
