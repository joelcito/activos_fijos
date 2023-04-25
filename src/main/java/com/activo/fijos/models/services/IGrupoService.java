package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Grupo;

public interface IGrupoService {
	
	public List<Grupo> findAll();
	
	public Grupo save(Grupo grupo);
	
	public void delete(String id);
	
	public Grupo findById(String id);
	
	public String maxId();
	
	public void insertNewGrupo(String idgrupo, String descripcion, int nro_items, int vida_util);

}
