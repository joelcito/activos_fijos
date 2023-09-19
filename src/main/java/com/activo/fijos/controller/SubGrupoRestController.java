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

import com.activo.fijos.models.entity.SubGrupo;
import com.activo.fijos.models.services.ISubGrupoService;

//@CrossOrigin(origins = {"http://localhost:4200/"})

@CrossOrigin(origins = {"http://10.150.10.13/","http://localhost:4200/"})
		
@RestController
@RequestMapping("/api/subGrupo")
public class SubGrupoRestController {

	@Autowired
	private ISubGrupoService subGrupoService;
	
	@GetMapping("/listado")
	public List<SubGrupo> index() {
		return this.subGrupoService.findAll();
	}
		
	@GetMapping("/{id}")
	public SubGrupo show(@PathVariable String id) {
		return this.subGrupoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public SubGrupo create(@RequestBody SubGrupo subgrupo) {
		
		subgrupo.setIdsubgrupo(sacaId());
		subgrupo.setFecha(new Date());
		subgrupo.setFechacreacion(new Date());
		
		return this.subGrupoService.save(subgrupo);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public SubGrupo updare(@RequestBody SubGrupo subGrupo, @PathVariable String id) {
		
		SubGrupo subGrupoActual = subGrupoService.findById(id);
		
		subGrupoActual.setGrupo(subGrupo.getGrupo());
		subGrupoActual.setDescripcion(subGrupo.getDescripcion());
		subGrupoActual.setFechamodificacion(new Date());

		return subGrupoService.save(subGrupoActual);
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		subGrupoService.delete(id);
	}
	
	@GetMapping("/byGrupo/{idgrupo}")
	public List<SubGrupo> getSubGruposByIdGrupo(@PathVariable String idgrupo){
		return this.subGrupoService.getSubGruposByGrupoId(idgrupo);
	}
	
	private String sacaId() {
		String max = this.subGrupoService.maxId();
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		return id+"";
	}
}
