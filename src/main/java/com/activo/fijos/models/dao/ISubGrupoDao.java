package com.activo.fijos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.SubGrupo;

public interface ISubGrupoDao extends CrudRepository<SubGrupo, String> {
	
	@Query(value="select * from afw_subgrupo where grupo_id = :idsubgrupo", nativeQuery=true)
    List<SubGrupo> getSubGruposByGrupoId(String idsubgrupo);
	
	@Query(value="select  max(TRY_CAST(idsubgrupo AS BIGINT)) as ultimo from afw_subgrupo", nativeQuery=true)
	String maxId();

	
}
