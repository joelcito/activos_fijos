package com.activo.fijos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Caracteristica;

public interface ICaracteristicaDao extends CrudRepository<Caracteristica, String>{
	
	@Query(value="select  max(TRY_CAST(idcaracteristica AS BIGINT)) as ultimo from afw_caracteristica", nativeQuery=true)
	String maxId();
	
	@Query(value="select * from afw_caracteristica where activo_id = :idactivo", nativeQuery=true)
    List<Caracteristica> getCaracteristicasByIdActivo(String idactivo);

}
