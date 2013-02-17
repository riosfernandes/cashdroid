package br.com.am.cashdroid;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.am.cashdroid.model.RelatorioDetalhe;
import br.com.am.cashdroid.model.SubCategoria;

public class ARelatorioDetalheSubCategoriaData extends ListActivity {
	TextView lblTitulo;
	Intent intent;
	String periodo;
	String nomeCategoria;
	SubCategoria subCategoria;
	List<RelatorioDetalhe<String>> dados;
	String[] datas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_relatorio_periodo);
		
		intent = getIntent();
		periodo = intent.getExtras().getString("periodo");
		nomeCategoria = intent.getExtras().getString("categoria_nome");
		subCategoria= (SubCategoria)intent.getExtras().getSerializable("subcategoria");
		datas = (String[])intent.getExtras().get("periodo_data");
		
		lblTitulo = (TextView)findViewById(R.id.lblRelatorioTitulo);
		lblTitulo.setText(
				String.format(
						"%s\n\t%s\n\t\t%s",
						periodo,
						nomeCategoria,
						subCategoria.getDescricao()));		
		
		try {
			dados = ControleRelatorio.getDadosSubCategoriaDetalheData(
						this,						
						subCategoria, 
						datas[0], 
						datas[1]);
		} catch (Exception e) {
			return;
		}
		
		setListAdapter(new MyAdapter());
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	
	/*
	 * adapter para a lista (personalizado)
	 * */
	public class MyAdapter extends ArrayAdapter<RelatorioDetalhe<String>>{
		public MyAdapter(){
			super(ARelatorioDetalheSubCategoriaData.this, R.layout.m_two_columns_list, dados);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			/* usar o convertView promove a otimização
			 * da bateria pois envolve menos processo
			 * evitando carga da lista novamente quando
			 * o usuário efetua um scroll.
			 * */
			View row = convertView;
			if(row == null)
			{
				LayoutInflater inflater = getLayoutInflater(); 
				row = inflater.inflate(R.layout.m_two_columns_list, parent, false);
			}
			
			RelatorioDetalhe<String> item_linha =(RelatorioDetalhe<String>)dados.get(position);
			String data = item_linha.getT();
			Double total = item_linha.getTotal();
			
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			TextView item = (TextView)row.findViewById(R.id.m_simple_list_item_left);
			
			try {
				item.setText( DateFormat.format("dd/MM/yyyy", s.parse(data) ));
			} catch (ParseException e) {
				item.setText(data);
			}
						
			
			
			TextView valor = (TextView)row.findViewById(R.id.m_simple_list_item_right);
			DecimalFormat f = new DecimalFormat(",##0.00");
			valor.setText( f.format(total) );
			
			return row;
		}
	}
}
