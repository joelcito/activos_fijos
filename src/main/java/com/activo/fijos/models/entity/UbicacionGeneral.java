package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="afw_ubicaciongeneral")
public class UbicacionGeneral implements Serializable {

	@Id
	@Column(length = 15)
	private String idubicaciongeneral;
	private String nombre;
	private String descripcion;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificaion;
			
	public String getIdubicaciongeneral() {
		return idubicaciongeneral;
	}

	public void setIdubicaciongeneral(String idubicaciongeneral) {
		this.idubicaciongeneral = idubicaciongeneral;
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

	public Date getFechamodificaion() {
		return fechamodificaion;
	}

	public void setFechamodificaion(Date fechamodificaion) {
		this.fechamodificaion = fechamodificaion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
