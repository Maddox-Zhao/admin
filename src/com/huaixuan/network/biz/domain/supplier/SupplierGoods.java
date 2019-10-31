package com.huaixuan.network.biz.domain.supplier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



/**
 * @author Mr_Yang   2015-9-9 下午05:19:50
 * 供货商产品
 **/

public class SupplierGoods
{
	
	private Long id;		//
	private Long idFile;		//上传的excel文件
	private Long idSupply;		//供货商ID
	private String title;		//title
	private String brandName;		//品牌
	private String series;		//品名
	private String type;		//型号
	private String material;		//材质
	private String color;		//颜色
	private String style;		//风格
	private BigDecimal cost;		//供货商成本
	private BigDecimal retail;		//欧洲零售价
	private BigDecimal percent;		//折扣 eg:(0.83)
	private BigDecimal price;		//供货价
	private String currency;		//币种
	private Integer totalNum;		//总数量
	private String imgOriginal;		//默认商品原图
	private String imgLarge;		//默认商品大图路径
	private String imgMiddle;		//商品 中图默认路径
	private String imgSmall;		//默认商品小图 路径
	private Long isDelete;		//状态 是否删除 0-未删除 1-已删除
	private String supplierName; //供货商名称
	private Date gmtCreate;		//
	private String size; //大小
	private Date gmtModify;		//
	
	private List<SupplierGoodsSize> goodsSize;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getIdFile()
	{
		return idFile;
	}
	public void setIdFile(Long idFile)
	{
		this.idFile = idFile;
	}
	public Long getIdSupply()
	{
		return idSupply;
	}
	public void setIdSupply(Long idSupply)
	{
		this.idSupply = idSupply;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getBrandName()
	{
		return brandName;
	}
	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}
	public String getSeries()
	{
		return series;
	}
	public void setSeries(String series)
	{
		this.series = series;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getMaterial()
	{
		return material;
	}
	public void setMaterial(String material)
	{
		this.material = material;
	}
	public String getColor()
	{
		return color;
	}
	public void setColor(String color)
	{
		this.color = color;
	}
	public String getStyle()
	{
		return style;
	}
	public void setStyle(String style)
	{
		this.style = style;
	}
	public BigDecimal getCost()
	{
		return cost;
	}
	public void setCost(BigDecimal cost)
	{
		this.cost = cost;
	}
	public BigDecimal getRetail()
	{
		return retail;
	}
	public void setRetail(BigDecimal retail)
	{
		this.retail = retail;
	}
	public BigDecimal getPercent()
	{
		return percent;
	}
	public void setPercent(BigDecimal percent)
	{
		this.percent = percent;
	}
	public BigDecimal getPrice()
	{
		return price;
	}
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}
	public String getCurrency()
	{
		return currency;
	}
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	 
	public String getImgOriginal()
	{
		return imgOriginal;
	}
	public void setImgOriginal(String imgOriginal)
	{
		this.imgOriginal = imgOriginal;
	}
	public String getImgLarge()
	{
		return imgLarge;
	}
	public void setImgLarge(String imgLarge)
	{
		this.imgLarge = imgLarge;
	}
	public String getImgMiddle()
	{
		return imgMiddle;
	}
	public void setImgMiddle(String imgMiddle)
	{
		this.imgMiddle = imgMiddle;
	}
	public String getImgSmall()
	{
		return imgSmall;
	}
	public void setImgSmall(String imgSmall)
	{
		this.imgSmall = imgSmall;
	}
	public Long getIsDelete()
	{
		return isDelete;
	}
	public void setIsDelete(Long isDelete)
	{
		this.isDelete = isDelete;
	}
	public Date getGmtCreate()
	{
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate)
	{
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify()
	{
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify)
	{
		this.gmtModify = gmtModify;
	}
	public Integer getTotalNum()
	{
		return totalNum;
	}
	public void setTotalNum(Integer totalNum)
	{
		this.totalNum = totalNum;
	}
	public String getSupplierName()
	{
		return supplierName;
	}
	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	public List<SupplierGoodsSize> getGoodsSize()
	{
		return goodsSize;
	}
	public void setGoodsSize(List<SupplierGoodsSize> goodsSize)
	{
		this.goodsSize = goodsSize;
	}
	
	
	
	
	
	
}
 
