package com.huaixuan.network.biz.dao.stock.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.stock.ShoppingRefundDao;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundSearch;

/**
* @ClassName: ShoppingRefundDaoiBatis
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:49:38
 */
@Repository("shoppingRefundDao")
public class ShoppingRefundDaoiBatis implements ShoppingRefundDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

//	@Autowired
//	private Logger log = Logger.getLogger(ShoppingRefundDaoiBatis.class);

	public long addShoppingRefund(ShoppingRefund shoppingRefund)
			throws Exception {
		return (Long) sqlMapClient.insert("addShoppingRefund", shoppingRefund);
	}

	public void editShoppingRefund(ShoppingRefund shoppingRefund)
			throws Exception {
		sqlMapClient.update("editShoppingRefund", shoppingRefund);
	}

	public void editShoppingRefundStatus(ShoppingRefund shoppingRefund)
			throws Exception {
		sqlMapClient.update("editShoppingRefundStatus", shoppingRefund);
	}

	public void removeShoppingRefund(Long shoppingRefundId) throws Exception {
		sqlMapClient.delete("removeShoppingRefund", shoppingRefundId);
	}

	public ShoppingRefund getShoppingRefund(Long shoppingRefundId)
			throws Exception {
		return (ShoppingRefund) sqlMapClient.queryForObject(
				"getShoppingRefund", shoppingRefundId);
	}

	@SuppressWarnings("unchecked")
	public ShoppingRefund getShoppingRefunds(Map<String, String> paramMap)
			throws Exception {
		List<ShoppingRefund> sr = sqlMapClient.queryForList(
				"getShoppingRefunds", paramMap);
		if (null != sr) {
			return sr.get(0);
		}
		return null;
	}

	public int getCountByParameterMap(Map<String, String> parMap) {
		return (Integer) sqlMapClient.queryForObject("getSRlistCount", parMap);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingRefund> getShoppingRefundListByParameterMap(
			Map<String, String> parameterMap) throws Exception {
		List<ShoppingRefund> shoppingRList = sqlMapClient.queryForList(
				"getSRlists", parameterMap);
		return shoppingRList;
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingRefundSearch> getShoppingRefundNem(Long shoppingRefundId)
			throws Exception {
		return sqlMapClient.queryForList("getShoppingRefundNum",
				shoppingRefundId);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingRefundDSearch> getByParameterMap(
			Map<String, String> parMap) throws Exception {
		return (List<ShoppingRefundDSearch>) sqlMapClient.queryForList(
				"getShoppingRefundlistCount", parMap);
	}

	public int getShoppingRefundCountDetailSearchByParameterMap(
			Map<String, String> parMap) throws Exception {
		return (Integer) sqlMapClient.queryForObject(
				"getShoppingRefundDetailSearchCount", parMap);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingRefundDetailSearch> getShoppingRefundDetailSearchRusultByParameterMap(
			Map parameterMap) throws Exception {
//		if (null != page)
//			return this.findQueryPage("getShoppingRefundDetailSearchList",
//					parameterMap, page);
			return sqlMapClient.queryForList(
					"getShoppingRefundDetailSearchList", parameterMap);
	}

	public int getShoppingRefundCountGatherSearchByParameterMap(
			Map<String, String> parMap) throws Exception {
		return (Integer) sqlMapClient.queryForObject(
				"getShoppingRefundGatherSearchCount", parMap);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingRefundGatherSearch> getShoppingRefundGatherSearchRusultByParameterMap(
			Map parameterMap) throws Exception {
//		if (null != page)
//			return this.findQueryPage("getShoppingRefundGatherSearchList",
//					parameterMap, page);
			return sqlMapClient.queryForList(
					"getShoppingRefundGatherSearchList", parameterMap);
	}

	public List<ShoppingRefundDetail> getBatchNumByRelationNum(
			String relationNum) {
		try {
			return sqlMapClient.queryForList("getBatchNumByRelationNum",
					relationNum);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

}
