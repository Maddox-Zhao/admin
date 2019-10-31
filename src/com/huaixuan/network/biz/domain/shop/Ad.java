package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

public class Ad extends BaseObject implements Serializable {
	/* @property: */
	private long id;
	/* @property: */
	private String adPositionIdstr;

	private long adPositionId;
	/* @property: */
	private String adType;
	/* @property: */
	private String adName;
	/* @property: */
	private Date startTime;
	private String startTimeStr;
	/* @property: */
	private Date endTime;
	private String endTimeStr;
	/* @property: */
	private String linkMan;
	/* @property: */
	private String linkEmail;
	/* @property: */
	private String linkPhone;
	/* @property: */
	private String status;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;

	/* @property: */
	private String positionName;
	/* @property: */
	private String mediaType;

	private Long postionId;

	private String link;
	/* @property: */
	private String mediaCode;

	private int sort;

	/* @property: */
	private String sortstr;

	/* @property: */
	/* ״̬Ϊopen */
	public static final String Status_open = "open";

	/* ״̬Ϊclose */
	public static final String Status_close = "close";

	/* ״̬Ϊdelete */
	public static final String Status_delete = "delete";

	/* Default constructor - creates a new instance with no values set. */
	public Ad() {
	}

	public void setId(long obj) {
		this.id = obj;
	}

	public long getId() {
		return this.id;
	}

	public void setAdPositionId(long obj) {
		this.adPositionId = obj;
	}

	public long getAdPositionId() {
		return this.adPositionId;
	}

	public void setAdName(String obj) {
		this.adName = obj;
	}

	public String getAdName() {
		return this.adName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
		if (this.getStartTime() != null) {
			return df.format(this.getStartTime());
		} else {
			return null;
		}
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
		Date ts = null;
		if (StringUtils.isNotBlank(startTimeStr)) {
			try {
				ts = df.parse(startTimeStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		this.startTime = ts;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
		if (this.getEndTime() != null) {
			return df.format(this.getEndTime());
		} else {
			return null;
		}
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
		Date ts = null;
		if (StringUtils.isNotBlank(endTimeStr)) {
			try {
				ts = df.parse(endTimeStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		this.endTime = ts;
	}

	public void setLinkMan(String obj) {
		this.linkMan = obj;
	}

	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkEmail(String obj) {
		this.linkEmail = obj;
	}

	public String getLinkEmail() {
		return this.linkEmail;
	}

	public void setLinkPhone(String obj) {
		this.linkPhone = obj;
	}

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setStatus(String obj) {
		this.status = obj;
	}

	public String getStatus() {
		return this.status;
	}

	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* {@inheritDoc} */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Ad other = (Ad) obj;
		if (adName == null) {
			if (other.adName != null)
				return false;
		} else if (!adName.equals(other.adName))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (linkMan == null) {
			if (other.linkMan != null)
				return false;
		} else if (!linkMan.equals(other.linkMan))
			return false;
		if (linkEmail == null) {
			if (other.linkEmail != null)
				return false;
		} else if (!linkEmail.equals(other.linkEmail))
			return false;
		if (linkPhone == null) {
			if (other.linkPhone != null)
				return false;
		} else if (!linkPhone.equals(other.linkPhone))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		if (positionName == null) {
			if (other.positionName != null)
				return false;
		} else if (!positionName.equals(other.positionName))
			return false;
		if (mediaType == null) {
			if (other.mediaType != null)
				return false;
		} else if (!mediaType.equals(other.mediaType))
			return false;
		if (mediaType == null) {
			if (other.mediaType != null)
				return false;
		} else if (!mediaType.equals(other.mediaType))
			return false;
		if (adPositionIdstr == null) {
			if (other.adPositionIdstr != null)
				return false;
		} else if (!adPositionIdstr.equals(other.adPositionIdstr))
			return false;
		return true;
	}

	/* {@inheritDoc} */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adName == null) ? 0 : adName.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((linkMan == null) ? 0 : linkMan.hashCode());
		result = prime * result
				+ ((linkEmail == null) ? 0 : linkEmail.hashCode());
		result = prime * result
				+ ((linkPhone == null) ? 0 : linkPhone.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result
				+ ((positionName == null) ? 0 : positionName.hashCode());
		result = prime * result
				+ ((mediaType == null) ? 0 : mediaType.hashCode());
		result = prime * result
				+ ((adPositionIdstr == null) ? 0 : adPositionIdstr.hashCode());
		return result;
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("adPositionId", this.adPositionId)
				.append("adName", this.adName)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime)
				.append("linkMan", this.linkMan)
				.append("linkEmail", this.linkEmail)
				.append("linkPhone", this.linkPhone)
				.append("status", this.status)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify)
				.append("positionName", this.positionName)
				.append("mediaType", this.mediaType)
				.append("adPositionIdstr", this.adPositionIdstr);
		return sb.toString();
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getAdPositionIdstr() {
		return adPositionIdstr;
	}

	public void setAdPositionIdstr(String adPositionIdstr) {
		this.adPositionIdstr = adPositionIdstr;
	}

	public Long getPostionId() {
		return postionId;
	}

	public void setPostionId(Long postionId) {
		this.postionId = postionId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMediaCode() {
		return mediaCode;
	}

	public void setMediaCode(String mediaCode) {
		this.mediaCode = mediaCode;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getSortstr() {
		return sortstr;
	}

	public void setSortstr(String sortstr) {
		this.sortstr = sortstr;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

}
