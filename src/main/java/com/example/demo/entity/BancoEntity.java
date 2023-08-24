package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "banco", schema = "sql7641808", catalog = "")
public class BancoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_banco")
    private int idBanco;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BancoEntity that = (BancoEntity) o;
        return idBanco == that.idBanco && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBanco, nombre);
    }
}
