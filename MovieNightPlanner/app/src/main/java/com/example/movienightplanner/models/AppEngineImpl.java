package com.example.movienightplanner.models;

import android.util.Log;
import android.app.AlertDialog;
import com.example.movienightplanner.MainActivity;
import android.content.DialogInterface;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
public class AppEngineImpl implements AppEngine {

    // set up singleton
    private static AppEngineImpl sharedInstance;

    public List<MovieImpl> moviesList;
    public List<EventImpl> eventLists;

    public static AppEngineImpl getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new AppEngineImpl();
        }
        return sharedInstance;
    }

    @Override
    public void startUp(Context context) {
        this.moviesList = initMovieList(context);
        this.eventLists = initEventsList(context);
    }

    @Override
    public List<MovieImpl> initMovieList(Context context) {
        List<MovieImpl> list = new ArrayList<MovieImpl>();
        for (String line : readTextFile("movies.txt",context)) {
            String[] splitedLine = line.split("\",\"");
            //add lengeth check
            if(splitedLine.length == 4) {
                list.add(new MovieImpl(splitedLine[0],splitedLine[1],splitedLine[2],splitedLine[3]));
            } else {
                showAlert("read txt error occurred", context);
            }
        }
        return list;
    }
    @Override
    public List<EventImpl> initEventsList(Context context) {
        List<EventImpl> list = new ArrayList<EventImpl>();
        for (String line : readTextFile("events.txt",context)) {
            String[] splitedLine = line.split("\",\"");
            //add length check
            if(splitedLine.length == 6) {
                //the latitude and longitude was split, so I add them up again
                list.add(new EventImpl(splitedLine[0],splitedLine[1],splitedLine[2],splitedLine[3],splitedLine[4],splitedLine[5]));
            } else {
                showAlert("read txt error occurred", context);
            }

        }
        return list;
    }

    @Override
    //will get the function parameter context from main activity
    public List<String> readTextFile(String fileName, Context context) {
        String line = null;
        List<String> lines = new ArrayList<String>();
        try {
            InputStream fileInputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                lines.add(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            String TAG = MainActivity.class.getName();
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            String TAG = MainActivity.class.getName();
            Log.d(TAG, ex.getMessage());
        }
        return lines;
    }

    public void showAlert(String message, Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
