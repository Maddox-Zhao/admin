package com.huaixuan.network.biz.dao.user;

import com.huaixuan.network.biz.domain.user.UserPointsWatertable;


public interface UserPointsWatertableDao {
    /**
     * 新增对象
     * 
     * @param userPointsWatertable
     * @throws Exception
     */
    void addUserPointsWatertable(UserPointsWatertable userPointsWatertable) throws Exception;
    

    /* @interface model: UserPointsWatertable */
    void editUserPointsWatertable(UserPointsWatertable userPointsWatertable) throws Exception;

    /* @interface model: UserPointsWatertable */
    void removeUserPointsWatertable(Long userPointsWatertableId) throws Exception;

    /* @interface model: UserPointsWatertable,UserPointsWatertable */
    UserPointsWatertable getUserPointsWatertable(Long userPointsWatertableId) throws Exception;

        
    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询，以创建时间为序
     * 
     * @param pointsWaterable 流水对象用来封装查询条件，不为null
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable, int currentPage, int pageSize, int totalCount) throws Exception;
    
    
    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询，以创建时间为序
     * 
     * @param pointsWaterable 流水对象用来封装查询条件，不为null
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable) throws Exception;
    
    
    
    /**
     * 查询某个会员帐号会员积分流水，以创建时间为序
     * 
     * @param userNick 会员帐号，不为能null和空
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getWatertablesByUserNick(String userNick, int currentPage, int pageSize, int totalCount) throws Exception;

    
    /**
     * 查询全部流水，以创建时间为序
     * 
     * @param page
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getUserPointsWatertables(Page page) throws Exception;
    
        
    /**
     * 全部流水Count
     * @return
     * @throws Exception
     */
    Integer getUserPointsWatertablesCount() throws Exception;
    
    
    /**
     * 查询某个会员帐号会员积分流水Count
     * 
     * @param userNick
     * @return
     * @throws Exception
     */
    Integer getWatertablesByUserNickCount(String userNick) throws Exception;
    
    
    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询,获取总数
     * 
     * @param pointsWaterable
     * @return
     * @throws Exception
     */
    Integer getWatertablesByComConditionCount(UserPointsWatertable pointsWaterable) throws Exception;
    
}
