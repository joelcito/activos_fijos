package com.activo.fijos.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Partida;

public interface IPartidaDao extends CrudRepository<Partida, String> {
	@Query(value="select  max(TRY_CAST(idpartida AS BIGINT)) as ultimo from afw_partida", nativeQuery=true)
	String maxId();
}
