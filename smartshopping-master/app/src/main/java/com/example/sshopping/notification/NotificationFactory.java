package com.example.sshopping.notification;

import android.app.Activity;

import SmartShopping.OV.OVNotification;

/**
 * Created by jianli on 22/05/2015.
 */
public class NotificationFactory {

    public static Notification BuildNotification(Activity a, OVNotification notification){
        Notification alertNotif = null;
        if(notification.getResponseNeeded() == 1){
            alertNotif = new ButtonNotification(a, notification);
        }else{
            alertNotif = new SimpleNotification(a, notification.getTexte());
        }
        return alertNotif;
    }

}
