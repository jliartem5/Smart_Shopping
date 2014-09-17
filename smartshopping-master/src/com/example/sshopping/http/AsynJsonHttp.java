package com.example.sshopping.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Classe qui cree un thread en parallele du thread principal et qui appelle la classe permettant de gerer
 * la connection a un serveur.
 * @author Shinthujan, Jian, Walid, Wally, Youssef
 */
public class AsynJsonHttp extends AsyncTask<List<NameValuePair>, Integer, JSONObject>{
	
	private String url;
	private ProgressDialog progressDialog;
	private Context context;
	private OnDataReturnListener _onDataListener = null;
	public AsynJsonHttp(String URL, Context context){
		this.url = URL;
		this.context = context;
		if(context != null){
			this.progressDialog = new ProgressDialog(context);
		}
	}
	
	@Override
	protected JSONObject doInBackground(List<NameValuePair>... params) {
		JSONObject jsonRec = HttpClients.SendHttpPost(url, params[0]);

		return jsonRec;
	}
	
	public void setOnReturnDataListener(OnDataReturnListener listener){
		this._onDataListener = listener;
		
	}
	
	protected void onProgressUpdate(Integer... progress) {
		
    }
	
	@Override
	protected void onPostExecute(JSONObject result) {
		if(this.progressDialog != null){
			this.progressDialog.dismiss();
		}
    	if(this._onDataListener != null){
    		this._onDataListener.OnDataReturn(result);
    	}
    } 
	
    @Override
    protected void onPreExecute(){
    	if(this.progressDialog != null){
    		this.progressDialog = ProgressDialog.show(context, "", "Chargement...", true, false);
    	}
    	super.onPreExecute();
    }
}
