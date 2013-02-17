package br.com.am.cashdroid.model;

import java.io.Serializable;

public class Gasto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int usuarioId;
	private String data;
	private int categoriaId;
	private int subCategoriaId;
	private Double valor;
	private int id;
	
	public int getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}
	public int getSubCategoriaId() {
		return subCategoriaId;
	}
	public void setSubCategoriaId(int subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
