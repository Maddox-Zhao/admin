package com.huaixuan.network.biz.domain.report;

import java.util.HashMap;
import java.util.Map;

/**
 * �������,��toXMLString() ������ȡ����ת����XML��
 * @author yuancong
 * @version $Id: FCChart.java,v 0.1 Mar 10, 2009 6:04:45 PM yuancong Exp $
 */
public class FCChart {
    /**
     * <chart palette="1" caption="Product Comparison" shownames="1" showvalues="0" numberPrefix="$" showSum="1" decimals="0" overlapColumns="0">
     */
    public final static String    KEY              = "chart";
    /**
     * �������Լ���keyΪ��������valueΪ���Ե�ֵ����ο�FusionCharts������˵����
     */
    protected Map<String, String> otherPropertyMap = new HashMap<String, String>();

    /*
     * ����ı���
     */
    private String                caption;
    /*
     * ����ĸ�����
     */
    private String                subCaption;
    //���������Ŀ�����ʾ����
    private String                shownames;
    private String                showvalues;
    private String                decimals;
    private String                numberPrefix;
    /**
     * �Ƿ�ʹ������Բ��Ч����1����ʾʹ�ã�0����ʾ��ʹ��
     */
    private String                useRoundEdges;
    /**
     * Ĭ�������С
     */
    private String                baseFontSize;
    //--------------
    private String                palette;
    private String                showSum;
    private String                overlapColumns;

    //********************
    private String                xAxisName;
    private String                yAxisName;
    private String                formatNumberScale;
    private String                chartRightMargin;

    public String toXMLString() {
        return toXMLStringPrevious() + toXMLStringFollow();
    }

    public String toXMLStringPrevious() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("<" + KEY);
        sBuffer.append(FusionChartsUtil.makeProperty2String("caption", caption));
        sBuffer.append(FusionChartsUtil.makeProperty2String("subCaption", subCaption));
        sBuffer.append(FusionChartsUtil.makeProperty2String("shownames", shownames));
        sBuffer.append(FusionChartsUtil.makeProperty2String("showvalues", showvalues));
        sBuffer.append(FusionChartsUtil.makeProperty2String("decimals", decimals));
        sBuffer.append(FusionChartsUtil.makeProperty2String("numberPrefix", numberPrefix));
        sBuffer.append(FusionChartsUtil.makeProperty2String("palette", palette));
        sBuffer.append(FusionChartsUtil.makeProperty2String("showSum", showSum));
        sBuffer.append(FusionChartsUtil.makeProperty2String("overlapColumns", overlapColumns));
        sBuffer.append(FusionChartsUtil.makeProperty2String("xAxisName", xAxisName));
        sBuffer.append(FusionChartsUtil.makeProperty2String("yAxisName", yAxisName));
        sBuffer
            .append(FusionChartsUtil.makeProperty2String("formatNumberScale", formatNumberScale));
        sBuffer.append(FusionChartsUtil.makeProperty2String("chartRightMargin", chartRightMargin));
        sBuffer.append(FusionChartsUtil.makeProperty2String("useRoundEdges", useRoundEdges));
        sBuffer.append(FusionChartsUtil.makeProperty2String("baseFontSize", baseFontSize));
        sBuffer.append(FusionChartsUtil.makePropertyMap2String(otherPropertyMap));
        sBuffer.append(">");
        return sBuffer.toString();
    }

    public String toXMLStringFollow() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("</" + KEY + ">");
        return sBuffer.toString();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getShownames() {
        return shownames;
    }

    public void setShownames(String shownames) {
        this.shownames = shownames;
    }

    public String getShowvalues() {
        return showvalues;
    }

    public void setShowvalues(String showvalues) {
        this.showvalues = showvalues;
    }

    public String getDecimals() {
        return decimals;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getNumberPrefix() {
        return numberPrefix;
    }

    public void setNumberPrefix(String numberPrefix) {
        this.numberPrefix = numberPrefix;
    }

    public String getPalette() {
        return palette;
    }

    public void setPalette(String palette) {
        this.palette = palette;
    }

    public String getShowSum() {
        return showSum;
    }

    public void setShowSum(String showSum) {
        this.showSum = showSum;
    }

    public String getOverlapColumns() {
        return overlapColumns;
    }

    public void setOverlapColumns(String overlapColumns) {
        this.overlapColumns = overlapColumns;
    }

    public String getXAxisName() {
        return xAxisName;
    }

    public void setXAxisName(String axisName) {
        xAxisName = axisName;
    }

    public String getYAxisName() {
        return yAxisName;
    }

    public void setYAxisName(String axisName) {
        yAxisName = axisName;
    }

    public String getFormatNumberScale() {
        return formatNumberScale;
    }

    public void setFormatNumberScale(String formatNumberScale) {
        this.formatNumberScale = formatNumberScale;
    }

    public String getChartRightMargin() {
        return chartRightMargin;
    }

    public void setChartRightMargin(String chartRightMargin) {
        this.chartRightMargin = chartRightMargin;
    }

    public Map<String, String> getOtherPropertyMap() {
        return otherPropertyMap;
    }

    public void setOtherPropertyMap(Map<String, String> otherPropertyMap) {
        this.otherPropertyMap = otherPropertyMap;
    }

    public String getUseRoundEdges() {
        return useRoundEdges;
    }

    public void setUseRoundEdges(String useRoundEdges) {
        this.useRoundEdges = useRoundEdges;
    }

    public String getBaseFontSize() {
        return baseFontSize;
    }

    public void setBaseFontSize(String baseFontSize) {
        this.baseFontSize = baseFontSize;
    }

    public String getSubCaption() {
        return subCaption;
    }

    public void setSubCaption(String subCaption) {
        this.subCaption = subCaption;
    }
}
