package com.example.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.OutstandingEntity;
import org.springframework.data.jpa.repository.Query;

public interface IOutstandingDao extends JpaRepository<OutstandingEntity, Integer> {

    @Query("select sum(o.cantidadRestante) from OutstandingEntity o where o.facility = :idFacility")
    public Double obtenerSumaOutstandingFacility(int idFacility);

}
