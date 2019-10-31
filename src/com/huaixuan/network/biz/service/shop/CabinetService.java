package com.huaixuan.network.biz.service.shop;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.query.QueryPage;

public interface CabinetService {
	public Long addCabinet(Cabinet cabinet) throws Exception;

	public void editCabinet(Cabinet cabinet) throws Exception;

	public void removeCabinet(Long cabinetId) throws Exception;

	public Cabinet getCabinet(Long cabinetId) throws Exception;

	public List<Cabinet> getCabinets() throws Exception;

	public List<Category> getCategorysList(int i) throws Exception;

	public Map<String, Object> getCabAndGoodsInfo() throws Exception;

	List<Goods> getCabGoodsByName(String name) throws Exception;

	public QueryPage showcaseGoods(String catCode, String goodsName,
			String goodsSn, double priceLow, double priceHigh, int currentPage,
			int pageSize, String selectSort, String isCutprice, Long goodsNumber)
			throws Exception;

	public Map<String, Object> getCabAndGoodInfoByName(String name)
			throws Exception;

	public Map<String, Object> getCabAndGoodsInfoByWholeSale() throws Exception;

	public Long addCabinet(Cabinet cabinet, List<MultipartFile> files) throws Exception;

    public void editCabinet(Cabinet cabinet, List<MultipartFile> files) throws Exception ;
}
