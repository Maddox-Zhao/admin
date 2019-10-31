package com.huaixuan.network.biz.dao.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 *
 * @version 3.2.0
 */
public interface ExpressDao {
	void addExpress(Express express);

	void editExpress(Express express);

	void removeExpress(Long expressId);

	Express getExpress(Long expressId);

	List<Express> getExpresss();

	/**
	 * 根据物流名称查找物流信息(由于物流名称不能重复，理论上list.size最多只能为1)
	 *
	 * @param expressName
	 *            String
	 * @return List
	 * @author chenyan 2009/08/07
	 */
	List<Express> getExpressByName(String expressName);

	/**
	 * 根据物流代码查找物流信息
	 *
	 * @param expressName
	 *            String
	 * @return List
	 * @author fanyj 2009/12/07
	 */
	List<Express> getExpressByCode(String expressCode);

	/**
	 * 检索物流信息总数
	 *
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenyan 2009/08/10
	 */
	int getExpressCountByCond(Map<String, String> parMap);

	/**
	 * 检索物流信息
	 *
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return QueryPage
	 * @author chenyan 2009/08/10
	 */
	QueryPage listExpressByCond(Map parMap, int currentPage, int pageSize);

	/**
	 * 根据ID更新物流信息状态
	 *
	 * @param status
	 *            String
	 * @param id
	 *            Long
	 * @return int
	 * @author chenyan 2009/08/10
	 */
	int updateExpressStatusById(String status, Long id);

	/**
	 *
	 * @author chenhang 2011-5-5
	 * @description 根据物流ID修改淘宝同步物流
	 * @param id
	 * @return
	 */
	String getInterfaceExpressCodeByExpressId(Long id);

	/**
	 *
	 * @author chenhang 2011-5-17
	 * @description
	 * @param id
	 * @return
	 */
	Express getExpressIdByInterfaceExpressCode(String interfaceExpressCode);

	/**
	 *
	 * @author chenhang 2011-5-18
	 * @description
	 * @param id
	 * @return
	 */
	Express getExpressIdByExpressCode(String expressCode);

	/**
	 * 根据状态取得物流信息
	 *
	 * @param status
	 *            String
	 * @return List
	 * @author chenyan 2009/08/17
	 */
	List<Express> listExpressByStatus(String status);

	/**
	 * 物流费用统计数量
	 *
	 * @param parMap
	 * @author zhangwy
	 * @return
	 */
	int getFreightCountByParameterMap(Map<String, String> parMap);

	/**
	 * 物流费用列表
	 *
	 * @param parMap
	 * @author zhangwy
	 * @param QueryPage
	 * @return
	 */
	QueryPage getFreightListsByParameterMap(
			FreightStatisticsQuery freightStatisticsQuery, int currentPage,
			int pageSize);

	/**
	 * 物流总费用
	 *
	 * @param parMap
	 * @return
	 */
	double getFreightAmountByParameterMap(
			FreightStatisticsQuery freightStatisticsQuery);

	/**
	 * 获取商品关联物流公司
	 *
	 * @param parMap
	 * @return
	 */
	List<Express> getGoodsExpressRelation(Map parMap);

	/**
	 * 获取非商品关联物流公司
	 *
	 * @param parMap
	 * @return
	 */
	List<Express> getNotGoodsExpressRelation(Map parMap);
}
