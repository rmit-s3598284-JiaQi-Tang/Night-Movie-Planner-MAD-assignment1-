package com.example.movienightplanner.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.AddNewEventOnClickListener;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;

public class AddEventActivity extends AppCompatActivity {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

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

        //event logic in AddNewEventOnClickListener class
        addNewButton.setOnClickListener(new AddNewEventOnClickListener(this, tittleText, startDateText, endDateText, venueText, locationText));

    }
}
