package com.example.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.ParticipanteEntity;

public interface IParticipanteDao extends JpaRepository<ParticipanteEntity, Integer> {

}
