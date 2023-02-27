package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.UnididadManejo;

public interface IUnidadManejoService {

	public List<UnididadManejo> findAll();
	
	public UnididadManejo save(UnididadManejo unidadManejo);
	
	public void delete(String idunidadmanejo);
	
	public UnididadManejo findById(String idunidadmanejo);
}
