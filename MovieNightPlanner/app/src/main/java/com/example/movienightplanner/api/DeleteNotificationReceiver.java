package com.example.movienightplanner.api;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.movienightplanner.database.DBHelper;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.views.CalendarViewActivity;
import com.example.movienightplanner.views.MainActivity;

import java.util.concurrent.TimeUnit;

import static com.example.movienightplanner.api.MyLocationService.NOTIFICATION_CHANNEL;

public class DeleteNotificationReceiver extends BroadcastReceiver {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("eventTitle");
        int titleIndex = -1;
        int eventId = -1;
        for(EventImpl event : appEngine.eventLists) {
            if(event.getTittle().equals(title)) {
                titleIndex = appEngine.eventLists.indexOf(event);
                eventId = Integer.parseInt(event.getId());
            }
        }

        if(titleIndex != -1 && eventId != -1){
            DBHelper dbHelper = new DBHelper(context);
            dbHelper.deleteEvent(eventId);
            appEngine.eventLists.remove(titleIndex);
            MainActivity.getInstance().updateListView();
            Toast.makeText(context,title + " was deleted", Toast.LENGTH_LONG);

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.cancel(NOTIFICATION_CHANNEL);
        }

    }
}
