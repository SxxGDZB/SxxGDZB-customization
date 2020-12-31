package com.kiwihouse.common.utils;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yjzn
 * @date 2020-01-02-下午 2:28
 */
public class TimeUtil {

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * second为负或正，获取当前时间前或后second秒的时间
     *
     * @param currentTime 当前时间
     * @param second      前或后多少秒
     * @return
     */
    public static String getPassSecTime(String currentTime, int second) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = new GregorianCalendar();
        Date parse = null;
        try {
            parse = df.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(parse != null ? parse : new Date());
        c.add(Calendar.SECOND, second);//把日期往后增加SECOND秒.整数往后推,负数往前移动
        Date date = c.getTime(); //得到的结果
        return df.format(date);
    }

    
    /* 时间戳转换成日期格式字符串 
    * @param seconds 精确到秒的字符串 
    * @param formatStr 
    * @return 
    */  
   public static String timeStamp2Date(String seconds) {  
       if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
           return "";  
       }  
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
       return sdf.format(new Date(Long.valueOf(seconds+"000")));  
   }  
 

    /**
     * time to timestamp
     *
     * @param time 时间字符串
     * @return 时间戳
     */
    public static Long dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 校验日期是否符合格式(yyyy-MM-dd)
     *
     * @param dateString 日期字符串
     * @return 是否校验成功
     */
    public static boolean isValidDate(String dateString) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007-03-01
            format.setLenient(false);
            format.parse(dateString);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 校验时间是否符合格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param TimeString 时间字符串
     * @return 是否校验成功
     */
    public static boolean isValidTime(String TimeString) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007-03-01
            format.setLenient(false);
            format.parse(TimeString);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 校验时间是否符合格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param tod <==> Time Of Day 时间字符串
     * @return 是否校验成功
     */
    public static boolean isValidTod(String tod) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007-03-01
            format.setLenient(false);
            format.parse(tod);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    /**
     * 获取该时间段内以天为区分的list
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getDayList(String startTime, String endTime) {
        List<String> betweenTime = new ArrayList<>();
        try {
            Date sdate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
            Date edate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

            SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sdate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            sCalendar.set(year, month, day, 0, 0, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            eCalendar.set(year, month, day, 0, 0, 0);

            while (sCalendar.before(eCalendar)) {
                String format = outformat.format(sCalendar.getTime());
                betweenTime.add(format);
                sCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            //包括最后一天
            //betweenTime.add(outformat.format(eCalendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }

    /**
     * 获取该时间段以时为区分的list
     *
     * @param startTime 开始日期
     * @return
     */
    public static List<String> getHourLists(String startTime, String endTime) {
        List<String> betweenTime = new ArrayList<>();

        try {
            Date sdate = new SimpleDateFormat("yyyy-MM-dd HH").parse(startTime);
            Date edate = new SimpleDateFormat("yyyy-MM-dd HH").parse(endTime);
            SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd HH");

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sdate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            int hour = sCalendar.get(Calendar.HOUR_OF_DAY);
            sCalendar.set(year, month, day, hour, 0, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            hour = eCalendar.get(Calendar.HOUR_OF_DAY);
            eCalendar.set(year, month, day, hour, 0, 0);

            while (sCalendar.before(eCalendar)) {
                String format = outformat.format(sCalendar.getTime());
                betweenTime.add(format);
                sCalendar.add(Calendar.HOUR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }

    /**
     * 获取该时间段以时为区分的list
     *
     * @param startTime 开始日期
     * @return
     */
    public static List<String> getMinLists(String startTime, String endTime) {
        List<String> betweenTime = new ArrayList<>();

        try {
            Date sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime);
            Date edate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime);
            SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sdate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            int hour = sCalendar.get(Calendar.HOUR_OF_DAY);
            int min = sCalendar.get(Calendar.MINUTE);
            sCalendar.set(year, month, day, hour, min, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            hour = eCalendar.get(Calendar.HOUR_OF_DAY);
            min = eCalendar.get(Calendar.MINUTE);
            eCalendar.set(year, month, day, hour, min, 0);

            while (sCalendar.before(eCalendar)) {
                String format = outformat.format(sCalendar.getTime());
                betweenTime.add(format);
                sCalendar.add(Calendar.MINUTE, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }


    /**
     * 获取该时间段内以天为区分的list
     *
     * @param startTime
     * @param endTime
     * @param verifyStartTime
     * @param verifyEndTime
     * @return
     */
    public static List<String> getDayList(String startTime, String endTime, String verifyStartTime, String verifyEndTime) {
        List<String> betweenTime = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sdate = sdf.parse(startTime);
            Date edate = sdf.parse(endTime);
            Date vStartDate = sdf.parse(verifyStartTime);
            Date vEndDate = sdf.parse(verifyEndTime);

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sdate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            sCalendar.set(year, month, day, 0, 0, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            eCalendar.set(year, month, day, 0, 0, 0);

            while (sCalendar.before(eCalendar)) {
                if (sCalendar.getTime().before(vStartDate) || sCalendar.getTime().after(vEndDate)) {
                    sCalendar.add(Calendar.DAY_OF_YEAR, 1);
                    continue;
                }
                String format = sdf.format(sCalendar.getTime());
                betweenTime.add(format);
                sCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            //包括最后一天
            betweenTime.add(sdf.format(eCalendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }

    /**
     * 获取该时间段以时为区分的list
     *
     * @param startTime       开始日期
     * @param endTime
     * @param verifyStartTime
     * @param verifyEndTime
     * @return
     */
    public static List<String> getHourLists(String startTime, String endTime, String verifyStartTime, String verifyEndTime) {
        List<String> betweenTime = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            Date sdate = sdf.parse(startTime);
            Date edate = sdf.parse(endTime);
            Date vStartDate = sdf.parse(verifyStartTime);
            Date vEndDate = sdf.parse(verifyEndTime);

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sdate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            int hour = sCalendar.get(Calendar.HOUR_OF_DAY);
            sCalendar.set(year, month, day, hour, 0, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            hour = eCalendar.get(Calendar.HOUR_OF_DAY);
            eCalendar.set(year, month, day, hour, 0, 0);

            while (sCalendar.before(eCalendar)) {
                if (sCalendar.getTime().before(vStartDate) || sCalendar.getTime().after(vEndDate)) {
                    sCalendar.add(Calendar.HOUR, 1);
                    continue;
                }
                String format = sdf.format(sCalendar.getTime());
                betweenTime.add(format);
                sCalendar.add(Calendar.HOUR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }


    /**
     * 获取该时间段以时为区分的list
     *
     * @param startTime       开始日期
     * @param endTime         借宿日期
     * @param verifyStartTime 校验开始日期
     * @param verifyEndTime   校验借宿日期
     * @return
     */
    public static List<String> getMinLists(String startTime, String endTime, String verifyStartTime, String verifyEndTime) {
        List<String> betweenTime = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date sdate = sdf.parse(startTime);
            Date edate = sdf.parse(endTime);
            Date vStartDate = sdf.parse(verifyStartTime);
            Date vEndDate = sdf.parse(verifyEndTime);

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sdate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            int hour = sCalendar.get(Calendar.HOUR_OF_DAY);
            int min = sCalendar.get(Calendar.MINUTE);
            sCalendar.set(year, month, day, hour, min, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            hour = eCalendar.get(Calendar.HOUR_OF_DAY);
            min = eCalendar.get(Calendar.MINUTE);
            eCalendar.set(year, month, day, hour, min, 0);

            while (sCalendar.before(eCalendar)) {
                if (sCalendar.getTime().before(vStartDate) || sCalendar.getTime().after(vEndDate)) {
                    sCalendar.add(Calendar.MINUTE, 1);
                    continue;
                }
                String format = sdf.format(sCalendar.getTime());
                betweenTime.add(format);
                sCalendar.add(Calendar.MINUTE, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }


    /**
     * 以endDate开始,往前倒退minNum分钟
     *
     * @param endDate 结束时间
     * @param minNum  区间个数
     * @return
     */
    public static List<String> getMinList(String endDate, int minNum, boolean asc) {
        List<String> betweenTime = new ArrayList<>();

        try {
            Date edate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate);
            SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(edate);
            int year = eCalendar.get(Calendar.YEAR);
            int month = eCalendar.get(Calendar.MONTH);
            int day = eCalendar.get(Calendar.DATE);
            int hour = eCalendar.get(Calendar.HOUR_OF_DAY);
            int min = eCalendar.get(Calendar.MINUTE);
            int second = eCalendar.get(Calendar.SECOND);

            eCalendar.set(year, month, day, hour, min, second);
            if (asc) {
                eCalendar.add(Calendar.MINUTE, -1 * (minNum-1));
            }
            for (int i = 0; i < minNum; i++) {
                String format = outformat.format(eCalendar.getTime());
                betweenTime.add(format);
                if (asc) {
                    eCalendar.add(Calendar.MINUTE, 1);
                } else {
                    eCalendar.add(Calendar.MINUTE, -1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }


    /**
     * 格式化时间
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Calendar parseFromDateFormat(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar d = Calendar.getInstance();
            d.setTime(sdf.parse(dateStr));
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转字符串
     *
     * @param c      日期
     * @param format 格式
     * @return
     */
    public static String calendarToString(Calendar c, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
	 * 得到日期之间的日期
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getBetweenDates(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    result.add(start);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    //result.add(end);
	    return result;
	}
	/**
	 * str --->date
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate,String fmt) {//
		    SimpleDateFormat formatter = new SimpleDateFormat(fmt);
		    ParsePosition pos = new ParsePosition(0);
		    Date strtodate = formatter.parse(strDate, pos);
		    return strtodate;
	}
	
	
	
	/**
	 * date ---> str
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    String dateString = formatter.format(dateDate);
		    return dateString;
	}
	
	public static void main(String[] args) {
		List<String> list = getDayList("2020-12-05", "2020-12-28");
		System.out.println(list.toString());
	}
}
