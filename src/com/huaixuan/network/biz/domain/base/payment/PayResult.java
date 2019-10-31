/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :zhengsb
 * Version    :1.0
 * Create Date:Aug 20, 2009
 */
package com.huaixuan.network.biz.domain.base.payment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Administrator
 * @version $Id: PayResult.java,v 0.1 Aug 20, 2009 2:17:35 PM Administrator Exp $
 */
public class PayResult implements Serializable {

    private static final long   serialVersionUID = 2757506380034085379L;

    //支付结果是否成功标识符
    private boolean             result           = false;
    //存放返回的结果map
    private Map<String, Object> map              = new HashMap<String, Object>();

    public boolean isSuccess() {
        return result;
    }

    public void setSuccess(boolean result) {
        this.result = result;
    }

    public void addModel(String key, Object obj) {
        map.put(key, obj);
    }

    public Object getModel(String key) {
        return map.get(key);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-882798189, -212030861).appendSuper(super.hashCode()).append(
            this.result).append(this.map).toHashCode();
    }

}
