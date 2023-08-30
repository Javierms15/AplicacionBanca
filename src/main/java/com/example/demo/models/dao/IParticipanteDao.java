package com.example.demo.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.ParticipanteEntity;

public interface IParticipanteDao extends JpaRepository<ParticipanteEntity, Integer> {

	List<ParticipanteEntity> findByIdDeal(int idDeal);
}
