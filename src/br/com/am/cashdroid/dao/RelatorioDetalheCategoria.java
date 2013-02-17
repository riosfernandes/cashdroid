package br.com.am.cashdroid.dao;

import java.io.Serializable;

import br.com.am.cashdroid.model.Categoria;

public class RelatorioDetalheCategoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Categoria categoria;

	public RelatorioDetalheCategoria(Categoria cat, double total) {
		this.categoria = cat;
		this.total = total; 
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	private Double total;
}
