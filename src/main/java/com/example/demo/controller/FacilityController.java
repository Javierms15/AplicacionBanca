package com.example.demo.controller;

import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IClienteService;
import com.example.demo.models.service.IDealService;
import com.example.demo.models.service.IFacilityService;
import com.example.demo.models.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class FacilityController {

	@Autowired
	private IFacilityDao facilityDao;

	@Autowired
	private IFacilityService facilityService;

	@Autowired
	private IDealService dealService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUsuarioService usuarioService;

	@RequestMapping("/nuevaFacility")
	public String mostrarPantallaNuevaFacility(Model model) {
		FacilityEntity facility = new FacilityEntity();

		List<DealEntity> deals = new ArrayList<>();
		List<DealEntity> dealsAll = dealService.findAll();
		for (DealEntity d : dealsAll) {
			Double sumaTotalFacilities = facilityDao.obtenerSumaFacilityDeal(d.getIdDeal()) == null ? 0
					: facilityDao.obtenerSumaFacilityDeal(d.getIdDeal());
			String estado = (String) d.getEstado();
			if (sumaTotalFacilities < d.getCantidadPrestamo() && !Objects.equals(estado, "CLOSED")) {
				deals.add(d);
			}
		}

		model.addAttribute("deals", deals);
		model.addAttribute("facility", facility);
		return "facility/facility";
	}

	@PostMapping("/crearFacility")
	public String crearFacility(Model model, FacilityEntity facility, RedirectAttributes flash, HttpSession session) {

		if (facility == null) {
			flash.addFlashAttribute("error", "Ha intentado crear una facility nula");
			return "redirect:/listarFacility";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (!puedeEditar(usuario, facility)) {
			flash.addFlashAttribute("error", "No tiene permiso para crear esa facility");
			return "redirect:/listarFacility";
		}

		Double sumaTotalAcumulada = facilityDao.obtenerSumaFacilityDeal(facility.getDeal()) == null ? 0
				: facilityDao.obtenerSumaFacilityDeal(facility.getDeal());
		Double sumaTotalTeorica = sumaTotalAcumulada + facility.getCantidad();
		Double totalPrestamoDeal = dealService.findOne(facility.getDeal()).getCantidadPrestamo();

		Date fechaCreacion = facility.getFechaCreacion();
		Date fechaEfectiva = facility.getFechaEfectiva();
		Date fechaFinalizacion = facility.getFechaFinalizacion();

		if (fechaCreacion.compareTo(fechaEfectiva) > 0) {
			model.addAttribute("error", "La fecha de creación no es válida");
			facility.setCantidad(0);
			model.addAttribute("facility", facility);
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
			return "facility/facility";
		} else if (fechaEfectiva.compareTo(fechaFinalizacion) > 0) {
			model.addAttribute("error", "La fecha efectiva no es válida");
			facility.setCantidad(0);
			model.addAttribute("facility", facility);
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
			return "facility/facility";

		}

		if (sumaTotalTeorica <= totalPrestamoDeal) {
			facilityDao.save(facility);
			flash.addFlashAttribute("success", "Facility creada correctamente");
			return "redirect:/listarFacility";
		} else {
			model.addAttribute("error", "La cantidad total de la facility no puede superar los "
					+ (totalPrestamoDeal - sumaTotalAcumulada));
			facility.setCantidad(0);
			model.addAttribute("facility", facility);
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
			return "facility/facility";
		}
	}

	@RequestMapping("/listarFacility")
	public String mostrarFacility(Model model, HttpSession session) {

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (usuario.getRol().equals("ADMIN")) {
			List<FacilityEntity> facilitys = facilityService.findAll();
			model.addAttribute("facilitys", facilitys);
		} else {
			List<FacilityEntity> facilitys = facilityService.filter("", "", "", "", "", "", "",
					usuario.getBanco().toString());
			model.addAttribute("facilitys", facilitys);
		}

		FacilityFilter filter = new FacilityFilter();
		model.addAttribute("filter", filter);
		List<DealEntity> deals = dealService.findAll();
		model.addAttribute("deals", deals);
		return "facility/facility_all";
	}

	private boolean puedeEditar(UsuarioEntity usuario, FacilityEntity facility) {
		DealEntity deal = dealService.findOne(facility.getDeal());

		if (deal == null) {
			return false;
		}

		return dealService.puedeEditar(usuario, deal);
	}

	@RequestMapping("eliminarFacility/{id}")
	public String eliminar(@PathVariable(value = "id") int id, RedirectAttributes flash, HttpSession session) {
		FacilityEntity facility = facilityService.findOne(id);

		if (facility == null) {
			flash.addFlashAttribute("error", "Ha intentado eliminar una facility que no existe");
			return "redirect:/listarFacility";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (!puedeEditar(usuario, facility)) {
			flash.addFlashAttribute("error", "No tiene permiso para eliminar esa facility");
			return "redirect:/listarFacility";
		}

		facilityService.delete(id);
		flash.addFlashAttribute("success", "Facility eliminada correctamente");
		return "redirect:/listarFacility";
	}

	@RequestMapping("editarFacility/{id}")
	public String editarFacility(@PathVariable(value = "id") int id, RedirectAttributes flash, HttpSession session,
			Map<String, Object> model, Model Model) {
		FacilityEntity facility = facilityService.findOne(id);

		if (facility == null) {
			flash.addFlashAttribute("error", "Ha intentado editar una facility que no existe");
			return "redirect:/listarFacility";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (!puedeEditar(usuario, facility)) {
			flash.addFlashAttribute("error", "No tiene permiso para editar esa facility");
			return "redirect:/listarFacility";
		}

		model.put("facility", facility);

		List<DealEntity> deals = new ArrayList<>();
		List<DealEntity> dealsAll = dealService.findAll();
		for (DealEntity d : dealsAll) {
			Double sumaTotalFacilities = facilityDao.obtenerSumaFacilityDeal(d.getIdDeal()) == null ? 0
					: facilityDao.obtenerSumaFacilityDeal(d.getIdDeal());
			String estado = (String) d.getEstado();
			if (sumaTotalFacilities < d.getCantidadPrestamo() && !Objects.equals(estado, "CLOSED")) {
				deals.add(d);
			}
		}
		Model.addAttribute("deals", deals);

		return "facility/facility_edit";
	}

	@RequestMapping(value = "/editarFacilitySave", method = RequestMethod.POST)
	public String editarFacilitySave(FacilityEntity facility, RedirectAttributes flash, HttpSession session,
			Model model) {

		if (facility == null) {
			flash.addFlashAttribute("error", "Ha intentado editar una facility nula");
			return "redirect:/listarFacility";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (!puedeEditar(usuario, facility)) {
			flash.addFlashAttribute("error", "No tiene permiso para editar esa facility");
			return "redirect:/listarFacility";
		}

		Double sumaTotalAcumulada = facilityDao.obtenerSumaFacilityDeal(facility.getDeal()) == null ? 0
				: facilityDao.obtenerSumaFacilityDeal(facility.getDeal());
		sumaTotalAcumulada -= facilityService.findOne(facility.getIdFacility()).getCantidad();
		Double sumaTotalTeorica = sumaTotalAcumulada + facility.getCantidad();
		Double totalPrestamoDeal = dealService.findOne(facility.getDeal()).getCantidadPrestamo();

		Date fechaCreacion = facility.getFechaCreacion();
		Date fechaEfectiva = facility.getFechaEfectiva();
		Date fechaFinalizacion = facility.getFechaFinalizacion();

		if (fechaCreacion.compareTo(fechaEfectiva) > 0) {
			model.addAttribute("error", "La fecha de creación no es válida");
			facility.setCantidad(0);
			model.addAttribute("facility", facility);
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
			return "facility/facility";
		} else if (fechaEfectiva.compareTo(fechaFinalizacion) > 0) {
			model.addAttribute("error", "La fecha efectiva no es válida");
			facility.setCantidad(0);
			model.addAttribute("facility", facility);
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
			return "facility/facility";
		}

		if (sumaTotalTeorica <= totalPrestamoDeal) {
			facilityDao.save(facility);
			flash.addFlashAttribute("success", "Facility editada correctamente");
			return "redirect:/listarFacility";
		} else {
			model.addAttribute("error", "La cantidad total de la facility no puede superar los "
					+ (totalPrestamoDeal - sumaTotalAcumulada));
			facility.setCantidad(0);
			model.addAttribute("facility", facility);
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
			return "facility/facility_edit";
		}
	}

	@RequestMapping(value = "/filtrarFacility")
	public String filtrarFacility(FacilityFilter filter, Model model, HttpSession session) {

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (usuario.getRol().equals("ADMIN")) {
			List<FacilityEntity> facilitys = facilityService.findAll();
			model.addAttribute("facilitys", facilitys);
		} else {
			List<FacilityEntity> facilitys = facilityService.filter(filter.tipo, filter.estado, filter.cantidad,
					filter.fechaCreacion, filter.fechaEfectiva, filter.fechaFinalizacion, filter.deal,
					usuario.getBanco().toString());
			model.addAttribute("facilitys", facilitys);
		}

		model.addAttribute("filter", filter);
		List<DealEntity> deals = dealService.findAll();
		model.addAttribute("deals", deals);

		return "facility/facility_all";
	}

	class FacilityFilter {

		private String tipo;
		private String estado;
		private String cantidad;

		private String fechaCreacion;
		private String fechaEfectiva;
		private String fechaFinalizacion;
		private String deal;

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getCantidad() {
			return cantidad;
		}

		public void setCantidad(String cantidad) {
			this.cantidad = cantidad;
		}

		public String getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(String fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}

		public String getFechaEfectiva() {
			return fechaEfectiva;
		}

		public void setFechaEfectiva(String fechaEfectiva) {
			this.fechaEfectiva = fechaEfectiva;
		}

		public String getFechaFinalizacion() {
			return fechaFinalizacion;
		}

		public void setFechaFinalizacion(String fechaFinalizacion) {
			this.fechaFinalizacion = fechaFinalizacion;
		}

		public String getDeal() {
			return deal;
		}

		public void setDeal(String deal) {
			this.deal = deal;
		}
	}

}
