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
import com.activo.fijos.models.entity.Caracteristica;
import com.activo.fijos.models.entity.Componente;
import com.activo.fijos.models.services.IActivoService;
import com.activo.fijos.models.services.ICaracteristicaService;
import com.activo.fijos.models.services.IComponenteService;

//@CrossOrigin(origins = {"http://localhost:4200/"})
/*
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
*/
@RestController
@RequestMapping("/api/caracteristica")
public class CaracteristicaRestController {

	@Autowired
	private ICaracteristicaService caracteristicaService;
	@Autowired
	private IActivoService activoService;
	@Autowired
	private IComponenteService componenteService;
	
	
	@GetMapping("/listado")
	public List<Caracteristica> index() {
		return this.caracteristicaService.findAll();
	}
	
	@GetMapping("/{id}")
	public Caracteristica show(@PathVariable String id) {
		return this.caracteristicaService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Caracteristica create(@RequestBody Caracteristica caracteristica) {
		
		String max = caracteristicaService.maxId();
		
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		
		String nuevoid = id+"";
		System.out.println("------------------------");
		//System.out.println(caracteristica.getDescripcion());
		System.out.println(caracteristica.getActivo());
		System.out.println("------------------------");
		
		caracteristica.setIdcaracteristica(nuevoid);
		caracteristica.setFecha(new Date());
		caracteristica.setFechacreacion(new Date());
		return this.caracteristicaService.save(caracteristica);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Caracteristica update(@RequestBody Caracteristica caracteristica, @PathVariable String id) {
		
		Caracteristica caracteristicaActual = this.caracteristicaService.findById(id);
		
		caracteristicaActual.setActivo(caracteristica.getActivo());
		//caracteristicaActual.setSubgrupo(caracteristica.getSubgrupo());
		caracteristicaActual.setDescripcion(caracteristica.getDescripcion());
		caracteristicaActual.setFechamodificacion(new Date());
		
		return this.caracteristicaService.save(caracteristicaActual);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.caracteristicaService.delete(id);
	}
	
	@PostMapping("/agregaJson")
	public void agrega(@RequestBody String datos) {
		
		String[] primero = datos.split("┴");
		String idActivo = primero[primero.length-1];
		Activo activo = activoService.findById(idActivo);
		
		for(int i = 0; i < primero.length-1; i++){
			String[] separado = primero[i].split("┬");
			String componente_idA = separado[0];
			String descripcionA = separado[1];
			
			Componente componente = componenteService.findById(componente_idA);
			
			Caracteristica caracteristicaNew = new Caracteristica();
			
			caracteristicaNew.setIdcaracteristica(sacaId());
			caracteristicaNew.setActivo(activo);
			caracteristicaNew.setDescripcion(descripcionA);
			caracteristicaNew.setFecha(new Date());
			caracteristicaNew.setFechacreacion(new Date());
			caracteristicaNew.setFechamodificacion(new Date());
			caracteristicaNew.setActivo(activo);
			caracteristicaNew.setComponente(componente);
			
			this.caracteristicaService.save(caracteristicaNew);
						
		}
			
	}
	
	@GetMapping("/getCaracteristicasByIdActivo/{idactivo}")
	public List<Caracteristica> getCaracteristicasByIdActivo(@PathVariable String idactivo){
		return this.caracteristicaService.getCaracteristicasByIdActivo(idactivo);
	}
	
	private String sacaId() {
		String max = caracteristicaService.maxId();
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		return id+"";
	}
}
