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

import com.activo.fijos.models.entity.UbicacionEspecifica;
import com.activo.fijos.models.services.IUbicacionEspecificaService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {"http://10.150.10.13/"})
@RestController
@RequestMapping("/api/ubicacionEspecifica")
public class UbicacionEspecificaRestController {

	@Autowired
	private IUbicacionEspecificaService ubicacionEspecificaService;
	
	@GetMapping("/listado")
	public List<UbicacionEspecifica> index(){
		return this.ubicacionEspecificaService.findAll();
	}
	
	@GetMapping("/{id}")
	public UbicacionEspecifica show(@PathVariable String id) {
		return ubicacionEspecificaService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public UbicacionEspecifica create(@RequestBody UbicacionEspecifica ubicacionEspecifica) {
		ubicacionEspecifica.setFechacreacion(new Date());
		return ubicacionEspecificaService.save(ubicacionEspecifica);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public UbicacionEspecifica update(@RequestBody UbicacionEspecifica ubicacionEspecifica, @PathVariable String id) {
		UbicacionEspecifica ubicacionActual = ubicacionEspecificaService.findById(id);
		
		ubicacionActual.setDescripcion(ubicacionEspecifica.getDescripcion());
		ubicacionActual.setNombre(ubicacionEspecifica.getNombre());
		
		return ubicacionEspecificaService.save(ubicacionActual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		ubicacionEspecificaService.dalete(id);
	}
	
}
