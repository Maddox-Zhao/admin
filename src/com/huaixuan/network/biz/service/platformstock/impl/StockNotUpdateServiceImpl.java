package com.huaixuan.network.biz.service.platformstock.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.platformstock.StockNotUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.StockNotUpdate;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.platformstock.StockNotUpdateService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.hundsun.network.melody.common.util.StringUtil;



/**
 * @author Mr_Yang   2016-11-10 下午02:25:56
 **/

@Service("stockNotUpdateService")
public class StockNotUpdateServiceImpl implements StockNotUpdateService
{
	@Autowired
	private StockNotUpdateDao stockNotUpdateDao; 

	@Override
	public void deleteStockNotUpdate(Long id)
	{
		stockNotUpdateDao.deleteStockNotUpdate(id);

	}

	@Override
	public void insertStockNotUpdate(StockNotUpdate stockNotUpdate)
	{
		stockNotUpdateDao.insertStockNotUpdate(stockNotUpdate);

	}

	@Override
	public MiniUiGrid searchStockNotUpdateByMap(Map<String, String> searchMap)
	{
		QueryPage queryPage = new QueryPage(new Object());
		int count =  stockNotUpdateDao.searchStockNotUpdateCntByMap(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			
			//不要分页 查询所有
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow")))
			{
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<StockNotUpdate> list =  stockNotUpdateDao.searchStockNotUpdateByMap(searchMap);
			
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	 @Override
	public void updateStockUpdateSku2Null(String sku, String type, String field)
	{
		 Map<String,String> map = new HashMap<String, String>();
		 map.put("sku", sku);
		 map.put("type", type);
		 map.put("field", field);
		 stockNotUpdateDao.updateStockUpdateSku2Null(map);
		
	}

	@Override
	public void dealNotUpdate2PlatformSku()
	{
	 
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("noStartRowAndEndRow", "yes");
		MiniUiGrid gird = searchStockNotUpdateByMap(searchMap);
		List<StockNotUpdate> list = (List<StockNotUpdate>) gird.getData();
		if(list == null) return;
		for(StockNotUpdate s : list)
		{
			// shangpin - 尚品   kaola - 考拉  
			// zhenpin   - 珍品  siku - 寺库  
			// yhd - 一号店   higo - higo  tmall - 天猫
			String platform = s.getPlatform();
			String field = "";
			String sku = s.getSku();
			String type = s.getType();
			if("shangpin".equals(platform))
			{
				field = "shangpin_sku";
			}
			else if("kaola".equals(platform))
			{
				field = "kaola_sku";
			}
			else if("zhenpin".equals(platform))
			{
				field = "zhenpin_sku";
			}
			else if("yhd".equals(platform))
			{
				field = "yhd_sku";
			}
			else if("higo".equals(platform))
			{
				field = "higo_sku";
			}
			else if("tmall".equals(platform))
			{
				field = "tmall_sku";
			}
			else
			{
				//siku 不能这样处理
				continue;
			}
			if(StringUtil.isNotBlank(sku) && sku.length() == 13)
			{
				updateStockUpdateSku2Null(sku, type, field);
			}
		}
	}
}
 
