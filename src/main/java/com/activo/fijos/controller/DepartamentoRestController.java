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

import com.activo.fijos.models.entity.Departamento;
import com.activo.fijos.models.services.IDepartamentoService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
@RestController
@RequestMapping("/api/departamento")
public class DepartamentoRestController {

	@Autowired
	private IDepartamentoService departamentoService;
	
	@GetMapping("/listado")
	public List<Departamento>index(){
		return this.departamentoService.findAll();
	}
	
	@GetMapping("/{id}")
	public Departamento show(@PathVariable String id) {
		return departamentoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Departamento create(@RequestBody Departamento departamento) {
		departamento.setFecha(new Date());
		departamento.setFechacreacion(new Date());
		
		return departamentoService.save(departamento);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Departamento update(@RequestBody Departamento departamento, @PathVariable String iddepartamento) {
		Departamento departamentoActual = departamentoService.findById(iddepartamento);
		
		departamentoActual.setNombre(departamento.getNombre());
		departamentoActual.setDescripcion(departamento.getDescripcion());
		
		return departamentoService.save(departamentoActual);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		departamentoService.delete(id);
	}
}
