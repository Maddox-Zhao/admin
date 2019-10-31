package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.AcquisitionProDao;
import com.huaixuan.network.biz.domain.hx.AcquisitionPro;


/**
 * 2012-03-02 17:43
 * @author Mr_Yang
 *
 */
@Repository("acquisitionProDao")
public class AcquisitionProDaoiBatis implements AcquisitionProDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addAcquisitionPro(AcquisitionPro acquisitionPro)
	{
		sqlMapClient.insert("insertAcquisitionPro",acquisitionPro);
	}

	@Override
	public void deleteAcquisitionProById(Long id)
	{
		sqlMapClient.delete("deleteByPrimaryKey",id);
	}

	@Override
	public List<AcquisitionPro> getAcquisitionByAcqCode(String acqCode)
	{
		return sqlMapClient.queryForList("selectProByAcqCode",acqCode);
	}

	@Override
	public AcquisitionPro getOneAcquisitionById(Long id)
	{
		return (AcquisitionPro)sqlMapClient.queryForObject("selectByPrimaryKey", id);
	}

	@Override
	public void updateAcquisitionProByNotNull(AcquisitionPro acquisitionPro)
	{
		sqlMapClient.update("updateByNotNull",acquisitionPro);
	}

	@Override
	public void updateAcquisitionProPic(Map parMap)
	{
		sqlMapClient.update("updateAcquisitionProPic",parMap);
	}
	
	

}
