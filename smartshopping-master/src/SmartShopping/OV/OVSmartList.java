package SmartShopping.OV;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class OVSmartList extends OVObject{
	
	private List<OVListeProduit> produitsSmartList;
	private String nom;
	
	public OVSmartList(String jsonStr){
        try 
        {
            JSONObject object = new JSONObject(jsonStr);
            if(object.has("smartList")){
            	object = new JSONObject(jsonStr).getJSONObject("smartList");
            }
            if(object.has("id")){
            	this.id = object.getInt("id");
            }else{
            	this.id = 1;
            }
            this.setNom(object.getString("nom"));
            JSONArray smartListArr = object.getJSONArray("produitsSmartList");
            this.produitsSmartList = new ArrayList<OVListeProduit>();
            for(int i=0; i<smartListArr.length();++i){
            	OVListeProduit newlistProduit = new OVListeProduit(smartListArr.get(i).toString());
            	produitsSmartList.add(newlistProduit);
            }
        }
        catch (JSONException ex) 
        {
        	Log.i("HttpClient", ex.getMessage());
        }	
	}
	
	public List<OVListeProduit> getProduitsSmartList() {
		return produitsSmartList;
	}
	public void setProduitsSmartList(List<OVListeProduit> produitsSmartList) {
		this.produitsSmartList = produitsSmartList;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public OVSmartList(List<OVListeProduit> produitsSmartList, String nom, int id) {
		super();
		this.produitsSmartList = produitsSmartList;
		this.nom = nom;
		this.id = id;
	}
	
}
