package br.com.am.cashdroid.messages;

import br.com.am.cashdroid.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

public class Mensagem extends Builder {
	private TipoMensagem tipo;

	public Mensagem(Context arg0, TipoMensagem tipo) {
		super(arg0);
		this.tipo = tipo;
	}
	
	public Mensagem(Context arg0, TipoMensagem tipo, int msgId) {
		this(arg0, tipo);
		super.setMessage(msgId);
	}
		
	@Override
	public AlertDialog show() {
		switch (tipo) {
		case AVISO:
			super.setTitle(R.string.mensagem_titulo_aviso);
			super.setIcon(android.R.drawable.ic_dialog_alert);
			super.setPositiveButton("OK", null);
			break;
			
		case INFORMACAO:
			super.setTitle(R.string.mensagem_titulo_informacao);
			super.setIcon(android.R.drawable.ic_dialog_info);
			super.setPositiveButton("OK", null);
			break;

		default:
			break;
		}

		return super.show();
	}
}
