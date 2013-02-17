package br.com.am.cashdroid.control;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import br.com.am.cashdroid.R;
import br.com.am.cashdroid.dao.CashDBHelper;
import br.com.am.cashdroid.dao.GenericDao;
import br.com.am.cashdroid.dao.UsuarioDao;
import br.com.am.cashdroid.exception.EUsuarioNaoEncontrado;
import br.com.am.cashdroid.messages.Mensagem;
import br.com.am.cashdroid.messages.TipoMensagem;
import br.com.am.cashdroid.model.Sessao;
import br.com.am.cashdroid.model.Usuario;

public class ControleDados<T> {
	public static final int PICK_RESULT = 9999;
	Class<T> clazz;
	Context context;

	public ControleDados(Context context, Class<T> clazz) {
		this.context = context;
		this.clazz = clazz;		
	}

	@SuppressWarnings("unchecked")
	public Usuario salvarUsuario(Usuario usuario)
			throws Exception {
		try {
			Usuario u = (Usuario) salvar((T) usuario);

			lembrarUsuario(u);

			return u;
		} catch (Exception e) {
			throw e;
		}
	}

	public void lembrarUsuario(Usuario usuario)
			throws Exception {
		SQLiteDatabase db = null;
		try {
			db = (new CashDBHelper(context)).getWritableDatabase();
			db.beginTransaction();

			UsuarioDao dao = new UsuarioDao();
			dao.lembrarUsuario(db, usuario);

			db.setTransactionSuccessful();

		} catch (Exception ex) {
			Mensagem msn = new Mensagem(context, TipoMensagem.ERRO,
					R.string.erroInesperado);
			msn.show();
			throw ex;
		} finally {
			try {
				db.endTransaction();
				db.close();
				db = null;
			} catch (Exception ex) {
			}
		}
	}

	public T salvar(T t) throws Exception {
		GenericDao<T> dao = new GenericDao<T>(t.getClass());
		SQLiteDatabase db = null;
		try {
			try {
				db = new CashDBHelper(context).getWritableDatabase();

				db.beginTransaction();
				dao.salvar(db, t);
				db.setTransactionSuccessful();

			} catch (Exception ex) {
				throw ex;
			} finally {
				try {
					db.endTransaction();
					db.close();
					db = null;
				} catch (Exception ex) {
				}
			}
			try {
				db = new CashDBHelper(context).getReadableDatabase();
				
				T t1 = dao.findLast(db);
				return t1;
				
			} catch (Exception e) {
				throw e;
			} finally {
				try {
					db.close();
				} catch (Exception ex) {
				}
			}
		} catch (Exception e) {
			Mensagem msg = new Mensagem(context, TipoMensagem.AVISO,
					R.string.erroInesperado);
			msg.show();
			throw e;
		}
	}

	public List<Usuario> listarUsuarios() throws Exception {
		GenericDao<Usuario> dao = new GenericDao<Usuario>(Usuario.class);
		try {
			return dao.findAll(
					new CashDBHelper(context).getReadableDatabase(),
					null, 
					null);
		} catch (Exception ex) {
			Mensagem msg = new Mensagem(
					context, 
					TipoMensagem.AVISO,
					R.string.erroInesperado);
			msg.show();
			throw ex;
		}
	}

	public void logarUsuario(Usuario usuario) throws Exception {
		UsuarioDao dao = new UsuarioDao();
		Usuario u = null;
		
		try{
		try {
			u = dao.find(
					new CashDBHelper(context).getReadableDatabase(),
					new String[] { "login", "senha" }, 
					new String[] { usuario.getLogin(), usuario.getSenha() });

		} catch (Exception ex) {			
			throw ex;
		}

		if (u != null) {
			u.setIsLembrar(usuario.getIsLembrar());
			
			lembrarUsuario(u);
			Sessao.setUsuarioLogado(u);
		}
		else 
			throw new EUsuarioNaoEncontrado();
		
		} catch (EUsuarioNaoEncontrado ex) {
			Mensagem msg = new Mensagem(
					context, 
					TipoMensagem.AVISO,
					R.string.usuarioNaoEncontrado);
			msg.show();
			throw ex;
		} catch (Exception ex) {
			Mensagem msg = new Mensagem(
					context, 
					TipoMensagem.AVISO,
					R.string.erroInesperado);
			msg.show();
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayAdapter<T> getArrayAdapterOf(String[] clausulas, String[] parametros)
			throws Exception {
		GenericDao<T> dao = new GenericDao<T>(clazz);

		try {
			List<T> listaDados = dao.findAll(
					new CashDBHelper(context).getReadableDatabase(),
					clausulas,
					parametros);

//			T c = clazz.getConstructor(String.class, int.class).newInstance("Novo", 0);			
//			listaDados.add(c);

			T[] arrayOf = (T[])listaDados.toArray();

			ArrayAdapter<T> adapter = new ArrayAdapter<T>(
					context, 
					android.R.layout.simple_spinner_item,
					arrayOf);
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			return adapter;

		} catch (EUsuarioNaoEncontrado ex) {
			Mensagem msg = new Mensagem(context, TipoMensagem.AVISO,
					R.string.usuarioNaoEncontrado);
			msg.show();
			throw ex;
		} catch (Exception ex) {
			Mensagem msg = new Mensagem(context, TipoMensagem.AVISO,
					R.string.erroInesperado);
			msg.show();
			throw ex;
		}
	}
}
