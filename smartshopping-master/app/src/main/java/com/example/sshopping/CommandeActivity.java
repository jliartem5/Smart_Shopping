package com.example.sshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sshopping.http.OnDataReturnListener;
import com.example.sshopping.views.ISlideMenuActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import SmartShopping.OV.OVCommande;
import SmartShopping.OV.OVPromotion;
import SmartShopping.OV.OVUtilisateur;
import SmartShopping.OV.RepCommande;
import SmartShopping.OV.RepPromotion;
import SmartShopping.OV.ReqUtilisateur;
import SmartShopping.Remote.WebServer;

public class CommandeActivity extends Activity implements ISlideMenuActivity{

	private DrawerLayout	_drawerLayout;
	private List<OVCommande> _commandeListe = null;
	private TableLayout _tableCommande;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_commande);
		this._drawerLayout = (DrawerLayout)this.findViewById(R.id.main_container);
		this._tableCommande = (TableLayout)this.findViewById(R.id.table_commande);

		List<NameValuePair> nvp = new ArrayList<NameValuePair>();

		ReqUtilisateur reqUtilisateur = new ReqUtilisateur();
		TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = mngr.getDeviceId();
		deviceID = deviceID.substring(deviceID.length()-8, deviceID.length());

		OVUtilisateur ovUtilisateur = new OVUtilisateur(Integer.parseInt(deviceID));
		try {
			nvp.add(new BasicNameValuePair("utilisateur", ovUtilisateur.toJSON().toString()));
			Log.i("COMMANDE LOG", "Request: " + ovUtilisateur.toJSON().toString());
		}catch (Exception e){
			Log.i("COMMANDE LOG", e.getMessage());
		}
		WebServer.getInstance().sendRequest(WebServer.COMMANDE.GetCommandes, nvp, new OnDataReturnListener() {

			@Override
			public void OnDataReturn(JSONObject jobj) {
				Log.i("COMMANDE LOG", jobj.toString());
				RepCommande repP = new RepCommande(jobj);
				CommandeActivity.this._commandeListe = repP.getLstCommandes();
				CommandeActivity.this.ShowCommandes();
			}
		});
	}

	public void ShowCommandes(){
		if(this._commandeListe.size() == 0){
			final TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

			TextView tv = new TextView(this);
			tv.setText("Aucune commande");
			tableRow.addView(tv);
			this._tableCommande.addView(tableRow);
		}else {
			for (OVCommande cmd : this._commandeListe) {
				final TableRow tableRow = new TableRow(this);
				tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
				TextView tv = new TextView(this);
				tv.setText("Le "+cmd.getDate().toString());

				final TextView montant = new TextView(this);
				montant.setText(cmd.getMontant() + " €");
				tableRow.addView(tv);
				tableRow.addView(montant);
				this._tableCommande.addView(tableRow);
			}
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





