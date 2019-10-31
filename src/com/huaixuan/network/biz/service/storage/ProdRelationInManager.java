package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationIn;

/**
 * ��ⵥ�м�������Ϣ(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ProdRelationInManager {
	/**
	 * ����
	 * 
	 * @param prodRelationIn
	 */
	public void addProdRelationIn(ProdRelationIn prodRelationIn);

	/**
	 * �༭
	 * 
	 * @param prodRelationIn
	 */
	public void editProdRelationIn(ProdRelationIn prodRelationIn);

	/**
	 * ɾ��
	 * 
	 * @param prodRelationInId
	 */
	public void removeProdRelationIn(Long prodRelationInId);

	/**
	 * ����ID��ѯ����
	 * 
	 * @param prodRelationInId
	 * @return
	 */
	public ProdRelationIn getProdRelationIn(Long prodRelationInId);

	/**
	 * ��ѯ���ж���
	 * 
	 * @return
	 */
	public List<ProdRelationIn> getProdRelationIns();

	/**
	 * �õ���ӡ��ⵥ�������
	 * 
	 * @param inDepId
	 * @return
	 */
	public List<ProdRelationIn> getPrintList(Long inDepId);

	/**
	 * ȡ�ñ�����Ŀ�λID�����������ڸ��¿���
	 * 
	 * @param prodRelationIn
	 *            ProdRelationIn
	 * @return List
	 * @author chenyan 2009/07/25
	 */
	List<ProdRelationIn> getLocIdAndAmountForStorage(ProdRelationIn prodRelationIn);

	/**
	 * ȡ���ѷ�����Ĳ�Ʒ�����������˻�����
	 * 
	 * @param map
	 *            Map
	 * @return List
	 * @author chenyan 2009/08/03
	 */
	@SuppressWarnings("unchecked")
	List<ProdRelationIn> getSalesDistributedAmount(Map map);

	/**
	 * ȡ���ѷ�����Ĳ�Ʒ�������ɹ����̵㣩
	 * 
	 * @param map
	 *            Map
	 * @return Long
	 * @author chenyan 2009/08/03
	 */
	@SuppressWarnings("unchecked")
	Long getShoppingAndCheckDistributedAmount(Map map);
}
