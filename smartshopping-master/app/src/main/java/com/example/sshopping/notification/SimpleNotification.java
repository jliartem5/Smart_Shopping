package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by jianli on 07/05/2015.
 */
public class SimpleNotification extends Notification {


    public SimpleNotification(Activity a) {
        super(a);
    }

    public void Show(String info) {
        builder.setMessage(info);
        builder.show();
    }
}
