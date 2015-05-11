package com.example.sshopping.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.sshopping.MainActivity;
import com.example.sshopping.SmartPlanActivity;
import com.example.sshopping.SmartShoppingApplication;
import com.example.sshopping.http.OnDataReturnListener;

import SmartShopping.OV.OVListeProduit;
import SmartShopping.OV.OVProduit;
import SmartShopping.OV.ReqListeProduit;
import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainListProduitAdapter extends BaseAdapter{

	public List<OVListeProduit> smartList = new ArrayList<OVListeProduit>();
	public List<OVProduit> allProduits;
	private Context context;
	private LayoutInflater inflater;
	public MainListProduitAdapter(Context context, List<OVProduit> produits, List<OVListeProduit> sl){
		this.smartList = sl;
		this.allProduits = produits;
		this.context = context;
		this.inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return smartList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.smartList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return this.smartList.get(arg0).getId();
	}

	public OVProduit getProduitById(int id){
		for(OVProduit prd : this.allProduits){
			if(prd.getId() == id){
				return prd;
			}
		}
		return null;
	}
	
	
	
	@Override
	public View getView(int position, View converter, ViewGroup parent) {
		// TODO Auto-generated method stub
			converter = inflater.inflate(com.example.sshopping.R.layout.list_produit_row, null);
			OVListeProduit prodToShow = this.smartList.get(position);

			OVProduit clickedProduit = this.getProduitById(prodToShow.getIdProduit());

			//Creation Checkbox
			final CheckBox checkbox = (CheckBox) converter.findViewById(com.example.sshopping.R.id.list_produit_row_checked);
			checkbox.setTag(prodToShow);
			checkbox.setChecked(prodToShow.getCoche());
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean arg1) 
					{
					
					//On desactive la view jusau'au retour de serveur
					checkbox.setEnabled(false);

					OVListeProduit objToUpdate = (OVListeProduit) arg0.getTag();
					objToUpdate.setCoche(arg1);
					ReqListeProduit requestObj = new ReqListeProduit();
					requestObj.setOvListeProduit(objToUpdate);
					requestObj.requestUpdateListeProduit(new OnDataReturnListener(){

						@Override
						public void OnDataReturn(JSONObject jobj) {
							checkbox.setEnabled(true);//On reactive la view
						}
						
					});
					
				}
				
			});

			// Creation textView
			final TextView text = (TextView) converter.findViewById(com.example.sshopping.R.id.list_produit_row_description);
			text.setTag(clickedProduit);
			Typeface font = Typeface.createFromAsset(context.getAssets(), "calibriLight.ttf");
			text.setText(clickedProduit.getNomProduit() + "    " + clickedProduit.getPrix() + "");
			
			// Creation  button
			final Button btnGo = (Button) converter.findViewById(com.example.sshopping.R.id.list_produit_row_btnGO);
			btnGo.setTag(clickedProduit);
			btnGo.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(MainActivity._allSommets != null){
						OVProduit prod = (OVProduit)arg0.getTag();
						int idCat = prod.getOvCategorie().getId();
						Intent switchToPlanActivity = new Intent(context, SmartPlanActivity.class);
						switchToPlanActivity.putExtra("idCategorie", idCat);
						context.startActivity(switchToPlanActivity);
					}else{
						//Alert?
					}
				}
				
			});

			
		return converter;
	}
	
}
