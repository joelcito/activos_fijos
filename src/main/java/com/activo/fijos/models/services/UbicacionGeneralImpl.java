package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IUbicacionGeneralDao;
import com.activo.fijos.models.entity.UbicacionGeneral;

@Service
public class UbicacionGeneralImpl implements IUbicacionGeneralService {
	
	@Autowired
	private IUbicacionGeneralDao ubicacionGeneralDao;

	@Override
	@Transactional(readOnly = true)
	public List<UbicacionGeneral> findAll() {
		return (List<UbicacionGeneral>)this.ubicacionGeneralDao.findAll();
	}

	@Override
	@Transactional
	public UbicacionGeneral save(UbicacionGeneral ubicaciongeneral) {
		return ubicacionGeneralDao.save(ubicaciongeneral);
	}

	@Override
	@Transactional
	public void delete(String idubicaciongeneral) {
		ubicacionGeneralDao.deleteById(idubicaciongeneral);
	}

	@Override
	@Transactional(readOnly = true)
	public UbicacionGeneral findById(String ubicaciongeneral) {
		return ubicacionGeneralDao.findById(ubicaciongeneral).orElse(null);
	}

}
