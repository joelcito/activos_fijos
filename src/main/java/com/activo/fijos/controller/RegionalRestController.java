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

import com.activo.fijos.models.entity.Regional;
import com.activo.fijos.models.services.IRegionalService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {"http://10.150.10.13/activos/"})
@RestController
@RequestMapping("/api/regional")
public class RegionalRestController {

	@Autowired
	private IRegionalService regionalService;
	
	@GetMapping("/listado")
	public List<Regional> index() {
		return this.regionalService.findAll();
	}
	
	@GetMapping("/{id}")
	public Regional show(@PathVariable String id) {
		return this.regionalService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Regional create(@RequestBody Regional regional) {
		regional.setFecha(new Date());
		regional.setFechacreacion(new Date());
		
		return this.regionalService.save(regional);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Regional update(@RequestBody Regional regional, @PathVariable String id) {
		Regional regionalActual = this.regionalService.findById(id);
		
		regionalActual.setDepartamento(regional.getDepartamento());
		regionalActual.setNombre(regional.getNombre());
		regionalActual.setDescripcion(regional.getDescripcion());
		regionalActual.setEstado(regional.getEstado());
		
		regionalActual.setFechamodificacion(new Date());
		
		return this.regionalService.save(regionalActual);
		
		
	}
	
	@DeleteMapping("/{id}")
	public  void delete(@PathVariable String id){
		this.regionalService.delete(id);
	}
}
