package com.example.demo.models.dao;

import com.example.demo.models.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUsuarioDao extends CrudRepository<UsuarioEntity, Integer> {

    public UsuarioEntity findUsuarioEntityByNombreAndAndContrasena(String nombre, String contrasena);

}
