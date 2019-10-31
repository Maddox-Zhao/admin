package com.huaixuan.network.biz.domain.report;

import java.util.List;

/**
 * 多列数据的报表的FusionCharts对象,调toXMLString() 方法获取数据转换成XML。
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
