package com.example.demo.models.service;

import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.entity.UsuarioEntity;

import java.util.List;

public interface IFacilityService {

    List<FacilityEntity> findAll();

    FacilityEntity findOne(int id);

    void save(FacilityEntity deal);

    void delete(int id);

    List<FacilityEntity> filter(String tipo, String estado, String cantidad, String fechaCreacion,
                            String fechaEfectiva, String fechaFinalizacion, String deal, String idBanco);

    List<FacilityEntity> findByBancoUsuario(UsuarioEntity usuario);
}
