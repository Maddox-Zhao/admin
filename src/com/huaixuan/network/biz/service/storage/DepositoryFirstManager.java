package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DepositoryFirst;

public interface DepositoryFirstManager {

	/**
	 * 根据一级仓库id获取一级仓库列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<DepositoryFirst> getDepositoryFirstListByIds(List<Long> ids);

	/**
	 * 根据一级仓库id获取一级仓库
	 * 
	 * @param id
	 * @return
	 */
	public DepositoryFirst getDepositoryById(Long id);

	/**
	 * 获取所有的一级仓库信息
	 * 
	 * @return
	 */
	List<DepositoryFirst> getDepositoryFirstList();

	boolean updateDepositoryFirst(DepositoryFirst depositryFirst);

	Long insertDepositoryFirst(DepositoryFirst depositryFirst);

	DepositoryFirst getDepositoryByName(String depFirstName);

	/**
	 * 取得符合条件的一级仓库的列表
	 * 
	 * @param parMap
	 * @return
	 * @author lilei 2010/06/11
	 */
	public List<DepositoryFirst> getDepositoryFirstListByParMap(Map parMap);

}
