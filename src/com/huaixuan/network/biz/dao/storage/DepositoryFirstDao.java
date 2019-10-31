package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DepositoryFirst;

public interface DepositoryFirstDao {

	List<DepositoryFirst> getDepositoryFirstListByIds(List<Long> ids);

	DepositoryFirst getDepositoryById(Long id);

	/**
	 * 得到所有的仓库信息
	 * 
	 * @return
	 */
	List<DepositoryFirst> getDepositoryFirstList();

	Long insertDepositoryFirst(DepositoryFirst depositryFirst);

	void updateDepositoryFirst(DepositoryFirst depositryFirst);

	/**
	 * 根据名称查当前记录
	 * 
	 * @param depFirstName
	 * @return
	 */
	DepositoryFirst getDepositoryByName(String depFirstName);

	/**
	 * 取得符合条件的一级仓库的列表
	 * 
	 * @param parMap
	 * @return
	 * @author lilei 2010/06/11
	 */
	List<DepositoryFirst> getDepositoryFirstListByParMap(Map<String, ?> parMap);
}
