package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

import SmartShopping.Remote.WebServer;

public class ReqSmartList extends OVReq{
	
	private OVSmartList smartList; 
	
	public ReqSmartList(){
		
	}
	
	public RepSmartList requestGetSmartList() throws JSONException{

		WebServer ws = WebServer.getInstance();		
		JSONObject JSONrep = ws.sendRequest(WebServer.COMMANDE.GetSmartList, this);
		return new RepSmartList(JSONrep.toString());
	}

	public OVSmartList getSmartList() {
		return smartList;
	}

	public void setSmartList(OVSmartList smartList) {
		this.smartList = smartList;
	}
}
