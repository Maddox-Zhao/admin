package com.huaixuan.network.biz.dao.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoApply;

/**
 * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface TaobaoApplyDao {
    /* @interface model: ï¿½ï¿½ï¿½Ò»ï¿½ï¿½InterfaceApplyï¿½ï¿½Â¼ */
    void addTaobaoApply(TaobaoApply taobaoApply) throws Exception;

    /* @interface model: ï¿½ï¿½ï¿½ï¿½Ò»ï¿½ï¿½InterfaceApplyï¿½ï¿½Â¼ */
    void editTaobaoApply(TaobaoApply taobaoApply) throws Exception;

    /* @interface model: ï¿½ï¿½ï¿½ï¿½Ò»ï¿½ï¿½InterfaceApplyï¿½ï¿½Â¼ */
    void editGmtSync(TaobaoApply taobaoApply) throws Exception;

    /* @interface model: É¾ï¿½ï¿½Ò»ï¿½ï¿½InterfaceApplyï¿½ï¿½Â¼ */
    void removeTaobaoApply(Long taobaoApplyId) throws Exception;

    /* @interface model: ï¿½ï¿½Ñ¯Ò»ï¿½ï¿½InterfaceApplyï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½InterfaceApplyï¿½ï¿½ï¿½ï¿½ */
    TaobaoApply getTaobaoApply(Long taobaoApplyId) throws Exception;

    TaobaoApply getTaobaoApplyByUserId(Long userId, String type) throws Exception;

    /* @interface model: ï¿½ï¿½Ñ¯ï¿½ï¿½ï¿½ï¿½InterfaceApplyï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½InterfaceApplyï¿½ï¿½ï¿½ï¿½Ä¼ï¿½ï¿½ï¿ */
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
     * ÐÞ¸Ä½ÓÈëÉêÇë×´Ì¬
     * @param id
     * @param status
     * @param memo
     */
    void editTaobaoApplyStatus(Long id, String status, String memo) throws Exception;
    
    
    /**
     * ¸ù¾ÝIDÐÞ¸ÄÍ¬²½Éú³É¶©µ¥Ê±ºòµÄÎïÁ÷¹«Ë¾ID
     * @param id Long
     * @param ownExpressId Long
     * @author chenyan 2011/03/11
     */
    void modifyOwnExpressIdById(Long id, Long ownExpressId);
    
    /**
     * ¸ù¾ÝIDÐÞ¸ÄÍ¬²½¶©µ¥·¢»õ×´Ì¬Ê±ºòµÄÎïÁ÷¹«Ë¾CODE
     * @param id Long
     * @param ownExpressId Long
     * @author chenyan 2011/03/11
     */
    void modifyInterfaceExpressCodeById(Long id, String interfaceExpressCode);
}
