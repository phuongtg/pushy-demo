package me.pushy.example;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create a test notification
        // (Use deprecated notification API for demonstration
        // purposes, to avoid having to import AppCompat into your project)

        String notificationTitle = "Pushy";
        String notificationDesc = "Test notification";

        // Attempt to grab the message property from the payload
        if (intent.getStringExtra("message") != null) {
            notificationDesc = intent.getStringExtra("message");
        }

        // Create a test notification
        Notification notification = new Notification(android.R.drawable.ic_dialog_info, notificationDesc, System.currentTimeMillis());

        // Sound + vibrate + light
        notification.defaults = Notification.DEFAULT_ALL;

        // Dismisses when pressed
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        // Set notification click intent
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, Main.class), 0);

        // Set title and desc
        notification.setLatestEventInfo(context, notificationTitle, notificationDesc, notificationIntent);

        // Get notification manager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Dispatch the notification
        mNotificationManager.notify(0, notification);
    }
}