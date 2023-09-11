package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "transaccion", schema = "sql7641808", catalog = "")
public class TransaccionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transaccion")
    private int idTransaccion;
    @Basic
    @Column(name = "cantidad")
    private double cantidad;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "id_banco")
    private int idBanco;
    @Basic
    @Column(name = "tipo_transaccion")
    private String tipoTransaccion;
    @Basic
    @Column(name = "outstanding")
    private int outstanding;

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public int getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(int outstanding) {
        this.outstanding = outstanding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransaccionEntity that = (TransaccionEntity) o;
        return idTransaccion == that.idTransaccion && Double.compare(that.cantidad, cantidad) == 0 && idCliente == that.idCliente && idBanco == that.idBanco && outstanding == that.outstanding && Objects.equals(tipoTransaccion, that.tipoTransaccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaccion, cantidad, idCliente, idBanco, tipoTransaccion, outstanding);
    }
}
