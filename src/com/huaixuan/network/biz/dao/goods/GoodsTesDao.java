package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;


import com.huaixuan.network.biz.domain.goods.Goods;


public interface  GoodsTesDao {
	/**
	 * 搜索全部商品
	 * @return
	 */
	public List<Goods> selectAllGoods();
	//
	public Integer getGoodsListByConditionWithPageCount(Map param,Goods goods);
	public List<Goods> getGoodsListByConditionWithPage(Map param,Goods goods);
	//miniui产品列表分页
	public int searchMiniuiGoodsCount(Map<String, String> searchMap);
	public List<Goods> selectMiniuiAllGoodsidOne(Map<String, String> searchMap); 
//	public QueryPage getGoodsListByConditionWithPage(Map parMap, int currPage, int pageSize);
	//购物车
	public Integer getShoppingCartWithPageCount(Map parMap);
	public List<Goods> getShoppingCartWithTestPage(Map parMap);
	
}
