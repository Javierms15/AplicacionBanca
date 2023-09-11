package com.example.demo;

import com.example.demo.controller.DealController;
import com.example.demo.models.entity.*;
import com.example.demo.models.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
public class DealControllerTest {

	private MockMvc mvc;

	private List<DealEntity> allDeals;
	private UsuarioEntity usuarioAdmin;
	private UsuarioEntity usuarioBancoCaixa;
	private UsuarioEntity usuarioBancoSantander;

	@Mock
	private IDealService iDealService;

	@Mock
	private IFacilityService facilityService;

	@InjectMocks
	private DealController dealController;

	@Mock
	private IClienteService clienteService;

	@Mock
	private IUsuarioService usuarioService;

	@Mock
	private IParticipanteService participanteService;

	@Mock
	private IBancoService bancoService;

	@Mock
	private INotificacionService notificacionService;

	@BeforeEach
	public void setup() {
		usuarioAdmin = new UsuarioEntity();
		usuarioAdmin.setIdUsuario(0);
		usuarioAdmin.setRol("ADMIN");
		usuarioBancoCaixa = new UsuarioEntity();
		usuarioBancoCaixa.setRol("BANCA");
		usuarioBancoCaixa.setBanco(2);
		usuarioBancoCaixa.setIdUsuario(1);
		usuarioBancoSantander = new UsuarioEntity();
		usuarioBancoSantander.setRol("BANCA");
		usuarioBancoSantander.setBanco(1);
		usuarioBancoSantander.setIdUsuario(2);
		mvc = MockMvcBuilders.standaloneSetup(dealController).build();
	}

	private DealEntity crearDeal(int idDeal, String estado, double cantidadPrestamo, double cantidadAbonada,
			double cantidadAPagar, String moneda, String tipo, byte descuento, int cliente, int creadoPor,
			Integer aprobadoPor, Integer cerradoPor) {
		DealEntity deal = new DealEntity();
		deal.setIdDeal(idDeal);
		deal.setEstado(estado);
		deal.setCantidadPrestamo(cantidadPrestamo);
		deal.setCantidadAbonada(cantidadAbonada);
		deal.setCantidadAPagar(cantidadAPagar);
		deal.setMoneda(moneda);
		deal.setTipo(tipo);
		deal.setDescuento(descuento);
		deal.setCliente(cliente);
		deal.setCreadoPor(creadoPor);
		deal.setAprobadoPor(aprobadoPor);
		deal.setCerradoPor(cerradoPor);
		return deal;
	}

	@Test
    public void redireccionCorrectaListarDealsSiendoAdmin() throws Exception {
        when(iDealService.findAll()).thenReturn(allDeals);

        MockHttpServletResponse response = mvc.perform(get("/deal").sessionAttr("usuario", usuarioAdmin)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
    }

	@Test
	public void redireccionCorrectaListarDealsSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/deal").sessionAttr("usuario", usuarioBancoCaixa))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
	}

	@Test
	public void redireccionCorrectaFiltrarSinFiltroSiendoAdmin() throws Exception {
		DealController.DealFilter filter = dealController.new DealFilter();
		filter.setCliente("");
		filter.setDescuento("");
		filter.setMoneda("");
		filter.setTipo("");
		filter.setCantidadPrestamo("");
		filter.setCreadoPor("");
		filter.setEstado("");
		filter.setCantidadAbonada("");
		filter.setCantidadAPagar("");

		MockHttpServletResponse response = mvc.perform(post("/deal").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(filter.toString()).sessionAttr("usuario", usuarioAdmin)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
	}

	@Test
	public void redireccionCorrectaFiltrarConFiltroSiendoAdmin() throws Exception {
		DealController.DealFilter filter = dealController.new DealFilter();
		filter.setCliente("");
		filter.setDescuento("");
		filter.setMoneda("");
		filter.setTipo("");
		filter.setCantidadPrestamo("");
		filter.setCreadoPor("");
		filter.setEstado("PENDING");
		filter.setCantidadAbonada("");
		filter.setCantidadAPagar("100");

		MockHttpServletResponse response = mvc.perform(post("/deal").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(filter.toString()).sessionAttr("usuario", usuarioAdmin)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
	}

	@Test
	public void redireccionCorrectaFiltrarSinFiltroSinSerAdmin() throws Exception {
		DealController.DealFilter filter = dealController.new DealFilter();
		filter.setCliente("");
		filter.setDescuento("");
		filter.setMoneda("");
		filter.setTipo("");
		filter.setCantidadPrestamo("");
		filter.setCreadoPor("");
		filter.setEstado("");
		filter.setCantidadAbonada("");
		filter.setCantidadAPagar("");

		MockHttpServletResponse response = mvc.perform(post("/deal").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(filter.toString()).sessionAttr("usuario", usuarioBancoCaixa)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
	}

	@Test
	public void redireccionCorrectaFiltrarConFiltroSinSerAdmin() throws Exception {
		DealController.DealFilter filter = dealController.new DealFilter();
		filter.setCliente("");
		filter.setDescuento("");
		filter.setMoneda("");
		filter.setTipo("");
		filter.setCantidadPrestamo("");
		filter.setCreadoPor("");
		filter.setEstado("PENDING");
		filter.setCantidadAbonada("");
		filter.setCantidadAPagar("100");

		MockHttpServletResponse response = mvc.perform(post("/deal").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(filter.toString()).sessionAttr("usuario", usuarioBancoCaixa)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
	}

	@Test
	public void verDealIdIncorrecto() throws Exception {
		int dealId = 0;

		when(iDealService.findOne(dealId)).thenReturn(null);

		MockHttpServletResponse response = mvc.perform(get("/deal/see/" + dealId))
				.andExpect(flash().attribute("error", "Acceso a un deal que no existe")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void verDealSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SOLE_LENDER", (byte) 0, 3, 3, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/see/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoCaixa))
				.andExpect(flash().attribute("error", "No tiene permiso para ver el deal")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	private String dealToString(DealEntity deal) {
		return "idDeal=" + deal.getIdDeal() + "&estado=" + deal.getEstado() + "&cantidadAbonada="
				+ deal.getCantidadAbonada() + "&cantidadAPagar=" + deal.getCantidadAPagar() + "&creadoPor="
				+ deal.getCreadoPor() + "&aprobadoPor=" + (deal.getAprobadoPor() == null ? "" : deal.getAprobadoPor())
				+ "&cerradoPor=" + (deal.getCerradoPor() == null ? "" : deal.getCerradoPor()) + "&cantidadPrestamo="
				+ deal.getCantidadPrestamo() + "&moneda=" + deal.getMoneda() + "&tipo=" + deal.getTipo() + "&descuento="
				+ deal.getDescuento() + "&cliente=" + deal.getCliente();
	}

	private String participanteToString(ParticipanteEntity participante) {
		String agente = participante.getAgente() == 1 ? "&agente=" + participante.getIdBanco() : "";
		return "banco=" + participante.getIdBanco() + "&bancoPorcentaje" + participante.getIdBanco() + "="
				+ participante.getPorcentajeParticipacion() + agente;
	}

	@Test
	public void verDealCorrectamente() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SOLE_LENDER", (byte) 0, 3, 3, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/see/" + deal.getIdDeal()).sessionAttr("usuario", usuarioAdmin)).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_ver");
	}

	@Test
	public void redireccionCorrectaPantallaCrearDeal() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/deal/create")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_form");
	}

	@Test
	public void guardarDealSumaPorcentajeIncorrecta() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		ParticipanteEntity participante1 = new ParticipanteEntity();
		participante1.setIdParticipante(1);
		participante1.setIdDeal(deal.getIdDeal());
		participante1.setIdBanco(1);
		participante1.setPorcentajeParticipacion(0.4);
		participante1.setAgente((byte) 0);
		ParticipanteEntity participante2 = new ParticipanteEntity();
		participante2.setIdParticipante(2);
		participante2.setIdDeal(deal.getIdDeal());
		participante2.setIdBanco(2);
		participante2.setPorcentajeParticipacion(0.4);
		participante2.setAgente((byte) 1);

		String content = dealToString(deal) + "&" + participanteToString(participante1) + "&"
				+ participanteToString(participante2) + "&bancoSoleLender=0";
		MockHttpServletResponse response = mvc
				.perform(post("/deal/save").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(content))
				.andExpect(model().attribute("error", "La suma del porcentaje de participación debe ser 1")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_form");
	}

	@Test
	public void guardarDealSoleLenderCorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SOLE_LENDER", (byte) 0, 3, 3, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(null);

		ParticipanteEntity participante1 = new ParticipanteEntity();
		participante1.setIdParticipante(1);
		participante1.setIdDeal(deal.getIdDeal());
		participante1.setIdBanco(1);
		participante1.setPorcentajeParticipacion(1);
		participante1.setAgente((byte) 1);

		String content = dealToString(deal) + "&bancoSoleLender=" + participante1.getIdBanco();
		MockHttpServletResponse response = mvc
				.perform(post("/deal/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.sessionAttr("usuario", usuarioAdmin).content(content))
				.andExpect(flash().attribute("success", "Deal guardado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void guardarDealSyndicatedCorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(null);

		ParticipanteEntity participante1 = new ParticipanteEntity();
		participante1.setIdParticipante(1);
		participante1.setIdDeal(deal.getIdDeal());
		participante1.setIdBanco(1);
		participante1.setPorcentajeParticipacion(0.5);
		participante1.setAgente((byte) 0);
		ParticipanteEntity participante2 = new ParticipanteEntity();
		participante2.setIdParticipante(2);
		participante2.setIdDeal(deal.getIdDeal());
		participante2.setIdBanco(2);
		participante2.setPorcentajeParticipacion(0.5);
		participante2.setAgente((byte) 1);

		String content = dealToString(deal) + "&" + participanteToString(participante1) + "&"
				+ participanteToString(participante2) + "&bancoSoleLender=0";
		MockHttpServletResponse response = mvc
				.perform(post("/deal/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.sessionAttr("usuario", usuarioAdmin).content(content))
				.andExpect(flash().attribute("success", "Deal guardado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void editarDealSoleLenderCorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SOLE_LENDER", (byte) 0, 3, 3, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);

		ParticipanteEntity participante1 = new ParticipanteEntity();
		participante1.setIdParticipante(1);
		participante1.setIdDeal(deal.getIdDeal());
		participante1.setIdBanco(1);
		participante1.setPorcentajeParticipacion(1);
		participante1.setAgente((byte) 1);

		String content = dealToString(deal) + "&bancoSoleLender=" + participante1.getIdBanco();
		MockHttpServletResponse response = mvc
				.perform(post("/deal/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.sessionAttr("usuario", usuarioAdmin).content(content))
				.andExpect(flash().attribute("success", "Deal guardado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void editarDealSyndicatedCorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);

		ParticipanteEntity participante1 = new ParticipanteEntity();
		participante1.setIdParticipante(1);
		participante1.setIdDeal(deal.getIdDeal());
		participante1.setIdBanco(1);
		participante1.setPorcentajeParticipacion(0.5);
		participante1.setAgente((byte) 0);
		ParticipanteEntity participante2 = new ParticipanteEntity();
		participante2.setIdParticipante(2);
		participante2.setIdDeal(deal.getIdDeal());
		participante2.setIdBanco(2);
		participante2.setPorcentajeParticipacion(0.5);
		participante2.setAgente((byte) 1);

		String content = dealToString(deal) + "&" + participanteToString(participante1) + "&"
				+ participanteToString(participante2) + "&bancoSoleLender=0";
		MockHttpServletResponse response = mvc
				.perform(post("/deal/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.sessionAttr("usuario", usuarioAdmin).content(content))
				.andExpect(flash().attribute("success", "Deal guardado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void aprobarDealIdIncorrecto() throws Exception {
		int dealId = 0;
		when(iDealService.findOne(dealId)).thenReturn(null);

		MockHttpServletResponse response = mvc.perform(get("/deal/approve/" + dealId))
				.andExpect(flash().attribute("error", "Acceso a un deal que no existe")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void aprobarDealSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/approve/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoCaixa))
				.andExpect(flash().attribute("error", "No tiene permiso para aprobar el deal")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void aprobarDealConPermisoEstadoIncorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "CLOSED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/approve/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No se puede aprobar el deal")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void aprobarDealConPermisoEstadoCorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/approve/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("success", "Deal aprobado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void cerrarDealIdIncorrecto() throws Exception {
		int dealId = 0;
		when(iDealService.findOne(dealId)).thenReturn(null);

		MockHttpServletResponse response = mvc.perform(get("/deal/close/" + dealId))
				.andExpect(flash().attribute("error", "Acceso a un deal que no existe")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void cerrarDealSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/close/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoCaixa))
				.andExpect(flash().attribute("error", "No tiene permiso para cerrar el deal")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void cerrarDealEstadoIncorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2,
				usuarioBancoSantander.getIdUsuario(), null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/close/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para cerrar el deal")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void cerrarDealCorrectamente() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2,
				usuarioBancoSantander.getIdUsuario(), null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		UsuarioEntity usuarioBancoSantander2 = new UsuarioEntity();
		usuarioBancoSantander2.setRol("BANCA");
		usuarioBancoSantander2.setBanco(usuarioBancoSantander.getBanco());

		MockHttpServletResponse response = mvc
				.perform(get("/deal/close/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander2))
				.andExpect(flash().attribute("success", "Deal cerrado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void eliminarDealIdIncorrecto() throws Exception {
		int dealId = 0;
		when(iDealService.findOne(dealId)).thenReturn(null);

		MockHttpServletResponse response = mvc.perform(get("/deal/delete/" + dealId))
				.andExpect(flash().attribute("error", "Acceso a un deal que no existe")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void eliminarDealSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/delete/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoCaixa))
				.andExpect(flash().attribute("error", "No tiene permiso para eliminar el deal")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void eliminarDealCorrectamente() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/delete/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("success", "Deal eliminado correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void redireccionEditarDealIdIncorrecto() throws Exception {
		int dealId = 0;
		when(iDealService.findOne(dealId)).thenReturn(null);

		MockHttpServletResponse response = mvc.perform(get("/deal/edit/" + dealId))
				.andExpect(flash().attribute("error", "Acceso a un deal que no existe")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void redireccionEditarDealSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/edit/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoCaixa))
				.andExpect(flash().attribute("error", "No tiene permiso para editar el deal")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void redireccionEditarDealEstadoIncorrecto() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/edit/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void redireccionEditarDealCorrectamente() throws Exception {
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(get("/deal/edit/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_form");
	}

	@Test
	public void notificacionCerrarDealIdIncorrecto() throws Exception {
		int dealId = 0;
		when(iDealService.findOne(dealId)).thenReturn(null);

		MockHttpServletResponse response = mvc
				.perform(post("/deal/notifyClose/" + dealId).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content("usuario=0"))
				.andExpect(flash().attribute("error", "Acceso a un deal que no existe")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void notificacionCerrarDealSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(deal.getCreadoPor())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(post("/deal/notifyClose/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoCaixa)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("usuario=0"))
				.andExpect(flash().attribute("error", "No tiene permiso para editar el deal")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void notificacionCerrarDealUsuarioNoExiste() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1, 2, null, null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(10)).thenReturn(null);

		MockHttpServletResponse response = mvc
				.perform(post("/deal/notifyClose/" + deal.getIdDeal()).sessionAttr("usuario", usuarioAdmin)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("usuario=10"))
				.andExpect(flash().attribute("error", "El usuario al que quería enviar la notificación no existe"))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void notificacionCerrarDealUsuarioSinPermiso() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1,
				usuarioBancoSantander.getIdUsuario(), usuarioBancoSantander.getIdUsuario(), null);
		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(10)).thenReturn(usuarioBancoCaixa);
		when(usuarioService.findOne(usuarioBancoSantander.getIdUsuario())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(post("/deal/notifyClose/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("usuario=10"))
				.andExpect(flash().attribute("error",
						"El usuario al que quería enviar la notificación no tiene permiso para cerrar el deal"))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}

	@Test
	public void notificacionCerrarDealCorrectamente() throws Exception {
		DealEntity deal = crearDeal(1, "APPROVED", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 1,
				usuarioBancoSantander.getIdUsuario(), usuarioBancoSantander.getIdUsuario(), null);

		UsuarioEntity usuarioBancoSantander2 = new UsuarioEntity();
		usuarioBancoSantander2.setRol("BANCA");
		usuarioBancoSantander2.setIdUsuario(100);
		usuarioBancoSantander2.setBanco(usuarioBancoSantander.getBanco());

		when(iDealService.findOne(deal.getIdDeal())).thenReturn(deal);
		when(usuarioService.findOne(10)).thenReturn(usuarioBancoSantander2);
		when(usuarioService.findOne(usuarioBancoSantander.getIdUsuario())).thenReturn(usuarioBancoSantander);

		MockHttpServletResponse response = mvc
				.perform(post("/deal/notifyClose/" + deal.getIdDeal()).sessionAttr("usuario", usuarioBancoSantander)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("usuario=10"))
				.andExpect(flash().attribute("success", "Notificación enviada correctamente")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/deal");
	}
}
