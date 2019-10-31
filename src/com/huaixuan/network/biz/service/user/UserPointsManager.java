package com.huaixuan.network.biz.service.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.user.UserPoints;

public interface UserPointsManager {
    public void addUserPoints(UserPoints userPoints);

    /**
     *
     * @param userPoints
     */
    public void editUserPoints(UserPoints userPoints);

    /**
     *
     * @param userPointsId
     */
    public void removeUserPoints(Long userPointsId);

    /**
     *
     *
     * @param userPointsId
     * @return
     */
    public UserPoints getUserPoints(Long userPointsId);

    /**
     * л
     *
     * @return
     */
//    public List<UserPoints> getUserPoints(Map parm, Page page);

    public List<UserPoints> getUserPointssExportList(UserPoints userPoints);

    /**
     * 
     *
     * @return
     * @throws Exception
     */
    Integer getUserPointsCount(Map parm);

    /**
     * 
     *
     * @param userNick
     * @return
     */
    UserPoints getUserPointsByUserNick(String userNick);

    /**根据会员帐号统计其是否已存在积分记录
     * @param userNice
     * @return
     */
    public Integer getCountByUserNick(String userNice);

    /**
     * 获取所有用户（未被冻结）的可用积分
     * @return List<UserPoints>
     */
    public List<UserPoints> getUserPointsList();

    /**
     * 根据会员Id统计其是否已存在积分记录
     *
     * @param userId 不为空、null
     * @return  返回记录条数，没有返回0
     * @throws Exception
     */
    public UserPoints getUserPointsByUserId(String userId) throws Exception;
}
