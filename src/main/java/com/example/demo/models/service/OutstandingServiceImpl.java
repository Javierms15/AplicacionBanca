package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.dao.IOutstandingDao;
import com.example.demo.models.dao.OutstandingCustom;
import com.example.demo.models.entity.OutstandingEntity;

@Service
public class OutstandingServiceImpl implements IOutstandingService {

	@Autowired
	private IOutstandingDao outstandingDao;

	@Autowired
	private OutstandingCustom outstandingCustom;

	@Override
	@Transactional(readOnly = true)
	public List<OutstandingEntity> findAll() {
		return outstandingDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public OutstandingEntity findOne(int id) {
		return outstandingDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(OutstandingEntity outstanding) {
		outstandingDao.save(outstanding);
	}

	@Override
	@Transactional
	public void delete(int id) {
		outstandingDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OutstandingEntity> filter(String cantidadRestante, String fechaEfectiva, String fechaCreacion,
			String fechaFinalizacion, String pagoPrincipal, String pagoIntereses, String tipoInteres, String tipoCobros,
			String periodicidad, String cantidadCobroPeriodico, String facility, String idBanco) {
		if (cantidadRestante == "" && fechaEfectiva == "" && fechaCreacion == "" && fechaFinalizacion == ""
				&& pagoPrincipal == "" && pagoIntereses == "" && tipoInteres == "" && tipoCobros == ""
				&& periodicidad == "" && cantidadCobroPeriodico == "" && facility == "" && idBanco == "") {
			return findAll();
		}

		try {
			if (cantidadRestante != "") {
				Double.parseDouble(cantidadRestante);
			}
			if (pagoPrincipal != "") {
				Double.parseDouble(pagoPrincipal);
			}
			if (pagoIntereses != "") {
				Double.parseDouble(pagoIntereses);
			}
			if (tipoInteres != "") {
				Integer.parseInt(tipoInteres);
			}
			if (cantidadCobroPeriodico != "") {
				Double.parseDouble(cantidadCobroPeriodico);
			}
			if (facility != "") {
				Integer.parseInt(facility);
			}
		} catch (NumberFormatException e) {
			return findAll();
		}

		return outstandingCustom.filtradoOutstanding(cantidadRestante, fechaEfectiva, fechaCreacion, fechaFinalizacion,
				pagoPrincipal, pagoIntereses, tipoInteres, tipoCobros, periodicidad, cantidadCobroPeriodico, facility, idBanco);
	}

	@Override
	public Double obtenerSumaOutsandingFacility(int idFacility) {
		return outstandingDao.obtenerSumaOutstandingFacility(idFacility);
	}

}
