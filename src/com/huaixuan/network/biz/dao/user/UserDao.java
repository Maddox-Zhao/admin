package com.huaixuan.network.biz.dao.user;

import java.util.List;
import java.util.Map;

import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.trade.TradeSalesCount;
import com.huaixuan.network.biz.domain.user.User;

public interface UserDao {
    public User loadUserByUsername(String username) throws UsernameNotFoundException;

    public List<User> getUsers();

    List getAgentUsers(long id);

    public User getUserByAccount(String account);

    Long saveUser(User user);

    void updateUserGrade(Map<String, String> parMap) throws Exception;

    void updateUserScrap(User user) throws Exception;

    void deleteUserRoles(final Long userId);

    void deleteUser(Long userId);

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    String getUserPassword(String username);

    void editPassword(User user);

    void editPayPassword(User user);

    Boolean checkEmail(String account);

    Boolean checkAccount(String account);

    void updateLastLogin(User user);

    Boolean checkPassword(User user);

    Boolean checkPayPassword(User user);

//    List<User> getUsersByCondition(User user, Page page);

    User getUserById(Long id) ;

    void addUserRoleRegister(Long userId, Long roleId);

//    List<User> getUsers(User user, Page page);

    List<User> getUserByIsAdmin(int isAdmin);

    public User getUserByEmail(String email) ;

    public User getUser(String userName, String password);

    void updateUserType(long id, String type);


    /**�����û��ȼ�
     * @param parMap
     * @throws Exception
     */
    void updateGrade(Map<String, Object> parMap) throws Exception;

    /**
     * ���»�Ա�Ĵ��������޶�
     * @param userId Long
     * @param agentCountNumber Long
     * @author chenyan 2010/04/15
     */
    void updateAgentCount(Long userId, Long agentCountNumber);

    /**
     *
     * ���»�Ա���Զ���Ʊ����
     * @param userId Long
     * @param invoice String
     * @author chenhang 2010/12/14
     */
    void updateAgentInvoice(Long userId, String invoice);

    /**
     * ���»�Ա�����ͺ�VIP���˵��
     * @param map
     * @author lilei 2010/06/02
     */
	public void updateUserByParMap(Map map);

	/**
	 * ���´����Ա�����۶�
	 * @param tradeSales
	 * @author lilei 2010/06/03
	 */
	public void updateUserSalesCount(TradeSalesCount tradeSales);

	/**
	 * ����û����ͻ�ȡ�û��б�
	 * @param userTypes
	 * @return
	 */
	List<User> getUserWithTypes(Map parMap);

	/**
	 * ���»�Ա�����ڽ�����Ϣ
	 * @param user
	 * @author fanyj 2010/07/19
	 */
	void updateUserPeriod(User user) throws Exception;

	void editGmtPeriodPay();

	/**
	 * ���»�Ա�����ڽ�����
	 * @param user
	 * @author fanyj 2010/07/19
	 */
	void updateUserPeriodAmount(User user);

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
    
    Integer getUserListByConditionWithPageCount(Map parMap, User user);
    
    Integer serchUserListByConditionWithPageCount(Map parMap, User user);
    
    List<User> getUserListByConditionWithPage(Map parMap, User user);
    
    List<User> serchUserListByConditionWithPage(Map parMap, User user);
    
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
