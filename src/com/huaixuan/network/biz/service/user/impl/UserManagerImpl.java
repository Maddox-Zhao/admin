package com.huaixuan.network.biz.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.dao.user.UserAddressDao;
import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.dao.user.UserInfoDao;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradeSalesCount;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.domain.user.UserPoints;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.user.UserAgentManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.biz.service.user.UserPointsManager;
import com.huaixuan.network.biz.service.user.UserService;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("userManager")
public class UserManagerImpl implements UserManager, UserService {

    protected Log             logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserDao           userDao;

    @Autowired
    private UserInfoDao       userInfoDao;

    @Autowired
    private UserAddressDao    userAddressDao;

    @Autowired
    private PasswordEncoder   passwordEncoder;

    @Autowired
    private UserPointsManager userPointsManager;

    @Autowired
    private UserAgentManager  userAgentManager;

    //	@Autowired
    //	private AccountManager accountManager;

    @Autowired
    private TradeDao          tradeDao;

    /**
     * {@inheritDoc}
     */
    public User getUser(Long userId) {
        return userDao.getUserById(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers(User user) {
        return userDao.getUsers();
    }

    /**
     * {@inheritDoc}
     */
    public Long saveUser(User user) {
        return userDao.saveUser(user);

    }

    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
        logger.debug("removing user: " + userId);
        userDao.deleteUser(new Long(userId));
    }

    public String createToMd5(String gmtCreate) {
        if (passwordEncoder != null && null != gmtCreate) {
            return passwordEncoder.encodePassword(gmtCreate, null);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }

    @Transactional
    @Override
    public void registerUser(User user, UserInfo userInfo, UserAddress userAddress,
                             AgentInfo agentInfo) {

        try {

            if (passwordEncoder != null) {
                //锟斤拷锟斤拷md5锟斤拷锟斤拷
                user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
                user.setPayPassword(passwordEncoder.encodePassword(user.getPayPassword(), null));
            } else {
                logger.warn("PasswordEncoder not set, skipping password encryption...");
            }
            /* 为user锟斤拷锟斤拷一些默锟斤拷锟斤拷锟斤拷 */
            user.setVisitCount(1);
            user.setUserRank("heart-1");
            if (!(user.getStauts().equals(1))) {
                user.setStauts(0);//锟斤拷锟斤拷锟斤拷
            }
            user.setIsValidated(0);
            if (null == user.getSex()) {
                user.setSex(0);
            }
            //锟矫伙拷注锟斤拷时 锟矫伙拷锟斤拷锟斤拷 为锟秸的伙拷 注锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟酵拷没锟�(type=p) lilei 2010/06/17
            if (StringUtil.isBlank(user.getType())) {
                user.setType(EnumUserType.NORMAL_USER.getKey());
            }
            Long userid = userDao.saveUser(user);
            user.setId(userid);

            /* 锟斤拷userId锟斤拷UserAddress锟斤拷 */
            if (userAddress != null && userInfo != null) {
                userAddress.setContextName(userInfo.getUserName());
                userAddress.setEmail(user.getEmail());
                userAddress.setUserId(userid);
                Long id = userAddressDao.addUserAddress(userAddress);
                /* 锟斤拷address_id锟斤拷UserInfo锟斤拷 */
                userInfo.setAddress_id(id);
                userInfo.setUserId(userid);
                userInfoDao.addUserInfo(userInfo);
            } else {
                UserAddress tmpAddr = new UserAddress();
                tmpAddr.setUserId(userid);
                tmpAddr.setEmail(user.getEmail());
                //                tmpAddr.setProvince(getText("please.select"));
                //                tmpAddr.setCity(getText("please.select"));
                //                tmpAddr.setDistrict(getText("please.select"));
                Long tmpId = userAddressDao.addUserAddress(tmpAddr);

                UserInfo tmp = new UserInfo();
                tmp.setUserId(userid);
                tmp.setAddress_id(tmpId);

                userInfoDao.addUserInfo(tmp);

            }
            if (null != agentInfo) {
                agentInfo.setUserId(userid);
                userAgentManager.insertApply(agentInfo);
            }
            //            //锟斤拷锟斤拷锟剿伙拷
            //            boolean isSuccess = accountManager.addFrontWebAccount(userid, EnumUserType.NORMAL_USER
            //                .getKey());
            //            if (!isSuccess) {
            //                throw new Exception("锟矫伙拷锟绞伙拷锟斤拷锟斤拷失锟斤拷");
            //            }
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            //            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
        //           catch (EntityExistsException e) {
        //            // needed for JPA
        //            logger.error(e.getMessage());
        //            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        //        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void editUser(User user, UserInfo userInfo, UserAddress userAddress) {
        try {
            user.setStauts(1);
            userDao.saveUser(user);

            userAddress.setTel(userInfo.getHomePhone());
            userAddress.setMobile(userInfo.getMobilePhone());
            userAddress.setContextName(user.getNickname());
            userAddress.setEmail(user.getEmail());
            //锟斤拷锟絘ddress锟斤拷息锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷虏锟斤拷锟�
            if (userAddress.getId() != 0L) {
                userAddressDao.editUserAddress(userAddress);
                userInfo.setAddress_id(userAddress.getId());
            } else {
                userAddress.setUserId(user.getId());
                Long addressId = userAddressDao.addUserAddress(userAddress);
                userInfo.setAddress_id(addressId);
            }
            userInfoDao.editUserInfo(userInfo);
        } catch (DataIntegrityViolationException e) {
            logger.warn(e.getMessage());
            //            throw new UserExistsException(user.getEmail() + "' already exists!");
        }
        //        catch (EntityExistsException e) { // needed for JPA
        //            logger.warn(e.getMessage());
        //            throw new UserExistsException(user.getEmail() + "' already exists!");
        //        }
        catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * @param user
     */
    public Boolean updateStatus(User user) {
        try {
            if (null != user) {
                user.setStauts(1);//锟斤拷锟矫伙拷为锟斤拷使锟斤拷
                userDao.saveUser(user);
                if (null != user.getReferenceId()) {//锟斤拷锟斤拷萍锟斤拷没锟斤拷锟轿拷眨锟斤拷锟斤拷锟狡硷拷锟矫伙拷锟接伙拷锟�
                    User userReference = userDao.getUserById(user.getReferenceId());
                    if (null != userReference) {
                        UserPoints userPoints = new UserPoints();
                        userPoints.setUserId(userReference.getId());
                        userPoints.setUserNick(userReference.getAccount());
                        if (0 == userPointsManager.getCountByUserNick(userReference.getAccount())) {
                            userPoints.setUsablePoints(1);
                            userPointsManager.addUserPoints(userPoints);
                        } else {
                            userPoints.setType("transaction");
                            userPoints.setPoints(1);
                            userPointsManager.editUserPoints(userPoints);
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public void updateUserGrade(Map<String, String> parMap) throws Exception {
        userDao.updateUserGrade(parMap);
    }

    public Boolean editPassword(User user) throws Exception {
        User temp = new User();
        temp.setId(user.getId());
        temp.setPassword(user.getPassword());
        if (passwordEncoder != null) {
            //锟斤拷锟斤拷md5锟斤拷锟斤拷
            temp.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        } else {
            logger.warn("PasswordEncoder not set, skipping password encryption...");
        }
        userDao.editPassword(temp);
        return true;
    }

    public Boolean editPayPassword(User user) throws Exception {
        User temp = new User();
        temp.setId(user.getId());
        temp.setPayPassword(user.getPayPassword());
        if (passwordEncoder != null) {
            //锟斤拷锟斤拷md5锟斤拷锟斤拷
            temp.setPayPassword(passwordEncoder.encodePassword(user.getPayPassword(), null));
        } else {
            logger.warn("PasswordEncoder not set, skipping password encryption...");
        }
        userDao.editPayPassword(temp);
        return true;
    }

    public Boolean checkAccount(String account) throws Exception {
        return userDao.checkAccount(account);
    }

    public Boolean checkEmail(String email) throws Exception {
        return userDao.checkEmail(email);
    }

    public Boolean checkPassword(User user) {
        User temp = new User();
        temp.setId(user.getId());
        temp.setCurrentlypwd(user.getCurrentlypwd());
        if (passwordEncoder != null) {
            //锟斤拷锟斤拷md5锟斤拷锟斤拷
            temp.setCurrentlypwd(passwordEncoder.encodePassword(user.getCurrentlypwd(), null));
        } else {
            logger.warn("PasswordEncoder not set, skipping password encryption...");
        }
        return userDao.checkPassword(temp);
    }

    public Boolean checkPayPassword(User user) {
        User temp = new User();
        temp.setId(user.getId());
        temp.setCurrentlypwd(user.getCurrentlypwd());
        if (passwordEncoder != null) {
            //锟斤拷锟斤拷md5锟斤拷锟斤拷
            temp.setCurrentlypwd(passwordEncoder.encodePassword(user.getCurrentlypwd(), null));
        } else {
            logger.warn("PasswordEncoder not set, skipping password encryption...");
        }
        return userDao.checkPayPassword(temp);
    }

    public User getUser(String userId) {
        return userDao.getUserById(new Long(userId));
    }

    //    public List<User> getUsersByCondition(User user, Page page) throws Exception {
    //        return userDao.getUsersByCondition(user, page);
    //    }

    //    public Boolean editUserInfo(User user, UserInfo userInfo, List<String> roles) {
    //        try {
    //            UserInfo temp = userInfoDao.getUserInfoByUserId(userInfo.getUserId());
    //            if (temp == null) {
    //                userInfoDao.addUserInfo(userInfo);
    //            } else {
    //                userInfoDao.editUserInfo(userInfo);
    //            }
    //            userDao.saveUser(user);
    //        } catch (Exception e) {
    //            logger.warn("edit user info not success...");
    //            return false;
    //        }
    //        return true;
    //    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getUserListByConditionWithPage(User user, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(user);
        Map pramas = queryPage.getParameters();

        int count = userDao.getUserListByConditionWithPageCount(pramas, user);

        if (count > 0) {

            /* 锟斤拷前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页锟斤拷锟斤拷 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<User> userList = userDao.getUserListByConditionWithPage(pramas, user);
            if (userList != null && userList.size() > 0) {
                queryPage.setItems(userList);
            }
        }
        return queryPage;
    }

    public Boolean checkSamePassword(String oldPassword, String newPassword) {
        String newpassword = passwordEncoder.encodePassword(newPassword, null);

        return oldPassword.equals(newpassword);
    }

    public List<User> getUserByIsAdmin(int isAdmin) {
        return userDao.getUserByIsAdmin(isAdmin);
    }

    public User getUser(String userName, String password, boolean isPasswdNeedEncode) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return null;
        }
        String passwd = password;
        if (isPasswdNeedEncode) {
            passwd = passwordEncoder.encodePassword(password, null);
        }
        try {
            return userDao.getUser(userName, passwd);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public void updateAgentCount(Long userId, Long agentCountNumber) {
        logger.info("UserManagerImpl.updateAgentCount method");
        try {
            this.userDao.updateAgentCount(userId, agentCountNumber);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateAgentInvoice(Long userId, String invoice) {
        logger.info("UserManagerImpl.updateAgentInvoiceLong method");
        try {
            this.userDao.updateAgentInvoice(userId, invoice);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateGrade(Map<String, Object> parMap) throws Exception {
        userDao.updateGrade(parMap);
    }

    public void updateUserTypeAndRmark(String vipUserId, String modifyType, String vipMemo) {
        logger.info("UserManagerImpl.updateUserTypeAndRmark method");
        try {
            Map map = new HashMap();
            map.put("id", vipUserId);
            map.put("type", modifyType);
            map.put("vipRemark", vipMemo);
            this.userDao.updateUserByParMap(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateUserSalesCount(TradeSalesCount tradeSales) {
        logger.info("UserManagerImpl.updateUserSalesCount method");
        try {
            this.userDao.updateUserSalesCount(tradeSales);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public List<User> getUserWithTypes(Map parMap) {
        logger.info("UserManagerImpl.getUserWithTypes method");
        try {
            return userDao.getUserWithTypes(parMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void updateUserPeriod(User user) {
        logger.info("UserManagerImpl.updateUserPeriod method");
        try {
            this.userDao.updateUserPeriod(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void updateUserPeriodAmount() {
        logger.info("UserManagerImpl.updateUserPeriod method");
        try {
            List<Trade> list = this.tradeDao.getSumAmountGroupByUserId();
            if (list != null && list.size() > 0) {
                User user = null;
                for (Trade obj : list) {
                    user = new User();
                    user.setId(obj.getId());//锟斤拷锟截斤拷锟斤拷锟绞癸拷锟斤拷硕锟斤拷锟絀D锟斤拷锟芥购锟斤拷锟斤拷ID
                    user.setPeriodAmountNow(obj.getGoodsAmountSum());
                    this.userDao.updateUserPeriodAmount(user);// 锟睫改碉拷前锟斤拷锟节斤拷锟斤拷锟杰斤拷锟�
                }
                this.userDao.editGmtPeriodPay();// 锟睫革拷锟斤拷锟节斤拷锟斤拷时锟斤拷
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateChannelMemoByUserId(Long userId, String channelMemo) {
        logger.info("UserManagerImpl.updateChannelMemoByUserId method");
        try {
            this.userDao.updateChannelMemoByUserId(userId, channelMemo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateMainMemoByUserId(Long userId, String mainMemo) {
        logger.info("UserManagerImpl.updateMainMemoByUserId method");
        try {
            this.userDao.updateMainMemoByUserId(userId, mainMemo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateConnectMemoByUserId(Long userId, String connectMemo) {
        logger.info("UserManagerImpl.updateConnectMemoByUserId method");
        try {
            this.userDao.updateConnectMemoByUserId(userId, connectMemo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateIsFulltimeByUserId(Long userId, String isFulltime) {
        logger.info("UserManagerImpl.updateIsFulltimeByUserId method");
        try {
            this.userDao.updateIsFulltimeByUserId(userId, isFulltime);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

	@Override
	public void updateUserScrap(User user) {
		try {
			this.userDao.updateUserScrap(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void updateUserByNotNull(User user)
	{
		userDao.updateUserByNotNull(user);
	}

	@Override
	public Long getCustomerIdByUserId(Long userId)
	{
		return userDao.getCustomerIdByUserId(userId);
	}

	@Override
	public QueryPage serchUserListByConditionWithPage(User user, int currPage,
			int pageSize) {
        QueryPage queryPage = new QueryPage(user);
        Map pramas = queryPage.getParameters();

        int count = userDao.serchUserListByConditionWithPageCount(pramas, user);

        if (count > 0) {

            /* 锟斤拷前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页锟斤拷锟斤拷 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);
            

            
            
            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<User> userList = userDao.serchUserListByConditionWithPage(pramas, user);
            if (userList != null && userList.size() > 0) {
                queryPage.setItems(userList);
            }
        }
        return queryPage;
	}

}
