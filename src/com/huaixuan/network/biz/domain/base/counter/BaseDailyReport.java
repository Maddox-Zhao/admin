/**
 * created since 2010-6-7
 */
package com.huaixuan.network.biz.domain.base.counter;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;



/**
 * @author guoyj
 * @version $Id: BaseInputFile.java,v 0.1 2010-6-7 ÏÂÎç2:23:48 guoyj Exp $
 */
public class BaseDailyReport extends BaseObject implements Serializable {
   
    private static final long serialVersionUID = -7291506382945771585L;

    private Long id;
    
    private String reportType;
    
    private String reportDate;
    
    private String title;
    
    private long value1;
    
    private long value2;
    
    private long value3;
    
    private long value4;
    
    private long value5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getValue1() {
        return value1;
    }

    public void setValue1(long value1) {
        this.value1 = value1;
    }

    public long getValue2() {
        return value2;
    }

    public void setValue2(long value2) {
        this.value2 = value2;
    }

    public long getValue3() {
        return value3;
    }

    public void setValue3(long value3) {
        this.value3 = value3;
    }

    public long getValue4() {
        return value4;
    }

    public void setValue4(long value4) {
        this.value4 = value4;
    }

    public long getValue5() {
        return value5;
    }

    public void setValue5(long value5) {
        this.value5 = value5;
    }
    
}
