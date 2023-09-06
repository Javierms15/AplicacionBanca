package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.entity.NotificacionEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.INotificacionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/notificacion")
public class NotificacionController {

	@Autowired
	private INotificacionService notificacionService;

	@GetMapping({ "", "/" })
	public String ver(Model model, HttpSession session) {
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		List<NotificacionEntity> notificaciones = notificacionService.findByReciever(usuario.getIdUsuario());
		model.addAttribute("notificaciones", notificaciones);
		return "notificacion_ver";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable int id, RedirectAttributes flash, HttpSession session) {
		NotificacionEntity notificacion = notificacionService.findOne(id);
		if (notificacion == null) {
			flash.addFlashAttribute("error", "Ha intentado eliminar una notificacion que no existe");
			return "redirect:/notificacion";
		}
		
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (notificacion.getEnviadoA() != usuario.getIdUsuario()) {
			flash.addFlashAttribute("error", "No puede eliminar esa notificacion");
			return "redirect:/notificacion";
		}

		notificacionService.delete(id);
		return "redirect:/notificacion";
	}
}
