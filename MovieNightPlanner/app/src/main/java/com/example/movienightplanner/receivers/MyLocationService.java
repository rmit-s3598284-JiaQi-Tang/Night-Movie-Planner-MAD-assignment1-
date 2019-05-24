package com.example.movienightplanner.receivers;

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
import com.example.movienightplanner.api.APIManager;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.google.android.gms.location.LocationResult;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyLocationService extends BroadcastReceiver {

    public static AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    public static final String ACTION_PROCESS_UPDATE = "com.example.movienightplanner.api.UPDATE_LOCATION";
    public static NotificationCompat.Builder builder;

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
                                for(EventImpl event : appEngine.eventLists) {
                                    long duration = APIManager.getDurationSeconds(context,location,new Double(event.getLocation().split(",")[0].trim()), new Double(event.getLocation().split(",")[1].trim())) / 60;
                                    Date currentTime = Calendar.getInstance().getTime();
                                    int leftMinutes = (int)(TimeUnit.MILLISECONDS.toMinutes(event.getDateTime().getTime()) - TimeUnit.MILLISECONDS.toMinutes(currentTime.getTime()));

                                    //send notifications at 15 minutes before the exact start time
                                    if(duration + appEngine.getThreshold() == leftMinutes ) {
                                        showNotification(context, appEngine.eventLists.indexOf(event),"is starting in " + leftMinutes + " minutes, you are now " + duration+" minutes driving away from the event location");
                                    }
                                    //send notifications for reachable events start in 1 hour
                                    else if (duration <= leftMinutes && leftMinutes <= 60) {
                                        showNotification(context, appEngine.eventLists.indexOf(event),"is starting in " + leftMinutes + " minutes, you are now " + duration+" minutes driving away from the event location");
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

    public static void showNotification(Context context, int id, String message) {

        Intent deleteBroadcastIntent = new Intent(context, DeleteNotificationReceiver.class);
        deleteBroadcastIntent.putExtra("channelId","" + id);
        PendingIntent deleteActionIntent = PendingIntent.getBroadcast(context,id,deleteBroadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissBroadcastIntent = new Intent(context, DismissNotificationReceiver.class);
        dismissBroadcastIntent.putExtra("channelId","" + id);
        PendingIntent dismissActionIntent = PendingIntent.getBroadcast(context,id,dismissBroadcastIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        Intent remindLaterBroadcastIntent = new Intent(context, RemindLaterNotificationReceiver.class);
        remindLaterBroadcastIntent.putExtra("channelId","" + id);
        PendingIntent remindLaterActionIntent = PendingIntent.getBroadcast(context,id,remindLaterBroadcastIntent,PendingIntent.FLAG_CANCEL_CURRENT);


        builder = new NotificationCompat.Builder(context,"default")
                .setSmallIcon(R.drawable.stopwatch)
                .setContentTitle(appEngine.eventLists.get(id).getTittle())
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setShowWhen(true)
                .addAction(R.mipmap.ic_launcher,"Delete",deleteActionIntent)
                .addAction(R.mipmap.ic_launcher_round, "Dismiss", dismissActionIntent)
                .addAction(R.mipmap.ic_launcher_round,"Remind me " +AppEngineImpl.getRemindingPeriod()+ " mins later",remindLaterActionIntent);


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
        notificationManager.notify(id,builder.build());

    }
}
