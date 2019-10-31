package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;
import java.math.BigInteger;

import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.hundsun.network.melody.common.web.cookyjar.SelfDependence;
import com.hundsun.network.melody.common.web.cookyjar.util.SelfUtil;

public class AdminAgent implements Serializable, SelfDependence{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7334058410889621278L;

	private Long              id;  //id
    
    private String            username; //
    
    private String            password; //

    private Integer           status;  
    
    private String            loginSystem; // 
    
    /**
     *   
     */
    private BigInteger        depfirstIds; // 
    
    private String siteName ; //站点名称
    private Long siteId;     //站点ID
    private String name; //姓名
    private String dutyIds; //web端职务(可能有多个51,21)
    private String searchIdSites; //该账号只能查询的站点(最多查询这些站点)
    private String type; //dx-代销账号 zy-直营账号
    
    
    /**
     *  ��2����λ��¼����Ա�û���Ȩ��
     */
    private BigInteger         permissions; // 
    
    public void setPermissions(int pos) {
        if (this.permissions == null) {
            this.permissions = new BigInteger("0");
        }
        this.permissions = this.permissions.setBit(pos);
    }
    
    public void setDepfirstIds(int pos) {
        if (this.depfirstIds == null) {
            this.depfirstIds = new BigInteger("0");
        }
        this.depfirstIds = this.depfirstIds.setBit(pos);
    }
    
    public boolean havePermission(EnumAdminPermission permission) {
        return this.permissions.testBit(permission.ordinal());
    }
    
    public String lieDown() {
        return SelfUtil.format(Long.toString(id), username, password, Integer.toString(status), loginSystem, depfirstIds.toString(36), permissions.toString(36),siteName,Long.toString(siteId),name,dutyIds,searchIdSites,type);
    }

    public SelfDependence riseUp(String value) {

    	
        String[] values = SelfUtil.recover(value);

        this.id = Long.parseLong(values[0]);
        this.username = values[1];
        this.password = values[2];
        this.status = Integer.parseInt(values[3]);
        this.loginSystem = values[4];
        this.depfirstIds = new BigInteger(values[5], 36);
        this.permissions = new BigInteger(values[6], 36);
        this.siteName = values[7];
        this.siteId = Long.valueOf(values[8]);
        this.name = values[9];
        this.dutyIds = values[10];
        this.searchIdSites = values[11];
        this.type = values[12];
        return this;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLoginSystem() {
		return loginSystem;
	}

	public void setLoginSystem(String loginSystem) {
		this.loginSystem = loginSystem;
	}
	
    public BigInteger getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(BigInteger depfirstIds) {
		this.depfirstIds = depfirstIds;
	}

	public BigInteger getPermissions() {
        return permissions;
    }

    public void setPermissions(BigInteger permissions) {
        this.permissions = permissions;
    }

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public Long getSiteId()
	{
		return siteId;
	}

	public void setSiteId(Long siteId)
	{
		this.siteId = siteId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDutyIds()
	{
		return dutyIds;
	}

	public void setDutyIds(String dutyIds)
	{
		this.dutyIds = dutyIds;
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
	
	
	
	
    

}
