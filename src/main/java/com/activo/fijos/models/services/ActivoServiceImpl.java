package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IActivoDao;
import com.activo.fijos.models.entity.Activo;

@Service
public class ActivoServiceImpl implements IActivoService{

	@Autowired
	private IActivoDao activoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Activo> findAll() {
		return (List<Activo>) this.activoDao.findAll();
	}

	@Override
	@Transactional
	public Activo save(Activo activo) {
		return activoDao.save(activo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		activoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Activo findById(Long Id) {
		return activoDao.findById(Id).orElse(null);
	}
	
}
