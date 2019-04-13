package com.example.movienightplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movienightplanner.models.AppEngine;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;

public class AddEventActivity extends AppCompatActivity {
    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Button addNewButton;
        final EditText tittleText;
        final EditText startDateText;
        final EditText endDateText;
        final EditText venueText;
        final EditText locationText;

        addNewButton = (Button) findViewById(R.id.addButton);
        tittleText = (EditText) findViewById(R.id.tittleEditTextID);
        startDateText = (EditText) findViewById(R.id.startDateEditTextID);
        endDateText = (EditText) findViewById(R.id.endDateEditTextID);
        venueText = (EditText) findViewById(R.id.venueEditTextID);
        locationText = (EditText) findViewById(R.id.locationEditTextID);

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventImpl newEvents = new EventImpl(""+appEngine.eventLists.size(), tittleText.getText().toString(), startDateText.getText().toString(), endDateText.getText().toString(), venueText.getText().toString(), locationText.getText().toString());
                appEngine.eventLists.add(newEvents);
                appEngine.showAlert("A new event has been created !", AddEventActivity.this);
            }
        });
    }
}
