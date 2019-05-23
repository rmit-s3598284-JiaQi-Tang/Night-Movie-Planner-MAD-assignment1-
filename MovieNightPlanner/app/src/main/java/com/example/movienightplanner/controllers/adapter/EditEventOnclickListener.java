package com.example.movienightplanner.controllers.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.movienightplanner.database.DBHelper;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.models.helper.DateFormats;
import com.example.movienightplanner.views.AddEventActivity;
import com.example.movienightplanner.views.EditEventActivity;
import com.example.movienightplanner.views.MainActivity;

public class EditEventOnclickListener implements View.OnClickListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private EditEventActivity superActivity;
    final EditText tittleText;
    final EditText startDateText;
    final EditText endDateText;
    final EditText venueText;
    final EditText locationText;
    private int eventPosition;

    public EditEventOnclickListener(EditEventActivity superActivity, EditText tittleText, EditText startDateText, EditText endDateText, EditText venueText, EditText locationText, int eventPosition) {
        this.superActivity = superActivity;
        this.tittleText = tittleText;
        this.startDateText = startDateText;
        this.endDateText = endDateText;
        this.venueText = venueText;
        this.locationText = locationText;
        this.eventPosition = eventPosition;
    }

    @Override
    public void onClick(final View v) {

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
        } else if (!DateFormats.isValidDate(startDateText.getText().toString()) || !DateFormats.isValidDate(endDateText.getText().toString())) {
            appEngine.showAlert("Please follow the date time format: 2/01/2019 3:00:00 AM ", v.getContext());
        } else {

            appEngine.eventLists.get(eventPosition).setTittle(tittleText.getText().toString());
            appEngine.eventLists.get(eventPosition).setStartDate(startDateText.getText().toString());
            appEngine.eventLists.get(eventPosition).setEndDate(endDateText.getText().toString());
            appEngine.eventLists.get(eventPosition).setVenue(venueText.getText().toString());
            appEngine.eventLists.get(eventPosition).setLocation(locationText.getText().toString());
            appEngine.eventLists.get(eventPosition).setDateTime(DateFormats.convertToDate(appEngine.eventLists.get(eventPosition).getStartDate()));

            //update database
            DBHelper dbHelper = new DBHelper(v.getContext());
            EventImpl event = appEngine.eventLists.get(eventPosition);
            dbHelper.editEvent(new Integer(event.getId()),event.getTittle(),event.getStartDate(),event.getEndDate(),event.getVenue(),event.getLocation());

            //show alert to tell the new event created
            AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
            builder1.setMessage("Event has been Edited !");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            superActivity.startActivity(intent);
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
}
