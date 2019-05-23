package com.example.movienightplanner.models.helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormats {
    public static final String DATETIME_FORMAT = "d/MM/yyyy h:mm:ss a";
    public static final String MONTH_FORMAT = "MMMM yyyy";
    public static final String CALENDARDATE_FORMAT = "yyyyMMdd";

    public static Date convertToDate(String inputDate) {
        Date date = new Date();
        SimpleDateFormat date12Format = new SimpleDateFormat(DateFormats.DATETIME_FORMAT, Locale.ENGLISH);
        try {
            date = date12Format.parse(inputDate);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        return date;
    }
    public static boolean isValidDate(String date) {
        SimpleDateFormat date12Format = new SimpleDateFormat(DateFormats.DATETIME_FORMAT, Locale.ENGLISH);
        boolean flag = true;

        try {
            date12Format.parse(date);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }
}
