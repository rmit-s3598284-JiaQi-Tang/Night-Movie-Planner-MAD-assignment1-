package com.example.movienightplanner.models;

import java.util.Calendar;

public interface Day {

    public boolean getIsCurrentMonth();

    public void setIsCurrentMonth(Boolean bool);

    public int getDay();

    public void setDay(int day);

    public Calendar getCalendar();

    public void setCalendar(Calendar calendar);

    public boolean getHasEvent();

    public void setHasEvent(Boolean bool);
}
