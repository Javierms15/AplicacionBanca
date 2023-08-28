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
		String qcantidadRestante = "";
		String qfechaEfectiva = "";
		String qfechaCreacion = "";
		String qfechaFinalizacion = "";
		String qpagoPrincipal = "";
		String qpagoIntereses = "";
		String qtipoInteres = "";
		String qtipoCobros = "";
		String qperiodicidad = "";
		String qcantidadCobroPeriodico = "";
		String qfacility = "";

		if (!cantidadRestante.equals("")) {
			qcantidadRestante = " o.cantidadRestante = :cantidadRestante";
		}
		if (!fechaEfectiva.equals("")) {
			qfechaEfectiva = " o.fechaEfectiva = :fechaEfectiva";
		}
		if (!fechaCreacion.equals("")) {
			qfechaCreacion = " o.fechaCreacion = :fechaCreacion";
		}
		if (!fechaFinalizacion.equals("")) {
			qfechaFinalizacion = " o.fechaFinalizacion = :fechaFinalizacion";
		}
		if (!pagoPrincipal.equals("")) {
			qpagoPrincipal = " o.pagoPrincipal = :pagoPrincipal";
		}
		if (!pagoIntereses.equals("")) {
			qpagoIntereses = " o.pagoIntereses = :pagoIntereses";
		}
		if (!tipoInteres.equals("")) {
			qtipoInteres = " o.tipoInteres = :tipoInteres";
		}
		if (!tipoCobros.equals("")) {
			qtipoCobros = " o.tipoCobros = :tipoCobros";
		}
		if (!periodicidad.equals("")) {
			qperiodicidad = " o.periodicidad = :periodicidad";
		}
		if (!cantidadCobroPeriodico.equals("")) {
			qcantidadCobroPeriodico = " o.cantidadCobroPeriodico = :cantidadCobroPeriodico";
		}
		if (!facility.equals("")) {
			qfacility = " o.facility = :facility";
		}
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
