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
@Table(name="afw_grupo")
public class Grupo implements Serializable{

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 15)
	private String idgrupo;
	
	private String codanterior;
	
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subgrupo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private SubGrupo subgrupo;
	*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cuenta_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Cuenta cuenta;
	
	private String descripcion;
	private int vidaUtil;
	private String estado;
	private int nroItems;
	@Column(columnDefinition = "DATE")
	private Date fecha;
	private Date fechacreacion;
	private Date fechamodificacion;
	
	/*
	public SubGrupo getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(SubGrupo subgrupo) {
		this.subgrupo = subgrupo;
	}
	*/

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public int getNroItems() {
		return nroItems;
	}

	public void setNroItems(int nroItems) {
		this.nroItems = nroItems;
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



	public int getVidaUtil() {
		return vidaUtil;
	}



	public void setVidaUtil(int vidaUtil) {
		this.vidaUtil = vidaUtil;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	


	public String getCodanterior() {
		return codanterior;
	}

	public void setCodanterior(String cod_anterior) {
		this.codanterior = cod_anterior;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
