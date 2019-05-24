package com.example.movienightplanner.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.movienightplanner.models.AppEngineImpl;

import java.util.Timer;
import java.util.TimerTask;

public class RemindLaterNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String id = intent.getStringExtra("channelId");

        //Dismiss notification
        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Integer.parseInt(id));

        //send notification again
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    MyLocationService.builder.setChannelId("YOUR_PACKAGE_NAME");
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            "YOUR_PACKAGE_NAME",
                            "YOUR_APP_NAME",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }
                }
                notificationManager.notify(Integer.parseInt(id),MyLocationService.builder.build());
            }
        }, 60000 * AppEngineImpl.getRemindingPeriod());// 60000 millsec = 1 min

     }
}