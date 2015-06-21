package com.example.sshopping.PlaceSelect;

import com.example.sshopping.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * Classe de popup
 */
public class PlaceSelectPopupWindow extends PopupWindow{
	
	Button btn;
	
	public PlaceSelectPopupWindow(Context context){
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(com.example.sshopping.R.layout.select_place_popupwindow, null);
		this.setContentView(view);
		ColorDrawable dw = new ColorDrawable(0xD0AAAAAA);
		this.setBackgroundDrawable(dw);
		
		btn =(Button) view.findViewById(R.id.btn_se_localiser);
		btn.setTextColor(0xEEDDDDDD);
		
		
		this.setOutsideTouchable(true);
		this.setFocusable(true);
	}
	
	public void SetOnSubmitListener(OnClickListener listener){
		btn.setOnClickListener(listener);
	}
}
