package com.huaixuan.network.biz.service.statistics.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.dao.statistics.AnalysisDao;
import com.huaixuan.network.biz.dao.storage.InOutStatReportDao;
import com.huaixuan.network.biz.domain.StoreDay;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.report.FCCateGories;
import com.huaixuan.network.biz.domain.report.FCCateGory;
import com.huaixuan.network.biz.domain.report.FCDataSet;
import com.huaixuan.network.biz.domain.report.FCSet;
import com.huaixuan.network.biz.domain.report.FusionChartsPojo;
import com.huaixuan.network.biz.domain.report.FusionChartsUtil;
import com.huaixuan.network.biz.domain.report.MultiFusionChartsPojo;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.statistics.CatalogOrderAnalysis;
import com.huaixuan.network.biz.domain.statistics.GoodsAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InOutStatReport;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.statistics.AnalysisManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.Page;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("analysisManager")
public class AnalysisManagerImpl implements AnalysisManager {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	AnalysisDao analysisDao;

	@Autowired
	GoodsDao goodsDao;

	@Autowired
	GoodsInstanceDao goodsInstanceDao;

	@Autowired
	CategoryManager categoryManager;

	@Autowired
	DepLocationManager depLocationManager;

	@Autowired
	DepositoryFirstManager depositoryFirstManager;

	@Autowired
	BrandDao brandDao;

	@Autowired
	InOutStatReportDao inOutStatReportDao;

	public String getMultiOrderAnalysis(Map parMap) {
        String dataString = "";
        try {
            MultiFusionChartsPojo multiFusionChartsPojo = new MultiFusionChartsPojo();
            //
            FCCateGories fcCateGories = new FCCateGories();
            List<FCCateGory> fcCateGoryList = new ArrayList<FCCateGory>();
            fcCateGoryList.add(new FCCateGory(EnumTradeStatus.WAIT_BUYER_PAY.getValue()));
            fcCateGoryList.add(new FCCateGory(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getValue()));
            fcCateGoryList.add(new FCCateGory(EnumTradeStatus.WAIT_DISTRIBUTION.getValue()));
            fcCateGoryList.add(new FCCateGory(EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getValue()));
            fcCateGoryList.add(new FCCateGory(EnumTradeStatus.TRADE_FINISH.getValue()));
            fcCateGoryList.add(new FCCateGory(EnumTradeStatus.TRADE_CLOSE.getValue()));
            fcCateGories.setFcCateGoryList(fcCateGoryList);
            // 
            multiFusionChartsPojo.setFcCateGories(fcCateGories);
            //
            List<FCDataSet> fcDataSetList = new ArrayList<FCDataSet>();
            List<String> yearMonthList = new ArrayList<String>();
            // yearMonth 
            if (parMap.get("yearMonth1") != null
                && parMap.get("yearMonth1").toString().trim().length() > 0) {
                String yearMonth = parMap.get("yearMonth1").toString().trim() + "-01";
                yearMonthList.add(yearMonth);
            }
            if (parMap.get("yearMonth2") != null
                && parMap.get("yearMonth2").toString().trim().length() > 0) {
                String yearMonth = parMap.get("yearMonth2").toString().trim() + "-01";
                yearMonthList.add(yearMonth);
            }
            if (parMap.get("yearMonth3") != null
                && parMap.get("yearMonth3").toString().trim().length() > 0) {
                String yearMonth = parMap.get("yearMonth3").toString().trim() + "-01";
                yearMonthList.add(yearMonth);
            }
            if (parMap.get("yearMonth4") != null
                && parMap.get("yearMonth4").toString().trim().length() > 0) {
                String yearMonth = parMap.get("yearMonth4").toString().trim() + "-01";
                yearMonthList.add(yearMonth);
            }
            if (parMap.get("yearMonth5") != null
                && parMap.get("yearMonth5").toString().trim().length() > 0) {
                String yearMonth = parMap.get("yearMonth5").toString().trim() + "-01";
                yearMonthList.add(yearMonth);
            }
            if (yearMonthList.size() > 0) {
                for (String yearMonth : yearMonthList) {
                    FCDataSet fcCDataSet = new FCDataSet();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("yearMonth", yearMonth);
                    List<FCSet> fcSetList = analysisDao.getSingleOrderAnalysis(map);
                    // 
                    List<FCSet> fcSetListData = new ArrayList<FCSet>();
                    for (FCCateGory fcCateGory : fcCateGoryList) {
                        boolean isOK = false;
                        if (fcSetList != null && fcSetList.size() > 0) {
                            for (FCSet set : fcSetList) {
                                if (!isOK
                                    && set.getLabel().trim().equals(fcCateGory.getLabel().trim())) {
                                    fcSetListData.add(set);
                                    isOK = true;
                                    break;
                                }
                            }
                        }
                        if (!isOK) {
                            FCSet fcSet = new FCSet();
                            fcSet.setLabel(fcCateGory.getLabel().trim());
                            fcSet.setValue("0");
                            fcSetListData.add(fcSet);
                        }
                    }
                    fcCDataSet.setFcSetList(fcSetListData);
                    fcCDataSet.setSeriesName(yearMonth.substring(0, yearMonth.lastIndexOf("-"))
                                             + "月份");
                    fcDataSetList.add(fcCDataSet);
                }
            }
            // 
            multiFusionChartsPojo.setFcDataSetList(fcDataSetList);
            // 

            multiFusionChartsPojo.setCaption("【订单概况比较】图形报表");//
            // multiFusionChartsPojo
            // .setSubCaption(parMap.get("dateStart") + " - " +
            // parMap.get("dateEnd"));//
            multiFusionChartsPojo.setPalette("2");// palette
            multiFusionChartsPojo.setUseRoundEdges("1");// useRoundEdges
            multiFusionChartsPojo.setBaseFontSize("12");// 
            multiFusionChartsPojo.setYAxisName("交易数量");
            multiFusionChartsPojo.setXAxisName("交易状态");
            // multiFusionChartsPojo.setNumberPrefix("");//
            Map<String, String> otherPropertyMap = new HashMap<String, String>();
            otherPropertyMap.put("numberSuffix", "个");
            // numberSuffix //
            multiFusionChartsPojo.setOtherPropertyMap(otherPropertyMap);
            boolean is2Html = true;

            String flashName = parMap.get("flashName") == null ? "/fusionCharts/swf/MSColumn2D.swf"
                : parMap.get("flashName").toString();
            String chartWidth = parMap.get("chartWidth") == null ? "400" : parMap.get("chartWidth")
                .toString();
            String chartHeight = parMap.get("chartHeight") == null ? "300" : parMap.get(
                "chartHeight").toString();
            if (is2Html) {
                dataString = FusionChartsUtil.createChartHTML(flashName, null,
                    multiFusionChartsPojo.toXMLString(), "chartId_"
                                                         + RandomStringUtils.randomAlphabetic(6),
                    Integer.parseInt(chartWidth), Integer.parseInt(chartHeight), false);
            } else {
                dataString = FusionChartsUtil.createChart(flashName, null, multiFusionChartsPojo
                    .toXMLString(), "chartId_" + RandomStringUtils.randomAlphabetic(6), Integer
                    .parseInt(chartWidth), Integer.parseInt(chartHeight), false, false);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return dataString;
	}

	public String getSingleOrderAnalysis(Map parMap) {
		String dataString = "";
		try {
			FusionChartsPojo fusionChartsPojo = new FusionChartsPojo();
			List<FCSet> fcSetList = analysisDao.getSingleOrderAnalysis(parMap);
			fusionChartsPojo.setFcSetList(fcSetList);

			fusionChartsPojo.setCaption("【订单概况】图形报表");// 
			fusionChartsPojo.setSubCaption((parMap.get("dateStart") != null?parMap.get("dateStart"):"") + " - " + (parMap.get("dateEnd") != null?parMap.get("dateEnd"):""));//
			fusionChartsPojo.setPalette("2");// palette
			fusionChartsPojo.setUseRoundEdges("1");// useRoundEdges
			fusionChartsPojo.setBaseFontSize("12");// 
			fusionChartsPojo.setYAxisName("交易数量");
			fusionChartsPojo.setXAxisName("交易状态");
			// multiFusionChartsPojo.setNumberPrefix("");//
			Map<String, String> otherPropertyMap = new HashMap<String, String>();
			otherPropertyMap.put("numberSuffix", "个");
			// numberSuffix //
			fusionChartsPojo.setOtherPropertyMap(otherPropertyMap);

			if (null == fusionChartsPojo) {
				return "查询无结果.";
			}
			boolean is2Html = true;

			String flashName = parMap.get("flashName") == null ? "/fusionCharts/swf/Pie2D.swf" : parMap
					.get("flashName").toString();
			String chartWidth = parMap.get("chartWidth") == null ? "400" : parMap.get("chartWidth").toString();
			String chartHeight = parMap.get("chartHeight") == null ? "300" : parMap.get("chartHeight").toString();
			if (is2Html) {
				dataString = FusionChartsUtil.createChartHTML(flashName, null, fusionChartsPojo.toXMLString(),
						"chartId_" + RandomStringUtils.randomAlphabetic(6), Integer.parseInt(chartWidth),
						Integer.parseInt(chartHeight), false);
			} else {
				dataString = FusionChartsUtil.createChart(flashName, null, fusionChartsPojo.toXMLString(), "chartId_"
						+ RandomStringUtils.randomAlphabetic(6), Integer.parseInt(chartWidth),
						Integer.parseInt(chartHeight), false, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return dataString;
	}

	/**
	 * 查询一级目录商品销售统计报表
	 * 
	 * @author liuwd
	 * @param parMap
	 * @return
	 */
	public String getCatalogAnalysis(Map parMap) {
		String dataString = "";
		try {
			FusionChartsPojo fusionChartsPojo = new FusionChartsPojo();
			List<FCSet> fcSetList = new ArrayList<FCSet>();
			FCSet set = null;
			// 读取一级目录list
			List<Category> catList = categoryManager.getCatInfoByDepth(1);
			// 循环统计每个目录的销售量和销售金额
			for (Category cat : catList) {
				parMap.put("catCode", cat.getCatCode());
				Map map = analysisDao.getCatalogAnalysis(parMap);// 结果集：Map(key:销售数量，value:销售金额)
				if (map != null && map.size() > 0) {
					Object saleNum = map.keySet().iterator().next();
					Object salePrice = map.get(saleNum);
					set = new FCSet();
					set.setLabel(cat.getCatName() + "(" + saleNum + ")");
					set.setValue(salePrice.toString());
					fcSetList.add(set);
				}
			}

			fusionChartsPojo.setFcSetList(fcSetList);
			fusionChartsPojo.setCaption("【一级目录销售金额概况】图形报表");// 标题
			fusionChartsPojo.setSubCaption((parMap.get("dateStart") != null?parMap.get("dateStart"):"") + " - " + (parMap.get("dateEnd") != null?parMap.get("dateEnd"):""));// 副标题
			fusionChartsPojo.setPalette("2");// palette
			fusionChartsPojo.setUseRoundEdges("1");// useRoundEdges
			fusionChartsPojo.setBaseFontSize("12");//
			fusionChartsPojo.setFormatNumberScale("2");
			Map<String, String> otherPropertyMap = new HashMap<String, String>();
			otherPropertyMap.put("numberPrefix", "￥");// 数值前缀

			fusionChartsPojo.setOtherPropertyMap(otherPropertyMap);

			if (null == fusionChartsPojo) {
				return "查询无结果.";
			}
			boolean is2Html = true;

			String flashName = parMap.get("flashName") == null ? "/fusionCharts/swf/Pie2D.swf" : parMap
					.get("flashName").toString();
			String chartWidth = parMap.get("chartWidth") == null ? "400" : parMap.get("chartWidth").toString();
			String chartHeight = parMap.get("chartHeight") == null ? "300" : parMap.get("chartHeight").toString();
			if (is2Html) {
				dataString = FusionChartsUtil.createChartHTML(flashName, null, fusionChartsPojo.toXMLString(),
						"chartId_" + RandomStringUtils.randomAlphabetic(6), Integer.parseInt(chartWidth),
						Integer.parseInt(chartHeight), false);
			} else {
				dataString = FusionChartsUtil.createChart(flashName, null, fusionChartsPojo.toXMLString(), "chartId_"
						+ RandomStringUtils.randomAlphabetic(6), Integer.parseInt(chartWidth),
						Integer.parseInt(chartHeight), false, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return dataString;
	}

	public AnalysisDao getAnalysisDao() {
		return analysisDao;
	}

	public void setAnalysisDao(AnalysisDao analysisDao) {
		this.analysisDao = analysisDao;
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setGoodsInstanceDao(GoodsInstanceDao goodsInstanceDao) {
		this.goodsInstanceDao = goodsInstanceDao;
	}

	public int getSaleAnalysisCount(Map parMap) {
		int total = 0;
		try {
			if (parMap != null) {
				String title = (String) parMap.get("title");
				String catId = (String) parMap.get("catId");
				String goodsSn = (String) parMap.get("goodsSn");
				parMap.put("catCode", catId);
				parMap.put("goodsSn", goodsSn);
				if (StringUtil.isNotBlank(title) || StringUtil.isNotBlank(catId) || StringUtil.isNotBlank(goodsSn))
					parMap.put("goodsIds", "goodsIds");
				total = analysisDao.getSaleAnalysisCount(parMap);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return total;
	}

	public Map getSaleAnalysisTradeOutPriceSum(Map parMap) {
		Map sumMap = new HashMap();
		try {
			sumMap = analysisDao.getSaleAnalysisTradeOutPriceSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sumMap;
	}

	/**
	 * @Title: getCatalogOrderAnalysis
	 * @Description: 类目订单统计
	 * @param parMap
	 * @param page
	 * @return Map
	 * @throws
	 */
	// public List<CatalogOrderAnalysis> getCatalogOrderAnalysis(Map parMap, Page page) {
	// List<CatalogOrderAnalysis> sales = new ArrayList();
	// try {
	// if (parMap != null) {
	//
	// sales = analysisDao.getCatalogOrderAnalysis(parMap);
	// for (CatalogOrderAnalysis s : sales) {
	// s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(s.getCatCode(), ">"));
	// }
	//
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return sales;
	// }

	public QueryPage getCatalogOrderAnalysis(Map parMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);
		// Map<String, String> pramas = queryPage.getParameters();

		int count = analysisDao.getCatalogOrderAnalysisCount(parMap);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<CatalogOrderAnalysis> sales = analysisDao.getCatalogOrderAnalysis(parMap);
			for (CatalogOrderAnalysis s : sales) {
				try {
					s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(s.getCatCode(), ">"));
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			if (sales != null && sales.size() > 0) {
				queryPage.setItems(sales);
			}
		}
		return queryPage;
	}

	public Map getSaleAnalysisDetailSum(Map parMap) {
		Map sumMap = new HashMap();
		try {
			sumMap = analysisDao.getSaleAnalysisDetailSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sumMap;
	}

	public List<SaleAnalysisTwo> getSaleAnalysis(Map parMap) {
        List<SaleAnalysisTwo> sales = new ArrayList();
        try {
            if (parMap != null) {

                sales = analysisDao.getSaleAnalysis(parMap);
                // 添加产品销售统计（modify by fanyj 2010-12-3）TODO
                if("products".equals(parMap.get("actionType"))){
                	for (SaleAnalysisTwo s : sales) {
                		GoodsInstance goodsInstance = (GoodsInstance) goodsInstanceDao.getInstance(s.getGoodsId());
                        if (goodsInstance != null) {
                            s.setTitle(goodsInstance.getInstanceName());
                            s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goodsInstance.getCatCode(), ">"));
                            s.setAttrs(goodsInstance.getAttrs());
                            s.setUnit(goodsInstance.getGoodsUnit());
                            s.setGoodsSn(goodsInstance.getCode());
                            s.setGoodsNumber((long)goodsInstance.getExistNum());
                        }
                    }
                }else{
                	for (SaleAnalysisTwo s : sales) {
                        Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
                        if (goods != null) {
                            s.setTitle(goods.getTitle());
                            s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods
                                .getCatCode(), ">"));
                            s.setAttrs(goods.getAttrValue());
                            s.setUnit(goods.getGoodsUnit());
                            s.setGoodsSn(goods.getGoodsSn());
                            s.setGoodsNumber((long)goods.getGoodsNumber());
                        }
                    }
                }  
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return sales;
	}

	public int getSaleAnalysisDetailCount(Map parMap) {
		int total = 0;
		try {
			if (parMap != null) {
				String title = (String) parMap.get("title");
				String catId = (String) parMap.get("catId");
				String goodsSn = (String) parMap.get("goodsSn");
				parMap.put("catCode", catId);
				parMap.put("goodsSn", goodsSn);
				if (StringUtil.isNotBlank(title) || StringUtil.isNotBlank(catId) || StringUtil.isNotBlank(goodsSn))
					parMap.put("goodsIds", "goodsIds");
				total = analysisDao.getSaleAnalysisDetailCount(parMap);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return total;
	}

    public List<SaleAnalysis> getSaleAnalysisDetail(Map parMap) {
        List<SaleAnalysis> sales = new ArrayList();
        try {
            if (parMap != null) {

                sales = analysisDao.getSaleAnalysisDetail(parMap);
                for (SaleAnalysis s : sales) {
                    Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
                    if (goods != null) {
                        s.setTitle(goods.getTitle());
                        s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods
                            .getCatCode(), ">"));
                        s.setAttrs(goods.getAttrValue());
                        s.setUnit(goods.getGoodsUnit());
                        s.setGoodsSn(goods.getGoodsSn());
                    }
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return sales;
    }

	// public List<GoodsAnalysis> getHotSalesGoods(Map parMap, Page page) {
	// List<GoodsAnalysis> gas = new ArrayList();
	// try {
	// gas = analysisDao.getHotSalsGoods(parMap, page);
	//
	// for (GoodsAnalysis ga : gas) {
	// Goods g = goodsDao.getGoods(ga.getGoodsId());
	// if (g != null) {
	// ga.setGoodsName(g.getTitle());
	// ga.setCatName(categoryManager.getCatFullNameByCatcodeSimple(g.getCatCode(), ">"));
	// ga.setCatCode(g.getCatCode());
	// ga.setGoodsSn(g.getGoodsSn());
	// // ga.setAttrValue(attributeManager
	// // .getFullAttributeStringByAttrs(g.getAttrValue()));
	// ga.setAttrValue(g.getAttrValue());
	// ga.setUnit(g.getGoodsUnit());
	// Brand brand = brandDao.getBrand(g.getBrandId());
	// if (brand != null) {
	// ga.setBrandName(brand.getBrandName());
	// }
	// }
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return gas;
	// }

	public int getHotSalesGoodsCount(Map parMap) {
		try {
			return analysisDao.getHotSalsGoodsCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public Map getHotSalesGoodsSum(Map parMap) {
		Map sumMap = new HashMap();
		try {
			sumMap = analysisDao.getHotSalesGoodsSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sumMap;
	}

	// public List<GoodsAnalysis> getSlowSalesGoods(Map parMap, Page page) {
	// List<GoodsAnalysis> gas = new ArrayList();
	// try {
	// gas = analysisDao.getSlowSalsGoods(parMap, page);
	// for (GoodsAnalysis ga : gas) {
	// Goods g = goodsDao.getGoods(ga.getGoodsId());
	// if (g != null) {
	// ga.setGoodsName(g.getTitle());
	// ga.setCatName(categoryManager.getCatFullNameByCatcodeSimple(g.getCatCode(), ">"));
	// ga.setCatCode(g.getCatCode());
	// ga.setGoodsSn(g.getGoodsSn());
	// // ga.setAttrValue(attributeManager
	// // .getFullAttributeStringByAttrs(g.getAttrValue()));
	// ga.setAttrValue(g.getAttrValue());
	// ga.setUnit(g.getGoodsUnit());
	// }
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return gas;
	// }

	public int getSlowSalesGoodsCount(Map parMap) {
		try {
			return analysisDao.getSlowSalsGoodsCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public Map getSlowSalesGoodsSum(Map parMap) {
		Map sumMap = new HashMap();
		try {
			sumMap = analysisDao.getSlowSalesGoodsSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sumMap;
	}

	public int getCatSalesGoodsCount(Map parMap) {
		try {
			return analysisDao.getCatSalesGoodsCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public Map getCatSalesGoodsSum(Map parMap) {
		Map sumMap = new HashMap();
		try {
			sumMap = analysisDao.getCatSalesGoodsSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sumMap;
	}

	public QueryPage getAnalysisRefundGoods(Map parMap, Page page) {
		QueryPage gas = analysisDao.getAnalysisRefundGoods(parMap, page);
		if (gas.getItems() != null) {
			for (GoodsAnalysis ga : (List<GoodsAnalysis>) gas.getItems()) {
				GoodsInstance g = goodsInstanceDao.getInstance(ga.getGoodsId());
				if (g != null) {
					ga.setGoodsName(g.getInstanceName());
					ga.setGoodsSn(g.getCode());
					ga.setCatName(categoryManager.getCatFullNameByCatcodeSimple(g.getCatCode(), ">"));
					ga.setCatCode(g.getCatCode());
					ga.setProperties(g.getProperties());
					ga.setUnit(g.getGoodsUnit());
				}
			}
		}
		return gas;
	}

	public int getAnalysisRefundGoodsCount(Map parMap) {
		try {
			return analysisDao.getAnalysisRefundGoodsCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public int getGoodsInStorageCount(Map parMap) {
		log.info("AnalysisManager.getGoodsInStorageCount method");
		try {
			return this.analysisDao.getGoodsInStorageCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public List<SaleAnalysis> getRefundAnalysisDetail(Map parMap) {
        List<SaleAnalysis> sales = new ArrayList();
        try {
            if (parMap != null) {

                sales = analysisDao.getRefundAnalysisDetail(parMap);
                for (SaleAnalysis s : sales) {
                    Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
                    if (goods != null) {
                        s.setTitle(goods.getTitle());
                        s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods
                            .getCatCode(), ">"));
                        s.setAttrs(goods.getAttrValue());
                        s.setUnit(goods.getGoodsUnit());
                        s.setGoodsSn(goods.getGoodsSn());
                    }
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return sales;
	}

	public int getRefundAnalysisDetailCount(Map parMap) {
		int total = 0;
		try {
			if (parMap != null) {
				String title = (String) parMap.get("title");
				String catId = (String) parMap.get("catId");
				String goodsSn = (String) parMap.get("goodsSn");
				parMap.put("catCode", catId);
				parMap.put("goodsSn", goodsSn);
				if (StringUtil.isNotBlank(title) || StringUtil.isNotBlank(catId) || StringUtil.isNotBlank(goodsSn))
					parMap.put("goodsIds", "goodsIds");
				total = analysisDao.getRefundAnalysisDetailCount(parMap);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return total;
	}

	public Map getRefundAnalysisDetailSum(Map parMap) {
		Map refunMap = new HashMap();
		try {
			refunMap = analysisDao.getRefundAnalysisDetailSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return refunMap;
	}

	public int getRefundAnalysisCount(Map parMap) {
		int total = 0;
		try {
			if (parMap != null) {
				String title = (String) parMap.get("title");
				String catId = (String) parMap.get("catId");
				String goodsSn = (String) parMap.get("goodsSn");
				parMap.put("catCode", catId);
				parMap.put("goodsSn", goodsSn);
				if (StringUtil.isNotBlank(title) || StringUtil.isNotBlank(catId) || StringUtil.isNotBlank(goodsSn))
					parMap.put("goodsIds", "goodsIds");
				total = analysisDao.getRefundAnalysisCount(parMap);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return total;
	}

	// public List<SaleAnalysisTwo> getRefundAnalysis(Map parMap) {
	// List<SaleAnalysisTwo> sales = new ArrayList();
	// try {
	// if (parMap != null) {
	// sales = analysisDao.getRefundAnalysis(parMap);
	// // 添加产品销售统计（modify by fanyj 2010-12-3）
	// if ("products".equals(parMap.get("actionType"))) {
	// for (SaleAnalysisTwo s : sales) {
	// GoodsInstance goodsInstance = (GoodsInstance) goodsInstanceDao
	// .getInstance(s.getGoodsId());
	// if (goodsInstance != null) {
	// s.setTitle(goodsInstance.getInstanceName());
	// s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(
	// goodsInstance.getCatCode(), ">"));
	// s.setAttrs(goodsInstance.getAttrs());
	// s.setUnit(goodsInstance.getGoodsUnit());
	// s.setGoodsSn(goodsInstance.getCode());
	// }
	// }
	// } else {
	// for (SaleAnalysisTwo s : sales) {
	// Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
	// if (goods != null) {
	// s.setTitle(goods.getTitle());
	// s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(
	// goods.getCatCode(), ">"));
	// s.setAttrs(goods.getAttrValue());
	// s.setUnit(goods.getGoodsUnit());
	// s.setGoodsSn(goods.getGoodsSn());
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	//
	// return sales;
	// }

	public Map getRefundAnalysisTradeOutPriceSum(Map parMap) {
		Map refundMap = new HashMap();
		try {
			refundMap = analysisDao.getRefundAnalysisTradeOutPriceSum(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return refundMap;
	}

	@Autowired
	private MessageSource messageSource;

	protected String getText(String code, String... args) {
		String message = messageSource.getMessage(code, args, Locale.CHINA);
		return message;
	}

	@Override
	public List<SaleAnalysisTwo> getRefundAnalysis(Map parMap) {
        List<SaleAnalysisTwo> sales = new ArrayList();
        try {
            if (parMap != null) {
                sales = analysisDao.getRefundAnalysis(parMap);
                // 添加产品销售统计（modify by fanyj 2010-12-3）
                if("products".equals(parMap.get("actionType"))){
                	for (SaleAnalysisTwo s : sales) {
                		GoodsInstance goodsInstance = (GoodsInstance) goodsInstanceDao.getInstance(s.getGoodsId());
                		if (goodsInstance != null) {
                            s.setTitle(goodsInstance.getInstanceName());
                            s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goodsInstance.getCatCode(), ">"));
                            s.setAttrs(goodsInstance.getAttrs());
                            s.setUnit(goodsInstance.getGoodsUnit());
                            s.setGoodsSn(goodsInstance.getCode());
                        }
                    }
                }else{
                	for (SaleAnalysisTwo s : sales) {
                        Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
                        if (goods != null) {
                            s.setTitle(goods.getTitle());
                            s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods
                                .getCatCode(), ">"));
                            s.setAttrs(goods.getAttrValue());
                            s.setUnit(goods.getGoodsUnit());
                            s.setGoodsSn(goods.getGoodsSn());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return sales;
	}

	@Override
	public QueryPage getHotSalesGoods(Map parMap, int currPage, int pageSize) {

		QueryPage queryPage = new QueryPage(parMap);

		int count = analysisDao.getHotSalsGoodsCount(parMap);
		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<GoodsAnalysis> gas = analysisDao.getHotSalsGoods(parMap);
			for (GoodsAnalysis ga : gas) {
				Goods g = goodsDao.getGoods(ga.getGoodsId());
				try {
					if (g != null) {
						ga.setGoodsName(g.getTitle());
						ga.setCatName(categoryManager.getCatFullNameByCatcodeSimple(g.getCatCode(), ">"));
						ga.setCatCode(g.getCatCode());
						ga.setGoodsSn(g.getGoodsSn());
						ga.setAttrValue(g.getAttrValue());
						ga.setUnit(g.getGoodsUnit());
						Brand brand = brandDao.getBrand(g.getBrandId());
						if (brand != null) {
							ga.setBrandName(brand.getBrandName());
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			if (gas != null && gas.size() > 0) {
				queryPage.setItems(gas);
			}
		}
		return queryPage;
	}

	@Override
	public QueryPage getSlowSalesGoods(Map parMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);

		int count = analysisDao.getSlowSalsGoodsCount(parMap);
		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<GoodsAnalysis> gas = analysisDao.getSlowSalsGoods(parMap);
			for (GoodsAnalysis ga : gas) {
				Goods g = goodsDao.getGoods(ga.getGoodsId());
				try {
					if (g != null) {
						ga.setGoodsName(g.getTitle());
						ga.setCatName(categoryManager.getCatFullNameByCatcodeSimple(g.getCatCode(), ">"));
						ga.setCatCode(g.getCatCode());
						ga.setGoodsSn(g.getGoodsSn());
						ga.setAttrValue(g.getAttrValue());
						ga.setUnit(g.getGoodsUnit());
						Brand brand = brandDao.getBrand(g.getBrandId());
						if (brand != null) {
							ga.setBrandName(brand.getBrandName());
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			if (gas != null && gas.size() > 0) {
				queryPage.setItems(gas);
			}
		}
		return queryPage;
	}

	public QueryPage getCatSalesGoods(Map parMap, int currPage, int pageSize) {

		QueryPage queryPage = new QueryPage(parMap);

		int count = analysisDao.getCatSalesGoodsCount(parMap);
		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<GoodsAnalysis> gas = analysisDao.getCatSalesGoods(parMap);
			for (GoodsAnalysis ga : gas) {
				try {
					ga.setCatName(categoryManager.getCatFullNameByCatcodeSimple(ga.getCatCode(), ">"));
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			if (gas != null && gas.size() > 0) {
				queryPage.setItems(gas);
			}
		}
		return queryPage;
	}

	@Override
	public QueryPage getGoodsInStorage(Map parMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);

		int count = analysisDao.getGoodsInStorageCount(parMap);
		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<SaleAnalysis> list = analysisDao.getGoodsInStorage(parMap);
			for (SaleAnalysis tmp : list) {
				if (tmp.getDepFirstId() != null) {
					DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(tmp.getDepFirstId());
					if (depositoryFirst != null) {
						tmp.setDepFirstName(depositoryFirst.getDepFirstName());
					}
				}
				if (tmp.getLocId() != null) {
					Depository depository = depLocationManager.getDepositoryByLocationId(tmp.getLocId());
					if (depository != null) {
						tmp.setDepositoryName(depository.getName());
					}
				}
			}
			if (list != null && list.size() > 0) {
				queryPage.setItems(list);
			}
		}
		return queryPage;
	}

	@Override
	public QueryPage getInOutStatReportListByMap(Map parMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);
		parMap = getNewParMap(parMap);
		int count = inOutStatReportDao.getInOutStatReportCountByMap(parMap);
		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(count);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<InOutStatReport> inOutStatReportList = inOutStatReportDao.getInOutStatReportListByMap(parMap);
			List<InOutStatReport> newStatReportList = new ArrayList<InOutStatReport>();
			if (inOutStatReportList != null && inOutStatReportList.size() > 0) {
				for (InOutStatReport inOutStatReport : inOutStatReportList) {
					if (inOutStatReport.getLastRemainAmount() == 0 && inOutStatReport.getLastRemainMoney() == 0
							&& inOutStatReport.getThisInAmount() == 0 && inOutStatReport.getThisInMoney() == 0
							&& inOutStatReport.getThisOutAmount() == 0 && inOutStatReport.getThisOutMoney() == 0
							&& inOutStatReport.getThisRemainAmount() == 0 && inOutStatReport.getThisRemainMoney() == 0) {
						continue;
					} else {
						newStatReportList.add(inOutStatReport);
					}
				}
			}
			if (newStatReportList != null && newStatReportList.size() > 0) {
				queryPage.setItems(newStatReportList);
			}
		}
		return queryPage;
	}

	private Map getNewParMap(Map parMap) {
		String depFirstId = (String) parMap.get("depFirstId");
		String depId = (String) parMap.get("depId");
		String locId = (String) parMap.get("locId");
		if (StringUtil.isNotBlank(locId)) {
			parMap.put("groupByLocId", "y");
		}
		if (StringUtil.isBlank(locId) && StringUtil.isNotBlank(depId)) {
			parMap.put("groupByDepId", "y");
		}
		if (StringUtil.isBlank(depId) && StringUtil.isBlank(locId) && StringUtil.isNotBlank(depFirstId)) {
			parMap.put("groupByDepFirstId", "y");
		}
		return parMap;
	}

    public int getSaleAnalysisDepositoryCount(Map parMap) {
        int total = 0;
        try {
            if (parMap != null) {
                String title = (String) parMap.get("title");
                String catId = (String) parMap.get("catId");
                String goodsSn = (String) parMap.get("goodsSn");
                parMap.put("catCode", catId);
                parMap.put("title", title);
                parMap.put("goodsSn", goodsSn);
                if (StringUtil.isNotBlank(title) || StringUtil.isNotBlank(catId)
                    || StringUtil.isNotBlank(goodsSn))
                    parMap.put("goodsIds", "goodsIds");
                total = analysisDao.getSaleAnalysisDepositoryCount(parMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return total;
    }
    
    public int getRefundAnalysisDepositoryCount(Map parMap) {
        int total = 0;
        try {
            if (parMap != null) {
                String title = (String) parMap.get("title");
                String catId = (String) parMap.get("catId");
                String goodsSn = (String) parMap.get("goodsSn");
                parMap.put("catCode", catId);
                parMap.put("title", title);
                parMap.put("goodsSn", goodsSn);
                if (StringUtil.isNotBlank(title) || StringUtil.isNotBlank(catId)
                    || StringUtil.isNotBlank(goodsSn))
                    parMap.put("goodsIds", "goodsIds");
                total = analysisDao.getRefundAnalysisDepositoryCount(parMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return total;
    }
    
    public Map getSaleAnalysisDepositoryTradeOutPriceSum(Map parMap) {
        Map sumMap = new HashMap();
        try {
            sumMap = analysisDao.getSaleAnalysisDepositoryTradeOutPriceSum(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sumMap;
    }
    
    public List<SaleAnalysisTwo> getSaleAnalysisDepository(Map parMap) {
        List<SaleAnalysisTwo> sales = new ArrayList();
        try {
            if (parMap != null) {
                sales = analysisDao.getSaleAnalysisDepository(parMap);
                for (SaleAnalysisTwo s : sales) {
                    Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
                    if (goods != null) {
                        s.setTitle(goods.getTitle());
                        s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods
                            .getCatCode(), ">"));
                        s.setAttrs(goods.getAttrValue());
                        s.setUnit(goods.getGoodsUnit());
                        s.setGoodsSn(goods.getGoodsSn());
                        s.setGoodsNumber((long)goods.getGoodsNumber());
                    }
                    //根据ID获取一级仓库，仓库，库位的名称
                    if(s.getDepFirstId()!=null){
                        DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(s.getDepFirstId());
                        if(depositoryFirst!=null){
                            s.setDepFirstName(depositoryFirst.getDepFirstName());
                        }
                    }
                    if(s.getLocId()!=null){
                        Depository depository = depLocationManager.getDepositoryByLocationId(s.getLocId());
                        if(depository!=null){
                           s.setDepositoryName(depository.getName());
                           s.setLocName(depository.getLocName());
                        }
                    }
                }  
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return sales;
    }
    
    public Map getRefundAnalysisDepositoryTradeOutPriceSum(Map parMap) {
        Map refundMap = new HashMap();
        try {
            refundMap = analysisDao.getRefundAnalysisDepositoryTradeOutPriceSum(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return refundMap;
    }
    
    public List<SaleAnalysisTwo> getRefundAnalysisDepository(Map parMap) {
        List<SaleAnalysisTwo> sales = new ArrayList();
        try {
            if (parMap != null) {
                sales = analysisDao.getRefundAnalysisDepository(parMap);
                
                for (SaleAnalysisTwo s : sales) {
                    Goods goods = (Goods) goodsDao.getGoods(s.getGoodsId());
                    if (goods != null) {
                        s.setTitle(goods.getTitle());
                        s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods
                            .getCatCode(), ">"));
                        s.setAttrs(goods.getAttrValue());
                        s.setUnit(goods.getGoodsUnit());
                        s.setGoodsSn(goods.getGoodsSn());
                    }
                    //根据ID获取一级仓库，仓库，库位的名称
                    if(s.getDepFirstId()!=null){
                        DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(s.getDepFirstId());
                        if(depositoryFirst!=null){
                            s.setDepFirstName(depositoryFirst.getDepFirstName());
                        }
                    }
                    if(s.getLocId()!=null){
                        Depository depository = depLocationManager.getDepositoryByLocationId(s.getLocId());
                        if(depository!=null){
                           s.setDepositoryName(depository.getName());
                           s.setLocName(depository.getLocName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return sales;
    }
    
    public List<String> getStoreAnalysis(Map parMap) {
        String dataString = "";
        String dataStringSell = "";
        try {
            MultiFusionChartsPojo multiFusionChartsPojo = new MultiFusionChartsPojo();
            
            MultiFusionChartsPojo multiFusionChartsPojoSell = new MultiFusionChartsPojo();
            //
            FCCateGories fcCateGories = new FCCateGories();
            List<FCCateGory> fcCateGoryList = new ArrayList<FCCateGory>();
            fcCateGoryList.add(new FCCateGory("总"));
            fcCateGoryList.add(new FCCateGory("香港库存"));
            
            FCCateGories fcCateGoriesSell = new FCCateGories();
            List<FCCateGory> fcCateGoryListSell = new ArrayList<FCCateGory>();
            fcCateGoryListSell.add(new FCCateGory("总销售额"));
            fcCateGoryListSell.add(new FCCateGory("香港销售额"));
            
            
            fcCateGories.setFcCateGoryList(fcCateGoryList);
            
            fcCateGoriesSell.setFcCateGoryList(fcCateGoryListSell);
            // 
            multiFusionChartsPojo.setFcCateGories(fcCateGories);
            
            multiFusionChartsPojoSell.setFcCateGories(fcCateGoriesSell);
            //
            List<FCDataSet> fcDataSetList = new ArrayList<FCDataSet>();
            
            List<FCDataSet> fcDataSetListSell = new ArrayList<FCDataSet>();
            
            List<String> yearMonthList = new ArrayList<String>();
            // yearMonth 
            if (parMap.get("dateStart") != null
                && parMap.get("dateEnd")!= null) {
            	
            	Date date  = DateUtil.convertStringToDate(DateUtil.dtSimple,parMap.get("dateStart").toString());
            	Date dateend  = DateUtil.convertStringToDate(DateUtil.dtSimple,parMap.get("dateEnd").toString());
            	while(!date.after(dateend)){
            		String yearMonth  = DateUtil.convertDateToString(DateUtil.dtSimple,date);
            		Calendar c = Calendar.getInstance();
                    c.setTime(date);   //设置当前日期
                    c.add(Calendar.DATE, 1); //日期加1
                    date = c.getTime(); //结果
            		yearMonthList.add(yearMonth);
            	}
            	
            }
            if (yearMonthList.size() > 0) {
                for (String yearMonth : yearMonthList) {
                    FCDataSet fcCDataSet = new FCDataSet();
                    
                    FCDataSet fcCDataSetSell = new FCDataSet();
                    
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("datemonthday", yearMonth);
                    StoreDay storeDay = analysisDao.getStoreDay(map);
                    if(storeDay!=null){
	                    List<FCSet> fcSetListData = new ArrayList<FCSet>();
	                    
	                    List<FCSet> fcSetListDataSell = new ArrayList<FCSet>();
	                    
	                    FCSet fcSet = new FCSet();
	            		fcSet.setLabel("总库存RMB");
	            		fcSet.setValue(String.format("%.2f", storeDay.getStoreAmount()/10000));
	            		fcSetListData.add(fcSet);
	            		
	            		FCSet fcSet4 = new FCSet();
	            		fcSet4.setLabel("香港库存RMB");
	            		fcSet4.setValue(String.format("%.2f", storeDay.getHkStoreAmount()/10000));
	            		fcSetListData.add(fcSet4);
	            		
	            		FCSet fcSet5 = new FCSet();
	            		fcSet5.setLabel("总销售额RMB");
	            		fcSet5.setValue(String.format("%.2f", storeDay.getSellAmount()/10000));
	            		fcSetListDataSell.add(fcSet5);
	            		
	            		FCSet fcSet6 = new FCSet();
	            		fcSet6.setLabel("香港销售额RMB");
	            		fcSet6.setValue(String.format("%.2f", storeDay.getHkSellAmount()/10000));
	            		fcSetListDataSell.add(fcSet6);
	            		
	            		fcCDataSet.setFcSetList(fcSetListData);
	            		
	            		fcCDataSetSell.setFcSetList(fcSetListDataSell);
                    }else{
                    	List<FCSet> fcSetListData = new ArrayList<FCSet>();
                    	List<FCSet> fcSetListDataSell = new ArrayList<FCSet>();
                    	
                    	FCSet fcSet = new FCSet();
	            		fcSet.setLabel("总库存RMB");
	            		fcSet.setValue(""+0);
	            		fcSetListData.add(fcSet);
	            		
	            		
	            		FCSet fcSet4 = new FCSet();
	            		fcSet4.setLabel("香港库存RMB");
	            		fcSet4.setValue(""+0);
	            		fcSetListData.add(fcSet4);
	            		
	            		FCSet fcSet5 = new FCSet();
	            		fcSet5.setLabel("总销售额RMB");
	            		fcSet5.setValue(""+0);
	            		fcSetListDataSell.add(fcSet5);
	            		
	            		FCSet fcSet6 = new FCSet();
	            		fcSet6.setLabel("香港销售额RMB");
	            		fcSet6.setValue(""+0);
	            		fcSetListDataSell.add(fcSet6);
	            		
	            		fcCDataSet.setFcSetList(fcSetListData);
	            		fcCDataSetSell.setFcSetList(fcSetListDataSell);
                    }
                    
//                    fcCDataSet.setSeriesName(yearMonth.substring(0, yearMonth.lastIndexOf("-"))
//                                             + "月份");
                    fcCDataSet.setSeriesName(yearMonth);
                    fcCDataSetSell.setSeriesName(yearMonth);
                    fcDataSetList.add(fcCDataSet);
                    
                    fcDataSetListSell.add(fcCDataSetSell);
                    
                }
            }
            // 
            multiFusionChartsPojo.setFcDataSetList(fcDataSetList);
            
            multiFusionChartsPojoSell.setFcDataSetList(fcDataSetListSell);
            // 

            multiFusionChartsPojo.setCaption("【库存】图形报表");//
            
            multiFusionChartsPojoSell.setCaption("【销售额】图形报表");//
            // multiFusionChartsPojo
            // .setSubCaption(parMap.get("dateStart") + " - " +
            // parMap.get("dateEnd"));//
            multiFusionChartsPojo.setPalette("2");// palette
            multiFusionChartsPojo.setUseRoundEdges("1");// useRoundEdges
            multiFusionChartsPojo.setBaseFontSize("12");// 
            multiFusionChartsPojo.setYAxisName(" WAN RMB ");
            multiFusionChartsPojo.setXAxisName("地区 日期");
            // multiFusionChartsPojo.setNumberPrefix("");//
            Map<String, String> otherPropertyMap = new HashMap<String, String>();
            
            multiFusionChartsPojo.setShownames("1");
            multiFusionChartsPojo.setShowSum("1");
            multiFusionChartsPojo.setShowvalues("0");
            
            otherPropertyMap.put("numberSuffix", "万");
            
            
            multiFusionChartsPojoSell.setPalette("2");// palette
            multiFusionChartsPojoSell.setUseRoundEdges("1");// useRoundEdges
            multiFusionChartsPojoSell.setBaseFontSize("12");// 
            multiFusionChartsPojoSell.setYAxisName(" WAN RMB ");
            multiFusionChartsPojoSell.setXAxisName("地区 日期");
            // multiFusionChartsPojo.setNumberPrefix("");//
            
            multiFusionChartsPojoSell.setShownames("1");
            multiFusionChartsPojoSell.setShowSum("1");
            multiFusionChartsPojoSell.setShowvalues("0");
            
            
            
            // numberSuffix //
//            multiFusionChartsPojo.setOtherPropertyMap(otherPropertyMap);
            boolean is2Html = true;

            String flashName = parMap.get("flashName") == null ? "/fusionCharts/swf/MSColumn2D.swf"
                : parMap.get("flashName").toString();
            String chartWidth = parMap.get("chartWidth") == null ? "400" : parMap.get("chartWidth")
                .toString();
            String chartHeight = parMap.get("chartHeight") == null ? "300" : parMap.get(
                "chartHeight").toString();
            if (is2Html) {
                dataString = FusionChartsUtil.createChartHTML(flashName, null,
                    multiFusionChartsPojo.toXMLString(), "chartId_"
                                                         + RandomStringUtils.randomAlphabetic(6),
                    Integer.parseInt(chartWidth), Integer.parseInt(chartHeight), false);
                
                dataStringSell = FusionChartsUtil.createChartHTML(flashName, null,
                		multiFusionChartsPojoSell.toXMLString(), "chartId_"
                                                             + RandomStringUtils.randomAlphabetic(6),
                        Integer.parseInt(chartWidth), Integer.parseInt(chartHeight), false);
            } else {
                dataString = FusionChartsUtil.createChart(flashName, null, multiFusionChartsPojo
                    .toXMLString(), "chartId_" + RandomStringUtils.randomAlphabetic(6), Integer
                    .parseInt(chartWidth), Integer.parseInt(chartHeight), false, false);
                
                dataStringSell = FusionChartsUtil.createChart(flashName, null, multiFusionChartsPojoSell
                        .toXMLString(), "chartId_" + RandomStringUtils.randomAlphabetic(6), Integer
                        .parseInt(chartWidth), Integer.parseInt(chartHeight), false, false);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException();
        }
        
        List<String> datalist = new ArrayList<String>();
        datalist.add(dataString);
        datalist.add(dataStringSell);
        return datalist;
	}
    
    
    public List<String> getStoreAnalysisday(Map parMap) {
        String dataString = "";
        String dataStringSell = "";
        try {
        	MultiFusionChartsPojo multiFusionChartsPojo = new MultiFusionChartsPojo();
            MultiFusionChartsPojo multiFusionChartsPojoSell = new MultiFusionChartsPojo();
            //
            FCCateGories fcCateGories = new FCCateGories();
            List<FCCateGory> fcCateGoryList = new ArrayList<FCCateGory>();
            fcCateGoryList.add(new FCCateGory("总库存"));
            fcCateGoryList.add(new FCCateGory("香港库存"));
            
            FCCateGories fcCateGoriesSell = new FCCateGories();
            List<FCCateGory> fcCateGoryListSell = new ArrayList<FCCateGory>();
            fcCateGoryListSell.add(new FCCateGory("总销售额"));
            fcCateGoryListSell.add(new FCCateGory("香港销售额"));
            
            
            fcCateGories.setFcCateGoryList(fcCateGoryList);
            
            fcCateGoriesSell.setFcCateGoryList(fcCateGoryListSell);
            // 
            multiFusionChartsPojo.setFcCateGories(fcCateGories);
            
            multiFusionChartsPojoSell.setFcCateGories(fcCateGoriesSell);
            //
            List<FCDataSet> fcDataSetList = new ArrayList<FCDataSet>();
            
            List<FCDataSet> fcDataSetListSell = new ArrayList<FCDataSet>();
            
            List<String> yearMonthList = new ArrayList<String>();
            
            // yearMonth 
            if (parMap.get("yearMonth1") != null) {
            		yearMonthList.add(parMap.get("yearMonth1").toString());
            }
            if (parMap.get("yearMonth2") != null) {
	        		yearMonthList.add(parMap.get("yearMonth2").toString());
	        }
            if (parMap.get("yearMonth3") != null) {
	        		yearMonthList.add(parMap.get("yearMonth3").toString());
	        }
            if (parMap.get("yearMonth4") != null) {
	        		yearMonthList.add(parMap.get("yearMonth4").toString());
	        }
            if (parMap.get("yearMonth5") != null) {
	        		yearMonthList.add(parMap.get("yearMonth5").toString());
	        }
            if (parMap.get("yearMonth6") != null) {
	        		yearMonthList.add(parMap.get("yearMonth6").toString());
	        }
            if (yearMonthList.size() > 0) {
                for (String yearMonth : yearMonthList) {
                    FCDataSet fcCDataSet = new FCDataSet();
                    
                    FCDataSet fcCDataSetSell = new FCDataSet();
                    
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("datemonthday", yearMonth);
                    StoreDay storeDay = analysisDao.getStoreDay(map);
                    if(storeDay!=null){
	                    List<FCSet> fcSetListData = new ArrayList<FCSet>();
	                    
	                    List<FCSet> fcSetListDataSell = new ArrayList<FCSet>();
	                    
	                    FCSet fcSet = new FCSet();
	            		fcSet.setLabel("总库存RMB");
	            		fcSet.setValue(String.format("%.2f", storeDay.getStoreAmount()/10000));
	            		fcSetListData.add(fcSet);
	            		
	            		FCSet fcSet4 = new FCSet();
	            		fcSet4.setLabel("香港库存RMB");
	            		fcSet4.setValue(String.format("%.2f", storeDay.getHkStoreAmount()/10000));
	            		fcSetListData.add(fcSet4);
	            		
	            		FCSet fcSet5 = new FCSet();
	            		fcSet5.setLabel("总销售额RMB");
	            		fcSet5.setValue(String.format("%.2f", storeDay.getSellAmount()/10000));
	            		fcSetListDataSell.add(fcSet5);
	            		
	            		FCSet fcSet6 = new FCSet();
	            		fcSet6.setLabel("香港销售额RMB");
	            		fcSet6.setValue(String.format("%.2f", storeDay.getHkSellAmount()/10000));
	            		fcSetListDataSell.add(fcSet6);
	            		
	            		fcCDataSet.setFcSetList(fcSetListData);
	            		
	            		fcCDataSetSell.setFcSetList(fcSetListDataSell);
                    }else{
                    	List<FCSet> fcSetListData = new ArrayList<FCSet>();
                    	List<FCSet> fcSetListDataSell = new ArrayList<FCSet>();
                    	
                    	FCSet fcSet = new FCSet();
	            		fcSet.setLabel("总库存RMB");
	            		fcSet.setValue(""+0);
	            		fcSetListData.add(fcSet);
	            		
	            		
	            		FCSet fcSet4 = new FCSet();
	            		fcSet4.setLabel("香港库存RMB");
	            		fcSet4.setValue(""+0);
	            		fcSetListData.add(fcSet4);
	            		
	            		FCSet fcSet5 = new FCSet();
	            		fcSet5.setLabel("总销售额RMB");
	            		fcSet5.setValue(""+0);
	            		fcSetListDataSell.add(fcSet5);
	            		
	            		FCSet fcSet6 = new FCSet();
	            		fcSet6.setLabel("香港销售额RMB");
	            		fcSet6.setValue(""+0);
	            		fcSetListDataSell.add(fcSet6);
	            		
	            		fcCDataSet.setFcSetList(fcSetListData);
	            		fcCDataSetSell.setFcSetList(fcSetListDataSell);
                    }
                    
//                    fcCDataSet.setSeriesName(yearMonth.substring(0, yearMonth.lastIndexOf("-"))
//                                             + "月份");
                    fcCDataSet.setSeriesName(yearMonth);
                    fcCDataSetSell.setSeriesName(yearMonth);
                    fcDataSetList.add(fcCDataSet);
                    
                    fcDataSetListSell.add(fcCDataSetSell);
                    
                }
            }
            // 
            multiFusionChartsPojo.setFcDataSetList(fcDataSetList);
            
            multiFusionChartsPojoSell.setFcDataSetList(fcDataSetListSell);
            // 

            multiFusionChartsPojo.setCaption("【库存】图形报表");//
            
            multiFusionChartsPojoSell.setCaption("【销售额】图形报表");//
            // multiFusionChartsPojo
            // .setSubCaption(parMap.get("dateStart") + " - " +
            // parMap.get("dateEnd"));//
            multiFusionChartsPojo.setPalette("2");// palette
            multiFusionChartsPojo.setUseRoundEdges("1");// useRoundEdges
            multiFusionChartsPojo.setBaseFontSize("12");// 
            multiFusionChartsPojo.setYAxisName(" WAN RMB ");
            multiFusionChartsPojo.setXAxisName("地区 日期");
            // multiFusionChartsPojo.setNumberPrefix("");//
            Map<String, String> otherPropertyMap = new HashMap<String, String>();
            
            multiFusionChartsPojo.setShownames("1");
            multiFusionChartsPojo.setShowSum("1");
            multiFusionChartsPojo.setShowvalues("0");
            
            otherPropertyMap.put("numberSuffix", "万");
            
            
            multiFusionChartsPojoSell.setPalette("2");// palette
            multiFusionChartsPojoSell.setUseRoundEdges("1");// useRoundEdges
            multiFusionChartsPojoSell.setBaseFontSize("12");// 
            multiFusionChartsPojoSell.setYAxisName(" WAN RMB ");
            multiFusionChartsPojoSell.setXAxisName("地区 日期");
            // multiFusionChartsPojo.setNumberPrefix("");//
            
            multiFusionChartsPojoSell.setShownames("1");
            multiFusionChartsPojoSell.setShowSum("1");
            multiFusionChartsPojoSell.setShowvalues("0");
            
            
            
            // numberSuffix //
//            multiFusionChartsPojo.setOtherPropertyMap(otherPropertyMap);
            boolean is2Html = true;

            String flashName = parMap.get("flashName") == null ? "/fusionCharts/swf/MSColumn2D.swf"
                : parMap.get("flashName").toString();
            String chartWidth = parMap.get("chartWidth") == null ? "400" : parMap.get("chartWidth")
                .toString();
            String chartHeight = parMap.get("chartHeight") == null ? "300" : parMap.get(
                "chartHeight").toString();
            if (is2Html) {
                dataString = FusionChartsUtil.createChartHTML(flashName, null,
                    multiFusionChartsPojo.toXMLString(), "chartId_"
                                                         + RandomStringUtils.randomAlphabetic(6),
                    Integer.parseInt(chartWidth), Integer.parseInt(chartHeight), false);
                
                dataStringSell = FusionChartsUtil.createChartHTML(flashName, null,
                		multiFusionChartsPojoSell.toXMLString(), "chartId_"
                                                             + RandomStringUtils.randomAlphabetic(6),
                        Integer.parseInt(chartWidth), Integer.parseInt(chartHeight), false);
            } else {
                dataString = FusionChartsUtil.createChart(flashName, null, multiFusionChartsPojo
                    .toXMLString(), "chartId_" + RandomStringUtils.randomAlphabetic(6), Integer
                    .parseInt(chartWidth), Integer.parseInt(chartHeight), false, false);
                
                dataStringSell = FusionChartsUtil.createChart(flashName, null, multiFusionChartsPojoSell
                        .toXMLString(), "chartId_" + RandomStringUtils.randomAlphabetic(6), Integer
                        .parseInt(chartWidth), Integer.parseInt(chartHeight), false, false);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException();
        }
        
        List<String> datalist = new ArrayList<String>();
        datalist.add(dataString);
        datalist.add(dataStringSell);
        return datalist;
	}
    
}