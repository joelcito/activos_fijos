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

import com.activo.fijos.models.entity.Caracteristica;
import com.activo.fijos.models.services.ICaracteristicaService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/caracteristica")
public class CaracteristicaRestController {

	@Autowired
	private ICaracteristicaService caracteristicaService;
	
	@GetMapping("/listado")
	public List<Caracteristica> index() {
		return this.caracteristicaService.findAll();
	}
	
	@GetMapping("/{id}")
	public Caracteristica show(@PathVariable String id) {
		return this.caracteristicaService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Caracteristica create(@RequestBody Caracteristica caracteristica) {
		caracteristica.setFecha(new Date());
		caracteristica.setFechacreacion(new Date());
		return this.caracteristicaService.save(caracteristica);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Caracteristica update(@RequestBody Caracteristica caracteristica, @PathVariable String id) {
		
		Caracteristica caracteristicaActual = this.caracteristicaService.findById(id);
		
		caracteristicaActual.setActivo(caracteristica.getActivo());
		caracteristicaActual.setSubgrupo(caracteristica.getSubgrupo());
		caracteristicaActual.setDescripcion(caracteristica.getDescripcion());
		caracteristicaActual.setFechamodificacion(new Date());
		
		return this.caracteristicaService.save(caracteristicaActual);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.caracteristicaService.delete(id);
	}
}
