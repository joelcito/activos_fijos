package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Cuenta;

public interface ICuentaService {

	public List<Cuenta> findAll();
	
	public Cuenta save(Cuenta cuenta);
	
	public void delete(String idcuenta);
	
	public Cuenta findById(String idcuenta);
}
