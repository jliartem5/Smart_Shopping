/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

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
    
	public RepProduit sendRequestTousLesProduits() throws JSONException{
		WebServer ws = WebServer.getInstance();		
		JSONObject JSONrep = ws.sendRequest(WebServer.COMMANDE.TousLesProduits, this);
		return new RepProduit(JSONrep.toString());
	}
	
	public RepProduit sendRequestAjoutProduit(){
		return null;
		
	}
    
}
