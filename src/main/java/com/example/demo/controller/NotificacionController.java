package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.entity.NotificacionEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.INotificacionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/notificacion")
public class NotificacionController {
	
	@Autowired
	private INotificacionService notificacionService;
	
	@GetMapping({"", "/"})
	public String ver(Model model, HttpSession session) {
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		List<NotificacionEntity> notificaciones = notificacionService.findByReciever(usuario.getIdUsuario());
		model.addAttribute("notificaciones", notificaciones);
		return "notificacion_ver";
	}
}
