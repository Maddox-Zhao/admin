package com.huaixuan.network.biz.dao.active;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.active.MoveFrame;
import com.huaixuan.network.biz.domain.active.MoveFrameInfo;
import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;

/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-27 下午05:06:32
 * 当不同活动框中的同款产品对同一个客户有不同价格的时候发送邮件给负责人
 */

public interface SendMailDao
{
	/**
	 * 得到所有客户的ID
	 * @return
	 */
	public List<Long> getAllCustomerId();
	
	
	/**
	 * 通过活动框ID得到该活动框下所有的活动产品
	 * @param goodsId
	 * @return
	 */
	public List<MoveframeProduct> getMoveFrameGoodsInfoByMoveFrameId(Long moveFrameId);
	
	
	
	/**
	 * 通过cutomerId得到该客户所对应的活动框编号
	 * @param customerId
	 * @return
	 */
	public List<Long> getMoveFrameIdsByCustomerId(Long customerId);
	
	/**
	 * 通过活动框编号和活动价得到活动框Name
	 * @param parMap
	 * @return
	 */
	public String getMoveFrameName(Map parMap);
	
	
	/**
	 * 通过活动框编号得到负责人的邮件地址
	 * @param userName
	 * @return
	 */
	public String getHeaderEmail(Long moveFrameId);
	
	
	
	
	/**
	 * 得到当前时间前一天创建的所有活动 
	 * @return
	 */
	public List<MoveFrameInfo> getYesterDayNewCreateMoveFrames(Integer type);
	
	
	/**
	 * 得到当前时间前一天修改的所有活动 
	 * @return
	 */
	public List<MoveFrameInfo> getYesterDayModifyMoveFrames(Integer type);
	
	
	/**
	 * 根据活动框ID得到对应的客户信息和活动框所属员工的Email
	 * @param id
	 * @return
	 */
	public  List<MoveFrameInfo> getEmailAndCustomerName(Long id);
	
	
	/**
	 * 得到活动框信息
	 * @param id
	 * @return
	 */
	public MoveFrame getMoveFrameById(Long moveframeId);
	
	/**
	 * 更新log日志的状态0.为未处理 1.为已经处理
	 */
	public void updateLogStatus(Integer type);
	
	
	public List<Long> getMoveFrameIdByGoodsId(Long goodsId);
	
	
	/**
	 * 得到未處理的產品
	 * @return
	 */
	public List<MoveframeProduct> getMoveFrameProductByLog(Map parMap);
	
	/**
	 * 更新特惠产品是否处理
	 * @param parMap
	 */
	public void updateLogDealStatus(Map parMap);
	
	/**
	 * 
	 * @return
	 */
	public List<MoveFrameLog> getMoveFrameLogByType(Integer type);
	
	
}
