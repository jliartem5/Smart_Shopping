package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by jianli on 07/05/2015.
 */
public class SimpleNotification extends Notification {

    String msg;
    public SimpleNotification(Activity a, String message) {
        super(a);
        this.msg = message;
        builder.setMessage(this.msg);
    }

}
