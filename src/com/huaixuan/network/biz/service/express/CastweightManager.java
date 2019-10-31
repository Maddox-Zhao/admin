package com.huaixuan.network.biz.service.express;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.query.QueryPage;

 /**
  * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public interface CastweightManager {
	 	/* @interface model: ï¿½ï¿½ï¿½Ò»ï¿½ï¿½Castweightï¿½ï¿½Â¼ */
	 	public void addCastweight(Castweight castweight);

	 	/* @interface model: ï¿½ï¿½ï¿½ï¿½Ò»ï¿½ï¿½Castweightï¿½ï¿½Â¼ */
	 	public void editCastweight(Castweight castweight);

	 	/* @interface model: É¾ï¿½ï¿½Ò»ï¿½ï¿½Castweightï¿½ï¿½Â¼ */
	 	public void removeCastweight(Long castweightId);

	 	/* @interface model: ï¿½ï¿½Ñ¯Ò»ï¿½ï¿½Castweightï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½Castweightï¿½ï¿½ï¿½ï¿½ */
	 	public Castweight getCastweight(Long castweightId);

	 	/* @interface model: æ ¹æ®å•†å“IDå’Œç‰©æµå…¬å¸IDæŸ¥è¯¢è®°å½•*/
	 	public Castweight getCastweightByGoodsIdAndExpessId(Long goodsId,Long expressId);

	 	/* @interface model: ï¿½ï¿½Ñ¯ï¿½ï¿½ï¿½ï¿½Castweightï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½Castweightï¿½ï¿½ï¿½ï¿½Ä¼ï¿½ï¿½ï¿ */
	 	public List<Castweight> getCastweights();

 	/**
 	 *¼ìË÷¼ÆÅ×ÖØÁ¿×ÜÊı
 	 *@param parMap Map
 	 *@return int
 	 *@author chenhang 2011/02/16
 	 */
 	int getCastWeightCount(Map<String,String> parMap);

 	/**
 	 *¼ìË÷¼ÆÅ×ÖØÁ¿
 	 *@param parMap Map
 	 *@param page Page
 	 *@return List
 	 *@author chenhang 2011/02/16
 	 */
 	QueryPage getCastWeightList(Map<String,String> parMap, int currentPage, int pageSize, boolean isPage);

 	/**
 	 * ÅúÁ¿ĞÂÔöÉÌÆ·µÄ¼ÆÅ×ÖØÁ¿
 	 * @author chenhang 2011/02/16
 	 */
 	public void addCastweights(Map<String,String> parMap,String[] checkStatus,List<Express> expressInfoList);

 	/**
 	 * ÅúÁ¿¸üĞÂÉÌÆ·µÄ¼ÆÅ×ÖØÁ¿
 	 * @author chenhang 2011/02/16
 	 */
 	public void modifyCastweights(Map<String,String> parMap,String[] checkStatus,List<Express> expressInfoList);
 }
