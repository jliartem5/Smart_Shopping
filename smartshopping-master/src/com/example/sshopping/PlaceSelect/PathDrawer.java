package com.example.sshopping.PlaceSelect;

import java.util.List;

import com.example.sshopping.R;

import SmartShopping.ShortestPath.Dijkstra;
import SmartShopping.ShortestPath.SmartMap;
import SmartShopping.ShortestPath.Vertex;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.BlurMaskFilter.Blur;
import android.util.Log;

public class PathDrawer {
	
	private Point planOffset;//
	private Vertex beginPoint;//Place de la personne ou de départ à cet instant
	private Vertex endPoint;
	private List<Vertex> path;
	private SmartMap smartMap;
	private SmartPlanView _v;
	private int size = 25;
	

	private Bitmap from;
	private Bitmap to;
	
	public PathDrawer(SmartPlanView view, SmartMap sm, Vertex beginPosition, Vertex endPosition){
		this.planOffset = view.getPlanOffset();
		this.beginPoint = beginPosition;
		this.endPoint = endPosition;
		this.smartMap = sm;
		this._v = view;
		Point oneCaseSize = new Point(
				this._v.getPlan().getWidth() / this.smartMap.getMapSize().x,
				this._v.getPlan().getHeight() / this.smartMap.getMapSize().y
				);
		
		this.from = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.smartshopping_logo);
		this.to = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.finish);
		
		Dijkstra.computePaths(beginPoint);
		this.path = Dijkstra.getShortestPathTo(endPoint);
		Log.v("Path length:", path.size()+"");

		// on vois quel sommet est le plus proche
		for (Vertex v : path){
			Log.v("#### TEST ####", "DANS LA BOUCLE : Le sommet  "+ v 
					+ " est à une distance de : " + v.minDistance);
			Log.v("#### TEST ####", "Le chemin pour y arriver : " + Dijkstra.getShortestPathTo(v));
        }
		
	}
	
	public void Draw(Canvas canvas){

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(30);
		paint.setStyle(Style.STROKE);
		
		paint.setColor(Color.rgb(203, 0, 115));
		paint.setMaskFilter(new BlurMaskFilter(this.size/5 * this._v.getPlanScale(), Blur.NORMAL));
		
		Path drawPath = new Path();
		float fromLeft=0, fromTop=0, toLeft=0, toTop=0;
		for(int i=0; i<path.size();++i){
			Vertex from = path.get(i);
			
			Point mapDrawPosition  = this.CalculMapDrawNormalizedPosition(from.mapPosition);
			
			Point oneCaseSize = new Point(
					this._v.getPlan().getWidth() / this.smartMap.getMapSize().x,
					this._v.getPlan().getHeight() / this.smartMap.getMapSize().y
					);
			
			int startX = mapDrawPosition.x * oneCaseSize.x - (oneCaseSize.x/2);
			int startY = mapDrawPosition.y * oneCaseSize.y + (oneCaseSize.y / 2);

			if(i == 0){
				drawPath.moveTo(startX, startY);
				fromLeft= startX - (this.from.getWidth()/2);
				fromTop = startY - (this.from.getHeight()/2);
			}else{
				drawPath.lineTo(startX, startY);
				if(i == path.size()-1){
					toLeft = startX- (this.to.getWidth()/2);
					toTop = startY - (this.to.getWidth()/2);
				}
			}
		}
		canvas.drawPath(drawPath, paint);
		canvas.drawBitmap(from, fromLeft, fromTop, paint);
		canvas.drawBitmap(to, toLeft, toTop, paint);
	}
	
	public Point CalculMapDrawNormalizedPosition(int mapPosition){
		if(mapPosition <= 0){
			return null;
		}
		
		Point size = this.smartMap.getMapSize();
		int x = ((mapPosition - 1) % size.x)+1;// ((5-1)%5)+1 = 5; 
		int y = ((mapPosition - 1) / size.x)+1;//((5-1)/7)+1 = 1;
		
		return new Point(x,y);
	}
	
}
