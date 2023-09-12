package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.UsuarioEntity;

public interface IDealService {
	List<DealEntity> findAll();

	DealEntity findOne(int id);

	void save(DealEntity deal);

	void delete(int id);

	List<DealEntity> filter(String idBanco, String estado, String moneda, String tipo, String cliente,
			String cantidadPrestamo, String cantidadAbonada, String cantidadAPagar, String descuento, String creadoPor);

	boolean puedeEditar(UsuarioEntity usuario, DealEntity deal);
}
