package com.example.demo.models.dao;

import com.example.demo.models.entity.ClienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<ClienteEntity,Integer> {

}
