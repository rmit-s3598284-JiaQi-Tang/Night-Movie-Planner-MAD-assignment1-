package com.example.movienightplanner.models;

import android.content.Context;

import com.example.movienightplanner.database.DBHelper;

import java.util.Date;
import java.util.List;

public interface AppEngine {

    public boolean getDataRead();

    public void startUp(Context context);

    public void initEventsList(Context context, DBHelper dbHelper);

    public void initMovieList(Context context, DBHelper dbHelper);

    public List<String> readTextFile(String fileName, Context context);

//    public Date convertToDate(String inputDate);

    public void ascendEvents();

    public void descendEvents();

    public List<EventImpl> get3SoonestEvents();

//    public boolean isValidDate(String date);

    public void showAlert(String message, Context context);

}
