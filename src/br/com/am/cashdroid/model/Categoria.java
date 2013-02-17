package br.com.am.cashdroid.model;

import java.io.Serializable;

public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
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
	
	public Categoria(){
		
	}
	
	public Categoria(String descricao, int id){
		this.descricao = descricao;
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descricao;
	}
}
