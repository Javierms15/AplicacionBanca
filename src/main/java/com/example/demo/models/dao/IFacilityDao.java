package com.example.demo.models.dao;

import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

public interface IFacilityDao extends CrudRepository<FacilityEntity, Integer> {
}
