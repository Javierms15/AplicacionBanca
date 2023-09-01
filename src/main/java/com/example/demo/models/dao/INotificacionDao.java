package com.example.demo.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.NotificacionEntity;

public interface INotificacionDao extends JpaRepository<NotificacionEntity, Integer> {

	List<NotificacionEntity> findByEnviadoA(int enviadoA);
}
