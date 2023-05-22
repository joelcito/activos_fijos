package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "afw_refaccion")
public class Refaccion implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idrefaccion;
	
	private String codactivo;
	
	@Column(length = 1000)
	private String descripcion;

	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificacion;
	public Long getIdrefaccion() {
		return idrefaccion;
	}
	public void setIdrefaccion(Long idrefaccion) {
		this.idrefaccion = idrefaccion;
	}
	public String getCodactivo() {
		return codactivo;
	}
	public void setCodactivo(String codactivo) {
		this.codactivo = codactivo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
