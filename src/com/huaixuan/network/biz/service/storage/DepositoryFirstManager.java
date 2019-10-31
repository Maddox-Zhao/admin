package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DepositoryFirst;

public interface DepositoryFirstManager {

	/**
	 * ����һ���ֿ�id��ȡһ���ֿ��б�
	 * 
	 * @param ids
	 * @return
	 */
	public List<DepositoryFirst> getDepositoryFirstListByIds(List<Long> ids);

	/**
	 * ����һ���ֿ�id��ȡһ���ֿ�
	 * 
	 * @param id
	 * @return
	 */
	public DepositoryFirst getDepositoryById(Long id);

	/**
	 * ��ȡ���е�һ���ֿ���Ϣ
	 * 
	 * @return
	 */
	List<DepositoryFirst> getDepositoryFirstList();

	boolean updateDepositoryFirst(DepositoryFirst depositryFirst);

	Long insertDepositoryFirst(DepositoryFirst depositryFirst);

	DepositoryFirst getDepositoryByName(String depFirstName);

	/**
	 * ȡ�÷���������һ���ֿ���б�
	 * 
	 * @param parMap
	 * @return
	 * @author lilei 2010/06/11
	 */
	public List<DepositoryFirst> getDepositoryFirstListByParMap(Map parMap);

}
