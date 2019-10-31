package com.huaixuan.network.biz.dao.express.ibatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.express.MotorTransInfoDao;
import com.huaixuan.network.biz.domain.express.MotorTransInfo;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("motorTransInfoDao")
public class MotorTransInfoDaoiBatis implements MotorTransInfoDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public int getMotorCountByCond(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject("getMotorCountByCond", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage listMotorByCond(Map parMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getMotorCountByCond", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* ��ҳ��ѯ */
			queryPage.setItems(sqlMapClient.queryForList("listMotorByCond", parMap));
		}
		return queryPage;
	}

	/**
	 * @Title: getMotorTransInfoById
	 * @Description: 根据id读取汽运信息
	 * @param @param id
	 * @param @return
	 * @param @
	 * @return MotorTransInfo
	 * @throws
	 */
	@Override
	public MotorTransInfo getMotorTransInfoById(Long id) {
		return (MotorTransInfo) this.sqlMapClient.queryForObject("getMotorTransInfo", id);
	}

	/**
	 * @Title: addInterfaceUserTrade
	 * @Description: 增加汽运信息
	 * @param @param interfaceUserTrade
	 * @param @
	 * @return void
	 * @throws
	 */
	@Override
	public void addMotorTransInfo(MotorTransInfo motorTransInfo) {
		this.sqlMapClient.insert("addMotorTransInfo", motorTransInfo);
	}

	/**
	 * @Title: editMotorTransInfo
	 * @Description: 编辑汽运信息
	 * @param @param motorTransInfo
	 * @param @
	 * @return void
	 * @throws
	 */
	@Override
	public void editMotorTransInfo(MotorTransInfo motorTransInfo) {
		this.sqlMapClient.update("editMotorTransInfo", motorTransInfo);
	}

	/**
	 * @Title: delMotorTransInfo
	 * @Description: 删除汽运信息
	 * @param @param id
	 * @return void
	 * @throws
	 */
	@Override
	public void delMotorTransInfo(Long id) {
		this.sqlMapClient.update("updateStatus", id);
	}
}