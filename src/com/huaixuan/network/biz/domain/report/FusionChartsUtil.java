package com.huaixuan.network.biz.domain.report;

import java.util.Date;
import java.util.Map;

public class FusionChartsUtil {
    /**
     * 转成String，结果格式为：" propertyName='propertyValue'"，分隔符号为空格，如果两参数为null或者值propertyValue为空或空格，则返回空格
     * @param propertyName 变量名
     * @param propertyValue 变量的值
     * @return
     */
    public static String makeProperty2String(String propertyName, String propertyValue) {
        return makeProperty2String(propertyName, propertyValue, " ");
    }

    /**
     * 转成String，结果格式为："分隔符号propertyName='propertyValue'"，如果两参数为null或者值propertyValue为空或空格，则返回空格
     * @param propertyName 变量名
     * @param propertyValue 变量的值
     * @param separatorStr
     * @return
     */
    public static String makeProperty2String(String propertyName, String propertyValue,
                                             String separatorStr) {
        if (separatorStr == null) {
            separatorStr = " ";
        }
        if (propertyName == null || propertyValue == null || propertyName.trim().length() == 0
            || propertyValue.trim().length() == 0) {
            return "";
        }
        return separatorStr + propertyName + "='" + propertyValue + "'";
    }

    /**
     * 将MAP转成String，结果格式为："key='value' key='value' key='value'....."，分隔符号为空格，如果key和value为null或者值value为空或空格，则忽略此值
     * @param propertyMap 源MAP
     * @return 结果格式为："key='value' key='value' key='value'....."
     */
    public static String makePropertyMap2String(Map<String, String> propertyMap) {
        return makePropertyMap2String(propertyMap, " ");
    }

    /**
     * 将MAP转成String，结果格式为："key='value'分隔符号key='value'分隔符号key='value'....."，如果key和value为null或者值value为空或空格，则忽略此值
     * @param propertyMap 源MAP
     * @param separatorStr 分隔符号字符串,为null时，使用默认为空格。
     * @return 结果格式为："key='value'分隔符号key='value'分隔符号key='value'....."
     */
    public static String makePropertyMap2String(Map<String, String> propertyMap, String separatorStr) {
        if (propertyMap == null) {
            return "";
        }
        if (separatorStr == null) {
            separatorStr = " ";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> m : propertyMap.entrySet()) {
            if (m.getValue() == null || m.getValue().trim().length() == 0) {
                continue;
            }
            stringBuffer.append(separatorStr + m.getKey() + "='" + m.getValue() + "'");
        }

        return stringBuffer.toString();
    }

    /**
     * 给URL末尾加个日期参数，以便浏览器不使用缓存数据而重新请求数据。<br>  
     * @param strDataURL - 原来的网页地址
     * @return cachedURL - 添加了新的动态参数后的地址
     */

    public static String addCacheToDataURL(String strDataURL) {
        String cachedURL = strDataURL;
        Date now = new Date();

        if (strDataURL.indexOf("?") > 0) {
            cachedURL = strDataURL + "&FCCurrTime=" + now.getTime();
        } else {
            cachedURL = strDataURL + "?FCCurrTime=" + now.getTime();
        }

        return cachedURL;
    }

    /**
     * 创建一个 JavaScript + HTML DIV 来显示Flash报表.<br>
     * 使用此方法前，请确保你的网页上已经引入了FusionCharts js才能显示出报表<br>
     * 它将创建一个DIV和一段javaScript，通过javaScript将Flash添加到这个DIV中 <br>
     * the required parameters to it.<br>
     * 注意: 参数 strXML 与 strURL 至少有一个不能为空，优先使用strXML，如果为null或者空格或者长度为0，则使用strURL来请求数据<br>
     * 
     * @param chartSWF - Flash的文件名（含路径URL）
     * @param strURL - 如果想使用URL来请求报表数据，请将请求地址放在这里，如果不使用这个方法，请设置为null或者空
     * @param strXML - 如果想直接使用XML数据字符串，请将数据字符串放在这里，如果不使用这个方法，请设置为null或者空
     * @param chartId - Flash的ID，如果页面中有多个报表同时存在，请保证这个ID是唯一的。
     * @param chartWidth - Flash的显示宽度（单位为像素）
     * @param chartHeight - Flash的显示高度（单位为像素）
     * @param debugMode - 是否使用调试模式，true/false
     * @param registerWithJS - true/false , Whether to ask chart to register itself with JavaScript
     */
    public static String createChart(String chartSWF, String strURL, String strXML, String chartId,
                                     int chartWidth, int chartHeight, boolean debugMode,
                                     boolean registerWithJS) {
        if (chartId == null || chartId.trim().length() == 0) {
            chartId = new Date().getTime() + "";
        }
        StringBuffer strBuf = new StringBuffer();
        // First we create a new DIV for each chart. We specify the name of DIV
        // as "chartId"Div.
        // DIV names are case-sensitive.

        strBuf.append("\t\t<!-- START Script Block for Chart-->\n");
        strBuf.append("\t\t<div id='" + chartId + "Div' align='center'>\n");
        strBuf.append("\t\t\t\tIt is waitting report data for show.\n");
        strBuf.append("\t\t</div>\n");

        /*
         * Now, we create the chart using FusionCharts js class. Each chart's
         * instance (JavaScript) Id is named as chart_"chartId".
         */

        strBuf.append("\t\t<script type='text/javascript'>\n");
        // Instantiate the Chart
        Boolean registerWithJSBool = new Boolean(registerWithJS);
        Boolean debugModeBool = new Boolean(debugMode);
        int regWithJSInt = boolToNum(registerWithJSBool);
        int debugModeInt = boolToNum(debugModeBool);

        strBuf.append("\t\t\t\tvar chart_" + chartId + " = new FusionCharts('" + chartSWF + "', '"
                      + chartId + "', '" + chartWidth + "', '" + chartHeight + "', '"
                      + debugModeInt + "', '" + regWithJSInt + "');\n");
        // Check whether we've to provide data using dataXML method or dataURL
        // method
        if (strXML == null || strXML.trim().length() == 0) {
            strBuf.append("\t\t\t\t// Set the dataURL of the chart\n");
            strBuf.append("\t\t\t\tchart_" + chartId + ".setDataURL(\"" + strURL + "\");\n");
        } else {
            strBuf.append("\t\t\t\t// Provide entire XML data using dataXML method\n");
            strBuf.append("\t\t\t\tchart_" + chartId + ".setDataXML(\"" + strXML + "\");\n");
        }
        strBuf.append("\t\t\t\t// Finally, render the chart.\n");
        strBuf.append("\t\t\t\tchart_" + chartId + ".render(\"" + chartId + "Div\");\n");
        strBuf.append("\t\t</script>\n");
        strBuf.append("\t\t<!--END Script Block for Chart-->\n");
        return strBuf.substring(0);
    }

    /**
     * 直接生成Flash显示时的 html 源码.<br>
     * 网页上不需要引入FusionCharts js就可以显示报表.<br>
     * 注意: 参数 strXML 与 strURL 至少有一个不能为空，优先使用strXML，如果为null或者空格或者长度为0，则使用strURL来请求数据<br>
     * 
     * @param chartSWF - Flash的文件名（含路径URL）
     * @param strURL - 如果想使用URL来请求报表数据，请将请求地址放在这里，如果不使用这个方法，请设置为null或者空
     * @param strXML - 如果想直接使用XML数据字符串，请将数据字符串放在这里，如果不使用这个方法，请设置为null或者空
     * @param chartId - Flash的ID，如果页面中有多个报表同时存在，请保证这个ID是唯一的。
     * @param chartWidth - Flash的显示宽度（单位为像素）
     * @param chartHeight - Flash的显示高度（单位为像素）
     * @param debugMode - 是否使用调试模式，true/false
     */
    public static String createChartHTML(String chartSWF, String strURL, String strXML,
                                         String chartId, int chartWidth, int chartHeight,
                                         boolean debugMode) {
        /*
        * Generate the FlashVars string based
        * on whether dataURL has been provided
        * or dataXML.
        */
        String strFlashVars = "";
        Boolean debugModeBool = new Boolean(debugMode);

        if (strXML == null || strXML.trim().length() == 0) {
            // DataURL Mode
            strFlashVars = "chartWidth=" + chartWidth + "&chartHeight=" + chartHeight
                           + "&debugMode=" + boolToNum(debugModeBool) + "&dataURL=" + strURL + "";
        } else {
            // DataXML Mode
            strFlashVars = "chartWidth=" + chartWidth + "&chartHeight=" + chartHeight
                           + "&debugMode=" + boolToNum(debugModeBool) + "&dataXML=" + strXML + "";
        }
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("\t\t<!--START Code Block for Chart-->\n");
        strBuf.append("\t<div id='" + chartId + "Div' align='center'>\n");
        strBuf
            .append("\t\t<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='"
                    + chartWidth + "' height='" + chartHeight + "' id='" + chartId + "'>\n");
        strBuf.append("\t\t\t\t<param name='allowScriptAccess' value='always' />\n");
        strBuf.append("\t\t\t\t<param name='movie' value='" + chartSWF + "'/>\n");
        strBuf.append("\t\t\t\t<param name='FlashVars' value=\"" + strFlashVars + "\" />\n");
        strBuf.append("\t\t\t\t<param name='quality' value='high' />\n");
        strBuf
            .append("\t\t\t\t<embed src='"
                    + chartSWF
                    + "' FlashVars=\""
                    + strFlashVars
                    + "\" quality='high' width='"
                    + chartWidth
                    + "' height='"
                    + chartHeight
                    + "' name='"
                    + chartId
                    + "' allowScriptAccess='always' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />\n");
        strBuf.append("\t\t</object>\n");
        strBuf.append("\t</div>\n");
        strBuf.append("\t\t<!--END Code Block for Chart-->\n");
        return strBuf.substring(0);
    }

    /**
     * Converts a Boolean value to int value<br>
     * 
     * @param bool Boolean value which needs to be converted to int value 
     * @return int value correspoding to the boolean : 1 for true and 0 for false
     */
    public static int boolToNum(Boolean bool) {
        int num = 0;
        if (bool.booleanValue()) {
            num = 1;
        }
        return num;
    }
}
