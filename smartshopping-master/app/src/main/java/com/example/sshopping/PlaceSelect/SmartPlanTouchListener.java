package com.example.sshopping.PlaceSelect;

import java.util.Map;

import com.example.sshopping.R;

import SmartShopping.ShortestPath.Vertex;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class SmartPlanTouchListener implements OnTouchListener {
	private SmartPlanView view;
	private static int CLICK_MARGIN = 50;// Il faut effectuer une manoeuvre de 50 pixel pour
										// que ce soit considere comme un slide
	private static int LONG_PRESS_DURATION_DETECT = 1500;

	private float lastX, lastY;
	private float margin_counter = 0;

	private long lastTouchDownTimestamp = 0;
	Bitmap marker;
	private OnMarkerClickListener  onMarkerClick = null;

	public SmartPlanTouchListener(SmartPlanView view) {
		this.view = view;
		this.marker = BitmapFactory.decodeResource(view.getResources(), R.drawable.localisation_article);
		Log.i("PlaceSelectToucheListener", "ListenerTouch OK");
	}
	
	public void setOnMarkerClickListener(OnMarkerClickListener omcl){
		this.onMarkerClick = omcl;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int userX =(int)(event.getX());
		int userY =(int)(event.getY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("PlaceSelectToucheListener", "ActionDown");
			this.lastX = event.getX();
			this.lastY = event.getY();
			this.lastTouchDownTimestamp = System.currentTimeMillis();
			
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = (int)(event.getX() - this.lastX);
			int dy = (int) (event.getY() - this.lastY);
			this.margin_counter += Math.abs(dy+dx);
			if (this.margin_counter > CLICK_MARGIN) {
				//this.view.ScrollPlan(dx*2, dy * 2);

				this.lastX = event.getX();
				this.lastY = event.getY();
			}

			break;
		case MotionEvent.ACTION_UP:

			if (Math.abs(this.margin_counter) <= CLICK_MARGIN) {
				Log.i("PlaceSelectToucheListener",
						"Not much move:" + Math.abs(this.margin_counter)
								+ ", solved as click");
				Map<Vertex, int[]> markersPositions = this.view.getMarkersPositions();
				boolean touchedOnTheMarker = false;
				if(markersPositions != null){
					for(Vertex key : markersPositions.keySet()){
						int[] leftTop = markersPositions.get(key);
						Rect rct = new Rect(leftTop[0], leftTop[1], leftTop[0] + this.marker.getWidth(), leftTop[1]+this.marker.getHeight());
						if(rct.contains(userX, userY)){
							if(this.onMarkerClick != null){
								this.onMarkerClick.OnMarkerClick(key, leftTop);
								
							}
							touchedOnTheMarker = true;
							break;
						}
					}
				}
				if(touchedOnTheMarker == false){
					this.view.ShowPopupWindow();
				}
			}

			this.margin_counter = 0;
			Log.i("PlaceSelectToucheListener", "ActionUp");
			break;
		}
		return true;
	}

}
