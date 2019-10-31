package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.ShoppingCart;
import com.huaixuan.network.biz.domain.goods.PromContext;
import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationFullGive;
import com.huaixuan.network.biz.domain.goods.PromationGive;
import com.huaixuan.network.biz.domain.goods.PromationVO;
import com.huaixuan.network.biz.query.PromationQuery;
import com.huaixuan.network.biz.query.QueryPage;


 public interface PromationManager {
	 	/* @interface model: Promation */
	 	public void addPromation(Promation promation);

	 	/* @interface model: Promation */
	 	public void editPromation(Promation promation);

	 	/* @interface model: Promation */
	 	public void removePromation(Long promationId);

	 	/* @interface model: Promation,Promation */
	 	public Promation getPromation(Long promationId);
	 	
	 	/**
	     * 根据套餐类型查询有效的套餐记录
	     * @param context
	     * @return
	     */
	 	public Promation getPromationByModeCode(String modeCode);

	 	/**
	     * 根据套餐名称返回该套餐
	     * @param context
	     * @return
	     */
	 	public List<Promation> getPromationByName(String name);

	    /* @interface model: Promation,Promation */
	 	public List<Promation> getPromations();

	 	/**
	 	 * 根据商品ID和优惠上下文，获取商品所能享受的优惠列表
	 	 * 使用范围在商品详情、套餐详情页面、购物车列表、
	 	 * @param context
	 	 * @return
	 	 */
	    public List<PromationVO> getPromationsByContext(PromContext context);
	    
	    /**
	     * 判断商品是否是买就包邮套餐中的商品
	     * @param context
	     * @return
	     */
	    public boolean isExemptPostageGoods(PromContext context);
	    
	    /**
	     * 获得一个商品的打折促销折扣
	     * @param context
	     * @return
	     */
	    public double getGoodsDiscount(Long goodsId);

	    /**
	     * 根据优惠DO和优惠上下文进行数据新增
	     * @param promation
	     * @param promContext
	     * @return
	     */
	    public boolean addPromation(Promation promation,PromContext promContext);

	    /**
	     * 根据优惠ID,获取优惠数据跟优惠规则，规则以优惠上下文进行返回。
	     * @param promationId
	     * @returnd
	     */
	    public Map getPromContextByPromation(Long promationId);

	    /**
	     * 根据优惠ID列表,获取优惠数据跟优惠规则列表，规则以优惠上下文进行返回。
	     * @param promationId List
	     * @returnd
	     */
	    public List<Map> getPromationMapListByIds(List promationIdList);


	    public List<Promation> getPromationsByIds(List ids);

	    /**
	     * 根据优惠DO和优惠上下文进行数据修改
	     * @param promation
	     * @param promContext
	     * @return
	     */
	    public boolean editPromation(Promation promation,PromContext promContext);


	    /**
	     * 根据优惠ID，跟优惠的上下文，获取所能享受的优惠信息
	     *
	     * @param promationId
	     * @param promContext
	     * @return
	     */
	    public PromationVO getPromationInfo(Long promationId,PromContext promContext);
	    /**
	     * 根据分页和相关信息查询PromationList
	     * 使用范围在Promation查询
	     * @param context
	     * @return
	     */
//	    public List<Promation> getPromationsByPage(Map<String, Object> conditions,Page page);

	    /**
	     * 根据类型或许满就送（减）套餐
	     * @param scope
	     * @return
	     */
	    public List<PromationVO> getGivePromationList(String scope);

	    /**
	     * 定时器自动取消冻结
	     *
	     * @param promationId
	     * @param promContext
	     * @return
	     */
	    public void autoCanelFreeze();

	    /**
	     * 获取一个商品下全部买就赠的产品
	     * @author zhangwy
	     * @param goodsId
	     * @return
	     */
	    public List<PromationGive> getNewPromationGiveList(Long goodsId);
	    
	    /**
	     * 获取购物车中满足满就送套餐的所有产品
	     * @author fanyj
	     * @param shoppingCartsList
	     * @return
	     */
	    public List<PromationGive> getFullGiveGoodsList(List<ShoppingCart> shoppingCartsList);
	    
	    /**
	     * 获取满就送套餐记录集合
	     * @author fanyj
	     * @return
	     */
	    public Map<String,List<PromationFullGive>> getPromationFullGiveList();
	    
	    /**
	     * 套餐列表
	     * @param promation
	     * @param currPage
	     * @param pageSize
	     * @return
	     */
	    public QueryPage getPromationListByConditionWithPage(PromationQuery promation ,int currPage, int pageSize);
 }
