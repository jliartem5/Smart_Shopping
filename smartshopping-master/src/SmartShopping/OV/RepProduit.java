/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TheNabo1
 */
public class RepProduit extends OVRep {
    
    private ArrayList<OVProduit> listeProduit; 

    
    public RepProduit(String jsonString) throws JSONException {
        
        JSONObject repProduitJson = new JSONObject(jsonString).getJSONObject("repProduit");
        JSONArray arrayProduit = repProduitJson.getJSONArray("listeProduit");
        
        this.listeProduit = new ArrayList<OVProduit>();
        
        for(int i=0; i < arrayProduit.length(); i++){
            JSONObject jsonProduit = arrayProduit.getJSONObject(i);
            listeProduit.add(new OVProduit(jsonProduit.toString()));
        }
        
    }
    
    public ArrayList<OVProduit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(ArrayList<OVProduit> listeProduit) {
        this.listeProduit = listeProduit;
    }
    
}
