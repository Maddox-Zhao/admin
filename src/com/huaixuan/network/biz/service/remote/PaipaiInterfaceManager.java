/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-8
 */
package com.huaixuan.network.biz.service.remote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.w3c.dom.Document;

import com.huaixuan.network.biz.enums.EnumPaipaiApi;
import com.huaixuan.network.common.util.ApiParameter;

/**
 * @author shengyong
 * @version $Id: PaipaiInterfaceManager.java,v 0.1 2011-3-8 ÏÂÎç03:50:09 shengyong Exp $
 */
public interface PaipaiInterfaceManager {

    public JSONObject paipaiClientByJson(ApiParameter parameter, EnumPaipaiApi api,
                                         boolean needSign, Map<String, String> noSignPram,
                                         Long userId, String format)
                                                                    throws UnsupportedEncodingException,
                                                                    IOException;

    public Document paipaiClientByXml(ApiParameter parameter, EnumPaipaiApi api, boolean needSign,
                                      Map<String, String> noSignPram, Long userId, String format)
                                                                                                 throws UnsupportedEncodingException,
                                                                                                 IOException;

    public boolean paipaiClientByTimeTask(ApiParameter parameter, EnumPaipaiApi api, Long userId,
                                          String format) throws UnsupportedEncodingException,
                                                        IOException;

}
