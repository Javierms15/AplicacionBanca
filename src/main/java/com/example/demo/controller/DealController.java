package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.service.DealServiceImpl;

@Controller
@RequestMapping("/deal")
public class DealController {

	@Autowired
	DealServiceImpl dealService;

	@GetMapping({ "", "/" })
	public String ver(Model model) {
		List<DealEntity> deals = dealService.findAll();
		model.addAttribute("deals", deals);
		DealFilter filter = new DealFilter();
		model.addAttribute("filter", filter);
		return "deal_all";
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
		model.addAttribute("filter", filter);
		return "deal_all";
	}

	@GetMapping("/create")
	public String crear(Model model) {
		DealEntity deal = new DealEntity();
		model.addAttribute("deal", deal);
		model.addAttribute("buttonText", "Crear Deal");
		return "deal_form";
	}

	@PostMapping("/save")
	public String save(DealEntity deal) {
		dealService.save(deal);
		return "redirect:/deal";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		dealService.delete(id);
		return "redirect:/deal";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		DealEntity deal = dealService.findOne(id);
		model.addAttribute("deal", deal);
		model.addAttribute("buttonText", "Actualizar Deal");
		return "deal_form";
	}
}
