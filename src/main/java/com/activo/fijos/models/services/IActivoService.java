package com.activo.fijos.models.services;

import java.util.List;
import java.util.Map;

import com.activo.fijos.models.entity.Activo;

public interface IActivoService {
	
	public List<Activo> findAll();
	
	public Activo save(Activo activo);
	
	public void delete(String id);
	
	public Activo findById(String Id);
	
	//public List<Activo> getUltimoRegistroActivo();
	public String  max();
	
	public List<Activo> listaActivos();
	
	public List<Map<String, Object>> listaActivosPer();
	
	public List<Map<String, Object>> buscaActivo(String codigo);
	
	public List<Map<String, Object>> buscaActivoDescripcion(String descripcion);
	
	public String maxIdActivo(String idregional);
	
}
