package com.example.demo.controller;

import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PruebaController {

	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private IBancoDao bancoDao;

	@RequestMapping("/")
	public String prueba() {
		return "prueba";
	}

	@RequestMapping("/nuevoCliente")
	public String mostrarPantallaNuevoCliente(Model model){
		ClienteEntity cliente=new ClienteEntity();
		model.addAttribute("cliente",cliente);
		return"cliente";
	}

	@PostMapping("/crearCliente")
	public String crearCliente(Model model, ClienteEntity cliente){
		clienteDao.save(cliente);
		return "redirect:";
	}

	//@GetMapping("/editarCliente")
	//public String editarCliente(Model model, )

}
