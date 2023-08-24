package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "libro_inversion", schema = "sql7641808", catalog = "")
public class LibroInversionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_libro_inversion")
    private int idLibroInversion;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "dinero")
    private double dinero;
    @Basic
    @Column(name = "pertenece_a")
    private int perteneceA;

    public int getIdLibroInversion() {
        return idLibroInversion;
    }

    public void setIdLibroInversion(int idLibroInversion) {
        this.idLibroInversion = idLibroInversion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public int getPerteneceA() {
        return perteneceA;
    }

    public void setPerteneceA(int perteneceA) {
        this.perteneceA = perteneceA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibroInversionEntity that = (LibroInversionEntity) o;
        return idLibroInversion == that.idLibroInversion && Double.compare(that.dinero, dinero) == 0 && perteneceA == that.perteneceA && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLibroInversion, nombre, dinero, perteneceA);
    }
}
