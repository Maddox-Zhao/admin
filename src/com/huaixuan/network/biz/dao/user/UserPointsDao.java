package com.huaixuan.network.biz.dao.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.user.UserPoints;

public interface UserPointsDao {
    /**
     * 增加新的会员积分记录
     *
     * @param userPoints 不为null
     * @throws Exception
     */
    void addUserPoints(UserPoints userPoints) throws Exception;

    /**
     *  修改积分:下单(order)冻结、支付(pay)使用积分,冻结积分减少、
     *  取消订单(cancelOrder)解冻、交易成功(transaction)发放积分
     *
     * @param userPoints 不为null
     * @throws Exception
     */
    void editUserPoints(UserPoints userPoints) throws Exception;

    /* @interface model: UserPoints */
    void removeUserPoints(Long userPointsId) throws Exception;

    /* @interface model: UserPoints,UserPoints */
    UserPoints getUserPoints(Long userPointsId) throws Exception;

    /**
     * 根据会员帐号统计其是否已存在积分记录
     *
     * @param userNick 不为空、null
     * @return  返回记录条数，没有返回0
     * @throws Exception
     */
    Integer getCountByUserNick(String userNick) throws Exception;

    /**
     * 根据会员帐号查询积分情况
     *
     * @param userNick 不为空、null
     * @return UserPoints，没有查询到则返回null
     * @throws Exception
     */
    UserPoints getUserPointsByUserNick(String userNick) throws Exception;

    /**
     * 查询所有会员积分，以修改时间为序
     *
     * @param page
     * @return
     * @throws Exception
     */
//    List<UserPoints> getUserPoints(Map parm ,Page page) throws Exception;

    List<UserPoints> getUserPointssExportList(UserPoints userPoints) throws Exception;

    /**
     * 获取记录总数
     *
     * @return
     * @throws Exception
     */
    Integer getUserPointsCount(Map parm) throws Exception;

    /***
     * 获取用户积分信息
     * @return
     */
    List<UserPoints> getUserPointsList() throws Exception;

    /**
     * 根据会员Id统计其是否已存在积分记录
     *
     * @param userId 不为空、null
     * @return  返回记录条数，没有返回0
     * @throws Exception
     */
    UserPoints getUserPointsByUserId(String userId) throws Exception;

}
