package br.com.am.cashdroid.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GenericDao<T> {
	private Class<T> clazz;

	public GenericDao(Class<? extends Object> class1) {
		this.clazz = (Class<T>) class1;
	}

	public List<T> findAll(SQLiteDatabase db, String[] clausulas,
			String[] parametros) throws Exception {

//		List<String> columns = new ArrayList<String>();
		String projection = "";
		for (Field f : clazz.getDeclaredFields()) {
			if (!f.getName().equals("serialVersionUID")) {
				f.setAccessible(true);
//				columns.add(f.getName().toString());
				projection += String.format("%s,", f.getName());
			}
		}
		projection = projection.substring(0, projection.length() - 1);

		String where = new String();
		if (clausulas != null && parametros != null) {
			if (clausulas.length != parametros.length)
				throw new Exception(
						"Parametros não coencidem com a quantidade de clausulas");

			StringBuilder aux = new StringBuilder("WHERE ");
			for (int i = 0; i < parametros.length; i++) {
				aux.append(String.format("%s = '%s' AND ", clausulas[i],
						parametros[i]));
			}
			where = aux.substring(0, aux.length() - 5);
		}

		Cursor cr = db.rawQuery(String.format("SELECT %s FROM %s %s",
				projection, clazz.getSimpleName(), where), null);

		List<T> tList = new ArrayList<T>();
		if (cr.moveToFirst()) {
			Field[] fields = clazz.getDeclaredFields();

			do {
				T u = (clazz).getConstructor(null).newInstance(null);

				for (Field f : fields) {
					int index = cr.getColumnIndex(f.getName());
					if (index == -1)
						continue;
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
						throw e;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						throw e;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw e;
					}
				}

				tList.add(u);
			} while (cr.moveToNext());

			return tList;
		}

		return tList;

	}

	public void salvar(SQLiteDatabase db, T t) throws Exception {
		try {

			ContentValues cv = new ContentValues();
			for (Field f : t.getClass().getDeclaredFields()) {
				if (!f.getName().equals("id")
						&& !f.getName().equals("serialVersionUID")) {
					f.setAccessible(true);
					cv.put(f.getName().toString(), f.get(t).toString());
				}
			}

			int id = Integer.parseInt(t.getClass().getMethod("getId", null)
					.invoke(t, null).toString());
			if (id == 0)
				db.insert(t.getClass().getSimpleName(), null, cv);
			else {
				db.update(t.getClass().getSimpleName(), cv, "id = ?",
						new String[] { String.format("%s", id) });
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	public T findLast(SQLiteDatabase db) throws Exception {

		try {
			String projection = "";
			for (Field f : clazz.getDeclaredFields()) {
				if (!f.getName().equals("serialVersionUID")) {
					f.setAccessible(true);
					projection += String.format("%s,", f.getName());
				}
			}

			projection = projection.substring(0, projection.length() - 1);

			Cursor cr = db
					.rawQuery(
							String
									.format(
											"SELECT %1$s FROM %2$s where id = (select max(id) from %2$s)",
											projection, clazz.getSimpleName()),
							null);

			T obj = null;
			if (cr.moveToFirst()) {
				Field[] fields = clazz.getDeclaredFields();

				obj = (T) clazz.getConstructor(null).newInstance(null);

				for (Field f : fields) {
					int index = cr.getColumnIndex(f.getName());
					if (index == -1)
						continue;
					try {
						f.setAccessible(true);
						String getMethod = ("get" + f.getType().getSimpleName());
						getMethod = getMethod.replace(getMethod.charAt(3),
								Character.toUpperCase(getMethod.charAt(3)));
						Method method = cr.getClass().getMethod(getMethod,
								int.class);
						Object valor = method.invoke(cr, index);

						f.set(obj, valor);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						throw e;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						throw e;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw e;
					}
				}
			}
			return obj;
			
		} catch (Exception ex) {
			throw ex;
		}
	}

	public T find(SQLiteDatabase db, String[] clausulas, String[] parametros)
			throws Exception {
		try {
			String projection = "";
			for (Field f : clazz.getDeclaredFields()) {
				if (!f.getName().equals("serialVersionUID")) {
					f.setAccessible(true);
					projection += String.format("%s,", f.getName());
				}
			}
			projection = projection.substring(0, projection.length() - 1);

			String where = new String();
			if (clausulas != null && parametros != null) {
				if (clausulas.length != parametros.length)
					throw new Exception(
							"Parametros não coencidem com a quantidade de clausulas");

				StringBuilder aux = new StringBuilder("WHERE ");
				for (int i = 0; i < parametros.length; i++) {
					aux.append(String.format("%s = '%s' AND ", clausulas[i],
							parametros[i]));
				}
				where = aux.substring(0, aux.length() - 5);
			}

			Cursor cr = db.rawQuery(String.format("SELECT %s FROM %s %s",
					projection, clazz.getSimpleName(), where), null);

			if (cr.moveToFirst()) {
				Field[] fields = clazz.getDeclaredFields();

				T obj = (T) clazz.getConstructor(null).newInstance(null);

				for (Field f : fields) {
					int index = cr.getColumnIndex(f.getName());
					if (index == -1)
						continue;
					try {
						f.setAccessible(true);
						String getMethod = ("get" + f.getType().getSimpleName());
						getMethod = getMethod.replace(getMethod.charAt(3),
								Character.toUpperCase(getMethod.charAt(3)));
						Method method = cr.getClass().getMethod(getMethod,
								int.class);
						Object valor = method.invoke(cr, index);

						f.set(obj, valor);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						throw e;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						throw e;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw e;
					}
				}

				return obj;
			} else
				return null;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
