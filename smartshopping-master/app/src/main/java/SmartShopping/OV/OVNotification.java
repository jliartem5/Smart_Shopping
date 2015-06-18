/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TheNabo1
 */
public class OVNotification extends OVObject {

    private OVPromotion ovPromotion;
    private OVBeacon ovBeacon;
    private int distance;
    private int responseNeeded;
    private String texte;
    private OVReponse reponseEnvoye;

    private int idUtilisateur;
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    public OVNotification(String jsonStr, Activity activity) {

        try {
            JSONObject object;
            object = new JSONObject(jsonStr);
            TelephonyManager mngr = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceID = mngr.getDeviceId();
            deviceID = deviceID.substring(deviceID.length()-8, deviceID.length());
            Log.i("Notification Log", "Device id:" + deviceID);
            Integer userID = Integer.parseInt(deviceID);
            Log.i("Notification Log", "Device id:" + userID);
            this.setIdUtilisateur(userID);

            this.distance = object.getInt("distance");
            this.id = object.getInt("id");
            this.responseNeeded = object.getInt("responseNeeded");
            this.texte = object.getString("texte");
            this.ovBeacon = new OVBeacon(object.getJSONObject("ovBeacon").toString());
            this.reponseEnvoye = new OVReponse(object.getJSONObject("reponseEnvoye").toString());
            this.ovPromotion = new OVPromotion(object.getJSONObject("ovPromotion"));
        } catch (JSONException ex) {
            Log.i("Notification Log", ex.getMessage());
        }
    }

    public OVNotification(int idNotification, int distance, int responseNeeded, String texte) {
        this.id = idNotification;
        this.distance = distance;
        this.responseNeeded = responseNeeded;
        this.texte = texte;
        this.ovBeacon = new OVBeacon();
        this.reponseEnvoye = new OVReponse();
    }

    public OVPromotion getOvPromotion() {
        return ovPromotion;
    }

    public void setOvPromotion(OVPromotion ovPromotion) {
        this.ovPromotion = ovPromotion;
    }

    public OVBeacon getOvBeacon() {
        return ovBeacon;
    }

    public void setOvBeacon(OVBeacon ovBeacon) {
        this.ovBeacon = ovBeacon;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int isResponseNeeded() {
        return responseNeeded;
    }

    public void setResponseNeeded(int responseNeeded) {
        this.responseNeeded = responseNeeded;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public OVReponse getReponseEnvoye() {
        return reponseEnvoye;
    }

    public void setReponseEnvoye(OVReponse reponseEnvoye) {
        this.reponseEnvoye = reponseEnvoye;
    }
    public int getResponseNeeded(){
        return this.responseNeeded;
    }
}
