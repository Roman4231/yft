package com.yft.admin.myapplication.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.yft.admin.myapplication.MainActivity;
import com.yft.admin.myapplication.R;

public class MyReceiver extends BroadcastReceiver {

    public static int MID = 0;
    NotificationCompat.Builder mNotifyBuilder;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_logo1)
                .setContentTitle(intent.getStringExtra("Your fitness trainer"))
                .setContentText("Hi! Don't forget to get a workout today!")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());

        MID++;
       // throw new UnsupportedOperationException("Not yet implemented");
    }
}
