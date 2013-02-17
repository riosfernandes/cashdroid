package br.com.am.cashdroid;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import br.com.am.cashdroid.dao.RelatorioDao;
import br.com.am.cashdroid.dao.RelatorioDetalheCategoria;
import br.com.am.cashdroid.messages.Mensagem;
import br.com.am.cashdroid.messages.TipoMensagem;
import br.com.am.cashdroid.model.Categoria;
import br.com.am.cashdroid.model.RelatorioDetalhe;
import br.com.am.cashdroid.model.Sessao;
import br.com.am.cashdroid.model.SubCategoria;

public class ControleRelatorio {
	public static List<RelatorioDetalheCategoria> getDadosPorPeriodo(Context context, String parametro) throws Exception {
		try{
			String[] datas = obterPeriodoPorParametro(parametro);
			
			return RelatorioDao.getDadosPorPeriodo(context, datas[0], datas[1], Sessao.getUsuarioLogado().getId());
		}
		catch(Exception ex){
			Mensagem msg = new Mensagem(context, TipoMensagem.ERRO, R.string.erroInesperado);
			msg.show();
			throw ex;
		}		
	}
	
	public static String[] obterPeriodoPorParametro(String parametro){
		return new String[] { getDataInicial(parametro), (String)DateFormat.format("yyyy-MM-dd", new Date()) };
	}

	private static String getDataInicial(String p) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if (p.equals("Últimos 60 dias")) {
			c.add(Calendar.DATE, -60);
		} else if (p.equals("Últimos 45 dias")) {
			c.add(Calendar.DATE, -45);
		} else if (p.equals("Últimos 30 dias")) {
			c.add(Calendar.DATE, -30);
		} else if (p.equals("Últimos 15 dias")) {
			c.add(Calendar.DATE, -15);			
		} else  {
			c.add(Calendar.DATE, -7);
		}
		
		return new String((String) DateFormat.format("yyyy-MM-dd", c.getTime()));
	}

	public static List<RelatorioDetalhe<SubCategoria>> getDadosCategoriaPorPeriodo(
			Context context, Categoria categoria, String dataInicial, String dataFinal, String descricaoParaSubCategoriaIdefinida) throws Exception {
		try{
			return RelatorioDao.getDadosCategoriaPorPeriodo(
					context,
					categoria,
					dataInicial,
					dataFinal,
					Sessao.getUsuarioLogado().getId(),
					descricaoParaSubCategoriaIdefinida);
		}
		catch(Exception ex){
			Mensagem msg = new Mensagem(context, TipoMensagem.ERRO, R.string.erroInesperado);
			msg.show();
			throw ex;
		}	
	}

	public static List<RelatorioDetalhe<String>> getDadosSubCategoriaDetalheData(
			Context context, SubCategoria subCategoria, String dataInicial, String dataFinal) throws Exception {
		try{
			return RelatorioDao.getDadosSubCategoriaDetalheData(
					context,
					subCategoria,
					dataInicial,
					dataFinal,
					Sessao.getUsuarioLogado().getId());
		}
		catch(Exception ex){
			Mensagem msg = new Mensagem(context, TipoMensagem.ERRO, R.string.erroInesperado);
			msg.show();
			throw ex;
		}	
	}
}
