package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.ParticipanteEntity;

public interface IParticipanteService {

	List<ParticipanteEntity> findAll();

	ParticipanteEntity findOne(int id);

	void save(ParticipanteEntity participante);

	void delete(int id);

	List<ParticipanteEntity> findAllByDeal(int idDeal);
}
