package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IGrupoDao;
import com.activo.fijos.models.entity.Grupo;

@Service
public class GrupoServiceImpl implements IGrupoService{
	
	@Autowired
	private IGrupoDao grupoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> findAll() {
		return (List<Grupo>) this.grupoDao.findAll();
	}

	@Override
	@Transactional
	public Grupo save(Grupo grupo) {
		return this.grupoDao.save(grupo);
	}

	@Override
	@Transactional
	public void delete(String id) {
		this.grupoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo findById(String id) {
		return this.grupoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public String maxId() {
		return this.grupoDao.maxId();
	}

}
