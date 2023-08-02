package com.activo.fijos.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
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

import com.activo.fijos.models.services.IActivoService;
import com.activo.fijos.models.services.IGrupoService;
import com.activo.fijos.models.services.ISubGrupoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
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
	
	/*
	@Autowired
	private AuthenticationManager authenticationManager; 
	*/
	//@Autowired
	//private JwtTokenUtil jwtTokenUtil; 
	
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
	
	// ******************* MIGRACIONES *******************
	@GetMapping("/migrationItemActivo")
	public void migracionActivo() {
		
		List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT * FROM af_item");
		
		System.out.println(activos.size());
		int contador = 0;		
		
		for (Map<String, Object> act : activos) {
			System.out.print("<= | "+contador+" | => "+"ID: " + act.get("cod")+" | Cog 1: " + act.get("cod1")+" | Descr: " + act.get("des")+" | Grupo: " + act.get("codgrupo")+"\n");
			
			String codSubGrupo = act.get("codgrupo").toString();
			
			
			//String sql = "INSERT INTO afw_activo (idgrupo, descripcion, nro_items , vida_util) VALUES(?,?,?,?)";
			String sql = "INSERT INTO afw_activo ( "
					+ "idactivo ,"
					+ "codigo ,"
					+ "codigoalterno ,"
					+ "descripcion ,"
					+ "eficiencia ,"
					+ "estado ,"
					+ "estadoregistro ,"
					+ "fecha ,"
					+ "fechacompra ,"
					+ "fechacreacion ,"
					+ "fechamodificacion ,"
					+ "formainicial ,"
					+ "placa ,"
					+ "porcentaje_depreciacion ,"
					+ "precio ,"
					+ "ufv ,"
					+ "ufvcompra ,"
					+ "vida_util ,"
					+ "grupo_id ,"
					+ "incorporacion_id ,"
					+ "regimen_id ,"
					+ "regional_id ,"
					+ "subgrupo_id ,"
					+ "tipotransaccion_id ,"
					+ "ubicacionespecifica_id ,"
					+ "unidadmanejo_id ,"
					+ "codprovedor ,"
					+ "factura ,"
					+ "nuevocodigo ,"
					+ "depacumulada ,"
					+ "fechafin ,"
					+ "fechaini ,"
					+ "valactualizado ,"
					+ "valpresente ,"
					+ "vidautilres ,"
					+ "estado_vigencia ,"
					+ "estadoactivo ,"
					+ "fechabaja "
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //38

					String idactivo 		= (act.get("cod") != null) ? act.get("cod").toString().trim() : null;
					String codigo 			= (act.get("cod1") != null) ? act.get("cod1").toString().trim() : null;
					String codigoalterno 	= (act.get("cod2") != null) ? act.get("cod2").toString().trim() : null;
					String descripcion 		= (act.get("des1") != null) ? act.get("des1").toString().trim() : null;
					String eficiencia 		= (act.get("refe1") != null) ? act.get("refe1").toString().trim() : null;
					String subgrupo_id 		= act.get("codgrupo").toString().trim()+act.get("codsubgrp").toString().trim();
					String estado 			= (act.get("estado") != null) ? act.get("estado").toString().trim() : null;
					String estadoregistro 	= (act.get("registro") != null) ? act.get("registro").toString().trim() : null;
					String formainicial 	= (act.get("num1") != null) ? act.get("num1").toString().trim() : null;

					Integer estadoactivo;

					if(act.get("num2") != null){
						 estadoactivo 	= (int) Double.parseDouble(act.get("num2").toString());
						//estadoactivo = null;
					}else{
						estadoactivo = null;
					}

					String precio 			= (act.get("valcomprabs") != null) ? act.get("valcomprabs").toString().trim() : null;
					String vida_util 		= (act.get("vidautil") != null) ? act.get("vidautil").toString().trim() : null;
					String grupo_id 		= (act.get("codgrupo") != null) ? act.get("codgrupo").toString().trim() : null;
					String regimen_id 		= (act.get("refe2") != null) ? act.get("refe2").toString().trim() : null;
					String regional_id 		= (act.get("codregion") != null) ? act.get("codregion").toString().trim() : null;
					String unidadmanejo_id	= (act.get("codumanejo") != null) ? act.get("codumanejo").toString().trim() : null;
					
					
					Map<String, Object> ulMov = getUltimoMovActivo(idactivo);
					String estado_vigencia = "";
					
					if(!ulMov.isEmpty()){
						if(ulMov.get("estado") != null)
							estado_vigencia = ulMov.get("estado").toString().trim();
						else
							estado_vigencia = null;
					}
					else{
						estado_vigencia = null;
					}
						 
					jdbcTemplate.update(sql,
							idactivo,   				//idactivo
							codigo,   					//codigo
							codigoalterno ,  			//codigoalterno
							descripcion,  				//descripcion
							eficiencia,  				//eficiencia
							estado,  					//estado
							estadoregistro,  			//estadoregistro
							new Date(),  				//fecha
							act.get("fcompra"),			//fechacompra
							new Date(),  				//fechacreacion
							new Date(),  				//fechamodificacion
							formainicial,  				//formainicial
							null,  						//placa
							act.get("porcdepre"),  		//porcentaje_depreciacion
							precio,  					//precio
							0,  						//ufv
							act.get("tccompra"),  		//ufvcompra
							vida_util,  				//vida_util
							grupo_id,  					//grupo_id
							null,  						//incorporacion_id
							regimen_id,  				//regimen_id
							regional_id,  				//regional_id
							subgrupo_id,	  			//subgrupo_id
							null,  						//tipotransaccion_id
							null,  						//ubicacionespecifica_id
							unidadmanejo_id,  			//unidadmanejo_id
							null,						//codprovedor
							null,						//factura
							null,						//nuevocodigo
							act.get("depacumulada"),	//depacumulada
							act.get("fecha2"),			//fechafin
							act.get("fecha1"),			//fechaini
							act.get("valactualizado"),  //valactualizado
							act.get("valpresente"),		//valpresente
							act.get("vidautilr"),		//vidautilres
							estado_vigencia,			//estado_vigencia
							estadoactivo,				//estadoactivo
							act.get("fbaja")			//estadoactivo
							);//38
			





			
			
			/*
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
			*/
			contador++;
			
			// if(contador > 5) 
			// 	break;	
		
		}
		
		
	}

	@GetMapping("/migracionActivoMov")
	public void migracionActivoMov() {
		
		List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT cod FROM af_itemmov GROUP BY cod");
		
		// System.out.println(activos.size());
		int contador = 0;		
		
		for (Map<String, Object> act : activos) {

			System.out.println("-------- < "+contador+" > NUEVO ACTIVO --------");


			List<Map<String, Object>> movActivo = jdbcTemplate.queryForList("SELECT * FROM af_itemmov WHERE cod = ? ORDER BY fecha ASC", act.get("cod"));
			// List<Map<String, Object>> movActivo = jdbcTemplate.queryForList("SELECT * FROM af_itemmov WHERE cod = '10-0003325' ORDER BY fecha ASC");
			int connuevoMov = 10000;

			for(Map<String, Object> actMov : movActivo){
				System.out.println(actMov.get("cod")+" <--> "+connuevoMov);
				String sqlModMov = "UPDATE af_itemmov SET dr2 = ? WHERE cod = ? AND dr = ? AND fecha = ?";
				jdbcTemplate.update(sqlModMov, connuevoMov, actMov.get("cod"), actMov.get("dr"), actMov.get("fecha"));
				connuevoMov = connuevoMov + 10000;
			}

			// if(contador > 5)
			// 	break;
				
			contador++;
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
		    jdbcTemplate.update(sql, grup.get("cod").toString().trim(), grup.get("des").toString(), 0 , Integer.parseInt(grup.get("num1").toString()));
						
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
		
		int contador = 0;		
		
		for (Map<String, Object> subGrupo : subgrupos) {
			
			System.out.print("<= | "+contador+" | => "+"ID: " + subGrupo.get("cod")+" | Cod Grupo: " + subGrupo.get("codgrupo")+" | Descr: " + subGrupo.get("des")+"\n");
			
			String sql = "INSERT INTO afw_subgrupo (idsubgrupo, cod, descripcion, grupo_id) VALUES(?,?,?,?)";
		    jdbcTemplate.update(sql, (subGrupo.get("codgrupo").toString().trim()+subGrupo.get("cod").toString().trim()),subGrupo.get("cod").toString().trim(), subGrupo.get("des").toString(), subGrupo.get("codgrupo").toString().trim());
			
			contador++;
		}
		
	}
	
	@GetMapping("/migrationSaldos")
	public void migraSaldosActivos() {		
		System.out.println("-----------| NEUVO DEPREEE |--------");		
		List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT * FROM afw_activo WHERE codigo = 'COS-10-35950'");
		//List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT * FROM afw_activo");
		//List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT * FROM afw_activo order by fechacompra desc");
		//List<Map<String, Object>> activos = jdbcTemplate.queryForList("SELECT * FROM afw_activo WHERE left(fechacompra,4) <= '2022' AND porcentaje_depreciacion != 0  order by fechacompra desc");
		int contador = 0;		
		for (Map<String, Object> activo	 : activos) {
			
			System.out.print("<= | "+contador+" | => "+"ID: " + activo.get("idactivo")+" | Cod Activo: " + activo.get("codigo")+"\n");
			
			String fechaCompra 			= activo.get("fechacompra").toString();
			LocalDate fechaIni			= LocalDate.parse(fechaCompra, DateTimeFormatter.ofPattern("yyyy-MM-dd"));			
			LocalDate fechaFinDeAnio 	= LocalDate.of(fechaIni.getYear(), 12, 31);	
			long mesesFaltantes 		= ChronoUnit.MONTHS.between(fechaIni, fechaFinDeAnio);
			mesesFaltantes++;	        
	        
	        Float Depre 				= (float) Math.round(Float.parseFloat(activo.get("porcentaje_depreciacion").toString())*12);
	        
	        int cantidadBucles;
	        if(mesesFaltantes < 10)
	        	cantidadBucles 			=  (100 / (int) Math.floor(Depre))+1;
        	else
        		cantidadBucles 			=  (100 / (int) Math.floor(Depre));
	        
	        System.out.println(cantidadBucles);         
        	
        	//------------ PARA LA ACTUALIZACION ------------
        	int  gestion 				=  fechaFinDeAnio.getYear();
        	Float valorActivo 			= Float.parseFloat(activo.get("precio").toString());
        	float ufvAdquiridoIni 		= 0;
			float ufvAdquiridoFin 		= 0;
			float actualizacion 		= 0;
			float valorActualizado  	= 0;
			LocalDate fechaFinDeAnioSigueinteIni 	= fechaIni;
			Map<String, Object> ufvI = new HashMap();
			
			String sql = "select * from tcambio1 where fecha = ?";
			List<Map<String, Object>>  ArrayobjUfvIni = jdbcTemplate.queryForList(sql, fechaFinDeAnioSigueinteIni);
			if(ArrayobjUfvIni.size() > 0){
				ufvI = ArrayobjUfvIni.get(0);
				ufvAdquiridoIni = Float.parseFloat(ufvI.get("tc").toString());
			}
			
			//------------PARA LA DEPRECIACION------------
			float depreAcumulado 		= 0;
			float depreActualizacion 	= 0;
			float porcentajeDepre		= Depre/100;
			float depreEjerPerio        = 0;
			Long  cantMesD 				= (long) 0;
			float totalDepre	        = 0;
			float valorResidual 		= 0;
			
			for (int i = 1; i<=cantidadBucles ; i++) {
        		
        		Map<String, Object> datosdepre 	= new HashMap();	        		
        			        		
        		LocalDate fechaFinDeAnioSigueinteFin 	= LocalDate.of(gestion, 12, 31);	        		
        		      
        		List<Map<String, Object>>  ArrayobjUfvFin;
				sql 			= "select * from tcambio1 where fecha = ?";				
				
        		if(i == cantidadBucles) { 
        			LocalDate fechaFin;
        			if(fechaIni.getMonthValue() == 1) {
        				fechaFin 			= LocalDate.of(gestion, 12, 31);
        				fechaFinDeAnioSigueinteFin 	= LocalDate.of(gestion, 12, 31);
        			}else {
        				fechaFin 			= LocalDate.of(gestion, fechaIni.getMonth(), fechaIni.getDayOfMonth());
        				fechaFinDeAnioSigueinteFin 	= LocalDate.of(gestion, fechaFin.getMonthValue() - 1, fechaFin.getDayOfMonth());
        			}           			
        			ArrayobjUfvFin 				= jdbcTemplate.queryForList(sql, fechaFin);
    			}else {
        			ArrayobjUfvFin 				= jdbcTemplate.queryForList(sql, fechaFinDeAnioSigueinteFin);
        		}        			
				
				Map<String, Object> ufvF = new HashMap();
				
				if(ArrayobjUfvFin.size() > 0) {
					ufvF 				= ArrayobjUfvFin.get(0);	
					ufvAdquiridoFin 	= Float.parseFloat(ufvF.get("tc").toString());
					actualizacion 		= ((valorActivo * ufvAdquiridoFin)/ufvAdquiridoIni)-valorActivo;
					valorActualizado 	= valorActivo + actualizacion;						
					
					//********* PARA LA DEPRECIACION********* 
					cantMesD    		= ((Period.between(fechaFinDeAnioSigueinteIni, fechaFinDeAnioSigueinteFin)).toTotalMonths()) + 1;
					depreEjerPerio 		= ((valorActualizado*porcentajeDepre)/12)*cantMesD;
					depreActualizacion	= ((depreAcumulado*ufvAdquiridoFin)/ufvAdquiridoIni)-depreAcumulado;
					totalDepre			= depreActualizacion + depreAcumulado + depreEjerPerio; 
					valorResidual		= Math.abs(valorActualizado - totalDepre);
					
					double tolerancia = 1E-3; // Puedes ajustar la tolerancia según tus necesidades

					if (Math.abs(valorResidual) <= tolerancia)
					    valorResidual = 0;
										
					System.out.println(	i+" | "+
							gestion+" | "+
							fechaFinDeAnioSigueinteIni+" | "+
							ufvAdquiridoIni+" | "+
							ufvAdquiridoFin+" | "+
							valorActivo +" | "+
							actualizacion +" | "+
							valorActualizado+" |<>| "+
							depreAcumulado+" | "+
							depreActualizacion+" | "+
							Depre+" | " +
							depreEjerPerio+" | "+
							cantMesD+" | "+
							totalDepre +" | "+
							valorResidual+" | "+
							fechaFinDeAnioSigueinteFin
							);					
					
					//********* PARA LA ACTUALIZACION ********* 
					ufvAdquiridoIni 	= ufvAdquiridoFin;
					valorActivo 		= valorActualizado;
					//********* PARA LA DEPRECIACION********* 
					depreAcumulado 		= totalDepre;					
					
				}   else {
					System.out.println(i);
				}
				
        		gestion++;
        		
        		fechaFinDeAnioSigueinteIni 	= LocalDate.of(gestion, 01, 01);
        	}
			
			
			
	        
	        
	        //System.out.println("<= | fecha ini "+fechaIni+" | => "+"fin de gestion: " + fechaFinDeAnio+" | Meses Faltantes: "+fechaIni.getMonthValue()+" | " + (mesesFaltantes) +" otro " +"\n\n");
			System.out.println("\n\n");
			
			contador++;
			
			if(contador > 100)
				break;
			
			/*
			String sql = "INSERT INTO afw_subgrupo (idsubgrupo, cod, descripcion, grupo_id) VALUES(?,?,?,?)";
		    jdbcTemplate.update(sql, (subGrupo.get("codgrupo").toString().trim()+subGrupo.get("cod").toString().trim()),subGrupo.get("cod").toString().trim(), subGrupo.get("des").toString(), subGrupo.get("codgrupo").toString().trim());
			
			contador++;
			*/
		}
		
		
		/*
		LocalDate fechaFinDeAnio = LocalDate.of(fechaIni.getYear(), 12, 31);
        long mesesFaltantes = ChronoUnit.MONTHS.between(fechaIni, fechaFinDeAnio);
        
        Map<String, Object> gestiones 	= new HashMap();
        int cantidadBucles;
        if(mesesFaltantes < 12) {
        	cantidadBucles =  (100 / (int) Math.floor(Depre))+1; 
        	
        	//------------ PARA LA ACTUALIZACION ------------
        	int  gestion 			=  fechaFinDeAnio.getYear();
        	Float valorActivo 		= activoDepre.getPrecio();
        	float ufvAdquiridoIni 	= 0;
			float ufvAdquiridoFin 	= 0;
			float actualizacion 	= 0;
			float valorActualizado  = 0;
			LocalDate fechaFinDeAnioSigueinteIni 	= fechaIni;
			Map<String, Object> ufvI = new HashMap();
			
			String sql = "select * from tcambio1 where fecha = ?";
			List<Map<String, Object>>  ArrayobjUfvIni = jdbcTemplate.queryForList(sql, fechaFinDeAnioSigueinteIni);
			if(ArrayobjUfvIni.size() > 0){
				ufvI = ArrayobjUfvIni.get(0);
				ufvAdquiridoIni = Float.parseFloat(ufvI.get("tc").toString());
			}

			//------------PARA LA DEPRECIACION------------
			float depreAcumulado 		= 0;
			float depreActualizacion 	= 0;
			float porcentajeDepre		= Depre/100;
			float depreEjerPerio        = 0;
			Long  cantMesD 				= (long) 0;
			float totalDepre	        = 0;
			float valorResidual 		= 0;
			
        	for (int i = 1; i<=cantidadBucles ; i++) {
        	        		
        		Map<String, Object> datosdepre 	= new HashMap();	        		
        			        		
        		LocalDate fechaFinDeAnioSigueinteFin 	= LocalDate.of(gestion, 12, 31);	        		
        		      
        		List<Map<String, Object>>  ArrayobjUfvFin;
				sql 			= "select * from tcambio1 where fecha = ?";
				
        		if(i == cantidadBucles) {
        			ArrayobjUfvFin 				= jdbcTemplate.queryForList(sql, fechaFin);
        			fechaFinDeAnioSigueinteFin 	= LocalDate.of(gestion, fechaFin.getMonthValue() - 1, fechaFin.getDayOfMonth());
        		}else {
        			ArrayobjUfvFin 				= jdbcTemplate.queryForList(sql, fechaFinDeAnioSigueinteFin);
        		}	
				
				Map<String, Object> ufvF = new HashMap();
				//System.out.println(fechaFinDeAnioSigueinteIni+" | "+fechaFinDeAnioSigueinteFin+ " | "+ArrayobjUfvIni+" | "+ArrayobjUfvFin+" | ");
								
				if(ArrayobjUfvFin.size() > 0) {
					ufvF 				= ArrayobjUfvFin.get(0);	
					ufvAdquiridoFin 	= Float.parseFloat(ufvF.get("tc").toString());
					actualizacion 		= ((valorActivo * ufvAdquiridoFin)/ufvAdquiridoIni)-valorActivo;
					valorActualizado 	= valorActivo + actualizacion;						
					
					//********* PARA LA DEPRECIACION********* 
					cantMesD    		= ((Period.between(fechaFinDeAnioSigueinteIni, fechaFinDeAnioSigueinteFin)).toTotalMonths()) + 1;
					depreEjerPerio 		= ((valorActualizado*porcentajeDepre)/12)*cantMesD;
					depreActualizacion	= ((depreAcumulado*ufvAdquiridoFin)/ufvAdquiridoIni)-depreAcumulado;
					totalDepre			= depreActualizacion + depreAcumulado + depreEjerPerio; 
					valorResidual		= valorActualizado - totalDepre;
					
					datosdepre.put("gestion", gestion);
					datosdepre.put("ufv_Adq", ufvAdquiridoIni);
	        		datosdepre.put("valor_activo", valorActivo);
	        		datosdepre.put("actualizacion", actualizacion);
	        		datosdepre.put("valor_actualizado", valorActualizado);
	        		datosdepre.put("depre_acumulado", depreAcumulado);
	        		datosdepre.put("depre_actualizacion", depreActualizacion);
	        		datosdepre.put("depreciacion", Depre);
	        		datosdepre.put("depreciacion_periodo", depreEjerPerio);
	        		datosdepre.put("cant_meses", cantMesD);
	        		datosdepre.put("total_depre", totalDepre);
	        		datosdepre.put("valor_residual", valorResidual);
	        		
					
					
					System.out.println(	i+" | "+
							gestion+" | "+
							fechaFinDeAnioSigueinteIni+" | "+
							ufvAdquiridoIni+" | "+
							ufvAdquiridoFin+" | "+
							valorActivo +" | "+
							actualizacion +" | "+
							valorActualizado+" |<>| "+
							depreAcumulado+" | "+
							depreActualizacion+" | "+
							Depre+" | " +
							depreEjerPerio+" | "+
							cantMesD+" | "+
							totalDepre +" | "+
							valorResidual+" | "+
							fechaFinDeAnioSigueinteFin
							);
					
					
					
					//********* PARA LA ACTUALIZACION ********* 
					ufvAdquiridoIni 	= ufvAdquiridoFin;
					valorActivo 		= valorActualizado;
					//********* PARA LA DEPRECIACION********* 
					depreAcumulado 		= totalDepre;
					
					
				}        		
        		
        		//gestiones.put(gestion+"", gestion);
        		gestiones.put(i-1+"", datosdepre);
        		gestion++;
        		
        		fechaFinDeAnioSigueinteIni 	= LocalDate.of(gestion, 01, 01);
        	}
        }else {
        	
        }
        */
		
	}
	// ******************* END MIGRACIONES *******************

	
	// ******************* CUENTAS *******************
	@GetMapping("getCuentaPartidaByIdGrupo/{idgrupo}")
	public Map<String, Object> getCuentaPartidaByIdGrupo(@PathVariable String idgrupo) {
		
		String sql = "select afw.cuenta_id, e.des as des1, e.cod1, pa.des as des2, afw.idgrupo"
						+ " from afw_grupo afw inner join e1 e"
						+ "	on afw.cuenta_id = e.cod inner join e1 pa"
						+ "		on pa.cod = e.cod1"
						+ " WHERE afw.idgrupo = ?";
		
		System.out.println(sql);
		
		List<Map<String, Object>>  Arrayobj = jdbcTemplate.queryForList(sql, idgrupo);
		
		Map<String, Object> obj = new HashMap();
				
		if(Arrayobj.size() > 0) 
			obj = Arrayobj.get(0);
		
		return obj;
	}
	
	@GetMapping("getCuentas/")
	public Map<String, Object> getCuentas(@PathVariable String idgrupo) {
		
		String sql = "select afw.cuenta_id, e.des as des1, e.cod1, pa.des as des2, afw.idgrupo"
						+ " from afw_grupo afw inner join e1 e"
						+ "	on afw.cuenta_id = e.cod inner join e1 pa"
						+ "		on pa.cod = e.cod1"
						+ " WHERE afw.idgrupo = ?";
		
		System.out.println(sql);
		
		List<Map<String, Object>>  Arrayobj = jdbcTemplate.queryForList(sql, idgrupo);
		
		Map<String, Object> obj = new HashMap();
				
		if(Arrayobj.size() > 0) 
			obj = Arrayobj.get(0);
		
		return obj;
	}
	// ******************* END CUENTAS *******************

	
	// ******************* PROVEDORES *******************
	@GetMapping("/getProvedores")
	public List<Map<String, Object>> getProvedores(){		
		String sql = "select cod, des, dir, tel from a_prov ORDER BY REPLACE(REPLACE(des, ' ', ''), '\"', '')";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql);		
		return ArrayProv;
	}
	
	@GetMapping("/getProvedor/{codprovedor}")
	public Map<String, Object> getProvedor(@PathVariable String codprovedor){
		
		String sql = "select *  from a_prov where cod = ?";
		
		List<Map<String, Object>>  Arrayobj = jdbcTemplate.queryForList(sql, codprovedor);
		
		Map<String, Object> obj = new HashMap();
				
		if(Arrayobj.size() > 0) 
			obj = Arrayobj.get(0);
		
		return obj;
	}

	@GetMapping("/getProvedoresTodo")
	public List<Map<String, Object>> getProvedoresTodo(){		
		String sql = "select cod, des, dir, tel, fax, email from a_prov";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql);		
		return ArrayProv;
	}

	@PostMapping("/guardarProvedor")
	public Map<String, Object> guardarProvedor(@RequestBody String json){
		
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> obj = new HashMap();
		
		try {	
		 	Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			String provedor 	= jsonMap.get("provedor").toString();
			String cod			= jsonMap.get("nit").toString();
			String cod1		 	= ""; 
			String des		 	= jsonMap.get("nombre").toString(); 
			String estado		= "";
			String tipo		 	= "";
			String dir		 	= jsonMap.get("dirceccion").toString();
			String tel		 	= jsonMap.get("telefono").toString();
			String fax		 	= jsonMap.get("fax").toString();
			String email		= jsonMap.get("email").toString();
			String casilla		= "";
			
			//verificamos si ya existe el nit
			String sqlv = "SELECT * FROM a_prov WHERE cod = ?";
			List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sqlv, cod);
			
			System.out.println(ArrayProv.size());
			if(ArrayProv.size() > 0) {
				obj.put("estado", "error_cod");
			}else {
						
				if(provedor.equals("0")){
	
					String sql = "INSERT INTO a_prov ( "
												+"cod ,"
												+"cod1 ,"
												+"des ,"
												+"estado ,"
												+"tipo ,"
												+"dir ,"
												+"tel ,"
												+"fax ,"
												+"email ,"
												+"casilla "
											+ ") VALUES(?,?,?,?,?,?,?,?,?,?)"; //31
	
					jdbcTemplate.update(sql,
										cod,			//cod        	
										cod1,			//cod1        	
										des,			//des	
										estado,			//estado 	  	
										tipo,			//tipo 	  	
										dir,			//dir 	      	
										tel,			//tel 	      	
										fax,			//fax 	      	
										email,			//email 	      	
										casilla 	  	//casilla
										);//10
	
				}else{
					String sql = "UPDATE a_prov SET "
												+"des = ?, "
												+"dir = ?, "
												+"tel = ?, "
												+"fax = ?, "
												+"email = ? "
												+"WHERE cod = ?";
					jdbcTemplate.update(sql, 
										des,	//des
										dir,	//dir
										tel,	//tel
										fax,	//fax
										email,  //email
										cod); //cod
				}
				obj.put("estado", "success");
				obj.put("cod", cod);
			}			
			
		 } catch (JsonProcessingException e) {
		     // Handle the exception
		     e.printStackTrace();
		 	obj.put("estado", "error");
		 }
		
		return obj;
	}
	// ******************* END PROVEDORES *******************
	
	
	// ******************* MOVIMIENTOS *******************
	@GetMapping("/listaMovimientosActivoById/{idactivo}")
	public List<Map<String, Object>> listaMovimientosActivoById(@PathVariable String idactivo){
		
		String sql = "SELECT afm.fecha, p.des, p.des1, p.des2, ubi.des as ubiAct, ubig.des as ubicG, afm.estado "
					+ "FROM af_itemmov afm INNER JOIN persona p "
					+ "ON afm.ci = p.ci INNER JOIN af_ubicesp ubi "
					+ "ON afm.codubic = ubi.cod INNER JOIN af_ubicgral ubig "
					+ "ON ubi.codubicgral = ubig.codubicgral "
					+ "WHERE afm.cod = ? AND ubi.codregion = ubig.codregion "
					+ "ORDER BY afm.dr DESC";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql, idactivo);		
		return ArrayProv;
		
	}
	
	@GetMapping("/getUltimoMovActivo/{idactivo}")
	public Map<String, Object> getUltimoMovActivo(@PathVariable String idactivo){
		
		String sql = "SELECT *  "
					+ "FROM af_itemmov afm "
					+ "WHERE afm.cod = ? "
					+ "ORDER BY afm.dr DESC ";
		
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql, idactivo);	
		
		Map<String, Object> obj = new HashMap();
		
		if(ArrayProv.size() > 0) 
			obj = ArrayProv.get(0);
		
		return obj;
	}

	@PostMapping("/guardaLiberacionActivo")
	public Map<String, Object> guardaLiberacionActivo(@RequestBody String json) {
		
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> obj = new HashMap();
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			
			System.out.println("==========");
			System.out.println(jsonMap);
			System.out.println("==========");
			/*
			System.out.println("fecha		 => "+jsonMap.get("fecha"));
			System.out.println("cargo		 => "+jsonMap.get("cargo"));	
			System.out.println("reparticion	 => "+jsonMap.get("reparticion"));
			System.out.println("ubicacion	 => "+jsonMap.get("ubicacion"));
			System.out.println("descgeneral	 => "+jsonMap.get("descgeneral"));
			System.out.println("destino		 => "+jsonMap.get("destino"));
			System.out.println("observacion	 => "+jsonMap.get("observacion"));
			System.out.println("nombre		 => "+jsonMap.get("nombre"));
			System.out.println("cedula		 => "+jsonMap.get("cedula"));
			System.out.println("activo		 => "+jsonMap.get("activo"));
			System.out.println("ultimoMov	 => "+jsonMap.get("ultimoMov"));
			*/
		
			// PARA EL JSON DE ULTIMO ACTIVO
			Map<String, Object> jsonUltMov = objectMapper.readValue(jsonMap.get("ultimoMov").toString(), Map.class);
			System.out.println("V2 => "+jsonUltMov.get("dr"));
			
			String sql = "INSERT INTO af_itemmov ( "
						+"cod ,"
						+"dr ,"
			            +"fecha ,"
			            +"codcargoresp ,"
			            +"codrepart ,"
			            +"codubic ,"
			            +"refe1 ,"
			            +"refe2 ,"
			            +"refe3 ,"
			            +"refe4 ,"
			            +"codant ,"
			            +"codnue ,"
			            +"plaant ,"
			            +"planue ,"
			            +"i1 ,"
			            +"i2 ,"
			            +"i3 ,"
			            +"i4 ,"
			            +"usuarioi ,"
			            +"fechai ,"
			            +"usuariou ,"
			            +"fechau ,"
			            +"registro ,"
			            +"estado ,"
			            +"ci ,"
			            +"refe1a ,"
			            +"tipotr ,"
			            +"codregori ,"
			            +"codregdes ,"
			            +"dr1 ,"
			            +"dr2 "
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //31

					String cod 			= jsonMap.get("activo").toString();
					int dr;
					if(jsonUltMov.get("dr") != null) {
						dr 				= Integer.parseInt(jsonUltMov.get("dr").toString()) + 10000;
					}else {
						dr = 10000;
					}
					//dr 				= Integer.parseInt(jsonUltMov.get("dr").toString()) + 10000;
					String codcargoresp = jsonMap.get("cargo").toString(); 
					String codrepart 	= jsonMap.get("reparticion").toString();
					String codubic 		= jsonMap.get("ubicacion").toString();
					String refe1 		= jsonMap.get("descgeneral").toString();
					String refe2 		= jsonMap.get("nombre").toString();
					String refe3 		= jsonMap.get("destino").toString();
					String refe4 		= jsonMap.get("observacion").toString();
					String codant 		= null;
					String codnue 		= null;
					String plaant 		= null;
					String planue 		= null;
					String i1 			= "";
					String i2 			= "";
					String i3 			= "";
					String i4 			= "";
					String usuarioi 	= null;
					String fechai 		= null;
					String usuariou 	= null;
					String fechau 		= null;
					String registro 	= jsonMap.get("estadoregistro").toString();
					String estado 		= "";

					if(jsonMap.get("tipo").toString().equals("ASI")){
						obj.put("mensaje", "asignó");
						estado 		= "1";
					}
					else{
						obj.put("mensaje", "liberó");
						estado 		= "0";
					}

					String ci 			= jsonMap.get("cedula").toString();
					String refe1a 		= null;
					String tipotr 		= null;
					String codregori 	= null;
					String codregdes 	= null;
					String dr1 			= null;
					String dr2 			= null;


					/*
					System.out.println(cod);
					System.out.println(dr);
					System.out.println(codcargoresp);
					System.out.println(codrepart);
					System.out.println(codubic);
					System.out.println(refe1);
					System.out.println(refe2);
					System.out.println(refe3);
					System.out.println(refe4);
					System.out.println(codant);
					System.out.println(codnue);
					System.out.println(plaant);
					System.out.println(planue);
					System.out.println(i1);
					System.out.println(i2);
					System.out.println(i3);
					System.out.println(i4);
					System.out.println(usuarioi);
					System.out.println(fechai);
					System.out.println(usuariou);
					System.out.println(fechau);
					System.out.println(registro);
					System.out.println(estado);
					System.out.println(ci);
					System.out.println(refe1a);
					System.out.println(tipotr);
					System.out.println(codregori);
					System.out.println(codregdes);
					System.out.println(dr1);
					System.out.println(dr2);
					*/

					
					jdbcTemplate.update(sql,
										cod,         	// cod
							            dr,         	// dr
							            new Date(),		// fecha
							            codcargoresp, 	// codcargoresp
							            codrepart, 	    // codrepart
							            codubic, 	    // codubic
							            refe1, 	       	// refe1
							            refe2, 	       	// refe2
							            refe3, 	       	// refe3
							            refe4, 	       	// refe4
							            codant, 	    // codant
							            codnue, 	    // codnue
							            plaant, 	    // plaant
							            planue, 	    // planue
							            i1, 	       	// i1
							            i2, 	       	// i2
							            i3, 	       	// i3
							            i4, 	       	// i4
							            usuarioi, 	    // usuarioi
							            fechai, 	    // fechai
							            usuariou, 	    // usuariou
							            fechau, 	    // fechau
							            registro, 	    // registro
							            estado, 	    // estado
							            ci, 	       	// ci
							            refe1a, 	    // refe1a
							            tipotr, 	    // tipotr
							            codregori, 	    // codregori
							            codregdes, 	    // codregdes
							            dr1, 	       	// dr1
							            dr2  	       	// dr2
										);//31	
										
					
				//cambiamos al activo
					
				String sqla = "UPDATE afw_activo SET estado_vigencia = ? WHERE idactivo = ?";
				jdbcTemplate.update(sqla, estado, cod);
					
					
			obj.put("estado", "success");
			obj.put("cod", cod);
			
		} catch (JsonProcessingException e) {
		    // Handle the exception
		    e.printStackTrace();
			obj.put("estado", "error");
		}
		
		return obj;
		
	}
	
	@PostMapping("/guardaBajaActivo")
	public Map<String, Object> guardaBajaActivo(@RequestBody String json){
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> obj = new HashMap();
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
			
			
			System.out.println("==========");
			System.out.println(jsonMap);
			System.out.println("==========");	
		
			// PARA EL JSON DE ULTIMO ACTIVO
			Map<String, Object> jsonUltMov = objectMapper.readValue(jsonMap.get("ultimoMov").toString(), Map.class);
			System.out.println("V2 => "+jsonUltMov.get("dr"));
						
			String sql = "INSERT INTO af_itemmov ( "
						+"cod ,"
						+"dr ,"
			            +"fecha ,"
			            +"codcargoresp ,"
			            +"codrepart ,"
			            +"codubic ,"
			            +"refe1 ,"
			            +"refe2 ,"
			            +"refe3 ,"
			            +"refe4 ,"
			            +"codant ,"
			            +"codnue ,"
			            +"plaant ,"
			            +"planue ,"
			            +"i1 ,"
			            +"i2 ,"
			            +"i3 ,"
			            +"i4 ,"
			            +"usuarioi ,"
			            +"fechai ,"
			            +"usuariou ,"
			            +"fechau ,"
			            +"registro ,"
			            +"estado ,"
			            +"ci ,"
			            +"refe1a ,"
			            +"tipotr ,"
			            +"codregori ,"
			            +"codregdes ,"
			            +"dr1 ,"
			            +"dr2 "
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //31

					
					String cod 			= jsonMap.get("activo").toString();
					int dr;
					if(jsonUltMov.get("dr") != null) {
						dr 				= Integer.parseInt(jsonUltMov.get("dr").toString()) + 10000;
					}else {
						dr = 10000;
					}
					//dr 				= Integer.parseInt(jsonUltMov.get("dr").toString()) + 10000;
					//String codcargoresp = jsonMap.get("cargo").toString();
					
					String codcargoresp = "999"; 
					String codrepart 	= "";
					if(jsonUltMov.get("codrepart") != null){
						codrepart 	= jsonUltMov.get("codrepart").toString();
					}
										
					String codubic 		= "";
					if(jsonUltMov.get("codubic") != null) {
						codubic 		= jsonUltMov.get("codubic").toString();	
					}
					
					String refe1 		= jsonMap.get("docRespaldo").toString();
					String refe2 		= "";
					String refe3 		= "";
					String refe4 		= jsonMap.get("observacion").toString();
					String codant 		= jsonMap.get("codigo").toString();
					String codnue 		= null;
					String plaant 		= null;
					String planue 		= null;
					String i1 			= "B";
					String i2 			= "A";
					String i3 			= "B";
					String i4 			= "C";
					String usuarioi 	= null;
					String usuariou 	= null;
					String fechau 		= null;
					String registro 	= "";
					if(jsonUltMov.get("registro") != null) {
						registro 	= jsonUltMov.get("registro").toString();
					}
					String estado 		= null;		
					String ci 			= null;
					String refe1a 		= null;
					String tipotr 		= "BAJ";
					String codregori 	= null;
					String codregdes 	= null;
					String dr1 			= null;
					String dr2 			= null;
					
					
					System.out.println("v1 > "+jsonMap.get("activo"));
					System.out.println("v2 > "+jsonMap.get("dr"));
					System.out.println("v3 > "+jsonMap.get("reparticion"));
					System.out.println("v4 > "+jsonMap.get("ubicacion"));
					System.out.println("v5 > "+jsonMap.get("docRespaldo"));
					System.out.println("v6 > "+jsonMap.get("observacion"));
					System.out.println("v7 > "+jsonMap.get("codigo"));
					System.out.println("v8 > "+jsonMap.get("estadoregistro"));
					System.out.println("v9 > "+jsonMap.get("cedula"));
					
					
					jdbcTemplate.update(sql,
										cod,         	// cod
							            dr,         	// dr
							            new Date(),		// fecha
							            codcargoresp, 	// codcargoresp
							            codrepart, 	    // codrepart
							            codubic, 	    // codubic
							            refe1, 	       	// refe1
							            refe2, 	       	// refe2
							            refe3, 	       	// refe3
							            refe4, 	       	// refe4
							            codant, 	    // codant
							            codnue, 	    // codnue
							            plaant, 	    // plaant
							            planue, 	    // planue
							            i1, 	       	// i1
							            i2, 	       	// i2
							            i3, 	       	// i3
							            i4, 	       	// i4
							            usuarioi, 	    // usuarioi
							            new Date(),     // fechai
							            usuariou, 	    // usuariou
							            fechau, 	    // fechau
							            registro, 	    // registro
							            estado, 	    // estado
							            ci, 	       	// ci
							            refe1a, 	    // refe1a
							            tipotr, 	    // tipotr
							            codregori, 	    // codregori
							            codregdes, 	    // codregdes
							            dr1, 	       	// dr1
							            dr2  	       	// dr2
										);//31	
										
					
				//cambiamos al activo
					
				String sqla = "UPDATE afw_activo SET fechabaja = ?, estadoactivo = ?  WHERE idactivo = ?";
				jdbcTemplate.update(sqla, new Date(), 13, cod);					
					
			obj.put("estado", "success");
			obj.put("cod", cod);
						
			
		} catch (JsonProcessingException e) {
		    // Handle the exception
		    e.printStackTrace();
			obj.put("estado", "error");
		}
		
		return obj;
	}
	
	
	// ******************* END MOVIMIENTOS *******************

	
	// ******************* CARGOS *******************
	@GetMapping("/getCargos")
	public List<Map<String, Object>> getCargos(){		
		String sql = "select * from p_cargos ORDER BY des";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql);		
		return ArrayProv;
	}
	// ******************* END CARGOS *******************
	

	// ******************* UBICACION ESPECIFICA *******************
	@GetMapping("/getUbiEsp")
	public List<Map<String, Object>> getUbiEsp(){		
		String sql = "select * from af_ubicesp ORDER BY des";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql);		
		return ArrayProv;
	}
	// ******************* END UBICACION ESPECIFICA *******************


	// ******************* PERSONAS *******************
	@GetMapping("/getPersonaByCi/{cipersona}")
	public Map<String, Object> getPersonaByCi(@PathVariable String cipersona){		
		String sql = "select * from persona WHERE ci = ?";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql, cipersona);		
		
		Map<String, Object> obj = new HashMap();
		
		if(ArrayProv.size() > 0) 
			obj = ArrayProv.get(0);
		
		return obj;
	}
	
	@PostMapping("/getPersonasNombre")
	public List<Map<String, Object>> getPersonasNombre(@RequestBody String nombre){	
		
		
		
		String[] palabras = nombre.split(" ");
		
		String query = "SELECT TOP 15 p.des,p.des1, p.des2, p.ci FROM persona p WHERE 1 = 1 ";
		
		System.out.println(nombre+" "+palabras.length);
		if(palabras.length <= 3) {
			if(palabras.length == 1) {
				query += " AND p.des LIKE '%"+palabras[0].toString()+"%'";
			}
			
			if(palabras.length == 2) {
				query += " AND p.des LIKE '%"+palabras[0].toString()+"%' AND p.des1 LIKE '%"+palabras[1].toString()+"%'";
			}
			
			if(palabras.length == 3) {
				query += " AND p.des LIKE '%"+palabras[0].toString()+"%' AND p.des1 LIKE '%"+palabras[1].toString()+"%' AND p.des2 LIKE '%"+palabras[2].toString()+"%'";
			}
		}
		
		//String sql = "select * from persona WHERE ci = ?";
		
		//query += " ORDER BY af.fechacreacion DESC";
		
		//datos = jdbcTemplate.queryForList(query);
		
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(query) ;
		
		return ArrayProv;
		
	}
	// ******************* END PERSONAS *******************

	// ******************* REPARTICIONES *******************
	@GetMapping("/getReparticiones")
	public List<Map<String, Object>> getReparticiones(){		
		String sql = "SELECT cod, des FROM e2 WHERE tipo = 'RPT' order by des";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql);		
		return ArrayProv;
	}
	// ******************* END REPARTICIONES *******************


	// ******************* REFACCIONES *******************
	@PostMapping("/guardaRefaccion")
	public Map<String, Object> guardaRefaccion(@RequestBody String json){
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> obj = new HashMap();
		
		try {	
			Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);

			if(jsonMap.get("activo") != null && jsonMap.get("descripcion") != null){

				String sql = "INSERT INTO afw_refaccion ( "
													+ "codactivo ,"
													+ "descripcion ,"
													+ "fecha ,"
													+ "fechacreacion ,"
													+ "fechamodificacion "
													+ ") VALUES(?,?,?,?,?)"; //5
				String codactivo		= jsonMap.get("activo").toString().trim();
				String descripcion		= jsonMap.get("descripcion").toString().trim();
																	
				jdbcTemplate.update(sql,
									codactivo,   	//	codactivo
									descripcion, 	//	descripcion
									new Date(),  	//	fecha
									new Date(),  	//	fechacreacion
									null  			//	fechamodificacion
									);//5
			}

			obj = jsonMap;

			obj.put("estado", "success");
			
		} catch (JsonProcessingException e) {
		    // Handle the exception
		    e.printStackTrace();
			obj.put("estado", "error");
		}
		return obj;
	}

	@GetMapping("getRefaccionesByIdActivo/{codactivo}")
	public List<Map<String, Object>> getRefaccionesByIdActivo(@PathVariable String codactivo){
		String sql = "SELECT * FROM afw_refaccion WHERE codactivo = ? ORDER BY fechacreacion DESC";
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql, codactivo);		
		return ArrayProv;
	}
	// ******************* END REFACCIONES *******************
	
	


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
