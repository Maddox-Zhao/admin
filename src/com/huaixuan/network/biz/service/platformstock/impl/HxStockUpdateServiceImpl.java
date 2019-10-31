package com.huaixuan.network.biz.service.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.platformstock.HxPlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.platformstock.HxStockUpdateService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

@Service("HxStockUpdateService")
public class HxStockUpdateServiceImpl implements HxStockUpdateService {
     @Autowired
     private HxPlatformStockUpdateDao hxPlatformStockUpdateDao;
	@Override
	public MiniUiGrid searchStockUpdateHx(Map<String, String> searchmap) {
     
		QueryPage queryPage = new QueryPage(new Object());
		//查询出库存的数量
		int count = hxPlatformStockUpdateDao.HxStockUpdatecnt(searchmap);
 		//miniui的用法，用于返回页面，需要两个属性，total,data
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
		    	if (!"yes".equalsIgnoreCase(searchmap.get("noStartRowAndEndRow"))) {
		    	searchmap.put("startRow", queryPage.getPageFristItem()+"");
		    	searchmap.put("endRow", queryPage.getPageSize()+"");
		    	}
		    	List<StockUpdate> sulist = hxPlatformStockUpdateDao.HxSelectStockUpdate(searchmap);
		    	
		    	if(sulist!=null && sulist.size()>0){
		    		grid.setData(sulist);
		    	}
		    	
		    }		
				
				
				
		return grid;
	}

}
