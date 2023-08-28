package com.example.demo.models.dao;

import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFacilityDao extends CrudRepository<FacilityEntity, Integer> {

    @Query("select sum(f.cantidad) from FacilityEntity f where f.deal = :idDeal")
    public double obtenerSumaFacilityDeal(int idDeal);
}
