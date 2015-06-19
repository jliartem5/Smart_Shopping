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
public class OVCommande extends OVObject {
    
    private int idSmartlist;
    private int idUtilisateur;
    private float montant;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  OVCommande(int idsl, int idU, float mtant){
        this.idSmartlist = idsl;
        this.idUtilisateur = idU;
        this.montant = mtant;
    }

    public OVCommande(String jsonStr) {

        try {
            JSONObject object;
            object = new JSONObject(jsonStr);

            this.id = object.getInt("id");
            this.idUtilisateur = object.getInt("idUtilisateur");
            this.idSmartlist = object.getInt("idSmartlist");
            this.montant = (float)object.getDouble("montant");
           
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
    
    public int getIdSmartlist() {
        return idSmartlist;
    }

    public void setIdSmartlist(int idSmartlist) {
        this.idSmartlist = idSmartlist;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    
    
}
