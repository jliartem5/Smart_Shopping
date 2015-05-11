/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 *
 * @author TheNabo1
 */
public class OVReq {

	public JSONObject toJSON() throws JSONException{
		String jsonSTR  = new Gson().toJson(this);
		JSONObject json = new JSONObject(jsonSTR);
		return json;
	}
	/*
	public List<NameValuePair> getNameValuePairList(){
		List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
		try {
			JSONObject thisJsonObj = this.toJSON();
			Iterator<String> jsonKeys = thisJsonObj.keys();
			while(jsonKeys.hasNext()){
				nvpList.
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nvpList;
		
	}*/
}