package com.example.sshopping;

import com.example.sshopping.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Classe pour les tests : fragment vide
 * @author Shinthu
 */
public class EmptyFragment extends Fragment {	
	private MainActivity	_mainActivity;
	private View 			_view;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i("LG", "EmptyFragment attach");
		this._mainActivity = (MainActivity) activity;
	}
	
	@Override
	/**
	 * Methode se declenchant a la creation de la vue.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this._mainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
		this._mainActivity.menuOk = true;
		this._view = inflater.inflate(R.layout.empty_fragment, container, false);
		
		return this._view;
	}
	

}
