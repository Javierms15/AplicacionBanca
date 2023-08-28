package com.example.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.OutstandingEntity;

public interface IOutstandingDao extends JpaRepository<OutstandingEntity, Integer> {

}
