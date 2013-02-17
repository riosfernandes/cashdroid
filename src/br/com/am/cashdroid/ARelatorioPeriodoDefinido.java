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
import br.com.am.cashdroid.messages.Mensagem;
import br.com.am.cashdroid.messages.TipoMensagem;
import br.com.am.cashdroid.model.Categoria;

public class ARelatorioPeriodoDefinido extends ListActivity {
	TextView lblTitulo;
	List<RelatorioDetalheCategoria> dados;
	String itemSelecionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.a_relatorio_periodo);
		
		lblTitulo = (TextView)findViewById(R.id.lblRelatorioTitulo);
		
		Intent intent = this.getIntent();
		itemSelecionado = intent.getExtras().get("id").toString();
				
		lblTitulo.setText(itemSelecionado);
		
		try {
			dados = ControleRelatorio.getDadosPorPeriodo(ARelatorioPeriodoDefinido.this, itemSelecionado);			
		} catch (Exception e) {
			Mensagem msg = new Mensagem(this, TipoMensagem.ERRO, R.string.erroInesperado);
			msg.show();
			return;
		} 
		
		setListAdapter(new MyAdapter());			
	}
	
	@Override
	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		RelatorioDetalheCategoria item =(RelatorioDetalheCategoria) parent.getAdapter().getItem(position); 
		
		if(item.getCategoria().getId() != 0){
			Intent intent = new Intent(this, ARelatorioDetalheSubCategoria.class);
			intent.putExtra("item_selecionado", (RelatorioDetalheCategoria)parent.getAdapter().getItem(position));
			intent.putExtra("periodo_selecionado", itemSelecionado );
			
			startActivity(intent);
		}
	}

	public class MyAdapter extends ArrayAdapter<RelatorioDetalheCategoria>{
		public MyAdapter(){
			super(ARelatorioPeriodoDefinido.this, R.layout.m_two_columns_list, dados);
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
			
			RelatorioDetalheCategoria item_linha =(RelatorioDetalheCategoria)dados.get(position);
			Categoria categoria = item_linha.getCategoria();
			Double total = item_linha.getTotal();
			
			TextView item = (TextView)row.findViewById(R.id.m_simple_list_item_left);
			item.setText( categoria.getDescricao() );
			
			TextView valor = (TextView)row.findViewById(R.id.m_simple_list_item_right);
			DecimalFormat f = new DecimalFormat(",##0.00");
			valor.setText( f.format(total));
			
			return row;
		}
	}
}
