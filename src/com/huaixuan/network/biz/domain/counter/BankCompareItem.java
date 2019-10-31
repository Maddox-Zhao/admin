package com.huaixuan.network.biz.domain.counter;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.base.counter.BaseBankCompareItem;

/**  
 * (bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public class BankCompareItem extends BaseBankCompareItem {

    private static final long serialVersionUID = -4272337084809320246L;
    private Integer recount;
    public BankCompareItem() {
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(600405743, -1520388889).appendSuper(super.hashCode())
            .toHashCode();
    }

	public Integer getRecount() {
		return recount;
	}

	public void setRecount(Integer recount) {
		this.recount = recount;
	}

}
