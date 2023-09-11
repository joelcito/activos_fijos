package com.activo.fijos.controller;

import java.util.Comparator;
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

import com.activo.fijos.models.entity.Regimen;
import com.activo.fijos.models.services.IRegimenService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
/*
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
		*/
@RestController
@RequestMapping("/api/regimen")
public class RegimenRestController {
	@Autowired
	private IRegimenService regimenService;
	
	@GetMapping("/listado")
	public List<Regimen> index() {
		List<Regimen> regimenes = this.regimenService.findAll();
		regimenes.sort(Comparator.comparing(Regimen::getDescripcion));
		return regimenes;
	}
	
	@GetMapping("/{id}")
	public Regimen show(@PathVariable String id) {
		return this.regimenService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Regimen create(@RequestBody Regimen regimen) {
		regimen.setFecha(new Date());
		regimen.setFechacreacion(new Date());
		
		return this.regimenService.save(regimen);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Regimen update(@RequestBody Regimen regimen, @PathVariable String id) {
		Regimen regimenActual = this.regimenService.findById(id);
		
		regimenActual.setNombre(regimen.getNombre());
		regimenActual.setDescripcion(regimen.getDescripcion());
		regimenActual.setEstado(regimen.getEstado());
		
		regimenActual.setFechamodificacion(new Date());
		
		return this.regimenService.save(regimenActual);		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.regimenService.delete(id);
	}

}
