package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.domain.agent.AgentTrade;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.Unit;
import com.huaixuan.network.biz.query.QueryPage;

/**
 *
 * @version 3.2.0
 */
@Repository("goodsDao")
public class GoodsDaoiBatis implements GoodsDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public long addGoods(Goods goods) {
		return (Long) this.sqlMapClientTemplate.insert("Goods.addGoods", goods);
	}

	@Override
	public void editGoods(Goods goods) {
		this.sqlMapClientTemplate.update("Goods.editGoods", goods);
	}

	@Override
	public void removeGoods(Long goodsId) {
		this.sqlMapClientTemplate.delete("Goods.removeGoods", goodsId);
	}

	@Override
	public Goods getClientGoodsExist(Map pramas) {
		return (Goods) this.sqlMapClientTemplate.queryForObject("Goods.getClientGoodsExist",
				pramas);
	}

	@Override
	public Goods getGoods(Long goodsId) {
		return (Goods) this.sqlMapClientTemplate.queryForObject("Goods.getGoods",
				goodsId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodss() {
		return this.sqlMapClientTemplate.queryForList("Goods.getGoodss", null);
	}

	@Override
	public Goods getGoodsByTitle(String title) {
		return (Goods) this.sqlMapClientTemplate.queryForObject(
				"Goods.getGoodsByTitle", title);
	}

	@Override
	public Goods getGoodsByGoodsSn(String goodsSn) {
		return (Goods) this.sqlMapClientTemplate.queryForObject(
				"Goods.getGoodsByGoodsSn", goodsSn);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodsByIds(List ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return this.sqlMapClientTemplate.queryForList("Goods.getGoodsByIds", map);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodsByGoodsIds(List ids) {
		if (ids == null || ids.size() < 1) {
			return null;
		}
		Map map = new HashMap();
		map.put("ids", ids);
		return this.sqlMapClientTemplate.queryForList("Goods.getGoodsByIds", map);
	}

	@Override
	public Integer getGoodsListByConditionWithPageCount(Map parMap) {
		return (Integer) sqlMapClientTemplate.queryForObject(
				"Goods.getGoodsByConditionCount", parMap);
	}

	@Override
	public List<Goods> getGoodsListByConditionWithPage(Map parMap) {
		return sqlMapClientTemplate.queryForList("Goods.getGoodsByCondition", parMap);
	}
	
	@Override
	public void autoListing() {
		this.sqlMapClientTemplate.update("autoListing", null);
	}

	@Override
	public QueryPage getGoodsDynamic(Goods goods, int currentPage,
			final int pageSize) {
		QueryPage queryPage = new QueryPage(new Goods());
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject(
				"Goods.searchGoodsDynamicCount", goods);
		queryPage.setTotalItem(count);

		if (count > 0) {
			goods.setStartNum(queryPage.getPageFristItem());
			goods.setEndRow(queryPage.getPageLastItem());

			queryPage.setItems(sqlMapClientTemplate.queryForList(
					"Goods.searchGoodsDynamic", goods));
		}
		return queryPage;
	}

	@Override
	public int getGoodsCountDynamic(Goods goods) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"Goods.searchGoodsDynamicCount", goods);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> showcaseGoods(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("Goods.showcaseGoods", parMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodsAllForCabinet() {
		return this.sqlMapClientTemplate.queryForList("Goods.getCabInfo");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Integer getshowcaseGoodsCount(Map prama) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"Goods.getshowcaseGoodsCount", prama);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getNeedDelisting() {
		return this.sqlMapClientTemplate.queryForList("Goods.getNeedDelisting", null);
	}

	/**
	 *
	 */
	@Override
	public long countAllGoodsClickNum() {
		return (Long) this.sqlMapClientTemplate
				.queryForObject("Goods.countAllGoodsClickNum");
	}

	/**
	 * 
	 * TODO
	 */
	@Override
	@SuppressWarnings("unchecked")
	public QueryPage findGoodsByModifyDate(Date lastModify, int currPage,
			int end) {
		QueryPage queryPage = new QueryPage(new Goods());
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(end);

		Map parMap = new HashMap();
		parMap.put("lastModify", lastModify);
		queryPage.setTotalItem(end);

		queryPage.setItems(sqlMapClientTemplate.queryForList("Goods.findGoodsByModify",
				parMap));
		return queryPage;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> findGoodsByCutPrice(String code, int amount, int day) {
		Map parameters = new HashMap();
		parameters.put("code", code);
		parameters.put("amount", amount);
		parameters.put("amountAll", amount * 3);
		parameters.put("day", day);
		return (List<Goods>) this.sqlMapClientTemplate.queryForList(
				"Goods.findGoodsByCutPrice", parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getTradeGoods(String tradeId) {
		if (tradeId == null) {
			throw new NullPointerException("trade id can't be null.");
		}
		Map parameters = new HashMap();
		parameters.put("tradeId", tradeId);
		return (List<Goods>) this.sqlMapClientTemplate.queryForList(
				"Goods.findTradeGoods", parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getOtherTradeGoods(String goodsId, String tradeId,
			String catCode) {
		if (goodsId == null) {
			throw new NullPointerException("goodsId can't be null.");
		}
		if (tradeId == null) {
			throw new NullPointerException("trade id can't be null.");
		}
		Map parameters = new HashMap();
		parameters.put("goodsId", goodsId);
		parameters.put("tradeId", tradeId);
		parameters.put("catCode", catCode);
		return (List<Goods>) this.sqlMapClientTemplate.queryForList(
				"Goods.findOtherTradeGoods", parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> findBoughtGoods(Map<String, String> query) {

		return (List<Goods>) this.sqlMapClientTemplate.queryForList(
				"Goods.findBoughtGoods", query);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Unit> findAllUnits() {
		return this.sqlMapClientTemplate.queryForList("getUnits");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> findGoodsByCode(String code) {
		return this.sqlMapClientTemplate.queryForList("Goods.findGoodsByCode", code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateGoodsGoodsNumberById(Long goodsId, Long amount,boolean tag) {
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		map.put("amount", amount);
		if(tag){
			map.put("tag", tag);
		}
		return this.sqlMapClientTemplate
				.update("Goods.updateGoodsGoodsNumberById", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateGoodsHKGoodsNumberById(Long goodsId, Long amount,boolean tag) {
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		map.put("amount", amount);
		if(tag){
			map.put("tag", tag);
		}
		return this.sqlMapClientTemplate
				.update("Goods.updateGoodsHKGoodsNumberById", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateGoodsNumberZero() {
		Map map = new HashMap();
		return this.sqlMapClientTemplate
				.update("Goods.updateGoodsNumberZero", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateSaleNumberById(Long goodsId, Long amount) {
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		map.put("amount", amount);
		return this.sqlMapClientTemplate.update("Goods.updateSaleNumberById", map);
	}

	@Override
	public void insertAgentTrade(AgentTrade agentTrade) {
		this.sqlMapClientTemplate.insert("addAgentTrade", agentTrade);

	}

	@Override
	public double getRefund_amount(String tid) {
		Double refund_amount = (Double) this.sqlMapClientTemplate.queryForObject(
				"getRefund_amount", tid);
		if (refund_amount == null) {
			refund_amount = 0.0;
		}
		return refund_amount;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String goodisagent(Map map) {
		String flag = (String) this.sqlMapClientTemplate.queryForObject(
				"Goods.goodisagent", map);
		if (flag == null) {
			flag = "n";
		}
		return flag;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void doagent(Map map) {
		this.sqlMapClientTemplate.update("Goods.doagent", map);
	}

	@Override
	public void dodisagent(long id) {
		this.sqlMapClientTemplate.update("Goods.dodisagent", id);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listAgentGoodsId() {
		return this.sqlMapClientTemplate.queryForList("Goods.listAgentGoodsId");
	}

	@Override
	public void updateAgentPrice(Map<String, Object> paramMap) {
		this.sqlMapClientTemplate.update("Goods.updateAgentPrice", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getAgentGoodsByData(Map parMap,
			int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(new Goods());
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject(
				"Goods.getAgentGoodsByDataCount", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			
			queryPage.setItems(sqlMapClientTemplate.queryForList(
					"Goods.getAgentGoodsByData", parMap));
		}
		return queryPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getPaipaiGoodsByData(Map parMap,
			int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(new Goods());
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject(
				"Goods.getPaipaiGoodsByDataCount", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			queryPage.setItems(sqlMapClientTemplate.queryForList(
					"Goods.getPaipaiGoodsByData", parMap));
		}
		return queryPage;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodsAllForCabinetByWholeSale() {
		return this.sqlMapClientTemplate
				.queryForList("Goods.getGoodsAllForCabinetByWholeSale");
	}

	/* begin add   Oct 25, 2010 */
	@SuppressWarnings("unchecked")
	public QueryPage getTaobaoGoodsByData(Map parMap,
			int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(new Goods());
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject(
				"Goods.getTaobaoGoodsByDataCount", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			queryPage.setItems(sqlMapClientTemplate.queryForList(
					"Goods.getTaobaoGoodsByData", parMap));
		}
		return queryPage;
	}

	public int getTaobaoGoodsByDataCount(Map<String, String> parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"Goods.getTaobaoGoodsByDataCount", parMap);
	}
	
	public void updateHxMoveFrameGoodsNumZoro() {
		sqlMapClientTemplate.update("updateHxMoveFrameGoodsNumZoro", null);
	}
	
	public void updateHxMoveFrameGoodsNum(Map<String, Object> parMap) {
		sqlMapClientTemplate.update("updateHxMoveFrameGoodsNum", parMap);
	}
	
	public void updateHxMoveFrameGoodsSellNum(Map<String, Object> parMap) {
		sqlMapClientTemplate.update("updateHxMoveFrameGoodsSellNum", parMap);
	}
	
	public void updateHxMoveFrameInstanceNumZero() {
		sqlMapClientTemplate.update("updateHxMoveFrameInstanceNumZero", null);
	}
	
	public void updateHxMoveFrameInstanceNum(Map<String, Object> parMap) {
		sqlMapClientTemplate.update("updateHxMoveFrameInstanceNum", parMap);
	}
	
	public void updateHxMoveFrameInstanceSellNum(Map<String, Object> parMap) {
		sqlMapClientTemplate.update("updateHxMoveFrameInstanceSellNum", parMap);
	}

	/* end   Oct 25, 2010 */
	@Override
	public Goods getExpressGoods(Long goodsId) {
		return (Goods) this.sqlMapClientTemplate.queryForObject(
				"Goods.getExpressGoods", goodsId);
	}

	@Override
	public int updateGoodsBill(List<Long> gids, Long bid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gids", gids);
		param.put("bid", bid);
		return sqlMapClientTemplate.update("Goods.updateGoodsBill", param);
	}

	@Override
	public Long getBillId(Long goodsId) {
		return (Long)sqlMapClientTemplate.queryForObject("Goods.getBillId", goodsId);
	}

	@Override
	public String getGooodsMiddleImage(Map map)
	{
		return (String)sqlMapClientTemplate.queryForObject("getGooodsMiddleImage",map);
	}
	
	public  Goods getGooodsByTypeMaterialColor(Map map)
	{
		Object  obj = sqlMapClientTemplate.queryForObject("getGooodsByTypeMaterialColor",map);
		if(obj != null)
			return (Goods)obj;
		return null;
	}

	@Override
	public int updatesalesproprice(Goods goods) {
		return sqlMapClientTemplate.update("updatesalesproprice", goods);
	}

	@Override//专场活动
	public Integer getGoodsActivityByConditionWithPageCounts(Map parMap) {
		
		return (Integer) sqlMapClientTemplate.queryForObject(
				"Goods.getGoodsByActivityConditionCount", parMap);
	}

	@Override//专场活动
	public List<Goods> getGoodsActivityByConditionWithPages(Map parMap) {
		
		return sqlMapClientTemplate.queryForList("Goods.getGoodsByActivityCondition", parMap);
	}

	/* 取得某个goodsId一个月内的销售数量
	 * 
	 */
	@Override
	public int GetSalNumInOneMoth(Goods goods) {
		Integer count = (Integer) this.sqlMapClientTemplate.queryForObject("getOnMonthNum", goods);
//		System.out.println(count);
		if(count==null){
			count=0;
		}
		return count;
	}

	/* 
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateSaleNumberOneOrThree(Long goodsId,int one, int three) {
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		map.put("onenum", one);
		map.put("threenum", three);
		int o = this.sqlMapClientTemplate.update("updateSaleNumberOneOrThree", map);
//		System.out.println(o);
//		Goods gd = getGoods(goodsId);
//		System.out.println(gd.getSaleNumber());
//		System.out.println(gd.getHkthNumber());
		return o;
	}

	/* (non-Javadoc)
	 *清空字段sal_number和hk_th_number的值
	 */
	@Override
	public int updateSalNumberZero() {
		Map map = new HashMap();
		return this.sqlMapClientTemplate
				.update("Goods.updateSalNumberZero", map);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateCatcodDao(long goodsId,String catCode) {
		
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		map.put("catCode", catCode);
		int o = this.sqlMapClientTemplate.update("updateGoodsCatcod", map);
		if(o>0){
			return o;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.goods.GoodsDao#updateEmallGoodsWeight(com.huaixuan.network.biz.domain.goods.Goods)
	 */
	@Override
	public void updateEmallGoodsWeight(Goods g) {
		sqlMapClientTemplate.update("updateEmallGoodsWeight", g);
		
	}
	
	
}
