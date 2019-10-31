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
    * ��ѯ���ϲ�����ParameterMapҪ���UserAddress�ջ�����Ϣ�����
    * @param parameterMap ������
    * @return ���ϲ�����ParameterMapҪ���UserAddress�ջ�����Ϣ�����
    */
	@SuppressWarnings("unchecked")
   public List<UserAddress> getUserAddresssByParameterMap(Map parameterMap);

	/**
    * ���µ��̵�ַ
    * @param UserAddress
    * @return
    */
	void updateShopInfoAddress(UserAddress userAddress) throws Exception;
	
	/**
	 * ͨ��userAddressId�����û���ַ
	 */
	public void updateUserAddressByNotNull(UserAddress userAddress);
}
