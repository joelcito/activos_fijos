package com.activo.fijos.models.dao;

import java.util.List;
import java.util.Map;

//import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Activo;

public interface IActivoDao extends CrudRepository<Activo, String> {
	
	@Query(value="select  max(TRY_CAST(idactivo AS BIGINT)) as ultimo from afw_activo", nativeQuery=true)
	String max();
	
	//@Query(value="select idactivo, fecha, precio, descripcion from afw_activo", nativeQuery=true)
	@Query(value="select idactivo from afw_activo", nativeQuery=true)
	public List<Activo> listaActivos();
	
	
	//@Query(value="SELECT TOP 100 idactivo, descripcion, precio FROM afw_activo", nativeQuery=true)
	@Query(value="SELECT idactivo, descripcion, precio FROM afw_activo", nativeQuery=true)
	public List<Map<String, Object>> listaActivosPer();
	
	
	@Query(value="SELECT TOP 100 idactivo, descripcion, precio FROM afw_activo", nativeQuery=true)
	public List<Map<String, Object>> buscaActivo();
}
