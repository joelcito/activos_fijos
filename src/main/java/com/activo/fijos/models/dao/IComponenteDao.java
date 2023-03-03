package com.activo.fijos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Componente;

public interface IComponenteDao extends CrudRepository<Componente, String>{

	@Query(value="select * from componente where subgrupo_id = :idsubgrupo", nativeQuery=true)
    List<Componente> getComponentesByIdSubGrupo(String idsubgrupo);
}
