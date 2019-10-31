package com.huaixuan.network.biz.domain.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class UserAddress {
	 private long id;
	 /* @property: */
	 private String code;
	 /* @property: */
	 private long userId;
	 /* @property: */
	 private String contextName;
	 /* @property: */
	 private String email;
	 /* @property: */
	 private String country;
	 /* @property: */
	 private String province;
	 /* @property: */
	 private String city;
	 /* @property: */
	 private String district;
	 /* @property: */
	 private String address;
	 /* @property: */
	 private String zipcode;
	 /* @property: */
	 private String tel;
	 /* @property: */
	 private String mobile;
	 /* @property: */
	 private String remarks;
	 /* Default constructor - creates a new instance with no values set. */
	 public UserAddress(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setCode(String obj){
		 this.code = obj;
	 }

	 /* @model: */
	 public String getCode(){
		 return this.code;
	 }
	 /* @model: */
	 public void setUserId(long obj){
		 this.userId = obj;
	 }

	 /* @model: */
	 public long getUserId(){
		 return this.userId;
	 }
	 /* @model: */
	 public void setContextName(String obj){
		 this.contextName = obj;
	 }

	 /* @model: */
	 public String getContextName(){
		 return this.contextName;
	 }
	 /* @model: */
	 public void setEmail(String obj){
		 this.email = obj;
	 }

	 /* @model: */
	 public String getEmail(){
		 return this.email;
	 }
	 /* @model: */
	 public void setCountry(String obj){
		 this.country = obj;
	 }

	 /* @model: */
	 public String getCountry(){
		 return this.country;
	 }
	 /* @model: */
	 public void setProvince(String obj){
		 this.province = obj;
	 }

	 /* @model: */
	 public String getProvince(){
		 return this.province;
	 }
	 /* @model: */
	 public void setCity(String obj){
		 this.city = obj;
	 }

	 /* @model: */
	 public String getCity(){
		 return this.city;
	 }
	 /* @model: */
	 public void setDistrict(String obj){
		 this.district = obj;
	 }

	 /* @model: */
	 public String getDistrict(){
		 return this.district;
	 }
	 /* @model: */
	 public void setAddress(String obj){
		 this.address = obj;
	 }

	 /* @model: */
	 public String getAddress(){
		 return this.address;
	 }
	 /* @model: */
	 public void setZipcode(String obj){
		 this.zipcode = obj;
	 }

	 /* @model: */
	 public String getZipcode(){
		 return this.zipcode;
	 }
	 /* @model: */
	 public void setTel(String obj){
		 this.tel = obj;
	 }

	 /* @model: */
	 public String getTel(){
		 return this.tel;
	 }
	 /* @model: */
	 public void setMobile(String obj){
		 this.mobile = obj;
	 }

	 /* @model: */
	 public String getMobile(){
		 return this.mobile;
	 }
	 /* @model: */
	 public void setRemarks(String obj){
		 this.remarks = obj;
	 }

	 /* @model: */
	 public String getRemarks(){
		 return this.remarks;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof UserAddress)) {
			 return false;
		 }
		 final UserAddress useraddress = (UserAddress) o;
		 return this.hashCode() == useraddress.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     int prime=31;
	     int result=1;
	     result=prime*result+(int)id;
	     result=prime*result+((code==null)?0:code.hashCode());
	     result=prime*result+(int)userId;
	     result=prime*result+((contextName==null)?0:contextName.hashCode());
	     result=prime*result+((email==null)?0:email.hashCode());
	     result=prime*result+((country==null)?0:country.hashCode());
	     result=prime*result+((province==null)?0:province.hashCode());
	     result=prime*result+((city==null)?0:city.hashCode());
	     result=prime*result+((district==null)?0:district.hashCode());
	     result=prime*result+((address==null)?0:address.hashCode());
	     result=prime*result+((zipcode==null)?0:zipcode.hashCode());
	     result=prime*result+((tel==null)?0:tel.hashCode());
	     result=prime*result+((mobile==null)?0:mobile.hashCode());
	     result=prime*result+((remarks==null)?0:remarks.hashCode());
	     return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("code", this.code)
			 .append("userId", this.userId)
			 .append("contextName", this.contextName)
			 .append("email", this.email)
			 .append("country", this.country)
			 .append("province", this.province)
			 .append("city", this.city)
			 .append("district", this.district)
			 .append("address", this.address)
			 .append("zipcode", this.zipcode)
			 .append("tel", this.tel)
			 .append("mobile", this.mobile)
			 .append("remarks", this.remarks);
		 return sb.toString();
	 }
}
