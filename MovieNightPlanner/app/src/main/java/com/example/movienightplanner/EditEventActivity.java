package com.example.movienightplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;

public class EditEventActivity extends AppCompatActivity {

    int eventPosition;
    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

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

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if input was empty
                if(tittleText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Tittle can not be empty !", EditEventActivity.this);
                } else if(startDateText.getText().toString().isEmpty()){
                    appEngine.showAlert("Start date can not be empty !", EditEventActivity.this);
                } else if(endDateText.getText().toString().isEmpty()) {
                    appEngine.showAlert("End date can not be empty !", EditEventActivity.this);
                } else if(venueText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Venue can not be empty !", EditEventActivity.this);
                } else if(locationText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Location can not be empty !", EditEventActivity.this);
                } else if(!appEngine.isValidDate(startDateText.getText().toString()) || !appEngine.isValidDate(endDateText.getText().toString())) {
                    appEngine.showAlert("Please follow the date time format: 2/01/2019 3:00:00 AM ", EditEventActivity.this);
                } else {

                    appEngine.eventLists.get(eventPosition).setTittle(tittleText.getText().toString());
                    appEngine.eventLists.get(eventPosition).setStartDate(startDateText.getText().toString());
                    appEngine.eventLists.get(eventPosition).setEndDate(endDateText.getText().toString());
                    appEngine.eventLists.get(eventPosition).setVenue(venueText.getText().toString());
                    appEngine.eventLists.get(eventPosition).setLocation(locationText.getText().toString());
                    appEngine.eventLists.get(eventPosition).setDateTime(appEngine.convertToDate(appEngine.eventLists.get(eventPosition).getStartDate()));

                    //show alert to tell the new event created
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(EditEventActivity.this);
                    builder1.setMessage("Event has been Edited !");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intent = new Intent(EditEventActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });
    }
}
