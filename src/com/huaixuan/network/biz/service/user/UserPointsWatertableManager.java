package com.huaixuan.network.biz.service.user;

import com.huaixuan.network.biz.domain.user.UserPointsWatertable;


public interface UserPointsWatertableManager {
    /**
     * 
     * @param userPointsWatertable
     */
    public void addUserPointsWatertable(UserPointsWatertable userPointsWatertable);

    /**
     * 
     * @param userPointsWatertable
     */
    public void editUserPointsWatertable(UserPointsWatertable userPointsWatertable);

    /**
     * 
     * @param userPointsWatertableId
     */
    public void removeUserPointsWatertable(Long userPointsWatertableId);

    /**
     * 
     * @param userPointsWatertableId
     * @return
     */
    public UserPointsWatertable getUserPointsWatertable(Long userPointsWatertableId);

    
    /**
     * 
     * 
     * @param page
     * @return
     */
//    public PageUtil<UserPointsWatertable> getUserPointsWatertables(Page page);
    
    
    /**
     * 
     * 
     * @param userNick null
     * @param page
     * @return
     */
//    public PageUtil<UserPointsWatertable> getWatertablesByUserNick(String userNick, Page page);
    
        
    /**
     * type()()
     * 
     * @param pointsWaterable 4null
     * @param page 
     * @return
     * @throws Exception
     */
//    public PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable, Page page);
    

    /**
     * type()()
     * 
     * @param pointsWaterable 4null
     * @return
     * @throws Exception
     */
//    public PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable);
    
    
    /**
     * Count
     * @return
     * @throws Exception
     */
    Integer getUserPointsWatertablesCount();
    
    
    /**
     * Count
     * 
     * @param userNick
     * @return
     * @throws Exception
     */
    Integer getWatertablesByUserNickCount(String userNick);
    
    
    /**
     * type(),
     * 
     * @param pointsWaterable
     * @return
     * @throws Exception
     */
    Integer getWatertablesByComConditionCount(UserPointsWatertable pointsWaterable);    
    
    
}
