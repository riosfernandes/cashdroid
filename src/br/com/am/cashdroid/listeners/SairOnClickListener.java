package br.com.am.cashdroid.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;


public class SairOnClickListener implements OnClickListener {
	private Activity activity;
	
	public SairOnClickListener(Activity a){
		this.activity = a;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.activity.finish();
		System.exit(0);
	}
}
