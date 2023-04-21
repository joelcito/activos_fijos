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
@Table(name="afw_caracteristica")
public class Caracteristica implements Serializable {
	
	@Id
	@Column(length = 15)
	private String idcaracteristica;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="activo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Activo activo;
	
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subgrupo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private SubGrupo subgrupo;
	*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="componente_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Componente componente;
	
	@Column(columnDefinition = "DATE")
	private Date fecha;
	
	private String descripcion;
	private Date fechacreacion;
	private Date fechamodificacion;
	
	

	public String getIdcaracteristica() {
		return idcaracteristica;
	}



	public void setIdcaracteristica(String idcaracteristica) {
		this.idcaracteristica = idcaracteristica;
	}



	public Activo getActivo() {
		return activo;
	}



	public void setActivo(Activo activo) {
		this.activo = activo;
	}


/*
	public SubGrupo getSubgrupo() {
		return subgrupo;
	}



	public void setSubgrupo(SubGrupo subgrupo) {
		this.subgrupo = subgrupo;
	}
	*/
	
	



	public Date getFecha() {
		return fecha;
	}



	public Componente getComponente() {
		return componente;
	}



	public void setComponente(Componente componente) {
		this.componente = componente;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
