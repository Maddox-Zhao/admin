package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DepositoryFirst;

public interface DepositoryFirstDao {

	List<DepositoryFirst> getDepositoryFirstListByIds(List<Long> ids);

	DepositoryFirst getDepositoryById(Long id);

	/**
	 * �õ����еĲֿ���Ϣ
	 * 
	 * @return
	 */
	List<DepositoryFirst> getDepositoryFirstList();

	Long insertDepositoryFirst(DepositoryFirst depositryFirst);

	void updateDepositoryFirst(DepositoryFirst depositryFirst);

	/**
	 * �������Ʋ鵱ǰ��¼
	 * 
	 * @param depFirstName
	 * @return
	 */
	DepositoryFirst getDepositoryByName(String depFirstName);

	/**
	 * ȡ�÷���������һ���ֿ���б�
	 * 
	 * @param parMap
	 * @return
	 * @author lilei 2010/06/11
	 */
	List<DepositoryFirst> getDepositoryFirstListByParMap(Map<String, ?> parMap);
}
