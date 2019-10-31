package com.huaixuan.network.biz.service.purchase;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.purchase.BaseData;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 *2012-7-5 ����04:45:29
 *Mr_Yang
 */
public interface BaseDataService
{
	/**
	 * ͨ��ҳ���ɸѡ�����õ���Ӧ�Ĳ�Ʒ
	 * @param parMap
	 * @return
	 */
	public List<BaseData> getBaseDataTmpByMap(BaseData baseData);
	
	/**
	 * ͨ��ɸѡ��ӵõ��Ĳ�Ʒȥ��ѯ��Ӧ�����
	 * @param parMap
	 * @return
	 */
	public QueryPage getBaseDataByMap(BaseData baseData,int currPage,int pageSize);
	
	
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
	 * @param idBrand Ʒ��ID
	 * @param type 1.ͨ���ͺŲ�ѯ 2.ͨ����ʲ�ѯ 3.ͨ����ɫ��ѯ
	 * @param goodsId 
	 * @return
	 */
	public QueryPage getInStorageInfoBy(String type,Long goodsId,int currPage,int pageSize);
	
}
