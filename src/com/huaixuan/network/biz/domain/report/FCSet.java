package com.huaixuan.network.biz.domain.report;

import java.util.HashMap;
import java.util.Map;

/**
 * 最基础的数据,调toXMLString() 方法获取数据转换成XML。
 * @author yuancong
 * @version $Id: FCSet.java,v 0.1 Mar 10, 2009 6:04:00 PM yuancong Exp $
 */
public class FCSet {
    //<set value="25601.34" /> 
    public final static String  KEY              = "set";
    /**
     * 其它属性集，key为属性名，value为属性的值，请参考FusionCharts的属性说明。
     */
    private Map<String, String> otherPropertyMap = new HashMap<String, String>();

    private String              label;
    private String              value;
    private String              isSliced;

    public String toXMLString() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("<" + KEY);
        sBuffer.append(FusionChartsUtil.makeProperty2String("label", label));
        sBuffer.append(FusionChartsUtil.makeProperty2String("value", value));
        sBuffer.append(FusionChartsUtil.makeProperty2String("isSliced", isSliced));
        sBuffer.append(FusionChartsUtil.makePropertyMap2String(otherPropertyMap));
        sBuffer.append(" />");
        return sBuffer.toString();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsSliced() {
        return isSliced;
    }

    public void setIsSliced(String isSliced) {
        this.isSliced = isSliced;
    }

    public Map<String, String> getOtherPropertyMap() {
        return otherPropertyMap;
    }

    public void setOtherPropertyMap(Map<String, String> otherPropertyMap) {
        this.otherPropertyMap = otherPropertyMap;
    }

}
