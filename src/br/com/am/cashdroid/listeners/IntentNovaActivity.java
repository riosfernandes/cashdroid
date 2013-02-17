package br.com.am.cashdroid.listeners;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class IntentNovaActivity implements OnClickListener {	
	private Class<?> clazz;
	
	public IntentNovaActivity(Class<?> clazz){
		this.clazz = clazz;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(v.getContext(), clazz);
		v.getContext().startActivity(i);		 
	}	
}
