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

public class DeleteNotificationReceiver extends BroadcastReceiver {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra("channelId");
        int eventId = -1;
        eventId = Integer.parseInt(appEngine.eventLists.get(Integer.parseInt(id)).getId());

        if(eventId != -1){
            DBHelper dbHelper = new DBHelper(context);
            dbHelper.deleteEvent(eventId);
            appEngine.eventLists.remove(Integer.parseInt(id));
            MainActivity.getInstance().updateListView();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.cancel(Integer.parseInt(id));
        }

    }
}
