package com.huaixuan.network.biz.domain.autosyn;



/**
 * @author Mr_Yang   2015-12-2 下午04:11:55
 * 记录日志需要的数据
 **/

public class LogData
{
	private String fileName;
	private int beforNum;//之前库存
	private int nowNum;//更新后库存
	private String huohao; //货号
	private String platformSku; //平台sku
	private String sku;//我们的sku
	private String error; //错误信息 
	private String type; //sh hk
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public int getBeforNum()
	{
		return beforNum;
	}
	public void setBeforNum(int beforNum)
	{
		this.beforNum = beforNum;
	}
	public int getNowNum()
	{
		return nowNum;
	}
	public void setNowNum(int nowNum)
	{
		this.nowNum = nowNum;
	}
	public String getHuohao()
	{
		return huohao;
	}
	public void setHuohao(String huohao)
	{
		this.huohao = huohao;
	}
	public String getPlatformSku()
	{
		return platformSku;
	}
	public void setPlatformSku(String platformSku)
	{
		this.platformSku = platformSku;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getError()
	{
		return error;
	}
	public void setError(String error)
	{
		this.error = error;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	
	
	
}
 
