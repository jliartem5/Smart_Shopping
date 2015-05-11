package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by jianli on 07/05/2015.
 */
public class ButtonNotification extends Notification {


    public ButtonNotification(Activity a) {
        super(a);
    }

    public void Show(String info) {
        this.builder.setMessage(info).show();
    }

    public void AddButton(String btnText, DialogInterface.OnClickListener listener){
        this.builder.setPositiveButton(btnText, listener);
    }

    public AlertDialog.Builder getAlertBuilder(){
        return  this.builder;
    }
}
