package com.example.demo.controller;

import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IBancoService;
import com.example.demo.models.service.IUsuarioService;
import com.example.demo.models.service.UsuarioServiceImpl;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IBancoService bancoService;

	@RequestMapping("")
	public String inicioUsuario(Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		UsuarioEntity user = (UsuarioEntity) session.getAttribute("usuario");
		if (!user.getRol().equals("ADMIN")) {
			flash.addFlashAttribute("error", "No tiene permiso para ver esa pagina");
			return "redirect:/";
		}

		UsuarioEntity usuario = new UsuarioEntity();
		model.put("usuario", usuario);

		List<UsuarioEntity> listaUsuarios = usuarioService.findAll();
		model.put("usuarios", listaUsuarios);

		List<BancoEntity> listaBancos = bancoService.findAll();
		model.put("bancos", listaBancos);

		return "usuario/menuUsuario";
	}

	@RequestMapping("/crearUsuario")
	public String crearUsuario(UsuarioEntity usuario, RedirectAttributes flash, HttpSession session) {
		UsuarioEntity user = (UsuarioEntity) session.getAttribute("usuario");
		if (!user.getRol().equals("ADMIN")) {
			flash.addFlashAttribute("error", "No tiene permiso para ver esa pagina");
			return "redirect:/";
		}

		UsuarioEntity prevUsuario = usuarioService.findByName(usuario.getNombre());
		if (prevUsuario != null) {
			flash.addFlashAttribute("error", "Ya existe un usuario con ese nombre");
			return "redirect:/usuario";
		}

		usuarioService.save(usuario);
		flash.addFlashAttribute("success", "Usuario " + usuario.getNombre() + " creado correctamente");
		return "redirect:/usuario";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") int id, RedirectAttributes flash, HttpSession session) {
		UsuarioEntity user = (UsuarioEntity) session.getAttribute("usuario");
		if (!user.getRol().equals("ADMIN")) {
			flash.addFlashAttribute("error", "No tiene permiso para ver esa pagina");
			return "redirect:/";
		}

		UsuarioEntity prevUsuario = usuarioService.findOne(id);
		if (prevUsuario == null) {
			flash.addFlashAttribute("error", "El usuario que quiere borrar no existe");
			return "redirect:/usuario";
		}

		if (id == user.getIdUsuario()) {
			flash.addFlashAttribute("error", "No puede eliminarse a si mismo");
			return "redirect:/usuario";
		}

		usuarioService.delete(id);
		flash.addFlashAttribute("success", "Usuario eliminado correctamente");
		return "redirect:/usuario";
	}

	@RequestMapping("/formEditar/{id}")
	public String eliminar(@PathVariable(value = "id") int id, Map<String, Object> model, RedirectAttributes flash,
			HttpSession session) {
		UsuarioEntity user = (UsuarioEntity) session.getAttribute("usuario");
		if (!user.getRol().equals("ADMIN")) {
			flash.addFlashAttribute("error", "No tiene permiso para ver esa pagina");
			return "redirect:/";
		}

		UsuarioEntity usuario = usuarioService.findOne(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El usuario que quiere editar no existe");
			return "redirect:/usuario";
		}

		model.put("usuario", usuario);

		List<BancoEntity> listaBancos = bancoService.findAll();
		model.put("bancos", listaBancos);

		return "usuario/editarUsuario";
	}

	@RequestMapping(value = "/editarUsuario", method = RequestMethod.POST)
	public String eliminar(UsuarioEntity usuario, RedirectAttributes flash, HttpSession session) {
		UsuarioEntity user = (UsuarioEntity) session.getAttribute("usuario");
		if (!user.getRol().equals("ADMIN")) {
			flash.addFlashAttribute("error", "No tiene permiso para ver esa pagina");
			return "redirect:/";
		}

		usuarioService.save(usuario);
		flash.addFlashAttribute("success", "Usuario " + usuario.getNombre() + " editado correctamente");
		return "redirect:/usuario";
	}
}
