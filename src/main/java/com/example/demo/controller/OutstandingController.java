package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.models.entity.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import com.example.demo.models.entity.DealEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.entity.OutstandingEntity;
import com.example.demo.models.entity.TipoInteresEntity;
import com.example.demo.models.service.IDealService;
import com.example.demo.models.service.IFacilityService;
import com.example.demo.models.service.IOutstandingService;
import com.example.demo.models.service.ITipoInteresService;
import com.example.demo.models.service.IUsuarioService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/outstanding")
public class OutstandingController {

	@Autowired
	private IOutstandingService outstandingService;

	@Autowired
	private IDealService dealService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFacilityService facilityService;

	@Autowired
	private ITipoInteresService tipoInteresService;

	@GetMapping({ "", "/" })
	public String ver(Model model, HttpSession session) {

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (usuario.getRol().equals("ADMIN")) {
			List<OutstandingEntity> outs = outstandingService.findAll();
			model.addAttribute("outs", outs);
		} else {
			List<OutstandingEntity> outs = outstandingService.filter("", "", "", "", "", "", "", "", "", "", "",
					usuario.getBanco().toString());
			model.addAttribute("outs", outs);
		}

		OutstandingFilter filter = new OutstandingFilter();
		model.addAttribute("filter", filter);
		List<FacilityEntity> facilities = facilityService.findAll();
		model.addAttribute("facilities", facilities);
		List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
		model.addAttribute("tipoIntereses", tipoIntereses);
		return "outstanding/out_all";
	}

	class OutstandingFilter {
		private String cantidadRestante;
		private String fechaEfectiva;
		private String fechaCreacion;
		private String fechaFinalizacion;
		private String pagoPrincipal;
		private String pagoIntereses;
		private String tipoInteres;
		private String tipoCobros;
		private String periodicidad;
		private String cantidadCobroPeriodico;
		private String facility;

		public String getCantidadRestante() {
			return cantidadRestante;
		}

		public void setCantidadRestante(String cantidadRestante) {
			this.cantidadRestante = cantidadRestante;
		}

		public String getFechaEfectiva() {
			return fechaEfectiva;
		}

		public void setFechaEfectiva(String fechaEfectiva) {
			this.fechaEfectiva = fechaEfectiva;
		}

		public String getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(String fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}

		public String getFechaFinalizacion() {
			return fechaFinalizacion;
		}

		public void setFechaFinalizacion(String fechaFinalizacion) {
			this.fechaFinalizacion = fechaFinalizacion;
		}

		public String getPagoPrincipal() {
			return pagoPrincipal;
		}

		public void setPagoPrincipal(String pagoPrincipal) {
			this.pagoPrincipal = pagoPrincipal;
		}

		public String getPagoIntereses() {
			return pagoIntereses;
		}

		public void setPagoIntereses(String pagoIntereses) {
			this.pagoIntereses = pagoIntereses;
		}

		public String getTipoInteres() {
			return tipoInteres;
		}

		public void setTipoInteres(String tipoInteres) {
			this.tipoInteres = tipoInteres;
		}

		public String getTipoCobros() {
			return tipoCobros;
		}

		public void setTipoCobros(String tipoCobros) {
			this.tipoCobros = tipoCobros;
		}

		public String getPeriodicidad() {
			return periodicidad;
		}

		public void setPeriodicidad(String periodicidad) {
			this.periodicidad = periodicidad;
		}

		public String getCantidadCobroPeriodico() {
			return cantidadCobroPeriodico;
		}

		public void setCantidadCobroPeriodico(String cantidadCobroPeriodico) {
			this.cantidadCobroPeriodico = cantidadCobroPeriodico;
		}

		public String getFacility() {
			return facility;
		}

		public void setFacility(String facility) {
			this.facility = facility;
		}
	}

	@PostMapping({ "", "/" })
	public String ver(OutstandingFilter filter, Model model, HttpSession session) {
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

		if (usuario.getRol().equals("ADMIN")) {
			List<OutstandingEntity> outs = outstandingService.findAll();
			model.addAttribute("outs", outs);
		} else {
			List<OutstandingEntity> outs = outstandingService.filter(filter.cantidadRestante, filter.fechaEfectiva,
					filter.fechaCreacion, filter.fechaFinalizacion, filter.pagoPrincipal, filter.pagoIntereses,
					filter.tipoInteres, filter.tipoCobros, filter.periodicidad, filter.cantidadCobroPeriodico,
					filter.facility, usuario.getBanco().toString());
			model.addAttribute("outs", outs);
		}

		model.addAttribute("filter", filter);
		List<FacilityEntity> facilities = facilityService.findAll();
		model.addAttribute("facilities", facilities);
		List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
		model.addAttribute("tipoIntereses", tipoIntereses);
		return "outstanding/out_all";
	}

	@GetMapping("/create")
	public String crear(Model model, HttpSession session) {
		OutstandingEntity out = new OutstandingEntity();
		model.addAttribute("out", out);
		model.addAttribute("buttonText", "Crear Outstanding");
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		List<FacilityEntity> facilities = new ArrayList<>();
		if (usuario.getBanco() != null) {
			facilities = facilityService.findByBancoUsuario(usuario);
		} else {
			facilities = facilityService.findAll();
		}
		model.addAttribute("facilities", facilities);
		List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
		model.addAttribute("tipoIntereses", tipoIntereses);
		return "outstanding/out_form";
	}

	@ResponseBody
	@GetMapping("/create/dinero/{id}")
	public double obtenerDineroFacilitySelect(@PathVariable int id, HttpSession session) {
		FacilityEntity facility = facilityService.findOne(id);
		if (facility == null) {
			return -1;
		}

		DealEntity deal = dealService.findOne(facility.getDeal());
		if (deal == null) {
			return -2;
		}

		UsuarioEntity creadoPor = usuarioService.findOne(deal.getCreadoPor());
		if (creadoPor == null) {
			return -3;
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (!creadoPor.getRol().equals("ADMIN") && !usuario.getRol().equals("ADMIN")
				&& creadoPor.getBanco() != usuario.getBanco()) {
			return -4;
		}

		Double sumaTotalAcumulada = outstandingService.obtenerSumaOutsandingFacility(id) == null ? 0
				: outstandingService.obtenerSumaOutsandingFacility(id);
		Double totalPrestamoFacility = facilityService.findOne(id).getCantidad();
		double dinero = totalPrestamoFacility - sumaTotalAcumulada;

		return dinero;
	}

	@PostMapping("/save")
	public String save(OutstandingEntity out, RedirectAttributes flash, Model model, HttpSession session) {
		if (out == null) {
			flash.addFlashAttribute("error", "Ha intentado guardar un outstanding nulo");
			return "redirect:/outstanding";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (!puedeEditar(usuario, out)) {
			flash.addFlashAttribute("error", "No tiene permiso para guardar ese outstanding");
			return "redirect:/outstanding";
		}

		Date fechaCreacion = out.getFechaCreacion();
		Date fechaEfectiva = out.getFechaEfectiva();
		Date fechaFinalizacion = out.getFechaFinalizacion();

		Double sumaTotalAcumulada = outstandingService.obtenerSumaOutsandingFacility(out.getFacility()) == null ? 0
				: outstandingService.obtenerSumaOutsandingFacility(out.getFacility());
		Double sumaTotalTeorica = sumaTotalAcumulada + out.getPagoPrincipal() + out.getPagoIntereses();
		Double totalPrestamoFacility = facilityService.findOne(out.getFacility()).getCantidad();

		if (fechaCreacion.compareTo(fechaEfectiva) > 0 || fechaCreacion.compareTo(fechaFinalizacion) > 0) {
			model.addAttribute("error", "La fecha de creación no es válida");
			OutstandingEntity out2 = new OutstandingEntity();
			model.addAttribute("out", out2);
			model.addAttribute("buttonText", "Crear Outstanding");
			List<FacilityEntity> facilities = facilityService.findAll();
			model.addAttribute("facilities", facilities);
			List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
			model.addAttribute("tipoIntereses", tipoIntereses);
			return "outstanding/out_form";
		} else if (fechaEfectiva.compareTo(fechaCreacion) < 0 || fechaEfectiva.compareTo(fechaFinalizacion) > 0) {
			model.addAttribute("error", "La fecha efectiva no es válida");
			OutstandingEntity out2 = new OutstandingEntity();
			model.addAttribute("out", out2);
			model.addAttribute("buttonText", "Crear Outstanding");
			List<FacilityEntity> facilities = facilityService.findAll();
			model.addAttribute("facilities", facilities);
			List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
			model.addAttribute("tipoIntereses", tipoIntereses);
			return "outstanding/out_form";
		} else if (fechaFinalizacion.compareTo(fechaCreacion) < 0 || fechaFinalizacion.compareTo(fechaEfectiva) < 0) {
			model.addAttribute("error", "La fecha de finalización no es válida");
			OutstandingEntity out2 = new OutstandingEntity();
			model.addAttribute("out", out2);
			model.addAttribute("buttonText", "Crear Outstanding");
			List<FacilityEntity> facilities = facilityService.findAll();
			model.addAttribute("facilities", facilities);
			List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
			model.addAttribute("tipoIntereses", tipoIntereses);
			return "outstanding/out_form";
		} else {
			if (sumaTotalTeorica <= totalPrestamoFacility) {
				out.setCantidadRestante(out.getPagoPrincipal() + out.getPagoIntereses());
				outstandingService.save(out);
				flash.addFlashAttribute("success", "Outstanding guardado correctamente");
				return "redirect:/outstanding";
			} else {
				model.addAttribute("error", "La cantidad total del outsanding no puede superar los "
						+ (totalPrestamoFacility - sumaTotalAcumulada));
				OutstandingEntity out2 = new OutstandingEntity();
				model.addAttribute("out", out2);
				model.addAttribute("buttonText", "Crear Outstanding");
				List<FacilityEntity> facilities = facilityService.findAll();
				model.addAttribute("facilities", facilities);
				List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
				model.addAttribute("tipoIntereses", tipoIntereses);
				return "outstanding/out_form";
			}

		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes flash, HttpSession session) {
		OutstandingEntity out = outstandingService.findOne(id);
		if (out == null) {
			flash.addFlashAttribute("error", "Ha intentado borrar un outstanding que no existe");
			return "redirect:/outstanding";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (!puedeEditar(usuario, out)) {
			flash.addFlashAttribute("error", "No tiene permiso para borrar ese outstanding");
			return "redirect:/outstanding";
		}

		outstandingService.delete(id);
		flash.addFlashAttribute("success", "Outstanding eliminado correctamente");
		return "redirect:/outstanding";
	}

	private boolean puedeEditar(UsuarioEntity usuario, OutstandingEntity out) {
		FacilityEntity facility = facilityService.findOne(out.getFacility());
		if (facility == null) {
			return false;
		}

		DealEntity deal = dealService.findOne(facility.getDeal());
		if (deal == null) {
			return false;
		}

		UsuarioEntity creadoPor = usuarioService.findOne(deal.getCreadoPor());
		if (creadoPor == null) {
			return false;
		}

		if (usuario.getRol().equals("ADMIN")) {
			return true;
		}

		if (creadoPor.getRol().equals("ADMIN")) {
			return true;
		}

		return creadoPor.getBanco() == usuario.getBanco();
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model, RedirectAttributes flash, HttpSession session) {
		OutstandingEntity out = outstandingService.findOne(id);
		if (out == null) {
			flash.addFlashAttribute("error", "Ha intentado editar un outstanding que no existe");
			return "redirect:/outstanding";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (!puedeEditar(usuario, out)) {
			flash.addFlashAttribute("error", "No tiene permiso para editar ese outstanding");
			return "redirect:/outstanding";
		}

		model.addAttribute("out", out);
		model.addAttribute("buttonText", "Actualizar Outstanding");
		List<TipoInteresEntity> tipoIntereses = tipoInteresService.findAll();
		model.addAttribute("tipoIntereses", tipoIntereses);
		List<FacilityEntity> facilities;

		if (((String) usuario.getRol()).equals("ADMIN")) {
			facilities = facilityService.findAll();
		} else {
			facilities = facilityService.findByBancoUsuario(usuario);
		}

		model.addAttribute("facilities", facilities);
		return "outstanding/out_form";
	}

	@GetMapping("/pago/{id}")
	public String irPantallaPago(@PathVariable int id, RedirectAttributes flash, HttpSession session, Model model) {
		OutstandingEntity out = outstandingService.findOne(id);
		if (out == null) {
			flash.addFlashAttribute("error", "Ha intentado realizar el pago de un outstanding que no existe");
			return "redirect:/outstanding";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (!puedeEditar(usuario, out)) {
			flash.addFlashAttribute("error", "No tiene permiso para realizar el pago de ese outstanding");
			return "redirect:/outstanding";
		}

		model.addAttribute("cantidadRestante", out.getCantidadRestante());
		model.addAttribute("id", id);
		return "outstanding/out_pago";
	}

	@PostMapping("/realizarPago")
	public String realizarPago(@RequestParam(name = "idOut") int id,
			@RequestParam(name = "cantidadRestante") double cantidadRestante, @RequestParam(name = "pago") double pago,
			Model model, RedirectAttributes flash, HttpSession session) {
		OutstandingEntity out = outstandingService.findOne(id);
		if (out == null) {
			flash.addFlashAttribute("error", "Ha intentado realizar el pago de un outstanding que no existe");
			return "redirect:/outstanding";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (!puedeEditar(usuario, out)) {
			flash.addFlashAttribute("error", "No tiene permiso para realizar el pago de ese outstanding");
			return "redirect:/outstanding";
		}

		if (pago > cantidadRestante) {
			model.addAttribute("cantidadRestante", cantidadRestante);
			model.addAttribute("id", id);
			model.addAttribute("error", "El pago no puede superar el pago principal");
			return "outstanding/out_pago";
		}

		out.setCantidadRestante(cantidadRestante - pago);
		outstandingService.save(out);

		return "redirect:/outstanding";
	}

}
