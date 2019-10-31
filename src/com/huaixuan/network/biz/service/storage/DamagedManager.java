package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Damaged;
import com.huaixuan.network.biz.domain.storage.query.DamagedQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface DamagedManager {

	/**
	 * ���ӱ��е������¼
	 *
	 * @param damaged
	 * @return long
	 */
	public long addDamaged(Damaged damaged);

	/**
	 * �޸ı��е������¼
	 *
	 * @param damaged
	 */
	public void editDamaged(Damaged damaged);

	/**
	 * ɾ�����е������¼
	 *
	 * @param damagedId
	 */
	public void removeDamaged(Long damagedId);

	/**
	 * ����parMap��ȡ���е������¼
	 *
	 * @param parMap
	 * @return Damaged
	 */
	public Damaged getDamaged(Map parMap);

	/**
	 * ��ȡ���еı��е������¼
	 *
	 * @return
	 */
	public List<Damaged> getDamageds();

	/**
	 * ���ݲ�ѯ�����õ����е���¼����
	 */
	public QueryPage getDamagedListsByParameterMap(DamagedQuery damagedQuery,
			int currPage, int pageSize);

	/**
	 * ���ݲ�ѯ�����õ����е���¼����
	 */
	public int getDamagedCountByParameterMap(Map<String, String> parMap);
}
