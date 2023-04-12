package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.ICaracteristicaDao;
import com.activo.fijos.models.entity.Caracteristica;

@Service
public class CaracteristicaServiceImpl implements ICaracteristicaService{
	
	@Autowired
	private ICaracteristicaDao caracteristicaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Caracteristica> findAll() {
		return (List<Caracteristica>)this.caracteristicaDao.findAll();
	}

	@Override
	@Transactional
	public Caracteristica save(Caracteristica caracteristica) {
		return this.caracteristicaDao.save(caracteristica);
	}

	@Override
	@Transactional
	public void delete(String idcaracteristica) {
		this.caracteristicaDao.deleteById(idcaracteristica);
	}

	@Override
	@Transactional(readOnly = true)
	public Caracteristica findById(String idcaracteristica) {
		return this.caracteristicaDao.findById(idcaracteristica).orElse(null);
	}
	
	@Override
	@Transactional
	public String maxId() {
		return caracteristicaDao.maxId();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Caracteristica> getCaracteristicasByIdActivo(String idactivo) {
		return (List<Caracteristica>)this.caracteristicaDao.getCaracteristicasByIdActivo(idactivo);
	}

}
