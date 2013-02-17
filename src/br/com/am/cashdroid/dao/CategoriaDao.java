package br.com.am.cashdroid.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.am.cashdroid.exception.EUsuarioNaoEncontrado;
import br.com.am.cashdroid.model.Categoria;
import br.com.am.cashdroid.model.Sessao;

public class CategoriaDao implements Dao<Categoria> {
	private SQLiteDatabase db;
	private Context context;

	public CategoriaDao(Context context) {
		this.context = context;
	}

	@Override
	public void excluir(Categoria object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Categoria> findAll() throws Exception {
		db = (new CashDBHelper(context)).getReadableDatabase();

		List<String> columns = new ArrayList<String>();
		String projection = "";
		for (Field f : Categoria.class.getDeclaredFields()) {
			if (!f.getName().equals("serialVersionUID")) {
				f.setAccessible(true);
				columns.add(f.getName().toString());
				projection += String.format("%s,", f.getName());
			}
		}
		projection = projection.substring(0, projection.length() - 1);

		Cursor cr = db.rawQuery(String.format("SELECT %s FROM %s", projection,
				Categoria.class.getSimpleName()), null);

		List<Categoria> categorias = new ArrayList<Categoria>();
		if (cr.moveToFirst()) {
			Field[] fields = Categoria.class.getDeclaredFields();

			do {
				Categoria u = new Categoria();

				for (Field f : fields) {
					if (!f.getName().equals("serialVersionUID")) {
						int index = cr.getColumnIndex(f.getName());
						try {
							f.setAccessible(true);
							String getMethod = ("get" + f.getType()
									.getSimpleName());
							getMethod = getMethod.replace(getMethod.charAt(3),
									Character.toUpperCase(getMethod.charAt(3)));
							Method method = cr.getClass().getMethod(getMethod,
									int.class);
							Object valor = method.invoke(cr, index);
							f.set(u, valor);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				categorias.add(u);
			} while (cr.moveToNext());

			return categorias;
		}

		return categorias;
	}

	@Override
	public Categoria findById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Categoria find(String descricao) throws Exception,
			EUsuarioNaoEncontrado {
		db = (new CashDBHelper(context)).getWritableDatabase();
		try {
			String projection = "";
			for (Field f : Categoria.class.getDeclaredFields()) {
				if (!f.getName().equals("serialVersionUID")) {
					f.setAccessible(true);
					projection += String.format("%s,", f.getName());
				}
			}
			projection = projection.substring(0, projection.length() - 1);

			Cursor cr = db.rawQuery(String.format(
					"SELECT %s FROM %s WHERE descricao = '%s' AND usuarioId=%s",
					projection, Categoria.class.getSimpleName(), descricao, Sessao.getUsuarioLogado().getId()), null);

			if (cr.moveToFirst()) {
				Field[] fields = Categoria.class.getDeclaredFields();

				Categoria u = new Categoria();

				for (Field f : fields) {
					int index = cr.getColumnIndex(f.getName());
					try {
						f.setAccessible(true);
						String getMethod = ("get" + f.getType().getSimpleName());
						getMethod = getMethod.replace(getMethod.charAt(3),
								Character.toUpperCase(getMethod.charAt(3)));
						Method method = cr.getClass().getMethod(getMethod,
								int.class);
						Object valor = method.invoke(cr, index);

						f.set(u, valor);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				return u;
			} else
				return null;
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				db.close();
			} catch (Exception ex) {
			}
		}
	}

	@Override
	public void salvar(Categoria categoria) throws Exception {
		db = (new CashDBHelper(context)).getWritableDatabase();

		try {
			db.beginTransaction();

			ContentValues cv = new ContentValues();
			for (Field f : categoria.getClass().getDeclaredFields()) {
				if (!f.getName().equals("id")
						&& !f.getName().equals("serialVersionUID")) {
					f.setAccessible(true);
					cv.put(f.getName().toString(), f.get(categoria).toString());
				}
			}

			if (categoria.getId() == 0)
				db.insert(Categoria.class.getSimpleName(), null, cv);
			else {
				db.update(Categoria.class.getSimpleName(), cv, "id = ?",
								new String[] { String.format("%s", categoria
										.getId()) });
			}

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.endTransaction();
			db.close();
		}

	}

}
