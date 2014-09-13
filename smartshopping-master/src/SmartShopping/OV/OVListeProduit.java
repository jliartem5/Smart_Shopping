package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

public class OVListeProduit extends OVObject {
	private Boolean coche;
	private Boolean supprime;
	private Integer idProduit;
	private Integer idListe;
	
	public Boolean getCoche() {
		return coche;
	}

	public void setCoche(Boolean coche) {
		this.coche = coche;
	}

	public Boolean getSupprime() {
		return supprime;
	}

	public void setSupprime(Boolean supprime) {
		this.supprime = supprime;
	}

	public Integer getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Integer idProduit) {
		this.idProduit = idProduit;
	}

	public Integer getIdListe() {
		return idListe;
	}

	public void setIdListe(Integer idListe) {
		this.idListe = idListe;
	}

	public OVListeProduit(String jsonStr){

        try 
        {
            JSONObject object;
            object = new JSONObject(jsonStr);
            this.id = Integer.parseInt(object.get("id").toString());
            this.setCoche(object.getBoolean("coche"));
            this.setIdListe(object.getInt("idListe"));
            this.setIdProduit(object.getInt("idProduit"));
            this.setSupprime(object.getBoolean("supprime"));
        } 
        catch (JSONException ex) 
        {
        }
	}

	public OVListeProduit(Boolean coche, Boolean supprime, Integer idProduit,
			Integer idListe) {
		super();
		this.coche = coche;
		this.supprime = supprime;
		this.idProduit = idProduit;
		this.idListe = idListe;
	}
	
}
