package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.UserAddressDao;
import com.huaixuan.network.biz.domain.user.UserAddress;

@Repository("userAddressDao")
public class UserAddressDaoiBatis implements UserAddressDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
    /* @model: */
    public Long addUserAddress(UserAddress userAddress) throws Exception {
        return (Long) this.sqlMapClient.insert("addUserAddress", userAddress);
    }

    /* @model: */
    public void editUserAddress(UserAddress userAddress) throws Exception {
        this.sqlMapClient.update("editUserAddress", userAddress);
    }

    /* @model: */
    public void removeUserAddress(Long userAddressId) throws Exception {
        this.sqlMapClient.delete("removeUserAddress", userAddressId);
    }

    /* @model: */
    public UserAddress getUserAddress(Long userAddressId) throws Exception {
        return (UserAddress) this.sqlMapClient.queryForObject("getUserAddress",
            userAddressId);
    }

    /* @model: */
    public List<UserAddress> getUserAddresss() throws Exception {
        return this.sqlMapClient.queryForList("getUserAddresss", null);
    }

    /**
     * 查询符合参数集ParameterMap要求的UserAddress收货人信息结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的UserAddress收货人信息结果集
     */
    @SuppressWarnings("unchecked")
    public List<UserAddress> getUserAddresssByParameterMap(Map parameterMap) {
        return this.sqlMapClient.queryForList("getUserAddresssByParameterMap",
            parameterMap);
    }

    public void updateShopInfoAddress(UserAddress userAddress) throws Exception {
        this.sqlMapClient.update("updateShopInfoAddress", userAddress);
    }

	@Override
	public void updateUserAddressByNotNull(UserAddress userAddress)
	{
		sqlMapClient.update("updateUserAddressByNotNull",userAddress);
	}

}
