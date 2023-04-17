package com.activo.fijos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

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
		
		
		//List<Activo> activoUltimo = activoService.getUltimoRegistroActivo();
		String max = activoService.max();
		
		System.out.println(max);
		int id = 0;
		if(max==null) {
			id = 1;
		}else {
			id = Integer.parseInt(max) + 1;
		}
		System.out.println(id);
		/*
		if(ultimoActivo == null) {
			System.out.print("SI");
		}else {
			System.out.print("NO");
		}
		
		System.out.print(ultimoActivo.getIdactivo());
		
		*/
		String nuevoid = id+"";
		activo.setIdactivo(nuevoid);
		activo.setFecha(new Date());
		activo.setFechacreacion(new Date());
		return activoService.save(activo);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Activo update(@RequestBody Activo activo,@PathVariable String id) {
		
		Activo activoActual = activoService.findById(id);
		
		activoActual.setIncorporacion(activo.getIncorporacion());
		//activoActual.setGrupo(activo.getGrupo());
		activoActual.setSubgrupo(activo.getSubgrupo());
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
	
	@GetMapping("/calculaDepre/{id}/{fecha}")
	public Map<String, Object> calculaDepre(@PathVariable String id, @PathVariable String fecha){
				
		Map<String, Object> objeto = new HashMap();
		
		Activo activo = activoService.findById(id);
		System.out.println(activo.getFechacompra());
		//Float ufvIni 		= (float) 2.37352;//para el 36
		Float ufvIni 		= (float) 2.35915;//para el 36
		Float ufvFin 		= (float) 2.37376;
		Float precio 		= activo.getPrecio();
		Float Depre 		= activo.getPorcentaje_depreciacion();
		Float PorDepre  	= Depre/100; 
		Float costoActual 	= precio * (ufvFin/ufvIni);
		Float DepAnio 		= costoActual * PorDepre ;
		
		DateTimeFormatter fdf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		LocalDateTime fechaCompra = LocalDateTime.parse(activo.getFechacompra().toString(),fdf2);
		DateTimeFormatter fdf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		//LocalDate fechaFin = LocalDate.parse("2021-12-31", fdf1);
		LocalDate fechaFin = LocalDate.parse(fecha, fdf1);
		
		Long cantMes    	= (Period.between(fechaCompra.toLocalDate(), fechaFin)).toTotalMonths();
		if(cantMes == 0) {
			cantMes = 1L;
		}
		
		Float DepMes	 	= DepAnio/12; 
		Float DepGes	 	= DepMes * cantMes; 
		Float ValorNeto  	= costoActual - DepGes; 
		Float actGes	 	= costoActual - precio;
		objeto.put("actGes",actGes);
		objeto.put("costoActual",costoActual);
		objeto.put("DepGes",DepGes);
		objeto.put("ValorNeto",ValorNeto);
		objeto.put("ufvIni",ufvIni);
		objeto.put("ufvFin",ufvFin);
		objeto.put("precio",precio);
		objeto.put("cantMes",cantMes);
		objeto.put("ValorNeto",ValorNeto);
		objeto.put("costoActual",costoActual);
		
		return objeto;
	}
	
	
}
