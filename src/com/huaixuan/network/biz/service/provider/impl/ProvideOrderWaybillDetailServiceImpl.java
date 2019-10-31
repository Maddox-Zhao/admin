/**
 * 
 */
package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailYShangDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetailYShang;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.provider.ProvideOrderWaybillDetailService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 * @author TT
 * 
 */
//@Service("providerOrderService")
@Service("ProvideOrderWaybillDetailService")
public class ProvideOrderWaybillDetailServiceImpl implements ProvideOrderWaybillDetailService {
	
	@Autowired
	private ProvideOrderWaybillDetailDao providerOrderDao;
	
    @Autowired
	private ProvideOrderWaybillDetailYShangDao provideOrderWaybillDetailYShangDao;
    
	
	@Override
	public MiniUiGrid searchAllProviderOrderId(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = providerOrderDao.searchMiniuiProviderOrderCount(searchMap);
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
			List<ProvideOrderWaybillDetail> list = providerOrderDao.selectMiniuiAllProviderOrder(searchMap);
			//System.out.println(list);
			if(list!=null && list.size()>0){
				grid.setData(list);
			}
		}
		return grid;
	}

	/*  
	 * 
	 */
	@Override
	public MiniUiGrid selectMiniuiOrderListDetailService(Map<String, String> searchmap) {
		QueryPage queryPage = new QueryPage(new Object());
		MiniUiGrid gird = new MiniUiGrid();
		int count = provideOrderWaybillDetailYShangDao.selectMiniuiOrderListDetailCount(searchmap);
		gird.setTotal(count);
		if(count >0){
			//int currPage,int pageSize
			String pageIndex = searchmap.get("pageIndex");
			String pageSize = searchmap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			if(!"yes".equalsIgnoreCase(searchmap.get("noStartRowAndEndRow"))){
				searchmap.put("startRow", queryPage.getPageFristItem()+"");
				searchmap.put("endRow", queryPage.getPageSize()+"");
			}
			List<ProvideOrderWaybillDetailYShang> list = provideOrderWaybillDetailYShangDao.selectMiniuiOrderListDetail(searchmap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;
	}


}
