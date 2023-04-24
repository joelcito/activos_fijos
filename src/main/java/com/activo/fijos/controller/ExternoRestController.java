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
import com.activo.fijos.models.services.IActivoService;
import com.activo.fijos.models.services.IGrupoService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/externo")
public class ExternoRestController {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IActivoService activoService;
	
	@Autowired
	private IGrupoService grupoService;
	
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
											
				System.out.println(rows+"|"+idGrupo+"|"+rows.size()+"|"+rows.get(0));
				System.out.println("---------------------------");
				
				
			}else {
				System.out.println("************************");
			}
			
			activoNew.setIdactivo(sacaIdActivo());
			activoNew.setCodigo(act.get("cod").toString());
			activoNew.setDescripcion(act.get("des").toString());			
					
			this.activoService.save(activoNew);
			contador++;
			
			if(contador > 100) {
				break;	
			}
			
		
		}
		
		
	}
	
	
	private String sacaIdActivo() {
		String max = activoService.max();
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		return id+"";
	}
	
	@GetMapping("/migrationItemGrupo")
	public void migracionGrupo() {
		
		List<Map<String, Object>> grupos = jdbcTemplate.queryForList("SELECT * FROM af_grupo");
		
		System.out.println(grupos.size());
		int contador = 0;		
		
		for (Map<String, Object> grup : grupos) {
			
			System.out.print("<= | "+contador+" | => "+"ID: " + grup.get("cod")+" | Cog 1: " + grup.get("des")+" | Descr: " + grup.get("num1")+"\n");
			
						Grupo grpuNew = new Grupo();
			grpuNew.setIdgrupo(sacaIdGrupo());
			
			grpuNew.setCodanterior(grup.get("cod").toString());
			grpuNew.setDescripcion(grup.get("des").toString());
			grpuNew.setVidaUtil(Integer.parseInt(grup.get("num1").toString()));
			
			this.grupoService.save(grpuNew);
			
			
			contador++;
			
			if(contador > 500) {
				break;	
			}
			
		
		}
		
		
	}
	
	private String sacaIdGrupo() {
		String max = grupoService.maxId();
		int id = 0;
		
		if(max==null)
			id = 1;
		else
			id = Integer.parseInt(max) + 1;
		
		return id+"";
	}
	
}
