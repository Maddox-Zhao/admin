/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

import java.math.BigDecimal;

/**
 * @author TT
 * 只用于云尚的传递参数
 */
public class OrderItemDtoChild {

	
	private Integer productNum; //身份证号码
	private String productPrice;  //第三方商品销售单价
	private String productId;   //云尚互联商编
     private String shopCoupon; //店铺承担优惠金额（云尚互联承  担）
	
	private String platformCoupon; //第三方承担优惠金额


	public Integer getProductNum() {
		return productNum;
	}

	

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}



	


	public String getPlatformCoupon() {
		return platformCoupon;
	}



	public void setPlatformCoupon(String platformCoupon) {
		this.platformCoupon = platformCoupon;
	}



	public String getProductPrice() {
		return productPrice;
	}



	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}



	public String getShopCoupon() {
		return shopCoupon;
	}



	public void setShopCoupon(String shopCoupon) {
		this.shopCoupon = shopCoupon;
	}



	



	
}
