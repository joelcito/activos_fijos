package com.activo.fijos.models.dao;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Activo;

public interface IActivoDao extends CrudRepository<Activo, String> {
	
	//@Query(value="select  max(fechacreacion) as ultimo from activo", nativeQuery=true)
	@Query(value="select  max(idactivo) as ultimo from activo", nativeQuery=true)
	String max();
	//@Query(value="select * from activo", nativeQuery=true)
	//List<Activo> getUltimoRegistroActivo();
}
