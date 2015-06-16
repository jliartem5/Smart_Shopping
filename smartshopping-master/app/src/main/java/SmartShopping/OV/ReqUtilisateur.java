package SmartShopping.OV;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.sshopping.http.OnDataReturnListener;

import SmartShopping.Remote.WebServer;

public class ReqUtilisateur extends OVReq{

	public ReqUtilisateur(){
		
	}

	
	public boolean requestUser(String imei, OnDataReturnListener listener){
		WebServer ws = WebServer.getInstance();		

		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		Log.i("HttpClient", "User IMEI:"+imei.toString());
		nvp.add(new BasicNameValuePair("IMEI", imei));
		ws.sendRequest(WebServer.COMMANDE.GetUser, nvp, listener);
		return true;
	}

}
