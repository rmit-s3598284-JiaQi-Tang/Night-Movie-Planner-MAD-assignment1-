package com.example.movienightplanner.models;

import java.util.Calendar;

public class DayBean implements Day {

    Boolean isCurrentMonth = false;
    Boolean hasEvent = false;
    int day;
    Calendar calendar;

    @Override
    public boolean getIsCurrentMonth() {
        return isCurrentMonth;
    }

    @Override
    public void setIsCurrentMonth(Boolean bool) {
        isCurrentMonth = bool;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public boolean getHasEvent() {
        return hasEvent;
    }

    @Override
    public void setHasEvent(Boolean bool) {
        this.hasEvent = bool;
    }
}
