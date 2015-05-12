package SmartShopping.OV;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RepSommet extends OVRep {

    private ArrayList<OVSommet> listeSommet= new ArrayList<OVSommet>();

    public RepSommet(){
        this.listeSommet = new ArrayList<OVSommet>();
    }
    

	public RepSommet(String jsonStr){
		JSONArray arraySommet;
		try {
			arraySommet = new JSONObject(jsonStr).getJSONArray("listeSommet");
			this.listeSommet = new ArrayList<OVSommet>();
			for(int i=0; i < arraySommet.length(); i++){
			    JSONObject jsonSommet = arraySommet.getJSONObject(i);
			    listeSommet.add(new OVSommet(jsonSommet.toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    
    public ArrayList<OVSommet> getListeSommet() {
        return this.listeSommet;
    }

 
}