package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsPointDao;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;

@Repository("goodsPointDao")
public class GoodsPointDaoiBatis implements GoodsPointDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addGoodsPoint(GoodsPoint goodsPoint) throws Exception {
        sqlMapClient.insert("GoodsPoint.addGoodsPoint", goodsPoint);
    }

    @Override
    public void editGoodsPoint(GoodsPoint goodsPoint) throws Exception {
        sqlMapClient.update("GoodsPoint.editGoodsPoint", goodsPoint);
    }

    @Override
    public void removeGoodsPoint(Long goodsPointId) throws Exception {
        sqlMapClient.delete("GoodsPoint.removeGoodsPoint", goodsPointId);
    }

    @Override
    public GoodsPoint getGoodsPoint(Long goodsPointId) throws Exception {
        return (GoodsPoint) sqlMapClient.queryForObject("GoodsPoint.getGoodsPoint", goodsPointId);
    }

    @Override
    public List<GoodsPoint> getGoodsPoints() throws Exception {
        return sqlMapClient.queryForList("GoodsPoint.getGoodsPoints", null);
    }

    @Override
    public List<GoodsPoint> getHotSalePoints(String dateString) {
        return sqlMapClient.queryForList("GoodsPoint.getHotSalePoints", dateString);
    }

    @Override
    public List<GoodsPoint> getHighPopularPoint(String dateString) {
        return sqlMapClient.queryForList("GoodsPoint.getHighPopularPoint", dateString);
    }

    /**
     * 取得商品某天的排行分数 
     * @param goodsId
     * @param pointDate"yyyy-MM-dd"
     * @return
     * @see com.hundsun.bible.dao.GoodsPointDao#getGPByGoodsIdAndPointDate(java.lang.Long, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public GoodsPoint getGPByGoodsIdAndPointDate(Long goodsId, String pointDate) {
        Map map = new HashMap();
        map.put("goodsId", goodsId);
        map.put("pointDate", pointDate);
        GoodsPoint gp = (GoodsPoint) this.sqlMapClient.queryForObject(
            "GoodsPoint.getGPByGoodsIdAndPointDate", map);
        if (null == gp)
            return null;
        return gp;
    }
}
