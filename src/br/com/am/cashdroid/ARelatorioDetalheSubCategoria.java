package br.com.am.cashdroid;

import java.text.DecimalFormat;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.am.cashdroid.dao.RelatorioDetalheCategoria;
import br.com.am.cashdroid.model.RelatorioDetalhe;
import br.com.am.cashdroid.model.SubCategoria;

public class ARelatorioDetalheSubCategoria extends ListActivity {
	List<RelatorioDetalhe<SubCategoria>> dados;
	TextView lblTitulo;
	RelatorioDetalheCategoria itemSelecionado;
	String periodo;
	String[] datas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_relatorio_periodo);
		
		Intent intent = this.getIntent();
		itemSelecionado = (RelatorioDetalheCategoria)intent.getExtras().get("item_selecionado");		
		datas = ControleRelatorio.obterPeriodoPorParametro( intent.getExtras().getString("periodo_selecionado"));
		periodo = intent.getExtras().getString("periodo_selecionado");
		
		lblTitulo = (TextView)findViewById(R.id.lblRelatorioTitulo);
		lblTitulo.setText(
				String.format(
						"%s\n\t%s",
						periodo,
						itemSelecionado.getCategoria().getDescricao()));		
		
		try {
			dados = ControleRelatorio.getDadosCategoriaPorPeriodo(
					this,
					itemSelecionado.getCategoria(), 
					datas[0], 
					datas[1],
					getResources().getString(R.string.descricaoParaSubCategoriaIndefinida));
		} catch (Exception e) {
			return;
		}
		
		setListAdapter(new MyAdapter());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RelatorioDetalhe<SubCategoria> item = (RelatorioDetalhe<SubCategoria>)l.getAdapter().getItem(position);
		
		if(item.getT().getId() != -1){
			Intent intent = new Intent(this, ARelatorioDetalheSubCategoriaData.class);
			intent.putExtra("subcategoria", item.getT());
			intent.putExtra("categoria_nome", itemSelecionado.getCategoria().getDescricao());
			intent.putExtra("periodo", periodo);
			intent.putExtra("periodo_data", datas);
			
			startActivity(intent); 
		}
	}
	
	
	
	/*
	 * adapter para a lista (personalizado)
	 * */
	public class MyAdapter extends ArrayAdapter<RelatorioDetalhe<SubCategoria>>{
		public MyAdapter(){
			super(ARelatorioDetalheSubCategoria.this, R.layout.m_two_columns_list, dados);
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
			
			RelatorioDetalhe<SubCategoria> item_linha =(RelatorioDetalhe<SubCategoria>)dados.get(position);
			SubCategoria sub = item_linha.getT();
			Double total = item_linha.getTotal();
			
			TextView item = (TextView)row.findViewById(R.id.m_simple_list_item_left);
			item.setText( sub.getDescricao() );
			
			TextView valor = (TextView)row.findViewById(R.id.m_simple_list_item_right);
			DecimalFormat f = new DecimalFormat(",##0.00");
			valor.setText( f.format(total));
			
			return row;
		}
	}
}
