package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsGalleryDao;
import com.huaixuan.network.biz.domain.goods.GoodsGallery;


@Repository("goodsGalleryDao")
public class GoodsGalleryDaoiBatis implements GoodsGalleryDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public void addGoodsGallery(GoodsGallery goodsGallery) throws Exception {
        this.sqlMapClient.insert("GoodsGallery.addGoodsGallery", goodsGallery);
    }

    public void editGoodsGallery(GoodsGallery goodsGallery) throws Exception {
        this.sqlMapClient.update("GoodsGallery.editGoodsGallery", goodsGallery);
    }

    public void removeGoodsGallery(Long goodsGalleryId) throws Exception {
        this.sqlMapClient.delete("GoodsGallery.removeGoodsGallery", goodsGalleryId);
    }

    public GoodsGallery getGoodsGallery(Long goodsGalleryId) throws Exception {
        return (GoodsGallery) this.sqlMapClient.queryForObject("GoodsGallery.getGoodsGallery",
            goodsGalleryId);
    }

    public List<GoodsGallery> getGoodsGallerys() throws Exception {
        return this.sqlMapClient.queryForList("GoodsGallery.getGoodsGallerys", null);
    }

    public GoodsGallery getGoodsGalleryByDesc(String desc) throws Exception {
        return (GoodsGallery) this.sqlMapClient.queryForObject(
            "GoodsGallery.getGoodsGalleryByDesc", desc);
    }

    /**
     * 根据参数查询结果集
     * @param goodsId
     * @return
     * @see com.hundsun.bible.dao.GoodsGalleryDao#getGoodsGallerysByGoodsId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<GoodsGallery> getGoodsGallerysByParameterMap(Map parameterMap) {
        return this.sqlMapClient.queryForList("GoodsGallery.getGoodsGallerysByParameterMap", parameterMap);
    }

	public int getMaxSortNumByGoodsId(Long goodsId) {
		return (Integer) this.sqlMapClient.queryForObject(
	            "GoodsGallery.getMaxSortNumByGoodsId", goodsId);
	}

	public void goodsGallerysDownBig(Long goodsGalleryId) throws Exception {
        this.sqlMapClient.update("GoodsGallery.goodsGallerysDownBig", goodsGalleryId);
	}

	public void goodsGallerysDownSamll(Long goodsId, int sort) throws Exception {
        Map prama = new HashMap();
        prama.put("goodsId", goodsId);
        prama.put("sort", sort);
        this.sqlMapClient.update("GoodsGallery.goodsGallerysDownSmall", prama);

	}

	public void goodsGallerysUpBig(Long goodsGalleryId) throws Exception {
        this.sqlMapClient.update("GoodsGallery.goodsGallerysUpBig", goodsGalleryId);

	}

	public void goodsGallerysUpSamll(Long goodsId, int sort) throws Exception {
        Map prama = new HashMap();
        prama.put("goodsId", goodsId);
        prama.put("sort", sort);
        this.sqlMapClient.update("GoodsGallery.goodsGallerysUpSmall", prama);
	}

	public void updateGoodsGallerysSortByGoodsId(GoodsGallery goodsGallery)
			throws Exception {
        this.sqlMapClient.update("GoodsGallery.updateGoodsGallerysSortByGoodsId", goodsGallery);
	}

}
