package com.activo.fijos.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/externo")
public class ExternoRestController {
	
	private final JdbcTemplate jdbcTemplate;
	
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
}
