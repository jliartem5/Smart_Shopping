package com.example.sshopping.views;

import com.example.sshopping.MainActivity;
import com.example.sshopping.R;
import com.example.sshopping.REQUEST_CODE;
import com.example.sshopping.SmartPlanActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

/**
 * Classe permettant de gerer le slide menu de gauche. (vue)
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
		
		this.findViewById(R.id.slide_menu_smartlist).setOnClickListener(this);
		this.findViewById(R.id.slide_menu_plan).setOnClickListener(this);
		this.findViewById(R.id.slide_menu_coupons).setOnClickListener(this);
		this.findViewById(R.id.slide_menu_parametre).setOnClickListener(this);
		//this.findViewById(R.id.slide_menu_quit).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Log.i("LJ", "Click on the slide menu");
		switch(v.getId()){
			case R.id.slide_menu_smartlist:
				//this._mainActivity.switchFragment(new FragmentConnexion());
				break;
			case R.id.slide_menu_plan:
				Intent switchActivityIntent = new Intent(this.getContext(), SmartPlanActivity.class);
				this._mainActivity.startActivityForResult(switchActivityIntent, 0);
				break;	
			case R.id.slide_menu_coupons:
				break;	
			case R.id.slide_menu_parametre:
				break;
			//case R.id.slide_menu_quit:
			//	System.exit(0);
			//	break;
		}
		this._mainActivity.closeSlideMenu();
	}
	
}
