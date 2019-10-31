/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-8
 */
package com.huaixuan.network.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shengyong
 * @version $Id: ApiParameter.java,v 0.1 2011-3-8 下午03:53:01 shengyong Exp $
 */
public class ApiParameter {
    private static final Set<String>  RESERVED_NAMES = new HashSet<String>();
    static {
        for (ReservedName name : ReservedName.values()) {
            RESERVED_NAMES.add(name.toString());
        }
    }

    private Map<String, List<Object>> params         = new HashMap<String, List<Object>>();

    private ApiParameter addParam(String name, Object value) {
        if (RESERVED_NAMES.contains(name)) {
            throw new IllegalArgumentException("不能在ApiParameter中写入保留字段，请查询ApiClient中是否有set"
                                               + Character.toUpperCase(name.charAt(0))
                                               + name.substring(1) + "()方法。");
        }
        List<Object> list = params.get(name);
        if (list == null) {
            list = new ArrayList<Object>();
            params.put(name, list);
        }
        list.add(value);
        return this;
    }

    public ApiParameter addStringParam(String name, String value) {
        return addParam(name, value);
    }

    public ApiParameter addStringParam(String name, Number value) {
        return addParam(name, value.toString());
    }

    public ApiParameter addFileParam(String name, String fileName) {
        File file = new File(fileName);
        return addParam(name, file);
    }

    @SuppressWarnings("unchecked")
    public List getParam(String name) {
        return params.get(name);
    }

    public Set<String> getNames() {
        return params.keySet();
    }
}
