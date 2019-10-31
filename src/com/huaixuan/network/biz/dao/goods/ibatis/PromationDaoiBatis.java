package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.PromationDao;
import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationGive;
import com.huaixuan.network.biz.domain.goods.PromationVO;
import com.huaixuan.network.biz.exception.DaoException;
import com.huaixuan.network.biz.query.QueryPage;


@Repository("promationDao")
 public class PromationDaoiBatis implements PromationDao{
	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	 public long addPromation(Promation promation)throws Exception{
		Long id = (Long) this.sqlMapClient.insert("addPromation", promation);
		return id;
	 }

	 public void editPromation(Promation promation)throws Exception{
		 this.sqlMapClient.update("editPromation", promation);
	 }

	 public void removePromation(Long promationId)throws Exception{
		 this.sqlMapClient.delete("removePromation",promationId);
	 }

	 public Promation getPromation(Long promationId)throws Exception{
		 return (Promation)this.sqlMapClient.queryForObject("getPromation", promationId);
	 }

	 public List<Promation> getPromations()throws Exception{
		 return this.sqlMapClient.queryForList("getPromations", null);
	 }

   public List<PromationVO> getPromationsByParams(Map map) throws Exception {
       return this.sqlMapClient.queryForList("getPromationsByParams", map);
   }

//   public List<Promation> getPromationsByPage(Map<String, Object> conditions, Page page) throws Exception {
//       return this.findQueryPage("getPromationsByCondition", "getPromationsByConditionCount", conditions, page);
//   }


   public List<Promation> getPromationsByIds(List ids) throws DaoException {

       Map map = new HashMap();
       map.put("ids", ids);
       return this.sqlMapClient.queryForList("getPromationsByIds", map);
   }

	public List<PromationVO> getGivePromationList(Map<String, Object> params) {
		return this.sqlMapClient.queryForList("getGivePromationList", params);
	}

   public Promation getPromationByModeCode(String modeCode) throws Exception {
   	return (Promation)this.sqlMapClient.queryForObject("getPromationByModeCode", modeCode);
	}

	public List<Promation> getPromationByName(String name) throws Exception {
       return this.sqlMapClient.queryForList("getPromationsByName", name);
   }


   public void editPromationByDynamic(Promation promation) throws Exception {
       this.sqlMapClient.update("editPromationByDynamic", promation);
   }

   public void autoCanelFreeze() {
       this.sqlMapClient.update("autoCanelFreeze");
   }

	public List<PromationGive> getNewPromationVOListGive(String nowDate) {
		Map parMap = new HashMap();
		parMap.put("nowDate", nowDate);
		return this.sqlMapClient.queryForList("getNewPromationVOListGive", parMap);
	}

	public List<PromationGive> getGivePromation(Long promationId) {
		try{
		return this.sqlMapClient.queryForList("getGivePromation", promationId);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return null;
	}

	public List<Promation> getFullGivePromationList(Map parMap) {
		return this.sqlMapClient.queryForList("getFullGivePromationList", parMap);
	}

	@Override
	public Integer getPromationListByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getPromationsByConditionCount", parMap);
	}

	@Override
	public List<Promation> getPromationListByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getPromationsByCondition", parMap);
	}

 }
