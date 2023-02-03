package com.activo.fijos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Activo;

public interface IActivoDao extends CrudRepository<Activo, Long> {

}
