package br.com.am.cashdroid.model;

import java.io.Serializable;

public class Sessao implements Serializable{

	private static Usuario usuarioLogado;

	public static void setUsuarioLogado(Usuario usuarioLogado) {
		Sessao.usuarioLogado = usuarioLogado;
	}

	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
}
