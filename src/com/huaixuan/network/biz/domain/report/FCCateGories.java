package com.huaixuan.network.biz.domain.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类集,调toXMLString() 方法获取数据转换成XML。
 * @author yuancong
 * @version $Id: FCCateGories.java,v 0.1 Mar 10, 2009 6:05:40 PM yuancong Exp $
 */
public class FCCateGories {
    //    <categories>
    //    <category label="Austria" /> 
    //    <category label="Brazil" /> 
    //    <category label="France" /> 
    //    <category label="Germany" /> 
    //    <category label="USA" /> 
    //    </categories>
    public final static String  KEY              = "categories";
    /**
     * 其它属性集，key为属性名，value为属性的值，请参考FusionCharts的属性说明。
     */
    private Map<String, String> otherPropertyMap = new HashMap<String, String>();
    private List<FCCateGory>    fcCateGoryList   = null;

    public String toXMLString() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("<" + KEY);
        sBuffer.append(FusionChartsUtil.makePropertyMap2String(otherPropertyMap));
        sBuffer.append(">");
        for (FCCateGory fcCateGory : fcCateGoryList) {
            sBuffer.append(fcCateGory.toXMLString());
        }
        sBuffer.append("</" + KEY + ">");
        return sBuffer.toString();
    }

    //以下是其它属性
    public List<FCCateGory> getFcCateGoryList() {
        return fcCateGoryList;
    }

    public void setFcCateGoryList(List<FCCateGory> fcCateGoryList) {
        this.fcCateGoryList = fcCateGoryList;
    }

    public Map<String, String> getOtherPropertyMap() {
        return otherPropertyMap;
    }

    public void setOtherPropertyMap(Map<String, String> otherPropertyMap) {
        this.otherPropertyMap = otherPropertyMap;
    }
}
