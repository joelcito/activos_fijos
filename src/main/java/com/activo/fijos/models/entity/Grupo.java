package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="grupos")
public class Grupo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private int vidaUtil;
	private String codCuenta;
	private String estado;
	private Long nroItems;
	private Date creat_at;
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public String getCodCuenta() {
		return codCuenta;
	}



	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Long getNroItems() {
		return nroItems;
	}



	public void setNroItems(Long nroItems) {
		this.nroItems = nroItems;
	}



	public Date getCreat_at() {
		return creat_at;
	}



	public void setCreat_at(Date creat_at) {
		this.creat_at = creat_at;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
