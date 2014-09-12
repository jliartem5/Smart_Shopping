package com.example.sshopping.PlaceSelect;

import java.util.List;

import com.example.sshopping.R;
import com.example.sshopping.SelectPlaceActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class PlaceSelectionView extends SurfaceView implements Callback,
		Runnable {

	private Thread workThread;
	private boolean continueToWork = true;

	private SurfaceHolder holder;
	private int FPS = 1000 / 30;

	private Bitmap plan;
	private Point planOffset;
	private float planScale;// nous servira pour redimentionner le plan et
							// repositionner les places
	
	private Point screenSize;
	private SelectPlaceActivity context;
	
	private boolean isReadOnly = false;

	// Les objets necessaires pour dessiner la cible
	PlaceTargetDrawer targetDrawer = null;
	private Point targetDrawerBeginPlace = null;

	public PlaceSelectionView(Context context, AttributeSet attrs) {
		super(context);
		this.context = (SelectPlaceActivity) context;
		this.holder = this.getHolder();
		this.holder.addCallback(this);

		this.setKeepScreenOn(true);// On garde l'ecran allume
		this.setOnTouchListener(new PlaceSelectTouchListener(this));
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
	
	public Point getTargetPoint(){
		return this.targetDrawer.GetCenterPoint();
	}
	
	public Point getConvertedTargetPoint(){
		Point point =new Point( this.targetDrawer.GetCenterPoint().x, this.targetDrawer.GetCenterPoint().y);
		
		point.x += -this.planOffset.x;
		point.y += -this.planOffset.y;
		
		point.x /= this.planScale;
		point.y /= this.planScale;
		
		return point;
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
		int finalPlanWidth = (int) (this.screenSize.x * 0.8);

		planScale = finalPlanWidth / this.plan.getWidth();
		Matrix matrix = new Matrix();
		matrix.postScale(planScale, planScale);
		this.plan = Bitmap.createBitmap(this.plan, 0, 0, this.plan.getWidth(),
				this.plan.getHeight(), matrix, true);

		Point relativeCentralPoint = this.calculRelativeCentralPoint(this.plan.getWidth(), this.plan.getHeight());

		this.planOffset = new Point(relativeCentralPoint.x, 0);
		
		if(this.targetDrawerBeginPlace != null){
			int transformedX = (int) (targetDrawerBeginPlace.x * this.planScale);
			int transformedY = (int) (targetDrawerBeginPlace.y * this.planScale);
			
			//on trouve les positons par rapport a l'ecran
			transformedX += this.planOffset.x;
			transformedY += this.planOffset.y;
			
			if(this.targetDrawer == null){
				this.targetDrawer = new PlaceTargetDrawer(this, transformedX, transformedY);
			}else{
				this.targetDrawer.MoveTo(transformedX, transformedY);
			}
		}
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
			clearPaint.setColor(Color.WHITE);
			canvas.drawPaint(clearPaint);
			//clearPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));

			canvas.drawBitmap(this.plan, this.planOffset.x, this.planOffset.y,
					null);

			if (this.targetDrawer != null) {
				this.targetDrawer.Draw(canvas);
			}

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.WHITE);
			paint.setTextSize(25);
			String str = "Height:" + this.screenSize.y + "; Width:"
					+ this.screenSize.x;

			//Rect bound = new Rect();
			//paint.getTextBounds(str, 0, str.length(), bound);
			//canvas.drawText(str, 10, 10 + bound.height(), paint);

			this.holder.unlockCanvasAndPost(canvas);
		}
	}

	public void ScrollPlan(int y) {
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

		if (this.targetDrawer != null) {
			this.targetDrawer.Move(0, dy);
		}
	}

	public void moveTargetBoxToRelativeScreen(int relativeX, int relativeY) {
		if (this.targetDrawer == null) {
			this.targetDrawer = new PlaceTargetDrawer(this, relativeX, relativeY);
		} else {
			this.targetDrawer.MoveTo(relativeX, relativeY);
		}
	}
	
	public void setTargetBoxBeginPosition(int rawXrelativeToMap, int rawYrelativeToMap){
		this.targetDrawerBeginPlace = new Point(rawXrelativeToMap, rawYrelativeToMap);
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
