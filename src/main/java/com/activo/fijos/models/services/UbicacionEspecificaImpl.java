package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IUbicacionEspecificaDao;
import com.activo.fijos.models.entity.UbicacionEspecifica;


@Service
public class UbicacionEspecificaImpl implements IUbicacionEspecificaService {
	@Autowired
	private IUbicacionEspecificaDao ubicacionEspecificaDao;

	@Override
	@Transactional(readOnly = true)
	public List<UbicacionEspecifica> findAll() {
		return (List<UbicacionEspecifica>)this.ubicacionEspecificaDao.findAll();
	}

	@Override
	@Transactional
	public UbicacionEspecifica save(UbicacionEspecifica ubicacionEspecifica) {
		return ubicacionEspecificaDao.save(ubicacionEspecifica);
	}

	@Override
	@Transactional
	public void dalete(String idubicacionespecifica) {
		ubicacionEspecificaDao.deleteById(idubicacionespecifica);		
	}

	@Override
	@Transactional(readOnly = true)
	public UbicacionEspecifica findById(String idubicacionespecifica) {
		return ubicacionEspecificaDao.findById(idubicacionespecifica).orElse(null);
	}
}
