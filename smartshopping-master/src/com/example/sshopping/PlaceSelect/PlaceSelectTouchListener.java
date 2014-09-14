package com.example.sshopping.PlaceSelect;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class PlaceSelectTouchListener implements OnTouchListener {
	private PlaceSelectionView view;
	private static int CLICK_MARGIN = 50;// Il faut effectuer une manoeuvre de 50 pixel pour
										// que ce soit considere comme un slide
	private static int LONG_PRESS_DURATION_DETECT = 1500;

	private float lastX, lastY;
	private float margin_counter = 0;

	private long lastTouchDownTimestamp = 0;

	public PlaceSelectTouchListener(PlaceSelectionView view) {
		this.view = view;
		Log.i("PlaceSelectToucheListener", "ListenerTouch OK");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("PlaceSelectToucheListener", "ActionDown");
			this.lastX = event.getX();
			this.lastY = event.getY();
			this.lastTouchDownTimestamp = System.currentTimeMillis();

			LongPressThread thread = new LongPressThread(event);
			//thread.run();
			
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = event.getX() - this.lastX;
			int dy = (int) (event.getY() - this.lastY);
			this.margin_counter += Math.abs(dy);
			if (this.margin_counter > CLICK_MARGIN) {
				this.view.ScrollPlanY(dy * 2);

				this.lastX = event.getX();
				this.lastY = event.getY();
				Log.i("PlaceSelectToucheListener", "ActionMove pixel:"
						+ margin_counter);
			}

			break;
		case MotionEvent.ACTION_UP:

			if (Math.abs(this.margin_counter) <= CLICK_MARGIN) {
				Log.i("PlaceSelectToucheListener",
						"Not much move:" + Math.abs(this.margin_counter)
								+ ", solved as click");
				if(this.view.isReadOnly() == false){
					this.view.moveTargetBoxToRelativeScreen((int) event.getX(),
							(int) event.getY());
					this.view.ShowPopupWindow();
				}
			}

			this.margin_counter = 0;
			Log.i("PlaceSelectToucheListener", "ActionUp");
			break;
		}
		return true;
	}

	private class LongPressThread extends Thread {

		public MotionEvent event = null;

		public LongPressThread(MotionEvent e) {
			this.event = e;

		}

		public void setEvent(MotionEvent e) {
			event = e;
		}

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			long time = 0;

			while (event.getAction() == MotionEvent.ACTION_DOWN) {
				time = System.currentTimeMillis() - startTime;
				if (time > LONG_PRESS_DURATION_DETECT) {
					System.out.println("LOOONG CLICK!!");
					return;
				}
			}
		}
	}

}
