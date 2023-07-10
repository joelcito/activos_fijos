package com.activo.fijos.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Soundbank;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {
	
	private final JdbcTemplate jdbcTemplate;
	
	public ReporteController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@PostMapping("/reportIncoporacion")
	public List<Map<String, Object>> reportIncoporacion(@RequestBody String json) {
		
		ObjectMapper objectMapper 				= new ObjectMapper();
		List<Map<String, Object>>  ArrayProv 	= new ArrayList();
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			String fechaIni = jsonMap.get("fechaInicio").toString();
			String fechaFin = jsonMap.get("fechaFin").toString();
			String regional = jsonMap.get("regional").toString();
			String sql = "select * from afw_activo WHERE fechacompra BETWEEN ? AND ? AND regional_id = ?";
			ArrayProv = jdbcTemplate.queryForList(sql,fechaIni,fechaFin,regional);
		} catch (JsonProcessingException e) {
		     e.printStackTrace();
		 }
		
		return ArrayProv;
	}
	
	@PostMapping("/reporteGeneral")
	public List<Map<String, Object>> reporteGeneral(@RequestBody String json) {
		ObjectMapper objectMapper 				= new ObjectMapper();
		List<Map<String, Object>>  ArrayProv 	= new ArrayList();
		List<Map<String, Object>>  ArrayActivo  = new ArrayList();
		
		Map<String, Object> obj = new HashMap();
		Map<String, Object> datos = new HashMap();
				
		String sql;
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			String fechaIniEnviado = jsonMap.get("fechaInicio").toString();
			String fechaFinEnviado = jsonMap.get("fechaFin").toString();
			
			sql = " SELECT * "
					+ " from afw_activo "
					+" WHERE 1=1 ";
			
			Object placaValue = jsonMap.get("placa");
			if(placaValue != null && !placaValue.toString().isEmpty()) {
				sql = sql + " AND codigo = '"+jsonMap.get("placa").toString()+"' ";
			}
			
			Object regional = jsonMap.get("regional");
			if(regional != null && !regional.toString().isEmpty()) {
				sql = sql + " AND regional_id = '"+jsonMap.get("regional").toString()+"' ";
			}
		
			//System.out.println(jsonMap);
			
			sql = sql + " AND (estadoactivo in(0,1,15) or estadoactivo is null) AND grupo_id NOT IN('01','15','08')"; 
					
					//+ " where left(fechacompra,4) > 2015 and estadoactivo in(0,1,15) or estadoactivo is null";
					//+ " where (estadoactivo in(0,1,15) or estadoactivo is null) AND grupo_id NOT IN('01','15','08')";
					//+ " where codigo = 'COS-10-00678' AND ( estadoactivo in(0,1,15) or estadoactivo is null)";
					
			
			
			//String regional = jsonMap.get("regional").toString();
			//sql = "select * from afw_activo WHERE fechacompra BETWEEN ? AND ? ";
			//ArrayProv = jdbcTemplate.queryForList(sql,fechaIni,fechaFin);
			
			
			
			//ArrayActivo = jdbcTemplate.queryForList(sql,fechaIni,fechaFin);
			ArrayActivo = jdbcTemplate.queryForList(sql);
			int contador = 1;
			
			for(Map<String, Object> activo	 : ArrayActivo) {
				//ACTUALIZACION
				float valorActivo 			= 0;
				float actualizacion 		= 0;
				float valorActualizado 		= 0;
				
				valorActivo 				= Float.parseFloat(activo.get("precio").toString());
				valorActualizado 			= Float.parseFloat(activo.get("valactualizado").toString());
				actualizacion 				= (activo.get("act_gestion") != null)? Float.parseFloat(activo.get("act_gestion").toString()) : 0 ;	
				LocalDate fechaCompra		= LocalDate.parse(activo.get("fechacompra").toString());
				Float Depre 				= (float) Math.round(Float.parseFloat(activo.get("porcentaje_depreciacion").toString())*12);				
								
				//DEPRESIACION
				float depreciacionAcumulada 	= 0;
				float actualzaiconDepre 		= 0;
				float deprePeriodo 				= 0;			
				float valorResidual		 		= 0;
				float depreciacionAcumuladaIni	= 0;
				Long  cantMesD 				= (long) 0;
				
				//float UfvIni				= (float) 2.33187;
				float UfvIni				= (float) 1;
				//float Ufvfin				= (float) 2.40498;
				float Ufvfin				= (float) 1;
								
				float porcentajeDepre		= Depre/100;
								
				LocalDate fechaInicio		= fechaCompra;
				//LocalDate fechaInicio		= LocalDate.of(2021, 01, 15);
				LocalDate fechaFin			= LocalDate.of(fechaInicio.getYear(), 12, 31);
				LocalDate fechaFinAntesMigra= LocalDate.of(2022, 12, 31);
				
				//LocalDate fepru = LocalDate.parse("2021-07-19");
				//long mesesFaltantes 		= ChronoUnit.MONTHS.between(fepru, fechaFin);
				long mesesFaltantes 		= ChronoUnit.MONTHS.between(fechaCompra, fechaFin);
				//System.out.println(fechaCompra+" |"+fechaFin+" | ");
				mesesFaltantes++;	
				int mesesYaDepreciado = (int) (((fechaFinAntesMigra.getYear()-fechaCompra.getYear())*12)+mesesFaltantes);
				int aniosdepreciados;
				int cantidadAniosVidaUtil = (int) (100/Depre) ;
				if(mesesYaDepreciado % 12 == 0) {
					aniosdepreciados = (mesesYaDepreciado / 12);					
				}else {					
					aniosdepreciados = (mesesYaDepreciado / 12) + 1;
					cantidadAniosVidaUtil++;
				}
				aniosdepreciados++;
				
				System.out.println("["+contador+"]"+activo.get("idactivo")+" | "+fechaCompra+" | "+fechaFin+" | "+mesesFaltantes+" | > "+mesesYaDepreciado + " | "+aniosdepreciados);
							
				depreciacionAcumulada 	= (activo.get("depacumulada") != null)? Float.parseFloat(activo.get("depacumulada").toString() ): 0 ;
				actualzaiconDepre 		= (activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()) : 0;
				deprePeriodo 			= (activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()) : 0 ;
				valorResidual 			= (activo.get("valpresente") != null)? Float.parseFloat(activo.get("valpresente").toString()) : 0 ;
				
				LocalDate fechaFinDeAnioSigueinteIni 	= LocalDate.parse(fechaIniEnviado);
				LocalDate fechaFinEnvia					= LocalDate.parse(fechaFinEnviado);
				LocalDate fechaFinDeAnioSigueinteFin	;				
				//int gestion 				 			= fechaFinDeAnioSigueinteFin.getYear();
				int gestion 				 			= 2023;
				boolean entro 							= false;
				System.out.println(mesesYaDepreciado+" | "+
									aniosdepreciados+" <= "+
									cantidadAniosVidaUtil+" | "+
									fechaFinDeAnioSigueinteIni+" | "+
									fechaFinEnvia+" | "+
									(aniosdepreciados <= cantidadAniosVidaUtil)+" | "+
									(fechaFinDeAnioSigueinteIni.isBefore(fechaFinEnvia))
									);

				//for(int i = aniosdepreciados; i <= cantidadAniosVidaUtil; i++) {				
					//while(aniosdepreciados <= cantidadAniosVidaUtil && fechaFinEnvia.isBefore(fechaFinDeAnioSigueinteFin)) {
					while(aniosdepreciados <= cantidadAniosVidaUtil && fechaFinDeAnioSigueinteIni.isBefore(fechaFinEnvia)) {
						
						entro = true ; 
					
						//ACTUALZIACION
						valorActivo 		= valorActualizado;
						actualizacion 		= ((valorActivo * Ufvfin) / UfvIni) - valorActivo; 
						valorActualizado	= valorActivo + actualizacion;
						
						//if(cantidadAniosVidaUtil  == i) {
						if(cantidadAniosVidaUtil  == aniosdepreciados) {
							int mes = (int) (12 - mesesFaltantes);
							if(mes == 0) {
								mes = 12;
							}
							YearMonth aniMes = YearMonth.of(gestion, Month.of(mes));
							fechaFinDeAnioSigueinteFin = LocalDate.of(gestion,mes,aniMes.lengthOfMonth());
							cantMesD    				=  (long) mes;
							//System.out.println("si");
						}else {
							
							//System.out.println("no");
							fechaFinDeAnioSigueinteFin = LocalDate.of(gestion,12,31);
							
							if(fechaFinDeAnioSigueinteFin.isBefore(fechaFinEnvia)) {
							//if(fechaFinEnvia.isBefore(fechaFinDeAnioSigueinteFin)) {
								/*
								System.out.println("si che"+" | "+
													fechaFinDeAnioSigueinteFin+" | "+
													fechaFinEnvia+" | ["+
													fechaFinEnvia.getMonthValue()+" | "+
													fechaFinDeAnioSigueinteFin.getMonthValue()+" <> "+
													fechaFinEnvia.getYear()+" | "+
													fechaFinDeAnioSigueinteFin.getYear()+"]"
													);

								*/
								cantMesD    				= ((Period.between(fechaFinDeAnioSigueinteIni, fechaFinDeAnioSigueinteFin)).toTotalMonths());
							}else {
								/*
								System.out.println("no che"+" | "+
													fechaFinDeAnioSigueinteFin+" | "+
													fechaFinEnvia+" | [ "+
													fechaFinEnvia.getMonthValue()+" | "+
													fechaFinDeAnioSigueinteFin.getMonthValue()+" <> "+
													fechaFinEnvia.getYear()+" | "+
													fechaFinDeAnioSigueinteFin.getYear()+"]"
													);
													*/
								//cantMesD    				= ((Period.between(fechaFinDeAnioSigueinteIni, fechaFinDeAnioSigueinteFin)).toTotalMonths());
								cantMesD    				= (long) fechaFinEnvia.getMonthValue();
							}
							
							
						}					
						
						
						depreciacionAcumuladaIni	= depreciacionAcumulada;
						actualzaiconDepre			= ((depreciacionAcumuladaIni*Ufvfin)/UfvIni)-depreciacionAcumuladaIni;
						deprePeriodo				= ((valorActualizado*porcentajeDepre)/12)*cantMesD;
						depreciacionAcumulada		= depreciacionAcumuladaIni+actualzaiconDepre+deprePeriodo;
						valorResidual				= valorActualizado - depreciacionAcumulada;
						
						datos.put("valorActivo",					valorActivo);
						datos.put("actualizacion",					actualizacion);
						datos.put("valorActualizado",				valorActualizado);
						datos.put("depreciacionAcumuladaIni",		depreciacionAcumuladaIni);
						datos.put("actualzaiconDepre",				actualzaiconDepre);
						datos.put("Depre",					 		Depre);
						datos.put("fechaFinDeAnioSigueinteIni",		fechaFinDeAnioSigueinteIni);
						datos.put("fechaFinDeAnioSigueinteFin",		fechaFinDeAnioSigueinteFin);
						datos.put("deprePeriodo",					deprePeriodo);
						datos.put("cantMesD",					 	cantMesD);
						datos.put("depreciacionAcumulada",			depreciacionAcumulada);
						datos.put("valorResidual",					valorResidual);
						
						System.out.println(activo.get("idactivo")+" | "+"+ "+ gestion+" + | "+
								valorActivo+" | "+
								actualizacion+" | "+
								valorActualizado+" < | > "+
								depreciacionAcumuladaIni+" | "+
								actualzaiconDepre+" | "+
								Depre+" | "+
								fechaFinDeAnioSigueinteIni+" | "+
								fechaFinDeAnioSigueinteFin+" | "+
								deprePeriodo+" | "+
								cantMesD+" | "+
								depreciacionAcumulada+" | "+
								valorResidual
								);						
						
						//PARA LA SIGUEINTE EGSTION						
						gestion++;			
						
						//if(fechaFinEnvia.isBefore(fechaFinDeAnioSigueinteFin)) {
						/*
						if(fechaFinDeAnioSigueinteFin.isBefore(fechaFinEnvia)) {
							System.out.println(fechaFinDeAnioSigueinteFin+" | "+fechaFinEnvia+" | si");
						}else {
							System.out.println(fechaFinDeAnioSigueinteFin+" | "+fechaFinEnvia+" | no");
							break;
						}
						*/
						
						fechaFinDeAnioSigueinteIni = fechaFinDeAnioSigueinteFin;
					
						//	}	
						
						aniosdepreciados++;
				}
					Map<String, Object> activoDevuelo = new HashMap();
					
					activoDevuelo.put("idactivo", 					activo.get("idactivo"));
					activoDevuelo.put("codigo", 					activo.get("codigo"));
					activoDevuelo.put("descripcion", 				activo.get("descripcion"));
					activoDevuelo.put("fechacompra", 				activo.get("fechacompra"));
					activoDevuelo.put("precio", 					activo.get("precio"));
					
					if(entro) {
						activoDevuelo.put("valorActivo", 				datos.get("valorActivo"));
						activoDevuelo.put("actualizacion", 				datos.get("actualizacion"));
						activoDevuelo.put("valorActualizado", 			datos.get("valorActualizado"));
						activoDevuelo.put("depreciacionAcumuladaIni", 	datos.get("depreciacionAcumuladaIni"));
						activoDevuelo.put("actualzaiconDepre", 			datos.get("actualzaiconDepre"));
						activoDevuelo.put("Depre", 						datos.get("Depre"));
						activoDevuelo.put("fechaFinDeAnioSigueinteIni", datos.get("fechaFinDeAnioSigueinteIni"));
						activoDevuelo.put("fechaFinDeAnioSigueinteFin", datos.get("fechaFinDeAnioSigueinteFin"));
						activoDevuelo.put("deprePeriodo", 				datos.get("deprePeriodo"));
						activoDevuelo.put("cantMesD", 					datos.get("cantMesD"));
						activoDevuelo.put("depreciacionAcumulada", 		datos.get("depreciacionAcumulada"));
						activoDevuelo.put("valorResidual", 				datos.get("valorResidual"));
					}else {
						Depre 				= (float) Math.round(Float.parseFloat(activo.get("porcentaje_depreciacion").toString())*12);				
						activoDevuelo.put("valorActivo", 				"JOELCITO");
						activoDevuelo.put("actualizacion", 				activo.get("valactualizado"));
						activoDevuelo.put("valorActualizado", 			activo.get("valactualizado"));
						activoDevuelo.put("depreciacionAcumuladaIni", 	activo.get("act_dep_acumulado"));
						activoDevuelo.put("actualzaiconDepre", 			activo.get("act_dep_acumulado"));
						activoDevuelo.put("Depre", 						Depre);
						activoDevuelo.put("fechaFinDeAnioSigueinteIni", "JOELCITO");
						activoDevuelo.put("fechaFinDeAnioSigueinteFin", "JOELCITO");
						activoDevuelo.put("deprePeriodo", 				activo.get("dep_gestion"));
						activoDevuelo.put("cantMesD", 					0);
						activoDevuelo.put("depreciacionAcumulada", 		activo.get("depacumulada"));
						activoDevuelo.put("valorResidual", 				activo.get("valpresente"));
					}				
				
				ArrayProv.add(activoDevuelo);				
								
				//if(contador == 5)
					//break;
				
				contador++;
								
			}	
			
		} catch (JsonProcessingException e) {
		     e.printStackTrace();
		 }
		
		return ArrayProv;
	}

}
