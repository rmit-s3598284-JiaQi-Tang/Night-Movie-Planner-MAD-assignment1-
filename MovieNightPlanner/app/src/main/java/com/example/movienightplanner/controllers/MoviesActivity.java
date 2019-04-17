package com.example.movienightplanner.controllers;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the right event by event position parameter passed from last page
                int eventPosition = getIntent().getIntExtra("position", 1);
                appEngine.eventLists.get(eventPosition).setMovie(appEngine.moviesList.get(position));

                //show alert to tell the movie of the event has been changed
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MoviesActivity.this);
                builder1.setMessage("Movie Changed to: " + "\"" + appEngine.moviesList.get(position).getTittle() + "\"");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(MoviesActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }
}
