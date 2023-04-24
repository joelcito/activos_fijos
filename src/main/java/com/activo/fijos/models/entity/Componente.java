package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="afw_componente")
public class Componente implements Serializable {
	@Id
	@Column(length = 15)
	private String idcomponente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="subgrupo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private SubGrupo subgrupo;
	
	private String nombre;
	private String estado;
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificacion;
	
	public String getIdcomponente() {
		return idcomponente;
	}



	public void setIdcomponente(String idcomponente) {
		this.idcomponente = idcomponente;
	}



	public SubGrupo getSubgrupo() {
		return subgrupo;
	}



	public void setSubgrupo(SubGrupo subgrupo) {
		this.subgrupo = subgrupo;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
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
