package com.activo.fijos.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Cuenta;

public interface ICuentaDao extends CrudRepository<Cuenta, String>{
	@Query(value="select  max(TRY_CAST(idcuenta AS BIGINT)) as ultimo from cuenta", nativeQuery=true)
	String maxId();
}
