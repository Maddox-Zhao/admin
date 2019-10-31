package com.huaixuan.network.biz.dao.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.shop.Cabinet;

public interface CabinetDao {
	Long addCabinet(Cabinet cabinet) throws Exception;

	void editCabinet(Cabinet cabinet) throws Exception;

	void removeCabinet(Long cabinetId) throws Exception;

	Cabinet getCabinet(Long cabinetId) throws Exception;

	List<Cabinet> getCabinets() throws Exception;

	Integer getCabinetMaxSort() throws Exception;

	void updateCabinetSortUpdtae(int maxid, int maxid2) throws Exception;

	void updateCabinetSortlow(int maxid, int maxid2) throws Exception;

	/**
	 * 获取有效橱窗 在前台依次展现
	 *
	 * @return
	 * @throws Exception
	 */
	List<Cabinet> getCabinetForSort() throws Exception;

	int getGoodsAmountOfTheCabinet(Long id) throws Exception;

	Cabinet getCabinetByName(String name) throws Exception;

	List<Goods> getCabGoodsByName(String name);
}
