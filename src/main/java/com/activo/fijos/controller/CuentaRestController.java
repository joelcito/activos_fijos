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

import com.activo.fijos.models.entity.Cuenta;
import com.activo.fijos.models.services.ICuentaService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/cuenta")
public class CuentaRestController {

	@Autowired
	private ICuentaService cuentaService;
	
	@GetMapping("/listado")
	public List<Cuenta> index() {
		return this.cuentaService.findAll();
	}
	
	@GetMapping("/{id}")
	public Cuenta show(@PathVariable String id) {
		return this.cuentaService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Cuenta create(@RequestBody Cuenta cuenta) {
		cuenta.setFechacreacion(new Date());
		cuenta.setFecha(new Date());
		return this.cuentaService.save(cuenta);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cuenta update(@RequestBody Cuenta cuenta, @PathVariable String id) {
		Cuenta cuentaActual = cuentaService.findById(id);
		
		cuentaActual.setDescripcion(cuenta.getDescripcion());
		cuentaActual.setNombre(cuenta.getNombre());
		cuentaActual.setNroCuenta(cuenta.getNroCuenta());
		cuentaActual.setFechamodificacion(new Date());
		
		return cuentaService.save(cuentaActual);
		
	}
	
	@DeleteMapping("{id}")
	public  void delete(@PathVariable String id) {
		cuentaService.delete(id);
	}
}
