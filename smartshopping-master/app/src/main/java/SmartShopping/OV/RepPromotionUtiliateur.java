package SmartShopping.OV;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RepPromotionUtiliateur extends OVRep{

    private ArrayList<OVPromotionUtilisateur> promotionUtilisateur;

    public ArrayList<OVPromotionUtilisateur> getPromotionUtilisateur() {
        return promotionUtilisateur;
    }

    public void setPromotionUtilisateur(ArrayList<OVPromotionUtilisateur> promotionUtilisateur) {
        this.promotionUtilisateur = promotionUtilisateur;
    }


    public RepPromotionUtiliateur(String repString){
        this.promotionUtilisateur = new ArrayList<OVPromotionUtilisateur>();
    }

}
