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
     * ��
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

    /**���ݻ�Ա�ʺ�ͳ�����Ƿ��Ѵ��ڻ��ּ�¼
     * @param userNice
     * @return
     */
    public Integer getCountByUserNick(String userNice);

    /**
     * ��ȡ�����û���δ�����ᣩ�Ŀ��û���
     * @return List<UserPoints>
     */
    public List<UserPoints> getUserPointsList();

    /**
     * ���ݻ�ԱIdͳ�����Ƿ��Ѵ��ڻ��ּ�¼
     *
     * @param userId ��Ϊ�ա�null
     * @return  ���ؼ�¼������û�з���0
     * @throws Exception
     */
    public UserPoints getUserPointsByUserId(String userId) throws Exception;
}
