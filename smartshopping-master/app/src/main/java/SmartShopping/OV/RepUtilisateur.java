package SmartShopping.OV;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RepUtilisateur extends OVRep{

	private OVUtilisateur utilisateur;

    public OVUtilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(OVUtilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public RepUtilisateur(String repString){
        try {
            this.utilisateur = new OVUtilisateur(new JSONObject(repString).getJSONObject("utilisateur").toString());
        }catch (JSONException e){
            Log.e("JSON Error", e.getMessage());
        }
    }

}
