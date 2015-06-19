/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import com.example.sshopping.http.OnDataReturnListener;

import SmartShopping.Remote.WebServer;

/**
 *
 * @author TheNabo1
 */
public class ReqSommet extends OVReq {
    
    private OVSommet ovSommet;
	
    public OVSommet getOvSommet() {
        return ovSommet;
    }

    public void setOvSommet(OVSommet ovSommet) {
        this.ovSommet = ovSommet;
    }
    
	public void requestTousLesSommets(OnDataReturnListener dataListener){
		WebServer ws = WebServer.getInstance();		
		ws.sendRequest(WebServer.COMMANDE.FindAllSommets, new ArrayList<NameValuePair>(), dataListener);
	}
	
    
}
