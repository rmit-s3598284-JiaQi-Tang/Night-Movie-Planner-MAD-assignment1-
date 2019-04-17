package com.example.movienightplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movienightplanner.R;
import com.example.movienightplanner.adapter.CustomListAdapter_MainActivityList;
import com.example.movienightplanner.models.AppEngineImpl;

public class MainActivity extends AppCompatActivity {

    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    ListView listView;
    CustomListAdapter_MainActivityList listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise read txt files, but we only read once
        if(!appEngine.getDataRead()) {
            appEngine.startUp(this);
        }

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movie Night Planner");
        setSupportActionBar(toolbar);

        listAdapter = new CustomListAdapter_MainActivityList(this, appEngine.eventLists);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //define menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAddNew:
                startActivity(new Intent(MainActivity.this, AddEventActivity.class));
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuViewCalendar:
                startActivity(new Intent(MainActivity.this, CalendarViewActivity.class));
                Toast.makeText(this, "Calendar View", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuAscending:

                appEngine.ascendEvents();

                listAdapter = new CustomListAdapter_MainActivityList(this, appEngine.eventLists);
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);

                Toast.makeText(this, "Ascending Events", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuDescending:

                appEngine.descendEvents();

                listAdapter = new CustomListAdapter_MainActivityList(this, appEngine.eventLists);
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);

                Toast.makeText(this, "Descending Events", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    //to update the listview
    @Override
    protected void onResume(){
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }

}
