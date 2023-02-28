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
@Table(name="activo")
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

	private String codigo;
	private String placa;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private String eficiencia;
	private String codigoalterno;
	private String formainicial;
	private String descripcion;
	private String estadoregistro;
	private String estado;
	
	@Column(columnDefinition = "DATE")
	private Date fechacompra;
	
	@Column(columnDefinition = "Decimal(5,2)")
	private float porcentaje_depreciacion;
	
	@Column(columnDefinition = "Decimal(3,2)")
	private float ufv;

	@Column(columnDefinition = "Decimal(3,2)")
	private float ufvcompra;
	
	private int vida_util;
	private Date fechacreacion;
	private Date fechamodificacion;
			
	public String getIdactivo() {
		return idactivo;
	}

	public void setIdactivo(String idactivo) {
		this.idactivo = idactivo;
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



	public int getVida_util() {
		return vida_util;
	}



	public void setVida_util(int vida_util) {
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



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
