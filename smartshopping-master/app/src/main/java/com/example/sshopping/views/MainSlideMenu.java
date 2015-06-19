package com.example.sshopping.views;

import com.example.sshopping.CommandeActivity;
import com.example.sshopping.MainActivity;
import com.example.sshopping.PromotionActivity;
import com.example.sshopping.R;
import com.example.sshopping.REQUEST_CODE;
import com.example.sshopping.SmartPlanActivity;

import android.app.Activity;
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
	
	private ISlideMenuActivity	_slideActivity;
	private Activity _parentActivity;
	public MainSlideMenu(Context context, AttributeSet paramAttributeSet) {
		this(context);
	}
 
	public MainSlideMenu(Context context) {
		super(context); 
		this._slideActivity = ( ISlideMenuActivity) context;
		this._parentActivity = (Activity)context;
		_parentActivity.getLayoutInflater().inflate(R.layout.view_slide_menu, this, true);
		
		this.findViewById(R.id.slide_menu_smartlist).setOnClickListener(this);
		this.findViewById(R.id.slide_menu_plan).setOnClickListener(this);
		this.findViewById(R.id.slide_menu_coupons).setOnClickListener(this);
		//this.findViewById(R.id.slide_menu_parametre).setOnClickListener(this);
		this.findViewById(R.id.slide_menu_commande).setOnClickListener(this);
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
				this._parentActivity.startActivityForResult(switchActivityIntent, 0);
				break;	
			case R.id.slide_menu_coupons:
				Intent switchActivityIntent1 = new Intent(this.getContext(), PromotionActivity.class);
				this._parentActivity.startActivity(switchActivityIntent1);
				break;
			case R.id.slide_menu_commande:
				Intent switchActivityIntent2 = new Intent(this.getContext(), CommandeActivity.class);
				this._parentActivity.startActivity(switchActivityIntent2);
				break;
			//case R.id.slide_menu_quit:
			//	System.exit(0);
			//	break;
		}
		this._slideActivity.closeSlideMenu();
	}
	
}
