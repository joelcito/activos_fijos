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

import com.activo.fijos.models.entity.UbicacionGeneral;
import com.activo.fijos.models.services.IUbicacionGeneralService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
@RestController
@RequestMapping("/api/ubicacionGeneral")
public class UbicacionGeneralRestController {

	@Autowired
	private IUbicacionGeneralService ubicacionGeneralService;
	
	@GetMapping("/listado")
	public List<UbicacionGeneral>index(){
		return this.ubicacionGeneralService.findAll();
	}
	
	@GetMapping("/{id}")
	public UbicacionGeneral show(@PathVariable String id) {
		return ubicacionGeneralService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public UbicacionGeneral create(@RequestBody UbicacionGeneral ubicacionGeneral) {
		ubicacionGeneral.setFechacreacion(new Date());
		return ubicacionGeneralService.save(ubicacionGeneral);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public UbicacionGeneral update(@RequestBody UbicacionGeneral ubicacionGeneral, @PathVariable String id) {
		UbicacionGeneral ubicacionActual = ubicacionGeneralService.findById(id);
		
		ubicacionActual.setDescripcion(ubicacionGeneral.getDescripcion());
		ubicacionActual.setNombre(ubicacionGeneral.getNombre());
		
		return ubicacionGeneralService.save(ubicacionActual);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		ubicacionGeneralService.delete(id);
	}
}
