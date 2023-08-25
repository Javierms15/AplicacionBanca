package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.DealEntity;

public interface IDealService {
	List<DealEntity> findAll();
	
	DealEntity findOne(int id);
	
	void save(DealEntity deal);
	
	void delete(int id);
}
