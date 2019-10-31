package com.huaixuan.network.biz.domain.account;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.base.account.BaseAccount;
import com.hundsun.network.melody.common.util.Money;

/**  
 * (bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public class Account extends BaseAccount {

    private static final long serialVersionUID = -2950662973148525639L;

    public Account() {
    }

    /**
     * 获取可用余额
     * @return
     */
    public Money getCanUseBalance() {
        Money canUseBalanceMoney = new Money();
        if (this.getBalance() != null && this.getFreezeAmount() != null) {
            long canUseBalance = this.getBalance().longValue() - this.getFreezeAmount().longValue();
            canUseBalanceMoney.setCent(canUseBalance);
        }
        return canUseBalanceMoney;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1536971109, -486254083).appendSuper(super.hashCode())
            .toHashCode();
    }

}
