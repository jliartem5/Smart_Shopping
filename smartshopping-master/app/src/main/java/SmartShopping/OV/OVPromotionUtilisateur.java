/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 *
 * @author Shinthu
 */
public class OVPromotionUtilisateur extends OVObject{
    private int idUtilisateur;
    private int idPromotion;
    
    public OVPromotionUtilisateur (JSONObject jobj){
    	try {
			this.idUtilisateur = jobj.getInt("idUtilisateur");
                        this.idPromotion = jobj.getInt("idPromotion");
                        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
    }
    
    public OVPromotionUtilisateur(int idUser, int idPromo){
        idUtilisateur = idUser;
        idPromotion = idPromo;
    }
}
