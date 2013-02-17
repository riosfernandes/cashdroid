package br.com.am.cashdroid.model;

import java.io.Serializable;

public class SubCategoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int categoriaId;
	private int id;
	private int usuarioId;
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public int getCategoriaId() {
		return categoriaId;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
