package com.example.movienightplanner.models;

import com.example.movienightplanner.helper.CustomCalendarHelper;

import java.util.Calendar;
import java.util.Date;

public class DayBean {

    Boolean isCurrentMonth = false;
    Boolean isToday = false;
    int day;
    Calendar calendar;

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setIsCurrentMonth(Boolean bool) {
        isCurrentMonth = bool;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isToday() {
        return isToday;
    }
}
