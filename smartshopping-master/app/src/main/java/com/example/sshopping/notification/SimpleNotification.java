package com.example.sshopping.notification;

import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.sshopping.R;

/**
 * Created by jianli on 07/05/2015.
 */
public class SimpleNotification extends Notification {

    String msg;
    public SimpleNotification(Activity a, String message) {
        super(a);
        this.msg = message;
        builder.setMessage(this.msg);

        // Notif Appli et Montre
        android.app.Notification notificationWear =
                new NotificationCompat.Builder(a)
                        .setSmallIcon(R.drawable.smartshopping_logo)
                        .setContentTitle("Information")
                        .setContentText(message)
                                //.extend(new NotificationCompat.WearableExtender().addAction(action))
                        .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(a);
        notificationManagerCompat.notify(001, notificationWear);
    }

}
