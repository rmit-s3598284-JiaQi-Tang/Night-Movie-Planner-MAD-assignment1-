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

    public void ascendEvents();

    public void descendEvents();

    public List<EventImpl> get3SoonestEvents();

    public void showAlert(String message, Context context);

}
