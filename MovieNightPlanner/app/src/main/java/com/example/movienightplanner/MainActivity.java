package com.example.movienightplanner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.movienightplanner.adapter.CustomListAdapter;
import com.example.movienightplanner.models.AppEngine;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.models.Movie;
import com.example.movienightplanner.models.MovieImpl;

public class MainActivity extends AppCompatActivity {

    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    ListView listView;
    Button addNewButton;
    CustomListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        appEngine.startUp(this);

        listAdapter = new CustomListAdapter(this, appEngine.eventLists);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);

                //pass the position integer as parameter
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        addNewButton = (Button) findViewById(R.id.addButton);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEventActivity.class));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        //to update the listview
        listAdapter.notifyDataSetChanged();
    }

}
