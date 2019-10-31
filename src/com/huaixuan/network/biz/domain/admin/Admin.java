package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.enums.EnumAdminUserStatus;

public class Admin implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7415934939745514889L;
    private Long              id;
    private Date              gmtCreate;
    private Date              gmtModify;

    private String            userName;
    private String            password;
    /**
     * ȷ������
     */
    private String            confirmPassword;
    /**
     * ԭ����(�޸�����ʱ��)
     */
    private String            currentlypwd;

    /**
     * ����
     */
    private String            email;
    /**
     * ״̬
     */
    private Integer           status;
    /**
     * �ֻ�
     */
    private String            mobile;
    /**
     * �绰
     */
    private String            tel;
    /**
     * ����
     */
    private String            name;
    
    private String            userId;
    
    private String depCode; //���ű��

    private int clientrole;
    /*
     * һ���ֿ�Ȩ��
     */
    private String            depFirstId;
    
    private String           loginSystem; //��¼ϵͳ
    
    private String           loginTag; //��¼������Ϣ
    
    private Long 	functionRole; //C�ͻ���ְ��ID
    
    private Long  site;//站点
    
    private String searchIdSites; //该账号只能查询的站点(最多查询这些站点)
    
    private String type; //dx-代销账号 zy-直营账号 
    private String webDuty;//web端职务
    
    private String higerUserName;//直接上级
    
    
    private int idSite;    //用于页面显示站点名
    private String Sname;   //站点名
    private String depCodeName; //部门名称
    
    
    public String getDepCodeName() {
		return depCodeName;
	}

	public void setDepCodeName(String depCodeName) {
		this.depCodeName = depCodeName;
	}

	public int getIdSite() {
		return idSite;
	}

	public void setIdSite(int idSite) {
		this.idSite = idSite;
	}

	public String getSname() {
		return Sname;
	}

	public void setSname(String sname) {
		Sname = sname;
	}

	public String getLoginTag() {
		return loginTag;
	}

	public void setLoginTag(String loginTag) {
		this.loginTag = loginTag;
	}

	public String getLoginSystem() {
		return loginSystem;
	}

	public void setLoginSystem(String loginSystem) {
		this.loginSystem = loginSystem;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCurrentlypwd() {
        return currentlypwd;
    }

    public void setCurrentlypwd(String currentlypwd) {
        this.currentlypwd = currentlypwd;
    }
    
    

    public String getDepCode()
	{
		return depCode;
	}

	public void setDepCode(String depCode)
	{
		this.depCode = depCode;
	}

	public Long getFunctionRole()
	{
		return functionRole;
	}

	public void setFunctionRole(Long functionRole)
	{
		this.functionRole = functionRole;
	}

	/**
     * �жϵ�ǰ�û��Ƿ񱻶���
     * 
     * @return
     */
    public boolean isFreezing() {
        if (status == null) {
            return false;
        }
        return EnumAdminUserStatus.FREEZING.getValue() == status;

    }

    public String getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(String depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public Long getSite()
	{
		return site;
	}

	public void setSite(Long site)
	{
		this.site = site;
	}

	public String getWebDuty()
	{
		return webDuty;
	}

	public void setWebDuty(String webDuty)
	{
		this.webDuty = webDuty;
	}

	public String getSearchIdSites()
	{
		return searchIdSites;
	}

	public void setSearchIdSites(String searchIdSites)
	{
		this.searchIdSites = searchIdSites;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getClientrole() {
		return clientrole;
	}

	public void setClientrole(int clientrole) {
		
		this.clientrole = clientrole;
	}

	public String getHigerUserName()
	{
		return higerUserName;
	}

	public void setHigerUserName(String higerUserName)
	{
		this.higerUserName = higerUserName;
	}
	
	
    
    
    
    
    
    
}
