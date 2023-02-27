package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.UbicacionEspecifica;

public interface IUbicacionEspecificaService {
	
	public List<UbicacionEspecifica> findAll();
	
	public UbicacionEspecifica save(UbicacionEspecifica ubicacionEspecifica);
	
	public void dalete(String idubicacionespecifica);
	
	public UbicacionEspecifica findById(String idubicacionespecifica);
}
