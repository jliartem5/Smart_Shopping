package SmartShopping.OV;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shinthu
 */
public class RepPromotion extends OVRep {

    private ArrayList<OVPromotion> listePromotion; 

    public RepPromotion(JSONObject json){
        this.listePromotion = new ArrayList<OVPromotion>();
        try {
			JSONArray ja =  json.getJSONArray("listePromotion");
			for(int i=0; i<ja.length(); ++i){
				
				OVPromotion ovPromo = new OVPromotion(ja.getJSONObject(i));
				this.listePromotion.add(ovPromo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public ArrayList<OVPromotion> getListePromotion() {
        return this.listePromotion;
    }
	
}
