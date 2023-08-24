package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "sql7641808", catalog = "")
public class ClienteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "nombre_legal")
    private String nombreLegal;
    @Basic
    @Column(name = "direccion_legal")
    private String direccionLegal;
    @Basic
    @Column(name = "dinero")
    private double dinero;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "id_banco")
    private int idBanco;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public String getDireccionLegal() {
        return direccionLegal;
    }

    public void setDireccionLegal(String direccionLegal) {
        this.direccionLegal = direccionLegal;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return idCliente == that.idCliente && Double.compare(that.dinero, dinero) == 0 && idBanco == that.idBanco && Objects.equals(nombreLegal, that.nombreLegal) && Objects.equals(direccionLegal, that.direccionLegal) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, nombreLegal, direccionLegal, dinero, email, idBanco);
    }
}
