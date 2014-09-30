package SmartShopping.Remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sshopping.http.AsynJsonHttp;
import com.example.sshopping.http.HttpClients;
import com.example.sshopping.http.OnDataReturnListener;

import SmartShopping.OV.OVRep;
import SmartShopping.OV.OVReq;

public class WebServer {
	
	
	public enum COMMANDE {
		TousLesProduits, AjouterProduit,
		GetSmartList, UpdateListeProduit,
		AddListeProduit,postParam,
		UpdateSmartList, TousLesSommets,
		ToutesLesPromotions
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
	
	@SuppressWarnings("unchecked")
	public void sendRequest(COMMANDE cmd, List<NameValuePair> request, OnDataReturnListener dataListener){
		String finalURL = this.webBaseUrl + cmd.toString()+".jsp";
		AsynJsonHttp asynHttp = new AsynJsonHttp(finalURL, null);
		asynHttp.setOnReturnDataListener(dataListener);
		asynHttp.execute(request);
	}
	
}
