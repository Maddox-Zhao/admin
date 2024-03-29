package com.huaixuan.network.biz.domain.hx;

import java.math.BigDecimal;
import java.util.Date;

public class Acquisition
{
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private Long id;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.acq_code
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private String acpCode;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.status
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private String status;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.note
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private String note;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.operator_id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private Long operatorId;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.amount
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private BigDecimal amount;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.paid_amount
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private BigDecimal paidAmount;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.customer_id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private Integer customerId;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.idcards
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private Long idcards;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.idcards_image
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private String idcardsImage;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.acq_image
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private String acqImage;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.gmt_create
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private Date gmtCreate;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column hx_acquisition.gmt_modify
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	private Date gmtModify;

	private Date dateStart;
	private Date dateEnd;

	public Date getDateStart()
	{
		return dateStart;
	}

	public void setDateStart(Date dateStart)
	{
		this.dateStart = dateStart;
	}

	public Date getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.id
	 * 
	 * @return the value of hx_acquisition.id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.id
	 * 
	 * @param id
	 *            the value for hx_acquisition.id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.acq_code
	 * 
	 * @return the value of hx_acquisition.acq_code
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public String getAcpCode()
	{
		return acpCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.acq_code
	 * 
	 * @param acpCode
	 *            the value for hx_acquisition.acq_code
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setAcpCode(String acpCode)
	{
		this.acpCode = acpCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.status
	 * 
	 * @return the value of hx_acquisition.status
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.status
	 * 
	 * @param status
	 *            the value for hx_acquisition.status
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.note
	 * 
	 * @return the value of hx_acquisition.note
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public String getNote()
	{
		return note;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.note
	 * 
	 * @param note
	 *            the value for hx_acquisition.note
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setNote(String note)
	{
		this.note = note;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.operator_id
	 * 
	 * @return the value of hx_acquisition.operator_id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public Long getOperatorId()
	{
		return operatorId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.operator_id
	 * 
	 * @param operatorId
	 *            the value for hx_acquisition.operator_id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setOperatorId(Long operatorId)
	{
		this.operatorId = operatorId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.amount
	 * 
	 * @return the value of hx_acquisition.amount
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.amount
	 * 
	 * @param amount
	 *            the value for hx_acquisition.amount
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.paid_amount
	 * 
	 * @return the value of hx_acquisition.paid_amount
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public BigDecimal getPaidAmount()
	{
		return paidAmount;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.paid_amount
	 * 
	 * @param paidAmount
	 *            the value for hx_acquisition.paid_amount
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setPaidAmount(BigDecimal paidAmount)
	{
		this.paidAmount = paidAmount;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.customer_id
	 * 
	 * @return the value of hx_acquisition.customer_id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public Integer getCustomerId()
	{
		return customerId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.customer_id
	 * 
	 * @param customerId
	 *            the value for hx_acquisition.customer_id
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setCustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.idcards
	 * 
	 * @return the value of hx_acquisition.idcards
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public Long getIdcards()
	{
		return idcards;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.idcards
	 * 
	 * @param idcards
	 *            the value for hx_acquisition.idcards
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setIdcards(Long idcards)
	{
		this.idcards = idcards;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.idcards_image
	 * 
	 * @return the value of hx_acquisition.idcards_image
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public String getIdcardsImage()
	{
		return idcardsImage;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.idcards_image
	 * 
	 * @param idcardsImage
	 *            the value for hx_acquisition.idcards_image
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setIdcardsImage(String idcardsImage)
	{
		this.idcardsImage = idcardsImage;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.acq_image
	 * 
	 * @return the value of hx_acquisition.acq_image
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public String getAcqImage()
	{
		return acqImage;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.acq_image
	 * 
	 * @param acqImage
	 *            the value for hx_acquisition.acq_image
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setAcqImage(String acqImage)
	{
		this.acqImage = acqImage;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.gmt_create
	 * 
	 * @return the value of hx_acquisition.gmt_create
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public Date getGmtCreate()
	{
		return gmtCreate;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.gmt_create
	 * 
	 * @param gmtCreate
	 *            the value for hx_acquisition.gmt_create
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setGmtCreate(Date gmtCreate)
	{
		this.gmtCreate = gmtCreate;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column hx_acquisition.gmt_modify
	 * 
	 * @return the value of hx_acquisition.gmt_modify
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public Date getGmtModify()
	{
		return gmtModify;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column hx_acquisition.gmt_modify
	 * 
	 * @param gmtModify
	 *            the value for hx_acquisition.gmt_modify
	 * 
	 * @abatorgenerated Thu Mar 01 17:31:47 CST 2012
	 */
	public void setGmtModify(Date gmtModify)
	{
		this.gmtModify = gmtModify;
	}
}