package com.activo.fijos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activo.fijos.models.entity.Cliente;
import com.activo.fijos.models.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listado")
	public List<Cliente> index() {
		return this.clienteService.findAll();
	}
	
	@PostMapping("/nuevo")
	public Cliente create(@RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}
}
