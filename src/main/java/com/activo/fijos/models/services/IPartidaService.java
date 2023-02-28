package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Partida;

public interface IPartidaService {

	public List<Partida> findAll();
	
	public Partida save(Partida partida);
	
	public void delete(String idpartida);
	
	public Partida findById(String idpartida);
}
