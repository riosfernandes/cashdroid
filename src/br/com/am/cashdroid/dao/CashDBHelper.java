package br.com.am.cashdroid.dao;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.am.cashdroid.model.Categoria;
import br.com.am.cashdroid.model.Gasto;
import br.com.am.cashdroid.model.SubCategoria;
import br.com.am.cashdroid.model.Usuario;

public class CashDBHelper extends SQLiteOpenHelper {
	protected static final String DATABASE_NAME = "cashdroid.db";
	protected static final String AUTHORITY = "br.com.am.cashdroid";
	protected static final String CONTENT_URI = "content://" + AUTHORITY + "/";
	protected static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY; 
	protected static final int DATABASE_VERSION = 2;
	
	public CashDBHelper(Context c){
		super(c, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{		
		db.execSQL(MakeTable(Usuario.class).toString());
		db.execSQL(MakeTable(Categoria.class).toString());
		db.execSQL(MakeTable(SubCategoria.class).toString());
		db.execSQL(MakeTable(Gasto.class).toString());		
	}

	private StringBuilder MakeTable(Class<?> c) 
	{
		StringBuilder query = new StringBuilder(String.format("CREATE TABLE %s (id INTEGER PRIMARY KEY AUTOINCREMENT", c.getSimpleName()));	
		
		Field[] campos = c.getFields();
		
		for (Field f : c.getDeclaredFields()) {
			if(!f.getName().equals("id") && !f.getName().equals("serialVersionUID"))
				query.append(String.format(", %s %S", f.getName(), f.getType().getSimpleName()));
		}
		query.append(");");
		return query;
	}
	
	/**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	private static List<Class> getClasses(String packageName) 
		throws ClassNotFoundException, IOException 
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String fileName = resource.getFile();
            String fileNameDecoded = URLDecoder.decode(fileName, "UTF-8");
            dirs.add(new File(fileNameDecoded));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException 
	{
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
        	String fileName = file.getName();
            if (file.isDirectory()) {
                assert !fileName.contains(".");
            	classes.addAll(findClasses(file, packageName + "." + fileName));
            } else if (fileName.endsWith(".class") && !fileName.contains("$")) {
            	Class _class;
				try {
					_class = Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6));
				} catch (ExceptionInInitializerError e) {
					// happen, for example, in classes, which depend on 
					// Spring to inject some beans, and which fail, 
					// if dependency is not fulfilled
					_class = Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6),
							false, Thread.currentThread().getContextClassLoader());
				}
				classes.add(_class);
            }
        }
        return classes;
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
		db.execSQL("DROP TABLE IF EXISTS " + Usuario.class.getSimpleName());
		db.execSQL("DROP TABLE IF EXISTS " + Categoria.class.getSimpleName());
		db.execSQL("DROP TABLE IF EXISTS " + SubCategoria.class.getSimpleName());
		db.execSQL("DROP TABLE IF EXISTS " + Gasto.class.getSimpleName());
		onCreate(db);		
	}

}
