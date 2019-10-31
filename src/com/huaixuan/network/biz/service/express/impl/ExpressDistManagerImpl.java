package com.huaixuan.network.biz.service.express.impl;

 import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.express.ExpressDistDao;
import com.huaixuan.network.biz.dao.express.RegionDao;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.express.ExpressDistManager;

 /**
  * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
  * @version 3.2.0
  */
@Service("expressDistManager")
 public class ExpressDistManagerImpl implements ExpressDistManager {

	@Autowired
 	public ExpressDistDao expressDistDao;
	@Autowired
	RegionDao regionDao;

	@Override
 	public void addExpressDist(ExpressDist expressDistDao) {
// 		log.info("ExpressDistManagerImpl.addExpressDist method");
 		try {
 			 this.expressDistDao.addExpressDist(expressDistDao);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 	}

	@Override
 	public void editExpressDist(ExpressDist expressDist) {
// 		log.info("ExpressDistManagerImpl.editExpressDist method");
 		try {
 			this.expressDistDao.editExpressDist(expressDist);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 	}

	@Override
 	public void removeExpressDist(Long expressDistId) {
// 		log.info("ExpressDistManagerImpl.removeExpressDist method");
 		try {
 			this.expressDistDao.removeExpressDist(expressDistId);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 	}

	@Override
 	public ExpressDist getExpressDist(Long expressDistId) {
// 		log.info("ExpressDistManagerImpl.getExpressDist method");
 		try {
 			return this.expressDistDao.getExpressDist(expressDistId);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return null;
 	}

	@Override
 	public List<ExpressDist> getExpressDists() {
// 		log.info("ExpressDistManagerImpl.getExpressDists method");
 		try {
 			return this.expressDistDao.getExpressDists();
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return null;
 	}

	@Override
 	public ExpressDist getExpressDistById(Long expressDistId){
// 		log.info("ExpressDistManagerImpl.getExpressDistById method");
 		try {
 			return this.expressDistDao.getExpressDistById(expressDistId);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return null;
 	}

	@Override
 	public int removeExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd){
// 		log.info("ExpressDistManagerImpl.removeExpressDistByRegion method");
 		try {
 			return this.expressDistDao.removeExpressDistByRegion(expressId, regionCodeStart, regionCodeEnd);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return 0;
 	}

 	@SuppressWarnings("unchecked")
 	@Override
	public int getExpressDistCountByCond(Map map){
// 		log.info("ExpressDistManagerImpl.getExpressDistCountByCond method");
 		try {
 			return this.expressDistDao.getExpressDistCountByCond(map);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return 0;
 	}

 	@SuppressWarnings("unchecked")
	public QueryPage getExpressDistByCond(ExpressDistQuery expressDistQuery, int currentPage, int pageSize){
// 		log.info("ExpressDistManagerImpl.getExpressDistByCond method");
 		QueryPage query = new QueryPage(expressDistQuery);
        Map parMap = query.getParameters();
        parMap.put("regionCodeStartList", expressDistQuery.getRegionCodeStartList());
        parMap.put("regionCodeEndList", expressDistQuery.getRegionCodeEndList());
 		QueryPage queryPage = null;
 		try {
 			if(("0").equals(parMap.get("expressId"))){
 				parMap.put("expressId", null);
 			}
 			queryPage = this.expressDistDao.getExpressDistByCond(parMap, currentPage, pageSize);
 			List<ExpressDist> expressDistListForSearch = (List<ExpressDist>)queryPage.getItems();
 			if(expressDistListForSearch != null){
 				for (ExpressDist expressDistTemp : expressDistListForSearch) {
 	 	        	if (expressDistTemp != null) {
 	 	        		Region regionStartForDistrict = this.regionDao.getRegionByCode(expressDistTemp.getRegionCodeStart());
 	 	        		if (regionStartForDistrict != null) {
 	 	        			Region regionStartForCity = this.regionDao.getRegionByCode(regionStartForDistrict.getParentCode());
 	 	        			if (regionStartForCity != null) {
 	 	        				expressDistTemp.setRegionCodeStartCityName(regionStartForCity.getRegionName());
 	 	        				Region regionStartForProvince = this.regionDao.getRegionByCode(regionStartForCity.getParentCode());
 	 	        				if (regionStartForProvince != null) {
 	 	        					expressDistTemp.setRegionCodeStartProvinceName(regionStartForProvince.getRegionName());
 	 	        				}
 	 	        			}
 	 	        		}
 	 	        		Region regionEndForDistrict = this.regionDao.getRegionByCode(expressDistTemp.getRegionCodeEnd());
 	 	        		if (regionEndForDistrict != null) {
 	 	        			Region regionEndForCity = this.regionDao.getRegionByCode(regionEndForDistrict.getParentCode());
 	 	        			if (regionEndForCity != null) {
 	 	        				expressDistTemp.setRegionCodeEndCityName(regionEndForCity.getRegionName());
 	 	        				Region regionEndForProvince = this.regionDao.getRegionByCode(regionEndForCity.getParentCode());
 	 	        				if (regionEndForProvince != null) {
 	 	        					expressDistTemp.setRegionCodeEndProvinceName(regionEndForProvince.getRegionName());
 	 	        				}
 	 	        			}
 	 	        		}
 	 	        	}
 	 	        }
 	 			queryPage.setItems(expressDistListForSearch);
 			}
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 			e.printStackTrace();
 		}
 		return queryPage;
 	}

 	@Override
 	public List<ExpressDist> listExpressDistForTrade(){
// 		log.info("ExpressDistManagerImpl.listExpressDistForTrade method");
 		try {
 			return this.expressDistDao.listExpressDistForTrade();
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	@Override
 	public int getExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd, String payment){
// 		log.info("ExpressDistManagerImpl.getExpressDistByRegion method");
 		try {
 			return this.expressDistDao.getExpressDistByRegion(expressId, regionCodeStart, regionCodeEnd, payment);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return 0;
 	}

 	@Override
 	public int getCountByRegionCodeEnd(String regionCodeEnd, String payment){
// 		log.info("ExpressDistManagerImpl.getCountByRegionCodeEnd method");
 		try {
 			return this.expressDistDao.getCountByRegionCodeEnd(regionCodeEnd, payment);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return 0;
 	}

 	@SuppressWarnings("unchecked")
 	@Override
	public List<ExpressDist> listExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd, String payment, Long expressId){
// 		log.info("ExpressDistManagerImpl.listExpressDistByRegionCodeEnd method");
 		try {
 			return this.expressDistDao.listExpressDistByRegionCodeEnd(regionCodeStart, regionCodeEnd, payment, expressId);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	@Override
 	public int getCountByFourForUpdate(String regionCodeStart, String regionCodeEnd, Long expressId, Long id, String payment){
// 		log.info("ExpressDistManagerImpl.getCountByFourForUpdate method");
 		try {
 			return this.expressDistDao.getCountByFourForUpdate(regionCodeStart, regionCodeEnd, expressId, id, payment);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return 0;
 	}

 	@Override
 	public int editExpressDistByFourCond(ExpressDist expressDist){
// 		log.info("ExpressDistManagerImpl.editExpressDistByFourCond method");
 		try {
 			return this.expressDistDao.editExpressDistByFourCond(expressDist);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
 		return 0;
 	}

 	@Override
    public ExpressDist getExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd,
                                                     String payment, Long expressId) {
//        log.info("ExpressDistManagerImpl.getExpressDistByRegionCodeEnd method");
        try {
            return this.expressDistDao.getExpressDistByRegionCodeEnd(regionCodeStart, regionCodeEnd, payment,expressId);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
        return null;
    }

 	@Override
	public int bathUpdateStatus(List<Long> expressDistIds, String status) {
// 		log.info("ExpressDistManagerImpl.bathUpdateStatus method");
 		try {
 			return this.expressDistDao.bathUpdateStatus(expressDistIds, status);
 		} catch (Exception e) {
// 			log.error(e.getMessage());
 		}
        return 0;
	}
 }
