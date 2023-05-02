package com.activo.fijos.models.dao;

import java.util.List;
import java.util.Map;

//import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.activo.fijos.models.entity.Activo;

public interface IActivoDao extends CrudRepository<Activo, String> {
	
	@Query(value="select  max(TRY_CAST(idactivo AS BIGINT)) as ultimo from afw_activo", nativeQuery=true)
	String max();
	
	//@Query(value="select idactivo, fecha, precio, descripcion from afw_activo", nativeQuery=true)
	@Query(value="select idactivo from afw_activo", nativeQuery=true)
	public List<Activo> listaActivos();
		
	@Query(value="SELECT TOP 1000 af.idactivo, af.codigo, af.estado, af.fechacompra, af.descripcion, re.nombre FROM afw_activo af INNER JOIN afw_regional re ON af.regional_id = re.idregional ORDER BY af.fechacreacion DESC", nativeQuery=true)
	//@Query(value="SELECT idactivo, descripcion, precio FROM afw_activo", nativeQuery=true)
	public List<Map<String, Object>> listaActivosPer();
		
	@Query(value="SELECT idactivo, codigo, estado, fechacompra, regional_id, descripcion FROM afw_activo WHERE codigo = :codigo", nativeQuery=true)
	public List<Map<String, Object>> buscaActivo(String codigo);
	
	@Query(value="SELECT idactivo, codigo, estado, fechacompra, regional_id, descripcion FROM afw_activo WHERE descripcion LIKE %:descripcion%", nativeQuery=true)
	public List<Map<String, Object>> buscaActivoDescripcion(@Param("descripcion") String descripcion);
	
	@Query(value="select TOP 1 idactivo from afw_activo where left(idactivo,2) = :idregional order by RIGHT(idactivo,7) desc", nativeQuery = true)
	String maxIdActivo(String idregional);
}
