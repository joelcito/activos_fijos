package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.SubGrupo;


public interface ISubGrupoService {

public List<SubGrupo> findAll();
	
	public SubGrupo save(SubGrupo subgrupo);
	
	public void delete(String idsubgrupo);
	
	public SubGrupo findById(String idsubgrupo);
}
