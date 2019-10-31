package com.huaixuan.network.biz.dao.stock.ibatis;

 import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.stock.ShoppingRefundDetailDao;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;

/**
* @ClassName: ShoppingRefundDetailDaoiBatis
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:49:46
 */
@Repository("shoppingRefundDetailDao")
public class ShoppingRefundDetailDaoiBatis implements ShoppingRefundDetailDao{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

 	 public long addShoppingRefundDetail(ShoppingRefundDetail shoppingRefundDetail)throws Exception{
 		 return (Long) sqlMapClient.insert("addShoppingRefundDetail", shoppingRefundDetail);
 	 }

 	 public void editShoppingRefundDetail(ShoppingRefundDetail shoppingRefundDetail)throws Exception{
 		 sqlMapClient.update("editShoppingRefundDetail", shoppingRefundDetail);
 	 }

 	 public void removeShoppingRefundDetail(Long shoppingRefundDetailId)throws Exception{
 		 sqlMapClient.delete("removeShoppingRefundDetail",shoppingRefundDetailId);
 	 }

 	 public ShoppingRefundDetail getShoppingRefundDetail(Long shoppingRefundDetailId)throws Exception{
 		 return (ShoppingRefundDetail)sqlMapClient.queryForObject("getShoppingRefundDetail", shoppingRefundDetailId);
 	 }

 	 public List<ShoppingRefundDetail> getShoppingRefundDetails()throws Exception{
 		 return sqlMapClient.queryForList("getShoppingRefundDetails", null);
 	 }

 	@SuppressWarnings("unchecked")
	public List<ShoppingRefundDetail> getShoppingRefundDetails(Long shoppingRefundDetailId)throws Exception{
		 return sqlMapClient.queryForList("getShoppingRefundDetailes", shoppingRefundDetailId);
	 }



	public int getCountRefundByShoppingIdAndStatus(Map<String, String> parMap) throws Exception {
		return (Integer) sqlMapClient.queryForObject("getCountRefundByShoppingIdAndStatus", parMap);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingRefundDetail> getRefundDetial(Map<String, Object> parMap)
			throws Exception {
		return (List<ShoppingRefundDetail>)sqlMapClient.queryForList("getRefundDetail",parMap);
	}

	public List<ShoppingRefundDetail> getStorageRefundDetails(
			Map<String, Object> parMap) throws Exception {
		return (List<ShoppingRefundDetail>)sqlMapClient.queryForList("getStorageRefundDetails",parMap);
	}

	public int getCountRefundDetail(Map<String,Object> paramMap)throws Exception{
		return (Integer) sqlMapClient.queryForObject("countShoppingRefundDetailes",paramMap);
	}

	public ShoppingRefundDetail sumRefundDetailByShoppingId(
			Map<String, Object> param)throws Exception {
		return (ShoppingRefundDetail) sqlMapClient.queryForObject("sumRefundDetailsByShoppingId",param);
	}

	public List<ShoppingRefundDetail> getShoppingRefundDetailByShoppingRefundId(
			Long shoppingRefundId) {
		return sqlMapClient.queryForList("getShoppingRefundDetailByShoppingRefundId", shoppingRefundId);
	}
}
