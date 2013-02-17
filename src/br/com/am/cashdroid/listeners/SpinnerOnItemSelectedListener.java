package br.com.am.cashdroid.listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.am.cashdroid.R;
import br.com.am.cashdroid.model.Categoria;

public class SpinnerOnItemSelectedListener implements
		AdapterView.OnItemSelectedListener {
	Spinner spinner;
	String question;
	String textValue;

	public SpinnerOnItemSelectedListener(Spinner spinner, String question, String text) {
		this.spinner = spinner;
		this.question = question;
		this.textValue = text;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {

		Categoria c = (Categoria) spinner.getItemAtPosition(position);
		if (c.getId() == 0) {
			final Dialog d = new Dialog(v.getContext());
			d.setContentView(R.layout.m_dialog);
			d.setCancelable(true);

			TextView label = (TextView) d.findViewById(R.id.lblDialog);
			label.setText(question);

			Button btnVoltar = (Button) d.findViewById(R.id.btnVoltar);
			btnVoltar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					d.cancel();
				}
			});

			Button btnOk = (Button) d.findViewById(R.id.btnOk);
			btnOk.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					EditText txt = (EditText) d.findViewById(R.id.txtDialog);
					textValue = txt.getText().toString();										
					d.dismiss();					
				}
			});

			d.show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
