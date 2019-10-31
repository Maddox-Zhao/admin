/**
 * 
 */
package com.huaixuan.network.biz.domain.trade;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author shengyong
 *
 */
public class TradeResult extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = -5388338705653729904L;

    private boolean           isSucess;

    private String            message;

    public boolean isSucess() {
        return isSucess;
    }

    public void setSucess(boolean isSucess) {
        this.isSucess = isSucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
