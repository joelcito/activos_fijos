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

import com.activo.fijos.models.entity.Activo;
import com.activo.fijos.models.services.IActivoService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/activo")
public class ActivoRestController {
	
	@Autowired
	private IActivoService activoService;
	
	@GetMapping("/listado")
	public List<Activo> index() {
		return this.activoService.findAll();
	}
	
	@GetMapping("/{id}")
	public Activo show(@PathVariable Long id) {
		return activoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Activo create(@RequestBody Activo activo) {
		activo.setCreateAt(new Date());
		//System.out.println("HABER => "+activo.getId());
		return activoService.save(activo);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Activo update(@RequestBody Activo activo,@PathVariable Long id) {
		
		Activo activoActual = activoService.findById(id); 
		
		activoActual.setNombre(activo.getNombre());
		activoActual.setDescripcion(activo.getDescripcion());
		activoActual.setCodigo_item(activo.getCodigo_item());
		activoActual.setDepartameto(activo.getDepartameto());
		activoActual.setPorcentaje_depreciacion(activo.getPorcentaje_depreciacion());
		activoActual.setRegional(activo.getRegional());
		activoActual.setUfv_inicio(activo.getUfv_inicio());
		activoActual.setVida_util(activo.getVida_util());
		activoActual.setEstado(activo.getEstado());
		
		//forenana
		activoActual.setGrupo(activo.getGrupo());
		
		return activoService.save(activoActual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		activoService.delete(id);
	}
	
}
