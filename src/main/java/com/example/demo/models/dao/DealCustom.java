package com.example.demo.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.models.entity.DealEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class DealCustom {
	@PersistenceContext
	EntityManager em;

	public List<DealEntity> filtradoDeal(String estado, String moneda, String tipo, String cliente,
			String cantidadPrestamo, String cantidadAbonada, String cantidadAPagar, String descuento) {
		String qestado = "";
		String qmoneda = "";
		String qtipo = "";
		String qcliente = "";
		String qcantidadPrestamo = "";
		String qcantidadAbonada = "";
		String qcantidadAPagar = "";
		String qdescuento = "";

		if (!estado.equals("")) {
			qestado = " d.estado like :estado";
		}
		if (!moneda.equals("")) {
			qmoneda = " d.moneda like :moneda";
		}
		if (!tipo.equals("")) {
			qtipo = " d.tipo like :tipo";
		}
		if (!cliente.equals("")) {
			qcliente = " d.cliente like :cliente";
		}
		if (!cantidadPrestamo.equals("")) {
			qcantidadPrestamo = " d.cantidadPrestamo = :cantidadPrestamo";
		}
		if (!cantidadAbonada.equals("")) {
			qcantidadAbonada = " d.cantidadAbonada = :cantidadAbonada";
		}
		if (!cantidadAPagar.equals("")) {
			qcantidadAPagar = " d.cantidadAPagar = :cantidadAPagar";
		}
		if (!descuento.equals("")) {
			qdescuento = " d.descuento = :descuento";
		}
		String query = "SELECT d FROM DealEntity d WHERE";

		boolean x = false;
		if (!estado.equals("")) {
			query += qestado;
			x = true;
		}

		if (!moneda.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qmoneda;
			x = true;
		}

		if (!tipo.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qtipo;
			x = true;
		}

		if (!cliente.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qcliente;
			x = true;
		}

		if (!cantidadPrestamo.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qcantidadPrestamo;
			x = true;
		}

		if (!cantidadAbonada.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qcantidadAbonada;
			x = true;
		}

		if (!cantidadAPagar.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qcantidadAPagar;
			x = true;
		}

		if (!descuento.equals("")) {
			if (x) {
				query += " AND ";
				x = false;
			}

			query += qdescuento;
			x = true;
		}

		Query q = this.em.createQuery(query);
		if (!qestado.equals("")) {
			q.setParameter("estado", estado);
		}

		if (!qmoneda.equals("")) {
			q.setParameter("moneda", moneda);
		}

		if (!qtipo.equals("")) {
			q.setParameter("tipo", tipo);
		}

		if (!qcliente.equals("")) {
			q.setParameter("cliente", cliente);
		}

		if (!qcantidadPrestamo.equals("")) {
			q.setParameter("cantidadPrestamo", cantidadPrestamo);
		}

		if (!qcantidadAbonada.equals("")) {
			q.setParameter("cantidadAbonada", cantidadAbonada);
		}

		if (!qcantidadAPagar.equals("")) {
			q.setParameter("cantidadAPagar", cantidadAPagar);
		}

		if (!qdescuento.equals("")) {
			q.setParameter("descuento", descuento);
		}

		return q.getResultList();
	}
}
