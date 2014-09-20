package com.example.sshopping;

import SmartShopping.ShortestPath.*;

import java.util.ArrayList;
import java.util.List;

import com.example.sshopping.R;
import com.example.sshopping.PlaceSelect.PlaceSelectPopupWindow;
import com.example.sshopping.PlaceSelect.PlaceSelectionView;

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
 * Classe qui permet de gerer le systeme de positionnement du PMR.
 * @author Shinthujan, Jian, Walid, Wally, Youssef
 */
public class SmartPlanActivity extends Activity {
	
	PlaceSelectPopupWindow popupWindow;
	PlaceSelectionView view;
	
	int requestCode;
	boolean isReadOnly = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_place);
		FrameLayout layout = (FrameLayout) SmartPlanActivity.this.findViewById(R.id.place_selection_layout);
		view = (PlaceSelectionView) layout.getChildAt(0);
		
		
		/***** EXEMPLE DE MAIN POUR UTILISER DIJSKTRA ET SMART MAP ***********/
		/******************************************************************/
		/******************************************************************/
		
		
		SmartMap sm = new SmartMap();
		
		/******************************************************************/
		//TODO : A IMPLEMENTER : quand on clic sur un bouton go, on récupère l'ID catégorie du produit
		// A partir de cet ID? on parcours la liste des sommets de la SmartMap et si un sommet contient cet ID, on l'ajoute à la liste
		// La liste en question (verticies): (donc ici, tous les sommets contenant par exemple l'ID numéro XXX)
		// elle ne contiendra que les sommets dont l'idCategorie == idCategorie du produit recherché !
		List<Vertex> vertices = new ArrayList<Vertex>(); // la liste des sommets pouvant contenir la catégorie recherché
		// Cas à traiter : si il n'en existe pas ! 

		// POUR L'EXEMPLE
		vertices.add(sm.getV7());
		vertices.add(sm.getV17());
		vertices.add(sm.getV15());
		vertices.add(sm.getV25());
		vertices.add(sm.getV27());
		
		// Pour le Sprint 3
		//List<String> speedShopping = new ArrayList<String>();
		//Pour le SPritn 3 :  AUTRE boucle à faire sur le nombre de sommets à faire (nombre d'objet dans la liste de course)
        
		int min = 999; // grand chiffre par défaut
		Vertex minVertex = new Vertex("min");
		
		// Calcul des minDistance à partir de v1 (sommet 1)
		// Le sommet 1 représente le départ de l'utilisateur (en dur pour l'instant)
		Dijkstra.computePaths(sm.getUserPosition());       
		
		// on vois quel sommet est le plus proche
		for (Vertex v : vertices){
			Log.v("#### TEST ####", "DANS LA BOUCLE : Le sommet  "+ v 
					+ " est à une distance de : " + v.minDistance);
			Log.v("#### TEST ####", "Le chemin pour y arriver : " + Dijkstra.getShortestPathTo(v));
        	if(v.minDistance < min){
        		min = (int) v.minDistance;
        		minVertex = v;
        	}
        }
		
		// AFFICHAGE : le chemin le plus court jusqu'au vertex le plus proche
		List<Vertex> path = Dijkstra.getShortestPathTo(minVertex);
		Log.v("#### TEST ####", "AU FINAL : Le sommet le plus proche contenant la catégorie qui nous intéresse est "+ minVertex 
							+ " et la distance est de : " + minVertex.minDistance);
		Log.v("#### TEST ####", "Le chemin pour y arriver : " + path + "\n\n");
		
		/******************************************************************/
		/******************************************************************/
		/******************* FINEXEMPLE **********************************/
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
				Point targetPoint = view.getTargetPoint();
				Point convertedTargetPoint = view.getConvertedTargetPoint();
				
				// TODO Auto-generated method stub
				Log.i("LG", "Select raw point at "+targetPoint.toString()+", the converted point at:"+convertedTargetPoint.toString()+", with planscale:"+view.getPlanScale());
				//Construire les donnees a retourner
				Intent returnIntent = new Intent();
				returnIntent.putExtra("x", convertedTargetPoint.x);
				returnIntent.putExtra("y", convertedTargetPoint.y);
				
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
