package com.example.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.NotificacionEntity;

public interface INotificacionDao extends JpaRepository<NotificacionEntity, Integer> {

}
