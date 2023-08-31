package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.dao.ClienteCustom;
import com.example.demo.models.dao.FacilityCustom;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.FacilityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entity.ClienteEntity;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private ClienteCustom clienteCustom;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteEntity> findAll() {
		return clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteEntity findOne(int id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(ClienteEntity cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(int id) {
		clienteDao.deleteById(id);
	}


	@Override
	@Transactional(readOnly = true)
	public List<ClienteEntity> filter(String nombreLegal, String direccionLegal, String dinero,
									   String email, String idBanco, Boolean esBanca) {
		if (nombreLegal == "" && direccionLegal == "" && dinero == ""  && email == ""
				&& idBanco == "" && !esBanca) {
			return findAll();
		}

		return clienteCustom.filtradoCliente(nombreLegal, direccionLegal, dinero, email, idBanco, esBanca);
	}

}
