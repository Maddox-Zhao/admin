package com.huaixuan.network.biz.dao.goods;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsAttr;


 public interface GoodsAttrDao {
	 	/* @interface model: GoodsAttr */
	 	void addGoodsAttr(GoodsAttr goodsAttr) throws Exception;

	 	/* @interface model: GoodsAttr */
	 	void editGoodsAttr(GoodsAttr goodsAttr) throws Exception;

	 	/* @interface model: GoodsAttr */
	 	void removeGoodsAttr(Long goodsAttrId) throws Exception;

	 	/* @interface model: GoodsAttr,GoodsAttr */
	 	GoodsAttr getGoodsAttr(Long goodsAttrId) throws Exception;

	 	GoodsAttr getGoodsAttrByGoodsIdAndAttrId(Map parameterMap);

	 	/* @interface model: GoodsAttr,GoodsAttr */
	 	List <GoodsAttr> getGoodsAttrs() throws Exception;
	 	
	 	public void removeGoodsAttrByGoods(Long goodsAttrId) throws Exception; 
	 	
	 	/**
	 	 * 根据物品id返回List<GoodsAttr>
	 	 * @param goodsId
	 	 * @return
	 	 * @throws Exception
	 	 */
	 	public  List<GoodsAttr> getGoodsAttrByGoodSId(Long goodsId) throws Exception; 
	 	
	 	GoodsAttr getGoodsAttrByMap(Map parMap);
 }
