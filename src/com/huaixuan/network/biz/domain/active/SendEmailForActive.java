package com.huaixuan.network.biz.domain.active;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Mr_Yang
 * @version ����ʱ�䣺2012-3-27 ����03:52:51
 * ����ͬ����е�ͬ���Ʒ��ͬһ���ͻ��в�ͬ�۸��ʱ�����ʼ���������
 */

public class SendEmailForActive
{
	private String type; //��Ʒ�ͺ�
	private String color; //��Ʒ��ɫ
	private String material; //��Ʒ����
	private Long productId; 
	private Set<Double> price; //��ͬ�ļ�Ǯ

	private List<String> moveFrameName = new ArrayList<String>(); 
	
	
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getColor()
	{
		return color;
	}
	public void setColor(String color)
	{
		this.color = color;
	}
	public String getMaterial()
	{
		return material;
	}
	public void setMaterial(String material)
	{
		this.material = material;
	}

	public Long getProductId()
	{
		return productId;
	}
	public void setProductId(Long productId)
	{
		this.productId = productId;
	}
	public Set<Double> getPrice()
	{
		return price;
	}
	public void setPrice(Set<Double> price)
	{
		this.price = price;
	}
	public List<String> getMoveFrameName()
	{
		return moveFrameName;
	}
	public void setMoveFrameName(List<String> moveFrameName)
	{
		this.moveFrameName = moveFrameName;
	}

	@Override
	public int hashCode()
	{
		return productId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SendEmailForActive other = (SendEmailForActive) obj;
		if(price != null && other.getPrice() != null && productId != null && other.getProductId() != null)
		{
			if(!price.equals(other.getPrice()) && productId.equals(other.getPrice()))
			{
				return true;
			}
		}
		return false;
		
	}
	

	
	
}
