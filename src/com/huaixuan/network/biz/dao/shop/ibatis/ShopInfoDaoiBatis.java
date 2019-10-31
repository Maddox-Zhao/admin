package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ShopInfoDao;
import com.huaixuan.network.biz.domain.shop.ShopInfo;

@Service("shopInfoDao")
public class ShopInfoDaoiBatis implements ShopInfoDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addShopInfo(ShopInfo shopInfo) throws Exception {
		return (Long) this.sqlMapClient.insert("addShopInfo", shopInfo);
	}

	@Override
	public void editShopInfo(ShopInfo shopInfo) throws Exception {
		this.sqlMapClient.update("editShopInfo", shopInfo);
	}

	@Override
	public void removeShopInfo(Long shopInfoId) throws Exception {
		this.sqlMapClient.delete("removeShopInfo", shopInfoId);
	}

	/**
	 * @author chenhang 2011-3-2
	 * @description 获得店铺信息
	 */
	@Override
	public ShopInfo getShopInfo(Long shopInfoId) throws Exception {
		return (ShopInfo) this.sqlMapClient.queryForObject("getShopInfo",
				shopInfoId);
	}

	@Override
	public List<ShopInfo> getShopInfos() throws Exception {
		return this.sqlMapClient.queryForList("getShopInfos", null);
	}

	@Override
	public ShopInfo getShopInfoByUserId(long userId) throws Exception {
		// TODO Auto-generated method stub
		return (ShopInfo) this.sqlMapClient.queryForObject(
				"getShopInfoByUserId", userId);
	}
}
