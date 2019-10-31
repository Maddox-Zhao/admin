package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Showcase;

public interface ShowcaseDao {
	/* @interface model: Showcase */
	Long addShowcase(Showcase showcase) throws Exception;

	/* @interface model: Showcase */
	void editShowcase(Showcase showcase) throws Exception;

	/* @interface model: Showcase */
	void removeShowcase(Long showcaseId) throws Exception;

	/* @interface model: Showcase,Showcase */
	Showcase getShowcase(Long showcaseId) throws Exception;

	/* @interface model: */
	List<Showcase> getShowcases() throws Exception;

	/* @interface model: 通过cabinetId获取Showcase List */
	List<Showcase> getShowcasesByCabinetId(Long cabinetId) throws Exception;

	/* @interface model: 通过cabinetId获取同个橱窗里的最大Showcase sort */
	Integer getShowcaseMaxSortByCabinetId(Long cabinetId) throws Exception;

	void updateShowcaseSortByCabinetId(Showcase showcase) throws Exception;

	void showcaseUpSamll(Long cabinetId, int sort) throws Exception;

	void showcaseUpBig(Long showcaseId) throws Exception;

	Showcase getShowcaseByGoodsIdAndCabId(Long goodsId, Long cabId)
			throws Exception;

	void showcaseDownSamll(Long cabinetId, int sort) throws Exception;

	void showcaseDownBig(Long showcaseId) throws Exception;

	public Integer getShowcasesCount(long cabinetId) throws Exception;

	List<Showcase> getShowcasesPage(Map<String, String> conditions);

}
