package com.example.movienightplanner.api;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.movienightplanner.R;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.google.android.gms.location.LocationResult;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyLocationService extends BroadcastReceiver {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    public static final String ACTION_PROCESS_UPDATE = "com.example.movienightplanner.api.UPDATE_LOCATION";
    public static final int NOTIFICATION_CHANNEL = 1;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if(intent != null)
        {
            final String action = intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action))
            {
                LocationResult result = LocationResult.extractResult(intent);
                if(result != null)
                {
                    final Location location = result.getLastLocation();
                    String location_string = new StringBuilder("" + location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();

                    final PendingResult durationResult = goAsync();
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                for(EventImpl evemt1:appEngine.eventLists){
                                    System.out.println(evemt1.getDateTime().toString());
                                }
                                for(EventImpl event : appEngine.getFutureEvents()) {
                                    long duration = APIManager.getDurationSeconds(context,location,new Double(event.getLocation().split(",")[0].trim()), new Double(event.getLocation().split(",")[1].trim())) / 60;
                                    Date currentTime = Calendar.getInstance().getTime();
                                    int leftMinutes = (int)(TimeUnit.MILLISECONDS.toMinutes(event.getDateTime().getTime()) - TimeUnit.MILLISECONDS.toMinutes(currentTime.getTime()));

                                    //send notifications at 15 minutes before the exact start time
                                    if(duration + 15 == leftMinutes ) {
                                        showNotification(context, event.getTittle(),"is starting in " + leftMinutes + " minutes, you are now " + duration+" minutes driving away from the event location");
                                    }
                                    //send notifications for reachable events start in 1 hour
                                    else if (duration <= leftMinutes && leftMinutes <= 60) {
                                        showNotification(context, event.getTittle(),"is starting in " + leftMinutes + " minutes, you are now " + duration+" minutes driving away from the event location");
                                    }
                                }

                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                            durationResult.finish();
                        }
                    };
                    thread.start();

                }
            }
        }
    }

    private void showNotification(Context context, String title, String message) {

        Intent deleteBroadcastIntent = new Intent(context, DeleteNotificationReceiver.class);
        deleteBroadcastIntent.putExtra("eventTitle",title);
        PendingIntent deleteActionIntent = PendingIntent.getBroadcast(context,NOTIFICATION_CHANNEL,deleteBroadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissBroadcastIntent = new Intent(context, DismissNotificationReceiver.class);
        dismissBroadcastIntent.putExtra("eventTitle",title);
        PendingIntent dismissActionIntent = PendingIntent.getBroadcast(context,NOTIFICATION_CHANNEL,dismissBroadcastIntent,PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
                .setSmallIcon(R.drawable.stopwatch)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setShowWhen(true)
                .addAction(R.mipmap.ic_launcher,"Delete",deleteActionIntent)
                .addAction(R.mipmap.ic_launcher_round, "Dismiss", dismissActionIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("YOUR_PACKAGE_NAME");
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
        notificationManager.notify(NOTIFICATION_CHANNEL,builder.build());

    }
}
