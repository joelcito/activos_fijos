package com.activo.fijos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Regimen;

public interface IRegimenDao extends CrudRepository<Regimen, String> {

}
