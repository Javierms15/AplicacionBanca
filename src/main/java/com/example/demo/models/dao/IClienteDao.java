package com.example.demo.models.dao;

import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IClienteDao extends JpaRepository<ClienteEntity,Integer> {

    @Query("select c from ClienteEntity c where c.nombreLegal like :nombre")
    public List<ClienteEntity> findByNombre(String nombre);

    @Query("select c from ClienteEntity c where c.direccionLegal like :direccion")
    public List<ClienteEntity> findByDireccion(String direccion);

    @Query("select c from ClienteEntity c where c.dinero =:dinero")
    public List<ClienteEntity> findByCapital(Double dinero);

    @Query("select c from ClienteEntity c where c.email like :email")
    public List<ClienteEntity> findByEmail(String email);

    @Query("select c from ClienteEntity c where c.idBanco = :banco")
    public List<ClienteEntity> findByBanco(int banco);

}
