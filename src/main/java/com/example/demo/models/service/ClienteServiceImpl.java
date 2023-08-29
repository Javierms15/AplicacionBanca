package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entity.ClienteEntity;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;

	@Override
	public List<ClienteEntity> findAll() {
		return (List<ClienteEntity>) clienteDao.findAll();
	}

}
