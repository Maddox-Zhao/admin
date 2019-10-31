package com.huaixuan.network.biz.service.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.user.UserAddress;

public interface UserAddressManager {

 	/* @interface model: UserAddress */
 	public void addUserAddress(UserAddress userAddress);

 	/* @interface model: UserAddress */
 	public void editUserAddress(UserAddress userAddress);

 	/* @interface model: UserAddress */
 	public void removeUserAddress(Long userAddressId);

 	/* @interface model: UserAddress,UserAddress */
 	public UserAddress getUserAddress(Long userAddressId);

 	/* @interface model: UserAddress,UserAddress */
 	public List<UserAddress> getUserAddresss();

 	/**
 	 * 查询某个用户的地址信息列表
 	 * @param userId 用户ID
 	 * @return 返回用户的地址信息列表
 	 */
 	public List<UserAddress> getUserAddresssByUserId(Long userId);

 	/**
 	 * 查询符合参数集ParameterMap要求的UserAddress收货人信息结果集
 	 * @param parameterMap 参数集
 	 * @return 符合参数集ParameterMap要求的UserAddress收货人信息结果集
 	 */
    public List<UserAddress> getUserAddresssByParameterMap(Map parameterMap);

    public List<UserAddress> getTradeAddressByUserId(Long userId);
    
    
    /**
	 * 通过userAddressId更新用户地址
	 */
	public void updateUserAddressByNotNull(UserAddress userAddress);
	
}
