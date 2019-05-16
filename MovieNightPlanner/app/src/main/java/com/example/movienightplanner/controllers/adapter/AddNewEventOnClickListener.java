package com.example.movienightplanner.controllers.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.views.AddEventActivity;

public class AddNewEventOnClickListener implements View.OnClickListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private AddEventActivity superActivity;
    final EditText tittleText;
    final EditText startDateText;
    final EditText endDateText;
    final EditText venueText;
    final EditText locationText;

    public AddNewEventOnClickListener(AddEventActivity superActivity, EditText tittleText, EditText startDateText, EditText endDateText, EditText venueText, EditText locationText) {
        this.superActivity = superActivity;
        this.tittleText = tittleText;
        this.startDateText = startDateText;
        this.endDateText = endDateText;
        this.venueText = venueText;
        this.locationText = locationText;
    }

    @Override
    public void onClick(View v) {

        //check if input was empty
        if (tittleText.getText().toString().isEmpty()) {
            appEngine.showAlert("Tittle can not be empty !", v.getContext());
        } else if (startDateText.getText().toString().isEmpty()) {
            appEngine.showAlert("Start date can not be empty !", v.getContext());
        } else if (endDateText.getText().toString().isEmpty()) {
            appEngine.showAlert("End date can not be empty !", v.getContext());
        } else if (venueText.getText().toString().isEmpty()) {
            appEngine.showAlert("Venue can not be empty !", v.getContext());
        } else if (locationText.getText().toString().isEmpty()) {
            appEngine.showAlert("Location can not be empty !", v.getContext());
        } else if (!appEngine.isValidDate(startDateText.getText().toString()) || !appEngine.isValidDate(endDateText.getText().toString())) {
            appEngine.showAlert("Please follow the date time format: 2/01/2019 3:00:00 AM ", v.getContext());
        } else {
            EventImpl newEvents = new EventImpl("" + appEngine.eventLists.size(), tittleText.getText().toString(), startDateText.getText().toString(), endDateText.getText().toString(), venueText.getText().toString(), locationText.getText().toString());
            newEvents.setDateTime(appEngine.convertToDate(newEvents.getStartDate()));
            appEngine.eventLists.add(newEvents);

            //show alert to tell the new event created
            AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
            builder1.setMessage("A new event has been created !");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            superActivity.onBackPressed();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
}
