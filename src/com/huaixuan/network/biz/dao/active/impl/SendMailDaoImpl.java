package com.huaixuan.network.biz.dao.active.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.huaixuan.network.biz.dao.active.SendMailDao;
import com.huaixuan.network.biz.domain.active.MoveFrame;
import com.huaixuan.network.biz.domain.active.MoveFrameInfo;
import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;

/**
 * @author Mr_Yang
 * @version ����ʱ�䣺2012-3-27 ����05:08:57
 * 
 */

@Component("sendMailDao")
public class SendMailDaoImpl implements SendMailDao
{

	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	
	@Override
	public List<MoveframeProduct> getMoveFrameGoodsInfoByMoveFrameId(Long moveframeId)
	{
		return sqlMap.queryForList("getMoveFrameGoodsInfoByMoveFrameId",moveframeId);
	}

	@Override
	public List<Long> getMoveFrameIdsByCustomerId(Long customerId)
	{
		return sqlMap.queryForList("getMoveFrameIdsByCustomerId",customerId);
	}

	
	@Override
	public List<Long> getAllCustomerId()
	{
		return sqlMap.queryForList("getCustomerIds");
	}


	@Override
	public String getMoveFrameName(Map parMap)
	{
		return (String)sqlMap.queryForObject("selectMoveframeNameByProIdAndPrice",parMap);
	}


	@Override
	public String getHeaderEmail(Long moveFrameId)
	{
		return (String)sqlMap.queryForObject("getHeaderEmail",moveFrameId);
	}

	/**
	 * �õ���ǰʱ��ǰһ�촴�������л 
	 * @return
	 */
	@Override
	public List<MoveFrameInfo> getYesterDayNewCreateMoveFrames(Integer type)
	{
		return sqlMap.queryForList("getYesterDayNewCreateMoveFrames",type);
	}


	/**
	 * ���ݻ��ID�õ���Ӧ�Ŀͻ���Ϣ�ͻ������Ա����Email
	 */
	@Override
	public List<MoveFrameInfo> getEmailAndCustomerName(Long id)
	{
		return sqlMap.queryForList("getEmailAndCustomerName",id);
	}


	@Override
	public List<MoveFrameInfo> getYesterDayModifyMoveFrames(Integer type)
	{
		return sqlMap.queryForList("getYesterDayModifyMoveFrames",type);
	}


	@Override
	public MoveFrame getMoveFrameById(Long moveframeId)
	{
		return (MoveFrame)sqlMap.queryForObject("selectMoveframeById",moveframeId);
	}

	@Override
	public List<Long> getMoveFrameIdByGoodsId(Long goodsId)
	{
		return sqlMap.queryForList("getMoveFrameIdByGoodsId",goodsId);
	}

	@Override
	public void updateLogStatus(Integer type)
	{
		sqlMap.update("updateMoveFrameLogStatus",type);
	}

	@Override
	public List<MoveframeProduct> getMoveFrameProductByLog(Map parMap)
	{
		
		return sqlMap.queryForList("getMoveFrameProductByLog",parMap);
	}

	@Override
	public void updateLogDealStatus(Map parMap)
	{
			sqlMap.update("updateLogDealStatus",parMap);
	}
	
	public List<MoveFrameLog> getMoveFrameLogByType(Integer type)
	{
		return sqlMap.queryForList("selectNotDealLog",type);
	}
	




}
