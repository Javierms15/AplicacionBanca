package com.example.demo.controller;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
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
import com.example.demo.models.entity.NotificacionEntity;
import com.example.demo.models.entity.ParticipanteEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IBancoService;
import com.example.demo.models.service.IClienteService;
import com.example.demo.models.service.IDealService;
import com.example.demo.models.service.INotificacionService;
import com.example.demo.models.service.IParticipanteService;
import com.example.demo.models.service.IUsuarioService;
import com.example.demo.models.service.NotificacionServiceImpl;

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

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	INotificacionService notificacionService;

	@GetMapping({ "", "/" })
	public String ver_todos(Model model, HttpSession session) {
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (usuario.getRol().equals("ADMIN")) {
			List<DealEntity> deals = dealService.findAll();
			model.addAttribute("deals", deals);
		} else {
			List<DealEntity> deals = dealService.filter(usuario.getBanco().toString(), "", "", "", "", "", "", "", "",
					"");
			model.addAttribute("deals", deals);
		}

		model.addAttribute("usuarioId", usuario.getIdUsuario());
		DealFilter filter = new DealFilter();
		model.addAttribute("filter", filter);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		List<UsuarioEntity> usuarios = usuarioService.findAll();
		model.addAttribute("usuarios", usuarios);
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
		private String creadoPor;

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

		public String getCreadoPor() {
			return creadoPor;
		}

		public void setCreadoPor(String creadoPor) {
			this.creadoPor = creadoPor;
		}
	}

	@PostMapping({ "", "/" })
	public String ver_todos(DealFilter filter, Model model, HttpSession session) {
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		String bancoUsuario = usuario.getBanco() == null ? "" : usuario.getBanco().toString();
		List<DealEntity> deals = dealService.filter(bancoUsuario, filter.estado, filter.moneda, filter.tipo,
				filter.cliente, filter.cantidadPrestamo, filter.cantidadAbonada, filter.cantidadAPagar,
				filter.descuento, filter.creadoPor);
		model.addAttribute("filter", filter);
		model.addAttribute("deals", deals);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		List<UsuarioEntity> usuarios = usuarioService.findAll();
		model.addAttribute("usuarios", usuarios);
		return "deal/deal_all";
	}

	@GetMapping("/see/{id}")
	public String ver(@PathVariable int id, Model model, RedirectAttributes flash, HttpSession session) {
		DealEntity deal = dealService.findOne(id);
		if (deal == null) {
			flash.addFlashAttribute("error", "Acceso a un deal que no existe");
			return "redirect:/deal";
		}

		if (!puedeEditar((UsuarioEntity) session.getAttribute("usuario"), deal)) {
			flash.addFlashAttribute("error", "No tiene permiso para ver el deal");
			return "redirect:/deal";
		}

		model.addAttribute("deal", deal);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		List<ParticipanteEntity> participantes = participanteService.findAllByDeal(deal.getIdDeal());
		model.addAttribute("participantes", participantes);
		List<BancoEntity> bancos = bancoService.findAll();
		model.addAttribute("bancos", bancos);
		return "deal/deal_ver";
	}

	@GetMapping("/create")
	public String crear(Model model) {
		DealEntity deal = new DealEntity();
		deal.setEstado("PENDING");
		deal.setCantidadAbonada(0);

		model.addAttribute("deal", deal);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		return "deal/deal_form";
	}

	@PostMapping("/banks")
	public String banks(DealEntity deal, Model model) {
		model.addAttribute("deal", deal);
		List<BancoEntity> bancos = bancoService.findAll();
		model.addAttribute("bancos", bancos);

		List<Integer> bancosParticipantes = new LinkedList<>();

		DealEntity dealPrev = dealService.findOne(deal.getIdDeal());
		if (dealPrev != null) {
			List<ParticipanteEntity> participantes = participanteService.findAllByDeal(deal.getIdDeal());
			for (ParticipanteEntity participante : participantes) {
				bancosParticipantes.add(participante.getIdBanco());
			}

			for (ParticipanteEntity participante : participantes) {
				if (participante.getAgente() == 1) {
					model.addAttribute("agente", participante.getIdBanco());
					break;
				}
			}
			model.addAttribute("participantes", participantes);
			model.addAttribute("redirectUrl", "save_edit");
			model.addAttribute("buttonText", "Actualizar Deal");
		} else {
			model.addAttribute("redirectUrl", "save");
			model.addAttribute("buttonText", "Crear Deal");
		}

		model.addAttribute("bancosParticipantesId", bancosParticipantes);
		return "deal/deal_add_banks";
	}

	@PostMapping("/save")
	public String save(DealEntity deal, @RequestParam(name = "banco") int id,
			@RequestParam(name = "agente", required = false) Integer agenteId, HttpServletRequest req,
			HttpSession session, RedirectAttributes flash) {
		ParticipanteEntity participante;
		deal.setCantidadAPagar(deal.getCantidadPrestamo());
		deal.setCreadoPor(((UsuarioEntity) session.getAttribute("usuario")).getIdUsuario());
		dealService.save(deal);
		if (((String) deal.getTipo()).equals("SOLE_LENDER")) {
			participante = new ParticipanteEntity();
			participante.setIdBanco(id);
			participante.setIdDeal(deal.getIdDeal());
			participante.setAgente((byte) 1);
			participante.setPorcentajeParticipacion(1);
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
				String[] porcentajeParticipacionStr = req.getParameterValues("bancoPorcentaje" + bancoIdStr);
				if (porcentajeParticipacionStr == null) {
					// Esto no deberia ejecutarse nunca. Si se ejecuta es que hay un error en el
					// atributo nombre de los inputs del html
					throw new RuntimeException("Porcentaje de Participacion nulo");
				} else {
					try {
						Double porcentajeParticipacion = Double.parseDouble(porcentajeParticipacionStr[0]);
						participante.setPorcentajeParticipacion(porcentajeParticipacion);
					} catch (NumberFormatException e) {
						// TODO: Error
						throw new RuntimeException("Porcentaje de Participacion con formato no válido");
					}
				}

				participanteService.save(participante);
			}
		}
		flash.addFlashAttribute("success", "Deal guardado correctamente");
		return "redirect:/deal";
	}

	@PostMapping("/save_edit")
	public String save_edit(DealEntity deal, @RequestParam(name = "banco") int id,
			@RequestParam(name = "agente", required = false) Integer agenteId, HttpServletRequest req,
			RedirectAttributes flash) {

		// Es feisimo pero funciona

		List<ParticipanteEntity> participantes = participanteService.findAllByDeal(deal.getIdDeal());
		dealService.save(deal);
		ParticipanteEntity participante;
		if (((String) deal.getTipo()).equals("SOLE_LENDER")) {
			participante = new ParticipanteEntity();
			participante.setIdBanco(id);
			participante.setIdDeal(deal.getIdDeal());
			participante.setAgente((byte) 1);
			participante.setPorcentajeParticipacion(1);

			boolean exist = false;
			for (ParticipanteEntity prev : participantes) {
				if (prev.getIdDeal() == participante.getIdDeal() && prev.getIdBanco() == participante.getIdBanco()) {
					prev.setAgente(participante.getAgente());
					prev.setPorcentajeParticipacion(participante.getPorcentajeParticipacion());
					participanteService.save(prev);
					exist = true;
				} else {
					participanteService.delete(prev.getIdParticipante());
				}
			}

			if (!exist) {
				participanteService.save(participante);
			}

		} else {
			String[] bancoReq = req.getParameterValues("banco");
			List<ParticipanteEntity> actual = new LinkedList<>();
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
				String[] porcentajeParticipacionStr = req.getParameterValues("bancoPorcentaje" + bancoIdStr);
				if (porcentajeParticipacionStr == null) {
					// Esto no deberia ejecutarse nunca. Si se ejecuta es que hay un error en el
					// atributo nombre de los inputs del html
					throw new RuntimeException("Porcentaje de Participacion nulo");
				} else {
					try {
						Double porcentajeParticipacion = Double.parseDouble(porcentajeParticipacionStr[0]);
						participante.setPorcentajeParticipacion(porcentajeParticipacion);
					} catch (NumberFormatException e) {
						// TODO: Error
						throw new RuntimeException("Porcentaje de Participacion con formato no válido");
					}
				}
				actual.add(participante);
			}

			for (ParticipanteEntity prev : participantes) {
				boolean exist = false;
				for (ParticipanteEntity p : actual) {
					if (prev.getIdDeal() == p.getIdDeal() && prev.getIdBanco() == p.getIdBanco()) {
						prev.setAgente(p.getAgente());
						prev.setPorcentajeParticipacion(p.getPorcentajeParticipacion());
						participanteService.save(prev);
						exist = true;
						break;
					}
				}

				if (!exist) {
					participanteService.delete(prev.getIdParticipante());
				} else {
					exist = false;
				}
			}

			for (ParticipanteEntity p : actual) {
				boolean exist = false;
				for (ParticipanteEntity prev : participantes) {
					if (prev.getIdDeal() == p.getIdDeal() && prev.getIdBanco() == p.getIdBanco()) {
						exist = true;
						break;
					}
				}

				if (!exist) {
					participanteService.save(p);
				}
			}

		}
		flash.addFlashAttribute("success", "Deal guardado correctamente");
		return "redirect:/deal";
	}

	boolean puedeEditar(UsuarioEntity usuario, DealEntity deal) {
		// Si es admin siempre puede editar
		if (((String) usuario.getRol()).equals("ADMIN")) {
			return true;
		}

		UsuarioEntity creadoPor = usuarioService.findOne(deal.getCreadoPor());
		if (creadoPor == null) {
			// TODO: Esto no deberia ocurrir
			return false;
		}

		// Si es admin cualquiera puede editarlo
		if (((String) creadoPor.getRol()).equals("ADMIN")) {
			return true;
		}

		// Si no, sólo puede si es del mismo banco
		return creadoPor.getBanco() == usuario.getBanco();
	}

	boolean puedeCerrar(UsuarioEntity usuario, DealEntity deal) {
		if (!puedeEditar(usuario, deal)) {
			return false;
		}

		if (!((String) deal.getEstado()).equals("APPROVED")) {
			return false;
		}

		return deal.getAprobadoPor() != usuario.getIdUsuario();
	}

	@GetMapping("/approve/{id}")
	public String aprobar(@PathVariable int id, RedirectAttributes flash, HttpSession session) {
		DealEntity deal = dealService.findOne(id);
		if (deal == null) {
			flash.addFlashAttribute("error", "Acceso a un deal que no existe");
			return "redirect:/deal";
		}

		if (puedeEditar((UsuarioEntity) session.getAttribute("usuario"), deal)) {
			if (((String) deal.getEstado()).equals("PENDING")) {
				deal.setEstado("APPROVED");
				deal.setAprobadoPor(((UsuarioEntity) session.getAttribute("usuario")).getIdUsuario());
				dealService.save(deal);
				flash.addFlashAttribute("success", "Deal aprobado correctamente");
			} else {
				flash.addFlashAttribute("error", "No se puede aprobar el deal");
			}
		} else {
			flash.addFlashAttribute("error", "No tiene permiso para aprobar el deal");
		}

		return "redirect:/deal";
	}

	@GetMapping("/close/{id}")
	public String cerrar(@PathVariable int id, RedirectAttributes flash, HttpSession session) {
		DealEntity deal = dealService.findOne(id);
		if (deal == null) {
			flash.addFlashAttribute("error", "Acceso a un deal que no existe");
			return "redirect:/deal";
		}

		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		if (puedeCerrar(usuario, deal)) {
			deal.setEstado("CLOSED");
			deal.setCerradoPor(((UsuarioEntity) session.getAttribute("usuario")).getIdUsuario());
			dealService.save(deal);
			flash.addFlashAttribute("success", "Deal cerrado correctamente");
			NotificacionEntity notificacion = new NotificacionEntity();
			notificacion.setEnviadoA(deal.getAprobadoPor());
			notificacion.setEnviadoPor(usuario.getIdUsuario());
			notificacion.setEnlace("/deal/ver/" + deal.getIdDeal());
			notificacion.setFechaNotificacion(new Date(System.currentTimeMillis()));
			notificacion.setTitulo("Deal cerrado");
			notificacion.setMensaje("El usuario '" + usuario.getNombre() + "' ha cerrado el deal que habías aprobado.");
			notificacionService.save(notificacion);
		} else {
			flash.addFlashAttribute("error", "No tiene permiso para cerrar el deal");
		}

		return "redirect:/deal";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes flash, HttpSession session) {
		DealEntity deal = dealService.findOne(id);
		if (deal == null) {
			flash.addFlashAttribute("error", "Acceso a un deal que no existe");
			return "redirect:/deal";
		}

		if (puedeEditar((UsuarioEntity) session.getAttribute("usuario"), deal)) {
			dealService.delete(id);
			flash.addFlashAttribute("success", "Deal eliminado correctamente");
		} else {
			flash.addFlashAttribute("error", "No tiene permiso para eliminar el deal");

		}
		return "redirect:/deal";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model, RedirectAttributes flash, HttpSession session) {
		DealEntity deal = dealService.findOne(id);
		if (deal == null) {
			flash.addFlashAttribute("error", "Acceso a un deal que no existe");
			return "redirect:/deal";
		}

		if (!puedeEditar((UsuarioEntity) session.getAttribute("usuario"), deal)) {
			flash.addFlashAttribute("error", "No tiene permiso para editar el deal");
			return "redirect:/deal";
		}

		if (!((String) deal.getEstado()).equals("PENDING")) {
			flash.addFlashAttribute("error", "No se puede editar el deal");
			return "redirect:/deal";
		}

		model.addAttribute("deal", deal);
		List<ClienteEntity> clientes = clienteService.findAll();
		model.addAttribute("clientes", clientes);
		model.addAttribute("buttonText", "Actualizar Deal");
		return "deal/deal_form";
	}
}
