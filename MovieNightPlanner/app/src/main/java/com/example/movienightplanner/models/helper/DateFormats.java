package com.example.movienightplanner.models.helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormats {
    public static final String DATETIME_FORMAT = "d/MM/yyyy h:mm:ss";
    public static final String MONTH_FORMAT = "MMMM yyyy";
    public static final String CALENDARDATE_FORMAT = "yyyyMMdd";

    public static Date convertToDate(String inputDate) {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat(DateFormats.DATETIME_FORMAT);
        try {
            date = formater.parse(inputDate);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        return date;
    }
}
