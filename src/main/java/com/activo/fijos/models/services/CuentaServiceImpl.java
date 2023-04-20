package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.ICuentaDao;
import com.activo.fijos.models.entity.Cuenta;

@Service
public class CuentaServiceImpl implements ICuentaService {
	
	@Autowired
	private ICuentaDao cuentaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> findAll() {
		return (List<Cuenta>)this.cuentaDao.findAll();
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		return this.cuentaDao.save(cuenta);
	}

	@Override
	@Transactional
	public void delete(String idcuenta) {
		cuentaDao.deleteById(idcuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(String idcuenta) {
		return cuentaDao.findById(idcuenta).orElse(null);
	}

	@Override
	@Transactional
	public String maxId() {
		return this.cuentaDao.maxId();
	}

}
