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
public class OVPromotion extends OVObject{
	 
    private OVTypePromotion ovTypePromotion; 
    private OVProduit ovProduit; 
    private String libellePromotion; 
    private float promotion;
    private Date dateDebut;
    private Date dateFin;
    
    public OVPromotion (JSONObject jobj){
    	try {
			String libellePromo = jobj.getString("libellePromotion");
			float promotion = Float.parseFloat(jobj.get("promotion").toString());
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
			Date dateDebut = sdf.parse(jobj.getString("dateDebut"));
			Date dateFin = sdf.parse(jobj.getString("dateFin"));
			OVTypePromotion ovtp = new OVTypePromotion(jobj.getJSONObject("ovTypePromotion").getString("libelleTypePromotion"));
			OVProduit ovp = new OVProduit(jobj.getJSONObject("ovProduit").toString());
			this.Fill(ovtp, ovp, libellePromo, promotion, dateDebut, dateFin);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.e("Date parse Error", "Impposible de parse la date string");
			e.printStackTrace();
		}
    }
    
    public void Fill(OVTypePromotion ovTypePromotion, OVProduit ovProduit, 
            String libellePromotion, float promotion, Date dateDebut, 
            Date dateFin){

        this.ovTypePromotion = ovTypePromotion;
        this.ovProduit = ovProduit;
        this.libellePromotion = libellePromotion;
        this.promotion = promotion;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public OVTypePromotion getOvTypePromotion() {
        return ovTypePromotion;
    }

    public OVProduit getOvProduit() {
        return ovProduit;
    }

    public String getLibellePromotion() {
        return libellePromotion;
    }

    public float getPromotion() {
        return promotion;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setOvTypePromotion(OVTypePromotion ovTypePromotion) {
        this.ovTypePromotion = ovTypePromotion;
    }

    public void setOvProduit(OVProduit ovProduit) {
        this.ovProduit = ovProduit;
    }

    public void setLibellePromotion(String libellePromotion) {
        this.libellePromotion = libellePromotion;
    }

    public void setPromotion(float promotion) {
        this.promotion = promotion;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
   
    @Override
    public String toString(){
    	String promoTxt = "";
    	if(this.ovTypePromotion.getLibelleTypePromotion().toUpperCase() == "REMISE_EURO"){
    		promoTxt = this.libellePromotion + "\n"+(this.promotion*100)+"%";
    	}else{
    		promoTxt = this.libellePromotion;
    	}
    	return promoTxt;
    }
}
