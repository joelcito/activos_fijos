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

import com.activo.fijos.models.entity.Componente;
import com.activo.fijos.models.services.IComponenteService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/componente")
public class ComponenteController {
	
	@Autowired
	private IComponenteService componenteService;
	
	@GetMapping("/listado")
	public List<Componente> index() {
		return this.componenteService.findAll();
	}
	
	@GetMapping("/{id}")
	public Componente show(@PathVariable String id) {
		return this.componenteService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Componente create(@RequestBody Componente componente) {
		componente.setFecha(new Date());
		componente.setFechacreacion(new Date());
		return this.componenteService.save(componente);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Componente update(@RequestBody Componente componente,@PathVariable String id) {
		
		Componente componenteActual = componenteService.findById(id);
		
		componenteActual.setNombre(componente.getNombre());
		//activoActual.setGrupo(activo.getGrupo());
		componenteActual.setSubgrupo(componente.getSubgrupo());
		componenteActual.setEstado(componente.getEstado());
		componenteActual.setFechamodificacion(new Date());
				
		return this.componenteService.save(componenteActual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		this.componenteService.delete(id);
	}
	
	@GetMapping("/getComponeteByIdSubGrupo/{idsubgrupo}")
	public List<Componente> getComponeteByIdSubGrupo(@PathVariable String idsubgrupo){
		return this.componenteService.getComponentesByIdSubGrupo(idsubgrupo);
	} 

}
