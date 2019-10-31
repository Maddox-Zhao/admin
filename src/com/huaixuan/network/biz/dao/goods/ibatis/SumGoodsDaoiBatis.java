package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.SumGoodsDao;
import com.huaixuan.network.biz.domain.goods.SumGoods;

@Repository("sumGoodsDao")
public class SumGoodsDaoiBatis implements SumGoodsDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addSumGoods(SumGoods sumGoods) {
        this.sqlMapClient.insert("addSumGoods", sumGoods);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SumGoods> getSumGoodslist() {
        return this.sqlMapClient.queryForList("getSumGoods", null);
    }

    @Override
    public void updateSumGoods(SumGoods sumGoods) {
        this.sqlMapClient.update("editSumGoods", sumGoods);
    }
}
