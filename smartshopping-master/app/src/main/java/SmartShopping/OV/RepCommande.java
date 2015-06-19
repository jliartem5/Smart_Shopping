/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author TheNabo1
 */
public class RepCommande extends OVRep {
 
    private ArrayList<OVCommande> lstCommandes;

    public ArrayList<OVCommande> getLstCommandes() {
        return lstCommandes;
    }

    public void setLstCommandes(ArrayList<OVCommande> lstCommandes) {
        this.lstCommandes = lstCommandes;
    }
    
    public RepCommande(JSONObject json){
        this.lstCommandes = new ArrayList<OVCommande>();
        try {
            JSONArray ja =  json.getJSONArray("lstCommandes");
            for(int i=0; i<ja.length(); ++i){

                OVCommande ovCmd = new OVCommande(ja.getJSONObject(i).toString());
                this.lstCommandes.add(ovCmd);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
