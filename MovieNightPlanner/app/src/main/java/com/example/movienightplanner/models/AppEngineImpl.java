package com.example.movienightplanner.models;

import android.util.Log;
import android.app.AlertDialog;

import com.example.movienightplanner.database.DBHelper;
import com.example.movienightplanner.models.helper.DateFormats;
import com.example.movienightplanner.views.MainActivity;

import android.content.DialogInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Context;

public class AppEngineImpl implements AppEngine {

    // set up singleton
    private static AppEngineImpl sharedInstance;

    public List<MovieImpl> moviesList;
    public List<EventImpl> eventLists;

    private boolean dataRead = false;

    public static AppEngineImpl getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new AppEngineImpl();
        }
        return sharedInstance;
    }

    @Override
    public void startUp(Context context) {
        DBHelper dbHelper = new DBHelper(context);

        this.moviesList = dbHelper.getAllMovies();
        this.eventLists = dbHelper.getAllEvents();

        if(moviesList.isEmpty() && eventLists.isEmpty()) {
            initMovieList(context, dbHelper);
            initEventsList(context, dbHelper);
            this.moviesList = dbHelper.getAllMovies();
            this.eventLists = dbHelper.getAllEvents();
        }

        for (EventImpl event : eventLists) {
            //update movies
            if(event.getMovieId() != null) {
                for(MovieImpl movie : moviesList) {
                    if(new Integer(movie.getId()).equals(event.getMovieId())) {
                        event.setMovie(movie);
                    }
                }
            }
            //update attendees
            List<String> attendeeList = new ArrayList<String>();
            for (String attendee : dbHelper.getAllAttendeesOfAnEvent(new Integer(event.getId()))) {
                attendeeList.add(attendee);
            }
            event.setAttendees(attendeeList);
        }

        ascendEvents();
        //we only read the txt files once
        dataRead = true;
    }

    @Override
    public boolean getDataRead() {
        return dataRead;
    }

    @Override
    public void initMovieList(Context context, DBHelper dbHelper) {
        for (String line : readTextFile("movies.txt", context)) {
            String[] splitedLine = line.split("\",\"");
            //add lengeth check
            if (splitedLine.length == 4) {
                MovieImpl movie = new MovieImpl(splitedLine[0].replace("\"", ""), splitedLine[1].replace("\"", ""), splitedLine[2].replace("\"", ""), splitedLine[3].replace("\"", ""));

                //add to database
                dbHelper.insertMovie(movie.getTittle(), movie.getYear(), movie.getPosterImageName());

            } else {
                showAlert("read txt error occurred", context);
            }
        }
    }

    @Override
    public void initEventsList(Context context, DBHelper dbHelper) {
        for (String line : readTextFile("events.txt", context)) {
            String[] splitedLine = line.split("\",\"");
            //add length check
            if (splitedLine.length == 6) {
                //the latitude and longitude was split, so I add them up again
                EventImpl newEvent = new EventImpl(splitedLine[0].replace("\"", ""), splitedLine[1].replace("\"", ""), splitedLine[2].replace("\"", ""), splitedLine[3].replace("\"", ""), splitedLine[4].replace("\"", ""), splitedLine[5].replace("\"", ""));
                newEvent.setDateTime(convertToDate(newEvent.getStartDate()));

                //add to database
                dbHelper.insertEvent(newEvent.getTittle(),newEvent.getStartDate(),newEvent.getEndDate(),newEvent.getVenue(),newEvent.getLocation());
            } else {
                showAlert("read txt error occurred", context);
            }
        }
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

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            String TAG = MainActivity.class.getName();
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            String TAG = MainActivity.class.getName();
            Log.d(TAG, ex.getMessage());
        }
        return lines;
    }

    @Override
    public Date convertToDate(String inputDate) {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat(DateFormats.DATETIME_FORMAT);
        try {
            date = formater.parse(inputDate);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        return date;
    }

    @Override
    public void ascendEvents() {
        Collections.sort(eventLists, new Comparator<EventImpl>() {
            public int compare(EventImpl o1, EventImpl o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
    }

    @Override
    public void descendEvents() {
        Collections.sort(eventLists, new Comparator<EventImpl>() {
            public int compare(EventImpl o1, EventImpl o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });
    }

    @Override
    public boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormats.DATETIME_FORMAT);
        boolean flag = true;

        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }

    @Override
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
