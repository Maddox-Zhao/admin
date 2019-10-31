package com.huaixuan.network.biz.domain.account;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.base.account.BaseAccountLog;

/**  
 * (bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public class AccountLog extends BaseAccountLog
{
	private static final long serialVersionUID = -2338558722636464470L;

    public AccountLog()
	{
	}

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1334746885, 1946503599).appendSuper(super.hashCode())
            .toHashCode();
    }

}
