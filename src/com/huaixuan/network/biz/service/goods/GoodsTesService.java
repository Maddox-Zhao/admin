package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.MyCollection;
import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface  GoodsTesService {
	public List<Goods> selectAllGoods();
	public QueryPage getMyCollectionListByConditionWithPage(Goods goods, int currPage, int pageSize);
	/**
	 * @Description: TODO miniui分页
	 * @param  searchMap
	 * @return MiniUiGrid  
	 * @author zxt
	 * @date 2018-5-17
	 */
	public MiniUiGrid searchAllOrderId(Map<String, String> searchMap);
	/**
	 * 购物车商品查询测试
	 * @Description: TODO
	 * @param goods
	 * @param currPage
	 * @param  pageSize
	 * @return QueryPage  
	 * @author zxt
	 * @date 2018-5-23
	 */
	public QueryPage getMyShoppingCartConditionWithPages(Goods goods, int currPage, int pageSize);
	
}
