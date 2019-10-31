package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Showcase;
import com.huaixuan.network.biz.query.QueryPage;

public interface ShowcaseService {
	public Long addShowcase(Showcase showcase) throws Exception;

	public void editShowcase(Showcase showcase) throws Exception;

	public void removeShowcase(Long showcaseId) throws Exception;

	public Showcase getShowcase(Long showcaseId) throws Exception;

	public List<Showcase> getShowcases() throws Exception;

	void showcaseUp(Long showcaseId) throws Exception;

	void showcaseDown(Long showcaseId) throws Exception;

	void addGoodsToCabinet(List<Long> goodsIds, Long cabId) throws Exception;

	public QueryPage getShowcasesPage(long cabinetId, int currentPage,
			int pageSize) throws Exception;

	int getGoodsNumByCabId(long cabId) throws Exception;

	Showcase getShowcaseByGoodsIdAndCabId(Long goodsId, Long cabId)
			throws Exception;

}
