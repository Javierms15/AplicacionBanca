package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "sql7641808", catalog = "")
public class UsuarioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario")
    private int idUsuario;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "rol")
    private Object rol;
    @Basic
    @Column(name = "banco")
    private Integer banco;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Object getRol() {
        return rol;
    }

    public void setRol(Object rol) {
        this.rol = rol;
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return idUsuario == that.idUsuario && Objects.equals(nombre, that.nombre) && Objects.equals(contrasena, that.contrasena) && Objects.equals(rol, that.rol) && Objects.equals(banco, that.banco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, nombre, contrasena, rol, banco);
    }
}
