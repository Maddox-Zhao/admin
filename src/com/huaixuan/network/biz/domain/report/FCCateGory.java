package com.huaixuan.network.biz.domain.report;

import java.util.HashMap;
import java.util.Map;

/**
 * �������������,��toXMLString() ������ȡ����ת����XML��
 * @author yuancong
 * @version $Id: FCCateGory.java,v 0.1 Mar 10, 2009 6:05:14 PM yuancong Exp $
 */

public class FCCateGory {
    /**
     *  <category label="Product A" /> 
     */
    public final static String  KEY              = "category";
    /**
     * �������Լ���keyΪ��������valueΪ���Ե�ֵ����ο�FusionCharts������˵����
     */
    private Map<String, String> otherPropertyMap = new HashMap<String, String>();
    private String              label;

    public FCCateGory(String label) {
        setLabel(label);
    }
    public FCCateGory(String label, Map<String, String> otherPropertyMap) {
        setLabel(label);
        setOtherPropertyMap(otherPropertyMap);
    }

    public String toXMLString() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("<" + KEY);
        sBuffer.append(FusionChartsUtil.makeProperty2String("label", label));
        sBuffer.append(FusionChartsUtil.makePropertyMap2String(otherPropertyMap));
        sBuffer.append(" />");
        return sBuffer.toString();
    }

    public Map<String, String> getOtherPropertyMap() {
        return otherPropertyMap;
    }

    public void setOtherPropertyMap(Map<String, String> otherPropertyMap) {
        this.otherPropertyMap = otherPropertyMap;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
