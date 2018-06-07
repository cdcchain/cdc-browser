package com.browser.tools.common;

import com.browser.dao.entity.TblBcTransaction;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * <p>
 * Title:DateUtil
 * </p>
 * <p>
 * Description:日期工具类
 * </p>
 * 
 * @author David
 * @date 2016年8月29日上午11:45:37
 */
public class DateUtil {

	public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
	public static final String C_DATE_UTC_DEFAULT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	public static final String C_TIMES_PATTON_DEFAULT = "yyyyMMddHHmmss";
	public static final String C_DATES_PATTON_DEFAULT = "yyyyMMdd";
	public static final String T_TIMES_PATTON_DEFAULT = "HHmmss";
	
	

	public static final long C_ONE_SECOND = 1000;
	public static final long C_ONE_MINUTE = 60 * C_ONE_SECOND;
	public static final long C_ONE_HOUR = 60 * C_ONE_MINUTE;
	public static final long C_ONE_DAY = 24 * C_ONE_HOUR;

	private static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat sdf;
		if ("".equals(format) || null == format) {
			sdf = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
		} else {
			sdf = new SimpleDateFormat(format);
		}
		return sdf;
	}

	public static Date parseDate(String dateStr, String format) {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		return sdf.parse(dateStr, new ParsePosition(0));
	}

	public static Date parseDate(Date dateStr, String format) {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		String dt = sdf.format(dateStr);
		return sdf.parse(dt, new ParsePosition(0));
	}

	public static String parseStr(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date getPlusDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	public static int compareDate(String fdate, String sdate) {
		Date datef = parseDate(fdate, "yyyy-MM-dd");
		Date dates = parseDate(sdate, "yyyy-MM-dd");
		return datef.compareTo(dates);
	}

	public static boolean isCompareDate(Date date1, Date date2) {

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

	public static String parseGmt(Date date) {
		if(date == null){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
		return format.format(date);
	}
	
	public static String cd(String day,int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
		//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
		//量day格式一致
		        Date date = null;   
		        try {   
		            date = format.parse(day);   
		        } catch (Exception ex) {   
		            ex.printStackTrace();   
		        }   
		        if (date == null)   
		            return "";   
		        System.out.println("front:" + format.format(date)); //显示输入的日期  
		        Calendar cal = Calendar.getInstance();   
		        cal.setTime(date);   
		        cal.add(Calendar.HOUR_OF_DAY, x);// 24小时制   
		        date = cal.getTime();
		        cal = null;   
		        return format.format(date); 
	}
	
	public static void main(String[] args) {
		System.out.println(HourStatis());
		//System.out.println(cd("2016-12-23 12:00:00",8));
	}

	public static void trxTimeToDay(TblBcTransaction trx) {
		String disTime = "";
		try {
			String trxD = parseGmt(trx.getTrxTime());
			
			Date date = parseDate(trxD,C_TIME_PATTON_DEFAULT);

			Date now = new Date();

			long diff = now.getTime() - date.getTime();

			long second = (diff / (1000)) % 60;
			long minutes = (diff / (1000 * 60)) % 60;
			long hour = (diff / (1000 * 60 * 60)) % 24;
			long day = diff / (1000 * 60 * 60 * 24);

			if (0 != second) {
				disTime = second + " second " + disTime;
			}
			if (0 != minutes) {
				disTime = minutes + " minutes " + disTime;
			}
			if (0 != hour) {
				disTime = hour + " hour " + disTime;
			}
			if (0 != day) {
				disTime = day + " day " + disTime;
			}
			trx.setDisTime(disTime);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String HourStatis(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY,0);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		return df.format(cal.getTime());
	}

	public static void main(){
		System.out.println(HourStatis());
	}
}
