package com.example.demo.models.dao;

import com.example.demo.models.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.DealEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDealDao extends JpaRepository<DealEntity, Integer> {

}
