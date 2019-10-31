package com.huaixuan.network.biz.domain.express;

 import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Region extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private String code;
	 /* @property: */
	 private String parentCode;
	 /* @property: */
	 private String regionName;
	 /* @property: */
	 private int regionType;
	 /* Default constructor - creates a new instance with no values set. */
	 public Region(){}
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
	 public void setParentCode(String obj){
		 this.parentCode = obj;
	 }

	 /* @model: */
	 public String getParentCode(){
		 return this.parentCode;
	 }
	 /* @model: */
	 public void setRegionName(String obj){
		 this.regionName = obj;
	 }

	 /* @model: */
	 public String getRegionName(){
		 return this.regionName;
	 }
	 /* @model: */
	 public void setRegionType(int obj){
		 this.regionType = obj;
	 }

	 /* @model: */
	 public int getRegionType(){
		 return this.regionType;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Region)) {
			 return false;
		 }
		 final Region region = (Region) o;
		 return this.hashCode() == region.hashCode();
	 }

    public int hashCode(){
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((code == null) ? 0 : code.hashCode());
	    result = prime * result + (int)(id ^ (id >>> 32));
	    result = prime * result + ((parentCode == null) ? 0 : parentCode.hashCode());
	    result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
	    result = prime * result + regionType;
	    return result;
    }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("code", this.code)
			 .append("parentCode", this.parentCode)
			 .append("regionName", this.regionName)
			 .append("regionType", this.regionType);
		 return sb.toString();
	 }


 }
