package com.example.sshopping;

import SmartShopping.ShortestPath.*;

import java.util.ArrayList;
import java.util.List;

import com.example.sshopping.R;
import com.example.sshopping.PlaceSelect.PlaceSelectPopupWindow;
import com.example.sshopping.PlaceSelect.SmartPlanView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Classe qui permet l'affichage de la carte et des trajets
 * @author Jian, Django
 */
public class SmartPlanActivity extends Activity {
	
	PlaceSelectPopupWindow popupWindow;
	SmartPlanView view;
	int idCategorie;
	
	int requestCode;
	boolean isReadOnly = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_place);
		FrameLayout layout = (FrameLayout) SmartPlanActivity.this.findViewById(R.id.place_selection_layout);
		this.idCategorie = this.getIntent().getIntExtra("idCategorie", -1);
		view = (SmartPlanView) layout.getChildAt(0);
		view.setTargetCategorie(this.idCategorie);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		/*this.requestCode = getIntent().getExtras().getInt("request_code");
		Log.i("LG", "SelectionPlaceActivity request code:"+requestCode);
		if(getIntent().getExtras().containsKey("readOnly")){
			this.isReadOnly = getIntent().getExtras().getBoolean("readOnly");
		}
		this.view.setReadOnly(this.isReadOnly);
		if(requestCode == REQUEST_CODE.MODIFIE_POINT_ON_MAP){
			int x = getIntent().getExtras().getInt("x");
			int y = getIntent().getExtras().getInt("y");
			Log.i("LG", "Received old point: "+x+";"+y);
			view.setTargetBoxBeginPosition(x, y);
		}*/
	}

	public void ShowPopupWindow(){
		if(this.isReadOnly)
		{
			return ;
		}
		
		//this.menuWindow.showAtLocation(this.findViewById(R.id.place_selection_layout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 100, 100);
		popupWindow = new PlaceSelectPopupWindow(SmartPlanActivity.this);
		popupWindow.setWidth((int) (getWindowManager().getDefaultDisplay().getWidth() / 1.5));
		popupWindow.setHeight(250);
		popupWindow.showAtLocation(findViewById(R.id.place_selection_layout), Gravity.CENTER
				| Gravity.BOTTOM, 0, 0);
		popupWindow.SetOnSubmitListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(view==null){
					Log.e("LG", "Erreur view is null");
					return ;
				}
				
				Intent returnIntent = new Intent();
				
				SmartPlanActivity.this.setResult(RESULT_OK, returnIntent);
				SmartPlanActivity.this.finish();
			}
		});
	}
	
	@Override
	public void finish(){
		super.finish();
		if(this.popupWindow != null){
			this.popupWindow.dismiss();
		}
		this.view.StopRedraw();
	}
	
	public void HidePopupWindow(){
		if(this.isReadOnly){
			return ;
		}
		this.popupWindow.dismiss();
	}
}
