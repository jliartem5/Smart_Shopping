package com.example.sshopping.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.sshopping.MainActivity;
import com.example.sshopping.R;
import com.example.sshopping.http.OnDataReturnListener;

import org.json.JSONException;
import org.json.JSONObject;

import SmartShopping.OV.OVNotification;
import SmartShopping.OV.OVProduit;
import SmartShopping.OV.OVReponse;
import SmartShopping.OV.ReqNotification;

/**
 * Classe de notification avec bouton
 * Created by jianli on 07/05/2015.
 */
public class ButtonNotification extends Notification {

    OVReponse response;
    ReqNotification req = new ReqNotification();

    public ButtonNotification(Activity a, final OVNotification ovNotification) throws JSONException {

        super(a);

        response = ovNotification.getReponseEnvoye();
        this.builder.setMessage(ovNotification.getTexte());

        this.builder.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickAccpeterPromotion(ovNotification);
            }
        });

        this.builder.setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickRefuserPromotion(ovNotification);
            }
        });


        // Notif Appli et Montre et actions

        /*
        // REFUSER ANDROID WEAR
        Intent intentRefuser = new Intent(a, MainActivity.class);
        intentRefuser.putExtra("REFUSER", "KO"); // OK = accepter / KO = refuser
        PendingIntent pendingIntentRefuser = PendingIntent.getActivity(a, 0, intentRefuser, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action actionRefuser = new NotificationCompat.Action.Builder(
                R.drawable.smartshopping_logo, "Refuser", pendingIntentRefuser).build();

        // ACCEPTER ANDROID WEAR
        Intent intentAccpeter = new Intent(a, MainActivity.class);
        intentAccpeter.putExtra("ACCEPTER", new String[] { "OK", ovNotification.toJSON().toString()});
        PendingIntent pendingIntentAccepter = PendingIntent.getActivity(a, 0, intentAccpeter, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action actionAccepter = new NotificationCompat.Action.Builder(
                R.drawable.smartshopping_logo, "Accepter", pendingIntentAccepter).build();

        List<NotificationCompat.Action> actions = new ArrayList<NotificationCompat.Action>();
        actions.add(actionAccepter);
        actions.add(actionRefuser);
*/
        android.app.Notification notificationWear =
                new NotificationCompat.Builder(a)
                        .setSmallIcon(R.drawable.smartshopping_logo)
                        .setContentTitle("Promotion")
                        .setContentText(ovNotification.getTexte())
                        //.extend(new NotificationCompat.WearableExtender().addActions(actions))
                        .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(a);
        notificationManagerCompat.notify(001, notificationWear);

    }

    /**
     * Accepter le promotion
     * @param ovNotification
     */
    public void clickAccpeterPromotion(OVNotification ovNotification){
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

    public void clickRefuserPromotion(OVNotification ovNotification){
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

    public AlertDialog.Builder getAlertBuilder(){
        return  this.builder;
    }
}
