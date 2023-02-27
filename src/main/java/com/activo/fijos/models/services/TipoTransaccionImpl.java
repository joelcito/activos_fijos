package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.ITipoTransaccionDao;
import com.activo.fijos.models.entity.TipoTransaccion;

@Service
public class TipoTransaccionImpl implements ITipoTransaccionService {
	
	@Autowired
	private ITipoTransaccionDao tipoTransaccionDao;

	@Override
	@Transactional(readOnly = true)
	public List<TipoTransaccion> findAll() {
		return (List<TipoTransaccion>)this.tipoTransaccionDao.findAll();
	}

	@Override
	@Transactional
	public TipoTransaccion save(TipoTransaccion tipoTransaccion) {
		return tipoTransaccionDao.save(tipoTransaccion);
	}

	@Override
	@Transactional
	public void delete(String idtipotransaccion) {
		tipoTransaccionDao.deleteById(idtipotransaccion);
	}

	@Override
	@Transactional(readOnly = true)
	public TipoTransaccion findById(String idtipotransaccion) {
		return tipoTransaccionDao.findById(idtipotransaccion).orElse(null);
	}

}
