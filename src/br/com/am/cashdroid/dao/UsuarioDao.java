package br.com.am.cashdroid.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import br.com.am.cashdroid.model.Usuario;

public class UsuarioDao extends GenericDao<Usuario> {
	public UsuarioDao() {
		super(Usuario.class);
		// TODO Auto-generated constructor stub
	}
		
	public void lembrarUsuario(SQLiteDatabase db, Usuario u) throws Exception {
		try {
			ContentValues cv = null;
			if (u.getIsLembrar() == 1) {
				cv = new ContentValues();
				cv.put("isLembrar", 1);
				db.update(Usuario.class.getSimpleName(), cv, "id = ?",
						new String[] { String.format("%s", u.getId()) });
				
				cv = new ContentValues();
				cv.put("isLembrar", 0);				
				db.update(Usuario.class.getSimpleName(), cv, "id <> ?",
						new String[] { String.format("%s", u.getId()) });
			}
			else{
				cv = new ContentValues();
				cv.put("isLembrar", 0);
				db.update(Usuario.class.getSimpleName(), cv, "id = ?",
						new String[] { String.format("%s", u.getId()) });
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	// private SQLiteDatabase db;
	// private Context context;
	//
	// public UsuarioDao(Context context) {
	// this.context = context;
	// }
	//
	// public List<Usuario> findAll() {
	// db = (new CashDBHelper(context)).getReadableDatabase();
	//
	// List<String> columns = new ArrayList<String>();
	// String projection = "";
	// for (Field f : Usuario.class.getDeclaredFields()) {
	// if (!f.getName().equals("serialVersionUID")) {
	// f.setAccessible(true);
	// columns.add(f.getName().toString());
	// projection += String.format("%s,", f.getName());
	// }
	// }
	// projection = projection.substring(0, projection.length() - 1);
	//
	// Cursor cr = db.rawQuery(String.format("SELECT %s FROM %s", projection,
	// Usuario.class.getSimpleName()), null);
	//
	// List<Usuario> usuarios = new ArrayList<Usuario>();
	// if (cr.moveToFirst()) {
	// Field[] fields = Usuario.class.getDeclaredFields();
	//
	// do {
	// Usuario u = new Usuario();
	//
	// for (Field f : fields) {
	// int index = cr.getColumnIndex(f.getName());
	// try {
	// f.setAccessible(true);
	// String getMethod = ("get" + f.getType().getSimpleName());
	// getMethod = getMethod.replace(getMethod.charAt(3),
	// Character.toUpperCase(getMethod.charAt(3)));
	// Method method = cr.getClass().getMethod(getMethod,
	// int.class);
	// Object valor = method.invoke(cr, index);
	// f.set(u, valor);
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// usuarios.add(u);
	// } while (cr.moveToNext());
	//
	// return usuarios;
	// }
	//
	// return usuarios;
	//
	// }
	//
	// public void salvar(Usuario u) throws Exception {
	// db = (new CashDBHelper(context)).getWritableDatabase();
	//
	// try {
	// db.beginTransaction();
	//
	// ContentValues cv = new ContentValues();
	// for (Field f : u.getClass().getDeclaredFields()) {
	// if (!f.getName().equals("id")
	// && !f.getName().equals("serialVersionUID")) {
	// f.setAccessible(true);
	// cv.put(f.getName().toString(), f.get(u).toString());
	// }
	// }
	//
	// if (u.getId() == 0)
	// db.insert(Usuario.class.getSimpleName(), null, cv);
	// else {
	// db.update(Usuario.class.getSimpleName(), cv, "id = ?",
	// new String[] { String.format("%s", u.getId()) });
	//
	// /*
	// * desmarcar todos os usuários que são lembrados.
	// */
	// if (u.getIsLembrar() == 1) {
	// cv = new ContentValues();
	// cv.put("isLembrar", 0);
	//
	// db.update(Usuario.class.getSimpleName(), cv, "id <> ?",
	// new String[] { String.format("%s", u.getId()) });
	// }
	// }
	//
	// db.setTransactionSuccessful();
	// } catch (Exception ex) {
	// throw ex;
	// } finally {
	// db.endTransaction();
	// db.close();
	// }
	// }
	//
	// public Usuario find(Usuario usuario) throws Exception,
	// EUsuarioNaoEncontrado {
	// db = (new CashDBHelper(context)).getWritableDatabase();
	// try {
	// String projection = "";
	// for (Field f : Usuario.class.getDeclaredFields()) {
	// if (!f.getName().equals("serialVersionUID")) {
	// f.setAccessible(true);
	// projection += String.format("%s,", f.getName());
	// }
	// }
	// projection = projection.substring(0, projection.length() - 1);
	//
	// Cursor cr = db.rawQuery(String.format(
	// "SELECT %s FROM %s WHERE login = '%s' AND senha='%s'",
	// projection, Usuario.class.getSimpleName(), usuario
	// .getLogin().toString(), usuario.getSenha()
	// .toString()), null);
	//
	// if (cr.moveToFirst()) {
	// Field[] fields = Usuario.class.getDeclaredFields();
	//
	// Usuario u = new Usuario();
	//
	// for (Field f : fields) {
	// int index = cr.getColumnIndex(f.getName());
	// try {
	// f.setAccessible(true);
	// String getMethod = ("get" + f.getType().getSimpleName());
	// getMethod = getMethod.replace(getMethod.charAt(3),
	// Character.toUpperCase(getMethod.charAt(3)));
	// Method method = cr.getClass().getMethod(getMethod,
	// int.class);
	// Object valor = method.invoke(cr, index);
	//						
	// f.set(u, valor);
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// return u;
	// } else
	// return null;
	// } catch (Exception ex) {
	// throw ex;
	// } finally {
	// try {
	// db.close();
	// } catch (Exception ex) {
	// }
	// }
	// }
	//
	// @Override
	// public void excluir(Usuario object) throws Exception {
	// // TODO Auto-generated method stub
	//		
	// }
	//
	// @Override
	// public Usuario findById(int id) throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }
}
