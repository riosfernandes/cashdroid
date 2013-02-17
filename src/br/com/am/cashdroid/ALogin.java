package br.com.am.cashdroid;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import br.com.am.cashdroid.R.id;
import br.com.am.cashdroid.control.ControleDados;
import br.com.am.cashdroid.listeners.IntentNovaActivity;
import br.com.am.cashdroid.listeners.IntentNovaActivityComResult;
import br.com.am.cashdroid.listeners.SairOnClickListener;
import br.com.am.cashdroid.model.Usuario;

public class ALogin extends Activity {
	Button btnSair;
	Button btnEntrar;
	TextView lblNovoUsuario;
	EditText txtUsuario;
	EditText txtSenha;
	CheckBox ckLembrar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_login);

		lblNovoUsuario = (TextView) findViewById(R.id.lblNovoUsuario);
		lblNovoUsuario.setOnClickListener(new IntentNovaActivityComResult(this,
				AUsuario.class));

		txtUsuario = (EditText) findViewById(R.id.txtUsuario);
		txtSenha = (EditText) findViewById(R.id.txtSenha);
		ckLembrar = (CheckBox) findViewById(id.ckbLembrarMeusDados);

		btnSair = (Button) findViewById(R.id.btnSair);
		btnSair.setOnClickListener(new SairOnClickListener(this));

		btnEntrar = (Button) findViewById(R.id.btnEntrar);
		btnEntrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * efetuar o login no sistema
				 */
				Usuario usuario = new Usuario();
				usuario.setLogin(txtUsuario.getText().toString());
				usuario.setSenha(txtSenha.getText().toString());
				usuario.setIsLembrar(ckLembrar.isChecked() ? 1 : 0);
				ControleDados<Usuario> controle = new ControleDados<Usuario>(v.getContext(), Usuario.class);
				try {
					controle.logarUsuario(usuario);
				} catch (Exception ex) {
					return;
				}

				/*
				 * abre a tela principal do programa
				 */
				new IntentNovaActivity(APrincipal.class).onClick(v);
			}
		});

		List<Usuario> usuarios;
		ControleDados<Usuario> controle = new ControleDados<Usuario>(this, Usuario.class); 
		try {
			usuarios = controle.listarUsuarios();
		} catch (Exception e) {
			return;
		}

		/*
		 * lembrar usuário.
		 */
		for (Usuario usuario : usuarios) {
			if (usuario.getIsLembrar() == 1) {
				txtUsuario.setText(usuario.getLogin());
				txtSenha.setText(usuario.getSenha());
				ckLembrar.setChecked(true);
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode != ControleDados.PICK_RESULT)
			return;

		if (resultCode == RESULT_OK) {
			Usuario usuario = (Usuario) data.getSerializableExtra("usuario");

			txtUsuario.setText(usuario.getLogin());
			txtSenha.setText(usuario.getSenha());
			ckLembrar.setChecked(true);
		}
	}
}