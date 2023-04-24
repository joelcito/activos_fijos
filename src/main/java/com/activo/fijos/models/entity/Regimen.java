package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="afw_regimen")
public class Regimen implements Serializable{
	
	@Id
	@Column(length = 15)
	private String idregimen;
	private String nombre;
	private String descripcion;
	private String estado;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificacion;
		
	public String getIdregimen() {
		return idregimen;
	}

	public void setIdregimen(String idregimen) {
		this.idregimen = idregimen;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
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
