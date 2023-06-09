package com.activo.fijos.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.activo.fijos.models.entity.Activo;
import com.activo.fijos.models.entity.Regional;
import com.activo.fijos.models.entity.Ufv;
import com.activo.fijos.models.services.IActivoService;
import com.activo.fijos.models.services.IUfvService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.jdbc.core.JdbcTemplate;
//import com.fasterxml.jackson.databind.JsonMappingException;

//@CrossOrigin(origins = {"http://localhost:4200/"})
@CrossOrigin(origins = {
		"http://10.150.10.13/",
		"http://localhost:4200/"
		})
@RestController
@RequestMapping("/api/activo")
public class ActivoRestController {
	
	private final JdbcTemplate jdbcTemplate;
	//private static final String IMAGE_UPLOAD_PATH = "/src/main/resources/images/";
	//private static final String IMAGE_UPLOAD_PATH = "classpath:images";	
	@Autowired
	private IActivoService activoService;
	@Autowired
	private IUfvService ufvService;
	
	public ActivoRestController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
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
		
		//System.out.println("cod => "+activo.getCodigo());
		
		Regional regional = activo.getRegional();
		String idregional = regional.getIdregional();
			
		String ultimoRegistroID = this.activoService.maxIdActivo(idregional);
		
		String idString = "";
		
		if(ultimoRegistroID != null) {
			String[] partes =  ultimoRegistroID.split("-");
			
			int idNew = Integer.parseInt(partes[1])+1;
			idString = idNew+"";
			
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
			
		}else {
			idString = idregional+"-0000001";
		}
		
		activo.setIdactivo(idString);
		activo.setNuevocodigo("COS-"+idString);
		activo.setEstadoregistro("APR");
		activo.setFecha(new Date());
		activo.setFechacreacion(new Date());
		// activo.setEstadoVigencia("0");
					
		//return activo;
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
			
			System.out.println("V1 => "+jsonMap.get("variable1"));
			System.out.println("V2 => "+jsonMap.get("variable2"));	
			System.out.println("V3 => "+jsonMap.get("variable3"));
			System.out.println("V4 => "+jsonMap.get("variable4"));	
			
			String query = "SELECT TOP 1000 af.idactivo, af.codigo, af.estado, af.fechacompra, af.descripcion, re.nombre, af.estado_vigencia, pn.cod FROM afw_activo af INNER JOIN afw_regional re ON af.regional_id = re.idregional LEFT JOIN parnum pn ON pn.cod = af.estadoactivo and pn.atributo = 'NUM2' WHERE 1 = 1";
			
			if(!jsonMap.get("variable1").toString().equals("")) {
				query += " AND af.codigo = '"+jsonMap.get("variable1").toString()+"'";
			}
			
			if(!jsonMap.get("variable2").toString().equals("")) {
				query += " AND af.descripcion LIKE '%"+jsonMap.get("variable2").toString()+"%'";
			}
			
			if(!jsonMap.get("variable3").toString().equals("")) {
				if(jsonMap.get("variable3").toString().equals("1")) {
					query += " AND ( af.estadoactivo = '"+jsonMap.get("variable3").toString()+"' OR af.estadoactivo is null ) ";
				}else {
					query += " AND af.estadoactivo = '"+jsonMap.get("variable3").toString()+"' ";
				}
			}
			
			if(!jsonMap.get("variable4").toString().equals("")) {
				if(jsonMap.get("variable4").toString().equals("-1")) {
					query += " AND af.estado_vigencia is null";
				}else {
					query += " AND af.estado_vigencia = '"+jsonMap.get("variable4").toString()+"'";
				}
			}
			
			query += " ORDER BY af.fechacreacion DESC";
			
			datos = jdbcTemplate.queryForList(query);
			
			System.out.println(query);
						
			/*
			if(!jsonMap.get("variable1").toString().equals(""))		
				datos = this.activoService.buscaActivo(jsonMap.get("variable1").toString());
			else if(!jsonMap.get("variable2").toString().equals(""))
				datos = this.activoService.buscaActivoDescripcion(jsonMap.get("variable2").toString());
			else if(true) 			
				System.out.println("s");
			else if(!jsonMap.get("variable3").toString().equals(""))
				datos = this.activoService.buscaActivoEstadoVigencia(jsonMap.get("variable3").toString());
			else if(jsonMap.get("variable2").toString().equals("") && jsonMap.get("variable2").toString().equals("") && jsonMap.get("variable3").toString().equals(""))
				datos = this.activoService.listaActivosPer();
			*/
			
		} catch (JsonProcessingException e) {
		    // Handle the exception
		    e.printStackTrace();
		}
		
		return datos;
	}
	
	@GetMapping("getActivoMaximoByIdRegional/{idregional}")
	public Map<String, Object> getActivoMaximoByIdRegional(@PathVariable String idregional) {
		
		String sql = "SELECT af.* " +
			        "FROM afw_activo af " +
			        "INNER JOIN ( " +
			        "   SELECT MAX(SUBSTRING(idactivo, 4, LEN(idactivo))) AS maximo " +
			        "   FROM afw_activo " +
			        "   WHERE LEFT(idactivo, 2) = ? " +
			        ") sub ON RIGHT(af.idactivo, (LEN(af.idactivo) - 3)) LIKE CONCAT('%', sub.maximo, '%') " +
			        "WHERE LEFT(idactivo, 2) = ?";
		
		List<Map<String, Object>>  ArrayProv = jdbcTemplate.queryForList(sql, idregional,idregional);		
		
		Map<String, Object> obj = new HashMap();
		int incrementedValue = 1;
		String formattedValue;
		
		if(ArrayProv.size() > 0) {
			
			Map<String, Object> aux = new HashMap();
			aux = ArrayProv.get(0);
			
			String codigoactivo = aux.get("codigo").toString();
			String parts[] = codigoactivo.split("-");  
			
			// Increment the value by one
			incrementedValue = Integer.parseInt(parts[2]) + 1;

			// Format the incremented value with leading zeros
			formattedValue = String.format("%05d", incrementedValue);

			//System.out.println(formattedValue); // Output: 0007476
			
			//System.out.println(aux.get("idactivo"));
			
			obj.put("activo", aux);
			
		}else {
			formattedValue = String.format("%07d", incrementedValue);
		}
		obj.put("siguiente", formattedValue);
		return obj;
	}
		
	@PostMapping("/uploadImage")
	public Map<String, Object> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("idactivo") String idactivo) throws IOException {
		
		Map<String, Object> obj = new HashMap();
		Activo act = activoService.findById(idactivo);
		
		
		if(!image.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString()+"_"+image.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("images").resolve(nombreArchivo).toAbsolutePath();
			//Path rutaArchivo = Paths.get("src/main/resources/images").resolve(nombreArchivo).toAbsolutePath();
			
			try {
				
				if (!Files.exists(rutaArchivo)) {
					
					Path rutaDirectorio = Paths.get("images");
			        // Crear la carpeta "images"
			        //Files.createDirectories(Paths.get(rutaArchivo));
					System.out.println("No hay el direcrotio");
					try {
				        // Crear el directorio y los directorios padres si no existen
				        Files.createDirectories(rutaDirectorio);
				        System.out.println("El directorio se creó correctamente: " + rutaArchivo);
				    } catch (Exception e) {
				        System.out.println("Error al crear el directorio: " + e.getMessage());
				    }
			    }
				
				Files.copy(image.getInputStream(), rutaArchivo);
				obj.put("estado", "success");
			} catch (Exception e) {
				// TODO: handle exception
				obj.put("estado", "error");
				obj.put("msg", e.getMessage());
				obj.put("msg1", "error del baj dche al subir chee");
				obj.put("msg1", e);
			}
			
			//esto elimina la foto anteriror
			String nombreFotoAnt = act.getFoto();			
			if(nombreFotoAnt != null && nombreArchivo.length() > 0) {				
				Path rutaFotoAnterios = Paths.get("images").resolve(nombreFotoAnt).toAbsolutePath();
				File archivoAnterior = rutaFotoAnterios.toFile();
				if(archivoAnterior.exists() && archivoAnterior.canRead())
					archivoAnterior.delete();				
			}			
			
			act.setFoto(nombreArchivo);
			
			activoService.save(act);		
			
			obj.put("activo", act);
		}
			
		return obj;
	  }
	
	
	@GetMapping("/images/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		
		Path rutaArchivo = Paths.get("images").resolve(nombreFoto).toAbsolutePath();
		//Path rutaArchivo = Paths.get("src/main/resources/images").resolve(nombreFoto).toAbsolutePath();
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("errto al cargar la fort");
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
				
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
		
	}
}
