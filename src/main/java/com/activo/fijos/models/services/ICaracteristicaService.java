package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Caracteristica;

public interface ICaracteristicaService {
	public List<Caracteristica> findAll();
	
	public Caracteristica save(Caracteristica caracteristica);
	
	public void delete(String idcaracteristica);
	
	public Caracteristica findById(String idcaracteristica);
}
