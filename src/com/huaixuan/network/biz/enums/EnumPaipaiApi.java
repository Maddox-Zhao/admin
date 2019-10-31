package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口链接
 * 
 * @author zhangwy
 */
public enum EnumPaipaiApi {
	getNavigationChildList(EnumPaipaiApi.ATTR,"查询发布导航子类目列表"),
	
	getAttributeList(EnumPaipaiApi.ATTR,"查询发布导航属性列表"),
	
	addItem(EnumPaipaiApi.ITEM,"发布商品"),
	
	modifyItemPic(EnumPaipaiApi.ITEM,"修改商品图片接口"),
	
	modifyItemStock(EnumPaipaiApi.ITEM,"修改商品库存或价格"),
	
	getItem(EnumPaipaiApi.ITEM,"查询单个商品详细信息"),
	
	modifyItemState(EnumPaipaiApi.ITEM,"批量修改商品状态");
	
	private static final String ATTR="attr";
	private static final String ITEM="item";
	
	private final String floder;
	private final String showInfo;
	
	private EnumPaipaiApi(String floder, String showInfo) {
		this.floder = floder;
		this.showInfo = showInfo;
	}

	public String getFloder() {
		return floder;
	}

	public String getShowInfo() {
		return showInfo;
	}
}
