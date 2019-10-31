package com.huaixuan.network.biz.service.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.api.ApiProductDao;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.api.ApiProductService;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2015-12-4 上午11:34:47
 **/

@Service("apiProductService")
public class ApiProductServiceImpl implements ApiProductService
{
	
	@Autowired
	private ApiProductDao apiProductDao;
	
	@Override
	public MiniUiGrid getProductListByPages(Map<String, String> searchMap)
	{
		MiniUiGrid gird = new MiniUiGrid();
		
		//处理是查询香港还是上海可售库存
		String requestJson = searchMap.get("request"); //前面验证过参数都存在
		int type = -1; //类型
		int pageIndex= 1;
		int pageSize = 50;//默认每页50条
		try
		{
			JSONObject jsonObj = new JSONObject(requestJson);
			type = jsonObj.getInt("type");
			pageIndex = jsonObj.getInt("pageIndex");
			pageSize = jsonObj.getInt("pageSize");
			if(pageSize > 100) pageSize = 100;//每页最多100条
		}
		catch (JSONException e)
		{
			//之前验证过 按道理不会出现异常  action验证了type只能为0 或者1
		}
		if(-1 == type) return gird;
		
		String idLocation = "('101','209')"; //上海查询尚上总仓和紫安仓
		if(1 == type) //查询香港库存 0-上海  1-香港
		{
			idLocation = "('103','104')"; //香港只查询帝国中心
		}
		searchMap.put("idLocation", idLocation);
		
		//查询 -根据站点ID
		QueryPage queryPage = new QueryPage(new Object());
		int count = apiProductDao.getAvailableProductsByPagesCount(searchMap);
		
		gird.setTotal(count);
		
		if(count >0)
		{
			queryPage.setCurrentPage(pageIndex);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageLastItem()+"");
			
			List<StockData> list = apiProductDao.getAvailableProductsByPages(searchMap);
			
			if(list != null && list.size() >0)
			{
				//获取欧洲价  有些sku有多个euprice所以单独列出来获取 
				//根据sku获取欧洲价 默认获取最后一个
				List<String> skusList = new ArrayList<String>();
				for(StockData s : list)
				{
					skusList.add(s.getSku());
				}
				Map<String,Product> priceMap = apiProductDao.getEuPriceBySkus(skusList); //根据SKU获取欧洲价
				
				for(StockData s : list)
				{
					s.setEuPrice(priceMap.get(s.getSku()).getEuPrice());
					s.setHkPrice(priceMap.get(s.getSku()).getSmPrice());
					s.setShPrice(priceMap.get(s.getSku()).getSsPrice());
				}
				
				gird.setData(list);
			}
			
		}
		return gird;
	}
	

}
 
