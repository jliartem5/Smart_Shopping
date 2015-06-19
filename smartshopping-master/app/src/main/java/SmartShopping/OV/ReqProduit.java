/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sshopping.http.OnDataReturnListener;

import android.util.Log;
import SmartShopping.Remote.WebServer;

/**
 *
 * @author TheNabo1
 */
public class ReqProduit extends OVReq {
    
    private OVProduit ovProduit;

    public OVProduit getOvProduit() {
        return ovProduit;
    }

    public void setOvProduit(OVProduit ovProduit) {
        this.ovProduit = ovProduit;
    }
    
	public void requestTousLesProduits(OnDataReturnListener dataListener){
		WebServer ws = WebServer.getInstance();		
		ws.sendRequest(WebServer.COMMANDE.FindAllProduits, new ArrayList<NameValuePair>(), dataListener);
	}
	
	public boolean requestAjoutProduit(OnDataReturnListener dataListener){
		WebServer ws = WebServer.getInstance();		
		// this contient le produit a ajouter
		ws.sendRequest(WebServer.COMMANDE.AjouterProduit, new ArrayList<NameValuePair>(), dataListener); 
		return true;
	}
    
}
