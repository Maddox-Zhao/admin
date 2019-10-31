package com.huaixuan.network.biz.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.security.userdetails.UsernameNotFoundException;

import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.trade.TradeSalesCount;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.query.QueryPage;

public interface UserManager {

    //    void setUserDao(UserDao userDao);

    User getUser(Long userId);

    User getUserByUsername(String username) throws UsernameNotFoundException;

    List getUsers(User user);

    Long saveUser(User user);

    void editUser(User user, UserInfo userInfo, UserAddress userAddress);

    void removeUser(String userId);

    void registerUser(User user, UserInfo userInfo, UserAddress userAddress, AgentInfo agentInfo);

    Boolean editPassword(User user) throws Exception;

    Boolean editPayPassword(User user) throws Exception;

    Boolean checkEmail(String email) throws Exception;

    Boolean checkAccount(String account) throws Exception;

    Boolean checkPassword(User user);

    Boolean checkPayPassword(User user);

    //    List<User> getUsersByCondition(User user, Page page) throws Exception;

    public QueryPage getUserListByConditionWithPage(User user, int currPage, int pageSize);
    
    public QueryPage serchUserListByConditionWithPage(User user, int currPage, int pageSize);

    //    Boolean editUserInfo(User user, UserInfo userInfo, List<String> roles);

    User getUserById(Long id);

    Boolean checkSamePassword(String oldPassword, String newPassword);

    List<User> getUserByIsAdmin(int isAdmin);

    /**
     * ����ע���û���״̬��0�������У�1��ʹ��,2�����У�
     *
     * @param user
     */
    public Boolean updateStatus(User user);

    /**
     * ���ַ���м���
     *
     * @param gmtCreate
     * @return
     */
    public String createToMd5(String gmtCreate);

    /**
     * ����ʺź������ѯ�û�
     *
     * @param userName
     * @param password
     * @param isPasswdNeedEncode,�Ƿ�Ҫ���������
     * @return
     */
    User getUser(String userName, String password, boolean isPasswdNeedEncode);

    //    /**�����û��ȼ�
    //     * @param parMap
    //     * @throws Exception
    //     */
    void updateGrade(Map<String, Object> parMap) throws Exception;

    /**
     * ���»�Ա�Ĵ��������޶�
     * @param userId Long
     * @param agentCountNumber Long
     * @author chenyan 2010/04/15
     */
    void updateAgentCount(Long userId, Long agentCountNumber);

    /**
     * ���»�Ա�Ĵ��������޶�
     * @param userId Long
     * @param invoice String
     * @author chenhang 2010/12/14
     */
    void updateAgentInvoice(Long userId, String invoice);

    /**
     * ���»�Ա�����ͺ�VIP���˵��
     * @param id
     * @param type
     * @author lilei 2010/06/02
     */
    void updateUserTypeAndRmark(String vipUserId, String modifyType, String vipMemo);

    /**
     * ���´����Ա�����۶�
     * @param tradeSales
     * @author lilei 2010/06/03
     */
    void updateUserSalesCount(TradeSalesCount tradeSales);

    /**
     * ������ͻ�ȡ�û��б�
     * @author zhangwy
     * @param userTypes
     * @return
     */
    List<User> getUserWithTypes(Map parMap);

    /**
     * ���»�Ա�����ڽ�����Ϣ
     * @param user
     * @author fanyj 2010/07/19
     */
    void updateUserPeriod(User user);

    /**
     * ���»�Ա�����ڽ�����
     * @param user
     * @author fanyj 2010/07/19
     */
    void updateUserPeriodAmount();

    /**
     * ����û�ID���и���������ע
     * @param userId Long
     * @param channelMemo String
     * @author chenyan 2010/09/10
     */
    void updateChannelMemoByUserId(Long userId, String channelMemo);

    /**
     * ����û�ID���и���������ע
     * @param userId Long
     * @param channelMemo String
     * @author chenyan 2010/09/10
     */
    void updateMainMemoByUserId(Long userId, String mainMemo);

    /**
     * ����û�ID���и���������ע
     * @param userId Long
     * @param connectMemo String
     * @author chenyan 2010/09/10
     */
    void updateConnectMemoByUserId(Long userId, String connectMemo);

    /**
     * ����û�ID���и���������ע
     * @param userId Long
     * @param isFulltime String
     * @author chenyan 2010/09/10
     */
    void updateIsFulltimeByUserId(Long userId, String isFulltime);
    
    
    /**
     * ����û�id�������û�����
     * @param user
     */
    public void updateUserByNotNull(User user);
    
    
    
    /**
     * ͨ��userId�ĵ�CustomerId
     * @param userId
     * @return
     */
    public Long getCustomerIdByUserId(Long userId);
}
