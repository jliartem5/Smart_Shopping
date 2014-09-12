package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class OVObject {
	protected Integer id; 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JSONObject toJSON() throws JSONException{
		String jsonSTR  = new Gson().toJson(this);
		JSONObject json = new JSONObject(jsonSTR);
		return json;
	}
	
}
