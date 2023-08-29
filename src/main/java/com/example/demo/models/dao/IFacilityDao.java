package com.example.demo.models.dao;

import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFacilityDao extends JpaRepository<FacilityEntity, Integer> {

    @Query("select sum(f.cantidad) from FacilityEntity f where f.deal = :idDeal")
    public Double obtenerSumaFacilityDeal(int idDeal);
}
