package com.huaixuan.network.biz.domain.report;

import java.util.List;

/**
 * �������ݵı����FusionCharts����,��toXMLString() ������ȡ����ת����XML��
 * @author yuancong
 * @version $Id: MultiFusionChartsPojo.java,v 0.1 Mar 10, 2009 5:24:03 PM yuancong Exp $
 */
public class MultiFusionChartsPojo extends FCChart {
    private FCCateGories    fcCateGories  = null;
    private List<FCDataSet> fcDataSetList = null;

    public String toXMLString() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(toXMLStringPrevious());
        sBuffer.append(fcCateGories.toXMLString());
        for (FCDataSet fcDataSet : fcDataSetList) {
            sBuffer.append(fcDataSet.toXMLString());
        }
        sBuffer.append(toXMLStringFollow());
        return sBuffer.toString();
    }

    public FCCateGories getFcCateGories() {
        return fcCateGories;
    }

    public void setFcCateGories(FCCateGories fcCateGories) {
        this.fcCateGories = fcCateGories;
    }

    public List<FCDataSet> getFcDataSetList() {
        return fcDataSetList;
    }

    public void setFcDataSetList(List<FCDataSet> fcDataSetList) {
        this.fcDataSetList = fcDataSetList;
    }

}
