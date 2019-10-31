package com.huaixuan.network.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.huaixuan.network.biz.domain.base.Constants;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {
    private static Log          log               = LogFactory.getLog(DateUtil.class);

    private static final String TIME_PATTERN      = "HH:mm";

    // ������
    public static final String  dtSimple          = "yyyy-MM-dd";

    private final static String DAY_FORMAT        = "yyyyMMdd";

    private final static String DATE_FORMAT       = "yyyy-MM-dd";

    //  ������
    public static final String  dtSimpleChinese   = "yyyy��MM��dd��";

    public static final String  dtSimpleChineseMD = "MM&#26376;dd&#26085;";

    /**
     * ������ֱ��ת��������
     *
     * @param batchDate
     *            Date
     * @return StringBuffer
     * @author chenyan 2009/07/31
     */
    public static StringBuffer convertDateToBatch(Date batchDate) {
        StringBuffer batch = new StringBuffer();

        if (batchDate == null) {
            return null;
        }

        HashMap<Integer, String> dateYearBatch = new HashMap<Integer, String>();
        HashMap<Integer, String> dateMonthBatch = new HashMap<Integer, String>();
        dateYearBatch.put(2009, "A");
        dateYearBatch.put(2010, "B");
        dateYearBatch.put(2011, "C");
        dateYearBatch.put(2012, "D");
        dateYearBatch.put(2013, "E");
        dateYearBatch.put(2014, "F");
        dateYearBatch.put(2015, "G");
        dateYearBatch.put(2016, "H");
        dateYearBatch.put(2017, "J");
        dateYearBatch.put(2018, "K");
        dateYearBatch.put(2019, "L");
        dateYearBatch.put(2020, "M");
        dateYearBatch.put(2021, "N");
        dateYearBatch.put(2022, "P");
        dateYearBatch.put(2023, "Q");
        dateYearBatch.put(2024, "R");
        dateYearBatch.put(2025, "S");
        dateYearBatch.put(2026, "T");
        dateYearBatch.put(2027, "U");
        dateYearBatch.put(2028, "V");
        dateYearBatch.put(2029, "W");
        dateYearBatch.put(2030, "X");
        dateYearBatch.put(2031, "Y");

        dateMonthBatch.put(1, "A");
        dateMonthBatch.put(2, "B");
        dateMonthBatch.put(3, "C");
        dateMonthBatch.put(4, "D");
        dateMonthBatch.put(5, "E");
        dateMonthBatch.put(6, "F");
        dateMonthBatch.put(7, "G");
        dateMonthBatch.put(8, "H");
        dateMonthBatch.put(9, "J");
        dateMonthBatch.put(10, "K");
        dateMonthBatch.put(11, "L");
        dateMonthBatch.put(0, "M");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(batchDate);

        batch.append(dateYearBatch.get(calendar.get(Calendar.YEAR)) == null ? "" : dateYearBatch
            .get(calendar.get(Calendar.YEAR)));
        batch.append(dateMonthBatch.get(calendar.get(Calendar.MONTH)) == null ? "" : dateMonthBatch
            .get(calendar.get(Calendar.MONTH)));
        batch.append(calendar.get(Calendar.DAY_OF_MONTH));
        return batch;
    }

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    public DateUtil() {
    }

    // Timestamp��String֮��ת���ĺ�����
    public static String getTimestampToString(Timestamp obj) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �����ʽ������ʾ����
        String str = df.format(obj);
        return str;
    }

    // Stringת��ΪTimestamp:
    public static Timestamp getStringToTimestamp(String str) {
        Timestamp ts = Timestamp.valueOf(str);
        return ts;
    }

    // Date��String֮��ת���ĺ�����
    public static String getDateToString(Date obj) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �����ʽ������ʾ����
        String str = df.format(obj);
        return str;
    }
    
    //get Today To yyyy-MM-dd
    public static String getTodayToString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �����ʽ������ʾ����
        String str = df.format(new Date());
        return str;
    }

    public static Date strToDate(String str, String pattern) {
        Date dateTemp = null;
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        try {
            dateTemp = formater2.parse(str);
        } catch (Exception e) {
            log.error("exception in convert string to date!");
        }
        return dateTemp;
    }

    /**
     * Return default datePattern (MM/dd/yyyy)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString(
                "date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate
     *            date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     *
     * @param aMask
     *            the date pattern the string is in
     * @param strDate
     *            a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException
     *             when String doesn't match the expected format
     */
    public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     * a
     *
     * @param theTime
     *            the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws ParseException
     *             when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask
     *            the date pattern the string is in
     * @param aDate
     *            a date object
     * @return a formatted string representation of the date
     *
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     *
     * @param aDate
     *            A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate
     *            the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException
     *             when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            log.error(pe.getMessage());
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /**
     *
     * @param aDate
     * @return
     */
    public static String convertDateToString(String pattern, Date aDate) {
    	if(aDate == null)
    		return null;
        return getDateTime(pattern, aDate);
    }

    public static Date getDate(Date startdate, int days) {
        Date dateresult = startdate;
        try {
            GregorianCalendar cal = new GregorianCalendar();

            cal.setTime(new Date());
            cal.add(GregorianCalendar.DAY_OF_MONTH, days);
            dateresult = cal.getTime();
        } catch (Exception e) {
            System.out.println("exception" + e.toString());
        }
        return dateresult;
    }

    /**
     * ��ȡ�������ڵ��������
     *
     * @param dt
     * @param idiff
     *
     * @return
     */
    public static final String getDiffDate(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();

        c.setTime(dt);
        c.add(Calendar.DATE, idiff);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * ��ȡ�������ڵ��������YYYYMMDD
     *
     * @param dt
     * @param idiff
     *
     * @return
     */
    public static final String getDiffDateDayFormat(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();

        c.setTime(dt);
        c.add(Calendar.DATE, idiff);
        return dtDayFormatFormat(c.getTime());
    }

    /**
     * ��ȡ���������·ݵ��������
     *
     * @param dt
     * @param idiff
     * @return
     */
    public static final String getDiffMon(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, idiff);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimple).format(date);
    }

    /**
     * yyyyMMdd
     *
     * @param date
     *
     * @return
     */
    public static final String dtDayFormatFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(DAY_FORMAT).format(date);
    }

    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * �õ���ʽ����ĵ�ǰϵͳ���ڣ���ʽΪyyyyMMdd����20060215
     *
     * @see #getFormatDate(java.util.Date)
     * @return String ���ظ�ʽ����ĵ�ǰ������ϵͳ���ڣ���ʽΪyyyyMMdd����20060215
     */
    public static String getCurrDateStr() {
        return getFormatDate(getCurrDate(), DAY_FORMAT);
    }

    /**
     * ȡ�õ�ǰϵͳʱ�䣬����java.util.Date����
     *
     * @see java.util.Date
     * @return java.util.Date ���ط�������ǰϵͳʱ��
     */
    public static java.util.Date getCurrDate() {
        return new java.util.Date();
    }

    /**
     * ���ݸ�ʽ�õ���ʽ���������
     *
     * @param currDate
     *            Ҫ��ʽ��������
     * @param format
     *            ���ڸ�ʽ����yyyy-MM-dd
     * @see java.text.SimpleDateFormat#format(java.util.Date)
     * @return String ���ظ�ʽ��������ڣ���ʽ�ɲ���<code>format</code>
     *         ���壬��yyyy-MM-dd����2006-02-15
     */
    public static String getFormatDate(java.util.Date currDate, String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * �������ڲ�ֵ
     *
     * @param String
     * @param String
     * @return int��������
     */
    public static final int calculateDecreaseDate(String beforDate, String afterDate)
                                                                                     throws ParseException {
        Date date1 = getFormat(dtSimple).parse(beforDate);
        Date date2 = getFormat(dtSimple).parse(afterDate);
        long decrease = getDateBetween(date1, date2) / 1000 / 3600 / 24;
        int dateDiff = (int) decrease;
        return dateDiff;
    }

    /**
     * ����ʱ���
     *
     * @param dBefor
     *            ����
     * @param dAfter
     *            β��
     * @return ʱ���(����)
     */
    public static final long getDateBetween(Date dBefor, Date dAfter) {
        long lBefor = 0;
        long lAfter = 0;
        long lRtn = 0;

        /** ȡ�þ��� 1970��1��1�� 00:00:00 GMT �ĺ����� */
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();

        lRtn = lAfter - lBefor;

        return lRtn;
    }

    /**
     * ���������������������ȡ����
     *
     * @param dateStart
     *            һ��ǰ��С�ں���dateEnd
     * @param dateEnd
     *
     * @return
     */
    public static int countDays(Date dateStart, Date dateEnd) {
        if ((dateStart == null) || (dateEnd == null)) {
            return -1;
        }

        return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * ͳ������ʱ�����ص�������(��24Сʱ��һ�죬����24Сʱ��Ϊ0���������ʱ����ð�Сʱ�����ӵ�ȥ��)
     * @param begin ��ʼʱ��
     * @param end
     * @return
     */
    public static int countDays(String beginStr, String endStr, String Foramt) {
        Date end = strToDate(endStr, Foramt);
        Date begin = strToDate(beginStr, Foramt);
        long times = end.getTime() - begin.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }

    /**
     * yyyy��MM��dd��
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChinese).format(date);
    }

    /**
     * yyyy-MM-dd�� yyyy��MM��dd�� ת��
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormatStr(String date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChinese).format(string2Date(date));
    }

    /**
     * yyyy-MM-dd�� MM��dd�� ת��
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormatStrMD(String date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChineseMD).format(string2Date(date));
    }

    /**
     * yyyy-MM-dd �����ַ�ת��Ϊʱ��
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date string2Date(String stringDate) {
        if (stringDate == null) {
            return null;
        }

        try {
            return getFormat(dtSimple).parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

	/**
	 * ȡ�����������ܵĵ�һ��
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstWeekDay(Date date) {
		initCalendar(date);
		gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return gc.getTime();
	}

	/**
	 * ȡ�����������ܵ����һ��
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastWeekDay(Date date) {
		initCalendar(date);
		gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return gc.getTime();
	}

	/**
	 * ȡ�����������µ����һ��
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstMonthDay(Date date) {
		initCalendar(date);
		int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		gc.add(Calendar.DAY_OF_MONTH, 1 - dayOfMonth);
		return gc.getTime();
	}

	/**
	 * ȡ�����������µ����һ��
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastMonthDay(Date date) {
		initCalendar(date);
		int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		int maxDaysOfMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
		gc.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth - dayOfMonth);
		return gc.getTime();
	}

	/**
	 * ȡ����������Ѯ�����һ��
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstTenDaysDay(Date date) {
		initCalendar(date);
		int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth <= 10) {
			gc.set(Calendar.DAY_OF_MONTH, 1);
		} else if (dayOfMonth > 20) {
			gc.set(Calendar.DAY_OF_MONTH, 21);
		} else {
			gc.set(Calendar.DAY_OF_MONTH, 11);
		}
		return gc.getTime();
	}

	/**
	 * ȡ����������Ѯ�����һ��
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastTenDaysDay(Date date) {
		initCalendar(date);
		int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth <= 10) {
			gc.set(Calendar.DAY_OF_MONTH, 10);
		} else if (dayOfMonth > 20) {
			gc.set(Calendar.DAY_OF_MONTH, gc
					.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else {
			gc.set(Calendar.DAY_OF_MONTH, 19);
		}
		return gc.getTime();
	}

	private static void initCalendar(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("argument date must be not null");
		}

		gc.clear();
		gc.setTime(date);
	}

	private static GregorianCalendar gc = null;
	static {
		gc = new GregorianCalendar(Locale.CHINA);
		gc.setLenient(true);
		gc.setFirstDayOfWeek(Calendar.MONDAY);
	}

	/**
	 * ʱ����ַ���
	 * @return
	 */
	public static String dateline(){
		Date date = new Date();
		long time = date.getTime();

		// ʱ���ֻ��10λ Ҫ������
		String dateline = time + "";
		return dateline.substring(0, 10);
	}

	/** */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		// c.set(Calendar.MONTH, 7);
		// c.set(Calendar.DAY_OF_MONTH, 9);
//		Date date = c.getTime();
//
//		System.out.println("getFirstWeekDay = " + getDateToString(getFirstWeekDay(date)));
//		System.out.println("getLastWeekDay = " + getDateToString(getLastWeekDay(date)));
//		System.out.println("getFirstMonthDay = " + getDateToString(getFirstMonthDay(date)));
//		System.out.println("getLastMonthDay = " + getDateToString(getLastMonthDay(date)));
//		System.out.println("getFirstTenDaysDay = " + getDateToString(getFirstTenDaysDay(date)));
//		System.out.println("getLastTenDaysDay = " + getDateToString(getLastTenDaysDay(date)));
		System.out.println("ʱ����� " + dateline());
	}

}
