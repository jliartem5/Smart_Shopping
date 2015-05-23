/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

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

    public OVNotification(String jsonStr) {

        try {
            JSONObject object;
            object = new JSONObject(jsonStr);

            this.distance = object.getInt("distance");
            this.id = object.getInt("id");
            this.responseNeeded = object.getInt("responseNeeded");
            this.texte = "texte";
            this.ovBeacon = new OVBeacon(object.getJSONObject("ovBeacon").toString());
            this.reponseEnvoye = new OVReponse(object.getJSONObject("reponseEnvoye").toString());
        } catch (JSONException ex) {
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
