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

import com.activo.fijos.models.entity.TipoTransaccion;
import com.activo.fijos.models.services.ITipoTransaccionService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
/*
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
		*/
@RestController
@RequestMapping("/api/tipoTransaccion")
public class TipoTransaccionRestController {

	@Autowired
	private ITipoTransaccionService tipoTransaccionService;
	
	@GetMapping("/listado")
	public List<TipoTransaccion>index(){
		return this.tipoTransaccionService.findAll();
	}
	
	@GetMapping("/{id}")
	public TipoTransaccion show(@PathVariable String id) {
		return tipoTransaccionService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoTransaccion create(@RequestBody TipoTransaccion tipoTransaccion) {
		
		Date fecha = new Date();
		
		tipoTransaccion.setFechacreacion(fecha);
		tipoTransaccion.setFecha(fecha);
		
		return tipoTransaccionService.save(tipoTransaccion);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoTransaccion update(@RequestBody TipoTransaccion tipoTransaccion, @PathVariable String id) {
		TipoTransaccion tipoTransaccionActual = tipoTransaccionService.findById(id);
		
		tipoTransaccionActual.setDescripccion(tipoTransaccion.getDescripccion());
		
		return tipoTransaccionService.save(tipoTransaccionActual);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		tipoTransaccionService.delete(id);
	}
	
	
	
	
	
}
