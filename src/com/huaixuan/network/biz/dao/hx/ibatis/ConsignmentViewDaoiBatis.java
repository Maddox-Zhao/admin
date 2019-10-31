package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.ConsignmentViewDao;
import com.huaixuan.network.biz.domain.hx.ConsignmentView;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("consignmentViewDao")
public class ConsignmentViewDaoiBatis implements ConsignmentViewDao
{
	/**
	 * 2012-02-10 15:54
	 * @author Mr_Yang
	 * 
	 */	
	private SqlMapClientTemplate sqlMapClient;

	@Autowired
	public void setSqlMapClient(SqlMapClientTemplate sqlMapClient)
	{
		this.sqlMapClient = sqlMapClient;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Integer getConsignmentViewByConditionWithPageCount(Map parMap)
	{
		return (Integer)sqlMapClient.queryForObject("getConsignmentViewByConditionWithPageCount",parMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ConsignmentView> getConsignmentViewByConditionWithPage(Map parMap)
	{
		return  sqlMapClient.queryForList("getConsignmentViewByConditionWithPage",parMap);
	}
	

	@Override
	public Integer addConsignmentView(ConsignmentView consignmentView)
	{
		Object o = sqlMapClient.insert("addConsignmentView",consignmentView);
				
		return Integer.getInteger(o.toString());
	}

	@Override
	public void updateConsignmentView(ConsignmentView consignmentView)
	{
		sqlMapClient.update("updateConsignmentView",consignmentView);
	}

	@Override
	public ConsignmentView getOneConsignment(String id)
	{
		return (ConsignmentView)sqlMapClient.queryForObject("getOneConsignmentView",id);
	}

	@Override
	public void deleteConsignmentViewById(String id)
	{
		sqlMapClient.delete("deleteOneConsignmentView",id);
	}

	@Override
	public void delteConsignmentView(ConsignmentView consignmentView)
	{
		sqlMapClient.delete("deleteConsignmentView",consignmentView.getId());
	}

	@Override
	public Integer selectAmount(String id)
	{
		return (Integer)sqlMapClient.queryForObject("getAmount",id);
	}

	@Override
	public Integer selectPaidAmount(String id)
	{
		return (Integer)sqlMapClient.queryForObject("getPaidAmount",id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateAmount(Map parMap)
	{
		sqlMapClient.update("updateAmount",parMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updatePaidAmount(Map parMap)
	{
		sqlMapClient.update("updatePaidAmount",parMap);
	}

	@Override
	public Integer selectOneConsignmentViewByConCode(String conCode)
	{
		return (Integer)sqlMapClient.queryForObject("getOneConsignmentViewByConCode",conCode);
	}

	//更新身份证图片
	@Override
	@SuppressWarnings("unchecked")
	public void uploadConImage(Map parMap)
	{
		sqlMapClient.update("uploadConImage",parMap);
	}

	//更新寄卖凭证图片
	@Override
	@SuppressWarnings("unchecked")
	public void uploadIdcardImage(Map parMap)
	{
		sqlMapClient.update("uploadIdcardImage",parMap);
	}
	

}
