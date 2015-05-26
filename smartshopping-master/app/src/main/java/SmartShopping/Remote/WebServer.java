package SmartShopping.Remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.sshopping.SmartShoppingApplication;
import com.example.sshopping.http.AsynJsonHttp;
import com.example.sshopping.http.HttpClients;
import com.example.sshopping.http.OnDataReturnListener;

import SmartShopping.OV.OVRep;
import SmartShopping.OV.OVReq;

public class WebServer {
	
	
	public enum COMMANDE {
		TousLesProduits, AjouterProduit,
		GetSmartList, UpdateListeProduit,
		AddListeProduit, postParam,
		UpdateSmartList, TousLesSommets,
		ToutesLesPromotions, Notification,
		UpdateReponse, GetProduitByReponse
	};
	
	// Design Pattern Singleton
	protected static WebServer _instance = null;
	
	private final String webBaseUrl = "http://smartshopping.no-ip.org:8080/smartshopping/";
	
	private WebServer(){
		// constructer prive
	}
	
	public static WebServer getInstance(){
		if(WebServer._instance == null){
			WebServer._instance = new WebServer();
		}
		return WebServer._instance;
	};
	
	@SuppressWarnings("unchecked")
	public void sendRequest(COMMANDE cmd, List<NameValuePair> request, OnDataReturnListener dataListener){
		ConnectivityManager connectivityManager = (ConnectivityManager) SmartShoppingApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager.getActiveNetworkInfo().isAvailable()){
			String finalURL = this.webBaseUrl + cmd.toString()+".jsp";
			AsynJsonHttp asynHttp = new AsynJsonHttp(finalURL, null);
			asynHttp.setOnReturnDataListener(dataListener);
			asynHttp.execute(request);
		}else{
			Toast.makeText(SmartShoppingApplication.getAppContext(), "Internet indisponible", 1000).show();
		}
	}
	
}
