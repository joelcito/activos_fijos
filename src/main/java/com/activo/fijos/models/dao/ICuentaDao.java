package com.activo.fijos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Cuenta;

public interface ICuentaDao extends CrudRepository<Cuenta, String>{

}
