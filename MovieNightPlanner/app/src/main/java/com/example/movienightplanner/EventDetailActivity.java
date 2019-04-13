package com.example.movienightplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.movienightplanner.models.AppEngine;
import com.example.movienightplanner.models.AppEngineImpl;

public class EventDetailActivity extends AppCompatActivity {
    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //get the position integer parameter from MainActivity
        int savedExtra = getIntent().getIntExtra("position", 1);
        TextView myText = (TextView) findViewById(R.id.tittleID);
        myText.setText(appEngine.eventLists.get(savedExtra).getTittle());
    }
}
