package com.cse390.coronavirus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Hourly Notification")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Reminder: ")
                .setContentText("You are doing amazing! Keep going!")
                .setPriority(NotificationCompat.PRIORITY_HIGH); //TODO: Change To Custom Icon

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

         notificationManager.notify(200, builder.build());

    }
}
