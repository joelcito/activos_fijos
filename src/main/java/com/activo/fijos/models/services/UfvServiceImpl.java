package com.activo.fijos.models.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IUfvDao;
import com.activo.fijos.models.entity.Ufv;

@Service
public class UfvServiceImpl implements IUfvService{
	
	@Autowired
	private IUfvDao ufvDao;

	@Override
	@Transactional(readOnly = true)
	public List<Ufv> findAll() {
		return (List<Ufv>)this.ufvDao.findAll();
	}

	@Override
	@Transactional
	public Ufv save(Ufv ufv) {
		return this.ufvDao.save(ufv);
	}

	@Override
	@Transactional
	public void delete(String id) {
		this.ufvDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Ufv findById(String Id) {
		return this.ufvDao.findById(Id).orElse(null);
	}

	@Override
	@Transactional
	public String max() {
		return this.ufvDao.max();
	}

	@Override
	@Transactional(readOnly = true)
	public Ufv buscarPorFecha(LocalDate fecha) {
		return this.ufvDao.buscarPorFecha(fecha);
	}

}
