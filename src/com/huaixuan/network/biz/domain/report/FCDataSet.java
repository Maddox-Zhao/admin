package com.huaixuan.network.biz.domain.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据分组集,调toXMLString() 方法获取数据转换成XML。
 * @author yuancong
 * @version $Id: FCDataSet.java,v 0.1 Mar 10, 2009 6:04:23 PM yuancong Exp $
 */
public class FCDataSet {
    //    <dataset seriesName="1996" color="AFD8F8" showValues="0">
    //    <set value="25601.34" /> 
    //    <set value="20148.82" /> 
    //    <set value="17372.76" /> 
    //    <set value="35407.15" /> 
    //    <set value="38105.68" /> 
    //    </dataset>
    public final static String  KEY              = "dataset";
    /**
     * 其它属性集，key为属性名，value为属性的值，请参考FusionCharts的属性说明。
     */
    private Map<String, String> otherPropertyMap = new HashMap<String, String>();

    private List<FCSet>         fcSetList;
    //以下是其它属性,大部分是可选属性，非必填，但是，不填时，显示的效果不太好。
    private String              seriesName;
    private String              color;
    private String              showValues;

    public String toXMLString() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("<" + KEY);
        sBuffer.append(FusionChartsUtil.makeProperty2String("seriesName", seriesName));
        sBuffer.append(FusionChartsUtil.makeProperty2String("color", color));
        sBuffer.append(FusionChartsUtil.makeProperty2String("showValues", showValues));
        sBuffer.append(FusionChartsUtil.makePropertyMap2String(otherPropertyMap));
        sBuffer.append(">");
        for (FCSet fcSet : fcSetList) {
            sBuffer.append(fcSet.toXMLString());
        }
        sBuffer.append("</" + KEY + ">");
        return sBuffer.toString();
    }

    public List<FCSet> getFcSetList() {
        return fcSetList;
    }

    public void setFcSetList(List<FCSet> fcSetList) {
        this.fcSetList = fcSetList;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShowValues() {
        return showValues;
    }

    public void setShowValues(String showValues) {
        this.showValues = showValues;
    }

    public Map<String, String> getOtherPropertyMap() {
        return otherPropertyMap;
    }

    public void setOtherPropertyMap(Map<String, String> otherPropertyMap) {
        this.otherPropertyMap = otherPropertyMap;
    }

}
