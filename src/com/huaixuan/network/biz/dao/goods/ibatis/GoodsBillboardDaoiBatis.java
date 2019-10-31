package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsBillboardDao;
import com.huaixuan.network.biz.domain.goods.GoodsBillboard;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;

/**
 * @version 3.2.0
 */
@Repository("goodsBillboardDao")
public class GoodsBillboardDaoiBatis implements GoodsBillboardDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addGoodsBillboard(GoodsBillboard goodsBillboard)
			throws Exception {
		this.sqlMapClient.insert("GoodsBillboard.addGoodsBillboard", goodsBillboard);
	}

	@Override
	public void editGoodsBillboard(GoodsBillboard goodsBillboard)
			throws Exception {
		this.sqlMapClient.update("GoodsBillboard.editGoodsBillboard", goodsBillboard);
	}

	@Override
	public void removeGoodsBillboard(Long goodsBillboardId) throws Exception {
		this.sqlMapClient.delete("GoodsBillboard.removeGoodsBillboard", goodsBillboardId);
	}

	@Override
	public GoodsBillboard getGoodsBillboard(Long goodsBillboardId)
			throws Exception {
		return (GoodsBillboard) this.sqlMapClient.queryForObject(
				"getGoodsBillboard", goodsBillboardId);
	}

	@Override
	public List<GoodsBillboard> getGoodsBillboards() throws Exception {
		return this.sqlMapClient.queryForList("GoodsBillboard.getGoodsBillboards", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsBillboard> getRankGoodsBy(String cat, String type,
			Integer num, Integer numAll) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cat", cat);
		map.put("type", type);
		map.put("num", num);
		map.put("numAll", numAll);
		return this.sqlMapClient.queryForList("GoodsBillboard.getRankGoodsBy", map);
	}

	@Override
	public void createBillboard(GoodsPoint gp, long points, int type) {
		Map map = new HashMap();
		map.put("goodsId", gp.getGoodsId());
		map.put("goodsCat", gp.getGoodsCat());
		map.put("points", points);
		map.put("type", type);
		this.sqlMapClient.insert("GoodsBillboard.insertBilllboard", map);
	}

	@Override
	public boolean updateBilllboard(GoodsPoint gp, long points, int type) {
		Map map = new HashMap();
		map.put("goodsId", gp.getGoodsId());
		map.put("goodsCat", gp.getGoodsCat());
		map.put("points", points);
		map.put("type", type);
		int rows = this.sqlMapClient.update("GoodsBillboard.updateBilllboard", map);
		return rows >= 1;
	}
}
