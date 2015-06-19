package com.example.sshopping;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
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
import android.widget.ListView;

import com.example.sshopping.adapter.MainListProduitAdapter;
import com.example.sshopping.http.OnDataReturnListener;
import com.example.sshopping.notification.ButtonNotification;
import com.example.sshopping.notification.Notification;
import com.example.sshopping.notification.NotificationFactory;
import com.example.sshopping.notification.SimpleNotification;
import com.example.sshopping.views.ISlideMenuActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import SmartShopping.OV.OVCategorie;
import SmartShopping.OV.OVCommande;
import SmartShopping.OV.OVListeProduit;
import SmartShopping.OV.OVNotification;
import SmartShopping.OV.OVProduit;
import SmartShopping.OV.OVSmartList;
import SmartShopping.OV.OVSommet;
import SmartShopping.OV.OVUtilisateur;
import SmartShopping.OV.RepNotification;
import SmartShopping.OV.RepProduit;
import SmartShopping.OV.RepSmartList;
import SmartShopping.OV.RepSommet;
import SmartShopping.OV.RepUtilisateur;
import SmartShopping.OV.ReqListeProduit;
import SmartShopping.OV.ReqNotification;
import SmartShopping.OV.ReqProduit;
import SmartShopping.OV.ReqSmartList;
import SmartShopping.OV.ReqSommet;
import SmartShopping.OV.ReqUtilisateur;
import SmartShopping.Remote.WebServer;
import eu.smartbeacon.sdk.core.SBBeacon;
import eu.smartbeacon.sdk.core.SBLocationManager;
import eu.smartbeacon.sdk.core.SBLocationManager.Frequency;
import eu.smartbeacon.sdk.core.SBLocationManagerListener;
import eu.smartbeacon.sdk.utils.SBLogger;



// SmartBeacon imports
// other imports

/**
 * Classe principale, qui permet de gerer l'application.
 * @author Shinthujan, Jian, Walid, Wally, Youssef
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements ISlideMenuActivity, SBLocationManagerListener {

	public ActionBar 		actionBar;
	public boolean 			menuOk 					= false;

	private List<OVCategorie> _allCategorie = new ArrayList<OVCategorie>();
	private List<OVProduit> _allProduits = new ArrayList<OVProduit>();
	private List<OVListeProduit> _myListeProduit = new ArrayList<OVListeProduit>();
	
	public static List<OVSommet> _allSommets = null;
	private static List<OVProduit> _smartListEtablished = new ArrayList<OVProduit>();

	private OVUtilisateur _utilisateur;
	private OVSmartList _mySmartList;
	private MainListProduitAdapter adapter;
	private FragmentManager	_fm;
	private DrawerLayout	_drawerLayout;
	private AutoCompleteTextView _autocomleteView;
	private ListView _listview_produit;
	private Button _btnDelete;
	private Button _speedShopping;
	public static boolean boucleNotif = false;
	public static long boucleNotifTime = 0;
	public int delayNotif = 20000; //180000ms -> 3min, 60000ms -> 1min

    // btn pormotion avec reponse en cours
    private ButtonNotification btnNotif = null;
    private OVNotification ovNotif = null;

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
		MainActivity.this._autocomleteView =  (AutoCompleteTextView)MainActivity.this.findViewById(R.id.autocomplet_produit);
		this._listview_produit = (ListView)MainActivity.this.findViewById(R.id.main_produit_list);

		this._fm = getSupportFragmentManager();
		
		this._btnDelete = (Button)this.findViewById(R.id.btn_supprime_prod);
		this._btnDelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				List<OVListeProduit> listeCompletToUpdate = new ArrayList<OVListeProduit>();

				for(int i = 0, j = MainActivity.this._listview_produit.getChildCount(); i < j; i++){
					// then, you can remove the the row you want...
					// for instance...
					View row = (View) MainActivity.this._listview_produit.getChildAt(i);

					boolean isCheckboxChecked = ((CheckBox)row.findViewById(R.id.list_produit_row_checked)).isChecked();
					OVListeProduit oneListeProduit = (OVListeProduit) row.findViewById(R.id.list_produit_row_checked).getTag();
					oneListeProduit.setCoche(isCheckboxChecked);
					oneListeProduit.setSupprime(isCheckboxChecked);
					
					listeCompletToUpdate.add(oneListeProduit);
					
					if(isCheckboxChecked) {
						MainActivity.this._mySmartList.getProduitsSmartList().remove(oneListeProduit);
					}
				}
				
				ReqSmartList updateRequest = new ReqSmartList();
				OVSmartList smartListToUpdate = new OVSmartList(listeCompletToUpdate, MainActivity.this._mySmartList.getNom(), MainActivity.this._mySmartList.getId());
				smartListToUpdate.setId(MainActivity.this._mySmartList.getId());
				
				updateRequest.setSmartList(smartListToUpdate);
				
				updateRequest.requestUpdateSmartList(new OnDataReturnListener(){

					@Override
					public void OnDataReturn(JSONObject jobj) {
						// TODO Auto-generated method stub
						MainActivity.this.adapter.notifyDataSetChanged();
					}
					
				});
				
			}

		});
		this._speedShopping = (Button)this.findViewById(R.id.btn_spped_shopping);
		this._speedShopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ArrayList<Integer> listCategorieID = new ArrayList<Integer>();

				// clear la liste des produits établis, sinon doublons
				MainActivity._smartListEtablished.clear();

				for (int i = 0, j = MainActivity.this._listview_produit.getChildCount(); i < j; i++) {
					// then, you can remove the the row you want...
					// for instance...
					View row = (View) MainActivity.this._listview_produit.getChildAt(i);

					boolean isCheckboxChecked = ((CheckBox) row.findViewById(R.id.list_produit_row_checked)).isChecked();
					OVProduit oneProd = (OVProduit) row.findViewById(R.id.list_produit_row_btnGO).getTag();
					MainActivity._smartListEtablished.add(oneProd);
					if (/*!isCheckboxChecked && */listCategorieID.contains(oneProd.getOvCategorie().getId()) == false) {
						listCategorieID.add(oneProd.getOvCategorie().getId());
					}
				}
				Log.i("############", "Nb in MA:" + listCategorieID.size());
				Intent intent = new Intent(MainActivity.this, SmartPlanActivity.class);
				intent.putIntegerArrayListExtra("listeCategorie", listCategorieID);
				startActivity(intent);
			}
		});
		
		
		// requete tous les sommets
		ReqSommet reqSommet = new ReqSommet();
		reqSommet.requestTousLesSommets(new OnDataReturnListener() {

			@Override
			public void OnDataReturn(JSONObject jobj) {
				RepSommet repS = new RepSommet(jobj.toString());
				MainActivity._allSommets = repS.getListeSommet();


				Log.v("#### JSON ####", jobj.toString());
				Log.v("#### LISTE SOMMETS ####", MainActivity._allSommets.toString());
			}
		});

		// requete tous les produits
		ReqProduit reqProduit = new ReqProduit();
		reqProduit.requestTousLesProduits(new OnDataReturnListener() {

			@Override
			public void OnDataReturn(JSONObject jobj) {
				try {
					RepProduit repP = new RepProduit(jobj.toString());
					MainActivity.this._allProduits = repP.getListeProduit();

					MainActivity.this._autocomleteView.setAdapter(new ArrayAdapter<OVProduit>(MainActivity.this,
							android.R.layout.simple_dropdown_item_1line,
							MainActivity.this._allProduits
					));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		ReqUtilisateur reqUtilisateur = new ReqUtilisateur();
		TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		reqUtilisateur.requestUser(mngr.getDeviceId(), new OnDataReturnListener() {
			@Override
			public void OnDataReturn(JSONObject jobj) {
				RepUtilisateur repUtilisateur = new RepUtilisateur(jobj.toString());
				MainActivity.this._utilisateur = repUtilisateur.getUtilisateur();

				ReqSmartList reqSmartList = new ReqSmartList();
				reqSmartList.requestGetSmartList(MainActivity.this._utilisateur,
						new OnDataReturnListener() {
							@Override
							public void OnDataReturn(JSONObject jobj) {
								MainActivity.this._mySmartList = new RepSmartList(jobj.toString()).getSmartList();
								//MainActivity.this._mySmartList.setId(1);
								MainActivity.this.adapter = new MainListProduitAdapter(MainActivity.this, _allProduits, MainActivity.this._mySmartList.getProduitsSmartList());
								MainActivity.this._listview_produit.setAdapter(adapter);

							}

						});
			}
		});
		
		this._autocomleteView.setOnItemClickListener(new OnItemClickListener() {
			OVListeProduit produitToAdd;

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
									long id) {

				OVProduit clickedProduit = (OVProduit) parent.getItemAtPosition(position);
				if (clickedProduit != null) {
					MainActivity.this.addProduitToSmartList(clickedProduit);
				}

			}

		});


		/*********************CODE DE TESTE Notification*****************/
		ReqNotification reqNotification = new ReqNotification();
		reqNotification.requestNotifications(new OnDataReturnListener() {
			@Override
			public void OnDataReturn(JSONObject jobj) {
				RepNotification repN = new RepNotification();

				Log.v("BEACON", jobj.toString());

				try {
					JSONArray jsonA = jobj.getJSONArray("listeNotification");
					if (jsonA.length() > 0) {
						Log.v("BEACON", "Trouve "+jsonA.length()+" notif");
						OVNotification notification = new OVNotification(jsonA.getString(0), MainActivity.this); // Prend la premiere notification
						Notification notif = NotificationFactory.BuildNotification(MainActivity.this, notification);
						notif.Show();

					} else { // il n'y a aucune notification
						Log.i("Notification LOG", "No Notification received");
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("Notification LOG", "Error: "+e.toString());
				}

			}
		});

        // REPONSE ANDROID WEAR PROMOTION (accpeter / refuser)

       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] repAccepte = getIntent().getStringArrayExtra("ACCEPTER");
            //Boolean repRefuser = getIntent().getStringExtra("REFUSER", false);

            Log.i("REP ACCEPTE", "REP = "+ repAccepte.toString());
            //Log.i("REP REFUSER", "REP = "+ repRefuser.toString());

            OVNotification ovNot = new OVNotification(((String)repAccepte[1]), this);
            Log.i("REP NOTIF", ovNotif.getTexte());
            if(((String)repAccepte[0]).equals("OK")) { // on a accepté la notif de promo
                btnNotif.clickAccpeterPromotion(ovNot);
            }/*else if(repRefuser){ // refus notif
               // btnNotif.clickRefuserPromotion(btnNotif.getOvNotif());
            }
        }*/

		/******FIN Code de teste Notification**************/

		this.actionBar.setDisplayShowTitleEnabled(true);
		this.actionBar.setDisplayHomeAsUpEnabled(true);
		this.actionBar.setDisplayShowCustomEnabled(true);

		this._fm = getSupportFragmentManager();
		this.switchFragment(new EmptyFragment());
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
		return false;
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

	public OVProduit getProduitById(int id){
		for(OVProduit prd : this._allProduits){
			if(prd.getId() == id){
				return prd;
			}
		}
		return null;
	}
	
	public static List<OVSommet> getMapSommets(){
		return MainActivity._allSommets;
	}
	
	public static List<OVProduit> getSmartListEtablished(){
		return MainActivity._smartListEtablished;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// enable logging message
		SBLogger.setSilentMode(true);

		// get shared instance of SBLocationManager
		SBLocationManager sbManager = SBLocationManager.getInstance(this);

		// add SmartBeacon region
		sbManager.addEntireSBRegion();

		// register for beacon location update
		sbManager.addBeaconLocationListener(this);

		// you can also change update frequency (update between each update)
		// available values: Frequency.HIGH (eq. 1 sec), Frequency.DEFAULT (eq. 3 sec) and Frequency.LOW (eq. 5 sec)
		//
		// by default, value is Frequency.DEFAULT
		sbManager.setUpdateFrequency(Frequency.HIGH);

		// start monitoring and ranging beacons
		sbManager.startMonitoringAllBeaconRegions();
	}

	@Override
	protected void onDestroy() {
		// stop monitoring and ranging beacons
		SBLocationManager.getInstance(this)
				.stopMonitoringAllBeaconRegions();

		super.onDestroy();
	}

	//
	// these three methods will be called after waiting delay defined by frequency value.
	//
	// called when app enters in range of beacons list
	@Override
	public void onEnteredBeacons(List<SBBeacon> beacons) {
		SBLogger.d("we enter into " + beacons.size() + " beacons!");
	}

	// called when app exits in range of beacons list
	@Override
	public void onExitedBeacons(List<SBBeacon> beacons) {
		SBLogger.d("we leave " + beacons.size() + " beacons!");
	}

	// called when app is in range of beacons list
	@Override
	public void onDiscoveredBeacons(List<SBBeacon> beacons) {
		SBLogger.d("we discover " + beacons.size() + " beacons!");
		Log.i("test", "beacon " + beacons.size());
	}

	public void addProduitToSmartList(OVProduit produit){
		final OVListeProduit produitToAdd = new OVListeProduit(
				false, false, produit.getId(), MainActivity.this._mySmartList.getId()
		);
		produitToAdd.setId(-1);//Mettre un id bidon au départ

		try {
			Log.i("HttpClient", produitToAdd.toJSON().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReqListeProduit reqAddProduit = new ReqListeProduit();
		reqAddProduit.setOvListeProduit(produitToAdd);

		reqAddProduit.requestAddListeProduit(new OnDataReturnListener() {

			@Override
			public void OnDataReturn(JSONObject jobj) {
				try {
					int newLstProduitID = jobj.getInt("idListeProduit");
					produitToAdd.setId(newLstProduitID);
					MainActivity.this._mySmartList.getProduitsSmartList().add(produitToAdd);
					MainActivity.this.adapter.notifyDataSetChanged();
					MainActivity.this._autocomleteView.setText("");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
	}

	public OVSmartList getMySmartList(){
		return this._mySmartList;
	}

	@Override
	public void onUpdatedProximity(SBBeacon beacon, SBBeacon.Proximity fromProximity, SBBeacon.Proximity toProximity) {

		if(!this.boucleNotif) {

			int major = beacon.getMajor();
			int minor = beacon.getMinor();
			Log.i("BEACON LOG", "Major:"+major+";"+"Minor:"+minor);

			int distance = toProximity.getValue();
			Log.i("COMMANDE LOG", "BEACON Minor:"+minor+"; distance:"+distance);

			if(minor == 764){
				if( distance == SBBeacon.Proximity.IMMEDIATE.getValue() && MainActivity.this._mySmartList != null && MainActivity.this._allProduits != null) {
					Log.i("COMMANDE LOG", "BEACON caisse detecté");
					TelephonyManager mngr = (TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
					String str = mngr.getDeviceId();
					str = str.substring(str.length() - 8, str.length());

					double montantTotal = 0;

					List<OVListeProduit> currentProd = MainActivity.this._mySmartList.getProduitsSmartList();
					for(int i=0; i<currentProd.size();++i){
						int idProd = currentProd.get(i).getIdProduit();
						for(int j=0; j<MainActivity.this._allProduits.size(); ++j){
							OVProduit prod = MainActivity.this._allProduits.get(i);
							if(prod.getId() == idProd){
								montantTotal += prod.getPrix();
								break;
							}
						}
					}
					OVCommande ovCommande = new OVCommande(MainActivity.this._mySmartList.getId(), Integer.parseInt(str), (float)montantTotal);



					WebServer ws = WebServer.getInstance();
					try {
						List<NameValuePair> nvp = new ArrayList<NameValuePair>();

						Log.i("COMMANDE LOG", "New commande:" + ovCommande.toJSON().toString());
						nvp.add(new BasicNameValuePair("Commande", ovCommande.toJSON().toString()));
						ws.sendRequest(WebServer.COMMANDE.AddCommande, nvp, new OnDataReturnListener() {
							@Override
							public void OnDataReturn(JSONObject jobj) {

								SimpleNotification simpleNotification = new SimpleNotification(MainActivity.this, "Vous avez payé la commande !");
								simpleNotification.Show();

								Log.i("COMMANDE LOG", jobj.toString());
							}
						});

					} catch (Exception e) {
						Log.i("BEACON LOG", "Error Commande beacon:"+e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Log.i("BEACON LOG", "But SmartList NULL");
				}
			}else {
				Log.i("BEACON LOG", "promotion tache");

				// requete notification
				ReqNotification reqNotification = new ReqNotification();
				reqNotification.setMajor(major);
				reqNotification.setDistance(distance);

				reqNotification.requestNotifications(new OnDataReturnListener() {
					@Override
					public void OnDataReturn(JSONObject jobj) {
						RepNotification repN = new RepNotification();



						try {
							JSONArray jsonA = jobj.getJSONArray("listeNotification");
							if (jsonA.length() > 0) {
								OVNotification notification = new OVNotification(jsonA.getString(0), MainActivity.this); // Prend la premiere notification
								Notification notif = NotificationFactory.BuildNotification(MainActivity.this, notification);
                                if(notif.getClass().getSimpleName().equals("ButtonNotification")){
                                    btnNotif = (ButtonNotification) notif;
                                    ovNotif = notification;
                                }
								notif.Show();
                                MainActivity.boucleNotif = true;
								MainActivity.boucleNotifTime = System.currentTimeMillis();

							} else { // il n'y a aucune notification
								Log.i("Notification LOG", "No Notification received");
							}
						} catch (Exception e) {
							Log.e("Notification LOG", e.toString());
						}

					}
				});

			}

		} else if (System.currentTimeMillis() > (this.boucleNotifTime + this.delayNotif))  {
			this.boucleNotif = false;
		}
	}
}
