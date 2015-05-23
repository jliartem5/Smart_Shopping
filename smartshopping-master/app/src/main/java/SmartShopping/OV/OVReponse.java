/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

import java.sql.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TheNabo1
 */
public class OVReponse extends OVObject {
    
    private int etat;
    private Date dateTime; 

    
    public OVReponse(){
        this.id = 1;
        this.etat = 0;
    }
    
    public OVReponse(String jsonStr) {

        try {
            JSONObject object;
            object = new JSONObject(jsonStr);

            this.etat = object.getInt("etat");
            this.id = object.getInt("id");
        } catch (JSONException ex) {
        }
    }
    
    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    
}
