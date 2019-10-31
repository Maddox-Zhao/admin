package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.AcquisitionDao;
import com.huaixuan.network.biz.domain.hx.Acquisition;


/**
 * 2012-03-02 11:21
 * @author Mr_Yang
 *
 */
@Repository("acquisitionDao")
public class AcquisitionDaoiBatis implements AcquisitionDao
{

	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	@Override
	public Long addAcquisitionView(Acquisition acquisition)
	{
		sqlMap.insert("insertAcquisition",acquisition);
		return Long.parseLong("1");
	}

	@Override
	public void deleteAcquisitionViewById(Long id)
	{
		sqlMap.delete("deleteAcquisitionByPrimaryKey",id);
	}

	@Override
	public void delteAcquisitionView(Acquisition acquisition)
	{
		deleteAcquisitionViewById(acquisition.getId());
	}

	@Override
	public Acquisition getAcquisitionById(Long id)
	{
		return (Acquisition)sqlMap.queryForObject("selectAcquisitionById",id);
	}

	@Override
	public List<Acquisition> getAcquisitionViewByConditionWithPage(Map parMap)
	{
		return sqlMap.queryForList("AcquisitionViewByConditionWithPage",parMap);
	}

	@Override
	public Integer getConsignmentViewByConditionWithPageCount(Map parMap)
	{
		return (Integer)sqlMap.queryForObject("getAcquisitionViewByConditionWithPageCount",parMap);
	}

	@Override
	public Acquisition selectAcquisitionViewByAcpCode(String acpCode)
	{
		return (Acquisition)sqlMap.queryForObject("selectAcquisitionByAcpCode",acpCode);
	}
	
	@Override
	public void updateAcquisitionViewByNotNull(Acquisition acquisition)
	{
		sqlMap.update("updateAcquisitionByNotNull",acquisition);
	}

	@Override
	public void updateAcquisitionViewByPrimaryKey(Acquisition acquisition)
	{
		sqlMap.update("updateAcquisitionByPrimaryKey",acquisition);
	}

	//≈–∂œ±‡∫≈ «∑Ò¥Ê‘⁄
	@Override
	public Integer theAcpCodeIsEixst(String acpCode)
	{
		return (Integer)sqlMap.queryForObject("theAcpCodeIsExits",acpCode);
	}

	@Override
	public void uploadAcqImage(Map parMap)
	{
		sqlMap.update("acquisition_uploadAcqImage",parMap);
	}

	@Override
	public void uploadIdcardImage(Map parMap)
	{
		sqlMap.update("acquisition_uploadIdcardImage",parMap);
	}

}
