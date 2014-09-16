package SmartShopping.Remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.sshopping.http.HttpClients;

import SmartShopping.OV.OVRep;
import SmartShopping.OV.OVReq;

public class WebServer {
	
	
	public enum COMMANDE {
		TousLesProduits, AjouterProduit,
		GetSmartList, UpdateListeProduit,
		AddListeProduit,
		UpdateSmartList
	};
	
	// Design Pattern Singleton
	protected static WebServer _instance = null;
	
	private final String webBaseUrl = "http://smartshopping.no-ip.org:8080/smartshopping/";
	
	private WebServer(){
		// constructer privé
	}
	
	public static WebServer getInstance(){
		if(WebServer._instance == null){
			WebServer._instance = new WebServer();
		}
		return WebServer._instance;
	};
	
	public JSONObject sendRequest(COMMANDE cmd, OVReq request) throws JSONException{
		String finalURL = this.webBaseUrl + cmd.toString()+".jsp";
		JSONObject JSONrep = HttpClients.SendHttpPost(finalURL, request.toJSON());
		
		return JSONrep;
	}
}
