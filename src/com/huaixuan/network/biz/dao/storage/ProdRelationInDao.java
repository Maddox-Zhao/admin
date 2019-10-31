package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationIn;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ProdRelationInDao {
	/* @interface model: ProdRelationIn */
	long addProdRelationIn(ProdRelationIn prodRelationIn);

	/* @interface model: ProdRelationIn */
	void editProdRelationIn(ProdRelationIn prodRelationIn);

	/* @interface model: ProdRelationIn */
	void removeProdRelationIn(Long prodRelationInId);

	/* @interface model: ProdRelationIn,ProdRelationIn */
	ProdRelationIn getProdRelationIn(Long prodRelationInId);

	/* @interface model: ProdRelationIn,ProdRelationIn */
	List<ProdRelationIn> getProdRelationIns();

	/**
	 * �õ���ӡ��ⵥ�������
	 * 
	 * @param inDepId
	 * @return @
	 */
	List<ProdRelationIn> getPrintList(Long inDepId);

	/**
	 * ���������ǰ��ɾ������
	 * 
	 * @param prodRelationIn
	 *            ProdRelationIn
	 * @author chenyan 2009/07/23
	 */
	void removeProdRelationInForAdd(ProdRelationIn prodRelationIn);

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
