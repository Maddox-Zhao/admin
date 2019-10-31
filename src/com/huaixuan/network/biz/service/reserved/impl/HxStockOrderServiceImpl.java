package com.huaixuan.network.biz.service.reserved.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.reserved.HxStockOrderDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.reserved.HxStockOrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
@Service("HxStockOrderService")
public class HxStockOrderServiceImpl implements HxStockOrderService {
	@Autowired
    private HxStockOrderDao hxStockOrderDao;
	@Override
	public MiniUiGrid searchStockOrder(Map<String, String> searchmap) {
		
		QueryPage queryPage = new QueryPage(new Object());
		
		int count = hxStockOrderDao.searchStockOrderOne(searchmap);
		
		MiniUiGrid grid = new MiniUiGrid();
	       //total已得
	    grid.setTotal(count);
	
	    if(count>0){
	    	//从页面的传来的请求已经装换成map集合的形式，从map集合中的到从页面传来的pageIndex，pageSize
	    	String pageIndex = searchmap.get("pageIndex");
	    	String pageSize = searchmap.get("pageSize");
	    	//将pagesize.pageIndex,count放入QueryPage中，经过判断计算得到，所在这页的第一行是库存的第几条数据,
	    	//将所在这页的第一行是库存的第几条数据和每页的条数追加进map集合
	    	
	    	queryPage.setCurrentPageForMiniUi(pageIndex);
	    	queryPage.setPageSizeString(pageSize);
	    	queryPage.setTotalItemForMiniUi(count);
	    	searchmap.put("startRow", queryPage.getPageFristItem()+"");
	    	searchmap.put("endRow", queryPage.getPageSize()+"");
	    	List<PlatFormOrderRecord> listt = hxStockOrderDao.searchStockOrderDc(searchmap);
	    	
	    	if(listt!=null && listt.size()>0){
	    		
	    		grid.setData(listt);
	    	}
	    	
	    }		
			
			
			
	return grid;
}
	
	//用于首页面导出查询
	@Override
	public MiniUiGrid searchStockOrderList(Map<String, String> searchmap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = hxStockOrderDao.searchStockOrderOne(searchmap); // 
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = searchmap.get("pageIndex");
			String pageSize = searchmap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			if(!"yes".equalsIgnoreCase(searchmap.get("noStartRowAndEndRow")))
			{
				searchmap.put("startRow", queryPage.getPageFristItem()+"");
				searchmap.put("endRow", queryPage.getPageSize()+"");
			}
			List<PlatFormOrderRecord> list = hxStockOrderDao.searchStockOrderDc(searchmap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}
	
	
	@Override
	public MiniUiGrid searchDertailsOrder(Map<String, String> searchmap) {		
		QueryPage queryPage = new QueryPage(new Object());		
		int count = hxStockOrderDao.searchorDerdetails(searchmap);		
		MiniUiGrid grid = new MiniUiGrid();
	       //total已得
	    grid.setTotal(count);
	
	    if(count>0){
	    	//从页面的传来的请求已经装换成map集合的形式，从map集合中的到从页面传来的pageIndex，pageSize
	    	String pageIndex = searchmap.get("pageIndex");
	    	String pageSize = searchmap.get("pageSize");
	    	//将pagesize.pageIndex,count放入QueryPage中，经过判断计算得到，所在这页的第一行是库存的第几条数据,
	    	//将所在这页的第一行是库存的第几条数据和每页的条数追加进map集合
	    	
	    	queryPage.setCurrentPageForMiniUi(pageIndex);
	    	queryPage.setPageSizeString(pageSize);
	    	queryPage.setTotalItemForMiniUi(count);
	    	
	    	searchmap.put("startRow", queryPage.getPageFristItem()+"");
	    	searchmap.put("endRow", queryPage.getPageSize()+"");
			
	    	List<PlatFormOrderDetails> list = hxStockOrderDao.searchDetailsOrder(searchmap);
	    	
	    	if(list!=null && list.size()>0){
	    		
	    		grid.setData(list);
	    	}
	    	
	    }		
			
			
			
	return grid;
}
    //用于订单详情的查询
	//全部订单详情的导出
	@Override
	public MiniUiGrid selectDertails(Map<String, String> searchmap) {
		QueryPage queryPage = new QueryPage(new Object());		
		int count = hxStockOrderDao.selectDerdetails(searchmap);		
		MiniUiGrid grid = new MiniUiGrid();
	       //total已得
	    grid.setTotal(count);
	
	    if(count>0){
	    	//从页面的传来的请求已经装换成map集合的形式，从map集合中的到从页面传来的pageIndex，pageSize
	    	String pageIndex = searchmap.get("pageIndex");
	    	String pageSize = searchmap.get("pageSize");
	    	//将pagesize.pageIndex,count放入QueryPage中，经过判断计算得到，所在这页的第一行是库存的第几条数据,
	    	//将所在这页的第一行是库存的第几条数据和每页的条数追加进map集合
	    	
	    	queryPage.setCurrentPageForMiniUi(pageIndex);
	    	queryPage.setPageSizeString(pageSize);
	    	queryPage.setTotalItemForMiniUi(count);
	    	if(!"yes".equalsIgnoreCase(searchmap.get("noStartRowAndEndRow")))
			{
	    	searchmap.put("startRow", queryPage.getPageFristItem()+"");
	    	searchmap.put("endRow", queryPage.getPageSize()+"");
			}
	    	List<PlatFormOrderDetails> list = hxStockOrderDao.selectDetailsOrder(searchmap);
	    	
	    	if(list!=null && list.size()>0){
	    		
	    		grid.setData(list);
	    	}
	    	
	    }					
	return grid;
	}
	
	
}
