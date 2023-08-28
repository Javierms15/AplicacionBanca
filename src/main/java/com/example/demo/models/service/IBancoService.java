package com.example.demo.models.service;

import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.UsuarioEntity;

import java.util.List;

public interface IBancoService {

    List<BancoEntity> findAll();

    BancoEntity findOne(int id);

    void save (BancoEntity banco);

    void delete(int id);

}
