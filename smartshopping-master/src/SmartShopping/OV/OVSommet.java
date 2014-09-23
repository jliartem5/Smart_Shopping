package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

public class OVSommet extends OVObject {
    private int numSommet;
    private int idCategorie;

    public OVSommet(int id, int numSommet, int idCategorie) {
        this.id = id;
        this.numSommet = numSommet;
        this.idCategorie = idCategorie;
    }
    
    public OVSommet(String jsonObject){
        try 
        {
            JSONObject object;         
            object = new JSONObject(jsonObject);
            this.id = Integer.parseInt(object.get("id").toString());
            this.numSommet = object.getInt("numSommet");
            this.idCategorie = object.getInt("idCategorie");
        } 
        catch (JSONException ex) 
        {
        }     
    }

    public int getNumSommet() {
        return this.numSommet;
    }

    public int getIdCategorie() {
        return this.idCategorie;
    }

    public void setNumSommet(int numSommet) {
        this.numSommet = numSommet;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

	@Override
	public String toString() {
		return "OVSommet [numSommet=" + numSommet + ", idCategorie="
				+ idCategorie + ", id=" + id + "]";
	}
    
    

}