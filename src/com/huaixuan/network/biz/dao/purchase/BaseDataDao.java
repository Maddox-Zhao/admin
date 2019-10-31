package com.huaixuan.network.biz.dao.purchase;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.purchase.BaseData;

/**
 *2012-7-5 ����03:56:30
 *Mr_Yang
 */
public interface BaseDataDao
{
	/**
	 * ͨ��ҳ���ɸѡ�����õ���Ӧ�Ĳ�Ʒ
	 * @param parMap
	 * @return
	 */
	public List<BaseData> getBaseDataTmpByMap(Map parMap);
	

	public Integer getBaseDataTmpByMapCount(Map parMap);
	
	/**
	 * ͨ��ɸѡ��ӵõ��Ĳ�Ʒȥ��ѯ��Ӧ�����
	 * @param parMap
	 * @return
	 */
	public BaseData getBaseDataByMap(Map parMap);
	
	
	/**
	 * ͨ��GoodsId�õ��й�仯�����ϼ�
	 * @param goodsId
	 * @return
	 */
	public Map getNotSameHKPrice(Long goodsId);
	
	
	/**
	 * ͨ��GoodsId�õ��й�仯��ŷ�޼�
	 * @param goodsId
	 * @return
	 */
	public Map getNotSameEUPrice(Long goodsId);
	
	
	/**
	 * ����ṩ��������ѯ����¼
	 * @param parMap
	 * @return
	 */
	public List<BaseData> getInStorageInfoByMap(Map parMap);
	
	
	/**
	 * ����ṩ��������ѯ����¼����
	 * @param idBrand Ʒ��ID
	 * @param type ���ʻ����ͺŻ�����ɫ
	 * @return
	 */
	public Integer getInStorageInfoByMapCount(Map parMap);

}
