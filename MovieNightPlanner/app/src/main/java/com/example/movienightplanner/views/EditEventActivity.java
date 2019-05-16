package com.example.movienightplanner.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.EditEventOnclickListener;
import com.example.movienightplanner.models.AppEngineImpl;

public class EditEventActivity extends AppCompatActivity {

    private int eventPosition;
    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //get the position integer parameter from EventDetailActivity
        eventPosition = getIntent().getIntExtra("position", 1);

        Button editButton;
        final EditText tittleText;
        final EditText startDateText;
        final EditText endDateText;
        final EditText venueText;
        final EditText locationText;

        editButton = (Button) findViewById(R.id.editButton);

        tittleText = (EditText) findViewById(R.id.tittleEditTextID);
        tittleText.setText(appEngine.eventLists.get(eventPosition).getTittle());

        startDateText = (EditText) findViewById(R.id.startDateEditTextID);
        startDateText.setText(appEngine.eventLists.get(eventPosition).getStartDate());

        endDateText = (EditText) findViewById(R.id.endDateEditTextID);
        endDateText.setText(appEngine.eventLists.get(eventPosition).getEndDate());

        venueText = (EditText) findViewById(R.id.venueEditTextID);
        venueText.setText(appEngine.eventLists.get(eventPosition).getVenue());

        locationText = (EditText) findViewById(R.id.locationEditTextID);
        locationText.setText(appEngine.eventLists.get(eventPosition).getLocation());

        //handled logic in EditEventOnclickListener class
        editButton.setOnClickListener(new EditEventOnclickListener(this, tittleText, startDateText, endDateText, venueText,locationText, eventPosition));

    }
}
