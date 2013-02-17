package br.com.am.cashdroid.model;

import java.io.Serializable;

public class RelatorioDetalhe<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	private Double total;
}
