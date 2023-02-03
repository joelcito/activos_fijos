package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IIncorporacionDao;
import com.activo.fijos.models.entity.Incorporacion;

@Service
public class IncorporacionServiceImpl implements IIncorporacionService{
	
	@Autowired
	private IIncorporacionDao incorporacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<Incorporacion> findAll() {
		return (List<Incorporacion>) this.incorporacionDao.findAll();
	}

	@Override
	@Transactional
	public Incorporacion save(Incorporacion grupo) {
		return this.incorporacionDao.save(grupo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.incorporacionDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Incorporacion findById(Long id) {
		return this.incorporacionDao.findById(id).orElse(null);
	}

}
