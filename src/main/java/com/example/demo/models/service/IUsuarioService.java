package com.example.demo.models.service;

import com.example.demo.models.entity.UsuarioEntity;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioEntity> findAll();

    UsuarioEntity findOne(int id);

    void save (UsuarioEntity usuario);

    void delete(int id);

}
