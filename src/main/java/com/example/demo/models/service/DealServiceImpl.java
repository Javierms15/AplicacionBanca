package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.entity.DealEntity;

@Service
public class DealServiceImpl implements IDealService {

	@Autowired
	private IDealDao dealDao;

	@Override
	@Transactional(readOnly = true)
	public List<DealEntity> findAll() {
		return dealDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public DealEntity findOne(int id) {
		return dealDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(DealEntity deal) {
		dealDao.save(deal);
	}

	@Override
	@Transactional
	public void delete(int id) {
		dealDao.deleteById(id);
	}

}
