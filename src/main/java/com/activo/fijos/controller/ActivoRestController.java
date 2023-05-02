package com.activo.fijos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
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
import com.activo.fijos.models.entity.Regional;
import com.activo.fijos.models.entity.Ufv;
import com.activo.fijos.models.services.IActivoService;
import com.activo.fijos.models.services.IUfvService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
@RequestMapping("/api/activo")
public class ActivoRestController {
	
	@Autowired
	private IActivoService activoService;
	@Autowired
	private IUfvService ufvService;
	
	@GetMapping("/listado")
	public List<Activo> index() {
		return this.activoService.findAll();		
		//return this.activoService.listaActivos();
	}
	
	@GetMapping("/listadoPer")
	public List<Map<String, Object>> listadoPer() {
		return this.activoService.listaActivosPer();
		
	}
	
	
	@GetMapping("/{id}")
	public Activo show(@PathVariable String id) {
		return activoService.findById(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Activo create(@RequestBody Activo activo) {
		
		Regional regional = activo.getRegional();
		String idregional = regional.getIdregional();
		
		String ultimoRegistroID = this.activoService.maxIdActivo(idregional);
		
		if(ultimoRegistroID != null) {
			String[] partes =  ultimoRegistroID.split("-");
			
			int idNew = Integer.parseInt(partes[1])+1;
			String idString = idNew+"";
			
			if(idString.length() == 1)
				idString = idregional+"-000000"+idString;
			else if(idString.length() == 2)
				idString = idregional+"-00000"+idString;
			else if(idString.length() == 3)
				idString = idregional+"-0000"+idString;
			else if(idString.length() == 4)
				idString = idregional+"-000"+idString;
			else if(idString.length() == 5)
				idString = idregional+"-00"+idString;
			else if(idString.length() == 6)
				idString = idregional+"-0"+idString;
						
			activo.setIdactivo(idString);
			activo.setEstadoregistro("APR");
			activo.setFecha(new Date());
			activo.setFechacreacion(new Date());
			
			System.out.println(idString);
		}
				
		return activoService.save(activo);
		
		
		/*
		
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
		String nuevoid = id+"";
		activo.setIdactivo(nuevoid);
		activo.setEstadoregistro("APR");
		activo.setFecha(new Date());
		activo.setFechacreacion(new Date());
		//return activoService.save(activo);
		*/
		//return activo;
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
				
		Map<String, Object> objeto 	= new HashMap();
		
		Activo activo 				= activoService.findById(id);
		
		DateTimeFormatter fdf1 		= DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fechaFin 			= LocalDate.parse(fecha, fdf1);
				
		Float ufvIni 				= activo.getUfvcompra();//para el 36
		Ufv ufv 					= this.ufvService.buscarPorFecha(fechaFin);
		Float ufvFin 			= (float) 1;
		if(!ObjectUtils.isEmpty(ufv)) {
			ufvFin 			= ufv.getValor();
		}
		Float precio 		= activo.getPrecio();
		Float Depre 		= activo.getPorcentaje_depreciacion();
		Float PorDepre  	= Depre/100; 
		Float costoActual 	= precio * (ufvFin/ufvIni);
		Float DepAnio 		= costoActual * PorDepre ;
		
		DateTimeFormatter fdf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		LocalDateTime fechaCompra = LocalDateTime.parse(activo.getFechacompra().toString(),fdf2);
		
		
		Long cantMes    	= (Period.between(fechaCompra.toLocalDate(), fechaFin)).toTotalMonths();
		
		cantMes = cantMes+1;
		/*
		System.out.println(fechaCompra.toLocalDate()+" || "+fechaFin);
		if(cantMes == 0) {
			cantMes = 1L;
		}
		*/
		
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
		System.out.println("======================");
		System.out.println(ufvIni+" | "+ufvFin+" | "+(ufvFin/ufvIni)+" | "+costoActual+" | "+cantMes);
		System.out.println("======================");
		
		return objeto;
	}
	
	@PostMapping("/calculaDepreModificable")
	public Map<String, Object> calculaDepreModificable(@RequestBody String json) {
		
		Map<String, Object> objeto 	= new HashMap();
		
		//System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {			
		    //MyObject myObject = objectMapper.readValue(json, Map.class);
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			Activo activoDepre = this.activoService.findById((jsonMap.get("depreActivoId")).toString());
			
			LocalDate fechaIni	= LocalDate.parse(jsonMap.get("depreFechaIni").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate fechaFin 	= LocalDate.parse(jsonMap.get("depreFechaFin").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			Float ufvIni 		= (float) Float.parseFloat(jsonMap.get("depreUfvIni").toString());
			Float ufvFin		= (float) Float.parseFloat(jsonMap.get("depreUfvFin").toString());
			Float precio 		= activoDepre.getPrecio();
			Float Depre 		= activoDepre.getPorcentaje_depreciacion();
			Float PorDepre  	= Depre/100; 
			Float costoActual 	= precio * (ufvFin/ufvIni);
			Float DepAnio 		= costoActual * PorDepre ;
			
			Long cantMes    	= ((Period.between(fechaIni, fechaFin)).toTotalMonths()) + 1;
			
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
			/*
			System.out.println(jsonMap.get("depreFechaIni"));
			System.out.println(jsonMap.get("depreUfvIni"));
			System.out.println(jsonMap.get("depreActivoId"));
			System.out.println(jsonMap.get("depreFechaFin"));
			System.out.println(jsonMap.get("depreUfvFin"));
			*/
		} catch (JsonProcessingException e) {
		    // Handle the exception
		    e.printStackTrace();
		}
				
		return objeto;
	}
	
	
	@PostMapping("/buscaActivo")
	public List<Map<String, Object>> buscaActivo(@RequestBody String json) {
				
		ObjectMapper objectMapper = new ObjectMapper();
		
		List<Map<String, Object>> datos = new ArrayList<>();
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			if(!jsonMap.get("variable1").toString().equals(""))		
				datos = this.activoService.buscaActivo(jsonMap.get("variable1").toString());
			else if(!jsonMap.get("variable2").toString().equals(""))
				datos = this.activoService.buscaActivoDescripcion(jsonMap.get("variable2").toString());
			else if(jsonMap.get("variable2").toString().equals("") && jsonMap.get("variable2").toString().equals(""))
				datos = this.activoService.listaActivosPer();
			
		} catch (JsonProcessingException e) {
		    // Handle the exception
		    e.printStackTrace();
		}
		
		return datos;
	}
	
	
	
}
