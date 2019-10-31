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
 	 * ��ѯĳ���û��ĵ�ַ��Ϣ�б�
 	 * @param userId �û�ID
 	 * @return �����û��ĵ�ַ��Ϣ�б�
 	 */
 	public List<UserAddress> getUserAddresssByUserId(Long userId);

 	/**
 	 * ��ѯ���ϲ�����ParameterMapҪ���UserAddress�ջ�����Ϣ�����
 	 * @param parameterMap ������
 	 * @return ���ϲ�����ParameterMapҪ���UserAddress�ջ�����Ϣ�����
 	 */
    public List<UserAddress> getUserAddresssByParameterMap(Map parameterMap);

    public List<UserAddress> getTradeAddressByUserId(Long userId);
    
    
    /**
	 * ͨ��userAddressId�����û���ַ
	 */
	public void updateUserAddressByNotNull(UserAddress userAddress);
	
}
