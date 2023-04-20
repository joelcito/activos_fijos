package com.activo.fijos.models.services;

import java.time.LocalDate;
import java.util.List;

import com.activo.fijos.models.entity.Ufv;


public interface IUfvService {
	public List<Ufv> findAll();
	
	public Ufv save(Ufv activo);
	
	public void delete(String id);
	
	public Ufv findById(String Id);
	
	//public List<Activo> getUltimoRegistroActivo();
	public String  max();
	
	public Ufv buscarPorFecha(LocalDate fecha);
}
