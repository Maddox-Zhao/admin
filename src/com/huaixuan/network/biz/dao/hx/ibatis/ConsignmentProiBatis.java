package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.ConsignmentProDao;
import com.huaixuan.network.biz.domain.hx.ConsignmentPro;


/**
 * 2012-02-16 10:35
 * @author Mr_Yang
 *
 */
@Repository("consignmentPro")
public class ConsignmentProiBatis implements ConsignmentProDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public void addConsignmentPro(ConsignmentPro consignmentPro)
	{
		sqlMapClient.insert("addConsignmentPro",consignmentPro);
	}

	@Override
	public void deleteConsignmentPro(String id)
	{
		sqlMapClient.delete("deleteConsignmentPro",id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ConsignmentPro> getConsignmentById(String conId)
	{
		return (List<ConsignmentPro>)sqlMapClient.queryForList("getConsignmentProById",conId);
	}

	@Override
	public void updateConsignmentPro(ConsignmentPro consignmentPro)
	{
		sqlMapClient.update("updateComsignmentPro",consignmentPro);
	}

	@Override
	public ConsignmentPro getOntConsignmentPro(String id)
	{
		return (ConsignmentPro)sqlMapClient.queryForObject("getOneConsignmentPro", id);
	}

	@Override
	public void updateConsignmentProPic(Map parMap)
	{
		sqlMapClient.update("updateConsignmentProPic", parMap);
	}

	@Override
	public Integer selectConsignmentProAmount(String id)
	{
		return (Integer)sqlMapClient.queryForObject("selectConsignmentProAmount",id);
	}

	@Override
	public void updatePaidAmount(Map parMap)
	{
		sqlMapClient.update("updateConsignmentProPaidAmount",parMap);
	}

	@Override
	public Integer selectConsignmentProPaidAmount(String id)
	{
		return (Integer)sqlMapClient.queryForObject("selectConsignmentProPaidAmount",id);
	}

	@Override
	public void updateProStatusById(Map parMap)
	{
		sqlMapClient.update("updateProStatusById",parMap);
	}

}
