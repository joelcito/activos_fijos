package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Departamento;

public interface IDepartamentoService {

	public List<Departamento> findAll();
	
	public Departamento save(Departamento departamento);
	
	public void delete(String iddepartamento);
	
	public Departamento findById(String iddepartamento);
	
}
