package com.example.sshopping.http;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Classe qui cree un thread en parallele du thread principal et qui appelle la classe permettant de gerer
 * la connection a un serveur.
 * @author Shinthujan, Jian, Walid, Wally, Youssef
 */
public class AsynJsonHttp extends AsyncTask<JSONObject, Integer, JSONObject>{
	
	private String url;
	private ProgressDialog progressDialog;
	private Context context;
	private OnDataReturnListener _onDataListener = null;
	public AsynJsonHttp(String URL, Context context){
		this.url = URL;
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected JSONObject doInBackground(JSONObject... params) {
		JSONObject jsonRec = HttpClients.SendHttpPost(url, params[0]);

		return jsonRec;
	}
	
	public void setDataListener(OnDataReturnListener listener){
		this._onDataListener = listener;
		
	}
	
	protected void onProgressUpdate(Integer... progress) {
		
    }
	
	@Override
	protected void onPostExecute(JSONObject result) {
    	this.progressDialog.dismiss();
    	if(this._onDataListener != null){
    		this._onDataListener.OnDataReturn(result);
    	}
    } 
	
    @Override
    protected void onPreExecute(){
    	this.progressDialog = ProgressDialog.show(context, "", "Chargement...", true, false);
    	super.onPreExecute();
    }
}
