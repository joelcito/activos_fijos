package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="subgrupo")
public class SubGrupo implements Serializable{

	@Id
	@Column(length = 15)
	private String idsubgrupo;
	
	private String descripcion;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificacion;
		
	public String getIdsubgrupo() {
		return idsubgrupo;
	}

	public void setIdsubgrupo(String idsubgrupo) {
		this.idsubgrupo = idsubgrupo;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
