package com.huaixuan.network.biz.service.goods;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.Comments;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.Unit;
import com.huaixuan.network.biz.domain.shop.Activity;
import com.huaixuan.network.biz.query.QueryPage;


public interface GoodsManager {
    /* @interface model: Goods */
    public void addGoods(Goods goods);

    /* @interface model: Goods */
    public void editGoods(Goods goods);

    /* @interface model: Goods */
    public void removeGoods(Long goodsId);

    /* @interface model: Goods17,Goods */
    public Goods getGoods(Long goodsId);
    
    public Goods getClientGoodsExist(Map pramas);

    /* @interface model: Goods17,Goods17 */
    public List<Goods> getGoodss();

    //	public boolean dodisAgentGoods(long id,String ctx);
    /**
     * @Description: 修改类目
     * @date 2018-12-29
     */
    public int updateCatcod(Goods goods);
    /**
     * 锟斤拷锟矫达拷锟斤拷
     */
    public boolean doagent(Map map);

    /**
     * 取锟斤拷锟斤拷锟�
     */
    public void dodisagent(long id);

    /**
     * 锟斤拷锟斤拷锟狡稩D锟斤拷锟较ｏ拷锟斤拷取锟斤拷品锟脚恄17
     *
     * @param ids
     *            list锟斤拷只锟斤拷锟絀D锟斤拷息
     * @return
     */
    public List<Goods> getGoodsByIds(List ids);

    /**
     * 锟斤拷EXCEL锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟狡�
     *
     * @param goods
     * @return
     */
    public boolean publishGoodsByExcel(List<Object> goodsList);

    /**
     * 锟斤拷锟斤拷锟斤拷品
     *
     * @param goods
     * @return
     */
    public boolean publishGoods(Goods goods, File[] images, String[] imagesFileName, Map<Integer, List<String>> levels);

    /**
     * 锟睫革拷锟斤拷品
     *
     * @param goods
     * @param images17锟斤拷锟较达拷锟侥硷拷1717
     * @param imagesFileName
     * @param ctx String
     * @return
     */
    public Map<String, String> updateGoods(Goods goods, List<MultipartFile> files);

    /**
     * 锟睫革拷锟斤拷品状1717
     *
     * @param id
     * @param status
     * @return
     */
    public boolean updateGoodsStatus(String id, String status);

    /**
     * 删锟斤拷锟斤拷品图片
     *
     * @param goodsGalleryId
     * @return
     */
    public boolean removeGoodsGallery(String goodsGalleryId);
    
	/**
	 * 锟斤拷页锟斤拷询锟斤拷取goodsList
	 *
	 * @param goodsList
	 * @return
	 */
	
    
    public boolean listingGoods(Long id);
    
    public void autoListing();

    /**
     * 锟斤拷页锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟狡凤拷艕_17
     *
     * @param goods
     * @param currentPage
     * @param pageSize
     * @return
     * @throws Exception
     */
//    public PageUtil<Goods> searchGoods(Goods goods, int currentPage, int pageSize) throws Exception;

    public boolean deListingGoods(Long id);

    /**
     * 锟斤拷品锟斤拷锟斤拷锟斤拷时锟斤拷锟饺★拷锟斤拷锟狡凤拷锟竭的碉拷锟斤拷锟斤拷@17
     *
     * @param catCode
     * @return
     * @throws Exception
     */
    public Map<String, List<Category>> getCategotyMenu(String catCode) throws Exception;

    public Map<String, Object> getCabAndGoodsInfo() throws Exception;

    /**
     * 锟斤拷取锟斤拷17要锟铰硷拷锟教猴拷17
     *
     * @return List<Goods> 锟斤拷只锟斤拷ID锟斤拷锟斤拷荩锟斤拷锟斤拷锟斤拷锟捷讹拷为锟絔17
     * @throws Exception
     */
    public List<Goods> getNeedDelisting() throws Exception;

    /**
     * 统锟狡戯拷17锟斤拷锟斤拷品锟侥碉拷锟斤拷锟斤拷锟斤拷芎锟�锟斤拷锟斤拷锟窖撅拷锟铰架碉拷17,锟皆硷拷锟竭硷拷删锟斤拷锟�7
     *
     * @return 锟斤拷17锟斤拷锟斤拷品锟侥碉拷锟斤拷锟斤拷锟斤拷芎锟�
     */
    public long countAllGoodsClickNum();

    /**
     * 锟斤拷锟斤拷锟斤拷锟狡凤拷锟斤拷锟斤拷锟�锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷
     *
     * @param goodsId
     * @param comments
     */
    public void saveComments(Long goodsId, Comments comments);

    /**
     * 锟斤拷锟斤拷为锟截硷拷锟教猴拷17
     *
     * @param id
     * @param status
     * @return
     */
    public boolean updateCutPrice(Long id);

    /**
     * 取锟斤拷锟截硷拷锟斤拷品
     *
     * @param id
     * @param status
     * @return
     */
    public boolean updateCanelCutPrice(Long id);

    /**
     * 锟斤拷锟斤拷品锟斤拷息取锟斤拷锟斤拷铱锟�7锟斤拷锟斤拷17 锟斤拷锟脚碉拷good.setAttrValue锟斤拷锟斤拷锟斤拷去
     *
     * @param good
     *
     * @return good
     */

    public Goods addGoodsAttr(Goods good);

    public List<Unit> findAllUnits();

    public boolean checkGoodsCode(Goods goods);

    public boolean testGoodsInstance();

    /**
     * 锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷锟斤拷锟斤拷
     * @param goodsId Long
     * @param amount Long  
     * @param tag 状态
     * @return int
     * @author  2009/07/30
     */
    int updateGoodsGoodsNumberById(Long goodsId, Long amount,boolean tag);
    
    int updateGoodsHKGoodsNumberById(Long goodsId, Long amount,boolean tag);
    
    int updateGoodsNumberZero();

    /**
     * 锟睫革拷锟斤拷品锟斤拷锟酵ｏ拷锟筋动锟斤拷锟截价★拷锟斤拷通锟斤拷品锟斤拷
     *
     * @param id
     * @param status
     * @return
     */
    public boolean updateGoodsType(Long id, String type);

    public List<Goods> getGoodsByGoodsIds(List ids);

    /**
     * 锟斤拷锟斤拷锟斤拷品锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
     * @param goodsId Long
     * @param amount Long
     * @return int
     * @author chenyan 2009/09/18
     */
    int updateSaleNumberById(Long goodsId, Long amount);

    boolean doAgentGoods(Map map);

    boolean doCanelAgentGoods(Map map);

    boolean isCutPriceGoods(long id);

    /**
    * 取锟矫达拷锟斤拷锟斤拷品锟斤拷锟斤拷锟斤拷ID
    * @return List
    * @author chenyan 2009/11/13
    */
    List<Long> listAgentGoodsId();

    void updateAgentPrice(Map<String, Object> paramMap);

    /**通锟斤拷锟斤拷品ID锟斤拷询 锟斤拷品锟斤拷锟节仓匡拷锟斤拷锟斤拷锟�
     * @param goodsId
     * @return
     */
    List<AvailableStock> getAvailableStockGroupByGoodsId(Long goodsId);

    /**
     * 锟斤拷锟斤拷锟捷匡拷取锟斤拷锟斤拷锟斤拷品
     * @author zhangwy
     * @param parMap
     * @param page
     * @return
     */
//    List<Goods> getAgentGoodsByData(Map<String, String> parMap, Page page);

    /**
     * 锟斤拷锟斤拷锟捷匡拷取锟斤拷锟斤拷锟斤拷品
     * @author zhangwy
     * @param parMap
     * @param page
     * @return
     */
//    List<Goods> getPaipaiGoodsByData(Map<String, String> parMap, Page page);

    /**
     * 锟斤拷锟絞oodsSn锟斤拷询goods实锟斤拷
     * @param goodsSn
     * @return
     */
    public Goods getGoodsByCode(String goodsSn);

    /* begin add by shenzh Oct 25, 2010 说锟斤拷锟斤拷 */
    /**
     * 锟斤拷锟斤拷锟捷匡拷取锟皆憋拷锟斤拷品
     * @param parMap
     * @param page
     * @return
     */
//    public List<Goods> getTaobaoGoodsByData(Map<String, String> parMap, Page page);
    /**
     * 锟斤拷锟斤拷锟捷匡拷锟饺★拷员锟斤拷锟狡凤拷锟侥�
     * @param parMap
     * @param page
     * @return
     * @author shenzh
     * Oct 25, 2010
     */
    public int getTaobaoGoodsByDataCount(Map<String, String> parMap);
    /* end by shenzh Oct 25, 2010 */

    /**
     * 锟斤拷取锟斤拷锟斤拷锟斤拷司锟斤拷品
     * @author zhangwy
     * @param goodsId
     */
    public Goods getExpressGoods(Long goodsId);

    /**
     * 锟斤拷锟斤拷锟狡凤拷锟斤拷锟斤拷询
     * @author chenhang 2011/02/17
     */
    public Goods getGoodsByGoodsSn(String goodsSn);
    
    /**
     * 锟铰凤拷锟斤拷锟斤拷品
     *
     * @param goods
     * @return
     */
    public boolean newPublishGoods(Goods goods, List<MultipartFile> files);
    
    public boolean newClientToBrowser(Goods goods, File file,String fileName);
    
    public boolean updateGoodsPicture(Goods goods, File file,String fileName,int num);
    
    public boolean editGoodsAddGoodsInstance(Goods goods);
    
    public List<Goods> getGoodsListByConditionWithPage(Goods goods);
    
    /**
     * 锟斤拷锟斤拷锟狡凤拷锟斤拷锟斤拷目锟狡憋拷锟剿�
     * @param gids 锟斤拷品ID
     * @param bid 锟斤拷票锟斤拷司ID
     * @return
     */
    public int updateGoodsBill(List<Long> gids, Long bid);
    
    
    public int checkGoodsNum(Map<String,Object> parMap);
    
    int updatesalesproprice(Goods goods);
    
    /**
     * @author 专场活动分页
     * @date 2018-7-10
     */
    public QueryPage getGoodsActivityByCnditionWithPage(Activity activity,int currPage, int pageSize);

    
    public QueryPage getGoodsListByConditionWithPage(Goods goods, int currPage, int pageSize);
	/**
	 * @param goods
	 * @param currPage
	 * @param pageSize
	 * @param list
	 * @return
	 */
	public QueryPage getGoodsListByConditionWithPageByList(Goods goods,
			int currPage, int pageSize, List<Long> list,List<String> codeList);

	/**
	 * @param goods
	 * @param currPage
	 * @param pageSize
	 * @param codeList
	 * @return
	 */
	public QueryPage getGoodsListByConditionWithPageBycodeList(Goods goods,
			int currPage, int pageSize, List<String> codeList);

	/**
	 * @param goods
	 * @param list
	 * @param codeList
	 * @return
	 */
	public List<Goods> getGoodsListByConditionWithPageByListExport(Goods goods,List<Long> list, List<String> codeList);

	/**
	 * @param goods
	 * @param codeList
	 * @return
	 */
	public List<Goods> getGoodsListByConditionWithPageByCodeListExport(
			Goods goods, List<String> codeList);

	/**
	 * @param locationMap
	 */
	public void updateEmallGoods(Map<String, List<String>> locationMap);

}
