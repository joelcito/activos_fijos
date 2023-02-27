package com.activo.fijos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.TipoTransaccion;

public interface ITipoTransaccionDao extends CrudRepository<TipoTransaccion, String> {

}
