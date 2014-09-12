package com.example.sshopping.views;

import com.example.sshopping.MainActivity;
import com.example.sshopping.R;
import com.example.sshopping.REQUEST_CODE;
import com.example.sshopping.SelectPlaceActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

/**
 * Classe permettant de gerer le slide menu de gauche. (vue)
 * C'est cette classe qui permet d'appeler/cree les fragments desires.
 * @author Shinthujan, Jian, Walid, Wally, Youssef
 */
public class MainSlideMenu extends LinearLayout implements OnClickListener{
	
	private MainActivity	_mainActivity;
	
	public MainSlideMenu(Context context, AttributeSet paramAttributeSet) {
		this(context);
	}
 
	public MainSlideMenu(Context context) {
		super(context); 
		this._mainActivity = (MainActivity) context;
		_mainActivity.getLayoutInflater().inflate(R.layout.view_slide_menu, this, true);
		
	}

	@Override
	public void onClick(View v) {
		Log.i("LJ", "Click on the slide menu");
		switch(v.getId()){
			default:
				break;
		}
		this._mainActivity.closeSlideMenu();
	}
	
}
