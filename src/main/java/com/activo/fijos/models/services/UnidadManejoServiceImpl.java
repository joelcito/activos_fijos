package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IUnidadManejoDao;
import com.activo.fijos.models.entity.UnididadManejo;

@Service
public class UnidadManejoServiceImpl implements IUnidadManejoService{
	
	@Autowired
	private IUnidadManejoDao unidadManejoDao;

	@Override
	@Transactional(readOnly = true)
	public List<UnididadManejo> findAll() {
		return (List<UnididadManejo>)this.unidadManejoDao.findAll();
	}

	@Override
	@Transactional
	public UnididadManejo save(UnididadManejo unidadManejo) {
		return unidadManejoDao.save(unidadManejo);
	}

	@Override
	@Transactional
	public void delete(String idunidadmanejo) {
		unidadManejoDao.deleteById(idunidadmanejo);
	}

	@Override
	@Transactional(readOnly = true)
	public UnididadManejo findById(String idunidadmanejo) {
		return unidadManejoDao.findById(idunidadmanejo).orElse(null);
	}

}
