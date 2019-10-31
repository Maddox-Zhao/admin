package com.huaixuan.network.biz.domain.goods;

import com.huaixuan.network.biz.domain.BaseObject;
import com.hundsun.network.melody.common.util.StringUtil;


public class GoodsAttrShow extends BaseObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long showNumber;

	private String attrTitle;

	private String attrContent;

	public long getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(long showNumber) {
		this.showNumber = showNumber;
	}

	public String getAttrTitle() {
		return attrTitle;
	}

	public void setAttrTitle(String attrTitle) {
		this.attrTitle = attrTitle;
	}

	public String getAttrContent() {
        if (StringUtil.isNotBlank(this.attrContent)) {
            String attrDesc = StringUtil.replace(this.attrContent, "\r\n", "</br>");
            return attrDesc;
        } else {
            return this.attrContent;
        }
	}

	public void setAttrContent(String attrContent) {
		this.attrContent = attrContent;
	}
}
