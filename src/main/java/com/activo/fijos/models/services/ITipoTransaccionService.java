package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.TipoTransaccion;

public interface ITipoTransaccionService {

	public List<TipoTransaccion> findAll();
	
	public TipoTransaccion save(TipoTransaccion tipoTransaccion);
	
	public void delete(String idtipotransaccion);
	
	public TipoTransaccion findById(String idtipotransaccion);
}
