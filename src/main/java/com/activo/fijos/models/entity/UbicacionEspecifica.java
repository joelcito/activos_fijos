package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ubicacionespecifica")
public class UbicacionEspecifica implements Serializable {

	@Id
	@Column(length = 15)
	private String idubicacionespecifica;
	private String nombre;
	private String descripcion;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificacion;
	
	public String getIdubicacionespecifica() {
		return idubicacionespecifica;
	}

	public void setIdubicacionespecifica(String idubicacionespecifica) {
		this.idubicacionespecifica = idubicacionespecifica;
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
