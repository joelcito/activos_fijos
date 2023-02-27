package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IDepartamentoDao;
import com.activo.fijos.models.entity.Departamento;

@Service
public class DepartamentoImpl implements IDepartamentoService{
	
	@Autowired
	private IDepartamentoDao departamentoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> findAll() {
		return (List<Departamento>)this.departamentoDao.findAll();
	}

	@Override
	@Transactional
	public Departamento save(Departamento departamento) {
		return departamentoDao.save(departamento);
	}

	@Override
	@Transactional
	public void delete(String iddepartamento) {
		departamentoDao.deleteById(iddepartamento);
	}

	@Override
	@Transactional(readOnly = true)
	public Departamento findById(String iddepartamento) {
		return departamentoDao.findById(iddepartamento).orElse(null);
	}

}
