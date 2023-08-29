package com.example.demo.models.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.models.entity.OutstandingEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class OutstandingCustom {
	@PersistenceContext
	EntityManager em;

	public List<OutstandingEntity> filtradoOutstanding(String cantidadRestante, String fechaEfectiva,
			String fechaCreacion, String fechaFinalizacion, String pagoPrincipal, String pagoIntereses,
			String tipoInteres, String tipoCobros, String periodicidad, String cantidadCobroPeriodico,
			String facility) {
		String qcantidadRestante = cantidadRestante.equals("") ? "" : " o.cantidadRestante = :cantidadRestante";
		String qfechaEfectiva = fechaEfectiva.equals("") ? "" : " o.fechaEfectiva = :fechaEfectiva";
		String qfechaCreacion = fechaCreacion.equals("") ? "" : " o.fechaCreacion = :fechaCreacion";
		String qfechaFinalizacion = fechaFinalizacion.equals("") ? "" : " o.fechaFinalizacion = :fechaFinalizacion";
		String qpagoPrincipal = pagoPrincipal.equals("") ? "" : " o.pagoPrincipal = :pagoPrincipal";
		String qpagoIntereses = pagoIntereses.equals("") ? "" : " o.pagoIntereses = :pagoIntereses";
		String qtipoInteres = tipoInteres.equals("") ? "" : " o.tipoInteres = :tipoInteres";
		String qtipoCobros = tipoCobros.equals("") ? "" : " o.tipoCobros = :tipoCobros";
		String qperiodicidad = periodicidad.equals("") ? "" : " o.periodicidad = :periodicidad";
		String qcantidadCobroPeriodico = cantidadCobroPeriodico.equals("") ? "" : " o.cantidadCobroPeriodico = :cantidadCobroPeriodico";
		String qfacility = facility.equals("") ? "" : " o.facility = :facility";

		String query = "SELECT o FROM OutstandingEntity o WHERE";

		boolean x = false;
		if (!cantidadRestante.equals("")) {
			query += qcantidadRestante;
			x = true;
		}

		if (!fechaEfectiva.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qfechaEfectiva;
			x = true;
		}

		if (!fechaCreacion.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qfechaCreacion;
			x = true;
		}

		if (!fechaFinalizacion.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qfechaFinalizacion;
			x = true;
		}

		if (!pagoPrincipal.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qpagoPrincipal;
			x = true;
		}

		if (!pagoIntereses.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qpagoIntereses;
			x = true;
		}

		if (!tipoInteres.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qtipoInteres;
			x = true;
		}

		if (!tipoCobros.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qtipoCobros;
			x = true;
		}

		if (!periodicidad.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qperiodicidad;
			x = true;
		}

		if (!cantidadCobroPeriodico.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qcantidadCobroPeriodico;
			x = true;
		}

		if (!facility.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qfacility;
			x = true;
		}

		Query q = this.em.createQuery(query);

		if (!qcantidadRestante.equals("")) {
			q.setParameter("cantidadRestante", cantidadRestante);
		}

		if (!qfechaEfectiva.equals("")) {
			Date dateEfectiva = Date.valueOf(fechaEfectiva);
			q.setParameter("fechaEfectiva", dateEfectiva);
		}

		if (!qfechaCreacion.equals("")) {
			Date dateCreacion = Date.valueOf(fechaCreacion);
			q.setParameter("fechaCreacion", dateCreacion);
		}

		if (!qfechaFinalizacion.equals("")) {
			Date dateFinalizacion = Date.valueOf(fechaFinalizacion);
			q.setParameter("fechaFinalizacion", dateFinalizacion);
		}

		if (!qpagoPrincipal.equals("")) {
			q.setParameter("pagoPrincipal", pagoPrincipal);
		}

		if (!qpagoIntereses.equals("")) {
			q.setParameter("pagoIntereses", pagoIntereses);
		}

		if (!qtipoInteres.equals("")) {
			q.setParameter("tipoInteres", tipoInteres);
		}

		if (!qtipoCobros.equals("")) {
			q.setParameter("tipoCobros", tipoCobros);
		}

		if (!qperiodicidad.equals("")) {
			q.setParameter("periodicidad", periodicidad);
		}

		if (!qcantidadCobroPeriodico.equals("")) {
			q.setParameter("cantidadCobroPeriodico", cantidadCobroPeriodico);
		}

		if (!qfacility.equals("")) {
			q.setParameter("facility", facility);
		}

		return q.getResultList();
	}
}
