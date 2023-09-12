package com.example.demo;

import com.example.demo.controller.FacilityController;
import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.*;
import com.example.demo.models.service.DealServiceImpl;
import com.example.demo.models.service.FacilityServiceImpl;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

@ExtendWith(MockitoExtension.class)
public class FacilityControllerTest {

	private UsuarioEntity usuarioAdmin;
	private UsuarioEntity usuarioBancoSantander;
	private UsuarioEntity usuarioBancoCaixa;
	private List<BancoEntity> allBancos;

	private List<FacilityEntity> allFacilities;

	@Mock
	private IFacilityDao facilityDao;

	@InjectMocks
	private FacilityServiceImpl facilityService;

	@Mock
	private FacilityServiceImpl iFacilityService;

	@Mock
	private IDealDao dealDao;

	@Mock
	private DealServiceImpl dealService;

	private MockMvc mvc;

	@InjectMocks
	private FacilityController facilityController;

	private List<FacilityEntity> facilitiesAll() {
		List<FacilityEntity> lista = new ArrayList<>();
		lista.add(crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2));
		return lista;
	}

	private List<BancoEntity> bancosAll() {
		String[] nombres = { "Santander", "BBVA", "La Caixa", "ING" };
		List<BancoEntity> bancos = new ArrayList<>();

		for (int i = 0; i < nombres.length; i++) {
			BancoEntity banco = new BancoEntity();
			banco.setIdBanco(i + 1);
			banco.setNombre(nombres[i]);
			bancos.add(banco);
		}

		return bancos;
	}

	public FacilityEntity crearFacility(int idFacility, String tipo, String estado, double cantidad, Date fechaCreacion,
			Date fechaEfectiva, Date fechaFinalizacion, int deal) {
		FacilityEntity facility = new FacilityEntity();

		facility.setIdFacility(idFacility);
		facility.setTipo(tipo);
		facility.setEstado(estado);
		facility.setCantidad(cantidad);
		facility.setFechaCreacion(fechaCreacion);
		facility.setFechaEfectiva(fechaEfectiva);
		facility.setFechaFinalizacion(fechaFinalizacion);
		facility.setDeal(deal);

		return facility;
	}

	@BeforeEach
	public void setup() {
		allBancos = bancosAll();
		allFacilities = facilitiesAll();
		usuarioAdmin = new UsuarioEntity();
		usuarioAdmin.setRol("ADMIN");
		usuarioBancoSantander = new UsuarioEntity();
		usuarioBancoSantander.setRol("BANCA");
		usuarioBancoSantander.setBanco(allBancos.get(0).getIdBanco());
		usuarioBancoCaixa = new UsuarioEntity();
		usuarioBancoCaixa.setRol("BANCA");
		usuarioBancoCaixa.setBanco(allBancos.get(2).getIdBanco());
		mvc = MockMvcBuilders.standaloneSetup(facilityController).build();
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
	public void creaFacilitySinPermiso() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(dealService.puedeEditar(usuarioBancoSantander, deal)).thenReturn(false);

		MockHttpServletResponse response = mvc
				.perform(post("/crearFacility").sessionAttr("usuario", usuarioBancoSantander)
						.content(facility.toString()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(flash().attribute("error", "No tiene permiso para crear esa facility")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void creaFacilityMalFechaCreacion() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(post("/crearFacility").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La fecha de creación no es válida")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void creaFacilityMalFechaFinalizacion() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 22),
				new Date(2023, 9, 20), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(post("/crearFacility").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La fecha efectiva no es válida")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void creaFacilityMalFechaEfectiva() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(post("/crearFacility").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La fecha de creación no es válida")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void creaFacilitySuperiorDineroTotalDeal() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 20), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(facilityDao.obtenerSumaFacilityDeal(facility.getDeal())).thenReturn(2000.0);

		Double sumaTotalAcumulada = facilityDao.obtenerSumaFacilityDeal(facility.getDeal()) == null ? 0
				: facilityDao.obtenerSumaFacilityDeal(facility.getDeal());
		Double totalPrestamoDeal = dealService.findOne(facility.getDeal()).getCantidadPrestamo();

		MockHttpServletResponse response = mvc
				.perform(post("/crearFacility").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La cantidad total de la facility no puede superar los "
						+ (totalPrestamoDeal - sumaTotalAcumulada)))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void creaFacilityCorrectamente() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 20), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(facilityDao.obtenerSumaFacilityDeal(facility.getDeal())).thenReturn(80.0);

		MockHttpServletResponse response = mvc
				.perform(post("/crearFacility").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(flash().attribute("success", "Facility creada correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void editarFacilitySinPermiso() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(dealService.puedeEditar(usuarioBancoSantander, deal)).thenReturn(false);

		MockHttpServletResponse response = mvc
				.perform(post("/editarFacilitySave").sessionAttr("usuario", usuarioBancoSantander)
						.content(facility.toString()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(flash().attribute("error", "No tiene permiso para editar esa facility")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void editarFacilityMalFechaCreacion() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(post("/editarFacilitySave").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La fecha de creación no es válida")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void editarFacilityMalFechaEfectiva() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(post("/editarFacilitySave").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La fecha de creación no es válida")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void editarFacilityMalFechaFinalizacion() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 21), new Date(2023, 9, 21),
				new Date(2023, 9, 20), 2);
		DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);

		MockHttpServletResponse response = mvc
				.perform(post("/editarFacilitySave").sessionAttr("usuario", usuarioAdmin).content(facility.toString())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La fecha efectiva no es válida")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void editarFacilitySuperiorDineroTotalDeal() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 5, new Date(2023, 9, 20), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		FacilityEntity facilityNew = crearFacility(1, "TERM", "PENDING", 20, new Date(2023, 9, 20),
				new Date(2023, 9, 21), new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(facilityDao.obtenerSumaFacilityDeal(facility.getDeal())).thenReturn(95.0);

		Double sumaTotalAcumulada = facilityDao.obtenerSumaFacilityDeal(facility.getDeal()) == null ? 0
				: facilityDao.obtenerSumaFacilityDeal(facility.getDeal());
		sumaTotalAcumulada -= facility.getCantidad();
		Double totalPrestamoDeal = dealService.findOne(facility.getDeal()).getCantidadPrestamo();

		MockHttpServletResponse response = mvc
				.perform(post("/editarFacilitySave").sessionAttr("usuario", usuarioAdmin)
						.content(facilityNew.toString()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(model().attribute("error", "La cantidad total de la facility no puede superar los "
						+ (totalPrestamoDeal - sumaTotalAcumulada)))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_edit");

	}

	@Test
	public void editarFacilityCorrectamente() throws Exception {
		FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 5, new Date(2023, 9, 20), new Date(2023, 9, 21),
				new Date(2023, 9, 22), 2);
		FacilityEntity facilityNew = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 20),
				new Date(2023, 9, 21), new Date(2023, 9, 22), 2);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);
		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(facilityDao.obtenerSumaFacilityDeal(facility.getDeal())).thenReturn(95.0);

		Double sumaTotalAcumulada = facilityDao.obtenerSumaFacilityDeal(facility.getDeal()) == null ? 0
				: facilityDao.obtenerSumaFacilityDeal(facility.getDeal());
		sumaTotalAcumulada -= facility.getCantidad();
		Double totalPrestamoDeal = dealService.findOne(facility.getDeal()).getCantidadPrestamo();

		MockHttpServletResponse response = mvc
				.perform(post("/editarFacilitySave").sessionAttr("usuario", usuarioAdmin)
						.content(facilityNew.toString()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(flash().attribute("success", "Facility editada correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void mostrarPantalallaNuevaFacility() throws Exception {

		MockHttpServletResponse response = mvc.perform(get("/nuevaFacility")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");

	}

	@Test
	public void mostrarPantalallaListarFacilitiesAdmin() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/listarFacility").sessionAttr("usuario", usuarioAdmin))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_all");

	}

	@Test
	public void mostrarPantalallaListarFacilitiesNOAdmin() throws Exception {

		MockHttpServletResponse response = mvc
				.perform(get("/listarFacility").sessionAttr("usuario", usuarioBancoSantander)).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_all");

	}

	@Test
	public void eliminarFacilityIdIncorrecto() throws Exception {
		int id = 20;
		MockHttpServletResponse response = mvc
				.perform(get("/eliminarFacility/" + id).sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "Ha intentado eliminar una facility que no existe")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void eliminarFacilitySinPermiso() throws Exception {
		FacilityEntity facility = allFacilities.get(0);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);

		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(dealService.puedeEditar(usuarioBancoSantander, deal)).thenReturn(false);

		MockHttpServletResponse response = mvc
				.perform(get("/eliminarFacility/" + facility.getIdFacility()).sessionAttr("usuario",
						usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para eliminar esa facility")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void eliminarFacilityCorrectamente() throws Exception {
		FacilityEntity facility = allFacilities.get(0);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);

		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);

		MockHttpServletResponse response = mvc
				.perform(get("/eliminarFacility/" + facility.getIdFacility()).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("success", "Facility eliminada correctamente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void redireccionEditarFacilityIdIncorrecto() throws Exception {
		int id = 20;
		MockHttpServletResponse response = mvc
				.perform(get("/editarFacility/" + id).sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "Ha intentado editar una facility que no existe")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void redireccionEditarFacilitySinPermiso() throws Exception {
		FacilityEntity facility = allFacilities.get(0);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);

		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(dealService.puedeEditar(usuarioBancoSantander, deal)).thenReturn(false);

		MockHttpServletResponse response = mvc
				.perform(get("/editarFacility/" + facility.getIdFacility()).sessionAttr("usuario",
						usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para editar esa facility")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarFacility");

	}

	@Test
	public void redireccionEditarFacilityCorrectamente() throws Exception {
		FacilityEntity facility = allFacilities.get(0);
		DealEntity deal = crearDeal(facility.getDeal(), "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3,
				null, null);

		when(iFacilityService.findOne(facility.getIdFacility())).thenReturn(facility);
		when(dealService.findOne(facility.getDeal())).thenReturn(deal);
		when(dealService.puedeEditar(usuarioAdmin, deal)).thenReturn(true);

		MockHttpServletResponse response = mvc
				.perform(get("/editarFacility/" + facility.getIdFacility()).sessionAttr("usuario", usuarioAdmin))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_edit");

	}

	@Test
	public void filtrarFacilitiesSiendoAdmin() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/filtrarFacility").sessionAttr("usuario", usuarioAdmin))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_all");
	}

	@Test
	public void filtrarFacilitiesSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc
				.perform(get("/filtrarFacility").sessionAttr("usuario", usuarioBancoCaixa)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_all");
	}
}
