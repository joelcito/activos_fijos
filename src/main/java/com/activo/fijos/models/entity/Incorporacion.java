package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="afw_incorporacion")
public class Incorporacion implements Serializable{
	
	@Id
	@Column(length = 15)
	private String idincorporacion;
	private String nombre;
	private String descripcion;
	private String estado;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificado;
		
	
	public String getIdincorporacion() {
		return idincorporacion;
	}

	public void setIdincorporacion(String idincorporacion) {
		this.idincorporacion = idincorporacion;
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

	public Date getFechamodificado() {
		return fechamodificado;
	}

	public void setFechamodificado(Date fechamodificado) {
		this.fechamodificado = fechamodificado;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
