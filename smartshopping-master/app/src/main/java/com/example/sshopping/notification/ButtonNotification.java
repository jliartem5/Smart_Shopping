package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.sshopping.http.OnDataReturnListener;

import org.json.JSONObject;

import SmartShopping.OV.OVNotification;
import SmartShopping.OV.OVReponse;
import SmartShopping.OV.ReqNotification;

/**
 * Created by jianli on 07/05/2015.
 */
public class ButtonNotification extends Notification {

    OVReponse response;
    ReqNotification req = new ReqNotification();
    public ButtonNotification(Activity a, final OVNotification ovNotification) {

        super(a);
        response = ovNotification.getReponseEnvoye();

        this.builder.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                response.setEtat(1);
                ovNotification.setReponseEnvoye(response);
                req.updateNotificationResponse(ovNotification, new OnDataReturnListener() {
                    @Override
                    public void OnDataReturn(JSONObject jobj) {

                        Log.i("Notification LOG", "Accepter répondu, retour de donnée:");
                        Log.i("Notification LOG", jobj.toString());
                    }
                });
            }
        });

        this.builder.setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                response.setEtat(-1);
                ovNotification.setReponseEnvoye(response);
                req.updateNotificationResponse(ovNotification, new OnDataReturnListener() {
                    @Override
                    public void OnDataReturn(JSONObject jobj) {

                        Log.i("Notification LOG", "Refuser répondu, retour de donnée:");
                        Log.i("Notification LOG", jobj.toString());
                    }
                });
            }
        });

    }

    public void Show(String info) {
        this.builder.setMessage(info).show();
    }

    public AlertDialog.Builder getAlertBuilder(){
        return  this.builder;
    }
}
