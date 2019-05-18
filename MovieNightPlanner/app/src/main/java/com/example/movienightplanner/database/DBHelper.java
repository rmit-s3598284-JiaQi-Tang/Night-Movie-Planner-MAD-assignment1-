package com.example.movienightplanner.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.models.MovieImpl;

import static com.example.movienightplanner.models.helper.DateFormats.convertToDate;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";

    //define table and column names
    public static final String EVENTS_TABLE_NAME = "events";
    public static final String EVENTS_COLUMN_ID = "eventId";
    public static final String EVENTS_COLUMN_TITLE = "eventTitle";
    public static final String EVENTS_COLUMN_STARTDATE = "eventStartDate";
    public static final String EVENTS_COLUMN_ENDDATE = "eventEndDate";
    public static final String EVENTS_COLUMN_VENUE= "eventVenue";
    public static final String EVENTS_COLUMN_LOCATION = "eventLocation";
    public static final String EVENTS_COLUMN_MOVIEID = "movieId";

    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "movieId";
    public static final String MOVIES_COLUMN_TITLE = "movieTitle";
    public static final String MOVIES_COLUMN_YEAR = "movieYear";
    public static final String MOVIES_COLUMN_POSTERIMAGENAME = "moviePosterImageName";

    public static final String ATTENDANCE_TABLE_NAME = "attendance";
    public static final String ATTENDANCE_COLUMN_EVENTID = "eventId";
    public static final String ATTENDANCE_COLUMN_ATTENDEESNAME = "attendeesName";


    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // generate tables with SQL queries
        db.execSQL(
                "create table " + MOVIES_TABLE_NAME +
                        "("+ MOVIES_COLUMN_ID + " integer primary key, " + MOVIES_COLUMN_TITLE +" text," + MOVIES_COLUMN_YEAR + " text, " + MOVIES_COLUMN_POSTERIMAGENAME + " text, UNIQUE (" + MOVIES_COLUMN_ID +"," + MOVIES_COLUMN_TITLE + ") ON CONFLICT REPLACE)"
        );
        db.execSQL(
                "create table " + EVENTS_TABLE_NAME +
                        "(" + EVENTS_COLUMN_ID + " integer primary key, " + EVENTS_COLUMN_TITLE + " text," + EVENTS_COLUMN_STARTDATE + " text, " + EVENTS_COLUMN_ENDDATE + " text, " + EVENTS_COLUMN_VENUE + " text," + EVENTS_COLUMN_LOCATION + " text," + EVENTS_COLUMN_MOVIEID + " integer, foreign key(" + EVENTS_COLUMN_MOVIEID +") REFERENCES " + MOVIES_TABLE_NAME + "("+ MOVIES_COLUMN_ID + "),UNIQUE (" + EVENTS_COLUMN_ID +"," + EVENTS_COLUMN_TITLE + ") ON CONFLICT REPLACE)"
        );
        db.execSQL(
                "create table " + ATTENDANCE_TABLE_NAME +
                        "(" + ATTENDANCE_COLUMN_EVENTID + " integer, " + ATTENDANCE_COLUMN_ATTENDEESNAME + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop tables in reasonable order
        db.execSQL("DROP TABLE IF EXISTS ATTENDEES_TABLE_NAME");
        db.execSQL("DROP TABLE IF EXISTS MOVIES_TABLE_NAME");
        db.execSQL("DROP TABLE IF EXISTS EVENTS_TABLE_NAME");
        db.execSQL("DROP TABLE IF EXISTS ATTENDANCE_TABLE_NAME");
        onCreate(db);
    }

    //add an Event
    public void insertEvent (String title, String startDate, String endDate, String venue,String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_COLUMN_TITLE, title);
        contentValues.put(EVENTS_COLUMN_STARTDATE, startDate);
        contentValues.put(EVENTS_COLUMN_ENDDATE, endDate);
        contentValues.put(EVENTS_COLUMN_VENUE, venue);
        contentValues.put(EVENTS_COLUMN_LOCATION, location);
        db.insert(EVENTS_TABLE_NAME, null, contentValues);

    }

    //add a Movie
    public void insertMovie (String title, String year, String image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIES_COLUMN_TITLE, title);
        contentValues.put(MOVIES_COLUMN_YEAR, year);
        contentValues.put(MOVIES_COLUMN_POSTERIMAGENAME, image);
        db.insert(MOVIES_TABLE_NAME, null, contentValues);

    }

    //combine an Attendee with an Event
    public boolean insertAttendance (Integer eventId, String attendeeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ATTENDANCE_COLUMN_EVENTID, eventId);
        contentValues.put(ATTENDANCE_COLUMN_ATTENDEESNAME, attendeeName);
        db.insert(ATTENDANCE_TABLE_NAME, null, contentValues);
        return true;
    }

    //update all necessary elements of an Event row
    public boolean editEvent (Integer id, String title, String startDate, String endDate, String venue,String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_COLUMN_TITLE, title);
        contentValues.put(EVENTS_COLUMN_STARTDATE, startDate);
        contentValues.put(EVENTS_COLUMN_ENDDATE, endDate);
        contentValues.put(EVENTS_COLUMN_VENUE, venue);
        contentValues.put(EVENTS_COLUMN_LOCATION, location);
        db.update(EVENTS_TABLE_NAME, contentValues,  EVENTS_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    //combine a Movie with an Event
    public boolean addMovieToEvent (Integer id, Integer movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_COLUMN_MOVIEID, movieId);
        db.update(EVENTS_TABLE_NAME, contentValues,  EVENTS_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    //delete an Attendance using 2 specific ids
    public void deleteAttendeeFromEvent (Integer eventId, String attendeeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ATTENDANCE_TABLE_NAME + " where " + ATTENDANCE_COLUMN_EVENTID + "=" + eventId + " and " + ATTENDANCE_COLUMN_ATTENDEESNAME + "=" + attendeeName);
    }


    //get all attendees' names of an Event
    public ArrayList<String> getAllAttendeesOfAnEvent(Integer eventId) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ ATTENDANCE_TABLE_NAME + " where " + ATTENDANCE_COLUMN_EVENTID + " = " + eventId, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ATTENDANCE_COLUMN_ATTENDEESNAME)));
            res.moveToNext();
        }
        return array_list;
    }

    //get all events
    public ArrayList<EventImpl> getAllEvents() {
        ArrayList<EventImpl> array_list = new ArrayList<EventImpl>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ EVENTS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            if(res.getString(res.getColumnIndex(EVENTS_COLUMN_MOVIEID)) != null) {
                array_list.add(new EventImpl(res.getString(res.getColumnIndex(EVENTS_COLUMN_ID)),res.getString(res.getColumnIndex(EVENTS_COLUMN_TITLE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_STARTDATE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_ENDDATE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_VENUE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_LOCATION)), new Integer(res.getString(res.getColumnIndex(EVENTS_COLUMN_MOVIEID)))));
            }
            else
            {
                array_list.add(new EventImpl(res.getString(res.getColumnIndex(EVENTS_COLUMN_ID)),res.getString(res.getColumnIndex(EVENTS_COLUMN_TITLE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_STARTDATE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_ENDDATE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_VENUE)),res.getString(res.getColumnIndex(EVENTS_COLUMN_LOCATION))));
            }

            res.moveToNext();
        }

        for(EventImpl newEvent : array_list) {
            newEvent.setDateTime(convertToDate(newEvent.getStartDate()));
        }
        return array_list;
    }

    //get all movies
    public ArrayList<MovieImpl> getAllMovies() {
        ArrayList<MovieImpl> array_list = new ArrayList<MovieImpl>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ MOVIES_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new MovieImpl(res.getString(res.getColumnIndex(MOVIES_COLUMN_ID)),res.getString(res.getColumnIndex(MOVIES_COLUMN_TITLE)),res.getString(res.getColumnIndex(MOVIES_COLUMN_YEAR)),res.getString(res.getColumnIndex(MOVIES_COLUMN_POSTERIMAGENAME))));
            res.moveToNext();
        }
        return array_list;
    }


}
