package com.activo.fijos.models.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.activo.fijos.models.entity.Grupo;

public interface IGrupoDao extends CrudRepository<Grupo, String> {

	@Query(value="select  max(TRY_CAST(idgrupo AS BIGINT)) as ultimo from afw_grupo", nativeQuery=true)
	String maxId();
	
	
	@Modifying
	@Query(value = "INSERT INTO afw_grupo (idgrupo, descripcion, nro_items , vida_util) VALUES (:idgrupo, :descripcion, :nro_items,:vida_util)", nativeQuery = true)
	public void insertNewGrupo(@Param("idgrupo") String idgrupo, @Param("descripcion") String descripcion, @Param("nro_items") int nro_items, @Param("vida_util") int vida_util);

	/*
	@Query(value = "insert into afw_grupo (idgrupo, descripcion, nro_items , vida_util) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	public void insertNewGrupo(String idgrupo, String descripcion,int nro_items , int vida_util);
	*/
}
