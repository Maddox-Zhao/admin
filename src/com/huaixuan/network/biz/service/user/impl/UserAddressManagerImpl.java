package com.huaixuan.network.biz.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.UserAddressDao;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.service.user.UserAddressManager;
import com.huaixuan.network.biz.service.user.UserInfoManager;

@Service("userAddressManager")
public class UserAddressManagerImpl implements UserAddressManager {

	@Autowired
	private UserAddressDao userAddressDao;
	
	@Autowired
	private UserInfoManager userInfoManager;
	
	protected Log  log = LogFactory.getLog(this.getClass());
	
    public void addUserAddress(UserAddress userAddressDao) {
        log.info("UserAddressManagerImpl.addUserAddress method");
        try {
            this.userAddressDao.addUserAddress(userAddressDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editUserAddress(UserAddress userAddress) {
        log.info("UserAddressManagerImpl.editUserAddress method");
        try {
            this.userAddressDao.editUserAddress(userAddress);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeUserAddress(Long userAddressId) {
        log.info("UserAddressManagerImpl.removeUserAddress method");
        try {
            this.userAddressDao.removeUserAddress(userAddressId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public UserAddress getUserAddress(Long userAddressId) {
        log.info("UserAddressManagerImpl.getUserAddress method");
        try {
            return this.userAddressDao.getUserAddress(userAddressId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<UserAddress> getUserAddresss() {
        log.info("UserAddressManagerImpl.getUserAddresss method");
        try {
            return this.userAddressDao.getUserAddresss();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
    * 查询某个用户的地址信息列表
    * @param userId 用户ID
    * @return 返回用户的地址信息列表
    */
    @SuppressWarnings("unchecked")
    public List<UserAddress> getUserAddresssByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        List<UserAddress> userAddressList = userAddressDao
            .getUserAddresssByParameterMap(parameterMap);
        return userAddressList;
    }

    /**
     * 查询符合参数集ParameterMap要求的UserAddress收货人信息结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的UserAddress收货人信息结果集
     */
    @SuppressWarnings("unchecked")
    public List<UserAddress> getUserAddresssByParameterMap(Map parameterMap) {
        if (null == parameterMap || parameterMap.size() < 1) {
            return null;
        }
        List<UserAddress> userAddressList = userAddressDao
            .getUserAddresssByParameterMap(parameterMap);
        return userAddressList;
    }

    public List<UserAddress> getTradeAddressByUserId(Long userId){
    	UserInfo userInfo = userInfoManager.getUserInfoByUserId(userId);
    	Long addressIdForRegister = userInfo.getAddress_id();

//    	UserAddress addressForRegister = getUserAddress(addressIdForRegister);

    	List<UserAddress> allAddresses = getUserAddresssByUserId(userId);
//    	rewrite the hashCode()...
//    	allAddresses.remove(addressForRegister);

    	for(UserAddress userAddress : allAddresses){
    		if(userAddress.getId() == addressIdForRegister){
    			allAddresses.remove(userAddress);
    			return allAddresses;
    		}
    	}
    	return allAddresses;
    }

	@Override
	public void updateUserAddressByNotNull(UserAddress userAddress)
	{
		userAddressDao.updateUserAddressByNotNull(userAddress);
	}

}
