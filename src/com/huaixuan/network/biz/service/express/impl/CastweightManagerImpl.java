package com.huaixuan.network.biz.service.express.impl;

 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.express.CastweightDao;
import com.huaixuan.network.biz.dao.express.ExpressDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.express.CastweightManager;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
 * @version 3.2.0
 */
@Service("castweightManager")
public class CastweightManagerImpl implements CastweightManager {

	@Autowired
	public CastweightDao castweightDao;
	@Autowired
	public GoodsDao       goodsDao;
	@Autowired
	public ExpressDao   expressDao;

	protected final Log    log = LogFactory.getLog(getClass());

	@Override
	public void addCastweight(Castweight castweightDao) {
		log.info("CastweightManagerImpl.addCastweight method");
		try {
			 this.castweightDao.addCastweight(castweightDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editCastweight(Castweight castweight) {
		log.info("CastweightManagerImpl.editCastweight method");
		try {
			this.castweightDao.editCastweight(castweight);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeCastweight(Long castweightId) {
		log.info("CastweightManagerImpl.removeCastweight method");
		try {
			this.castweightDao.removeCastweight(castweightId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Castweight getCastweight(Long castweightId) {
		log.info("CastweightManagerImpl.getCastweight method");
		try {
			return this.castweightDao.getCastweight(castweightId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Castweight getCastweightByGoodsIdAndExpessId(Long goodsId,
			Long expressId) {
		log.info("CastweightManagerImpl.getCastweightByGoodsIdAndExpessId method");
		try {
			return this.castweightDao.getCastweightByGoodsIdAndExpessId(goodsId,expressId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Castweight> getCastweights() {
		log.info("CastweightManagerImpl.getCastweights method");
		try {
			return this.castweightDao.getCastweights();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
 	public int getCastWeightCount(Map<String, String> parMap){
 		log.info("CastWeightManagerImpl.getCastWeightCount method");
 		try {
 			return this.castweightDao.getCastWeightCount(parMap);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return 0;
 	}

	@Override
 	public QueryPage getCastWeightList(Map<String,String> parMap, int currentPage, int pageSize, boolean isPage){
 		log.info("CastWeightManagerImpl.getCastWeightList method");
 		try {
 			return this.castweightDao.getCastWeightList(parMap, currentPage, pageSize, isPage);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

	@Override
 	public void addCastweights(Map<String,String> parMap,String[] checkStatus,List<Express> expressInfoList){
 		log.info("CastweightManagerImpl.addCastweights method");
		try {
			int count=0;
			for(Express e:expressInfoList){
				if(StringUtil.isNotBlank(parMap.get(String.valueOf(e.getId())))){
				Castweight c=new Castweight();
				String goodsSn=parMap.get("goodsSn");
				Double cWeight=Double.valueOf(parMap.get(String.valueOf(e.getId())));
				c.setCastWeight(cWeight);
				Goods g=goodsDao.getGoodsByGoodsSn(goodsSn);
				c.setGoodsSn(goodsSn);
				c.setGoodsId(g.getId());
				c.setExpressId(e.getId());
				c.setStatus(checkStatus[count]);
				this.castweightDao.addCastweight(c);
				}
				count++;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
 	}

	@Override
 	public void modifyCastweights(Map<String,String> parMap,String[] checkStatus,List<Express> expressInfoList){
 		log.info("CastweightManagerImpl.modifyCastweights method");
		try {
			int count=0;
			String goodsSn=parMap.get("goodsSn");
			for(Express e:expressInfoList){
				if(StringUtil.isNotBlank(parMap.get(String.valueOf(e.getId())))){
				Castweight cw=new Castweight();
				Map<String,String> pm=new HashMap<String, String>();
				pm.put("expressId", String.valueOf(e.getId()));
				pm.put("goodsSn", goodsSn);
				cw=castweightDao.getCheckCastWeight(pm);
				if(null!=cw){
					Castweight c=new Castweight();
					Double cWeight=Double.valueOf(parMap.get(String.valueOf(e.getId())));
					c.setCastWeight(cWeight);
					c.setGoodsSn(goodsSn);
					c.setExpressId(e.getId());
					c.setStatus(checkStatus[count]);
					this.castweightDao.updateCastweight(c);
				}else{
					Castweight c=new Castweight();
					Double cWeight=Double.valueOf(parMap.get(String.valueOf(e.getId())));
					c.setCastWeight(cWeight);
					Goods g=goodsDao.getGoodsByGoodsSn(goodsSn);
					c.setGoodsSn(goodsSn);
					c.setGoodsId(g.getId());
					c.setExpressId(e.getId());
					c.setStatus(checkStatus[count]);
					this.castweightDao.addCastweight(c);
				}
			}
				count++;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
 	}
 }
