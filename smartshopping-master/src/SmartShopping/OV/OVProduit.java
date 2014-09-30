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
public class OVProduit extends OVObject{
	
    private String nomProduit; 
    private OVCategorie ovCategorie; 
    private double prix; 
    
    public OVProduit(String jsonObject){
        try 
        {
            JSONObject object;
            
            object = new JSONObject(jsonObject);
            this.id = Integer.parseInt(object.get("id").toString());
            this.nomProduit = object.get("nomProduit").toString();
            this.ovCategorie = new OVCategorie(object.get("ovCategorie").toString());
            this.prix = Double.parseDouble(object.get("prix").toString());
        } 
        catch (JSONException ex) 
        {
        }
        
    }
   
    
    public OVProduit(int idProduit, String nomProduit, OVCategorie ovCategorie, double prix) {
        this.id = idProduit;
        this.nomProduit = nomProduit;
        this.ovCategorie = ovCategorie;
        this.prix = prix;
    }
    
    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public OVCategorie getOvCategorie() {
        return ovCategorie;
    }

    public void setOvCategorie(OVCategorie ovCategorie) {
        this.ovCategorie = ovCategorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    @Override
    public String toString(){
    	return this.nomProduit + "\t" + this.prix + " €";
    }
    
}
