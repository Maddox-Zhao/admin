/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideGoodsYShangImge {

	private long id;
	private String skuId; //云尚的sku
	private String ourSku; //我们的sku
	private String yshangImage; //云尚的圖片地址
	
	private String imagePic;//图片的连接地址	
	private String gmtCreate; //图片插入时间
	private String gmtModify; //图片插入时间
	private String status;
	
	private String type;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getOurSku() {
		return ourSku;
	}
	public void setOurSku(String ourSku) {
		this.ourSku = ourSku;
	}
	public String getYshangImage() {
		return yshangImage;
	}
	public void setYshangImage(String yshangImage) {
		this.yshangImage = yshangImage;
	}
	public String getImagePic() {
		return imagePic;
	}
	public void setImagePic(String imagePic) {
		this.imagePic = imagePic;
	}
	public String getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(String gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
