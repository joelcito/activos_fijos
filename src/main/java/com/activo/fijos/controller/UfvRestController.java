package com.activo.fijos.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activo.fijos.models.entity.Ufv;
import com.activo.fijos.models.services.IUfvService;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/ufv")
public class UfvRestController {
	@Autowired
	private IUfvService ufvService;
	
	
	@GetMapping("/{fecha}")
	public Ufv buscarPorFecha(@PathVariable String fecha) {
		
		LocalDate fechaConvert = LocalDate.parse(fecha);
		
		return (Ufv)this.ufvService.buscarPorFecha(fechaConvert);
	}
	
}
