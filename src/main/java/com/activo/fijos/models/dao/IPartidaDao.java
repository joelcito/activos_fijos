package com.activo.fijos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Partida;

public interface IPartidaDao extends CrudRepository<Partida, String> {

}
