package com.example.movienightplanner.api;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static android.support.v4.content.ContextCompat.getSystemService;

public class APIManager extends AsyncTask<String, String, String> {
    public static long getDurationSeconds(Context context, Location currentLocation, Double latidude, Double longitude) {
        long duration = 0;
        StringBuilder stringBuilder = new StringBuilder();
        String url = API.getURL(currentLocation, latidude, longitude);
        System.out.println(url);
        try {
            HttpPost httppost = new HttpPost(url);

            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringBuilder.toString());

            Welcome data = Converter.fromJsonString(stringBuilder.toString());

            duration = data.getRows()[0].getElements()[0].getDuration().getValue();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
