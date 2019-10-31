package com.huaixuan.network.biz.domain.user;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class User {
	    private static final long  serialVersionUID = 3832626162173359411L;

	    public static final String UserInSession    = "emall_user_in_http_session";

	    public static final String USER_TYPE        = "p";                         // �û�����

	    public static final String AGENT_STATUS     = "d";                         // ��������

	    private Long               id;
	    private String             account;                                        // required
	    private String             password;                                       // required
	    private String             confirmPassword;
	    private String             passwordHint;
	    private String             firstName;                                      // required
	    private String             lastName;                                       // required
	    private String             email;                                          // required; unique
	    private String             website;
	    private Integer            sex;
	    private Long 				 idCustomer ;
	    /* @property: */
	    private Integer            visitCount;
	    /* @property: */
	    private String             userRank;
	    /* @property: */
	    private Date               gmtCreate;
	    /* @property: */
	    private Date               gmtLast;
	    /* @property: */
	    private String             ipLast;
	    /* @property: */
	    private Integer            stauts;

	    private String              agentManagerId;                                 // ��չ��ԱID
	    private String             agentManagerName;                               // ��չ��Ա���
	    private String             isHasAgentManager;                              // �Ƿ�����չ��Ա�Ĳ�ѯ����
	    private String		       isScrap;                                        // �Ƿ����
	    private String             catCode;                                        // ��Ӫ��Ŀ

	    private String             taobaoMainBusiness;
	    private String             paipaiMainBusiness;
	    private String             youaMainBusiness;
	    private String             ebayMainBusiness;
	    private String             otherMainBusiness;
	    private String             entityMainBusiness;
	    private String             selfshopMainBusiness;

	    /* @property: */
	    private Integer            isValidated;

	    private Address            address          = new Address();
	    private Integer            version;
	    private String             nickname;
	    private String             currentlypwd;
	    private String             fullName;
	    private String             realName;                                       // ��ʵ����
	    private String             gmtCreateMd5;                                   // ��ʱ�����

	    private Long               referenceId;                                    // �Ƽ���id

	    private String             type;                                           // �����Ա�ж�����
	    private String             apply_stauts;                                   // ����״̬

	    // �����Ա��������״̬
	    private String             cashStatus;
	    //���������޶�
	    private Long               agentCount;
	    private String             payPassword;                                    // required
	    private String             confirmPayPassword;
	    
	    private Double             salesCount;                                     //�����û����۽��
	    private String             vipRemark;                                      //VIP���˵��
	    private String             isPeriodPay;                                    //�Ƿ�����ڽ��㣨n:���ɣ�Ĭ�ϣ���y:���ԣ�
	    private Date               gmtPeriodPayStart;	                           //���ڽ��㿪ʼʱ�䣨Ĭ�ϳ�0ʱ0�֣�
	    private String             gmtPeriodPayStart_str;	                       //���ڽ��㿪ʼʱ���ַ�
	    private Date               gmtPeriodPayEnd;	                               //���ڽ������ʱ�䣨Ĭ�ϳ�23��0�֣�
	    private Integer            period;				                           //���ڣ��죩
	    private Integer            ycperiod;				                       //�ӳ٣��죩
	    private Double             periodAmountMax;	                               //�����ڽ���������
	    private Double             periodAmountNow;	                               //��ǰ������δ���㶩�������ڽ��㶩����
	    private String             isPaipai;                            //��ǰ������δ���㶩�������ڽ��㶩����
	    private String             isTaobao;
	    private String 			   invoice;                                       //��Ʊ
	    private UserAddress userAddress;
	    private UserInfo userInfo;
	    private String depCode;
	    
	    
	    
	    
	    private String name;
	    private String phone;
	    private String useraddress;
	    private String integral;
	    

	    
	    public UserInfo getUserInfo()
		{
			return userInfo;
		}

		public void setUserInfo(UserInfo userInfo)
		{
			this.userInfo = userInfo;
		}

		public String getInvoice() {
			return invoice;
		}

		public void setInvoice(String invoice) {
			this.invoice = invoice;
		}

		public String getIsPaipai() {
			return isPaipai;
		}

		public void setIsPaipai(String isPaipai) {
			this.isPaipai = isPaipai;
		}

		public String getTaobaoMainBusiness() {
			return taobaoMainBusiness;
		}

		public void setTaobaoMainBusiness(String taobaoMainBusiness) {
			this.taobaoMainBusiness = taobaoMainBusiness;
		}

		public String getPaipaiMainBusiness() {
			return paipaiMainBusiness;
		}

		public void setPaipaiMainBusiness(String paipaiMainBusiness) {
			this.paipaiMainBusiness = paipaiMainBusiness;
		}

		public String getYouaMainBusiness() {
			return youaMainBusiness;
		}

		public void setYouaMainBusiness(String youaMainBusiness) {
			this.youaMainBusiness = youaMainBusiness;
		}

		public String getEbayMainBusiness() {
			return ebayMainBusiness;
		}

		public void setEbayMainBusiness(String ebayMainBusiness) {
			this.ebayMainBusiness = ebayMainBusiness;
		}

		public String getOtherMainBusiness() {
			return otherMainBusiness;
		}

		public void setOtherMainBusiness(String otherMainBusiness) {
			this.otherMainBusiness = otherMainBusiness;
		}

		public String getEntityMainBusiness() {
			return entityMainBusiness;
		}

		public void setEntityMainBusiness(String entityMainBusiness) {
			this.entityMainBusiness = entityMainBusiness;
		}

		public String getSelfshopMainBusiness() {
			return selfshopMainBusiness;
		}

		public void setSelfshopMainBusiness(String selfshopMainBusiness) {
			this.selfshopMainBusiness = selfshopMainBusiness;
		}

		public String getCatCode() {
			return catCode;
		}

		public void setCatCode(String catCode) {
			this.catCode = catCode;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getIsHasAgentManager() {
			return isHasAgentManager;
		}

		public void setIsHasAgentManager(String isHasAgentManager) {
			this.isHasAgentManager = isHasAgentManager;
		}

		public String getIsPeriodPay() {
			return isPeriodPay;
		}

		public void setIsPeriodPay(String isPeriodPay) {
			this.isPeriodPay = isPeriodPay;
		}

		public Date getGmtPeriodPayStart() {
			return gmtPeriodPayStart;
		}

		public void setGmtPeriodPayStart(Date gmtPeriodPayStart) {
			this.gmtPeriodPayStart = gmtPeriodPayStart;
		}

		public Date getGmtPeriodPayEnd() {
			return gmtPeriodPayEnd;
		}

		public void setGmtPeriodPayEnd(Date gmtPeriodPayEnd) {
			this.gmtPeriodPayEnd = gmtPeriodPayEnd;
		}


		public String getIsScrap() {
			return isScrap;
		}

		public void setIsScrap(String isScrap) {
			this.isScrap = isScrap;
		}

		public Integer getPeriod() {
			return period;
		}

		public void setPeriod(Integer period) {
			this.period = period;
		}

		public Double getPeriodAmountMax() {
			return periodAmountMax;
		}

		public void setPeriodAmountMax(Double periodAmountMax) {
			this.periodAmountMax = periodAmountMax;
		}

		public Double getPeriodAmountNow() {
			return periodAmountNow;
		}

		public void setPeriodAmountNow(Double periodAmountNow) {
			this.periodAmountNow = periodAmountNow;
		}

		public String getCashStatus() {
	        return cashStatus;
	    }

	    public void setCashStatus(String cashStatus) {
	        this.cashStatus = cashStatus;
	    }

	    public String getApply_stauts() {
	        return apply_stauts;
	    }

	    public void setApply_stauts(String apply_stauts) {
	        this.apply_stauts = apply_stauts;
	    }

	    /**
	     * @return the type
	     */
	    public String getType() {
	        return type;
	    }

	    /**
	     * @param type
	     *            the type to set
	     */
	    public void setType(String type) {
	        this.type = type;
	    }
	    

	    public Long getIdCustomer()
		{
			return idCustomer;
		}

		public void setIdCustomer(Long idCustomer)
		{
			this.idCustomer = idCustomer;
		}

		/**
	     * Default constructor - creates a new instance with no values set.
	     */
	    public User() {
	    }

	    /**
	     * Create a new instance and set the account.
	     *
	     * @param account
	     *            login name for user.
	     */
	    public User(final String account) {
	        this.account = account;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getAccount() {
	        return account;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public String getConfirmPassword() {
	        return confirmPassword;
	    }

	    public String getPasswordHint() {
	        return passwordHint;
	    }

	    public String getFirstName() {
	        return firstName;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public String getWebsite() {
	        return website;
	    }

	    public Address getAddress() {
	        return address;
	    }

	    public Integer getVersion() {
	        return version;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public void setAccount(String account) {
	        this.account = account;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public void setConfirmPassword(String confirmPassword) {
	        this.confirmPassword = confirmPassword;
	    }

	    public void setPasswordHint(String passwordHint) {
	        this.passwordHint = passwordHint;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public void setWebsite(String website) {
	        this.website = website;
	    }

	    public void setAddress(Address address) {
	        this.address = address;
	    }

	    public void setVersion(Integer version) {
	        this.version = version;
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (!(o instanceof User)) {
	            return false;
	        }

	        final User user = (User) o;

	        return !(account != null ? !account.equals(user.getAccount()) : user.getAccount() != null);

	    }

	    /**
	     * {@inheritDoc}
	     */
	    public int hashCode() {
	        return (account != null ? account.hashCode() : 0);
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public String toString() {
	        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append(
	            "account", this.account);
	        return sb.toString();
	    }

	    public String getUsername() {
	        return null;
	    }

	    public Integer getSex() {
	        return sex;
	    }

	    public void setSex(Integer sex) {
	        this.sex = sex;
	    }

	    public Integer getVisitCount() {
	        return visitCount;
	    }

	    public void setVisitCount(Integer visitCount) {
	        this.visitCount = visitCount;
	    }

	    public String getUserRank() {
	        return userRank;
	    }

	    public void setUserRank(String userRank) {
	        this.userRank = userRank;
	    }

	    public Date getGmtCreate() {
	        return gmtCreate;
	    }

	    public void setGmtCreate(Date gmtCreate) {
	        this.gmtCreate = gmtCreate;
	    }

	    public Date getGmtLast() {
	        return gmtLast;
	    }

	    public void setGmtLast(Date gmtLast) {
	        this.gmtLast = gmtLast;
	    }

	    public String getIpLast() {
	        return ipLast;
	    }

	    public void setIpLast(String ipLast) {
	        this.ipLast = ipLast;
	    }

	    public Integer getStauts() {
	        return stauts;
	    }

	    public void setStauts(Integer stauts) {
	        this.stauts = stauts;
	    }

	    public Integer getIsValidated() {
	        return isValidated;
	    }

	    public void setIsValidated(Integer isValidated) {
	        this.isValidated = isValidated;
	    }

	    public String getNickname() {
	        return nickname;
	    }

	    public void setNickname(String nickname) {
	        this.nickname = nickname;
	    }

	    public String getCurrentlypwd() {
	        return currentlypwd;
	    }

	    public void setCurrentlypwd(String currentlypwd) {
	        this.currentlypwd = currentlypwd;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public Long getReferenceId() {
	        return referenceId;
	    }

	    public void setReferenceId(Long referenceId) {
	        this.referenceId = referenceId;
	    }

	    public String getGmtCreateMd5() {
	        return gmtCreateMd5;
	    }

	    public void setGmtCreateMd5(String gmtCreateMd5) {
	        this.gmtCreateMd5 = gmtCreateMd5;
	    }

	    public String getAgentManagerId() {
			return agentManagerId;
		}

		public void setAgentManagerId(String agentManagerId) {
			this.agentManagerId = agentManagerId;
		}

		public String getAgentManagerName() {
	        return agentManagerName;
	    }

	    public void setAgentManagerName(String agentManagerName) {
	        this.agentManagerName = agentManagerName;
	    }

	    public Long getAgentCount() {
	        return agentCount;
	    }

	    public void setAgentCount(Long agentCount) {
	        this.agentCount = agentCount;
	    }

	    public String getConfirmPayPassword() {
	        return confirmPayPassword;
	    }

	    public void setConfirmPayPassword(String confirmPayPassword) {
	        this.confirmPayPassword = confirmPayPassword;
	    }

	    public String getPayPassword() {
	        return payPassword;
	    }

	    public void setPayPassword(String payPassword) {
	        this.payPassword = payPassword;
	    }

		public Double getSalesCount() {
			return salesCount;
		}

		public void setSalesCount(Double salesCount) {
			this.salesCount = salesCount;
		}

		public String getVipRemark() {
			return vipRemark;
		}

		public void setVipRemark(String vipRemark) {
			this.vipRemark = vipRemark;
		}
		
		

		public UserAddress getUserAddress()
		{
			return userAddress;
		}

		public void setUserAddress(UserAddress userAddress)
		{
			this.userAddress = userAddress;
		}

		/**
	     * @return gmtPeriodPayStart_str
	     */
	    public String getGmtPeriodPayStart_str() {
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
	        if (this.getGmtPeriodPayStart() != null) {
	            return df.format(this.getGmtPeriodPayStart());
	        } else {
	            return null;
	        }
	    }

	    /**
	     * @param gmtPeriodPayStart_str
	     */
	    public void setGmtPeriodPayStart_str(String gmtPeriodPayStart_str) {
	        this.gmtPeriodPayStart_str = gmtPeriodPayStart_str;
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
	        Date ts = null;
	        if (StringUtils.isNotBlank(gmtPeriodPayStart_str)) {
	            try {
	                ts = df.parse(gmtPeriodPayStart_str);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        }
	        this.gmtPeriodPayStart = ts;
	    }

		public Integer getYcperiod() {
			return ycperiod;
		}

		public void setYcperiod(Integer ycperiod) {
			this.ycperiod = ycperiod;
		}

	    public String getIsTaobao() {
	        return isTaobao;
	    }

	    public void setIsTaobao(String isTaobao) {
	        this.isTaobao = isTaobao;
	    }

		public String getDepCode()
		{
			return depCode;
		}

		public void setDepCode(String depCode)
		{
			this.depCode = depCode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getUseraddress() {
			return useraddress;
		}

		public void setUseraddress(String useraddress) {
			this.useraddress = useraddress;
		}

		public String getIntegral() {
			return integral;
		}

		public void setIntegral(String integral) {
			this.integral = integral;
		}
	    
	    
	    
}
