package com.example.demo.controller;

import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		return "redirect:/listarClientes";
	}

	@RequestMapping("/listarClientes")
	public String mostrarClientes(Model model){
		List<ClienteEntity> clientes= (List<ClienteEntity>) clienteDao.findAll();
		model.addAttribute("clientes",clientes);
		return "listaClientes";
	}

	@RequestMapping("eliminarCliente/{id}")
	public String eliminar(@PathVariable(value = "id") int id) {
		ClienteEntity cliente=clienteDao.findById(id).orElse(null);
		clienteDao.delete(cliente);
		return "redirect:/listarClientes";
	}

	@RequestMapping("editarCliente/{id}")
	public String editarCliente(@PathVariable(value = "id") int id, Map<String, Object> model) {

		ClienteEntity cliente = clienteDao.findById(id).orElse(null);
		model.put("cliente", cliente);

		return "editarCliente";
	}

	@RequestMapping(value = "/editarClienteSave", method = RequestMethod.POST)
	public String editarClienteSave(ClienteEntity cliente) {
		clienteDao.save(cliente);
		return "redirect:/listarClientes";
	}

	@RequestMapping(value = "/filtrar")
	public String filtrar(Model model, @RequestParam(value = "valor") String valor, @RequestParam(value="filtro") String filtro) {
		List<ClienteEntity> clientes=new ArrayList<>();
		if(filtro.equals("nombre")) {
			clientes= (List<ClienteEntity>) clienteDao.findByNombre(valor);
		}else if(filtro.equals("direccion")){
			clientes= (List<ClienteEntity>) clienteDao.findByDireccion(valor);
		}else if(filtro.equals("dinero")){
			clientes= (List<ClienteEntity>) clienteDao.findByCapital(Double.parseDouble(valor));
		}else if(filtro.equals("email")){
			clientes= (List<ClienteEntity>) clienteDao.findByEmail(valor);
		}else if(filtro.equals("banco")){
			BancoEntity banco=bancoDao.findByNombre(valor);
			clientes= (List<ClienteEntity>) clienteDao.findByBanco(banco.getIdBanco());
		}else{
			clientes= (List<ClienteEntity>) clienteDao.findAll();
		}
		model.addAttribute("clientes",clientes);
		return "listaClientes";
	}

}
