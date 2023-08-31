package com.example.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.DealEntity;

public interface IDealDao extends JpaRepository<DealEntity, Integer> {

}
