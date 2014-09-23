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
	private List<Vertex> path;
	private SmartMap smartMap;
	private SmartPlanView _v;
	private int size = 25;
	

	private Bitmap from;
	private Bitmap to;
	
	
	public PathDrawer(SmartPlanView view, SmartMap sm, List< Vertex > calculedPath){
		this.planOffset = view.getPlanOffset();
		this.smartMap = sm;
		this._v = view;
		this.from = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.smartshopping_logo);
		this.to = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.finish);
		this.path = calculedPath;
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
		paint.setStrokeWidth(25);
		paint.setStyle(Style.STROKE);
		
		paint.setColor(Color.rgb(203, 0, 115));
		paint.setMaskFilter(new BlurMaskFilter(this.size/5 * this._v.getPlanScale(), Blur.NORMAL));
		
		Path drawPath = new Path();
		float fromLeft=0, fromTop=0, toLeft=0, toTop=0;
		
		Point oneCaseSize = new Point(
				this._v.getPlan().getWidth() /  (this.smartMap.getMapSize().x + this.smartMap.getMapSize().x/2),
				this._v.getPlan().getHeight() / (this.smartMap.getMapSize().y+ this.smartMap.getMapSize().y/2)
				);
		
		Point mapImgOffset = this._v.getPlanOffset();
		for(int i = 0;i<this.smartMap.getMapSize().y + this.smartMap.getMapSize().y/2;++i){
			canvas.drawLine(0 + mapImgOffset.x , oneCaseSize.y + mapImgOffset.y + i * oneCaseSize.y, canvas.getWidth(), oneCaseSize.y + mapImgOffset.y + i * oneCaseSize.y, new Paint());
		}
		for(int i = 0;i<this.smartMap.getMapSize().x + this.smartMap.getMapSize().x/2;++i){
			canvas.drawLine(oneCaseSize.x + mapImgOffset.x + i * oneCaseSize.x, 0 + mapImgOffset.y, i*oneCaseSize.x + oneCaseSize.x + mapImgOffset.x, canvas.getHeight(), new Paint());
		}
		
		for(int i=0; i<path.size();++i){
			Vertex from = path.get(i);
			
			Point mapDrawPosition  = this.CalculMapDrawNormalizedPosition(from.mapPosition);
			
			int startX = 0;
			if(mapDrawPosition.x%2 == 0){//Pair, le point est entre les deux points impair
				int left = this.getImpairPosition(oneCaseSize.x, mapDrawPosition.x-1);
				int right = this.getImpairPosition(oneCaseSize.x, mapDrawPosition.x+1);
				startX = (left + right) /2;
			}else{//Impair
				startX = this.getImpairPosition(oneCaseSize.x, mapDrawPosition.x);
			}
			int startY = 0;
			if(mapDrawPosition.y % 2 == 0){
				int top = this.getImpairPosition(oneCaseSize.y, mapDrawPosition.y   - 1);
				int bottom = this.getImpairPosition(oneCaseSize.y, mapDrawPosition.y  + 1);
				startY = (top + bottom) / 2;
			}else{
				startY = this.getImpairPosition(oneCaseSize.y, mapDrawPosition.y);
			}

			if(i == 0){
				drawPath.moveTo(startX + mapImgOffset.x, startY + mapImgOffset.y);
				fromLeft= startX - (this.from.getWidth()/2) + mapImgOffset.x;
				fromTop = startY - (this.from.getHeight()/2) + mapImgOffset.y;
			}else{
				drawPath.lineTo(startX + mapImgOffset.x, startY + mapImgOffset.y);
				if(i == path.size()-1){
					toLeft = startX- (this.to.getWidth()/2) + mapImgOffset.x;
					toTop = startY - (this.to.getWidth()/2) + mapImgOffset.y;
				}
			}
		}
		canvas.drawPath(drawPath, paint);
		//canvas.drawBitmap(from, fromLeft, fromTop, paint);
		//canvas.drawBitmap(to, toLeft, toTop, paint);
	}
	
	int getImpairPosition(int caseSideSize, int drawPosition){
		int offsetNoeud = drawPosition / 2;
		int position= (drawPosition + offsetNoeud) * caseSideSize - (caseSideSize /2);
		return position;
	}
	
	public Point CalculMapDrawNormalizedPosition(int mapPosition){
		if(mapPosition <= 0){
			return null;
		}
		
		Point size = this.smartMap.getMapSize();
		int x = ((mapPosition - 1) % size.x)+1;// ((5-1)%5)+1 = 5; 
		int y = ((mapPosition - 1) / size.x)+1;//((5-1)/5)+1 = 1;
		
		return new Point(x,y);
	}
	
}
