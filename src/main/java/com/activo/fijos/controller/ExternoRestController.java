package com.activo.fijos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activo.fijos.models.entity.Activo;
import com.activo.fijos.models.entity.Grupo;
import com.activo.fijos.models.entity.SubGrupo;
import com.activo.fijos.models.services.IActivoService;
import com.activo.fijos.models.services.IGrupoService;
import com.activo.fijos.models.services.ISubGrupoService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/externo")
public class ExternoRestController {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IActivoService activoService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ISubGrupoService subGrupoService;
	
	public ExternoRestController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@PostMapping("/login")
	//public List<Map<String, Object>> prueba() {
	public void prueba(@RequestBody Map<String, String> user) {
		System.out.println("Email: " + user.get("email"));
        System.out.println("Password: " + user.get("password"));
		//System.out.println("como es", email);
		//List<Map<String, Object>> usuarios = jdbcTemplate.queryForList("SELECT * FROM rh_usuario");
		
		//return usuarios;
	}
	
	
	@GetMapping("/migrationItemActivo")
	public void migracionActivo() {
		
		List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT * FROM af_item");
		
		System.out.println(activos.size());
		int contador = 0;		
		
		for (Map<String, Object> act : activos) {
			System.out.print("<= | "+contador+" | => "+"ID: " + act.get("cod")+" | Cog 1: " + act.get("cod1")+" | Descr: " + act.get("des")+" | Grupo: " + act.get("codgrupo")+"\n");
			
			Activo activoNew = new Activo();
			
			
			String idGrupo = act.get("codgrupo").toString();
			if(!idGrupo.equals("")) {				
				String sql = "SELECT * FROM afw_grupo WHERE codanterior = ?";
				List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, idGrupo);
				if(rows.size() > 0) {
					Grupo grupoNew = new Grupo();
					grupoNew = this.grupoService.findById((rows.get(0).get("idgrupo")).toString());
					activoNew.setGrupo(grupoNew);
				}
			}
			
			String idSubGrupo = act.get("codsubgrp").toString();
			System.out.println(idSubGrupo);
			if(!idSubGrupo.equals("")) {
				String sql = "SELECT * FROM afw_subgrupo WHERE codanterior = ?";
				List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, idSubGrupo);
				System.out.println(rows.size()+"|"+idSubGrupo);
				if(rows.size() > 0) {
					SubGrupo subgrupoNew = this.subGrupoService.findById((rows.get(0).get("idsubgrupo")).toString());
					activoNew.setSubgrupo(subgrupoNew);
				}
			}
			
			
			activoNew.setIdactivo(sacaIdGenerico(1));
			activoNew.setVida_util(Float.parseFloat(act.get("vidautil").toString()));
			activoNew.setCodigo(act.get("cod").toString());
			activoNew.setDescripcion(act.get("des1").toString());			
					
			this.activoService.save(activoNew);
			contador++;
			
			if(contador > 100) {
				break;	
			}
			
		
		}
		
		
	}
		
	@GetMapping("/migrationItemGrupo")
	public void migracionGrupo() {
		
		List<Map<String, Object>> grupos = jdbcTemplate.queryForList("SELECT * FROM af_grupo");
		
		System.out.println(grupos.size());
		int contador = 0;		
		
		for (Map<String, Object> grup : grupos) {
			
			System.out.print("<= | "+contador+" | => "+"ID: " + grup.get("cod")+" | Cog 1: " + grup.get("des")+" | Descr: " + grup.get("num1")+"\n");
			
			String sql = "INSERT INTO afw_grupo (idgrupo, descripcion, nro_items , vida_util) VALUES(?,?,?,?)";
		    jdbcTemplate.update(sql, grup.get("cod").toString(), grup.get("des").toString(), 0 , Integer.parseInt(grup.get("num1").toString()));
						
			/*
			 * 
			 					
			this.grupoService.insertNewGrupo(grup.get("cod").toString(), grup.get("des").toString(), 0, Integer.parseInt(grup.get("num1").toString()));
			* */
					
			/*
			
			Grupo grpuNew = new Grupo();
			
			grpuNew.setIdgrupo(grup.get("cod").toString().trim());
			grpuNew.setCodanterior(grup.get("cod").toString());
			grpuNew.setDescripcion(grup.get("des").toString());
			grpuNew.setVidaUtil(Integer.parseInt(grup.get("num1").toString()));
			
			this.grupoService.save(grpuNew);
			
			*/
			
			
			contador++;
			
			if(contador > 500) {
				break;	
			}
			
		
		}
		
		
	}
	
		
	@GetMapping("/migrationSubGrupo")
	public void migrationSubGrupos() {
		
		List<Map<String, Object>> subgrupos = jdbcTemplate.queryForList("SELECT * FROM af_subgrupo");
		
		//System.out.println(grupos.size());
		int contador = 0;		
		
		
		for (Map<String, Object> subGrupo : subgrupos) {
			
			System.out.print("<= | "+contador+" | => "+"ID: " + subGrupo.get("cod")+" | Cod Grupo: " + subGrupo.get("codgrupo")+" | Descr: " + subGrupo.get("des")+"\n");
			
			String sql = "INSERT INTO afw_subgrupo (idsubgrupo, cod, descripcion, grupo_id) VALUES(?,?,?,?)";
		    jdbcTemplate.update(sql, (subGrupo.get("codgrupo").toString().trim()+subGrupo.get("cod").toString().trim()),subGrupo.get("cod").toString().trim(), subGrupo.get("des").toString(), subGrupo.get("codgrupo").toString().trim());
			
			/*
			 * 
			 
						
			SubGrupo subGrupoNew = new SubGrupo();
			
			subGrupoNew.setIdsubgrupo(subGrupo.get("cod").toString().trim());
			subGrupoNew.setCodanterior(subGrupo.get("cod").toString());
			subGrupoNew.setDescripcion(subGrupo.get("des").toString());	
						
			String sql = "SELECT * FROM afw_grupo WHERE codanterior = ?";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, subGrupo.get("codgrupo"));
			
			//System.out.println(rows.size()+"|"+subGrupo.get("cod"));
			
			if(rows.size() > 0) {				
				Grupo grupoNew = this.grupoService.findById(rows.get(0).get("idgrupo").toString());
				subGrupoNew.setGrupo(grupoNew);
			}
			
			this.subGrupoService.save(subGrupoNew);
					
					
			* */
			contador++;
			
			/*
			if(contador > 100) {
				break;	
			}
			*/
			
			
		
		}
		
	}
	
	
	private String sacaIdGenerico(int tipo /*tipo de que tabla es*/) {
		
		String max = null;
		
		if(tipo == 1) {//activos
			max = activoService.max();
		}else if(tipo == 2) {//grupos
			max = grupoService.maxId();
		}else if(tipo == 3) {//sub grupos
			max = subGrupoService.maxId();
		}
				
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		return id+"";
	}
	
}
