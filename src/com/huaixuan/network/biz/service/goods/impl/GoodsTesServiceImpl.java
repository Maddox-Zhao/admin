package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsTesDao;
import com.huaixuan.network.biz.domain.goods.Goods;

import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsTesService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
@Service("goodsTesService")
public class GoodsTesServiceImpl implements GoodsTesService{
	@Autowired
	private GoodsTesDao goodsTesDao;
	
	@Override
	public List<Goods> selectAllGoods() {
		List<Goods> selectAllGoods = goodsTesDao.selectAllGoods();
		return selectAllGoods;
	}
	/**
	 * miniUI产品列表分页
	 * @param  searchMap
	 * @param    
	 * @return MiniUiGrid  
	 * @author zxt
	 * @date 2018-5-17
	 */
	@Override
	public MiniUiGrid searchAllOrderId(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = goodsTesDao.searchMiniuiGoodsCount(searchMap);
		MiniUiGrid grid = new MiniUiGrid();
		grid.setTotal(count);
		if(count>0){
			//从页面的传来的请求已经装换成map集合的形式，从map集合中的到从页面传来的pageIndex，pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
	    	//将pagesize.pageIndex,count放入QueryPage中，经过判断计算得到，所在这页的第一行是库存的第几条数据,
	    	//将所在这页的第一行是库存的第几条数据和每页的条数追加进map集合
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理.
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow"))){
				
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<Goods> list = goodsTesDao.selectMiniuiAllGoodsidOne(searchMap);
			//System.out.println(list);
			if(list!=null && list.size()>0){
				grid.setData(list);
			}
		}
		return grid;
	}
	
	
	@Override//员工查询测试
	public QueryPage getMyCollectionListByConditionWithPage(Goods goods, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
		//查询总条数
		int count = goodsTesDao.getGoodsListByConditionWithPageCount(pramas,goods);
		if(count>0){
			//当前页
			queryPage.setCurrentPage(currPage);
			//每页多少行
			queryPage.setPageSize(pageSize);
			//总页数
			queryPage.setTotalItem(count);/*setTotal(count);*/
			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
/*			goods.setStartNum(queryPage.getPageFristItem());
			goods.setEndRow(queryPage.getPageLastItem());*/
			//搜索详情
			List<Goods> goodsList = goodsTesDao.getGoodsListByConditionWithPage(pramas,goods);
			if(goodsList !=null && goodsList.size()>0){
				queryPage.setItems(goodsList);
				}
		}
		return queryPage;
	}
	
	@Override//购物车商品查询
	public QueryPage getMyShoppingCartConditionWithPages(Goods goods, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(goods);
		Map pramas = queryPage.getParameters();
		//查询总条数
		int count = goodsTesDao.getShoppingCartWithPageCount(pramas);
		if(count>0){
			//当前页
			queryPage.setCurrentPage(currPage);
			//每页多少行
			queryPage.setPageSize(pageSize);
			//总页数
			queryPage.setTotalItem(count);
			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
			//搜索详情
			List<Goods> goodsList = goodsTesDao.getShoppingCartWithTestPage(pramas);
			
			if(goodsList !=null && goodsList.size()>0){
				queryPage.setItems(goodsList);
				}
		}
		return queryPage;
	}
}
