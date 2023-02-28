package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Regimen;


public interface IRegimenService  {
	
	public List<Regimen> findAll();
	
	public Regimen save(Regimen regimen);
	
	public void delete(String idregimen);
	
	public Regimen findById(String idregimen);

}
