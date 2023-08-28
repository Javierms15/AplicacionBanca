package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.entity.OutstandingEntity;
import com.example.demo.models.service.OutstandingServiceImpl;

@Controller
@RequestMapping("/outstanding")
public class OutstandingController {

	@Autowired
	private OutstandingServiceImpl outstandingService;

	@GetMapping({ "", "/" })
	public String ver(Model model) {
		List<OutstandingEntity> outs = outstandingService.findAll();
		model.addAttribute("outs", outs);
		OutstandingFilter filter = new OutstandingFilter();
		model.addAttribute("filter", filter);
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
	public String ver(OutstandingFilter filter, Model model) {
		List<OutstandingEntity> outs = outstandingService.filter(filter.cantidadRestante, filter.fechaEfectiva,
				filter.fechaCreacion, filter.fechaFinalizacion, filter.pagoPrincipal, filter.pagoIntereses,
				filter.tipoInteres, filter.tipoCobros, filter.periodicidad, filter.cantidadCobroPeriodico,
				filter.facility);
		model.addAttribute("outs", outs);
		model.addAttribute("filter", filter);
		return "outstanding/out_all";
	}

	@GetMapping("/create")
	public String crear(Model model) {
		OutstandingEntity out = new OutstandingEntity();
		model.addAttribute("out", out);
		model.addAttribute("buttonText", "Crear Outstanding");
		return "outstanding/out_form";
	}

	@PostMapping("/save")
	public String save(OutstandingEntity out) {
		outstandingService.save(out);
		return "redirect:/outstanding";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		outstandingService.delete(id);
		return "redirect:/outstanding";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		OutstandingEntity out = outstandingService.findOne(id);
		model.addAttribute("out", out);
		model.addAttribute("buttonText", "Actualizar Outstanding");
		return "outstanding/out_form";
	}
}
