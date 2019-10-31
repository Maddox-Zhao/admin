package com.huaixuan.network.biz.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode().
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public abstract class BaseObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer startRow;
	private Integer endRow;
	private List<Long> depfirstIds;

		public List<Long> getDepfirstIds() {
		return depfirstIds;
	}
	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}


}
