package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IComponenteDao;
import com.activo.fijos.models.entity.Componente;

@Service
public class ComponenteServiceImpl implements IComponenteService{
	@Autowired
	private IComponenteDao componenteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Componente> findAll() {
		return (List<Componente>)this.componenteDao.findAll();
	}

	@Override
	@Transactional
	public Componente save(Componente componente) {
		return this.componenteDao.save(componente);
	}

	@Override
	@Transactional
	public void delete(String idcomponente) {
		this.componenteDao.deleteById(idcomponente);
	}

	@Override
	@Transactional(readOnly = true)
	public Componente findById(String idcomponente) {
		return this.componenteDao.findById(idcomponente).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Componente> getComponentesByIdSubGrupo(String idsubgrupo) {
		return this.componenteDao.getComponentesByIdSubGrupo(idsubgrupo);
	}

	@Override
	@Transactional
	public String maxId() {
		return this.componenteDao.maxId();
	}

}
