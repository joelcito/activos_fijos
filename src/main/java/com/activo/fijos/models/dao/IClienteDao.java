package com.activo.fijos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.activo.fijos.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
}
