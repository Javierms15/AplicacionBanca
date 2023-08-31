package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.FacilityEntity;

public interface IClienteService {

	List<ClienteEntity> findAll();


	ClienteEntity findOne(int id);

	void save(ClienteEntity deal);

	void delete(int id);

	List<ClienteEntity> filter(String nombreLegal, String direccionLegal, String dinero,
								String email, String idBanco, Boolean esBanca);

}
