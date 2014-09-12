package com.example.sshopping.PlaceSelect;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.BlurMaskFilter.Blur;

public class PlaceTargetDrawer {
	
	private Point centerPoint;//
	private PlaceSelectionView _v;
	private int size = 25;
	private int alpha = 150;
	private boolean addAlpha = true;
	
	public PlaceTargetDrawer(PlaceSelectionView view, int beginX, int beginY){
		this.centerPoint = new Point(beginX, beginY);
		Point planOffset = view.getPlanOffset();
		
		this._v = view;
	}
	
	public void Draw(Canvas canvas){

		Rect rectangle = new Rect();
		rectangle.left = (int) (centerPoint.x - this.size * _v.getPlanScale());
		rectangle.right = (int) (centerPoint.x + this.size * _v.getPlanScale());
		rectangle.top = (int) (centerPoint.y - this.size * _v.getPlanScale());
		rectangle.bottom = (int) (centerPoint.y+this.size*_v.getPlanScale());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setMaskFilter(new BlurMaskFilter(this.size/2 * this._v.getPlanScale(), Blur.NORMAL));
		if(addAlpha){
			this.alpha += 4;
			if(this.alpha >= 250){
				this.addAlpha = false;
			}
		}else{
			this.alpha -= 4;
			if(this.alpha <= 150){
				this.addAlpha = true;
			}
		}
		paint.setAlpha(this.alpha);
		canvas.drawRect(rectangle, paint);
	}
	
	public Point GetCenterPoint(){
		return this.centerPoint;
	}
	
	public void MoveTo(int x, int y){
		this.centerPoint.x = x;
		this.centerPoint.y = y;
	}
	
	public void Move(int x, int y){
		this.centerPoint.x += x;
		this.centerPoint.y += y;
	}
}
