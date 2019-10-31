package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsAttrDao;
import com.huaixuan.network.biz.domain.goods.GoodsAttr;

@Repository("goodsAttrDao")
public class GoodsAttrDaoiBatis implements GoodsAttrDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	 public void addGoodsAttr(GoodsAttr goodsAttr)throws Exception{
		 this.sqlMapClient.insert("addGoodsAttr", goodsAttr);
	 }

	 public void editGoodsAttr(GoodsAttr goodsAttr)throws Exception{
		 this.sqlMapClient.update("editGoodsAttr", goodsAttr);
	 }

	 public void removeGoodsAttr(Long goodsAttrId)throws Exception{
		 this.sqlMapClient.delete("removeGoodsAttr",goodsAttrId);
	 }

	 public GoodsAttr getGoodsAttr(Long goodsAttrId)throws Exception{
		 return (GoodsAttr)this.sqlMapClient.queryForObject("getGoodsAttr", goodsAttrId);
	 }

	 public List<GoodsAttr> getGoodsAttrs()throws Exception{
		 return this.sqlMapClient.queryForList("getGoodsAttrs", null);
	 }

	public GoodsAttr getGoodsAttrByGoodsIdAndAttrId(Map parameterMap) {
       return (GoodsAttr) this.sqlMapClient.queryForObject("getGoodsAttrByGoodsIdAndAttrId",
           parameterMap);
	}
	/**
    * 根据物品id返回List<GoodsAttr>
    * @param promation
    * @throws Exception
    */
  
	public List<GoodsAttr> getGoodsAttrByGoodSId(Long goodsId) throws Exception {
       return this.sqlMapClient.queryForList("getGoodsAttrByGoodSId", goodsId); 
   }
	/**
    * 删除物品id为 goodsAttrId的所有GoodSAttr
    * @param promation
    * @throws Exception
    */
	public void removeGoodsAttrByGoods(Long goodsAttrId)throws Exception{ 
       this.sqlMapClient.delete("removeGoodsAttrByGoods",goodsAttrId); 
   }

	public GoodsAttr getGoodsAttrByMap(Map parMap) {
		return (GoodsAttr)this.sqlMapClient.queryForObject("getGoodsAttrByMap", parMap);
	} 
}
