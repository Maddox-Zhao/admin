package com.huaixuan.network.biz.dao.active;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.active.MoveFrame;
import com.huaixuan.network.biz.domain.active.MoveFrameInfo;
import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;

/**
 * @author Mr_Yang
 * @version ����ʱ�䣺2012-3-27 ����05:06:32
 * ����ͬ����е�ͬ���Ʒ��ͬһ���ͻ��в�ͬ�۸��ʱ�����ʼ���������
 */

public interface SendMailDao
{
	/**
	 * �õ����пͻ���ID
	 * @return
	 */
	public List<Long> getAllCustomerId();
	
	
	/**
	 * ͨ�����ID�õ��û�������еĻ��Ʒ
	 * @param goodsId
	 * @return
	 */
	public List<MoveframeProduct> getMoveFrameGoodsInfoByMoveFrameId(Long moveFrameId);
	
	
	
	/**
	 * ͨ��cutomerId�õ��ÿͻ�����Ӧ�Ļ����
	 * @param customerId
	 * @return
	 */
	public List<Long> getMoveFrameIdsByCustomerId(Long customerId);
	
	/**
	 * ͨ������źͻ�۵õ����Name
	 * @param parMap
	 * @return
	 */
	public String getMoveFrameName(Map parMap);
	
	
	/**
	 * ͨ������ŵõ������˵��ʼ���ַ
	 * @param userName
	 * @return
	 */
	public String getHeaderEmail(Long moveFrameId);
	
	
	
	
	/**
	 * �õ���ǰʱ��ǰһ�촴�������л 
	 * @return
	 */
	public List<MoveFrameInfo> getYesterDayNewCreateMoveFrames(Integer type);
	
	
	/**
	 * �õ���ǰʱ��ǰһ���޸ĵ����л 
	 * @return
	 */
	public List<MoveFrameInfo> getYesterDayModifyMoveFrames(Integer type);
	
	
	/**
	 * ���ݻ��ID�õ���Ӧ�Ŀͻ���Ϣ�ͻ������Ա����Email
	 * @param id
	 * @return
	 */
	public  List<MoveFrameInfo> getEmailAndCustomerName(Long id);
	
	
	/**
	 * �õ������Ϣ
	 * @param id
	 * @return
	 */
	public MoveFrame getMoveFrameById(Long moveframeId);
	
	/**
	 * ����log��־��״̬0.Ϊδ���� 1.Ϊ�Ѿ�����
	 */
	public void updateLogStatus(Integer type);
	
	
	public List<Long> getMoveFrameIdByGoodsId(Long goodsId);
	
	
	/**
	 * �õ�δ̎��ĮaƷ
	 * @return
	 */
	public List<MoveframeProduct> getMoveFrameProductByLog(Map parMap);
	
	/**
	 * �����ػݲ�Ʒ�Ƿ���
	 * @param parMap
	 */
	public void updateLogDealStatus(Map parMap);
	
	/**
	 * 
	 * @return
	 */
	public List<MoveFrameLog> getMoveFrameLogByType(Integer type);
	
	
}
