package com.example.movienightplanner.receivers;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.movienightplanner.database.DBHelper;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.views.MainActivity;

public class DeleteNotificationReceiver extends BroadcastReceiver {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String channelId = intent.getStringExtra("channelId");
        int eventId = -1;
        eventId = Integer.parseInt(appEngine.eventLists.get(Integer.parseInt(channelId)).getId());

        EventImpl event = appEngine.eventLists.get(Integer.parseInt(channelId));
        String message = "Are you sure to delete " + event.getTittle() + " ? "+ System.getProperty("line.separator") + System.getProperty("line.separator")
                + " start time: " + event.getStartDate() + System.getProperty("line.separator")
                + " end time: " + event.getEndDate() + System.getProperty("line.separator")
                + " location: " + event.getVenue();


        final DBHelper dbHelper = new DBHelper(context);
        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        final int finalEventId = eventId;
        notificationManager.cancel(Integer.parseInt(channelId));

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.getInstance());
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(finalEventId != -1){
                            dbHelper.deleteEvent(finalEventId);
                            appEngine.eventLists.remove(Integer.parseInt(channelId));
                            MainActivity.getInstance().updateListView();
                        }
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
