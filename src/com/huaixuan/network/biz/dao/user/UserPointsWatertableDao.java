package com.huaixuan.network.biz.dao.user;

import com.huaixuan.network.biz.domain.user.UserPointsWatertable;


public interface UserPointsWatertableDao {
    /**
     * ��������
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
     * ���ݻ�Ա�ʺš�����ʱ�䡢��type(����)Ϊ������ѯ���Դ���ʱ��Ϊ��
     * 
     * @param pointsWaterable ��ˮ����������װ��ѯ��������Ϊnull
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable, int currentPage, int pageSize, int totalCount) throws Exception;
    
    
    /**
     * ���ݻ�Ա�ʺš�����ʱ�䡢��type(����)Ϊ������ѯ���Դ���ʱ��Ϊ��
     * 
     * @param pointsWaterable ��ˮ����������װ��ѯ��������Ϊnull
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable) throws Exception;
    
    
    
    /**
     * ��ѯĳ����Ա�ʺŻ�Ա������ˮ���Դ���ʱ��Ϊ��
     * 
     * @param userNick ��Ա�ʺţ���Ϊ��null�Ϳ�
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getWatertablesByUserNick(String userNick, int currentPage, int pageSize, int totalCount) throws Exception;

    
    /**
     * ��ѯȫ����ˮ���Դ���ʱ��Ϊ��
     * 
     * @param page
     * @return
     * @throws Exception
     */
//    PageUtil<UserPointsWatertable> getUserPointsWatertables(Page page) throws Exception;
    
        
    /**
     * ȫ����ˮCount
     * @return
     * @throws Exception
     */
    Integer getUserPointsWatertablesCount() throws Exception;
    
    
    /**
     * ��ѯĳ����Ա�ʺŻ�Ա������ˮCount
     * 
     * @param userNick
     * @return
     * @throws Exception
     */
    Integer getWatertablesByUserNickCount(String userNick) throws Exception;
    
    
    /**
     * ���ݻ�Ա�ʺš�����ʱ�䡢��type(����)Ϊ������ѯ,��ȡ����
     * 
     * @param pointsWaterable
     * @return
     * @throws Exception
     */
    Integer getWatertablesByComConditionCount(UserPointsWatertable pointsWaterable) throws Exception;
    
}
