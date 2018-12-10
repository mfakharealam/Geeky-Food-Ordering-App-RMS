package com.example.muhammadfakhar.pro.Helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

public class NotificationHelper extends ContextWrapper {

    private static String MY_CHANNEL_ID = "com.example.muhammadfakhar.pro";
    private static String MY_CHANNEL_NAME = "Geeky Foods";
    private NotificationManager notificationManager;


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createChannel();
        }
    }

    private void createChannel() {
/*
        NotificationChannel notificationChannel = new NotificationChannel(MY_CHANNEL_ID, MY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
*/

    }
}
