/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideGoodsImge {

	private long id;
	
	private String prodId; //银泰西有的sku
	private String ssProid; //我们的sku
	
	private String imagePic;//图片的连接地址
	private String xiyouImage; //銀泰西有的圖片
	private String type; //图片的类型。type=1,主图；type=2,非主图；type=3,详情图
	
	private String gmtCreate; //图片插入时间
	private String gmtModify; //图片插入时间
	
	private String status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getSsProid() {
		return ssProid;
	}
	public void setSsProid(String ssProid) {
		this.ssProid = ssProid;
	}
	public String getImagePic() {
		return imagePic;
	}
	public void setImagePic(String imagePic) {
		this.imagePic = imagePic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getXiyouImage() {
		return xiyouImage;
	}
	public void setXiyouImage(String xiyouImage) {
		this.xiyouImage = xiyouImage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
