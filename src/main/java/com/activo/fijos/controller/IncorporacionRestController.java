package com.activo.fijos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activo.fijos.models.entity.Incorporacion;
import com.activo.fijos.models.services.IIncorporacionService;


//@CrossOrigin(origins = {"http://localhost:4200/"})

/*
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
*/

@RestController
@RequestMapping("/api/incorporacion")
public class IncorporacionRestController {
	
	@Autowired
	private IIncorporacionService incorporacionService;
	
	@GetMapping("/listado")
	public List<Incorporacion> index(){
		return this.incorporacionService.findAll();
	}
}
