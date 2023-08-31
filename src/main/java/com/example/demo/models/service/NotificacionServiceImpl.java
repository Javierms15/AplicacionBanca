package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.INotificacionDao;
import com.example.demo.models.entity.NotificacionEntity;

@Service
public class NotificacionServiceImpl implements INotificacionService {

	@Autowired
	private INotificacionDao notificacionDao;

	@Override
	public List<NotificacionEntity> findAll() {
		return notificacionDao.findAll();
	}

	@Override
	public NotificacionEntity findOne(int id) {
		return notificacionDao.findById(id).orElse(null);
	}

	@Override
	public void save(NotificacionEntity notificacion) {
		notificacionDao.save(notificacion);
	}

	@Override
	public void delete(int id) {
		notificacionDao.deleteById(id);
	}

}
