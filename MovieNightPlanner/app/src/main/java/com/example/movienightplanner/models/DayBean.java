package com.example.movienightplanner.models;

import java.util.Calendar;

public class DayBean {

    Boolean isCurrentMonth = false;
    Boolean hasEvent = false;
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

    public boolean hasEvent() {
        return hasEvent;
    }

    public void setHasEvent(Boolean bool) {
        this.hasEvent = bool;
    }
}
