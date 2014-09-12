package com.example.sshopping;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.example.sshopping.R;

import SmartShopping.OV.OVCategorie;
import SmartShopping.OV.OVProduit;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Classe principale, qui permet de gerer l'application.
 * @author Shinthujan, Jian, Walid, Wally, Youssef
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	public ActionBar 		actionBar;
	public boolean 			menuOk 					= false;

	private List<OVCategorie> _allCategorie = new ArrayList<OVCategorie>();
	private List<OVProduit> _allProduits = new ArrayList<OVProduit>();

	private FragmentManager	_fm;
	private DrawerLayout	_drawerLayout;
	private AutoCompleteTextView _autocomleteView;
	private TableLayout _tableProduit;
	private Button _btnDelete;

	@SuppressLint("NewApi")
	@Override
	/**
	 * Methode se declenchant a la creation de l'activity.
	 * Elle cree la barre d'action (haut) et appelle le fragment connexion.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this._drawerLayout = (DrawerLayout) findViewById(R.id.main_container);

		this.actionBar = getActionBar();

		this.actionBar.setDisplayShowTitleEnabled(false);
		this.actionBar.setDisplayHomeAsUpEnabled(false);
		this.actionBar.setDisplayShowCustomEnabled(true);

		this._fm = getSupportFragmentManager();

		this._btnDelete = (Button)this.findViewById(R.id.btn_supprime_prod);
		this._btnDelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

				List<View> listViewToDelete = new ArrayList<View>();

				for(int i = 0, j = MainActivity.this._tableProduit.getChildCount(); i < j; i++){
					// then, you can remove the the row you want...
					// for instance...
					TableRow row = (TableRow) MainActivity.this._tableProduit.getChildAt(i);

					if(((CheckBox)row.getChildAt(0)).isChecked()) {
						listViewToDelete.add(MainActivity.this._tableProduit.getChildAt(i));
					}
				}
				for(View v : listViewToDelete){
					MainActivity.this._tableProduit.removeView(v);
				}
			}

		});

		this._allCategorie.add(new OVCategorie(0, "Lait"));	
		this._allCategorie.add(new OVCategorie(1, "Fruit"));	
		
		this._allProduits.add(new OVProduit(0,"Dadone", this._allCategorie.get(0), 10));
		this._allProduits.add(new OVProduit(1,"Lait naturel", this._allCategorie.get(0), 10));
		this._allProduits.add(new OVProduit(2,"Lait pourrie", this._allCategorie.get(0), 10));
		this._allProduits.add(new OVProduit(3,"Lait noir", this._allCategorie.get(0), 10));
		this._allProduits.add(new OVProduit(4,"Golden milk", this._allCategorie.get(0), 10));
		this._allProduits.add(new OVProduit(5,"Pomme", this._allCategorie.get(1), 10));
		this._allProduits.add(new OVProduit(6,"Golden appel", this._allCategorie.get(1), 10));
		this._allProduits.add(new OVProduit(7,"Raizin", this._allCategorie.get(1), 10));
		this._allProduits.add(new OVProduit(8,"Jian", this._allCategorie.get(1), 10));
		this._allProduits.add(new OVProduit(9,"RER", this._allCategorie.get(1), 10));
		this._allProduits.add(new OVProduit(10,"B-shock", this._allCategorie.get(1), 10));

		this._tableProduit = (TableLayout)this.findViewById(R.id.table_produit);
		this._autocomleteView =  (AutoCompleteTextView)this.findViewById(R.id.autocomplet_produit);
		this._autocomleteView.setAdapter(new ArrayAdapter<OVProduit>(this,
				android.R.layout.simple_dropdown_item_1line, 
				this._allProduits
				));
		this._autocomleteView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {

				OVProduit clickedProduit = (OVProduit) parent.getItemAtPosition(position);
				if(clickedProduit != null){
					// Creation row
					final TableRow tableRow = new TableRow(MainActivity.this);      
					tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

					//Creation Checkbox
					final CheckBox checkbox = new CheckBox(MainActivity.this);
					checkbox.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					checkbox.setTag(clickedProduit);

					// Creation textView
					final TextView text = new TextView(MainActivity.this);
					text.setTextSize(15);
					text.setText(clickedProduit.getNomProduit());
					text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

					// Creation  button
					final Button btnGo = new Button(MainActivity.this);
					btnGo.setText("GO");
					btnGo.setTextSize(20);
					btnGo.setTextColor(Color.WHITE);
					btnGo.setBackgroundColor(Color.BLUE);
					btnGo.setTag(clickedProduit);
					TableRow.LayoutParams p =new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
					p.gravity = Gravity.RIGHT;
					btnGo.setLayoutParams(p);


					//btnActiver.setOnClickListener(handler_btnActiver);
					tableRow.addView(checkbox);
					tableRow.addView(text);
					tableRow.addView(btnGo);

					MainActivity.this._tableProduit.addView(tableRow);
					MainActivity.this._autocomleteView.setText("");
				}

			}

		});

		//this.switchFragment(new FragmentConnexion());
	}


	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that simply returns the
	 * {@link android.app.Activity} if <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("LJ", "Click On:" + item.getTitle() + "-" + item.getItemId());
		if(menuOk) { 
			switch (item.getItemId()) {
			case android.R.id.home:
				if (this._drawerLayout.isDrawerOpen(Gravity.LEFT)) {
					this._drawerLayout.closeDrawer(Gravity.LEFT);
				} else
					this._drawerLayout.openDrawer(Gravity.LEFT);
				break;

			default:
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Cette methode recoit en parametre un fragment et se charge de "switcher" du fragment courant vers
	 * le fragment en parametre.
	 * @param fragment
	 */
	public void switchFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction transaction = this._fm.beginTransaction();
		transaction.replace(R.id.fragment_main, fragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}

	/**
	 * Methode permettant de fermer le slide menu.
	 */
	public void closeSlideMenu() {
		this._drawerLayout.closeDrawer(Gravity.LEFT);
		Log.i("LJ", "Close slide menu");
	}

	/**
	 * Methode permettant d'ouvrir le slide menu.
	 */
	public void openSlideMenu() {
		this._drawerLayout.openDrawer(Gravity.LEFT);
		Log.i("LJ", "Open slide menu");
	}


}
