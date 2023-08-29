package com.example.demo.models.dao;

import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.DealEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class ClienteCustom {

    @PersistenceContext
    EntityManager em;

    public List<ClienteEntity> filtradoCliente(String idCliente, String nombreLegal, String direccionLegal, String dinero,
                                            String email, String idBanco) {
        String qidCliente = "";
        String qnombreLegal = "";
        String qdireccionLegal= "";
        String qdinero = "";
        String qemail = "";
        String qidBanco = "";


        if (!idCliente.equals("")) {
            qidCliente = " d.idCliente like :idCliente";
        }
        if (!nombreLegal.equals("")) {
            qnombreLegal = " d.nombreLegal like :nombreLegal";
        }
        if (!direccionLegal.equals("")) {
            qdireccionLegal = " d.direccionLegal like :direccionLegal";
        }
        if (!dinero.equals("")) {
            qdinero = " d.dinero = :dinero";
        }
        if (!email.equals("")) {
            qemail = " d.email = :email";
        }
        if (!idBanco.equals("")) {
            qidBanco = " d.idBanco = :idBanco";
        }

        String query = "SELECT d FROM ClienteEntity d WHERE";

        boolean x = false;
        if (!idCliente.equals("")) {
            query += qidCliente;
            x = true;
        }

        if (!nombreLegal.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qnombreLegal;
            x = true;
        }

        if (!direccionLegal.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qdireccionLegal;
            x = true;
        }

        if (!dinero.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qdinero;
            x = true;
        }

        if (!email.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qemail;
            x = true;
        }

        if (!idBanco.equals("")) {
            if (x) {
                query += " AND ";
                x = false;
            }

            query += qidBanco;
            x = true;
        }


        Query q = this.em.createQuery(query);
        if (!qidCliente.equals("")) {
            q.setParameter("idCliente", idCliente);
        }

        if (!qnombreLegal.equals("")) {
            q.setParameter("nombreLegal", nombreLegal);
        }

        if (!qdireccionLegal.equals("")) {
            q.setParameter("direccionLegal", direccionLegal);
        }

        if (!qdinero.equals("")) {
            q.setParameter("dinero", dinero);
        }

        if (!qemail.equals("")) {
            q.setParameter("email", email);
        }

        if (!qidBanco.equals("")) {
            q.setParameter("idBanco", idBanco);
        }



        return q.getResultList();
    }


}
