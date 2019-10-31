package com.huaixuan.network.biz.domain.report;

import java.util.List;

/**
 * ���б����FusionCharts����,��toXMLString() ������ȡ����ת����XML��
 * @author yuancong
 * @version $Id: FusionChartsPojo.java,v 0.1 Mar 10, 2009 5:21:49 PM yuancong Exp $
 */
public class FusionChartsPojo extends FCChart {
    private List<FCSet> fcSetList = null;

    public String toXMLString() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(toXMLStringPrevious());
        if (fcSetList != null && fcSetList.size() > 0) {
            for (FCSet fcSet : fcSetList) {
                sBuffer.append(fcSet.toXMLString() == null ? "" : fcSet.toXMLString());
            }
        }
        sBuffer.append(toXMLStringFollow());
        return sBuffer.toString();
    }

    public List<FCSet> getFcSetList() {
        return fcSetList;
    }

    public void setFcSetList(List<FCSet> fcSetList) {
        this.fcSetList = fcSetList;
    }
}
