package br.com.am.cashdroid.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.am.cashdroid.control.ControleDados;

public class IntentNovaActivityComResult implements OnClickListener {
	private Activity activity;
	private Class<?> clazz;

	public IntentNovaActivityComResult(Activity activity, Class<?> clazz) {
		this.activity = activity;
		this.clazz = clazz;
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(v.getContext(), clazz);
		activity.startActivityForResult(i, ControleDados.PICK_RESULT);
	}
}
