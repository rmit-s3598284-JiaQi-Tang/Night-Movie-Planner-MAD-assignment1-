package com.example.movienightplanner.models;

import android.content.Context;

import java.util.List;

public interface AppEngine {
    public void startUp();
    public List<EventImpl> getEventsList(Context context);
    public List<MovieImpl> getMovieList(Context context);
    public List<String> readTextFile(String fileName, Context context);
}
