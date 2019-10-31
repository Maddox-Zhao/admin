package com.huaixuan.network.biz.service.express.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.express.ExpressAnalysisDao;
import com.huaixuan.network.biz.dao.express.ExpressDao;
import com.huaixuan.network.biz.dao.express.MotorTransInfoDao;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.MotorTransInfo;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.domain.express.query.ExpressQuery;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.domain.express.query.MotorTransQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.express.ExpressManager;

/**
 * �����Զ����??(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Service("expressManager")
public class ExpressManagerImpl implements ExpressManager {

	// /**
	// * 事务模板
	// */
	// @Autowired
	// protected TransactionTemplate transactionTemplate;

	@Autowired
	ExpressDao expressDao;
	@Autowired
	MotorTransInfoDao motorTransInfoDao;
	@Autowired
	ExpressAnalysisDao expressAnalysisDao;

	@Override
	public void addExpress(Express express) {
		// log.info("ExpressManagerImpl.addExpress method");
		expressDao.addExpress(express);
	}

	@Override
	public void editExpress(Express express) {
		// log.info("ExpressManagerImpl.editExpress method");
		expressDao.editExpress(express);
	}

	@Override
	public void removeExpress(Long expressId) {
		// log.info("ExpressManagerImpl.removeExpress method");
			expressDao.removeExpress(expressId);
	}

	@Override
	public Express getExpress(Long expressId) {
		// log.info("ExpressManagerImpl.getExpress method");
		return expressDao.getExpress(expressId);
	}

	@Override
	public List<Express> getExpresss() {
		// log.info("ExpressManagerImpl.getExpresss method");
		return expressDao.getExpresss();
	}

	@Override
	public List<Express> getExpressByName(String expressName) {
		// log.info("ExpressManagerImpl.getExpressByName method");
		return expressDao.getExpressByName(expressName);
	}

	@Override
	public List<Express> getExpressByCode(String expressCode) {
		// log.info("ExpressManagerImpl.getExpressByCode method");
		return expressDao.getExpressByCode(expressCode);
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
		// log.info("ExpressManagerImpl.getMotorTransInfoById method");
		return this.motorTransInfoDao.getMotorTransInfoById(id);
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
		// log.info("ExpressManagerImpl.addMotorTransInfo method");
		this.motorTransInfoDao.addMotorTransInfo(motorTransInfo);
	}

	/**
	 * @
	 *
	 * @Title: editMotorTransInfo
	 * @Description: 编辑汽运信息
	 * @param @param motorTransInfo
	 * @param @
	 * @return void
	 * @throws
	 */
	@Override
	public void editMotorTransInfo(MotorTransInfo motorTransInfo) {
		// log.info("ExpressManagerImpl.editMotorTransInfo method");
		this.motorTransInfoDao.editMotorTransInfo(motorTransInfo);
	}

	/**
	 * @Title: delMotorTransInfo
	 * @Description: 删除汽运信息
	 * @param @param id
	 * @return void @
	 */
	@Override
	public void delMotorTransInfo(Long id) {
		// log.info("ExpressManagerImpl.delMotorTransInfo method");
		this.motorTransInfoDao.delMotorTransInfo(id);
	}

	@Override
	public int getMotorCountByCond(Map<String, String> parMap) {
		// log.info("ExpressManagerImpl.getMotorCountByCond method");
		return this.motorTransInfoDao.getMotorCountByCond(parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage listMotorByCond(MotorTransQuery motorTransQuery, int currentPage, int pageSize) {
		// log.info("ExpressManagerImpl.listMotorByCond method");
		QueryPage queryPage = new QueryPage(motorTransQuery);
        Map parMap = queryPage.getParameters();
		return this.motorTransInfoDao.listMotorByCond(parMap, currentPage, pageSize);
	}

	@Override
	public int getExpressCountByCond(Map<String, String> parMap) {
		// log.info("ExpressManagerImpl.getExpressCountByCond method");
		return expressDao.getExpressCountByCond(parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage listExpressByCond(ExpressQuery expressQuery, int currentPage, int pageSize) {
		// log.info("ExpressManagerImpl.listExpressByCond method");
		QueryPage queryPage = new QueryPage(expressQuery);
        Map parMap = queryPage.getParameters();
		return expressDao.listExpressByCond(parMap, currentPage, pageSize);
	}

	@Override
	public String getInterfaceExpressCodeByExpressId(Long id){
		return expressDao.getInterfaceExpressCodeByExpressId(id);
	}

	@Override
	public int updateExpressStatusById(String status, Long id) {
		// log.info("ExpressManagerImpl.updateExpressStatusById method");
		return expressDao.updateExpressStatusById(status, id);
	}

	@Override
	public List<Express> listExpressByStatus(String status) {
		// log.info("ExpressManagerImpl.listExpressByStatus method");
		return expressDao.listExpressByStatus(status);
	}

	@Override
	public int getFreightCountByParameterMap(Map<String, String> parMap) {
		// log.info("ExpressManagerImpl.getFreightCountByParameterMap method");
		return expressDao.getFreightCountByParameterMap(parMap);
	}

	public QueryPage getFreightListsByParameterMap(FreightStatisticsQuery freightStatisticsQuery, int currentPage, int pageSize) {
		// log.info("ExpressManagerImpl.getFreightCountByParameterMap method");
		return expressDao.getFreightListsByParameterMap(freightStatisticsQuery, currentPage, pageSize);
	}

	@Override
	public int getExpressAnalysisByExpCount(Map parMap) {
		// log.info("ExpressManagerImpl.getExpressAnalysisByExpCount method");
		return this.expressAnalysisDao.getExpressAnalysisByExpCount(parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getExpressAnalysisByExp(ExpressDistQuery expressDistQuery, int currentPage, int pageSize, boolean isPage) {
		// log.info("ExpressManagerImpl.getExpressAnalysisByExp method");
		QueryPage queryPage = new QueryPage(expressDistQuery);
        Map parMap = queryPage.getParameters();
		return this.expressAnalysisDao.getExpressAnalysisByExp(parMap, currentPage, pageSize, isPage);
	}

	@Override
	public int getExpressAnalysisByProCount(Map parMap) {
		// log.info("ExpressManagerImpl.getExpressAnalysisByProCount method");
		return this.expressAnalysisDao.getExpressAnalysisByProCount(parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getExpressAnalysisByPro(ExpressDistQuery expressDistQuery, int currPage, int pageSize, boolean isPage) {
		// log.info("ExpressManagerImpl.getExpressAnalysisByPro method");
		QueryPage queryPage = new QueryPage(expressDistQuery);
        Map parMap = queryPage.getParameters();
		return this.expressAnalysisDao.getExpressAnalysisByPro(parMap, currPage, pageSize, isPage);
	}

	@Override
	public double getFreightAmountByParameterMap(FreightStatisticsQuery freightStatisticsQuery) {
		return expressDao.getFreightAmountByParameterMap(freightStatisticsQuery);
	}

	@Override
	public List<Express> getGoodsExpressRelation(Map parMap) {
		return expressDao.getGoodsExpressRelation(parMap);
	}

	@Override
	public List<Express> getNotGoodsExpressRelation(Map parMap) {
		return expressDao.getNotGoodsExpressRelation(parMap);
	}
}
