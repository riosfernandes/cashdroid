package br.com.am.cashdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ARelatorioPeriodo extends ListActivity {
	ListView listaPeriodos;
	TextView itemLista;
	String[] array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_relatorio_periodo);

		array = getResources().getStringArray(R.array.listaPeriodos);
		
		setListAdapter(
				new ArrayAdapter<String>(
						this, 
						R.layout.m_simple_list,
						R.id.m_simple_list_item, 
						array));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		String item = (String)parent.getAdapter().getItem(position);
		
		if(!item.equals( getResources().getStringArray(R.array.listaPeriodos)[array.length-1])){			
			Intent intent = new Intent(this, ARelatorioPeriodoDefinido.class);
			intent.putExtra("id", parent.getAdapter().getItem(position).toString());
			
			startActivity(intent);
		}
	}
}

