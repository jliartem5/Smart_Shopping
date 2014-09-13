package SmartShopping.OV;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OVSmartList extends OVObject{
	
	private List<OVListeProduit> produitsSmartList;
	private String nom;
	
	public OVSmartList(String jsonStr){
        try 
        {
            JSONObject object;
            object = new JSONObject(jsonStr);
            this.id = Integer.parseInt(object.get("id").toString());
            this.setNom(object.getString("nom"));
            JSONArray smartListArr = object.getJSONArray("produitSmartList");
            
            for(int i=0; i<smartListArr.length();++i){
            	OVListeProduit newlistProduit = new OVListeProduit(smartListArr.get(i).toString());
            	produitsSmartList.add(newlistProduit);
            }
        }
        catch (JSONException ex) 
        {
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
	public OVSmartList(List<OVListeProduit> produitsSmartList, String nom) {
		super();
		this.produitsSmartList = produitsSmartList;
		this.nom = nom;
	}
	
}
