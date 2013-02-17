package br.com.am.cashdroid.listeners;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateOnClickListener implements OnClickListener {
	Calendar calendar;
	DatePickerDialog.OnDateSetListener d;

	public DateOnClickListener(final EditText v) {
		calendar = Calendar.getInstance();
		d = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				v.setText(DateFormat.format("dd/MM/yyyy", calendar.getTime()));
			}
		};
	}

	@Override
	public void onClick(View v) {
		new DatePickerDialog(
				v.getContext(), 
				d, 
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}
}
