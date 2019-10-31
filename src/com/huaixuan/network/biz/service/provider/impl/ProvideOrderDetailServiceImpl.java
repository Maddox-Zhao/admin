/**
 * 
 */
package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao;
import com.huaixuan.network.biz.domain.product.Transfer;
import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.provider.ProvideOrderDetailService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 * @author TT
 * 
 */
@Service("ProvideOrderDetailService")
public class ProvideOrderDetailServiceImpl implements ProvideOrderDetailService {

	@Autowired
	private ProvideOrderDetailDao provideOrderDetailDao;
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProvideOrderDetailService#getProvideOrderDetail(java.util.Map)
	 */
	@Override
	public MiniUiGrid getProvideOrderDetail(Map<String, String> searchmap) {
		QueryPage queryPage = new QueryPage(new Object());
		MiniUiGrid gird = new MiniUiGrid();
		int count = provideOrderDetailDao.selectOrderDetailCount(searchmap);
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
			List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;
	}
	
	public List<ProvideOrderDetail> searchOneProviderOrderOrderId(String orderId) {
		
		List selectProviderOrderId=null;
		
		if(orderId!=null && orderId!=""){
			selectProviderOrderId = provideOrderDetailDao.selectProviderOrderId(orderId);
		}
		return selectProviderOrderId;
	}
	
	@Override
	public MiniUiGrid searchOneProviderOrderOrderIdService(Map<String, String> searchmap) {
		QueryPage queryPage = new QueryPage(new Object());
		MiniUiGrid gird = new MiniUiGrid();
		int count = provideOrderDetailDao.selectOrderDetailCount(searchmap);
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
			List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public Integer updateProviderOrderDetaillService(Map<String, String> searchmap) {
		Integer uodd = provideOrderDetailDao.updateProviderOrderDetailDao(searchmap);
		
		return uodd;
	}

}
