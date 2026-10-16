
package com.pingo.messenger;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationHelper {

    public static final String CHANNEL_ID = "pingo_messages";
    private static final String CHANNEL_NAME = "PINGO паёмҳо";

    public static void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Огоҳиномаҳои паёмҳои PINGO");

            NotificationManager manager =
                context.getSystemService(NotificationManager.class);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public static void show(
            Context context,
            String title,
            String message,
            int notificationId) {

        createChannel(context);

        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setAutoCancel(true);

        NotificationManager manager =
            (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE
            );

        if (manager != null) {
            if (Build.VERSION.SDK_INT >= 33 &&
                context.checkSelfPermission(
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return;
            }

            manager.notify(notificationId, builder.build());
        }
    }
        }
              
