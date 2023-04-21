package com.activo.fijos.models.dao;

//import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Activo;

public interface IActivoDao extends CrudRepository<Activo, String> {
	
	@Query(value="select  max(TRY_CAST(idactivo AS BIGINT)) as ultimo from afw_activo", nativeQuery=true)
	String max();
}
