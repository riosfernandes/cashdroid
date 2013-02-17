package br.com.am.cashdroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import br.com.am.cashdroid.listeners.IntentNovaActivity;

public class APrincipal extends Activity {
	ImageButton btnRegGasto, btnRelatorios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_principal);
		
		btnRegGasto = (ImageButton)findViewById(R.id.btnRegGasto);
		btnRegGasto.setOnClickListener(new IntentNovaActivity(ARegistrarGasto.class));
		
		btnRelatorios = (ImageButton)findViewById(R.id.btnRelatorioGastos);
		btnRelatorios.setOnClickListener(new IntentNovaActivity(ARelatorioPeriodo.class));
	}
}
