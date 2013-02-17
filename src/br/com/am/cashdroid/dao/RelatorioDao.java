package br.com.am.cashdroid.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.am.cashdroid.model.Categoria;
import br.com.am.cashdroid.model.RelatorioDetalhe;
import br.com.am.cashdroid.model.SubCategoria;

public class RelatorioDao {
	public static List<RelatorioDetalheCategoria> getDadosPorPeriodo(
			Context context, String dataInicial, String dataFinal, int usuarioId) {

		SQLiteDatabase db = (new CashDBHelper(context)).getReadableDatabase();

		String query = "select" + "	categoria.id," + "	categoria.descricao,"
				+ "	sum(gasto.valor)" + " from" + "	categoria," + "	gasto"
				+ " where" + " categoria.usuarioId = %s and"
				+ "	gasto.categoriaId = categoria.id and"
				+ "	gasto.usuarioId = categoria.usuarioId and"
				+ "	gasto.data between '%s' and '%s'" + " group by"
				+ "	categoria.id" + " order by categoria.descricao;";

		Cursor cr = db.rawQuery(String.format(query, usuarioId, dataInicial,
				dataFinal), null);

		List<RelatorioDetalheCategoria> tList = new ArrayList<RelatorioDetalheCategoria>();
		Double valor, total = 0d;
		if (cr.moveToFirst()) {
			do {
				Categoria cat = new Categoria();
				cat.setId(cr.getInt(0));
				cat.setDescricao(cr.getString(1));
				cat.setUsuarioId(usuarioId);

				valor = cr.getDouble(2);

				RelatorioDetalheCategoria r = new RelatorioDetalheCategoria(
						cat, valor);

				tList.add(r);

				total += valor;
			} while (cr.moveToNext());
		}

		Categoria cat = new Categoria("Total", 0);
		RelatorioDetalheCategoria rTotal = new RelatorioDetalheCategoria(cat,
				total);
		tList.add(rTotal);
		return tList;
	}

	public static List<RelatorioDetalhe<SubCategoria>> getDadosCategoriaPorPeriodo(
			Context context, Categoria categoria, String dataInicial,
			String dataFinal, int usuarioId, String descricaoParaSubCategoriaIndefinida) {

		SQLiteDatabase db = (new CashDBHelper(context)).getReadableDatabase();

		String query = new String(
			" select" +
			" 	gasto.subcategoriaId," +
			" 	case when subcategoria.descricao is null" +
			" 	then" +
			"   	'%s'" +
			" 	else" +
			" 		subcategoria.descricao" +
			" 	end," +
			" 	sum(gasto.valor)" +
			" from" +
			" 	gasto left join subcategoria on" +
			"		gasto.subcategoriaId = subcategoria.Id " +
			" where" +
			"	gasto.usuarioId = %s and " +
			"	gasto.categoriaId = %s and " +
			"	gasto.data between '%s' and '%s'" +
			" group by" +
			" 	subcategoria.Id" +
			" order by" +
			"	subcategoria.descricao;");

		Cursor cr = db.rawQuery(
				String.format(
						query, 
						descricaoParaSubCategoriaIndefinida, 
						usuarioId, 
						categoria.getId(),
						dataInicial,
						dataFinal), 
				null);

		List<RelatorioDetalhe<SubCategoria>> tList = new ArrayList<RelatorioDetalhe<SubCategoria>>();
		Double valor, total = 0d;
		if (cr.moveToFirst()) {
			do {				
				RelatorioDetalhe<SubCategoria> r = new RelatorioDetalhe<SubCategoria>();
				
				SubCategoria subCat = new SubCategoria();
				subCat.setCategoriaId(categoria.getId());
				subCat.setId(cr.getInt(0));
				subCat.setDescricao(cr.getString(1));
				subCat.setUsuarioId(usuarioId);

				r.setT(subCat);
				valor = cr.getDouble(2);
				r.setTotal(valor);
				
				tList.add(r);

				total += valor;
			} while (cr.moveToNext());
		}
		
		RelatorioDetalhe<SubCategoria> r = new RelatorioDetalhe<SubCategoria>();
		SubCategoria subCat = new SubCategoria();
		subCat.setCategoriaId(categoria.getId());
		subCat.setId(-1);
		subCat.setDescricao("Total");
		subCat.setUsuarioId(usuarioId);
		r.setTotal(total);
		r.setT(subCat);
		
		tList.add(r);
		return tList;
	}

	public static List<RelatorioDetalhe<String>> getDadosSubCategoriaDetalheData(
			Context context, SubCategoria subCategoria, String dataInicial,
			String dataFinal, int usuarioId) {
		
		SQLiteDatabase db = (new CashDBHelper(context)).getReadableDatabase();

		String query = new String(
				"	select" +
				"		g.data as data," +
				"		sum(g.valor) as total" +
				"	from" +
				"		gasto as g" +
				" 	where" +
				"		g.subcategoriaId = %s and" +
				"		g.usuarioId = %s and" +
				"		g.categoriaId = %s and" +
				"		g.data between '%s' and '%s'" +
				" 	group by" +
				"		g.data" +
				"	order by" +
				"		g.data;"
		);

		Cursor cr = db.rawQuery(
				String.format(
						query, 
						subCategoria.getId(), 
						usuarioId,
						subCategoria.getCategoriaId(),
						dataInicial,
						dataFinal), 
				null);

		List<RelatorioDetalhe<String>> tList = new ArrayList<RelatorioDetalhe<String>>();
		Double valor, total = 0d;
		if (cr.moveToFirst()) {
			do {				
				RelatorioDetalhe<String> r = new RelatorioDetalhe<String>();
				r.setT(cr.getString(0));	//data
				valor = cr.getDouble(1);	//valor
				r.setTotal(valor);
				
				tList.add(r);

				total += valor;
			} while (cr.moveToNext());
		}
		
		RelatorioDetalhe<String> r = new RelatorioDetalhe<String>();
		r.setT("Total");
		r.setTotal(total);		
		
		tList.add(r);
		return tList;		
	}
}
