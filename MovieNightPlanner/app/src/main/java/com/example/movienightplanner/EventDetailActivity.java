package com.example.movienightplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienightplanner.models.AppEngineImpl;

public class EventDetailActivity extends AppCompatActivity {

    int eventPosition;
    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Event");
        setSupportActionBar(toolbar);

        //get the position integer parameter from MainActivity
        eventPosition = getIntent().getIntExtra("position", 1);

        TextView tittleText = (TextView) findViewById(R.id.tittleID);
        tittleText.setText(appEngine.eventLists.get(eventPosition).getTittle());

        TextView venueText = (TextView) findViewById(R.id.venueID);
        venueText.setText("At: " + appEngine.eventLists.get(eventPosition).getVenue());

        TextView startDateText = (TextView) findViewById(R.id.startDateID);
        startDateText.setText("Start Date: " + appEngine.eventLists.get(eventPosition).getStartDate());

        TextView endDateText = (TextView) findViewById(R.id.endDateID);
        endDateText.setText("End Date: " + appEngine.eventLists.get(eventPosition).getEndDate());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_eventdetail, menu);
        return true;
    }

    //define menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuEdit:
                startActivity(new Intent(EventDetailActivity.this, EditEventActivity.class));
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuDelete:

                //delete the event by that position
                appEngine.eventLists.remove(eventPosition);
                onBackPressed();
                Toast.makeText(this, "Calendar View", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
