package com.activo.fijos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.activo.fijos.models.entity.Grupo;
import com.activo.fijos.models.services.IGrupoService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/grupo")
public class GrupoRestController {	
	
	@Autowired
	private IGrupoService grupoService;
	
	@GetMapping("/listado")
	public List<Grupo> index(){
		return this.grupoService.findAll();
	}
	
	@GetMapping("/{id}")
	public Grupo show(@PathVariable String id) {
		return grupoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Grupo create(@RequestBody Grupo grupo) {
		grupo.setFecha(new Date());
		grupo.setFechacreacion(new Date());
		return grupoService.save(grupo);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Grupo update(@RequestBody Grupo grupo, @PathVariable String id) {
		Grupo grupoActual = grupoService.findById(id);
		
		//grupoActual.setSubgrupo(grupo.getSubgrupo());
		grupoActual.setCuenta(grupo.getCuenta());
		grupoActual.setDescripcion(grupo.getDescripcion());
		grupoActual.setVidaUtil(grupo.getVidaUtil());
		grupoActual.setEstado(grupo.getEstado());
		grupoActual.setNroItems(grupo.getNroItems());
		
		grupoActual.setFechamodificacion(new Date());
		
		return this.grupoService.save(grupoActual);
	}
}
