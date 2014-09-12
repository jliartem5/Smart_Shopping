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
public class OVCategorie extends OVObject{
   private String nomCategorie; 

   public OVCategorie(String jsonObject){
       try 
        {
            JSONObject object;
            object = new JSONObject(jsonObject);
            this.id = Integer.parseInt(object.get("id").toString());
            this.nomCategorie = object.get("nomCategorie").toString();  
        } 
        catch (JSONException ex)
        {
        }
   }
   
    public OVCategorie(int idCategorie, String nomCategorie) {
        this.id = idCategorie;
        this.nomCategorie = nomCategorie;
    }


    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
    
}
