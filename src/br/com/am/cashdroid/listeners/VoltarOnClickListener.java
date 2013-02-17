package br.com.am.cashdroid.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class VoltarOnClickListener implements OnClickListener {
	private Activity activity;
	
	public VoltarOnClickListener(Activity a){
		this.activity = a;
	}
	
	@Override
	public void onClick(View v) {
		activity.finish();
	}
}
