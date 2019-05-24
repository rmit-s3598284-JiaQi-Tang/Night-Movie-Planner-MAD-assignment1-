package com.example.movienightplanner.api;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.example.movienightplanner.api.MyLocationService.NOTIFICATION_CHANNEL;

public class DismissNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("eventTitle");
        Toast.makeText(context,title + " will not be alarmed again", Toast.LENGTH_LONG);
        System.out.print(title);
        //Dismiss notification
        NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_CHANNEL);
    }
}

