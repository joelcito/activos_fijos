package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.IRegionalDao;
import com.activo.fijos.models.entity.Regional;

@Service
public class RegionalServiceImpl implements IRegionalService {
	
	@Autowired
	private IRegionalDao regionalDao;

	@Override
	@Transactional(readOnly = true)
	public List<Regional> findAll() {
		return (List<Regional>)this.regionalDao.findAll();
	}

	@Override
	@Transactional
	public Regional save(Regional regional) {
		return this.regionalDao.save(regional);
	}

	@Override
	@Transactional
	public void delete(String idregional) {
		this.regionalDao.deleteById(idregional);
	}

	@Override
	@Transactional(readOnly = true)
	public Regional findById(String idregional) {
		return this.regionalDao.findById(idregional).orElse(null);
	}

}
