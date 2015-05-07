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
    private boolean responseNeeded;
    private String texte;

    
    public OVNotification(int idNotification, int distance, boolean responseNeeded, String texte) {
        this.id = idNotification;
        this.distance = distance;
        this.responseNeeded = responseNeeded;
        this.texte = texte;
        this.ovBeacon = new OVBeacon();
    }
    
    public OVNotification(String jsonStr){

        try 
        {
            JSONObject object;
            object = new JSONObject(jsonStr);
            
            this.setDistance(object.getInt("distance"));
            this.setId(object.getInt("id"));
            this.setResponseNeeded(object.getBoolean("responseNeeded"));
            this.setTexte("texte");
        } 
        catch (JSONException ex)
        {
        }
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

    public boolean isResponseNeeded() {
        return responseNeeded;
    }

    public void setResponseNeeded(boolean responseNeeded) {
        this.responseNeeded = responseNeeded;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    
    
}
