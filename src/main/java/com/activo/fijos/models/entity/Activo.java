package com.activo.fijos.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Table;
//import javax.persistence.Id;

@Entity
@Table(name="activos")
public class Activo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="grupo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Grupo grupo;
	
	/*
	private String grupo;
	*/
	private String codigo_item;
	private String departameto;
	private String regional;
	private String fecha_compra;
	private String ufv_inicio;
	private String vida_util;
	private String porcentaje_depreciacion;
	private String estado;
		
	private String rubro;
	private String tipo_cuenta;
	private String tipo_incorporacion;
	private String tipo_regimen;
	private String tipo_modelo;
	private String tipo_marca;
	private String numero_serie;
	private String ufv_compra;
	private String ubicacion_general;
	private String ubicacion_especifica;
	
	private Date createAt;
	
	public String getRubro() {
		return rubro;
	}
	public void setRubro(String rubro) {
		this.rubro = rubro;
	}
	public String getTipo_cuenta() {
		return tipo_cuenta;
	}
	public void setTipo_cuenta(String tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}
	public String getTipo_incorporacion() {
		return tipo_incorporacion;
	}
	public void setTipo_incorporacion(String tipo_incorporacion) {
		this.tipo_incorporacion = tipo_incorporacion;
	}
	public String getTipo_regimen() {
		return tipo_regimen;
	}
	public void setTipo_regimen(String tipo_regimen) {
		this.tipo_regimen = tipo_regimen;
	}
	public String getTipo_modelo() {
		return tipo_modelo;
	}
	public void setTipo_modelo(String tipo_modelo) {
		this.tipo_modelo = tipo_modelo;
	}
	public String getTipo_marca() {
		return tipo_marca;
	}
	public void setTipo_marca(String tipo_marca) {
		this.tipo_marca = tipo_marca;
	}
	public String getNumero_serie() {
		return numero_serie;
	}
	public void setNumero_serie(String numero_serie) {
		this.numero_serie = numero_serie;
	}
	public String getUfv_compra() {
		return ufv_compra;
	}
	public void setUfv_compra(String ufv_compra) {
		this.ufv_compra = ufv_compra;
	}
	public String getUbicacion_general() {
		return ubicacion_general;
	}
	public void setUbicacion_general(String ubicacion_general) {
		this.ubicacion_general = ubicacion_general;
	}
	public String getUbicacion_especifica() {
		return ubicacion_especifica;
	}
	public void setUbicacion_especifica(String ubicacion_especifica) {
		this.ubicacion_especifica = ubicacion_especifica;
	}
	private Date modifiqueAt;
	private Date deleteAt;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo_item() {
		return codigo_item;
	}
	public void setCodigo_item(String codigo_item) {
		this.codigo_item = codigo_item;
	}
	public String getDepartameto() {
		return departameto;
	}
	public void setDepartameto(String departameto) {
		this.departameto = departameto;
	}
	public String getRegional() {
		return regional;
	}
	public void setRegional(String regional) {
		this.regional = regional;
	}
	public String getFecha_compra() {
		return fecha_compra;
	}
	public void setFecha_compra(String fecha_compra) {
		this.fecha_compra = fecha_compra;
	}
	public String getUfv_inicio() {
		return ufv_inicio;
	}
	public void setUfv_inicio(String ufv_inicio) {
		this.ufv_inicio = ufv_inicio;
	}
	public String getVida_util() {
		return vida_util;
	}
	public void setVida_util(String vida_util) {
		this.vida_util = vida_util;
	}
	public String getPorcentaje_depreciacion() {
		return porcentaje_depreciacion;
	}
	public void setPorcentaje_depreciacion(String porcentaje_depreciacion) {
		this.porcentaje_depreciacion = porcentaje_depreciacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getModifiqueAt() {
		return modifiqueAt;
	}
	public void setModifiqueAt(Date modifiqueAt) {
		this.modifiqueAt = modifiqueAt;
	}
	public Date getDeleteAt() {
		return deleteAt;
	}
	public void setDeleteAt(Date deleteAt) {
		this.deleteAt = deleteAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
