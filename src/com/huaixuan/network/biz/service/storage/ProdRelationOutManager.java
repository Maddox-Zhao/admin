package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationOut;

/**
 * �����м����ݱ�(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ProdRelationOutManager {
	/**
	 * ����
	 * 
	 * @param prodRelationOut
	 */
	public void addProdRelationOut(ProdRelationOut prodRelationOut);

	/**
	 * �༭
	 * 
	 * @param prodRelationOut
	 */
	public void editProdRelationOut(ProdRelationOut prodRelationOut);

	/**
	 * ɾ��
	 * 
	 * @param prodRelationOutId
	 */
	public void removeProdRelationOut(Long prodRelationOutId);

	/**
	 * ����ID��ѯ����
	 * 
	 * @param prodRelationOutId
	 * @return
	 */
	public ProdRelationOut getProdRelationOut(Long prodRelationOutId);

	/**
	 * ��ѯ���ж���
	 * 
	 * @return
	 */
	public List<ProdRelationOut> getProdRelationOuts();

	/**
	 * �õ���ӡ���ⵥ�������
	 * 
	 * @param outDepId
	 * @return
	 */
	public List<ProdRelationOut> getPrintList(Long outDepId);

	/**
	 * ȡ���ѷ�����Ĳ�Ʒ����
	 * 
	 * @param map
	 *            Map
	 * @return Long
	 * @author chenyan 2009/08/02
	 */
	Long getDistributedAmount(Map map);
}
