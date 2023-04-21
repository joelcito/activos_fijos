package com.activo.fijos.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Grupo;

public interface IGrupoDao extends CrudRepository<Grupo, String> {

	@Query(value="select  max(TRY_CAST(idgrupo AS BIGINT)) as ultimo from afw_grupo", nativeQuery=true)
	String maxId();
	
}
