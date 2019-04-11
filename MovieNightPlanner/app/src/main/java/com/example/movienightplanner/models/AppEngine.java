package com.example.movienightplanner.models;

import android.content.Context;

public interface AppEngine {
    public void startUp();
    public void readTextFile(String fileName, Context context);
}
