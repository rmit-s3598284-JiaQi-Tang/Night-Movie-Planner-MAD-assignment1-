package com.example.movienightplanner.models;

import android.util.Log;

import com.example.movienightplanner.MainActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.content.res.AssetManager;
public class AppEngineImpl implements AppEngine {

    private static AppEngineImpl sharedInstance;

    public static AppEngineImpl getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new AppEngineImpl();
        }
        return sharedInstance;
    }

    @Override
    public void startUp() {

    }

    @Override
    public void readTextFile(String fileName, Context context) {
        String line = null;
        try {
            InputStream fileInputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

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
        System.out.println(line);
//        return line;
    }
}
