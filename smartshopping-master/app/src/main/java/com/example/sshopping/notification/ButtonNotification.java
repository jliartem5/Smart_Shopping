package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.sshopping.MainActivity;
import com.example.sshopping.http.OnDataReturnListener;

import org.json.JSONObject;

import SmartShopping.OV.OVListeProduit;
import SmartShopping.OV.OVNotification;
import SmartShopping.OV.OVProduit;
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
        this.builder.setMessage(ovNotification.getTexte());

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

                        //Si erreur est false(sans erreur) alors on ajoute le produit dans liste produit
                        try {
                            if (jobj.getBoolean("erreur") == false) {
                                //cherche produit correspondant
                                req.getProduitByReponse(response, new OnDataReturnListener() {
                                    @Override
                                    public void OnDataReturn(JSONObject jobj) {
                                        try {
                                            Log.v("Notification LOG", "Produit de response:" + jobj.toString());
                                            if (jobj.getBoolean("erreur") == false) {
                                                MainActivity mt = (MainActivity)ButtonNotification.this.activity;

                                                OVProduit produit = new OVProduit(jobj.getJSONArray("listeProduit").getString(0));

                                                mt.addProduitToSmartList(produit);

                                            } else {
                                                SimpleNotification notif = new SimpleNotification(ButtonNotification.this.activity, "Impossible d'accepter ce promotion...");
                                                notif.Show();
                                            }
                                        }catch(Exception e){
                                            Log.v("Notification LOG", "Erreur json retour:"+e.getMessage());
                                        }
                                    }
                                });
                            }
                        }catch(Exception e){
                            Log.v("Notification LOG", "Erreur json retour:"+e.getMessage());
                        }
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


    public AlertDialog.Builder getAlertBuilder(){
        return  this.builder;
    }
}
