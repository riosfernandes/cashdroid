package br.com.am.cashdroid.model;

import java.io.Serializable;

public class Usuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8122579834287728302L;
	
	private String login;
	private String senha;
	private int isLembrar;
	private int id;
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setIsLembrar(Integer isLembrar) {
		this.isLembrar = isLembrar;
	}

	public Usuario() {
		this.setIsLembrar(0);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setIsLembrar(int isLembrar) {
		this.isLembrar = isLembrar;
	}

	public int getIsLembrar() {
		return isLembrar;
	}
}
