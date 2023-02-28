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
	public Activo show(@PathVariable String id) {
		return activoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Activo create(@RequestBody Activo activo) {
		activo.setFecha(new Date());
		activo.setFechacreacion(new Date());
		return activoService.save(activo);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Activo update(@RequestBody Activo activo,@PathVariable String id) {
		
		Activo activoActual = activoService.findById(id);
		
		activoActual.setIncorporacion(activo.getIncorporacion());
		activoActual.setGrupo(activo.getGrupo());
		activoActual.setRegimen(activo.getRegimen());
		activoActual.setRegional(activo.getRegional());
		activoActual.setUnidadmanejo(activo.getUnidadmanejo());
		activoActual.setTipotransaccion(activo.getTipotransaccion());
		activoActual.setUbicacionespecifica(activo.getUbicacionespecifica());
		activoActual.setCodigo(activo.getCodigo());
		activoActual.setPlaca(activo.getPlaca());
		activoActual.setEficiencia(activo.getEficiencia());
		activoActual.setCodigoalterno(activo.getCodigoalterno());
		activoActual.setFormainicial(activo.getFormainicial());
		activoActual.setDescripcion(activo.getDescripcion());
		activoActual.setEstadoregistro(activo.getEstadoregistro());
		activoActual.setEstado(activo.getEstado());
		activoActual.setFechacompra(activo.getFechacompra());
		activoActual.setPorcentaje_depreciacion(activo.getPorcentaje_depreciacion());
		activoActual.setUfv(activo.getUfv());
		activoActual.setUfvcompra(activo.getUfvcompra());
		activoActual.setVida_util(activo.getVida_util());
		activoActual.setFechamodificacion(new Date());
				
		return activoService.save(activoActual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		activoService.delete(id);
	}
	
}
