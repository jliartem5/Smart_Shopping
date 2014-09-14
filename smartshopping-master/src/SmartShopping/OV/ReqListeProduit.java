package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

import SmartShopping.Remote.WebServer;

public class ReqListeProduit extends OVReq{

	OVListeProduit ovListeProduit;

	public boolean requestUpdateListeProduit() throws JSONException{
		WebServer ws = WebServer.getInstance();	
		JSONObject JSONrep = ws.sendRequest(WebServer.COMMANDE.UpdateListeProduit, this);
		return true;
	}
	
	public boolean requestAddListeProduit() throws JSONException{

		WebServer ws = WebServer.getInstance();	
		JSONObject JSONrep = ws.sendRequest(WebServer.COMMANDE.AddListeProduit, this);
		return true;
	}

	public OVListeProduit getOvListeProduit() {
		return ovListeProduit;
	}

	public void setOvListeProduit(OVListeProduit ovListeProduit) {
		this.ovListeProduit = ovListeProduit;
	}
	
}
