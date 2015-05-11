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

public class ReqSmartList extends OVReq{
	
	private OVSmartList smartList; 
	
	public ReqSmartList(){
		
	}
	
	public void requestGetSmartList(OnDataReturnListener listener){

		WebServer ws = WebServer.getInstance();		
		 ws.sendRequest(WebServer.COMMANDE.GetSmartList, new ArrayList<NameValuePair>(), listener);
	}
	
	public boolean requestUpdateSmartList(OnDataReturnListener listener){
		WebServer ws = WebServer.getInstance();		

		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		try {
			Log.i("HttpClient", this.smartList.toJSON().toString());
			nvp.add(new BasicNameValuePair("SmartList", this.smartList.toJSON().toString()));
			ws.sendRequest(WebServer.COMMANDE.UpdateSmartList, nvp, listener);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public OVSmartList getSmartList() {
		return smartList;
	}

	public void setSmartList(OVSmartList smartList) {
		this.smartList = smartList;
	}
}
