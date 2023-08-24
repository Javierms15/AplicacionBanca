package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "outstanding", schema = "sql7641808", catalog = "")
public class OutstandingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_outstanding")
    private int idOutstanding;
    @Basic
    @Column(name = "cantidad_restante")
    private double cantidadRestante;
    @Basic
    @Column(name = "fecha_efectiva")
    private Date fechaEfectiva;
    @Basic
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Basic
    @Column(name = "fecha_finalizacion")
    private Date fechaFinalizacion;
    @Basic
    @Column(name = "pago_principal")
    private double pagoPrincipal;
    @Basic
    @Column(name = "pago_intereses")
    private double pagoIntereses;
    @Basic
    @Column(name = "tipo_interes")
    private int tipoInteres;
    @Basic
    @Column(name = "tipo_cobros")
    private Object tipoCobros;
    @Basic
    @Column(name = "periodicidad")
    private Object periodicidad;
    @Basic
    @Column(name = "cantidad_cobro_periodico")
    private double cantidadCobroPeriodico;
    @Basic
    @Column(name = "facility")
    private int facility;

    public int getIdOutstanding() {
        return idOutstanding;
    }

    public void setIdOutstanding(int idOutstanding) {
        this.idOutstanding = idOutstanding;
    }

    public double getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(double cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    public Date getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(Date fechaEfectiva) {
        this.fechaEfectiva = fechaEfectiva;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public double getPagoPrincipal() {
        return pagoPrincipal;
    }

    public void setPagoPrincipal(double pagoPrincipal) {
        this.pagoPrincipal = pagoPrincipal;
    }

    public double getPagoIntereses() {
        return pagoIntereses;
    }

    public void setPagoIntereses(double pagoIntereses) {
        this.pagoIntereses = pagoIntereses;
    }

    public int getTipoInteres() {
        return tipoInteres;
    }

    public void setTipoInteres(int tipoInteres) {
        this.tipoInteres = tipoInteres;
    }

    public Object getTipoCobros() {
        return tipoCobros;
    }

    public void setTipoCobros(Object tipoCobros) {
        this.tipoCobros = tipoCobros;
    }

    public Object getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Object periodicidad) {
        this.periodicidad = periodicidad;
    }

    public double getCantidadCobroPeriodico() {
        return cantidadCobroPeriodico;
    }

    public void setCantidadCobroPeriodico(double cantidadCobroPeriodico) {
        this.cantidadCobroPeriodico = cantidadCobroPeriodico;
    }

    public int getFacility() {
        return facility;
    }

    public void setFacility(int facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutstandingEntity that = (OutstandingEntity) o;
        return idOutstanding == that.idOutstanding && Double.compare(that.cantidadRestante, cantidadRestante) == 0 && Double.compare(that.pagoPrincipal, pagoPrincipal) == 0 && Double.compare(that.pagoIntereses, pagoIntereses) == 0 && tipoInteres == that.tipoInteres && Double.compare(that.cantidadCobroPeriodico, cantidadCobroPeriodico) == 0 && facility == that.facility && Objects.equals(fechaEfectiva, that.fechaEfectiva) && Objects.equals(fechaCreacion, that.fechaCreacion) && Objects.equals(fechaFinalizacion, that.fechaFinalizacion) && Objects.equals(tipoCobros, that.tipoCobros) && Objects.equals(periodicidad, that.periodicidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOutstanding, cantidadRestante, fechaEfectiva, fechaCreacion, fechaFinalizacion, pagoPrincipal, pagoIntereses, tipoInteres, tipoCobros, periodicidad, cantidadCobroPeriodico, facility);
    }
}
