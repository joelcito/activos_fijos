package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.UbicacionGeneral;

public interface IUbicacionGeneralService {

	public List<UbicacionGeneral> findAll();
	
	public UbicacionGeneral save(UbicacionGeneral ubicaciongeneral);
	
	public void delete(String idubicaciongeneral);
	
	public UbicacionGeneral findById(String ubicaciongeneral);
}
