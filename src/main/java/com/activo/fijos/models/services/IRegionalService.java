package com.activo.fijos.models.services;

import java.util.List;

import com.activo.fijos.models.entity.Regional;


public interface IRegionalService {
	
	public List<Regional> findAll();
	
	public Regional save(Regional regional);
	
	public void delete(String idregional);
	
	public Regional findById(String idregional);

}
