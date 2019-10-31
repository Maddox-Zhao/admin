package com.huaixuan.network.biz.service.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.MotorTransInfo;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.domain.express.query.ExpressQuery;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.domain.express.query.MotorTransQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 锟斤拷锟斤拷锟皆讹拷锟斤拷锟(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface ExpressManager {
	/* @interface model: 锟斤拷锟揭伙拷锟紼xpress锟斤拷录 */
	public void addExpress(Express express);

	/* @interface model: 锟斤拷锟斤拷一锟斤拷Express锟斤拷录 */
	public void editExpress(Express express);

	/* @interface model: 删锟斤拷一锟斤拷Express锟斤拷录 */
	public void removeExpress(Long expressId);

	/* @interface model: 锟斤拷询一锟斤拷Express锟斤拷锟,锟斤拷锟斤拷Express锟斤拷锟斤拷 */
	public Express getExpress(Long expressId);

	/* @interface model: 锟斤拷询锟斤拷锟斤拷Express锟斤拷锟,锟斤拷锟斤拷Express锟斤拷锟斤拷募锟斤拷锟 */
	public List<Express> getExpresss();

	/**
	 * 根据物流名称查找物流信息
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
	 * @Title: getMotorTransInfoById
	 * @Description:根据id读取汽运信息
	 * @param @param id
	 * @param @return
	 * @param @
	 * @return MotorTransInfo
	 * @throws
	 */
	MotorTransInfo getMotorTransInfoById(Long id);

	/**
	 * @Title: addInterfaceUserTrade
	 * @Description: 增加汽运信息
	 * @param @param interfaceUserTrade
	 * @param @
	 * @return void
	 * @throws
	 */
	void addMotorTransInfo(MotorTransInfo motorTransInfo);

	/**
	 * @Title: editMotorTransInfo
	 * @Description: 编辑汽运信息
	 * @param @param motorTransInfo
	 * @param @
	 * @return void
	 * @throws
	 */
	void editMotorTransInfo(MotorTransInfo motorTransInfo);

	/**
	 * @Title: delMotorTransInfo
	 * @Description: 删除汽运信息
	 * @param @param id
	 * @return void
	 * @throws
	 */
	void delMotorTransInfo(Long id);

	/**
	 * 检索汽运信息总数
	 *
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenhang 2010/12/16
	 */
	int getMotorCountByCond(Map<String, String> parMap);

	/**
	 * 检索汽运信息
	 *
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenhang 2010/12/16
	 */
	QueryPage listMotorByCond(MotorTransQuery motorTransQuery, int currPage, int pageSize);

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
	 * @return List
	 * @author chenyan 2009/08/10
	 */
	QueryPage listExpressByCond(ExpressQuery expressQuery, int currPage, int pageSize);

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
	 * @param page
	 * @return
	 */
	QueryPage getFreightListsByParameterMap(FreightStatisticsQuery freightStatisticsQuery, int currPage, int pageSize);

	/**
	 * 物流费用总价
	 *
	 * @param parMap
	 * @return
	 */
	double getFreightAmountByParameterMap(FreightStatisticsQuery freightStatisticsQuery);

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

	/**
	 * 物流分析（物流公司）计数
	 *
	 * @param parMap
	 * @return
	 * @author chenhang 2011/01/17
	 */
	int getExpressAnalysisByExpCount(Map parMap);

	/**
	 * 物流分析（物流公司）
	 *
	 * @param expressDistQuery
	 * @param currPage
	 * @param pageSize
	 * @param isPage  是否分页
	 * @return
	 */
	QueryPage getExpressAnalysisByExp(ExpressDistQuery expressDistQuery, int currPage, int pageSize, boolean isPage);

	/**
	 * 物流分析（省份）计数
	 *
	 * @param parMap
	 * @return
	 * @author chenhang 2011/01/17
	 */
	int getExpressAnalysisByProCount(Map parMap);

	/**
	 * 物流分析（省份）
	 *
	 * @param parMap
	 * @return
	 * @author chenhang 2011/01/17
	 */
	QueryPage getExpressAnalysisByPro(ExpressDistQuery expressDistQuery, int currPage, int pageSize, boolean isPage);
}
