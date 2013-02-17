package br.com.am.cashdroid.dao;

import java.util.List;

public interface Dao<T> {
	List<T> findAll() throws Exception;
	
	void salvar(T object) throws Exception;
	
	T findById(int id) throws Exception;
	
	void excluir(T object) throws Exception;
}
