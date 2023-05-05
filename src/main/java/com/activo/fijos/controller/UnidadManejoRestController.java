package com.activo.fijos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.activo.fijos.models.entity.UnididadManejo;
import com.activo.fijos.models.services.IUnidadManejoService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
@RestController
@RequestMapping("/api/unidadManejo")
public class UnidadManejoRestController {
	
	@Autowired
	private IUnidadManejoService unidadManejoService;
	
	@GetMapping("/listado")
	public List<UnididadManejo> index() {
		return this.unidadManejoService.findAll();
	}
	
	@GetMapping("/{id}")
	public UnididadManejo show(@PathVariable String id) {
		return unidadManejoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public UnididadManejo create(@RequestBody UnididadManejo unidadManejo) {
		
		unidadManejo.setFechacreacion(new Date());
		unidadManejo.setFecha(new Date());
		
		return unidadManejoService.save(unidadManejo);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public UnididadManejo update(@RequestBody UnididadManejo unidadManejo, @PathVariable String id) {
		UnididadManejo unidadManejoActual = unidadManejoService.findById(id);
		
		unidadManejoActual.setDescripcion(unidadManejo.getDescripcion());
		
		return unidadManejoService.save(unidadManejoActual) ;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		unidadManejoService.delete(id);
	}

}
