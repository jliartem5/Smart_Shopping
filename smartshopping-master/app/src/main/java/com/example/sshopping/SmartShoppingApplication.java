package com.example.sshopping;

import android.app.Application;
import android.content.Context;

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
