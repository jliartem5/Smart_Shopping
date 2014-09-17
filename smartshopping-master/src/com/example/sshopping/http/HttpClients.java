package com.example.sshopping.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Classe permettant de gerer la connection a la base de donnees.
 */
public class HttpClients {
	private static final String TAG = "HttpClient";

	public static JSONObject SendHttpPost(String URL, List<NameValuePair> parameters) {
		Log.i(TAG, "Http url:"+URL);
		JSONObject errorJSONobj = new JSONObject();
		String errorMessage = "";
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPostRequest = new HttpPost(URL);
			/*
			JSONObject jsonForStringEntity = new JSONObject();
			
			for(NameValuePair nvp : parameters){
				jsonForStringEntity.accumulate(nvp.getName(), nvp.getValue());
			}
			StringEntity se = new StringEntity(jsonForStringEntity.toString());
			Log.i("HttpClient", jsonForStringEntity.toString());*/
			httpPostRequest.setEntity(new UrlEncodedFormEntity(parameters));
			
			long t = System.currentTimeMillis();
			
			HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
			
			Log.i(TAG, "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]");

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				Header contentEncoding = response.getFirstHeader("Content-Encoding");
				if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					instream = new GZIPInputStream(instream);
				}

				String resultString= convertStreamToString(instream);
				Log.i(TAG,"<JSONObject>\n"+resultString+"\n</JSONObject>");
				instream.close();
				resultString = resultString.substring(0,resultString.length()-1); 
				JSONObject jsonObjRecv = new JSONObject(resultString);
				return jsonObjRecv;
			}

		}
		catch (Exception e)
		{
			
			errorMessage = e.toString() == null ?"Null error msg":e.toString();
		}
		
		try {
			errorJSONobj.putOpt("error", errorMessage);

			Log.i(TAG, errorMessage);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return errorJSONobj;
	}


	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
