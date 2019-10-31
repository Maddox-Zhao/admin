package com.huaixuan.network.biz.domain.remote;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

import java.util.Date;

/**  
 * �����Զ����(bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public class InterfaceApply extends BaseObject implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = -5384735440213569619L;
    /* @property:���ID */
    private Long              id;
    /* @property:����ʱ�� */
    private Date              gmtCreate;
    /* @property:�޸�ʱ�� */
    private Date              gmtModify;
    /* @property:�û�ID */
    private Long              userId;
    private String            account;
    /* @property:���Ĳ���QQ�� */
    private String            paramOne;
    /* @property:���Ĳ���UID */
    private String            paramTwo;
    /* @property:���Ĳ���TOKEN */
    private String            paramThree;
    /* @property:���Ĳ���SPID */
    private String            paramFour;
    /* @property:����URL */
    private String            shopUrl;

    private String            shopType;
    /* @property:�˷�ģ��ID */
    private Long              postId;
    /* @property:ƽ�ʼ۸� */
    private double            postNormal;
    /* @property:EMS�۸� */
    private double            postEms;
    /* @property:��ݼ۸� */
    private double            postExpress;
    /* @property:״̬��new������ˣ�pass�����ͨ��fail����˲�ͨ�� */
    private String            status;
    /* @property:���ͣ�paipai:���Ľ��룻taobao:�Ա����� */
    private String            type;
    /* @property:��ע��Ϣ */
    private String            memo;
    /* @property:�������ͬ��ʱ�� */
    private Date              gmtSync;
    /* Default constructor - creates a new instance with no values set. */

    private Long              ownExpressId;
    private String            interfaceExpressCode;

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public Long getOwnExpressId() {
        return ownExpressId;
    }

    public void setOwnExpressId(Long ownExpressId) {
        this.ownExpressId = ownExpressId;
    }

    public String getInterfaceExpressCode() {
        return interfaceExpressCode;
    }

    public void setInterfaceExpressCode(String interfaceExpressCode) {
        this.interfaceExpressCode = interfaceExpressCode;
    }

    public InterfaceApply() {
    }

    /* @model:�������ID */
    public void setId(Long obj) {
        this.id = obj;
    }

    /* @model:��ȡ���ID */
    public Long getId() {
        return this.id;
    }

    /* @model:���ô���ʱ�� */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:��ȡ����ʱ�� */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:�����޸�ʱ�� */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model:��ȡ�޸�ʱ�� */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    /* @model:�����û�ID */
    public void setUserId(Long obj) {
        this.userId = obj;
    }

    /* @model:��ȡ�û�ID */
    public Long getUserId() {
        return this.userId;
    }

    /* @model:�������Ĳ���QQ�� */
    public void setParamOne(String obj) {
        this.paramOne = obj;
    }

    /* @model:��ȡ���Ĳ���QQ�� */
    public String getParamOne() {
        return this.paramOne;
    }

    /* @model:�������Ĳ���UID */
    public void setParamTwo(String obj) {
        this.paramTwo = obj;
    }

    /* @model:��ȡ���Ĳ���UID */
    public String getParamTwo() {
        return this.paramTwo;
    }

    /* @model:�������Ĳ���TOKEN */
    public void setParamThree(String obj) {
        this.paramThree = obj;
    }

    /* @model:��ȡ���Ĳ���TOKEN */
    public String getParamThree() {
        return this.paramThree;
    }

    /* @model:�������Ĳ���SPID */
    public void setParamFour(String obj) {
        this.paramFour = obj;
    }

    /* @model:��ȡ���Ĳ���SPID */
    public String getParamFour() {
        return this.paramFour;
    }

    /* @model:���õ���URL */
    public void setShopUrl(String obj) {
        this.shopUrl = obj;
    }

    /* @model:��ȡ����URL */
    public String getShopUrl() {
        return this.shopUrl;
    }

    /* @model:�����˷�ģ��ID */
    public void setPostId(Long obj) {
        this.postId = obj;
    }

    /* @model:��ȡ�˷�ģ��ID */
    public Long getPostId() {
        return this.postId;
    }

    /* @model:����ƽ�ʼ۸� */
    public void setPostNormal(double obj) {
        this.postNormal = obj;
    }

    /* @model:��ȡƽ�ʼ۸� */
    public double getPostNormal() {
        return this.postNormal;
    }

    /* @model:����EMS�۸� */
    public void setPostEms(double obj) {
        this.postEms = obj;
    }

    /* @model:��ȡEMS�۸� */
    public double getPostEms() {
        return this.postEms;
    }

    /* @model:���ÿ�ݼ۸� */
    public void setPostExpress(double obj) {
        this.postExpress = obj;
    }

    /* @model:��ȡ��ݼ۸� */
    public double getPostExpress() {
        return this.postExpress;
    }

    /* @model:����״̬��new������ˣ�pass�����ͨ��fail����˲�ͨ�� */
    public void setStatus(String obj) {
        this.status = obj;
    }

    /* @model:��ȡ״̬��new������ˣ�pass�����ͨ��fail����˲�ͨ�� */
    public String getStatus() {
        return this.status;
    }

    /* @model:�������ͣ�paipai:���Ľ��룻taobao:�Ա����� */
    public void setType(String obj) {
        this.type = obj;
    }

    /* @model:��ȡ���ͣ�paipai:���Ľ��룻taobao:�Ա����� */
    public String getType() {
        return this.type;
    }

    /* @model:���ñ�ע��Ϣ */
    public void setMemo(String obj) {
        this.memo = obj;
    }

    /* @model:��ȡ��ע��Ϣ */
    public String getMemo() {
        return this.memo;
    }

    /* @model:���ö������ͬ��ʱ�� */
    public void setGmtSync(Date obj) {
        this.gmtSync = obj;
    }

    /* @model:��ȡ�������ͬ��ʱ�� */
    public Date getGmtSync() {
        return this.gmtSync;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
