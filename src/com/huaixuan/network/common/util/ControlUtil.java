/**
 * created since Mar 30, 2009
 */
package com.huaixuan.network.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author taobao
 * @version $Id: ControlUtil.java,v 0.1 Mar 30, 2009 6:03:51 PM taobao Exp $
 */
public class ControlUtil {

    protected static Log log = LogFactory.getLog(ControlUtil.class);

    public static String getNoticesTitle(String str, int length) {
        if (StringUtil.isNotEmpty(str)) {

            if (str.length() > length) {
                return str.substring(0, length) + "...";
            } else {
                return str;
            }
        }
        return null;
    }
}
