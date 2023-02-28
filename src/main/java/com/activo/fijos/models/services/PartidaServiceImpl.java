package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IPartidaDao;
import com.activo.fijos.models.entity.Partida;

@Service
public class PartidaServiceImpl implements IPartidaService {
	
	@Autowired
	private IPartidaDao partidaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Partida> findAll() {
		return (List<Partida>)this.partidaDao.findAll();
	}

	@Override
	@Transactional
	public Partida save(Partida partida) {
		return partidaDao.save(partida);
	}

	@Override
	@Transactional
	public void delete(String idpartida) {
		partidaDao.deleteById(idpartida);
	}

	@Override
	@Transactional(readOnly = true)
	public Partida findById(String idpartida) {
		return partidaDao.findById(idpartida).orElse(null);
	}

}
