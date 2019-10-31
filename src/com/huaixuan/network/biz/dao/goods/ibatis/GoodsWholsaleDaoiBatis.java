package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsWholsaleDao;
import com.huaixuan.network.biz.domain.goods.GoodsWholsale;

@Repository("goodsWholsaleDao")
public class GoodsWholsaleDaoiBatis implements GoodsWholsaleDao {
	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	public void addGoodsWholsale(GoodsWholsale goodsWholsale) {
		this.sqlMapClient.insert("addgoodsWholsale",goodsWholsale);
	}

	public List<GoodsWholsale> getGoodsWholsalelistByGoodsId(Long goodsId) {
		return this.sqlMapClient.queryForList("getGoodsWholsalelistByGoodsId", goodsId);
	}

	public void removeGoodswholesaleById(Long id) {
		this.sqlMapClient.delete("removeGoodswholesaleById", id);
	}
}
