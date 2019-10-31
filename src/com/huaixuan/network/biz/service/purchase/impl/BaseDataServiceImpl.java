package com.huaixuan.network.biz.service.purchase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.purchase.BaseDataDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.purchase.BaseData;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.purchase.BaseDataService;

/**
 *2012-7-5 ����04:48:38
 *Mr_Yang
 */
@Service("baseDataService")
public class BaseDataServiceImpl implements BaseDataService
{

	@Autowired
	private BaseDataDao baseDataDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getBaseDataByMap(BaseData baseData,int currPage,int pageSize)
	{
		QueryPage query = new QueryPage(baseData);
		
		Map parMap = query.getParameters();
		Integer count = baseDataDao.getBaseDataTmpByMapCount(parMap);
		List<BaseData> result = new ArrayList<BaseData>();
		if(count != null && count.intValue() > 0)
		{
			query.setCurrentPage(currPage);
			query.setPageSize(pageSize);
			query.setTotalItem(count);
			parMap.put("startRow", query.getPageFristItem());
			parMap.put("endRow", query.getPageLastItem());
			List<BaseData> list = baseDataDao.getBaseDataTmpByMap(parMap);
			for(BaseData b : list)
			{
				QueryPage queryPage = new QueryPage(b);
				Map map = queryPage.getParameters();
				BaseData base = baseDataDao.getBaseDataByMap(map);
				if(base != null)
					result.add(base);
			}
		}		
		query.setItems(result);
		return query;
	}

	@Override
	public List<BaseData> getBaseDataTmpByMap(BaseData baseData)
	{
		QueryPage query = new QueryPage(baseData);
		Map parMap = query.getParameters();
		return baseDataDao.getBaseDataTmpByMap(parMap);
		
	}

	@Override
	public Map getNotSameEUPrice(Long goodsId)
	{
		return baseDataDao.getNotSameEUPrice(goodsId);
	}

	@Override
	public Map getNotSameHKPrice(Long goodsId)
	{
		Map map = baseDataDao.getNotSameHKPrice(goodsId);
		return map;
	}

	/**
	 * ����ṩ��������ѯ����¼
	 * @param idBrand Ʒ��ID
	 * @param type 1.ͨ���ͺŲ�ѯ 2.ͨ����ʲ�ѯ 3.ͨ����ɫ��ѯ
	 * @param goodsId 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getInStorageInfoBy(String type,Long goodsId,int currPage,int pageSize)
	{
		Goods goods = goodsDao.getGoods(goodsId);
		if(goods != null)
		{
			Map parMap = new HashMap();	
			parMap.put("idBrand", goods.getBrandId());
			if("1".equals(type))
				parMap.put("type", goods.getType());
			else if("2".equals(type))
				parMap.put("material", goods.getMaterial());
			else if("3".equals(type))
				parMap.put("color", goods.getColor());
		    Integer count = baseDataDao.getInStorageInfoByMapCount(parMap);
		    if(count != null && count.intValue() > 0)
		    {
		    	QueryPage queryPage = new QueryPage(null);
		    	queryPage.setCurrentPage(currPage);
		    	queryPage.setPageSize(pageSize);
		    	parMap.put("startRow", queryPage.getPageFristItem());
		    	parMap.put("endRow", queryPage.getPageLastItem());
		    	List<BaseData> list = baseDataDao.getInStorageInfoByMap(parMap);
		    	queryPage.setItems(list);
		    	return queryPage;
		    }
		}
		return null;
	}

}
