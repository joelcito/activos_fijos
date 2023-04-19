package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ufv")
public class Ufv implements Serializable{
	
	@Id
	@Column
	private String idufv;
	
	@Column(columnDefinition = "DATE")
	private Date fecha;
	
	private Date fechacreacion;

	private Date fechamodificacion;
	
	private float valor;

	public String getIdufv() {
		return idufv;
	}

	public void setIdufv(String idufv) {
		this.idufv = idufv;
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
		
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
