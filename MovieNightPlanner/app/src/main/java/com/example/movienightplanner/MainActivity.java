package com.example.movienightplanner;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.movienightplanner.models.AppEngine;
import com.example.movienightplanner.models.AppEngineImpl;

public class MainActivity extends AppCompatActivity {
    AppEngine appEngine = AppEngineImpl.getSharedInstance();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add(readFile("events.txt"));
//        arrayList.add(readFile("movies.txt"));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        appEngine.readTextFile("events.txt", this);
    }

}
