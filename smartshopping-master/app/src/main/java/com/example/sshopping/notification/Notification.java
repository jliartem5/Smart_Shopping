package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Created by jianli on 07/05/2015.
 */
public abstract class Notification {

    protected Activity activity;
    protected AlertDialog.Builder builder;
    public Notification(Activity a){
        this.activity = a;

        this.builder = new AlertDialog.Builder(this.activity);
    }
}
