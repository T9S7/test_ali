package com.armsmart.common.utils;

import java.time.*;
import java.util.Date;

public class DateUtils {

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * @param time 时间长整型
     * @return 年,月,日 例如 1,1,1
     */
    public static String dayComparePrecise(Long time) {

        Period period = Period.between(UDateToLocalDate(new Date(time)), LocalDate.now());
         int resultDate=period.getYears();
         if(period.getYears()>=1){
             return period.getYears()+"年";
         }else if(period.getMonths()>=1){
             return period.getMonths()+"月";
         }else{
             return period.getDays()+"天";
         }
/*
        StringBuffer sb = new StringBuffer();
        sb.append(period.getYears()).append(",")
                .append(period.getMonths()).append(",")
                .append(period.getDays());
        return sb.toString();*/
    }

    /**
     * java.util.Date --> java.time.LocalDate
     * @param date
     * @return LocalDate
     */
    private static LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return  localDate;
    }
    /**
     * 时间比较
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean dateCompare(Date date1,Date date2 ){
        if (date1.after(date2)) {
            return true;
        }
        return false;
    }
}
