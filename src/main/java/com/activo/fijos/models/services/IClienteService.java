package com.activo.fijos.models.services;

import java.util.List;


import com.activo.fijos.models.entity.Cliente;

public interface IClienteService {
	public List<Cliente> findAll();
	
	public Cliente save(Cliente activo);
}
