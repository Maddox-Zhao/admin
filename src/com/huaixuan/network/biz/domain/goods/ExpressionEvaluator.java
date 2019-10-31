package com.huaixuan.network.biz.domain.goods;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExpressionEvaluator {
    protected static Log       log                        = LogFactory
                                                              .getLog(ExpressionEvaluator.class);
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    public static String evaluate(String param, Map map) {
        return parseStringValue(param, map);

    }

    protected static String parseStringValue(String strVal, Map props) {
        StringBuffer buf = new StringBuffer(strVal);
        int startIndex = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        while (startIndex != -1) {
            int endIndex = buf.toString().indexOf(DEFAULT_PLACEHOLDER_SUFFIX,
                startIndex + DEFAULT_PLACEHOLDER_PREFIX.length());
            if (endIndex != -1) {
                String placeholder = buf.substring(
                    startIndex + DEFAULT_PLACEHOLDER_PREFIX.length(), endIndex);
                String propVal = resolvePlaceholder(placeholder, props);
                if (propVal != null) {
                    buf
                        .replace(startIndex, endIndex + DEFAULT_PLACEHOLDER_SUFFIX.length(),
                            propVal);

                    startIndex = buf.toString().indexOf(DEFAULT_PLACEHOLDER_PREFIX,
                        startIndex + propVal.length());
                } else
                    startIndex = -1;
                if (propVal == null)
                    startIndex = -1;
            } else {
                startIndex = -1;
            }
        }

        return buf.toString();
    }

    public static String resolvePlaceholder(String param, Map props) {
        Expression aExpression;
        try {
            aExpression = ExpressionFactory.createExpression(param);
            JexlContext jc = JexlHelper.createContext();
            jc.getVars().putAll(props);
            Object o = aExpression.evaluate(jc);
            return o.toString();
        } catch (Exception e) {

            log.error(e.getMessage());
            return null;
        }
    }

    public static String evaluate(String paramString, String name, String realPath) {
        Map map = new HashMap();
        map.put(name, realPath);
        return resolvePlaceholder(paramString, map);

    }

    public static void main(String[] args) throws Exception {
        String strVal = " goods.id == 2 ";
        Map props = new HashMap();
        props.put("isXiaoBao", 1);
        props.put("isP4P", 1);
        props.put("tt", 3);
        props.put("goods.id", "2");

        String o = ExpressionEvaluator.evaluate(strVal, props);
        System.out.println(o);

    }
}
