package com.example.movienightplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.movienightplanner.adapter.CustomListAdapter_MainActivityList;
import com.example.movienightplanner.adapter.CustomListAdapter_MoviesActivityList;
import com.example.movienightplanner.models.AppEngineImpl;

public class MoviesActivity extends AppCompatActivity {

    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    ListView listView;
    CustomListAdapter_MoviesActivityList listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        listAdapter = new CustomListAdapter_MoviesActivityList(this, appEngine.moviesList);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
