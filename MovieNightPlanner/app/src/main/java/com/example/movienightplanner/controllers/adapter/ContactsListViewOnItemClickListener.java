package com.example.movienightplanner.controllers.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.views.ContactsActivity;

import java.util.List;

public class ContactsListViewOnItemClickListener implements AdapterView.OnItemClickListener {
    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private Intent intent;
    private List<String> contactsList;

    public ContactsListViewOnItemClickListener(Intent intent, List<String> contactsList) {
        this.intent = intent;
        this.contactsList = contactsList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

        //get the right event by event position parameter passed from last page
        int eventPosition = intent.getIntExtra("position", 1);

        //check if this person have already been invited
        if (appEngine.eventLists.get(eventPosition).getAttendees().contains(contactsList.get(position))) {
            //show alert to tell the contact have been added
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setMessage("This person already exits !");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {
            List<String> newContacts = appEngine.eventLists.get(eventPosition).getAttendees();
            newContacts.add(contactsList.get(position));

            appEngine.eventLists.get(eventPosition).setAttendees(newContacts);

            //show alert to tell the contact have been added
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setMessage(contactsList.get(position) + " has been added to " + appEngine.eventLists.get(eventPosition).getTittle());
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
//                            onBackPressed();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
}
