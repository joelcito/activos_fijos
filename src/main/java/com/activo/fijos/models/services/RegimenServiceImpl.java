package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IRegimenDao;
import com.activo.fijos.models.entity.Regimen;

@Service
public class RegimenServiceImpl implements IRegimenService {
	@Autowired
	private IRegimenDao regimenDao;

	@Override
	@Transactional(readOnly = true)
	public List<Regimen> findAll() {
		return (List<Regimen>)this.regimenDao.findAll();
	}

	@Override
	@Transactional
	public Regimen save(Regimen regimen) {
		return this.regimenDao.save(regimen);
	}

	@Override
	@Transactional
	public void delete(String idregimen) {
		this.regimenDao.deleteById(idregimen);
	}

	@Override
	@Transactional(readOnly = true)
	public Regimen findById(String idregimen) {
		return this.regimenDao.findById(idregimen).orElse(null);
	}

}
