package com.example.hugo.myapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hugo on 16/06/17.
 */

public class DateUtil {

    public static Date yesterday(){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date intToDate(int year, int month, int dayOfMonth){
        String selectedDateString = intToStringDate(dayOfMonth, month, year);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date selectedDate = new Date();
        //if fail, selectedDate will be today
        try{
            selectedDate = sdf.parse(selectedDateString);
        }catch (Exception e){

            e.printStackTrace();
        }

        return selectedDate;
    }

    public static String intToStringDate(int day, int month, int year){
        String sday, smonth, syear;
        sday = String.format("%02d", day);
        smonth = String.format("%02d", month);
        syear = String.format("%04d", year);

        return syear + "-" + smonth + "-" + sday;

    }

    public static String dateTo_ddMM(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");

        return sdf.format(date);

    }

    public static String dateTo_yyyyMMdd(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
}
