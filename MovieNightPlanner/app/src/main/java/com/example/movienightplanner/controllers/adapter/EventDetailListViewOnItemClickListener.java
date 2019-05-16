package com.example.movienightplanner.controllers.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.views.EventDetailActivity;

public class EventDetailListViewOnItemClickListener implements AdapterView.OnItemClickListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private int eventPosition;
    private ArrayAdapter<String> adapter;

    public EventDetailListViewOnItemClickListener(int eventPosition, ArrayAdapter<String> adapter) {
        this.eventPosition = eventPosition;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        //show alert to tell the movie of the event has been changed
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Are you sure to delete the attendee called : " + appEngine.eventLists.get(eventPosition).getAttendees().get(position));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        appEngine.eventLists.get(eventPosition).getAttendees().remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
