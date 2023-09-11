package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "facility", schema = "sql7641808", catalog = "")
public class FacilityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_facility")
    private int idFacility;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @Basic
    @Column(name = "estado")
    private String estado;
    @Basic
    @Column(name = "cantidad")
    private double cantidad;
    @Basic
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Basic
    @Column(name = "fecha_efectiva")
    private Date fechaEfectiva;
    @Basic
    @Column(name = "fecha_finalizacion")
    private Date fechaFinalizacion;
    @Basic
    @Column(name = "deal")
    private int deal;

    public int getIdFacility() {
        return idFacility;
    }

    public void setIdFacility(int idFacility) {
        this.idFacility = idFacility;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(Date fechaEfectiva) {
        this.fechaEfectiva = fechaEfectiva;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public int getDeal() {
        return deal;
    }

    public void setDeal(int deal) {
        this.deal = deal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacilityEntity that = (FacilityEntity) o;
        return idFacility == that.idFacility && Double.compare(that.cantidad, cantidad) == 0 && deal == that.deal && Objects.equals(tipo, that.tipo) && Objects.equals(estado, that.estado) && Objects.equals(fechaCreacion, that.fechaCreacion) && Objects.equals(fechaEfectiva, that.fechaEfectiva) && Objects.equals(fechaFinalizacion, that.fechaFinalizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFacility, tipo, estado, cantidad, fechaCreacion, fechaEfectiva, fechaFinalizacion, deal);
    }

    @Override
    public String toString() {
        return "idFacility=" + idFacility +
                "&tipo=" + tipo +
                "&estado=" + estado +
                "&cantidad=" + cantidad +
                "&fechaCreacion=" + fechaCreacion +
                "&fechaEfectiva=" + fechaEfectiva +
                "&fechaFinalizacion=" + fechaFinalizacion +
                "&deal=" + deal;
    }
}
