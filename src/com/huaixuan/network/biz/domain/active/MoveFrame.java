package com.huaixuan.network.biz.domain.active;

import java.util.Date;

/**
 *2012-6-19 ����10:47:12
 *Mr_Yang
 */
public class MoveFrame
{
	private Long id;		//
	private String name;		//�������
	private String status;		//״̬ Ĭ�ϡ�open����������closed���ѽ�������wait��δ��ʼ
	private Long area;		//�����1-��ʾ��½��2-��ʾ���
	private Long headAdminId;		//������
	private String note;		//��ע
	private String depCodes;		//����code���������code�� ��;�������
	private String depNames;		//�������ƣ�������������� ��;�������
	private String adminIds;		//Ա��id�����Ա��id�� ��;�������
	private String adminNames;		//
	private String customerIds;		//�ͻ�id������ͻ�id�� ��;�������
	private Double customerRate;		//�ͻ��ۿ��ʣ���ӿͻ�ʱ�����ۿ��ʣ�
	private Long customerType;		//�ͻ�����
	private Double amount;		//��Ʒ�ܽ��
	private Double discountAmount;		//��Ʒ�ۺ��ܽ��
	private Double soldAmount;		//��ǰ���۽��
	private Double frameAmount;		//����۽��
	private Long productNum;		//��Ʒ����
	private Long soldProduct;		//��ǰ��������
	private Long frameProduct;		//���������
	private Long idcurrency;		//���֣�Ԥ����
	private Date gmtCreate;		//����ʱ��
	private Date gmtModify;		//�޸�ʱ��
	private Date gmtStart;		//���ʼʱ��
	private Date gmtEnd;		//�����ʱ��
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Long getArea()
	{
		return area;
	}
	public void setArea(Long area)
	{
		this.area = area;
	}
	public Long getHeadAdminId()
	{
		return headAdminId;
	}
	public void setHeadAdminId(Long headAdminId)
	{
		this.headAdminId = headAdminId;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getDepCodes()
	{
		return depCodes;
	}
	public void setDepCodes(String depCodes)
	{
		this.depCodes = depCodes;
	}
	public String getDepNames()
	{
		return depNames;
	}
	public void setDepNames(String depNames)
	{
		this.depNames = depNames;
	}
	public String getAdminIds()
	{
		return adminIds;
	}
	public void setAdminIds(String adminIds)
	{
		this.adminIds = adminIds;
	}
	public String getAdminNames()
	{
		return adminNames;
	}
	public void setAdminNames(String adminNames)
	{
		this.adminNames = adminNames;
	}
	public String getCustomerIds()
	{
		return customerIds;
	}
	public void setCustomerIds(String customerIds)
	{
		this.customerIds = customerIds;
	}
	public Double getCustomerRate()
	{
		return customerRate;
	}
	public void setCustomerRate(Double customerRate)
	{
		this.customerRate = customerRate;
	}
	public Long getCustomerType()
	{
		return customerType;
	}
	public void setCustomerType(Long customerType)
	{
		this.customerType = customerType;
	}
	public Double getAmount()
	{
		return amount;
	}
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}
	public Double getDiscountAmount()
	{
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount)
	{
		this.discountAmount = discountAmount;
	}
	public Double getSoldAmount()
	{
		return soldAmount;
	}
	public void setSoldAmount(Double soldAmount)
	{
		this.soldAmount = soldAmount;
	}
	public Double getFrameAmount()
	{
		return frameAmount;
	}
	public void setFrameAmount(Double frameAmount)
	{
		this.frameAmount = frameAmount;
	}
	public Long getProductNum()
	{
		return productNum;
	}
	public void setProductNum(Long productNum)
	{
		this.productNum = productNum;
	}
	public Long getSoldProduct()
	{
		return soldProduct;
	}
	public void setSoldProduct(Long soldProduct)
	{
		this.soldProduct = soldProduct;
	}
	public Long getFrameProduct()
	{
		return frameProduct;
	}
	public void setFrameProduct(Long frameProduct)
	{
		this.frameProduct = frameProduct;
	}
	public Long getIdcurrency()
	{
		return idcurrency;
	}
	public void setIdcurrency(Long idcurrency)
	{
		this.idcurrency = idcurrency;
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
	public Date getGmtStart()
	{
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart)
	{
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd()
	{
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd)
	{
		this.gmtEnd = gmtEnd;
	}
	
	

}
