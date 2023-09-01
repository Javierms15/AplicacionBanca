package com.example.demo.models.service;

import java.util.List;

import com.example.demo.models.entity.NotificacionEntity;

public interface INotificacionService {
	
	List<NotificacionEntity> findAll();
	
	NotificacionEntity findOne(int id);
	
	void save(NotificacionEntity notificacion);
	
	void delete(int id);
	
	List<NotificacionEntity> findByReciever(int idUsuario);
}
