package com.activo.fijos.models.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.activo.fijos.models.entity.Ufv;

public interface IUfvDao extends CrudRepository<Ufv, String> {

	@Query(value="select  max(TRY_CAST(idufv AS BIGINT)) as ultimo from ufv", nativeQuery=true)
	String max();
	
	@Query(value="select * from ufv where fecha = :fecha", nativeQuery=true)
	Ufv buscarPorFecha(@Param("fecha") LocalDate fecha);
}
