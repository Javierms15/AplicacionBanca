package com.example.demo.models.dao;

import com.example.demo.models.entity.BancoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IBancoDao extends CrudRepository<BancoEntity,Integer> {

    @Query("select b from BancoEntity b where b.nombre like :nombre")
    public BancoEntity findByNombre(String nombre);


}
