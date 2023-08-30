package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.IParticipanteDao;
import com.example.demo.models.entity.ParticipanteEntity;

@Service
public class ParticipanteServiceImpl implements IParticipanteService {

	@Autowired
	private IParticipanteDao participanteDao;

	@Override
	public List<ParticipanteEntity> findAll() {
		return participanteDao.findAll();
	}

	@Override
	public ParticipanteEntity findOne(int id) {
		return participanteDao.findById(id).orElse(null);
	}

	@Override
	public void save(ParticipanteEntity participante) {
		participanteDao.save(participante);
	}

	@Override
	public void delete(int id) {
		participanteDao.deleteById(id);
	}

	@Override
	public List<ParticipanteEntity> findAllByDeal(int idDeal) {
		return participanteDao.findByIdDeal(idDeal);
	}

}
