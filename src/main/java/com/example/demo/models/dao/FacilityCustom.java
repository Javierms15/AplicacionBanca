package com.example.demo.models.dao;

import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
@Repository
public class FacilityCustom {

    @PersistenceContext
    EntityManager em;

    public List<FacilityEntity> filtradoFacility(String tipo, String estado, String cantidad,
                                             String fechaCreacion, String fechaEfectiva, String fechaFinalizacion, String deal, String idBanco) {

        String qtipo = "";
        String qestado = "";
        String qcantidad = "";
        String qfechaCreacion = "";
        String qfechaEfectiva = "";
        String qfechaFinalizacion = "";
        String qdeal = "";


        if (!tipo.equals("")) {
            qtipo = " f.tipo like :tipo";
        }
        if (!estado.equals("")) {
            qestado = " f.estado = :estado";
        }
        if (!cantidad.equals("")) {
            qcantidad = " f.cantidad = :cantidad";
        }
        if (!fechaCreacion.equals("")) {
            qfechaCreacion = " f.fechaCreacion = :fechaCreacion";
        }
        if (!fechaEfectiva.equals("")) {
            qfechaEfectiva = " f.fechaEfectiva = :fechaEfectiva";
        }
        if (!fechaFinalizacion.equals("")) {
            qfechaFinalizacion = " f.fechaFinalizacion = :fechaFinalizacion";
        }
        if (!deal.equals("")) {
            qdeal = " f.deal = :deal";
        }

        String qidBanco = idBanco.equals("") ? " WHERE "
                : "JOIN ClienteEntity c on c.idCliente = d.cliente and c.idBanco = :idBanco WHERE d.idDeal = f.deal";


        String query = "SELECT f FROM FacilityEntity f, DealEntity d ";



        if (!(tipo == "" && estado == "" && cantidad == "" && fechaCreacion == "" && fechaEfectiva == ""
                && fechaFinalizacion == "" && deal == "")) {
            if (!idBanco.equals("")) {
                query += qidBanco;
            }
        } else {
            if (!idBanco.equals("")) {
                query += qidBanco;
            } else {
                throw new RuntimeException("No deberia ejecutarse esto");
            }
        }

        boolean x = idBanco.equals("")? true : false;

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
            Date fechaCreacion2 = Date.valueOf(fechaCreacion);
            q.setParameter("fechaCreacion", fechaCreacion2);
        }

        if (!qfechaFinalizacion.equals("")) {
            Date fechaFinalizacion2 = Date.valueOf(fechaFinalizacion);
            q.setParameter("fechaFinalizacion", fechaFinalizacion2);
        }

        if (!qfechaEfectiva.equals("")) {
            Date fechaEfectiva2 = Date.valueOf(fechaEfectiva);
            q.setParameter("fechaEfectiva", fechaEfectiva2);
        }

        if (!qdeal.equals("")) {
            q.setParameter("deal", deal);
        }

        if (!idBanco.equals("")) {
            q.setParameter("idBanco", idBanco);
        }

        return q.getResultList();
    }


}
