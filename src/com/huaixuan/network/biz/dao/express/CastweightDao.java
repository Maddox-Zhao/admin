package com.huaixuan.network.biz.dao.express;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.query.QueryPage;

 /**
  * 
  * @version 3.2.0
  */
 public interface CastweightDao{
	 	void addCastweight(Castweight castweight) throws Exception;

	 	void editCastweight(Castweight castweight) throws Exception;

	 	void removeCastweight(Long castweightId) throws Exception;

	 	Castweight getCastweight(Long castweightId) throws Exception;

	 	Castweight getCastweightByGoodsIdAndExpessId(Long goodsId,Long expressId) throws Exception;

	 	List <Castweight> getCastweights() throws Exception;

 	int getCastWeightCount(Map<String,String> parMap)throws Exception;

 	QueryPage getCastWeightList(Map parMap, int currentPage, int pageSize, boolean isPage)throws Exception;

 	void updateCastweight(Castweight castweight) throws Exception;
 	Castweight getCheckCastWeight(Map<String,String> pm)throws Exception;
 }
