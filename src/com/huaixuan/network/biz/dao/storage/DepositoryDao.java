package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface DepositoryDao {
	/* @interface model: ���һ��Depository��¼ */
	Long addDepository(Depository depository);

	/* @interface model: ����һ��Depository��¼ */
	void editDepository(Depository depository);

	/* @interface model: ɾ��һ��Depository��¼ */
	void removeDepository(Long depositoryId);

	/* @interface model: ��ѯһ��Depository�������Depository���� */
	Depository getDepository(Long depositoryId);

	public List<Depository> getDepositorys();

	/* @interface model: ��ѯ����Depository�������Depository����ļ��� */
	QueryPage getDepositorysByParMap(DepositoryQuery query, int currentPage, int pageSize, boolean isPage);

	int getCountByParMap(Map<String, String> parMap);

	List<Depository> getDeplistByFirstDepId(Long depfirstId);

	List<Depository> getDepositorysByDepFirstId(Map<String, Object> paramMap);

	List<Depository> getRightDeplistByFirstDepId(Long depfirstId);
}
