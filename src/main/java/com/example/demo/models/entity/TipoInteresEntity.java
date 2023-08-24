package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tipo_interes", schema = "sql7641808", catalog = "")
public class TipoInteresEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipo_interes")
    private int idTipoInteres;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "valor")
    private double valor;

    public int getIdTipoInteres() {
        return idTipoInteres;
    }

    public void setIdTipoInteres(int idTipoInteres) {
        this.idTipoInteres = idTipoInteres;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoInteresEntity that = (TipoInteresEntity) o;
        return idTipoInteres == that.idTipoInteres && Double.compare(that.valor, valor) == 0 && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoInteres, nombre, valor);
    }
}
