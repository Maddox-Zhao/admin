package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-5-11 下午05:50:42
 **/

public class StockNumber
{
	private String ourSku;
	private String platformSku;
	private int canSaleNum;
	private int totalNum;
	private int freezeNum;
	 
	public String getOurSku()
	{
		return ourSku;
	}
	public void setOurSku(String ourSku)
	{
		this.ourSku = ourSku;
	}
	public String getPlatformSku()
	{
		return platformSku;
	}
	public void setPlatformSku(String platformSku)
	{
		this.platformSku = platformSku;
	}
	public int getCanSaleNum()
	{
		return canSaleNum;
	}
	public void setCanSaleNum(int canSaleNum)
	{
		this.canSaleNum = canSaleNum;
	}
	public int getTotalNum()
	{
		return totalNum;
	}
	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
	}
	public int getFreezeNum()
	{
		return freezeNum;
	}
	public void setFreezeNum(int freezeNum)
	{
		this.freezeNum = freezeNum;
	}
	@Override
	public String toString()
	{
		return "psku:"+platformSku + " oursku:"+ourSku + " canSaleNum:"+canSaleNum + " freezeNum:" + freezeNum + " totalNum:" + totalNum;
	}
	
	
}
 
