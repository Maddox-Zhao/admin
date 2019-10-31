package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-5-10 下午04:51:04
 * 批量修改平台sku和本地sku对应关系
 **/

public class PlatformSku2LocationSku
{
	private String platformSku;
	private String ourSku;
	private String type;    
	private String platformField;   
	private String itemid;
	private String product_id;
	
	private String platformstatus;
	private String status;
	public String getPlatformSku()
	{
		return platformSku;
	}
	public void setPlatformSku(String platformSku)
	{
		this.platformSku = platformSku;
	}
	public String getOurSku()
	{
		return ourSku;
	}
	public void setOurSku(String ourSku)
	{
		this.ourSku = ourSku;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getPlatformField()
	{
		return platformField;
	}
	public void setPlatformField(String platformField)
	{
		this.platformField = platformField;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlatformstatus() {
		return platformstatus;
	}
	public void setPlatformstatus(String platformstatus) {
		this.platformstatus = platformstatus;
	}
	


	
}
 
