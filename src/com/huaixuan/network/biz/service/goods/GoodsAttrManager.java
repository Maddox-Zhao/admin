package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsAttr;

public interface GoodsAttrManager {
    /* @interface model: GoodsAttr */
    public void addGoodsAttr(GoodsAttr goodsAttr);

    /* @interface model: GoodsAttr */
    public void editGoodsAttr(GoodsAttr goodsAttr);

    /* @interface model: GoodsAttr */
    public void removeGoodsAttr(Long goodsAttrId);

    /* @interface model: GoodsAttr,GoodsAttr */
    public GoodsAttr getGoodsAttr(Long goodsAttrId);

    /* @interface model: GoodsAttr,GoodsAttr */
    public List<GoodsAttr> getGoodsAttrs();

    public GoodsAttr getGoodsAttrByGoodsIdAndAttrId(Long goodsId, Long goodsAttrId);

    /**
     * 根据物品id返回List<GoodsAttr>
     * @param goodsId
     * @return
     */
    public List<GoodsAttr> getGoodsAttrByGoodSId(Long goodsId) throws Exception;
    
    public GoodsAttr getGoodsAttrByMap(Map parMap);
}
