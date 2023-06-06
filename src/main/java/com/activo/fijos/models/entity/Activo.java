package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name="afw_activo")
public class Activo implements Serializable{
	
	@Id
	@Column(length = 15)
	private String idactivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="incorporacion_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Incorporacion incorporacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="grupo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Grupo grupo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="subgrupo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private SubGrupo subgrupo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="regimen_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Regimen regimen;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="regional_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Regional regional;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="unidadmanejo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private UnididadManejo unidadmanejo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tipotransaccion_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private TipoTransaccion tipotransaccion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ubicacionespecifica_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private UbicacionEspecifica ubicacionespecifica;

	//@Column(name="codigo")
	private String codigo;
	
	private String codprovedor;
	
	private String nuevocodigo;
	
	private String factura;
	
	private String estadoVigencia;
	
	//@Column(name="placa")
	private String placa;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	//@Column(name="eficiencia")
	private String eficiencia;
	//@Column(name="codigoalterno")
	private String codigoalterno;
	//@Column(name="formainicial")
	private String formainicial;
	
	@Column(length = 1000)
	private String descripcion;
	//@Column(name="estadoregistro")
	private String estadoregistro;
	//@Column(name="estado")
	private String estado;
	
	private Long estadoactivo;
	
	@Column(columnDefinition = "DATE")
	private Date fechacompra;
	
	@Column(columnDefinition = "Decimal(5,2)")
	private float porcentaje_depreciacion;
	
	@Column(columnDefinition = "Decimal(3,3)")
	private float ufv;

	//@Column(columnDefinition = "Decimal(3,5)")
	private float ufvcompra;
	
	@Column(columnDefinition = "Decimal(12,2)")
	private float precio;
	
	//@Column(name="vida_util")
	@Column(columnDefinition = "Decimal(12,2)")
	private float vida_util;
	//@Column(name="fechacreacion")
	private Date fechacreacion;
	//@Column(name="fechamodificacion")
	private Date fechamodificacion;
	
	@Column(columnDefinition = "DATE")
	private Date fechaini;
	
	@Column(columnDefinition = "DATE")
	private Date fechafin;
	
	@Column(columnDefinition = "Decimal(12,3)")
	private float valpresente;
	
	@Column(columnDefinition = "Decimal(12,3)")
	private float valactualizado;
	
	@Column(columnDefinition = "Decimal(12,3)")
	private float depacumulada;
	
	@Column(columnDefinition = "Decimal(12,3)")
	private float vidautilres;
	
	private Date fechabaja;
			
	public String getIdactivo() {
		return idactivo;
	}

	public void setIdactivo(String idactivo) {
		this.idactivo = idactivo;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Incorporacion getIncorporacion() {
		return incorporacion;
	}

	public void setIncorporacion(Incorporacion incorporacion) {
		this.incorporacion = incorporacion;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	

	public Regimen getRegimen() {
		return regimen;
	}

	public SubGrupo getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(SubGrupo subgrupo) {
		this.subgrupo = subgrupo;
	}

	public void setRegimen(Regimen regimen) {
		this.regimen = regimen;
	}



	public Regional getRegional() {
		return regional;
	}



	public void setRegional(Regional regional) {
		this.regional = regional;
	}



	public UnididadManejo getUnidadmanejo() {
		return unidadmanejo;
	}



	public void setUnidadmanejo(UnididadManejo unidadmanejo) {
		this.unidadmanejo = unidadmanejo;
	}



	public TipoTransaccion getTipotransaccion() {
		return tipotransaccion;
	}



	public void setTipotransaccion(TipoTransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}



	public UbicacionEspecifica getUbicacionespecifica() {
		return ubicacionespecifica;
	}



	public void setUbicacionespecifica(UbicacionEspecifica ubicacionespecifica) {
		this.ubicacionespecifica = ubicacionespecifica;
	}



	public String getCodigo() {
		return codigo;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	public String getPlaca() {
		return placa;
	}



	public void setPlaca(String placa) {
		this.placa = placa;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getEficiencia() {
		return eficiencia;
	}



	public void setEficiencia(String eficiencia) {
		this.eficiencia = eficiencia;
	}



	public String getCodigoalterno() {
		return codigoalterno;
	}



	public void setCodigoalterno(String codigoalterno) {
		this.codigoalterno = codigoalterno;
	}



	public String getFormainicial() {
		return formainicial;
	}



	public void setFormainicial(String formainicial) {
		this.formainicial = formainicial;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getEstadoregistro() {
		return estadoregistro;
	}



	public void setEstadoregistro(String estadoregistro) {
		this.estadoregistro = estadoregistro;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Date getFechacompra() {
		return fechacompra;
	}



	public void setFechacompra(Date fechacompra) {
		this.fechacompra = fechacompra;
	}



	public float getPorcentaje_depreciacion() {
		return porcentaje_depreciacion;
	}



	public void setPorcentaje_depreciacion(float porcentaje_depreciacion) {
		this.porcentaje_depreciacion = porcentaje_depreciacion;
	}



	public float getUfv() {
		return ufv;
	}



	public void setUfv(float ufv) {
		this.ufv = ufv;
	}



	public float getUfvcompra() {
		return ufvcompra;
	}



	public void setUfvcompra(float ufvcompra) {
		this.ufvcompra = ufvcompra;
	}



	public float getVida_util() {
		return vida_util;
	}



	public void setVida_util(float vida_util) {
		this.vida_util = vida_util;
	}



	public Date getFechacreacion() {
		return fechacreacion;
	}



	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}



	public Date getFechamodificacion() {
		return fechamodificacion;
	}



	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	
	public String getCodprovedor() {
		return codprovedor;
	}

	public void setCodprovedor(String codprovedor) {
		this.codprovedor = codprovedor;
	}
		
	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}
		
	public String getNuevocodigo() {
		return nuevocodigo;
	}

	public void setNuevocodigo(String nuevocodigo) {
		this.nuevocodigo = nuevocodigo;
	}
	
	public String getEstadoVigencia() {
		return estadoVigencia;
	}

	public void setEstadoVigencia(String estadoVigencia) {
		this.estadoVigencia = estadoVigencia;
	}

	public Date getFechaini() {
		return fechaini;
	}

	public void setFechaini(Date fechaini) {
		this.fechaini = fechaini;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public float getValpresente() {
		return valpresente;
	}

	public void setValpresente(float valpresente) {
		this.valpresente = valpresente;
	}

	public float getValactualizado() {
		return valactualizado;
	}

	public void setValactualizado(float valactualizado) {
		this.valactualizado = valactualizado;
	}

	public float getDepacumulada() {
		return depacumulada;
	}

	public void setDepacumulada(float depacumulada) {
		this.depacumulada = depacumulada;
	}

	public float getVidautilres() {
		return vidautilres;
	}

	public void setVidautilres(float vidautilres) {
		this.vidautilres = vidautilres;
	}

	public Long getEstadoactivo() {
		return estadoactivo;
	}

	public void setEstadoactivo(Long estadoactivo) {
		this.estadoactivo = estadoactivo;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}