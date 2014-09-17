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

public class ReqListeProduit extends OVReq{

	OVListeProduit ListeProduit;

	public boolean requestUpdateListeProduit(OnDataReturnListener dataListener){
		WebServer ws = WebServer.getInstance();	
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		try {
			nvp.add(new BasicNameValuePair("ListeProduit", this.ListeProduit.toJSON().toString()));
			ws.sendRequest(WebServer.COMMANDE.UpdateListeProduit, nvp, dataListener);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean requestAddListeProduit(OnDataReturnListener dataListener){
		WebServer ws = WebServer.getInstance();	
		try {
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			this.ListeProduit.setId(-1);
			nvp.add(new BasicNameValuePair("ListeProduit",this.ListeProduit.toJSON().toString()));
			ws.sendRequest(WebServer.COMMANDE.AddListeProduit, nvp, dataListener);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public OVListeProduit getOvListeProduit() {
		return ListeProduit;
	}

	public void setOvListeProduit(OVListeProduit ovListeProduit) {
		this.ListeProduit = ovListeProduit;
	}
	
}
