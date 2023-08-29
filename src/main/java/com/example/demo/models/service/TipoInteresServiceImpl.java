package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.ITipoInteresDao;
import com.example.demo.models.entity.TipoInteresEntity;

@Service
public class TipoInteresServiceImpl implements ITipoInteresService {

	@Autowired
	private ITipoInteresDao tipoInteresDao;
	
	@Override
	public List<TipoInteresEntity> findAll() {
		return tipoInteresDao.findAll();
	}

}
