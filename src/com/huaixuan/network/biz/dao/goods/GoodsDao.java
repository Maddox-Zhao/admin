package com.huaixuan.network.biz.dao.goods;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.agent.AgentTrade;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.Unit;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.exception.DaoException;
import com.huaixuan.network.biz.query.QueryPage;

public interface GoodsDao {
	long addGoods(Goods goods);

	void editGoods(Goods goods);

	void removeGoods(Long goodsId);

	Goods getGoods(Long goodsId);
	
	Goods getClientGoodsExist(Map pramas);

	Goods getGoodsByTitle(String title);
	/**
	 * @Description:修改类目
	 * @date 2018-12-29
	 */
	int updateCatcodDao(long goodsId,String catCode);
	
	
	
	
	public void autoListing();

	List<Goods> getGoodss();

	/**
	 * 锟斤拷锟斤拷锟狡凤拷锟脚诧拷询
	 *
	 * @author chenhang 2011/02/17
	 */
	Goods getGoodsByGoodsSn(String goodsSn);

	/**
	 * 锟叫讹拷锟斤拷品锟角凤拷锟斤拷源锟斤拷锟�y" "n"
	 */

	public String goodisagent(Map map);

	/**
	 * 锟斤拷锟矫达拷锟斤拷
	 */
	public void doagent(Map map);

	/**
	 * 取锟斤拷锟斤拷锟�
	 */
	public void dodisagent(long id);

	/**
	 *
	 * 锟斤拷锟接达拷锟斤拷锟斤拷品锟斤拷录
	 */
	public void insertAgentTrade(AgentTrade agentTrade);

	/**
	 *
	 * @param tid
	 *            取锟矫讹拷应锟斤拷锟斤拷锟剿匡拷锟�
	 */
	public double getRefund_amount(String tid);

	/**
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	List<Goods> getGoodsByIds(List ids);

	List<Goods> showcaseGoods(Map parMap);

	Integer getGoodsListByConditionWithPageCount(Map parMap);

	List<Goods> getGoodsListByConditionWithPage(Map parMap);

	/**
	 * 锟斤拷锟斤拷锟斤拷目 锟斤拷品锟斤拷疲锟侥ｏ拷锟�锟桔革拷锟斤拷锟�锟斤拷锟斤拷锟斤拷品
	 *
	 * @param goods
	 * @return
	 */
	QueryPage getGoodsDynamic(Goods goods, int currentPage, final int pageSize);

	int getGoodsCountDynamic(Goods goods);

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷goods锟叫憋拷
	 *
	 * @return @
	 */
	List<Goods> getGoodsAllForCabinet();

	/**
	 * 锟斤拷锟斤拷锟斤拷目 锟斤拷品锟斤拷疲锟侥ｏ拷锟�锟桔革拷锟斤拷锟�锟斤拷锟斤拷锟斤拷品锟斤拷目
	 *
	 * @param Map
	 * @return Integer
	 */
	public Integer getshowcaseGoodsCount(Map prama);

	/**
	 * 锟斤拷取锟铰硷拷锟斤拷品
	 *
	 * @return @
	 */
	public List<Goods> getNeedDelisting();

	/**
	 *
	 */
	long countAllGoodsClickNum();

	/**
	 *
	 * @param lastModify
	 * @param end
	 * @return
	 */
	QueryPage findGoodsByModifyDate(Date lastModify, int currPage, int end);

	/**
	 *
	 * @param lastModify
	 * @param end
	 * @return
	 */
	public List<Goods> findGoodsByCutPrice(String code, int amount, int day);

	/**
	 * 锟斤拷锟剿伙拷锟斤拷
	 *
	 * @param tradeId
	 * @return
	 */
	public List<Goods> getTradeGoods(String tradeId);

	/**
	 * 锟斤拷锟剿伙拷锟斤拷
	 *
	 * @param goodsId
	 * @param tradeId
	 * @return
	 */
	public List<Goods> getOtherTradeGoods(String goodsId, String tradeId,
			String catCode);

	List<Unit> findAllUnits();

	List<Goods> findGoodsByCode(String code);

	/**
	 * 锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷锟斤拷锟斤拷
	 *
	 * @param goodsId
	 *            Long
	 * @param amount
	 *            Long
	 * @return int
	 * @author  2009/07/30
	 */
	int updateGoodsGoodsNumberById(Long goodsId, Long amount,boolean tag);
	
	int updateGoodsHKGoodsNumberById(Long goodsId, Long amount,boolean tag);
	
	int updateGoodsNumberZero();
	

	/***************************************************************************
	 * 锟斤拷锟絞oods_id锟斤拷询锟斤拷锟剿达拷锟斤拷品锟斤拷锟剿伙拷锟斤拷锟斤拷锟叫╋拷锟斤拷频锟斤拷锟狡�
	 *
	 * @param goodsId
	 * @param catCode
	 * @return
	 */
	public List<Goods> findBoughtGoods(Map<String, String> query);

	public List<Goods> getGoodsByGoodsIds(List ids);

	/**
	 * 锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 *
	 * @param goodsId
	 *            Long
	 * @param amount
	 *            Long
	 * @return int
	 * @author chenyan 2009/09/18
	 */
	int updateSaleNumberById(Long goodsId, Long amount);

	/**
	 * 取锟矫达拷锟斤拷锟斤拷品锟斤拷锟斤拷锟斤拷ID
	 *
	 * @return List
	 * @author chenyan 2009/11/13
	 */
	List<Long> listAgentGoodsId();

	void updateAgentPrice(Map<String, Object> paramMap);

	/**
	 * 锟斤拷锟斤拷锟捷匡拷锟饺★拷锟斤拷锟斤拷锟狡�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage getAgentGoodsByData(Map parMap, int currPage,
			int pageSize);

	/**
	 * 锟斤拷锟斤拷锟捷匡拷锟饺★拷锟斤拷锟斤拷锟狡�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage getPaipaiGoodsByData(Map parMap, int currPage,
			int pageSize);

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷锟絞oods锟叫憋拷
	 *
	 * @return @
	 */
	List<Goods> getGoodsAllForCabinetByWholeSale();

	/* begin add by shenzh Oct 25, 2010 */
	/**
	 * 锟斤拷锟斤拷锟捷匡拷锟饺★拷员锟斤拷锟狡凤拷锟侥�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 * @author shenzh Oct 25, 2010
	 */
	int getTaobaoGoodsByDataCount(Map<String, String> parMap);
	
	void updateHxMoveFrameGoodsNumZoro();
	
	void updateHxMoveFrameGoodsNum(Map<String, Object> parMap);
	
	void updateHxMoveFrameGoodsSellNum(Map<String, Object> parMap);
	
	void updateHxMoveFrameInstanceNumZero();
	
	void updateHxMoveFrameInstanceNum(Map<String, Object> parMap);
	
	void updateHxMoveFrameInstanceSellNum(Map<String, Object> parMap);

	/**
	 * 锟斤拷锟斤拷锟捷匡拷锟饺★拷员锟斤拷锟狡�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<Goods> getTaobaoGoodsByData(Map parMap, Page page);
	/* end by shenzh Oct 25, 2010 */

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷司锟斤拷品
	 *
	 * @author zhangwy
	 * @param goodsId
	 */
	Goods getExpressGoods(Long goodsId);
	
	/**
	 * 锟斤拷锟斤拷锟狡凤拷锟斤拷锟斤拷目锟狡憋拷锟剿�
     * @param gids 锟斤拷品ID
     * @param bid 锟斤拷票锟斤拷司ID
	 * @return 锟斤拷锟铰碉拷锟斤拷锟斤拷
	 */
	int updateGoodsBill(List<Long> gids, Long bid);
	
	/**
	 * 锟斤拷取锟斤拷票id
	 * @param goodsId
	 * @return
	 */
	Long getBillId(Long goodsId);
	
	
	
	String getGooodsMiddleImage(Map map);
	
	
	Goods getGooodsByTypeMaterialColor(Map map);

	int updatesalesproprice(Goods goods);
	
	Integer getGoodsActivityByConditionWithPageCounts(Map map);
	
	List<Goods> getGoodsActivityByConditionWithPages(Map map);

	/**
	 * @param goods
	 * @return
	 * 取得某个goodsID在一个月内的销售数量
	 */
	int GetSalNumInOneMoth(Goods goods);

	/**
	 * @param goodsId 
	 * @param one
	 * @param three
	 * @return
	 */
	int updateSaleNumberOneOrThree(Long goodsId, int one, int three);

	/**
	 * @return
	 */
	int updateSalNumberZero();

	/**
	 * @param g
	 */
	void updateEmallGoodsWeight(Goods g);

}
