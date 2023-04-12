package com.activo.fijos.models.services;

import java.util.List;


import com.activo.fijos.models.entity.Componente;


public interface IComponenteService {
	
	public List<Componente> findAll();
	
	public Componente save(Componente componente);
	
	public void delete(String idcomponente);
	
	public Componente findById(String idcomponente);
	
	public List<Componente> getComponentesByIdSubGrupo(String idsubgrupo);
	
	public String  maxId();
	
}
