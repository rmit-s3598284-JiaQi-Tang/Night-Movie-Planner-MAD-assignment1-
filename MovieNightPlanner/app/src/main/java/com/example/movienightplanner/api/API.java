package com.example.movienightplanner.api;

import android.location.Location;
import android.location.LocationManager;

public class API {
    public static String API_KEY="AIzaSyBk-BQhMj0WA1E9nhDdUY2N14OUo4MPcsU";
    public static String getURL(Location currentLocation, Double latidude, Double longitude) {
        return "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&destinations=" + latidude + "," + longitude + "&mode=driving&key=" + API_KEY;
    }

}
