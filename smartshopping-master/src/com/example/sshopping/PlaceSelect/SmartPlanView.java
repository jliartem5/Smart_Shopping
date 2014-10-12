package com.example.sshopping.PlaceSelect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.sshopping.MainActivity;
import com.example.sshopping.R;
import com.example.sshopping.SmartPlanActivity;

import SmartShopping.OV.OVProduit;
import SmartShopping.ShortestPath.Dijkstra;
import SmartShopping.ShortestPath.SmartMap;
import SmartShopping.ShortestPath.Vertex;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

public class SmartPlanView extends SurfaceView implements Callback,
		Runnable {

	private Thread workThread;
	private boolean continueToWork = true;

	private SurfaceHolder holder;
	private int FPS = 1000 / 30;//1000/30=

	private Bitmap plan;
	
	private Point planOffset;
	private float planScale;// nous servira pour redimentionner le plan et
							// repositionner les places
	
	private Map<Vertex, int[]> markersPosition = null;
	
	
	private Point screenSize;
	private SmartPlanActivity context;
	
	private boolean isReadOnly = false;

	private SmartMap sm;
	private PathDrawer pathDrawer = null;
	private List<Integer> targetIdCategorie = null;

	public SmartPlanView(Context context, AttributeSet attrs) {
		super(context);
		this.context = (SmartPlanActivity) context;
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		
		this.sm = new SmartMap();

		this.setKeepScreenOn(true);// On garde l'ecran allume
		
		SmartPlanTouchListener sptl = new SmartPlanTouchListener(this);
		sptl.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public void OnMarkerClick(Vertex v, int[] position) {

				Log.d("##############", "Touch on the marker of vertex: "+v.toString());
				
				String productsToPickUp = "";
				for(OVProduit p : MainActivity.getSmartListEtablished())
					if(p.getOvCategorie().getId() == v.getIdCategorie())
						productsToPickUp += "- " + p.getNomProduit()+"\n";
				
				Log.d("#### ListProd ####", productsToPickUp.toString());
				
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setTitle("Produit(s) à ramasser").setMessage(productsToPickUp)
				 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.cancel();
	               }
				 });
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				
				dialog.show();
			}
			
		});
		this.setOnTouchListener(sptl);
		this.setLongClickable(true);
		
		this.workThread = new Thread(this);
		
	}
	
	public boolean isReadOnly(){
		return this.isReadOnly;
	}
	
	public void setReadOnly(boolean ro){
		this.isReadOnly = ro;
		Log.i("LG", "Place selection view set to read ONLY");
		
	}
	
	public void setTargetCategorie(List<Integer> catID){
		this.targetIdCategorie = catID;
	}

	public int getFPS() {
		return this.FPS;
	}

	public Point getPlanOffset() {
		return this.planOffset;
	}

	public float getPlanScale() {
		return this.planScale;
	}

	public Bitmap getPlan() {
		return this.plan;
	}
	
	public void setMarkersPositions(Map<Vertex, int[]> markersPosition){
		this.markersPosition = markersPosition;
		// ontouche
		//	clic sur un sommet ?
		//		==> quand on clic sur un sommet : listener(Vertex v) ou listener(IdCat ?)
	}
	
	public Map<Vertex, int[]> getMarkersPositions(){
		return this.markersPosition;
	}
	
	public void ShowPopupWindow(){
		this.context.ShowPopupWindow();
	}
	
	public void HidePopupWindow(){
		this.context.HidePopupWindow();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.screenSize = new Point(this.getWidth(), this.getHeight());

		// Et ensuite on redimentionne le plan en fonction de la taille de
		// l'ecran
		this.plan = BitmapFactory.decodeResource(getResources(),
				R.drawable.plan);
		int finalPlanWidth = (int) (this.screenSize.x * 0.9);

		planScale = ((float)finalPlanWidth / (float)this.plan.getWidth());
		
		
		Matrix matrix = new Matrix();
		matrix.postScale(planScale, planScale);
		this.plan = Bitmap.createBitmap(this.plan, 0, 0, 
				this.plan.getWidth(),
				this.plan.getHeight(), matrix, true);

		Point relativeCentralPoint = this.calculRelativeCentralPoint(this.plan.getWidth(), this.plan.getHeight());

		this.planOffset = relativeCentralPoint;
		
		// position de départ
		Vertex beginPosition = this.sm.getUserPosition();
	    // le chemin de vertex pas lequem on va devoir passer (ce qu'on cherche)
		List<Vertex> pathToFollow = new ArrayList<Vertex>();
		//la liste des idCategories par lesquels ont doit passer (récupérée à partir de la liste de course).
		List<Integer> categoriesToPass = new ArrayList<Integer>();
		categoriesToPass = targetIdCategorie;
		Log.i("##########", "Cat count:"+categoriesToPass.size());
		Dijkstra.computePaths(beginPosition); 
		pathToFollow.add(beginPosition);

		// on boucle jusqu'à êtr epassé par toutes les catégories
		while(!categoriesToPass.isEmpty()){
					
			// initialisation
			int min = 999;
			Vertex minVertex = new Vertex("min", 0, 0);
			
			// on cherche la catégorie qui nous intéresse la plus proche
			for(Vertex v : sm.getVertexArr()){
				if((categoriesToPass.contains(v.getIdCategorie())) && (v.minDistance < min)){
					Log.i("#########", ""+ v.minDistance);
					min = (int) v.minDistance;
	        		minVertex = v;
	        		

	        	}
			}
    		//on copie les elements de plus curt chemin dans pathtofollow
    		for(Vertex vertexFound : Dijkstra.getShortestPathTo(minVertex)){
    			//if(!pathToFollow.contains(vertexFound))
    				pathToFollow.add(vertexFound);
    		}

			// on l'ajoute à notre passage
			//pathToFollow.add(minVertex); 
			
			for(Vertex v : sm.getVertexArr()) {
	            v.reinitializeVertex();
	        } 
			// on recalcule le chemin à partir du nouveau vertex
			Dijkstra.computePaths(minVertex);     

    		//Pour chaque fin de sous-trajet, on set market à true
    		pathToFollow.get(pathToFollow.size()-1).setMarker(true);
			
			// on supprime la categorie par laquelle on est passée
			Log.i("CATEGORIE ", ""+categoriesToPass);
			categoriesToPass.remove(new Integer(minVertex.idCategorie));
			Log.i("CATEGORIE AFTER REMOVE ", ""+categoriesToPass);
		}
		Log.i("CHEMIN FINAL", ""+pathToFollow);
		
		this.pathDrawer = new PathDrawer(this, this.sm, pathToFollow);
		
		// Et a la fin on commence a dessiner le plan
		try{
			this.workThread.start();
		}catch(Exception e){
			Log.e("LG", "Erreur de thread:"+e.getMessage());			
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}
	
	public void StopRedraw(){
		continueToWork=false;
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.StopRedraw();
	}

	@Override
	public void run() {
		while (continueToWork) {
			long beforeTimestamp = System.currentTimeMillis();

			this.redraw();

			long afterTimestamp = System.currentTimeMillis();
			long drawDuration = afterTimestamp - beforeTimestamp;

			if (drawDuration < this.FPS) {
				int millisecondToSleep = (int) (this.FPS - drawDuration);
				try {
					Thread.sleep(millisecondToSleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void redraw() {
		synchronized (this.holder) {
			Canvas canvas = this.holder.lockCanvas();

			Paint clearPaint = new Paint();
			//clearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
			clearPaint.setColor(Color.BLACK);
			canvas.drawPaint(clearPaint);
			//clearPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));

			canvas.drawBitmap(this.plan, this.planOffset.x, this.planOffset.y,
					null);
			
			this.pathDrawer.Draw(canvas);

			this.holder.unlockCanvasAndPost(canvas);
		}
	}

	public void ScrollPlanY(int y) {
		int dy = y;
		int oldY = this.planOffset.y;
		this.planOffset.y += y;

		if (y + this.planOffset.y > 0) {
			dy = -oldY;
			this.planOffset.y = 0;
		}
		if (Math.abs(this.planOffset.y) + this.screenSize.y >= this.plan.getHeight()) {
			this.planOffset.y = -(this.plan.getHeight() - this.screenSize.y);
			dy =  this.planOffset.y - oldY;
		}

	}
	
	public void ScrollPlanX(int x){
		int dx = x;
		int oldX = this.planOffset.x;
		this.planOffset.x += x;
		
		//TODO: DEBUG
		if(x + this.planOffset.x > 0){
			dx = -oldX;
			this.planOffset.x = 0;
			Log.d("HttpClient", "111:"+dx);
		}
		if(Math.abs(this.planOffset.x) + this.screenSize.x >= this.plan.getWidth()){
			this.planOffset.x = -(this.plan.getWidth() - this.screenSize.x);
			dx = this.planOffset.x - oldX;
			Log.d("HttpClient", "222:"+dx);
		}
		
	}
	
	public void ScrollPlan(int x, int y){
		this.ScrollPlanX(x);
		this.ScrollPlanY(y);
	}

	

	private Point calculRelativeCentralPoint(int width, int height) {
		Point point = new Point();

		int x = this.screenSize.x / 2 - width / 2;
		int y = this.screenSize.y / 2 - height / 2;
		point.x = x;
		point.y = y;

		return point;
	}

}
