package com.example.movienightplanner.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.CustomListAdapter_MoviesActivityList;
import com.example.movienightplanner.controllers.adapter.MovieListViewOnItemClickListener;
import com.example.movienightplanner.models.AppEngineImpl;

public class MoviesActivity extends AppCompatActivity {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private ListView listView;
    private CustomListAdapter_MoviesActivityList listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        listAdapter = new CustomListAdapter_MoviesActivityList(this, appEngine.moviesList);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);

        //event handling code is in a separate class called MovieListViewOnItemClickListener
        listView.setOnItemClickListener(new MovieListViewOnItemClickListener(getIntent()));

    }
}
