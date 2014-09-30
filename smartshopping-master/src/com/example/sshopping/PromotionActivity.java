package com.example.sshopping;

import java.util.List;

import org.json.JSONObject;

import com.example.sshopping.http.OnDataReturnListener;
import com.example.sshopping.views.ISlideMenuActivity;

import SmartShopping.OV.OVPromotion;
import SmartShopping.OV.RepPromotion;
import SmartShopping.Remote.WebServer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PromotionActivity  extends Activity implements ISlideMenuActivity{

	private DrawerLayout	_drawerLayout;
	private List<OVPromotion> _promotionList = null;
	private TableLayout _tablePromotion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_promotion);
		this._drawerLayout = (DrawerLayout)this.findViewById(R.id.main_container);
		this._tablePromotion = (TableLayout)this.findViewById(R.id.table_primotion);
		WebServer.getInstance().sendRequest(WebServer.COMMANDE.ToutesLesPromotions, null, new OnDataReturnListener(){

			@Override
			public void OnDataReturn(JSONObject jobj) {
				Log.i("-------", jobj.toString());
				RepPromotion repP = new RepPromotion(jobj);
				PromotionActivity.this._promotionList = repP.getListePromotion();
				PromotionActivity.this.ShowPromotions();
			}
			
		});
		
	}
	
	public void ShowPromotions(){
		for(OVPromotion promo : this._promotionList){
			final TableRow tableRow = new TableRow(this);      
			tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
			TextView tv = new TextView(this);
			tv.setText(promo.toString());
			tableRow.addView(tv);
			this._tablePromotion.addView(tableRow);
		}
	}

	@Override
	public void closeSlideMenu() {
		// TODO Auto-generated method stub
		this._drawerLayout.closeDrawer(Gravity.LEFT);
	}

	@Override
	public void openSlideMenu() {
		this._drawerLayout.openDrawer(Gravity.LEFT);
	}
}
