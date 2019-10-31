package com.huaixuan.network.biz.dao.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.user.UserPoints;

public interface UserPointsDao {
    /**
     * �����µĻ�Ա���ּ�¼
     *
     * @param userPoints ��Ϊnull
     * @throws Exception
     */
    void addUserPoints(UserPoints userPoints) throws Exception;

    /**
     *  �޸Ļ���:�µ�(order)���ᡢ֧��(pay)ʹ�û���,������ּ��١�
     *  ȡ������(cancelOrder)�ⶳ�����׳ɹ�(transaction)���Ż���
     *
     * @param userPoints ��Ϊnull
     * @throws Exception
     */
    void editUserPoints(UserPoints userPoints) throws Exception;

    /* @interface model: UserPoints */
    void removeUserPoints(Long userPointsId) throws Exception;

    /* @interface model: UserPoints,UserPoints */
    UserPoints getUserPoints(Long userPointsId) throws Exception;

    /**
     * ���ݻ�Ա�ʺ�ͳ�����Ƿ��Ѵ��ڻ��ּ�¼
     *
     * @param userNick ��Ϊ�ա�null
     * @return  ���ؼ�¼������û�з���0
     * @throws Exception
     */
    Integer getCountByUserNick(String userNick) throws Exception;

    /**
     * ���ݻ�Ա�ʺŲ�ѯ�������
     *
     * @param userNick ��Ϊ�ա�null
     * @return UserPoints��û�в�ѯ���򷵻�null
     * @throws Exception
     */
    UserPoints getUserPointsByUserNick(String userNick) throws Exception;

    /**
     * ��ѯ���л�Ա���֣����޸�ʱ��Ϊ��
     *
     * @param page
     * @return
     * @throws Exception
     */
//    List<UserPoints> getUserPoints(Map parm ,Page page) throws Exception;

    List<UserPoints> getUserPointssExportList(UserPoints userPoints) throws Exception;

    /**
     * ��ȡ��¼����
     *
     * @return
     * @throws Exception
     */
    Integer getUserPointsCount(Map parm) throws Exception;

    /***
     * ��ȡ�û�������Ϣ
     * @return
     */
    List<UserPoints> getUserPointsList() throws Exception;

    /**
     * ���ݻ�ԱIdͳ�����Ƿ��Ѵ��ڻ��ּ�¼
     *
     * @param userId ��Ϊ�ա�null
     * @return  ���ؼ�¼������û�з���0
     * @throws Exception
     */
    UserPoints getUserPointsByUserId(String userId) throws Exception;

}
