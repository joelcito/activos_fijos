package com.activo.fijos.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.net.http.HttpHeaders;
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

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.core.io.ClassPathResource;
import java.util.Collections;

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
	
	public JasperPrint getReport(List<Map<String, Object>> List, String archivo, Map<String, Object> datosAux)  throws JRException, IOException{
		
		Map<String, Object> params  = new HashMap();
		
		params.put("tableActivos", new JRBeanCollectionDataSource(List));
		params.put("fechaIni", datosAux.get("fechaIni"));
		params.put("fechaFin", datosAux.get("fechaFin"));
		
		// Cargar la imagen "logocossmil.png" desde el classpath utilizando ClassPathResource
	    ClassPathResource imageResource = new ClassPathResource("logocoosmil.png");
	    InputStream imageStream = imageResource.getInputStream();
	    // Agregar la imagen al mapa de parámetros
	    params.put("logo", imageStream);
		
		System.out.println("1: "+archivo);		
		
		// Cargar el archivo JRXML desde el classpath utilizando ClassPathResource
	    ClassPathResource resource = new ClassPathResource(archivo + ".jrxml");
			InputStream jrxmlInput = resource.getInputStream();
			JasperPrint report = JasperFillManager.fillReport(
			        JasperCompileManager.compileReport(jrxmlInput), params, new JREmptyDataSource()
			    );
		
		/*
		JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
					ResourceUtils.getFile("classpath:"+archivo+".jrxml")
					.getAbsolutePath()), params, new JREmptyDataSource());
		 */	
		
		return report;	
		
	}
	
	public byte[] exporToPdf(List<Map<String, Object>> list, String archivo, Map<String, Object> datos) throws JRException,  IOException {
		System.out.println("2: "+archivo);
		return JasperExportManager.exportReportToPdf( getReport(list, archivo, datos));
	}
	
	@GetMapping("/reportGeneralPDFPrueba")
	public ResponseEntity<byte[]> exportPDFPrueba() throws JRException, IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("det_gen", "det_gen.pdf");
		
				
		List<Map<String, Object>> j = new ArrayList<>();

		Map<String, Object> obj  = new HashMap();
		obj.put("codigo", "123");		
		j.add(obj);		
		
		obj = new HashMap<>();
		obj.put("codigo", "456");
		j.add(obj);	
		
		obj = new HashMap<>();
		obj.put("codigo", "789");
		j.add(obj);		
						
		//String lim = "general.jrxml";
		String lim = "det_gen";
		//String lim = "prueba";	
		return ResponseEntity.ok().headers(headers).body(exporToPdf(j, lim, obj));
	}
	
	
	@PostMapping("/reportGeneralPDF")
	public ResponseEntity<byte[]> reportGeneralPDF(@RequestBody String json) throws JRException, IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("det_gen", "det_gen.pdf");
		
		List<Map<String, Object>> listadoActivosReporte = new ArrayList<>();
		String nombreArchivoReporte = "det_gen";	
		
		
		
		
		
		/*===========	DE AQUI COMIENZA EL REPORTE CHEEEE	=========*/
		ObjectMapper objectMapper 				= new ObjectMapper();
		List<Map<String, Object>>  ArrayProv 	= new ArrayList();
		List<Map<String, Object>>  ArrayActivo  = new ArrayList();
		
		Map<String, Object> obj 		= new HashMap();
		Map<String, Object> datos 		= new HashMap();		
		Map<String, Object> datosAux 	= new HashMap();
				
		String sql;
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			String fechaIniEnviado = jsonMap.get("fechaInicio").toString();
			String fechaFinEnviado = jsonMap.get("fechaFin").toString();
			
			datosAux.put("fechaIni", fechaIniEnviado);
			datosAux.put("fechaFin", fechaFinEnviado);
			
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
					
					
					Map<String, Object> objActivo  = new HashMap();
					
					objActivo.put("codigo",							activo.get("codigo"));
					objActivo.put("descripcion",					activo.get("descripcion"));
					objActivo.put("fecha_adq",						activo.get("fechacompra").toString());
					objActivo.put("costo_historico",				Float.parseFloat(activo.get("precio").toString()));
					
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
						
						
						objActivo.put("costo_actual",				Float.parseFloat(datos.get("valorActivo").toString()));
						objActivo.put("dep_acu_inicial",			Float.parseFloat(datos.get("depreciacionAcumuladaIni").toString()));
						objActivo.put("factor_actualizado",			datos.get("actualizacion").toString());
						objActivo.put("act_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));						
						objActivo.put("cost_actualizado",			Float.parseFloat(datos.get("valorActualizado").toString()));
						objActivo.put("porc_dep_anual",				datos.get("Depre").toString());
						objActivo.put("dep_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));
						objActivo.put("act_dep_acum",				Float.parseFloat(datos.get("actualzaiconDepre").toString()));
						objActivo.put("dep_acum_total",				Float.parseFloat(datos.get("depreciacionAcumulada").toString()));						
						objActivo.put("valor_neto",					Float.parseFloat(datos.get("valorResidual").toString()));						
						
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
						
						/*
						objActivo.put("cost_actualizado",			Float.parseFloat(datos.get("valorActualizado").toString()));
						objActivo.put("porc_dep_anual",				datos.get("Depre").toString());
						objActivo.put("dep_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));
						objActivo.put("act_dep_acum",				Float.parseFloat(datos.get("actualzaiconDepre").toString()));
						objActivo.put("dep_acum_total",				Float.parseFloat(datos.get("depreciacionAcumulada").toString()));						
						objActivo.put("valor_neto",					Float.parseFloat(datos.get("valorResidual").toString()));						
						*/
						
						objActivo.put("costo_actual",				(activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()) : 0);
						objActivo.put("dep_acu_inicial",			(activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0);
						objActivo.put("factor_actualizado",			(activo.get("valactualizado") != null)? activo.get("valactualizado").toString(): "");					
						objActivo.put("act_gestion",				(activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0);					
						objActivo.put("cost_actualizado",			(activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()): 0);
						objActivo.put("porc_dep_anual",				Depre.toString());
						objActivo.put("dep_gestion",				(activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0);
						objActivo.put("act_dep_acum",				(activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0);
						objActivo.put("dep_acum_total",				(activo.get("depacumulada") != null)? Float.parseFloat(activo.get("depacumulada").toString()): 0);
						objActivo.put("valor_neto",					(activo.get("valpresente") != null)? Float.parseFloat(activo.get("valpresente").toString()): 0);
						
					}				
				
				ArrayProv.add(activoDevuelo);	
				
				listadoActivosReporte.add(objActivo);		
								
				//if(contador == 5)
					//break;
				
				contador++;
								
			}	
			
		} catch (JsonProcessingException e) {
		     e.printStackTrace();
		 }	
		
		/*===========	AQUI TERMINA EL REPORTE CHEEEE	=========*/
		return ResponseEntity.ok().headers(headers).body(exporToPdf(listadoActivosReporte, nombreArchivoReporte, datosAux));
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
	
	
	@PostMapping("/reportePorRegimen")
	public ResponseEntity<byte[]> reportePorRegimen(@RequestBody String json) throws JRException, IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("report_regimen", "report_regimen.pdf");
		
		List<Map<String, Object>> listadoActivosReporte = new ArrayList<>();
		String nombreArchivoReporte = "report_regimen";		
		
		
		/*===========	DE AQUI COMIENZA EL REPORTE CHEEEE	=========*/
		ObjectMapper objectMapper 				= new ObjectMapper();
		List<Map<String, Object>>  ArrayProv 	= new ArrayList();
		List<Map<String, Object>>  ArrayActivo  = new ArrayList();
		List<Map<String, Object>>  ArrayRegimen  = new ArrayList();
		
		Map<String, Object> obj 		= new HashMap();
		Map<String, Object> datos 		= new HashMap();		
		Map<String, Object> datosAux 	= new HashMap();
				
		String sql, slqAntes;
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			String fechaIniEnviado = jsonMap.get("fechaInicio").toString();
			String fechaFinEnviado = jsonMap.get("fechaFin").toString();
			
			datosAux.put("fechaIni", fechaIniEnviado);
			datosAux.put("fechaFin", fechaFinEnviado);
			Object regimen = jsonMap.get("regimen");			
			
			slqAntes = " SELECT r.idregimen, r.descripcion "
					+ " from afw_activo a inner join afw_regimen r "
					+ "	on a.regimen_id = r.idregimen "
					+ " WHERE 1 = 1 ";
			
			if(regimen != null && !regimen.toString().isEmpty()) {
				slqAntes = slqAntes + " AND a.regimen_id = '"+jsonMap.get("regimen").toString()+"' ";
			}
			
			slqAntes = slqAntes + " group by r.idregimen, r.descripcion ORDER BY r.idregimen";
			
			ArrayRegimen = jdbcTemplate.queryForList(slqAntes);
			
			for(Map<String, Object> itemregimen : ArrayRegimen) {
				
				String valRegimen = itemregimen.get("idregimen").toString();
				String nomREgimen = itemregimen.get("descripcion").toString();
				
				sql = " SELECT a.*, r.descripcion as regDes, r.idregimen as regId"
						+ " from afw_activo a INNER JOIN afw_regimen r"
						+ " on a.regimen_id = r.idregimen "
						+" WHERE a.regimen_id = '"+valRegimen+"' ";				
				sql = sql + " AND (a.estadoactivo in(0,1,15) or a.estadoactivo is null) AND a.grupo_id NOT IN('01','15','08')"; 
				
				//ArrayActivo = jdbcTemplate.queryForList(sql,fechaIni,fechaFin);
				ArrayActivo = jdbcTemplate.queryForList(sql);
				int contador = 1;
				System.out.println(ArrayActivo.size()+" | "+jsonMap.get("regimen")+" | "+sql);
				
				float sumaTotalCostoHistorico 		= 0;
				float sumaTotalCostoActualizado 	= 0;
				float sumaTotalDepAcumTotalGrupo	= 0;
				float sumaTotalVAlorNetoIni 		= 0;
				float sumaTotalActGestion 			= 0;
				float sumaTotalCostoTotActualizado 	= 0;
				float sumaTotalDepGestion 			= 0;
				float sumaTotalActDepAcum	 		= 0;
				float sumaTotalDepAcum				= 0;
				float sumaTotalValorNeto			= 0;

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
					while(aniosdepreciados <= cantidadAniosVidaUtil && fechaFinDeAnioSigueinteIni.isBefore(fechaFinEnvia)) {
						entro = true ; 
					
						//ACTUALZIACION
						valorActivo 		= valorActualizado;
						actualizacion 		= ((valorActivo * Ufvfin) / UfvIni) - valorActivo; 
						valorActualizado	= valorActivo + actualizacion;
						
						if(cantidadAniosVidaUtil  == aniosdepreciados) {
							int mes = (int) (12 - mesesFaltantes);
							if(mes == 0) {
								mes = 12;
							}
							YearMonth aniMes = YearMonth.of(gestion, Month.of(mes));
							fechaFinDeAnioSigueinteFin = LocalDate.of(gestion,mes,aniMes.lengthOfMonth());
							cantMesD    				=  (long) mes;
						}else {
							fechaFinDeAnioSigueinteFin = LocalDate.of(gestion,12,31);
							
							if(fechaFinDeAnioSigueinteFin.isBefore(fechaFinEnvia)) {
								cantMesD    				= ((Period.between(fechaFinDeAnioSigueinteIni, fechaFinDeAnioSigueinteFin)).toTotalMonths());
							}else {
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
												
						//PARA LA SIGUEINTE EGSTION						
						gestion++;		
						fechaFinDeAnioSigueinteIni = fechaFinDeAnioSigueinteFin;
						aniosdepreciados++;
					}
	
					Map<String, Object> activoDevuelo = new HashMap();
					
					activoDevuelo.put("idactivo", 					activo.get("idactivo"));
					activoDevuelo.put("codigo", 					activo.get("codigo"));
					activoDevuelo.put("descripcion", 				activo.get("descripcion"));
					activoDevuelo.put("fechacompra", 				activo.get("fechacompra"));
					activoDevuelo.put("precio", 					activo.get("precio"));
					
					
					Map<String, Object> objActivo  = new HashMap();
					
					objActivo.put("codigo",							activo.get("regId"));
					objActivo.put("descripcion",					activo.get("regDes"));
					objActivo.put("fecha_adq",						activo.get("fechacompra").toString());
					objActivo.put("costo_historico",				Float.parseFloat(activo.get("precio").toString()));

					float costo_historico = Float.parseFloat(activo.get("precio").toString());
					float costo_actual ;
					float dep_acu_inicial ;
					float factor_actualizado ;
					float act_gestion ;
					float cost_actualizado ;
					float porc_dep_anual ;
					float dep_gestion ;
					float act_dep_acum ;
					float dep_acum_total ;
					float valor_neto ;
					
					if(entro) {						
						
						objActivo.put("costo_actual",				Float.parseFloat(datos.get("valorActivo").toString()));
						objActivo.put("dep_acu_inicial",			Float.parseFloat(datos.get("depreciacionAcumuladaIni").toString()));
						objActivo.put("factor_actualizado",			datos.get("actualizacion").toString());
						objActivo.put("act_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));						
						objActivo.put("cost_actualizado",			Float.parseFloat(datos.get("valorActualizado").toString()));
						objActivo.put("porc_dep_anual",				datos.get("Depre").toString());
						objActivo.put("dep_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));
						objActivo.put("act_dep_acum",				Float.parseFloat(datos.get("actualzaiconDepre").toString()));
						objActivo.put("dep_acum_total",				Float.parseFloat(datos.get("depreciacionAcumulada").toString()));						
						objActivo.put("valor_neto",					Float.parseFloat(datos.get("valorResidual").toString()));	
						
						
						costo_actual 			= Float.parseFloat(datos.get("valorActivo").toString());
						dep_acu_inicial 		= Float.parseFloat(datos.get("depreciacionAcumuladaIni").toString());
						act_gestion 			= Float.parseFloat(datos.get("deprePeriodo").toString());
						cost_actualizado 		= Float.parseFloat(datos.get("valorActualizado").toString());
						dep_gestion 			= Float.parseFloat(datos.get("deprePeriodo").toString());
						act_dep_acum 			= Float.parseFloat(datos.get("actualzaiconDepre").toString());
						dep_acum_total 			= Float.parseFloat(datos.get("depreciacionAcumulada").toString());
						valor_neto 				= Float.parseFloat(datos.get("valorResidual").toString());			
						
					}else {
						Depre 				= (float) Math.round(Float.parseFloat(activo.get("porcentaje_depreciacion").toString())*12);
						
						objActivo.put("costo_actual",				(activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()) : 0);
						objActivo.put("dep_acu_inicial",			(activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0);
						objActivo.put("factor_actualizado",			(activo.get("valactualizado") != null)? activo.get("valactualizado").toString(): "");					
						objActivo.put("act_gestion",				(activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0);					
						objActivo.put("cost_actualizado",			(activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()): 0);
						objActivo.put("porc_dep_anual",				Depre.toString());
						objActivo.put("dep_gestion",				(activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0);
						objActivo.put("act_dep_acum",				(activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0);
						objActivo.put("dep_acum_total",				(activo.get("depacumulada") != null)? Float.parseFloat(activo.get("depacumulada").toString()): 0);
						objActivo.put("valor_neto",					(activo.get("valpresente") != null)? Float.parseFloat(activo.get("valpresente").toString()): 0);

						costo_actual 		= (activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()) : 0;
						dep_acu_inicial 	= (activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0;
						act_gestion 		= (activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0;				
						cost_actualizado 	= (activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()): 0;
						dep_gestion 		= (activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0;
						act_dep_acum 		= (activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0;
						dep_acum_total 		= (activo.get("depacumulada") != null)? Float.parseFloat(activo.get("depacumulada").toString()): 0;
						valor_neto 			= (activo.get("valpresente") != null)? Float.parseFloat(activo.get("valpresente").toString()): 0;
					}				
					ArrayProv.add(activoDevuelo);	
					// listadoActivosReporte.add(objActivo);		
					contador++;

					// AQUI SE SUMA LA DEPRE DE LOS ACTIVOS DE UN REGIMEN 
					sumaTotalCostoHistorico			= sumaTotalCostoHistorico 		+ costo_historico;
					sumaTotalCostoActualizado		= sumaTotalCostoActualizado 	+ costo_actual;	
					sumaTotalDepAcumTotalGrupo		= sumaTotalDepAcumTotalGrupo 	+ dep_acu_inicial;	
					sumaTotalVAlorNetoIni			= sumaTotalVAlorNetoIni 		+ 0;
					sumaTotalActGestion			 	= sumaTotalActGestion 			+ act_gestion;
					sumaTotalCostoTotActualizado	= sumaTotalCostoTotActualizado 	+ cost_actualizado;		
					sumaTotalDepGestion			 	= sumaTotalDepGestion 			+ dep_gestion;
					sumaTotalActDepAcum			 	= sumaTotalActDepAcum 			+ act_dep_acum;
					sumaTotalDepAcum			 	= sumaTotalDepAcum 				+ dep_acum_total;
					sumaTotalValorNeto			 	= sumaTotalValorNeto 			+ valor_neto;
				}

				Map<String, Object> objActivoSuma  = new HashMap();
					
				objActivoSuma.put("codigo",							valRegimen);
				objActivoSuma.put("descripcion",					nomREgimen);
				objActivoSuma.put("fecha_adq",						"1");

				objActivoSuma.put("costo_historico",				sumaTotalCostoHistorico);
				objActivoSuma.put("costo_actual",					sumaTotalCostoActualizado);
				objActivoSuma.put("dep_acu_inicial",				sumaTotalDepAcumTotalGrupo);
				objActivoSuma.put("factor_actualizado",				"");
				objActivoSuma.put("act_gestion",					sumaTotalActGestion);						
				objActivoSuma.put("cost_actualizado",				sumaTotalCostoTotActualizado);
				objActivoSuma.put("porc_dep_anual",					"");
				objActivoSuma.put("dep_gestion",					sumaTotalDepGestion);
				objActivoSuma.put("act_dep_acum",					sumaTotalActDepAcum);
				objActivoSuma.put("dep_acum_total",					sumaTotalDepAcum);						
				objActivoSuma.put("valor_neto",						sumaTotalValorNeto);
			
				listadoActivosReporte.add(objActivoSuma);
			}
			
		} catch (JsonProcessingException e) {
		     e.printStackTrace();
		 }	
		
		/*===========	AQUI TERMINA EL REPORTE CHEEEE	=========*/
		return ResponseEntity.ok().headers(headers).body(exporToPdf(listadoActivosReporte, nombreArchivoReporte, datosAux));
	}

	
	@PostMapping("/reportePorGrupo")
	public ResponseEntity<byte[]> reportePorGrupo(@RequestBody String json) throws JRException, IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("report_grupo", "report_grupo.pdf");
		
		List<Map<String, Object>> listadoActivosReporte = new ArrayList<>();
		String nombreArchivoReporte = "report_grupo";		
		
		
		/*===========	DE AQUI COMIENZA EL REPORTE CHEEEE	=========*/
		ObjectMapper objectMapper 					= new ObjectMapper();
		List<Map<String, Object>>  ArrayProv 		= new ArrayList();
		List<Map<String, Object>>  ArrayActivo  	= new ArrayList();
		List<Map<String, Object>>  ArrayGrupo  		= new ArrayList();
		
		Map<String, Object> obj 		= new HashMap();
		Map<String, Object> datos 		= new HashMap();		
		Map<String, Object> datosAux 	= new HashMap();
				
		String sql, slqAntes;
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			String fechaIniEnviado = jsonMap.get("fechaInicio").toString();
			String fechaFinEnviado = jsonMap.get("fechaFin").toString();
			
			datosAux.put("fechaIni", fechaIniEnviado);
			datosAux.put("fechaFin", fechaFinEnviado);
			Object grupo = jsonMap.get("grupo");			
			
			slqAntes = " SELECT g.idgrupo, g.descripcion "
					+ " from afw_activo a inner join afw_grupo g "
					+ "	on a.grupo_id = g.idgrupo "
					+ " WHERE 1 = 1 ";
			
			if(grupo != null && !grupo.toString().isEmpty()) {
				slqAntes = slqAntes + " AND a.grupo_id = '"+jsonMap.get("grupo").toString()+"' ";
			}
			
			slqAntes = slqAntes + " group by g.idgrupo, g.descripcion ORDER BY g.idgrupo";
			
			ArrayGrupo = jdbcTemplate.queryForList(slqAntes);
			
			for(Map<String, Object> itemgrupo : ArrayGrupo) {
				
				String valGrupo = itemgrupo.get("idgrupo").toString();
				String nomGrupo = itemgrupo.get("descripcion").toString();
				
				sql = " SELECT a.*, r.descripcion as regDes, r.idregimen as regId"
						+ " from afw_activo a INNER JOIN afw_regimen r"
						+ " on a.regimen_id = r.idregimen "
						+" WHERE a.grupo_id = '"+valGrupo+"' ";				
				sql = sql + " AND (a.estadoactivo in(0,1,15) or a.estadoactivo is null) AND a.grupo_id NOT IN('01','15','08')"; 
				
				//ArrayActivo = jdbcTemplate.queryForList(sql,fechaIni,fechaFin);
				ArrayActivo = jdbcTemplate.queryForList(sql);
				int contador = 1;
				System.out.println(ArrayActivo.size()+" | "+jsonMap.get("regimen")+" | "+sql);
				
				float sumaTotalCostoHistorico 		= 0;
				float sumaTotalCostoActualizado 	= 0;
				float sumaTotalDepAcumTotalGrupo	= 0;
				float sumaTotalVAlorNetoIni 		= 0;
				float sumaTotalActGestion 			= 0;
				float sumaTotalCostoTotActualizado 	= 0;
				float sumaTotalDepGestion 			= 0;
				float sumaTotalActDepAcum	 		= 0;
				float sumaTotalDepAcum				= 0;
				float sumaTotalValorNeto			= 0;

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
					while(aniosdepreciados <= cantidadAniosVidaUtil && fechaFinDeAnioSigueinteIni.isBefore(fechaFinEnvia)) {
						entro = true ; 
					
						//ACTUALZIACION
						valorActivo 		= valorActualizado;
						actualizacion 		= ((valorActivo * Ufvfin) / UfvIni) - valorActivo; 
						valorActualizado	= valorActivo + actualizacion;
						
						if(cantidadAniosVidaUtil  == aniosdepreciados) {
							int mes = (int) (12 - mesesFaltantes);
							if(mes == 0) {
								mes = 12;
							}
							YearMonth aniMes = YearMonth.of(gestion, Month.of(mes));
							fechaFinDeAnioSigueinteFin = LocalDate.of(gestion,mes,aniMes.lengthOfMonth());
							cantMesD    				=  (long) mes;
						}else {
							fechaFinDeAnioSigueinteFin = LocalDate.of(gestion,12,31);
							
							if(fechaFinDeAnioSigueinteFin.isBefore(fechaFinEnvia)) {
								cantMesD    				= ((Period.between(fechaFinDeAnioSigueinteIni, fechaFinDeAnioSigueinteFin)).toTotalMonths());
							}else {
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
												
						//PARA LA SIGUEINTE EGSTION						
						gestion++;		
						fechaFinDeAnioSigueinteIni = fechaFinDeAnioSigueinteFin;
						aniosdepreciados++;
					}
	
					Map<String, Object> activoDevuelo = new HashMap();
					
					activoDevuelo.put("idactivo", 					activo.get("idactivo"));
					activoDevuelo.put("codigo", 					activo.get("codigo"));
					activoDevuelo.put("descripcion", 				activo.get("descripcion"));
					activoDevuelo.put("fechacompra", 				activo.get("fechacompra"));
					activoDevuelo.put("precio", 					activo.get("precio"));
					
					
					Map<String, Object> objActivo  = new HashMap();
					
					objActivo.put("codigo",							activo.get("regId"));
					objActivo.put("descripcion",					activo.get("regDes"));
					objActivo.put("fecha_adq",						activo.get("fechacompra").toString());
					objActivo.put("costo_historico",				Float.parseFloat(activo.get("precio").toString()));

					float costo_historico = Float.parseFloat(activo.get("precio").toString());
					float costo_actual ;
					float dep_acu_inicial ;
					float factor_actualizado ;
					float act_gestion ;
					float cost_actualizado ;
					float porc_dep_anual ;
					float dep_gestion ;
					float act_dep_acum ;
					float dep_acum_total ;
					float valor_neto ;
					
					if(entro) {						
						
						objActivo.put("costo_actual",				Float.parseFloat(datos.get("valorActivo").toString()));
						objActivo.put("dep_acu_inicial",			Float.parseFloat(datos.get("depreciacionAcumuladaIni").toString()));
						objActivo.put("factor_actualizado",			datos.get("actualizacion").toString());
						objActivo.put("act_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));						
						objActivo.put("cost_actualizado",			Float.parseFloat(datos.get("valorActualizado").toString()));
						objActivo.put("porc_dep_anual",				datos.get("Depre").toString());
						objActivo.put("dep_gestion",				Float.parseFloat(datos.get("deprePeriodo").toString()));
						objActivo.put("act_dep_acum",				Float.parseFloat(datos.get("actualzaiconDepre").toString()));
						objActivo.put("dep_acum_total",				Float.parseFloat(datos.get("depreciacionAcumulada").toString()));						
						objActivo.put("valor_neto",					Float.parseFloat(datos.get("valorResidual").toString()));	
						
						
						costo_actual 			= Float.parseFloat(datos.get("valorActivo").toString());
						dep_acu_inicial 		= Float.parseFloat(datos.get("depreciacionAcumuladaIni").toString());
						act_gestion 			= Float.parseFloat(datos.get("deprePeriodo").toString());
						cost_actualizado 		= Float.parseFloat(datos.get("valorActualizado").toString());
						dep_gestion 			= Float.parseFloat(datos.get("deprePeriodo").toString());
						act_dep_acum 			= Float.parseFloat(datos.get("actualzaiconDepre").toString());
						dep_acum_total 			= Float.parseFloat(datos.get("depreciacionAcumulada").toString());
						valor_neto 				= Float.parseFloat(datos.get("valorResidual").toString());			
						
					}else {
						Depre 				= (float) Math.round(Float.parseFloat(activo.get("porcentaje_depreciacion").toString())*12);
						
						objActivo.put("costo_actual",				(activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()) : 0);
						objActivo.put("dep_acu_inicial",			(activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0);
						objActivo.put("factor_actualizado",			(activo.get("valactualizado") != null)? activo.get("valactualizado").toString(): "");					
						objActivo.put("act_gestion",				(activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0);					
						objActivo.put("cost_actualizado",			(activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()): 0);
						objActivo.put("porc_dep_anual",				Depre.toString());
						objActivo.put("dep_gestion",				(activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0);
						objActivo.put("act_dep_acum",				(activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0);
						objActivo.put("dep_acum_total",				(activo.get("depacumulada") != null)? Float.parseFloat(activo.get("depacumulada").toString()): 0);
						objActivo.put("valor_neto",					(activo.get("valpresente") != null)? Float.parseFloat(activo.get("valpresente").toString()): 0);

						costo_actual 		= (activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()) : 0;
						dep_acu_inicial 	= (activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0;
						act_gestion 		= (activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0;				
						cost_actualizado 	= (activo.get("valactualizado") != null)? Float.parseFloat(activo.get("valactualizado").toString()): 0;
						dep_gestion 		= (activo.get("dep_gestion") != null)? Float.parseFloat(activo.get("dep_gestion").toString()): 0;
						act_dep_acum 		= (activo.get("act_dep_acumulado") != null)? Float.parseFloat(activo.get("act_dep_acumulado").toString()): 0;
						dep_acum_total 		= (activo.get("depacumulada") != null)? Float.parseFloat(activo.get("depacumulada").toString()): 0;
						valor_neto 			= (activo.get("valpresente") != null)? Float.parseFloat(activo.get("valpresente").toString()): 0;
					}				
					ArrayProv.add(activoDevuelo);	
					// listadoActivosReporte.add(objActivo);		
					contador++;

					// AQUI SE SUMA LA DEPRE DE LOS ACTIVOS DE UN REGIMEN 
					sumaTotalCostoHistorico			= sumaTotalCostoHistorico 		+ costo_historico;
					sumaTotalCostoActualizado		= sumaTotalCostoActualizado 	+ costo_actual;	
					sumaTotalDepAcumTotalGrupo		= sumaTotalDepAcumTotalGrupo 	+ dep_acu_inicial;	
					sumaTotalVAlorNetoIni			= sumaTotalVAlorNetoIni 		+ 0;
					sumaTotalActGestion			 	= sumaTotalActGestion 			+ act_gestion;
					sumaTotalCostoTotActualizado	= sumaTotalCostoTotActualizado 	+ cost_actualizado;		
					sumaTotalDepGestion			 	= sumaTotalDepGestion 			+ dep_gestion;
					sumaTotalActDepAcum			 	= sumaTotalActDepAcum 			+ act_dep_acum;
					sumaTotalDepAcum			 	= sumaTotalDepAcum 				+ dep_acum_total;
					sumaTotalValorNeto			 	= sumaTotalValorNeto 			+ valor_neto;
				}

				Map<String, Object> objActivoSuma  = new HashMap();
					
				objActivoSuma.put("codigo",							valGrupo);
				objActivoSuma.put("descripcion",					nomGrupo);
				objActivoSuma.put("fecha_adq",						"1");

				objActivoSuma.put("costo_historico",				sumaTotalCostoHistorico);
				objActivoSuma.put("costo_actual",					sumaTotalCostoActualizado);
				objActivoSuma.put("dep_acu_inicial",				sumaTotalDepAcumTotalGrupo);
				objActivoSuma.put("factor_actualizado",				"");
				objActivoSuma.put("act_gestion",					sumaTotalActGestion);						
				objActivoSuma.put("cost_actualizado",				sumaTotalCostoTotActualizado);
				objActivoSuma.put("porc_dep_anual",					"");
				objActivoSuma.put("dep_gestion",					sumaTotalDepGestion);
				objActivoSuma.put("act_dep_acum",					sumaTotalActDepAcum);
				objActivoSuma.put("dep_acum_total",					sumaTotalDepAcum);						
				objActivoSuma.put("valor_neto",						sumaTotalValorNeto);
			
				listadoActivosReporte.add(objActivoSuma);
			}
			
		} catch (JsonProcessingException e) {
		     e.printStackTrace();
		 }	
		
		/*===========	AQUI TERMINA EL REPORTE CHEEEE	=========*/
		return ResponseEntity.ok().headers(headers).body(exporToPdf(listadoActivosReporte, nombreArchivoReporte, datosAux));
	}


}
