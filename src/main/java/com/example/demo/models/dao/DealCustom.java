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
			String cantidadPrestamo, String cantidadAbonada, String cantidadAPagar, String descuento, String idBanco) {
		String qestado = estado.equals("") ? "" : " d.estado = :estado";
		String qmoneda = moneda.equals("") ? "" : " d.moneda = :moneda";
		String qtipo = tipo.equals("") ? "" : " d.tipo = :tipo";
		String qcliente = cliente.equals("") ? "" : " d.cliente = :cliente";
		String qcantidadPrestamo = cantidadPrestamo.equals("") ? "" : " d.cantidadPrestamo = :cantidadPrestamo";
		String qcantidadAbonada = cantidadAbonada.equals("") ? "" : " d.cantidadAbonada = :cantidadAbonada";
		String qcantidadAPagar = cantidadAPagar.equals("") ? "" : " d.cantidadAPagar = :cantidadAPagar";
		String qdescuento = descuento.equals("") ? "" : " d.descuento = :descuento";
		String qidBanco = idBanco.equals("") ? "" : " JOIN ClienteEntity c on c.idBanco = :idBanco and c.idCliente = d.cliente";

		String query;

		if (estado == "" && moneda == "" && tipo == "" && cliente == "" && cantidadPrestamo == ""
				&& cantidadAbonada == "" && cantidadAPagar == "" && descuento == "") {
			query = "SELECT d FROM DealEntity d ";
		}else{
			query = "SELECT d FROM DealEntity d WHERE";
		}

		boolean x = false;
		if (!estado.equals("")) {
			query += qestado;
			x = true;
		}

		if (!moneda.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qmoneda;
			x = true;
		}

		if (!tipo.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qtipo;
			x = true;
		}

		if (!cliente.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qcliente;
			x = true;
		}

		if (!cantidadPrestamo.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qcantidadPrestamo;
			x = true;
		}

		if (!cantidadAbonada.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qcantidadAbonada;
			x = true;
		}

		if (!cantidadAPagar.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qcantidadAPagar;
			x = true;
		}

		if (!descuento.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qdescuento;
			x = true;
		}

		if (!idBanco.equals("")) {
			if (x) {
				query += " AND ";
			}

			query += qidBanco;
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

		if (!qidBanco.equals("")) {
			q.setParameter("idBanco", idBanco);
		}

		return q.getResultList();
	}

}
