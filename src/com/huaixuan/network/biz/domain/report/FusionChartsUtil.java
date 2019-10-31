package com.huaixuan.network.biz.domain.report;

import java.util.Date;
import java.util.Map;

public class FusionChartsUtil {
    /**
     * ת��String�������ʽΪ��" propertyName='propertyValue'"���ָ�����Ϊ�ո����������Ϊnull����ֵpropertyValueΪ�ջ�ո��򷵻ؿո�
     * @param propertyName ������
     * @param propertyValue ������ֵ
     * @return
     */
    public static String makeProperty2String(String propertyName, String propertyValue) {
        return makeProperty2String(propertyName, propertyValue, " ");
    }

    /**
     * ת��String�������ʽΪ��"�ָ�����propertyName='propertyValue'"�����������Ϊnull����ֵpropertyValueΪ�ջ�ո��򷵻ؿո�
     * @param propertyName ������
     * @param propertyValue ������ֵ
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
     * ��MAPת��String�������ʽΪ��"key='value' key='value' key='value'....."���ָ�����Ϊ�ո����key��valueΪnull����ֵvalueΪ�ջ�ո�����Դ�ֵ
     * @param propertyMap ԴMAP
     * @return �����ʽΪ��"key='value' key='value' key='value'....."
     */
    public static String makePropertyMap2String(Map<String, String> propertyMap) {
        return makePropertyMap2String(propertyMap, " ");
    }

    /**
     * ��MAPת��String�������ʽΪ��"key='value'�ָ�����key='value'�ָ�����key='value'....."�����key��valueΪnull����ֵvalueΪ�ջ�ո�����Դ�ֵ
     * @param propertyMap ԴMAP
     * @param separatorStr �ָ������ַ���,Ϊnullʱ��ʹ��Ĭ��Ϊ�ո�
     * @return �����ʽΪ��"key='value'�ָ�����key='value'�ָ�����key='value'....."
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
     * ��URLĩβ�Ӹ����ڲ������Ա��������ʹ�û������ݶ������������ݡ�<br>  
     * @param strDataURL - ԭ������ҳ��ַ
     * @return cachedURL - ������µĶ�̬������ĵ�ַ
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
     * ����һ�� JavaScript + HTML DIV ����ʾFlash����.<br>
     * ʹ�ô˷���ǰ����ȷ�������ҳ���Ѿ�������FusionCharts js������ʾ������<br>
     * ��������һ��DIV��һ��javaScript��ͨ��javaScript��Flash��ӵ����DIV�� <br>
     * the required parameters to it.<br>
     * ע��: ���� strXML �� strURL ������һ������Ϊ�գ�����ʹ��strXML�����Ϊnull���߿ո���߳���Ϊ0����ʹ��strURL����������<br>
     * 
     * @param chartSWF - Flash���ļ�������·��URL��
     * @param strURL - �����ʹ��URL�����󱨱����ݣ��뽫�����ַ������������ʹ�����������������Ϊnull���߿�
     * @param strXML - �����ֱ��ʹ��XML�����ַ������뽫�����ַ���������������ʹ�����������������Ϊnull���߿�
     * @param chartId - Flash��ID�����ҳ�����ж������ͬʱ���ڣ��뱣֤���ID��Ψһ�ġ�
     * @param chartWidth - Flash����ʾ��ȣ���λΪ���أ�
     * @param chartHeight - Flash����ʾ�߶ȣ���λΪ���أ�
     * @param debugMode - �Ƿ�ʹ�õ���ģʽ��true/false
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
     * ֱ������Flash��ʾʱ�� html Դ��.<br>
     * ��ҳ�ϲ���Ҫ����FusionCharts js�Ϳ�����ʾ����.<br>
     * ע��: ���� strXML �� strURL ������һ������Ϊ�գ�����ʹ��strXML�����Ϊnull���߿ո���߳���Ϊ0����ʹ��strURL����������<br>
     * 
     * @param chartSWF - Flash���ļ�������·��URL��
     * @param strURL - �����ʹ��URL�����󱨱����ݣ��뽫�����ַ������������ʹ�����������������Ϊnull���߿�
     * @param strXML - �����ֱ��ʹ��XML�����ַ������뽫�����ַ���������������ʹ�����������������Ϊnull���߿�
     * @param chartId - Flash��ID�����ҳ�����ж������ͬʱ���ڣ��뱣֤���ID��Ψһ�ġ�
     * @param chartWidth - Flash����ʾ��ȣ���λΪ���أ�
     * @param chartHeight - Flash����ʾ�߶ȣ���λΪ���أ�
     * @param debugMode - �Ƿ�ʹ�õ���ģʽ��true/false
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
