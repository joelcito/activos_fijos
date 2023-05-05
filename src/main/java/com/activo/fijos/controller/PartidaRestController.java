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

import com.activo.fijos.models.entity.Partida;
import com.activo.fijos.models.services.IPartidaService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
@RestController
@RequestMapping("/api/partida")
public class PartidaRestController {

	@Autowired
	private IPartidaService partidaService;
	
	@GetMapping("/listado")
	public List<Partida>index(){
		return this.partidaService.findAll();
	}
	
	@GetMapping("/{id}")
	public Partida show(@PathVariable String id){
		return partidaService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Partida create (@RequestBody Partida partida) {
		
		partida.setIdpartida(sacaId());
		partida.setFecha(new Date());
		partida.setFechacreacion(new Date());
		
		return partidaService.save(partida);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Partida update(@RequestBody Partida partida, @PathVariable String id) {
		Partida partidaActual = partidaService.findById(id);
		
		partidaActual.setDescripcion(partida.getDescripcion());
		partidaActual.setNombre(partida.getNombre());
		partidaActual.setFechamodificacion(new Date());
		
		return partidaService.save(partidaActual);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		partidaService.delete(id);
	}
	
	private String sacaId() {
		String max = partidaService.maxId();
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		return id+"";
	}
		
}
