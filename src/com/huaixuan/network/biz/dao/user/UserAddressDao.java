package com.huaixuan.network.biz.dao.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.user.UserAddress;

public interface UserAddressDao {
 	/* @interface model: UserAddress */
	 Long addUserAddress(UserAddress userAddress) throws Exception;

	/* @interface model: UserAddress */
	void editUserAddress(UserAddress userAddress) throws Exception;

	/* @interface model: UserAddress */
	void removeUserAddress(Long userAddressId) throws Exception;

	/* @interface model: UserAddress,UserAddress */
	UserAddress getUserAddress(Long userAddressId) throws Exception;

	/* @interface model: UserAddress,UserAddress */
	List <UserAddress> getUserAddresss() throws Exception;

	/**
    * 查询符合参数集ParameterMap要求的UserAddress收货人信息结果集
    * @param parameterMap 参数集
    * @return 符合参数集ParameterMap要求的UserAddress收货人信息结果集
    */
	@SuppressWarnings("unchecked")
   public List<UserAddress> getUserAddresssByParameterMap(Map parameterMap);

	/**
    * 更新店铺地址
    * @param UserAddress
    * @return
    */
	void updateShopInfoAddress(UserAddress userAddress) throws Exception;
	
	/**
	 * 通过userAddressId更新用户地址
	 */
	public void updateUserAddressByNotNull(UserAddress userAddress);
}
