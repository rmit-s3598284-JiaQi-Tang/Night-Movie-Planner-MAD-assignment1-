package com.example.movienightplanner.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movienightplanner.R;
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

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if input was empty
                if (tittleText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Tittle can not be empty !", AddEventActivity.this);
                } else if (startDateText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Start date can not be empty !", AddEventActivity.this);
                } else if (endDateText.getText().toString().isEmpty()) {
                    appEngine.showAlert("End date can not be empty !", AddEventActivity.this);
                } else if (venueText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Venue can not be empty !", AddEventActivity.this);
                } else if (locationText.getText().toString().isEmpty()) {
                    appEngine.showAlert("Location can not be empty !", AddEventActivity.this);
                } else if (!appEngine.isValidDate(startDateText.getText().toString()) || !appEngine.isValidDate(endDateText.getText().toString())) {
                    appEngine.showAlert("Please follow the date time format: 2/01/2019 3:00:00 AM ", AddEventActivity.this);
                } else {
                    EventImpl newEvents = new EventImpl("" + appEngine.eventLists.size(), tittleText.getText().toString(), startDateText.getText().toString(), endDateText.getText().toString(), venueText.getText().toString(), locationText.getText().toString());
                    newEvents.setDateTime(appEngine.convertToDate(newEvents.getStartDate()));
                    appEngine.eventLists.add(newEvents);

                    //show alert to tell the new event created
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(AddEventActivity.this);
                    builder1.setMessage("A new event has been created !");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    onBackPressed();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });
    }
}
