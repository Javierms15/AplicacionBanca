package com.example.demo.models.dao;

import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class FacilityCustom {

    @PersistenceContext
    EntityManager em;

    public List<FacilityEntity> filtradoFacility(String id, String tipo, String estado, String cantidad,
                                             String fechaCreacion, String fechaEfectiva, String fechaFinalizacion, String deal) {
        String qid = "";
        String qtipo = "";
        String qestado = "";
        String qcantidad = "";
        String qfechaCreacion = "";
        String qfechaEfectiva = "";
        String qfechaFinalizacion = "";
        String qdeal = "";

        if (!id.equals("")) {
            qestado = " d.idFacility like :id";
        }
        if (!tipo.equals("")) {
            qtipo = " d.tipo like :tipo";
        }
        if (!estado.equals("")) {
            qestado = " d.estado = :estado";
        }
        if (!cantidad.equals("")) {
            qcantidad = " d.cantidad = :cantidad";
        }
        if (!fechaCreacion.equals("")) {
            qfechaCreacion = " d.fechaCreacion = :fechaCreacion";
        }
        if (!fechaEfectiva.equals("")) {
            qfechaEfectiva = " d.fechaEfectiva = :fechaEfectiva";
        }
        if (!fechaFinalizacion.equals("")) {
            qfechaFinalizacion = " d.fechaFinalizacion = :fechaFinalizacion";
        }
        if (!deal.equals("")) {
            qdeal = " d.deal = :deal";
        }

        String query = "SELECT d FROM FacilityEntity d WHERE";

        boolean x = false;
        if (!id.equals("")) {
            query += qid;
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

        if (!estado.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qestado;
            x = true;
        }

        if (!cantidad.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qcantidad;
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

        if (!fechaEfectiva.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qfechaEfectiva;
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

        if (!deal.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qdeal;
            x = true;
        }

        Query q = this.em.createQuery(query);
        if (!qid.equals("")) {
            q.setParameter("id", id);
        }

        if (!qtipo.equals("")) {
            q.setParameter("tipo", tipo);
        }

        if (!qestado.equals("")) {
            q.setParameter("estado", estado);
        }

        if (!qcantidad.equals("")) {
            q.setParameter("cantidad", cantidad);
        }

        if (!qfechaCreacion.equals("")) {
            q.setParameter("fechaCreacion", fechaCreacion);
        }

        if (!qfechaFinalizacion.equals("")) {
            q.setParameter("fechaFinalizacion", fechaFinalizacion);
        }

        if (!qfechaEfectiva.equals("")) {
            q.setParameter("fechaEfectiva", fechaEfectiva);
        }

        if (!qdeal.equals("")) {
            q.setParameter("deal", deal);
        }

        return q.getResultList();
    }


}
