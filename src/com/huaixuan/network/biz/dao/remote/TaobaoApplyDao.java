package com.huaixuan.network.biz.dao.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoApply;

/**
 * �����Զ����(bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface TaobaoApplyDao {
    /* @interface model: ���һ��InterfaceApply��¼ */
    void addTaobaoApply(TaobaoApply taobaoApply) throws Exception;

    /* @interface model: ����һ��InterfaceApply��¼ */
    void editTaobaoApply(TaobaoApply taobaoApply) throws Exception;

    /* @interface model: ����һ��InterfaceApply��¼ */
    void editGmtSync(TaobaoApply taobaoApply) throws Exception;

    /* @interface model: ɾ��һ��InterfaceApply��¼ */
    void removeTaobaoApply(Long taobaoApplyId) throws Exception;

    /* @interface model: ��ѯһ��InterfaceApply���,����InterfaceApply���� */
    TaobaoApply getTaobaoApply(Long taobaoApplyId) throws Exception;

    TaobaoApply getTaobaoApplyByUserId(Long userId, String type) throws Exception;

    /* @interface model: ��ѯ����InterfaceApply���,����InterfaceApply����ļ��� */
    List<TaobaoApply> getTaobaoApplys() throws Exception;

    /**
     *
     * @param parameterMap
     * @param page
     * @return
     */
    List<TaobaoApply> getListByMap(Map parMap) throws Exception;

    /**
    *
    * @param parMap
    * @return
    */
    int getCountByMap(Map parMap) throws Exception;

    /**
     * �޸Ľ�������״̬
     * @param id
     * @param status
     * @param memo
     */
    void editTaobaoApplyStatus(Long id, String status, String memo) throws Exception;
    
    
    /**
     * ����ID�޸�ͬ�����ɶ���ʱ���������˾ID
     * @param id Long
     * @param ownExpressId Long
     * @author chenyan 2011/03/11
     */
    void modifyOwnExpressIdById(Long id, Long ownExpressId);
    
    /**
     * ����ID�޸�ͬ����������״̬ʱ���������˾CODE
     * @param id Long
     * @param ownExpressId Long
     * @author chenyan 2011/03/11
     */
    void modifyInterfaceExpressCodeById(Long id, String interfaceExpressCode);
}
