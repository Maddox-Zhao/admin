package com.huaixuan.network.biz.domain.user;

import java.util.Date;

public class AgentInfo {
	private static final long serialVersionUID = 8816648519597571350L;

	private long id;

	private Date gmtCreate;

	private Date gmtModify;

	private long userId;

	private int ticketLeft;

	private int ticketUsed;

	private Date gmtApproved;

	private String status;

	private String memo;

	private String realName;

	private int sex;

	private String telNumber;

	private String chatNumber;

	private String chatType;

	private String province;

	private String city;

	private String address;

	private String zip;

	private String taobaoUser;

	private String taobaoShop;

	private String taobaoPrestige;

	private String taobaoMainBusiness;

	private String paipaiUser;

	private String paipaiShop;

	private String paipaiPrestige;

	private String paipaiMainBusiness;

	private String youaUser;

	private String youaShop;

	private String youaPrestige;

	private String youaMainBusiness;

	private String ebayUser;

	private String ebayShop;

	private String ebayPrestige;

	private String ebayMainBusiness;

	private String otherUser;

	private String otherShop;

	private String otherMainBusiness;

	private String entityName;

	private String entityAddress;

	private String entityMainBusiness;

	private String cashStatus;

	private Date gmtCashReceived;

	private String cashMemoAgent;
	
	private String cashMemoXilang;
	
	private int cashTicket;
	
	private String selfshopName;

	private String selfshopAddress;

	private String selfshopMainBusiness;
	
	/*
	 * 返点可兑换的金额
	 */
	private Double cashTotal;
	
	private Long agent_manager_id;
	
	//added by chenyan 20100909 start
    private Date gmtDistribute; //分配维护人员日期
    
    private String channelMemo; //渠道信息备注
    
    private String connectMemo; //联系方式备注
    
    private String mainMemo; //主营类别备注
    
    private String isFulltime; //是否全职：y：全职；n：兼职
    
    public Date getGmtDistribute() {
        return gmtDistribute;
    }

    public void setGmtDistribute(Date gmtDistribute) {
        this.gmtDistribute = gmtDistribute;
    }

    public String getChannelMemo() {
        return channelMemo;
    }

    public void setChannelMemo(String channelMemo) {
        this.channelMemo = channelMemo;
    }

    public String getConnectMemo() {
        return connectMemo;
    }

    public void setConnectMemo(String connectMemo) {
        this.connectMemo = connectMemo;
    }

    public String getMainMemo() {
        return mainMemo;
    }

    public void setMainMemo(String mainMemo) {
        this.mainMemo = mainMemo;
    }

    public String getIsFulltime() {
        return isFulltime;
    }

    public void setIsFulltime(String isFulltime) {
        this.isFulltime = isFulltime;
    }
    //added by chenyan 20100909 end
    
	public Long getAgent_manager_id() {
        return agent_manager_id;
    }

    public void setAgent_manager_id(Long agent_manager_id) {
        this.agent_manager_id = agent_manager_id;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getTicketLeft() {
		return ticketLeft;
	}

	public void setTicketLeft(int ticketLeft) {
		this.ticketLeft = ticketLeft;
	}

	public int getTicketUsed() {
		return ticketUsed;
	}

	public void setTicketUsed(int ticketUsed) {
		this.ticketUsed = ticketUsed;
	}

	public Date getGmtApproved() {
		return gmtApproved;
	}

	public void setGmtApproved(Date gmtApproved) {
		this.gmtApproved = gmtApproved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getChatNumber() {
		return chatNumber;
	}

	public void setChatNumber(String chatNumber) {
		this.chatNumber = chatNumber;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTaobaoUser() {
		return taobaoUser;
	}

	public void setTaobaoUser(String taobaoUser) {
		this.taobaoUser = taobaoUser;
	}

	public String getTaobaoShop() {
		return taobaoShop;
	}

	public void setTaobaoShop(String taobaoShop) {
		this.taobaoShop = taobaoShop;
	}

	public String getTaobaoPrestige() {
		return taobaoPrestige;
	}

	public void setTaobaoPrestige(String taobaoPrestige) {
		this.taobaoPrestige = taobaoPrestige;
	}

	public String getTaobaoMainBusiness() {
		return taobaoMainBusiness;
	}

	public void setTaobaoMainBusiness(String taobaoMainBusiness) {
		this.taobaoMainBusiness = taobaoMainBusiness;
	}

	public String getPaipaiUser() {
		return paipaiUser;
	}

	public void setPaipaiUser(String paipaiUser) {
		this.paipaiUser = paipaiUser;
	}

	public String getPaipaiShop() {
		return paipaiShop;
	}

	public void setPaipaiShop(String paipaiShop) {
		this.paipaiShop = paipaiShop;
	}

	public String getPaipaiPrestige() {
		return paipaiPrestige;
	}

	public void setPaipaiPrestige(String paipaiPrestige) {
		this.paipaiPrestige = paipaiPrestige;
	}

	public String getPaipaiMainBusiness() {
		return paipaiMainBusiness;
	}

	public void setPaipaiMainBusiness(String paipaiMainBusiness) {
		this.paipaiMainBusiness = paipaiMainBusiness;
	}

	public String getYouaUser() {
		return youaUser;
	}

	public void setYouaUser(String youaUser) {
		this.youaUser = youaUser;
	}

	public String getYouaShop() {
		return youaShop;
	}

	public void setYouaShop(String youaShop) {
		this.youaShop = youaShop;
	}

	public String getYouaPrestige() {
		return youaPrestige;
	}

	public void setYouaPrestige(String youaPrestige) {
		this.youaPrestige = youaPrestige;
	}

	public String getYouaMainBusiness() {
		return youaMainBusiness;
	}

	public void setYouaMainBusiness(String youaMainBusiness) {
		this.youaMainBusiness = youaMainBusiness;
	}

	public String getEbayUser() {
		return ebayUser;
	}

	public void setEbayUser(String ebayUser) {
		this.ebayUser = ebayUser;
	}

	public String getEbayShop() {
		return ebayShop;
	}

	public void setEbayShop(String ebayShop) {
		this.ebayShop = ebayShop;
	}

	public String getEbayPrestige() {
		return ebayPrestige;
	}

	public void setEbayPrestige(String ebayPrestige) {
		this.ebayPrestige = ebayPrestige;
	}

	public String getEbayMainBusiness() {
		return ebayMainBusiness;
	}

	public void setEbayMainBusiness(String ebayMainBusiness) {
		this.ebayMainBusiness = ebayMainBusiness;
	}

	public String getOtherUser() {
		return otherUser;
	}

	public void setOtherUser(String otherUser) {
		this.otherUser = otherUser;
	}

	public String getOtherShop() {
		return otherShop;
	}

	public void setOtherShop(String otherShop) {
		this.otherShop = otherShop;
	}

	public String getOtherMainBusiness() {
		return otherMainBusiness;
	}

	public void setOtherMainBusiness(String otherMainBusiness) {
		this.otherMainBusiness = otherMainBusiness;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityAddress() {
		return entityAddress;
	}

	public void setEntityAddress(String entityAddress) {
		this.entityAddress = entityAddress;
	}

	public String getEntityMainBusiness() {
		return entityMainBusiness;
	}

	public void setEntityMainBusiness(String entityMainBusiness) {
		this.entityMainBusiness = entityMainBusiness;
	}

	public String getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
	}

	public Date getGmtCashReceived() {
		return gmtCashReceived;
	}

	public void setGmtCashReceived(Date gmtCashReceived) {
		this.gmtCashReceived = gmtCashReceived;
	}

	public String getCashMemoAgent() {
		return cashMemoAgent;
	}

	public void setCashMemoAgent(String cashMemoAgent) {
		this.cashMemoAgent = cashMemoAgent;
	}

	public String getCashMemoXilang() {
		return cashMemoXilang;
	}

	public void setCashMemoXilang(String cashMemoXilang) {
		this.cashMemoXilang = cashMemoXilang;
	}

	public int getCashTicket() {
		return cashTicket;
	}

	public void setCashTicket(int cashTicket) {
		this.cashTicket = cashTicket;
	}

	public Double getCashTotal() {
		return cashTotal;
	}

	public void setCashTotal(Double cashTotal) {
		this.cashTotal = cashTotal;
	}

	public String getSelfshopName() {
		return selfshopName;
	}

	public void setSelfshopName(String selfshopName) {
		this.selfshopName = selfshopName;
	}

	public String getSelfshopAddress() {
		return selfshopAddress;
	}

	public void setSelfshopAddress(String selfshopAddress) {
		this.selfshopAddress = selfshopAddress;
	}

	public String getSelfshopMainBusiness() {
		return selfshopMainBusiness;
	}

	public void setSelfshopMainBusiness(String selfshopMainBusiness) {
		this.selfshopMainBusiness = selfshopMainBusiness;
	}
}
