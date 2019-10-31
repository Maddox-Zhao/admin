package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

public class Notice extends BaseObject implements Serializable {
	/**
     *
     */
	private static final long serialVersionUID = 1L;
	/* @property: */
	private long id;
	/* @property: */
	private String title;
	/* @property: */
	private String content;
	/* @property: */
	private Date noticeDate;
	/* @property: */
	private String status;
	/* @property: */
	private String author;
	/* @property: */
	private int isShow;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: statusÄ¬ÈÏÉóºËÍ¨¹ý×´Ì¬open */
	public static final String Status_open = "open";
	/* @property: statusÉ¾³ý×´Ì¬delete */
	public static final String Status_delete = "delete";

	/* @property: is_showÏÔÊ¾×´Ì¬delete */
	public static final int Isshow_yes = 1;
	/* @property: is_show²»ÏÔÊ¾×´Ì¬delete */
	public static final int Isshow_no = 0;

	private Long[] checkbox;

	/* @property: */
	private long shopId;

	private String noticeType;

	/* Default constructor - creates a new instance with no values set. */
	public Notice() {
	}

	public void setId(long obj) {
		this.id = obj;
	}

	public long getId() {
		return this.id;
	}

	public void setTitle(String obj) {
		this.title = obj;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String obj) {
		this.content = obj;
	}

	public String getContent() {
		return this.content;
	}

	/**
	 * @return the noticeDate
	 */
	public Date getNoticeDate() {
		return noticeDate;
	}

	/**
	 * @param noticeDate
	 *            the noticeDate to set
	 */
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public void setStatus(String obj) {
		this.status = obj;
	}

	public String getStatus() {
		return this.status;
	}

	public void setAuthor(String obj) {
		this.author = obj;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setIsShow(int obj) {
		this.isShow = obj;
	}

	public int getIsShow() {
		return this.isShow;
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
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Notice)) {
			return false;
		}
		final Notice notice = (Notice) o;
		return this.hashCode() == notice.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		return this.hashCode();
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("title", this.title).append("content", this.content)
				.append("noticeDate", this.noticeDate)
				.append("status", this.status).append("author", this.author)
				.append("isShow", this.isShow)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify);
		return sb.toString();
	}

	public Long[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Long[] checkbox) {
		this.checkbox = checkbox;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

}
