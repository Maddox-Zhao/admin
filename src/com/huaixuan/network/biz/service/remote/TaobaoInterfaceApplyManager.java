/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-9
 */
package com.huaixuan.network.biz.service.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoApply;

/**
 * @author shengyong
 * @version $Id: TaobaoInterfaceApplyManager.java,v 0.1 2011-3-9 ����11:35:11 shengyong Exp $
 */
public interface TaobaoInterfaceApplyManager {

    /* @interface model: InterfaceApply */
    public void addInterfaceApply(TaobaoApply interfaceApply);

    /* @interface model: InterfaceApply */
    public void editInterfaceApply(TaobaoApply interfaceApply);

    /* @interface model: InterfaceApply */
    public void editGmtSync(TaobaoApply interfaceApply);

    /* @interface model: InterfaceApply */
    public void removeInterfaceApply(Long interfaceApplyId);

    /* @interface model: InterfaceApply,InterfaceApply */
    public TaobaoApply getInterfaceApply(Long interfaceApplyId);

    public TaobaoApply getInterfaceApplyByUserId(Long userId, String type);

    /* @interface model: InterfaceApply,InterfaceApply */
    public List<TaobaoApply> getInterfaceApplys();

    /**
     *
     * @param parameterMap
     * @param page
     * @return
     */
    public List<TaobaoApply> getListByMap(Map parMap);

    /**
     *
     * @param parMap
     * @return
     */
    public int getCountByMap(Map parMap);

    /**
     * �޸Ľ�������״̬
     * @param id
     * @param status
     * @param memo
     */
    public void editInterfaceApplyStatus(Long id, String status, String memo);
    
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
