package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Incorporacion;

public interface IIncorporacionService {
	
	public List<Incorporacion> findAll();

	public Incorporacion save(Incorporacion incorporacion);
	
	public void delete(String id);
	
	public Incorporacion findById(String id);
	
}
