package com.example.demo;

import com.example.demo.controller.ClienteController;
import com.example.demo.controller.DealController;
import com.example.demo.models.entity.*;
import com.example.demo.models.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicReferenceFieldUpdater;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class DealControllerTest {


    private MockMvc mvc;

    private List<DealEntity> allDeals;
    private List<BancoEntity> allBancos;
    private List<FacilityEntity> allFacilities;

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
    private IBancoService bancoService;

    @BeforeEach
    public void setup() {
        allDeals = dealAll();
        allBancos = bancoAll() ;
        mvc = MockMvcBuilders.standaloneSetup(dealController).build();
    }

    private List<BancoEntity> bancoAll(){
        List<BancoEntity> bancos=new ArrayList<>();
        bancos.add(crearBanco(1,"Santander"));
        return bancos;
    }

    private List<DealEntity> dealAll() {
        List<DealEntity> deals = new ArrayList<>();
         deals.add(crearDeal(50, (Object) "PENDING", 2000.0, (double) 0, 100.0, (Object) "EUR", (Object) "SOLE_LENDER", (byte) 0,3,3,null,null));
        return deals;
    }

    private BancoEntity crearBanco(int idBanco,String nombre){
        BancoEntity banco=new BancoEntity();
        banco.setIdBanco(idBanco);
        banco.setNombre(nombre);

        return banco;
    }

    private DealEntity crearDeal(int idDeal, Object estado,double cantidadPrestamo, double cantidadAbonada,double cantidadAPagar,Object moneda,Object tipo,byte descuento,int cliente,int creadoPor,Integer aprobadoPor,Integer cerradoPor) {
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
    public void redireccionCorrectaNuevoDeal() throws Exception {
        when(iDealService.findAll()).thenReturn(allDeals);

        UsuarioEntity usuario=new UsuarioEntity();
        usuario.setRol("ADMIN");

        MockHttpServletResponse response = mvc.perform(get("/deal").sessionAttr("usuario", usuario)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
    }


    @Test
    public void redireccionCorrectaFiltrarSinFiltroSiendoAdmin() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRol("ADMIN");

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



        MockHttpServletResponse response = mvc
                .perform(post("/deal").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(filter.toString()).sessionAttr("usuario", usuario))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
    }

    @Test
    public void redireccionCorrectaFiltrarConFiltroSiendoAdmin() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRol("ADMIN");

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

        MockHttpServletResponse response = mvc
                .perform(post("/deal").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(filter.toString()).sessionAttr("usuario", usuario))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("deal/deal_all");
    }





}
