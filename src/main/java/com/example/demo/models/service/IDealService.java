package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.DealEntity;

public interface IDealService {
	List<DealEntity> findAll();

	DealEntity findOne(int id);

	void save(DealEntity deal);

	void delete(int id);

	List<DealEntity> filter(String estado, String moneda, String tipo, String cliente, String cantidadPrestamo,
			String cantidadAbonada, String cantidadAPagar, String descuento, String idBanco);
}
