package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.ParticipanteEntity;
import com.example.demo.models.service.IBancoService;
import com.example.demo.models.service.IClienteService;
import com.example.demo.models.service.IDealService;
import com.example.demo.models.service.IParticipanteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/deal")
public class DealController {

	@Autowired
	IDealService dealService;

	@Autowired
	IParticipanteService participanteService;

	@Autowired
	IBancoService bancoService;

	@Autowired
	IClienteService clienteService;
	
	@GetMapping({ "", "/" })
	public String ver(Model model) {
		List<DealEntity> deals = dealService.findAll();
		model.addAttribute("deals", deals);
		DealFilter filter = new DealFilter();
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		model.addAttribute("filter", filter);
		return "deal/deal_all";
	}

	class DealFilter {
		private String estado;
		private String moneda;
		private String tipo;
		private String cliente;
		private String cantidadPrestamo;
		private String cantidadAbonada;
		private String cantidadAPagar;
		private String descuento;

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getMoneda() {
			return moneda;
		}

		public void setMoneda(String moneda) {
			this.moneda = moneda;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getCliente() {
			return cliente;
		}

		public void setCliente(String cliente) {
			this.cliente = cliente;
		}

		public String getCantidadPrestamo() {
			return cantidadPrestamo;
		}

		public void setCantidadPrestamo(String cantidadPrestamo) {
			this.cantidadPrestamo = cantidadPrestamo;
		}

		public String getCantidadAbonada() {
			return cantidadAbonada;
		}

		public void setCantidadAbonada(String cantidadAbonada) {
			this.cantidadAbonada = cantidadAbonada;
		}

		public String getCantidadAPagar() {
			return cantidadAPagar;
		}

		public void setCantidadAPagar(String cantidadAPagar) {
			this.cantidadAPagar = cantidadAPagar;
		}

		public String getDescuento() {
			return descuento;
		}

		public void setDescuento(String descuento) {
			this.descuento = descuento;
		}
	}

	@PostMapping({ "", "/" })
	public String ver(DealFilter filter, Model model) {
		List<DealEntity> deals = dealService.filter(filter.estado, filter.moneda, filter.tipo, filter.cliente,
				filter.cantidadPrestamo, filter.cantidadAbonada, filter.cantidadAPagar, filter.descuento);
		model.addAttribute("deals", deals);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		model.addAttribute("filter", filter);
		return "deal/deal_all";
	}

	@GetMapping("/create")
	public String crear(Model model) {
		DealEntity deal = new DealEntity();
		model.addAttribute("deal", deal);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		model.addAttribute("buttonText", "Crear Deal");
		return "deal/deal_form";
	}

	@PostMapping("/banks")
	public String banks(DealEntity deal, Model model) {
		model.addAttribute("deal", deal);
		List<BancoEntity> bancos = bancoService.findAll();
		model.addAttribute("bancos", bancos);
		return "deal/deal_add_banks";
	}

	@PostMapping("/save")
	public String save(DealEntity deal, @RequestParam(name = "banco") int id,
			@RequestParam(name = "agente", required = false) Integer agenteId, HttpServletRequest req, RedirectAttributes flash) {
		ParticipanteEntity participante;
		dealService.save(deal);
		if (((String) deal.getTipo()).equals("SOLE_LENDER")) {
			participante = new ParticipanteEntity();
			participante.setIdBanco(id);
			participante.setIdDeal(deal.getIdDeal());
			participante.setAgente((byte) 1);
			participanteService.save(participante);
		} else {
			String[] bancoReq = req.getParameterValues("banco");
			for (String bancoIdStr : bancoReq) {
				participante = new ParticipanteEntity();
				try {
					int bancoId = Integer.parseInt(bancoIdStr);
					participante.setIdBanco(bancoId);
					if (bancoId == agenteId) {
						participante.setAgente((byte) 1);
					} else {
						participante.setAgente((byte) 0);
					}
				} catch (NumberFormatException e) {
					throw new RuntimeException("BancoId no valido en DealController - save");
				}
				participante.setIdDeal(deal.getIdDeal());
				participanteService.save(participante);
			}
		}
		flash.addFlashAttribute("success", "Deal guardado correctamente");
		return "redirect:/deal";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes flash) {
		dealService.delete(id);
		flash.addFlashAttribute("success", "Deal eliminado correctamente");
		return "redirect:/deal";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		DealEntity deal = dealService.findOne(id);
		model.addAttribute("deal", deal);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		model.addAttribute("buttonText", "Actualizar Deal");
		return "deal/deal_form";
	}
}
