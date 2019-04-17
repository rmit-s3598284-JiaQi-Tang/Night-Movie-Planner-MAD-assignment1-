package com.example.movienightplanner.models;

import android.content.Context;

import java.util.Date;
import java.util.List;

public interface AppEngine {

    public boolean getDataRead();

    public void startUp(Context context);

    public List<EventImpl> initEventsList(Context context);

    public List<MovieImpl> initMovieList(Context context);

    public List<String> readTextFile(String fileName, Context context);

    public Date convertToDate(String inputDate);

    public void ascendEvents();

    public void descendEvents();

    public boolean isValidDate(String date);

    public void showAlert(String message, Context context);

}
