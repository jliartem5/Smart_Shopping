package com.example.sshopping;

import android.app.Application;
import android.content.Context;

/**
 * Classe globale , fournir le context du l'application
 */
public class SmartShoppingApplication extends Application{
	private static Context context;
	
	public void onCreate(){
		super.onCreate();
		SmartShoppingApplication.context = getApplicationContext();
	}
	
	public static Context getAppContext(){
		return SmartShoppingApplication.context;
	}
}
