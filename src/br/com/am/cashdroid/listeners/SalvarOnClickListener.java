package br.com.am.cashdroid.listeners;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.am.cashdroid.R;
import br.com.am.cashdroid.control.ControleDados;
import br.com.am.cashdroid.messages.Mensagem;
import br.com.am.cashdroid.messages.TipoMensagem;

public class SalvarOnClickListener<T> implements OnClickListener {
	Context context;
	public T t;
	
	public SalvarOnClickListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		if(t == null)
			return;
		
		ControleDados<T> controle = new ControleDados<T>(context, (Class<T>)t.getClass());
		try{
			t = controle.salvar(t);
			
			Mensagem msg = new Mensagem(context, TipoMensagem.INFORMACAO, R.string.mensagem_salvo_com_sucesso);
			msg.show();
			
		}catch(Exception e){
			Mensagem msg = new Mensagem(context, TipoMensagem.ERRO, R.string.erroInesperado);
			msg.show();
		}
	}
}
