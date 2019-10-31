package com.huaixuan.network.biz.domain.shop;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class AdPosition extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String positionName;
	 /* @property: */
	 private String positionCode;
	 /* @property: */
	 private int width;
	 /* @property: */
	 private int height;
	 /* @property: */
	 private String positionDesc;
	 /* @property: */
	 private String positionStyle;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public AdPosition(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setPositionName(String obj){
		 this.positionName = obj;
	 }

	 /* @model: */
	 public String getPositionName(){
		 return this.positionName;
	 }
	 /* @model: */
	 public void setPositionCode(String obj){
		 this.positionCode = obj;
	 }

	 /* @model: */
	 public String getPositionCode(){
		 return this.positionCode;
	 }
	 /* @model: */
	 public void setWidth(int obj){
		 this.width = obj;
	 }

	 /* @model: */
	 public int getWidth(){
		 return this.width;
	 }
	 /* @model: */
	 public void setHeight(int obj){
		 this.height = obj;
	 }

	 /* @model: */
	 public int getHeight(){
		 return this.height;
	 }
	 /* @model: */
	 public void setPositionDesc(String obj){
		 this.positionDesc = obj;
	 }

	 /* @model: */
	 public String getPositionDesc(){
		 return this.positionDesc;
	 }
	 /* @model: */
	 public void setPositionStyle(String obj){
		 this.positionStyle = obj;
	 }

	 /* @model: */
	 public String getPositionStyle(){
		 return this.positionStyle;
	 }
	 /* @model: */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model: */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model: */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model: */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object obj) {
		 if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final AdPosition other = (AdPosition) obj;
			if (positionName == null) {
				if (other.positionName != null)
					return false;
			} else if (!positionName.equals(other.positionName))
				return false;
			if (positionCode == null) {
				if (other.positionCode != null)
					return false;
			} else if (!positionCode.equals(other.positionCode))
				return false;
			if (positionDesc == null) {
				if (other.positionDesc != null)
					return false;
			} else if (!positionDesc.equals(other.positionDesc))
				return false;
			if (gmtCreate == null) {
				if (other.gmtCreate != null)
					return false;
			} else if (!gmtCreate.equals(other.gmtCreate))
				return false;
			if (gmtModify == null) {
				if (other.gmtModify != null)
					return false;
			} else if (!gmtModify.equals(other.gmtModify))
				return false;
			if (positionStyle == null) {
				if (other.positionStyle != null)
					return false;
			} else if (!positionStyle.equals(other.positionStyle))
				return false;


			return true;
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 26;
			int result = 1;
			result = prime * result
					+ ((positionName == null) ? 0 : positionName.hashCode());
			result = prime * result + ((positionCode == null) ? 0 : positionCode.hashCode());
			result = prime * result + ((positionDesc == null) ? 0 : positionDesc.hashCode());
			result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
			result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
			result = prime * result + ((positionStyle == null) ? 0 : positionStyle.hashCode());
			return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("positionName", this.positionName)
			 .append("positionCode", this.positionCode)
			 .append("width", this.width)
			 .append("height", this.height)
			 .append("positionDesc", this.positionDesc)
			 .append("positionStyle", this.positionStyle)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
