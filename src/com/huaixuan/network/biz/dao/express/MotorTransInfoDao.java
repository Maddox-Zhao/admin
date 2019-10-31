package com.huaixuan.network.biz.dao.express;

import java.util.Map;

import com.huaixuan.network.biz.domain.express.MotorTransInfo;
import com.huaixuan.network.biz.query.QueryPage;

public interface MotorTransInfoDao {
	/**
	 * @Title: getMotorTransInfoById
	 * @param @param id
	 * @param @return
	 * @param @
	 * @return MotorTransInfo
	 * @throws
	 */
	MotorTransInfo getMotorTransInfoById(Long id);

	/**
	 * @Title: addInterfaceUserTrade
	 * @param @param interfaceUserTrade
	 * @param @
	 * @return void
	 * @throws
	 */
	void addMotorTransInfo(MotorTransInfo motorTransInfo);

	/**
	 * @Title: editMotorTransInfo
	 * @param @param motorTransInfo
	 * @param @
	 * @return void
	 * @throws
	 */
	void editMotorTransInfo(MotorTransInfo motorTransInfo);

	/**
	 * @Title: delMotorTransInfo
	 * @param @param id
	 * @return void
	 * @throws
	 */
	void delMotorTransInfo(Long id);

	/**
	 * 
	 * @param parMap
	 *            Map
	 * @return int
	 * @author 2010/12/16
	 */
	int getMotorCountByCond(Map<String, String> parMap);

	/**
	 * @param parMap
	 * @param currPage
	 * @param pageSize
	 * @return @
	 */
	QueryPage listMotorByCond(Map parMap, int currPage, int pageSize);
}