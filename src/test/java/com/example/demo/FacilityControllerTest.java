package com.example.demo;

import com.example.demo.controller.ClienteController;
import com.example.demo.controller.FacilityController;
import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.*;
import com.example.demo.models.service.ClienteServiceImpl;
import com.example.demo.models.service.DealServiceImpl;
import com.example.demo.models.service.FacilityServiceImpl;
import com.example.demo.models.service.IFacilityService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

@ExtendWith(MockitoExtension.class)
public class FacilityControllerTest {

    private UsuarioEntity usuarioAdmin;
    private UsuarioEntity usuarioBancoSantander;
    private List<BancoEntity> allBancos;

    private List<FacilityEntity> allFacilities;
    @Mock
    private IFacilityDao facilityDao;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    @Mock
    private IFacilityService iFacilityService;

    @Mock
    private IDealDao dealDao;

    @Mock
    private DealServiceImpl dealService;
    private MockMvc mvc;
    @InjectMocks
    private FacilityController facilityController;

    private List<FacilityEntity> facilitiesAll(){
        List<FacilityEntity> lista=new ArrayList<>();
        lista.add(crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21), new Date(2023, 9, 22), 2));
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

    public FacilityEntity crearFacility(int idFacility, Object tipo, Object estado, double cantidad, Date fechaCreacion, Date fechaEfectiva, Date fechaFinalizacion, int deal) {
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
        allFacilities=facilitiesAll();
        usuarioAdmin = new UsuarioEntity();
        usuarioAdmin.setRol("ADMIN");
        usuarioBancoSantander = new UsuarioEntity();
        usuarioBancoSantander.setRol("BANCA");
        usuarioBancoSantander.setBanco(allBancos.get(0).getIdBanco());
        mvc = MockMvcBuilders.standaloneSetup(facilityController).build();
    }

    private DealEntity crearDeal(int idDeal, Object estado, double cantidadPrestamo, double cantidadAbonada,
                                 double cantidadAPagar, Object moneda, Object tipo, byte descuento, int cliente, int creadoPor,
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
    public void creaFacilityMalFechaCreacion() throws Exception {
        FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21), new Date(2023, 9, 22), 2);
        DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
        when(dealService.findOne(2)).thenReturn(deal);

        MockHttpServletResponse response = mvc
                .perform(post("/crearFacility").content(facility.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(
                        model().attribute("error", "La fecha de creación no es válida"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");


    }

    @Test
    public void creaFacilityMalFechaFinalizacion() throws Exception {
        FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 22), new Date(2023, 9, 20), 2);
        DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
        when(dealService.findOne(2)).thenReturn(deal);

        MockHttpServletResponse response = mvc
                .perform(post("/crearFacility").content(facility.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(
                        model().attribute("error", "La fecha efectiva no es válida"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");


    }

    @Test
    public void creaFacilityMalFechaEfectiva() throws Exception {
        FacilityEntity facility = crearFacility(1, "TERM", "PENDING", 10, new Date(2023, 9, 22), new Date(2023, 9, 21), new Date(2023, 9, 22), 2);
        DealEntity deal = crearDeal(1, "PENDING", 100, 50, 50, "EUR", "SYNDICATED", (byte) 0, 3, 3, null, null);
        when(dealService.findOne(2)).thenReturn(deal);

        MockHttpServletResponse response = mvc
                .perform(post("/crearFacility").content(facility.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(
                        model().attribute("error", "La fecha de creación no es válida"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");


    }


    @Test
    public void mostrarPantalallaNuevaFacility() throws Exception {


        MockHttpServletResponse response = mvc
                .perform(get("/nuevaFacility"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");


    }
/*

    @Test
    public void mostrarPantalallaListarFacilitiesAdmin() throws Exception {

        when(facilityDao.findAll()).thenReturn(allFacilities);

        MockHttpServletResponse response = mvc
                .perform(get("/listarFacility").sessionAttr("usuario", usuarioAdmin))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("facility/facility_all");


    }

    @Test
    public void mostrarPantalallaListarFacilitiesNOAdmin() throws Exception {


        MockHttpServletResponse response = mvc
                .perform(get("/nuevaFacility"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("facility/facility");


    }
*/

}
