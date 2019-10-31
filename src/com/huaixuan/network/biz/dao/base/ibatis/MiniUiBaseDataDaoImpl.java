package com.huaixuan.network.biz.dao.base.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.base.MiniUiBaseDataDao;
import com.huaixuan.network.biz.domain.base.MiniUiBase;



/**
 * @author Mr_Yang   2015-11-23 ����04:19:36
 **/
@Repository("miniUiBaseDataDao")
public class MiniUiBaseDataDaoImpl  implements MiniUiBaseDataDao
{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public List<MiniUiBase> querySiteByType(String type)
	{
		return sqlMapClient.queryForList("selectSiteByType",type );
	}

	@Override
	public List<MiniUiBase> querySellChannel()
	{
		return sqlMapClient.queryForList("selectAllSellChannel","");
	}

	@Override
	public List<MiniUiBase> queryAllCurrency()
	{
		return sqlMapClient.queryForList("selectCurrency","");
	}

	@Override
	public List<MiniUiBase> queryAllPayMent()
	{
		return sqlMapClient.queryForList("selectPayment","");
	}

	@Override
	public List<MiniUiBase> querySellChannelByAccount(String accountId)
	{
		return sqlMapClient.queryForList("selectSellChannelByAccount", accountId);
	}

	@Override
	public List<MiniUiBase> querySiteByAccount(String accountId)
	{
		return sqlMapClient.queryForList("selectSiteByAccount", accountId);
	}

	@Override
	public List<MiniUiBase> querySupplier(Map<String, String> map)
	{
		return sqlMapClient.queryForList("selectSupplier",map);
	}

	@Override
	public List<MiniUiBase> queryProvince() {
		return sqlMapClient.queryForList("selectProvince","");
	}

	@Override
	public List<MiniUiBase> getSiteWhereMenDian(String type) {
		return sqlMapClient.queryForList("getSiteWhereMenDian",type );
	}

	@Override
	public List<MiniUiBase> getSiteByName(String name) {
		return sqlMapClient.queryForList("selectSiteByName",name);
	}


}
 
