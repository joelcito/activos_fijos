package com.activo.fijos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activo.fijos.models.dao.ISubGrupoDao;
import com.activo.fijos.models.entity.SubGrupo;

@Service
public class SubGrupoServiceImpl implements ISubGrupoService{

	@Autowired
	private ISubGrupoDao subGrupoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<SubGrupo> findAll() {
		return (List<SubGrupo>)this.subGrupoDao.findAll();
	}

	@Override
	@Transactional
	public SubGrupo save(SubGrupo subgrupo) {
		return this.subGrupoDao.save(subgrupo);
	}

	@Override
	@Transactional
	public void delete(String idsubgrupo) {
		this.subGrupoDao.deleteById(idsubgrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public SubGrupo findById(String idsubgrupo) {
		return this.subGrupoDao.findById(idsubgrupo).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SubGrupo> getSubGruposByGrupoId(String idgrupo) {
		return this.subGrupoDao.getSubGruposByGrupoId(idgrupo);
	}

}
