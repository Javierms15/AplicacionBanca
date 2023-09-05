package com.example.demo.controller;

import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entity.*;
import com.example.demo.models.service.IClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ClienteController {

	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IBancoDao bancoDao;

	@RequestMapping("/nuevoCliente")
	public String mostrarPantallaNuevoCliente(Model model) {
		ClienteEntity cliente = new ClienteEntity();
		List<BancoEntity> bancos = (List<BancoEntity>) bancoDao.findAll();
		model.addAttribute("bancos", bancos);
		model.addAttribute("cliente", cliente);
		return "cliente/cliente";
	}

	@PostMapping("/crearCliente")
	public String crearCliente(Model model, ClienteEntity cliente, RedirectAttributes flash) {
		clienteDao.save(cliente);
		flash.addFlashAttribute("success", "Cliente " + cliente.getNombreLegal() + " creado correctamente");

		return "redirect:/listarClientes";
	}

	@RequestMapping("/listarClientes")
	public String mostrarClientes(Model model, HttpSession session) {

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (usuario.getRol().equals("ADMIN")) {
			List<ClienteEntity> clientes = clienteService.findAll();
			model.addAttribute("clientes", clientes);
		} else {
			List<ClienteEntity> clientes = clienteService.filter("", "", "", "", usuario.getBanco().toString(), true);
			model.addAttribute("clientes", clientes);
		}

		List<BancoEntity> bancos = (List<BancoEntity>) bancoDao.findAll();
		model.addAttribute("bancos", bancos);
		ClienteFilter filter = new ClienteFilter();
		model.addAttribute("filter", filter);
		return "cliente/listaClientes";
	}

	@RequestMapping("eliminarCliente/{id}")
	public String eliminar(@PathVariable(value = "id") int id, RedirectAttributes flash) {
		ClienteEntity cliente = clienteDao.findById(id).orElse(null);
		clienteDao.delete(cliente);
		flash.addFlashAttribute("success", "Cliente eliminado correctamente");
		return "redirect:/listarClientes";
	}

	@RequestMapping("editarCliente/{id}")
	public String editarCliente(@PathVariable(value = "id") int id, Map<String, Object> model, Model Model) {

		ClienteEntity cliente = clienteDao.findById(id).orElse(null);
		model.put("cliente", cliente);
		List<BancoEntity> bancos = (List<BancoEntity>) bancoDao.findAll();
		Model.addAttribute("bancos", bancos);
		return "cliente/editarCliente";
	}

	@RequestMapping(value = "/editarClienteSave", method = RequestMethod.POST)
	public String editarClienteSave(ClienteEntity cliente, RedirectAttributes flash) {
		clienteDao.save(cliente);
		flash.addFlashAttribute("success", "Cliente " + cliente.getNombreLegal() + " creado correctamente");
		return "redirect:/listarClientes";
	}

	@RequestMapping(value = "/filtrar")
	public String filtrar(Model model, ClienteFilter filter, HttpSession session) {
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		List<ClienteEntity> clientes = clienteService.filter(filter.nombreLegal, filter.direccionLegal, filter.dinero,
				filter.email, usuario.getRol() == "BANCA" ? usuario.getBanco().toString() : filter.idBanco,
				usuario.getRol() == "BANCA" ? true : false);
		List<BancoEntity> bancos = (List<BancoEntity>) bancoDao.findAll();
		model.addAttribute("bancos", bancos);
		model.addAttribute("filter", filter);
		model.addAttribute("clientes", clientes);
		return "cliente/listaClientes";
	}

	public class ClienteFilter {

		private String nombreLegal;
		private String direccionLegal;
		private String dinero;

		private String email;
		private String idBanco;

		public String getNombreLegal() {
			return nombreLegal;
		}

		public void setNombreLegal(String nombreLegal) {
			this.nombreLegal = nombreLegal;
		}

		public String getDireccionLegal() {
			return direccionLegal;
		}

		public void setDireccionLegal(String direccionLegal) {
			this.direccionLegal = direccionLegal;
		}

		public String getDinero() {
			return dinero;
		}

		public void setDinero(String dinero) {
			this.dinero = dinero;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getIdBanco() {
			return idBanco;
		}

		public void setIdBanco(String idBanco) {
			this.idBanco = idBanco;
		}
	}
}
