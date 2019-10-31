package com.huaixuan.network.biz.service.hx.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.DxrecordDao;
import com.huaixuan.network.biz.domain.hx.Dxrecord;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.DxrecordService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

@Service("DxrecordService")
public class DxrecordServiceImpl implements DxrecordService{

	@Autowired
	private DxrecordDao dxrecordDao;
	@Override
	public QueryPage queryDxrecordList(Map<String, String> searchMap) {
			QueryPage queryPage = new QueryPage(searchMap);
			//返回代销用户登录信息总数量
			int count = dxrecordDao.getDxrecordLsitCount(searchMap);
			MiniUiGrid gird = new MiniUiGrid();
			gird.setTotal(count);
			queryPage.setTotalItem(count);
			if(count >0){
				String pageIndex = searchMap.get("pageIndex");
				String pageSize = searchMap.get("pageSize");
				queryPage.setCurrentPageForMiniUi(pageIndex);
				queryPage.setPageSizeString(pageSize);
				queryPage.setTotalItem(count);
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageLastItem()+"");
				List<Dxrecord> dxrecordList = dxrecordDao.queryDxrecordList(searchMap);
				if(dxrecordList != null && dxrecordList.size() >0)
				{
					queryPage.setItems(dxrecordList);
				}
			}
		return queryPage;
	}
	public QueryPage statisticsDxrecordList(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(searchMap);
		//返回代销用户数量
		int count = dxrecordDao.getstatisticsDxrecordListCount(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		queryPage.setTotalItem(count);
		if(count >0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItem(count);
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageLastItem()+"");
			List<Dxrecord> dxrecordList = dxrecordDao.statisticsDxrecordList(searchMap);
			if(dxrecordList != null && dxrecordList.size() >0)
			{
				queryPage.setItems(dxrecordList);
			}
		}
		return queryPage;
	}

}
