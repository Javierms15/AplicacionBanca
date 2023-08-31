package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.OutstandingEntity;

public interface IOutstandingService {

	List<OutstandingEntity> findAll();

	OutstandingEntity findOne(int id);

	void save(OutstandingEntity outstanding);

	void delete(int id);

	List<OutstandingEntity> filter(String cantidadRestante, String fechaEfectiva, String fechaCreacion,
			String fechaFinalizacion, String pagoPrincipal, String pagoIntereses, String tipoInteres, String tipoCobros,
			String periodicidad, String cantidadCobroPeriodico, String facility, String idBanco);

	Double obtenerSumaOutsandingFacility(int idDeal);
}
