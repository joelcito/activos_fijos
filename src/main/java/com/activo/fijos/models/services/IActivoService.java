package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Activo;

public interface IActivoService {
	public List<Activo> findAll();
	
	public Activo save(Activo activo);
	
	public void delete(String id);
	
	public Activo findById(String Id);
}
