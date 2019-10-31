package com.huaixuan.network.biz.dao.statistics.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.statistics.AnalysisDao;
import com.huaixuan.network.biz.domain.StoreDay;
import com.huaixuan.network.biz.domain.report.FCSet;
import com.huaixuan.network.biz.domain.statistics.CatalogOrderAnalysis;
import com.huaixuan.network.biz.domain.statistics.GoodsAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.Page;

@Repository("analysisDao")
public class AnalysisDaoIbatis implements AnalysisDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<FCSet> getSingleOrderAnalysis(Map parMap) {
		List<FCSet> pp = this.sqlMapClientTemplate.queryForList("getSingleOrderAnalysis", parMap);
		Map<String,String> enumTradeStatusMap = EnumTradeStatus.toMap();
		for(FCSet fcset :pp){
			fcset.setLabel(enumTradeStatusMap.get(fcset.getLabel()));
		}
		return pp;
	}

	@Override
	public Map getCatalogAnalysis(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getCatalogAnalysis", parMap, "saleNum", "salePrice");
	}

	@Override
	public int getCatalogOrderAnalysisCount(Map parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getCatalogOrderAnalysisCount", parMap);
	}

	@Override
	public int getSaleAnalysisCount(Map parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getSaleAnalysisTradeOutPriceCount", parMap);
	}

	@Override
	public int getHotSalsGoodsCount(Map parMap) {
		Integer i = (Integer) this.sqlMapClientTemplate.queryForObject("getHotSalesGoodsCount", parMap);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	@Override
	public Map getHotSalesGoodsSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getHotSalesGoodsSum", parMap, "goodsId");
	}

	@Override
	public Map getSaleAnalysisTradeOutPriceSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getSaleAnalysisTradeOutPriceSum", parMap, "goodsId");
	}

	@Override
	public Map getSaleAnalysisDetailSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getSaleAnalysisDetailSum", parMap, "goodsId");
	}

	@Override
	public int getSlowSalsGoodsCount(Map parMap) {
		Integer i = (Integer) this.sqlMapClientTemplate.queryForObject("getSlowSalesGoodsCount", parMap);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	@Override
	public Map getSlowSalesGoodsSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getSlowSalesGoodsSum", parMap, "goodsId");
	}

	@Override
	public int getCatSalesGoodsCount(Map parMap) {
		Integer i = (Integer) this.sqlMapClientTemplate.queryForObject("getCatSalesGoodsCount", parMap);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	@Override
	public Map getCatSalesGoodsSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getCatSalesGoodsSum", parMap, "goodsId");
	}

	@Override
	public QueryPage getAnalysisRefundGoods(Map<String, Object> parMap, Page page) {
		QueryPage r = new QueryPage(parMap);
		if (page != null) {
			r.setCurrentPage(page.getCurrentPage());
			r.setPageSize(page.getPageSize());

			int count = (Integer) sqlMapClientTemplate.queryForObject("getAnalysisRefundGoodsCount", parMap);
			r.setTotalItem(count);

			if (count > 0) {
				parMap.put("startRow", r.getPageFristItem());
				parMap.put("endRow", r.getPageLastItem());

				/* 分页查询操作员记录 */
				r.setItems(sqlMapClientTemplate.queryForList("getAnalysisRefundGoods", parMap));
			}
		} else {
			r.setItems(sqlMapClientTemplate.queryForList("getAnalysisRefundGoods", parMap));
		}

		return r;
	}

	@Override
	public int getAnalysisRefundGoodsCount(Map parMap) {
		Integer i = (Integer) this.sqlMapClientTemplate.queryForObject("getAnalysisRefundGoodsCount", parMap);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	@Override
	public int getSaleAnalysisDetailCount(Map parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getSaleAnalysisTradeDetailCount", parMap);
	}

	@Override
	public int getGoodsInStorageCount(Map parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getGoodsInStorageCount", parMap);
	}

	@Override
	public Map getRefundAnalysisDetailSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getRefundAnalysisDetailSum", parMap, "goodsId");
	}

	@Override
	public int getRefundAnalysisDetailCount(Map parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getRefundAnalysisDetailCount", parMap);
	}

	@Override
	public int getRefundAnalysisCount(Map parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getRefundAnalysisCount", parMap);
	}

	@Override
	public Map getRefundAnalysisTradeOutPriceSum(Map parMap) {
		return (Map) this.sqlMapClientTemplate.queryForMap("getRefundAnalysisTradeOutPriceSum", parMap, "goodsId");
	}

	@Override
	public List<CatalogOrderAnalysis> getCatalogOrderAnalysis(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getCatalogOrderAnalysis", parMap);
	}

	@Override
	public List<SaleAnalysisTwo> getSaleAnalysis(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getSaleAnalysisTradeOutPrice", parMap);
	}

	@Override
	public List<SaleAnalysisTwo> getRefundAnalysis(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getRefundAnalysisTradeOutPrice", parMap);
	}

	@Override
	public List<SaleAnalysis> getSaleAnalysisDetail(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getSaleAnalysisTradeDetail", parMap);
	}

	@Override
	public List<SaleAnalysis> getRefundAnalysisDetail(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getRefundAnalysisDetail", parMap);
	}

	@Override
	public List<GoodsAnalysis> getHotSalsGoods(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getHotSalesGoods", parMap);
	}

	@Override
	public List<GoodsAnalysis> getSlowSalsGoods(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getSlowSalesGoods", parMap);
	}

	@Override
	public List<GoodsAnalysis> getCatSalesGoods(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getCatSalesGoods", parMap);
	}

	@Override
	public List<SaleAnalysis> getGoodsInStorage(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getGoodsInStorage", parMap);
	}

	@Override
    public int getSaleAnalysisDepositoryCount(Map parMap) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "getSaleAnalysisDepositoryCount", parMap);
    }
    
	@Override
    public int getRefundAnalysisDepositoryCount(Map parMap) {
        return (Integer)this.sqlMapClientTemplate.queryForObject("getRefundAnalysisDepositoryCount", parMap);
    }
    
	@Override
    public Map getSaleAnalysisDepositoryTradeOutPriceSum(Map parMap) {
        return (Map) this.sqlMapClientTemplate.queryForMap("getSaleAnalysisDepositoryTradeOutPriceSum",
            parMap, "goodsId");
    }
    
	@Override
    public List<SaleAnalysisTwo> getSaleAnalysisDepository(Map parMap) {

        return this.sqlMapClientTemplate.queryForList("getSaleAnalysisDepository",parMap);
    }
    
	@Override
    public Map getRefundAnalysisDepositoryTradeOutPriceSum(Map parMap) {
        return (Map) this.sqlMapClientTemplate.queryForMap("getRefundAnalysisDepositoryTradeOutPriceSum", parMap, "goodsId");
    }
    
	@Override
    public List<SaleAnalysisTwo> getRefundAnalysisDepository(Map parMap) {
        return this.sqlMapClientTemplate.queryForList("getRefundAnalysisDepository",parMap);
    }

	@Override
	public StoreDay getStoreDay(Map parMap) throws Exception {
		return (StoreDay)this.sqlMapClientTemplate.queryForObject("getStoreDay", parMap);
	}
}
