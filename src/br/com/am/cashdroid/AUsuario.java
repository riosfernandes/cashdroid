package br.com.am.cashdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.am.cashdroid.control.ControleDados;
import br.com.am.cashdroid.listeners.VoltarOnClickListener;
import br.com.am.cashdroid.messages.Mensagem;
import br.com.am.cashdroid.messages.TipoMensagem;
import br.com.am.cashdroid.model.Usuario;

public class AUsuario extends Activity {
	Button btnVoltar;
	Button btnCadastrar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_usuario);
		
		btnVoltar = getBtnVoltar();
		btnVoltar.setOnClickListener(new VoltarOnClickListener(this));
		
		btnCadastrar = getBtnCadastrar();
		btnCadastrar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				EditText txtUsuario = (EditText)findViewById(R.id.txtUsuario);
				EditText txtSenha = (EditText)findViewById(R.id.txtSenha);				
				if(txtUsuario.getText().toString().trim().length() == 0 || txtSenha.getText().toString().trim().length() == 0){
					new Mensagem(v.getContext(), TipoMensagem.AVISO, R.string.mensagem_aviso_usuario_nao_informado).show();
				}
				
				/*
				 * persistir o usuário
				 * */				
				Usuario usuario = new Usuario();
				usuario.setLogin(txtUsuario.getText().toString());
				usuario.setSenha(txtSenha.getText().toString());
				Intent intent = null;
				ControleDados<Usuario> c = new ControleDados<Usuario>(v.getContext(), Usuario.class);
				try {					
					c.salvar(usuario);
					intent = getIntent();
					intent.putExtra("usuario", usuario);
					setResult(Activity.RESULT_OK, intent);					
				} catch (Exception e) {
					setResult(Activity.RESULT_CANCELED);
				}
				finally{
					finish();					
				}
			}
		});		
	}
	
	

	private Button getBtnCadastrar() {
		if(btnCadastrar ==null)
			return (Button)findViewById(R.id.btnCadastrar);
		else
			return btnCadastrar;			
	}

	private Button getBtnVoltar() {
		if(btnVoltar == null)
			return (Button)findViewById(R.id.btnVoltar);
		else
			return btnVoltar;
	}
}
