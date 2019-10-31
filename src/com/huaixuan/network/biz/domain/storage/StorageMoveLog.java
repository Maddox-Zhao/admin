package com.huaixuan.network.biz.domain.storage;

 import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * �����Զ�����(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class StorageMoveLog extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = -8824229929185384013L;
		/* @property: */
	 private long id;
	 /* @property: */
     private String moveCode;
	 /* @property: */
	 private String creater;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private long oldStorageId;
	 /* @property: */
	 private long newStorageId;
	 /* @property: */
	 private long newDepId;
	 /* @property: */
	 private long newLocId;
	 /* @property: */
	 private long moveNum;

	 private long sumMoveNum;
	 /* @property: */
	 private String type;

	 private Date gmtReturn;

	 private String depType;

	 private Long returnNum;

	 private long sumReturnNum;

	 private long thisReturnNum;

	 private String memo;
	 /* @property: */
	private String status;

	// ��Ʒ����
	private String code;

	// ��Ʒ����
	private String instanceName;

	// ����
	private String attrs;

	// ��λ
	private String goodsUnit;

	// ��Ӧ������
    private String supplierName;

    //�ֿ�����
    private String depositoryName;

    //��λ����
    private String depLocationName;

    //ԭ�ֿ�����
    private String oldDepositoryName;

    //ԭ��λ����
    private String oldDepLocationName;

    //�ϲֿ�ID
    private long oldLocId;

    //�²ֿ��¼����
    private long newNum;

    //�µ�һ���ֿ�ID
    private Long depFirstId;

    //�µ�һ���ֿ�����
    private String             depFirstName;

    //�ϵ�һ���ֿ�ID
    private Long oldDepFirstId;

    //�ϵĵ�һ���ֿ�����
    private String oldDepFirstName;

	public Long getOldDepFirstId() {
		return oldDepFirstId;
	}
	public void setOldDepFirstId(Long oldDepFirstId) {
		this.oldDepFirstId = oldDepFirstId;
	}
	public String getOldDepFirstName() {
		return oldDepFirstName;
	}
	public void setOldDepFirstName(String oldDepFirstName) {
		this.oldDepFirstName = oldDepFirstName;
	}
	public String getDepFirstName() {
		return depFirstName;
	}
	public void setDepFirstName(String depFirstName) {
		this.depFirstName = depFirstName;
	}
	public Long getDepFirstId() {
		return depFirstId;
	}
	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}
	/**
     * @return the thisReturnNum
     */
    public long getThisReturnNum() {
        return thisReturnNum;
    }
    /**
     * @param thisReturnNum the thisReturnNum to set
     */
    public void setThisReturnNum(long thisReturnNum) {
        this.thisReturnNum = thisReturnNum;
    }
    /**
     * @return the returnNum
     */
    public Long getReturnNum() {
        return returnNum;
    }
    /**
     * @param returnNum the returnNum to set
     */
    public void setReturnNum(Long returnNum) {
        this.returnNum = returnNum;
    }
    /* Default constructor - creates a new instance with no values set. */
	 public StorageMoveLog(){}
	 /* @model:���� */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:��ȡ */
	 public long getId(){
		 return this.id;
	 }
	 /**
     * @return the moveCode
     */
    public String getMoveCode() {
        return moveCode;
    }
    /**
     * @param moveCode the moveCode to set
     */
    public void setMoveCode(String moveCode) {
        this.moveCode = moveCode;
    }
    /* @model:���� */
	 public void setCreater(String obj){
		 this.creater = obj;
	 }

	 /**
     * @return the oldLocId
     */
    public long getOldLocId() {
        return oldLocId;
    }
    /**
     * @param oldLocId the oldLocId to set
     */
    public void setOldLocId(long oldLocId) {
        this.oldLocId = oldLocId;
    }
    /* @model:��ȡ */
	 public String getCreater(){
		 return this.creater;
	 }
	 /* @model:���� */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:��ȡ */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:���� */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:��ȡ */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	 /* @model:���� */
	 public void setOldStorageId(long obj){
		 this.oldStorageId = obj;
	 }

	 /* @model:��ȡ */
	 public long getOldStorageId(){
		 return this.oldStorageId;
	 }
	 /* @model:���� */
	 public void setNewStorageId(long obj){
		 this.newStorageId = obj;
	 }

	 /* @model:��ȡ */
	 public long getNewStorageId(){
		 return this.newStorageId;
	 }
	 /* @model:���� */
	 public void setNewDepId(long obj){
		 this.newDepId = obj;
	 }

	 /* @model:��ȡ */
	 public long getNewDepId(){
		 return this.newDepId;
	 }
	 /* @model:���� */
	 public void setNewLocId(long obj){
		 this.newLocId = obj;
	 }

	 /* @model:��ȡ */
	 public long getNewLocId(){
		 return this.newLocId;
	 }
	 /* @model:���� */
	 public void setMoveNum(long obj){
		 this.moveNum = obj;
	 }

	 /* @model:��ȡ */
	 public long getMoveNum(){
		 return this.moveNum;
	 }
	 /* @model:���� */
	 public void setType(String obj){
		 this.type = obj;
	 }

	 /* @model:��ȡ */
	 public String getType(){
		 return this.type;
	 }

	 /* @model:���� */
	 public void setStatus(String obj){
		 this.status = obj;
	 }

	 /* @model:��ȡ */
	 public String getStatus(){
		 return this.status;
	 }

	/**
     * @return the attrs
     */
    public String getAttrs() {
        return attrs;
    }
    /**
     * @param attrs the attrs to set
     */
    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /**
     * @return the goodsUnit
     */
    public String getGoodsUnit() {
        return goodsUnit;
    }
    /**
     * @param goodsUnit the goodsUnit to set
     */
    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }
    /**
     * @return the gmtReturn
     */
    public Date getGmtReturn() {
        return gmtReturn;
    }
    /**
     * @param gmtReturn the gmtReturn to set
     */
    public void setGmtReturn(Date gmtReturn) {
        this.gmtReturn = gmtReturn;
    }
    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("creater", this.creater)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("oldStorageId", this.oldStorageId)
			 .append("newStorageId", this.newStorageId)
			 .append("newDepId", this.newDepId)
			 .append("newLocId", this.newLocId)
			 .append("moveNum", this.moveNum)
			 .append("type", this.type)
			 .append("status", this.status)
			 .append("gmtReturn", this.gmtReturn)
			 .append("code", this.code)
			 .append("thisReturnNum", this.thisReturnNum)
			 .append("instanceName", this.instanceName)
			 .append("returnNum", this.returnNum)
			 .append("supplierName", this.supplierName);
		 return sb.toString();
	 }


	public long getSumMoveNum() {
		return sumMoveNum;
	}
	public void setSumMoveNum(long sumMoveNum) {
		this.sumMoveNum = sumMoveNum;
	}
	public long getSumReturnNum() {
		return sumReturnNum;
	}
	public void setSumReturnNum(long sumReturnNum) {
		this.sumReturnNum = sumReturnNum;
	}
	public String getDepositoryName() {
		return depositoryName;
	}

	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}

	public String getDepLocationName() {
		return depLocationName;
	}

	public void setDepLocationName(String depLocationName) {
		this.depLocationName = depLocationName;
	}

	public String getOldDepositoryName() {
		return oldDepositoryName;
	}

	public void setOldDepositoryName(String oldDepositoryName) {
		this.oldDepositoryName = oldDepositoryName;
	}

	public String getOldDepLocationName() {
		return oldDepLocationName;
	}

	public void setOldDepLocationName(String oldDepLocationName) {
		this.oldDepLocationName = oldDepLocationName;
	}

	public long getNewNum() {
		return newNum;
	}

	public void setNewNum(long newNum) {
		this.newNum = newNum;
	}

	/**
     * @return getGmtCreate_str
     */
    public String getGmtCreate_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(this.getGmtCreate()!=null){
            return df.format(this.getGmtCreate());
        }else{
            return null;
        }
    }

    /**
     * @return getGmtCreate_str
     */
    public String getGmtReturn_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(this.getGmtReturn()!=null){
            return df.format(this.getGmtReturn());
        }else{
            return null;
        }
    }
	public String getDepType() {
		return depType;
	}
	public void setDepType(String depType) {
		this.depType = depType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
 }
