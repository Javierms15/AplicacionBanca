package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.util.SerializationUtils;

import com.example.demo.controller.ClienteController;
import com.example.demo.controller.ClienteController.ClienteFilter;
import com.example.demo.models.dao.ClienteCustom;
import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.ClienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

	private MockMvc mvc;

	private List<BancoEntity> allBancos;
	private List<ClienteEntity> allClientes;

	@Mock
	private IClienteDao clienteDao;

	@Mock
	private ClienteCustom clienteCustom;

	@Mock
	private IBancoDao bancoDao;

	@InjectMocks
	private ClienteServiceImpl clienteService;

	@Mock
	private ClienteServiceImpl clienteServiceMock;

	@InjectMocks
	private ClienteController clienteController;

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

	private ClienteEntity crearCliente(int id, String nombre, String direccion, String email, int dinero, int idBanco) {
		ClienteEntity cliente = new ClienteEntity();
		cliente.setIdCliente(id);
		cliente.setNombreLegal(nombre);
		cliente.setDireccionLegal(direccion);
		cliente.setEmail(email);
		cliente.setDinero(dinero);
		cliente.setIdBanco(idBanco);
		return cliente;
	}

	private List<ClienteEntity> clientesAll() {
		List<ClienteEntity> clientes = new ArrayList<>();
		clientes.add(crearCliente(0, "Pepe", "direccion 1", "pepe@a.com", 1000, 1));
		clientes.add(crearCliente(1, "Arthur", "direccion 2", "arthur@b.com", 2000, 2));
		clientes.add(crearCliente(2, "Paco", "direccion 3", "paco@c.com", 3000, 1));
		return clientes;
	}

	@BeforeEach
	public void setup() {
		allBancos = bancosAll();
		allClientes = clientesAll();
		mvc = MockMvcBuilders.standaloneSetup(clienteController).build();
	}

	@Test
	public void redireccionCorrectaNuevoCliente() throws Exception {
		when(bancoDao.findAll()).thenReturn(allBancos);
		
		MockHttpServletResponse response = mvc.perform(get("/nuevoCliente")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/cliente");
	}

	@Test
	public void creaClienteCorrectamente() throws Exception {
		ClienteEntity cliente = crearCliente(1, "Pepe Pepito", "mi casa", "a@a.com", 1000, 1);

		when(clienteDao.save(any(ClienteEntity.class))).thenReturn(cliente);
		when(clienteDao.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));

		UsuarioEntity usuario=new UsuarioEntity();
		usuario.setRol("ADMIN");

		MockHttpServletResponse response = mvc
				.perform(post("/crearCliente").contentType(MediaType.APPLICATION_FORM_URLENCODED).sessionAttr("usuario", usuario)
						.content(SerializationUtils.serialize(cliente.toString())))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarClientes");

		ClienteEntity result = clienteService.findOne(cliente.getIdCliente());
		assertThat(result).isNotNull();
		assertThat(result.getIdCliente()).isEqualTo(cliente.getIdCliente());
	}

	@Test
	public void redireccionCorrectaListarClientesSiendoAdmin() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setRol("ADMIN");

		MockHttpServletResponse response = mvc.perform(get("/listarClientes").sessionAttr("usuario", usuario))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/listaClientes");
	}

	@Test
	public void redireccionCorrectaListarClientesSinSerAdmin() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setRol("BANCA");
		usuario.setBanco(1);

		MockHttpServletResponse response = mvc.perform(get("/listarClientes").sessionAttr("usuario", usuario))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/listaClientes");
	}

	@Test
	public void eliminaClienteCorrectamente() throws Exception {
		// TODO: Si usase service en vez de dao en el controlador creo que se podria
		// hacer mejor
		// TODO: Deberia controlar si el id es de un cliente valido o no

		MockHttpServletResponse response = mvc.perform(get("/eliminarCliente/" + allClientes.get(0).getIdCliente()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarClientes");
	}

	/*
	@Test
	public void redireccionCorrectaEditarCliente() throws Exception {
		// TODO: Deberia controlar si el id es de un cliente valido o no

		MockHttpServletResponse response = mvc.perform(get("/editarCliente/" + allClientes.get(0).getIdCliente()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/editarCliente");
	}
*/
	@Test
	public void guardarClienteEditadoCorrectamente() throws Exception {
		ClienteEntity cliente = crearCliente(1, "Pepe Pepito", "mi casa", "a@a.com", 1000, 1);

		when(clienteDao.save(any(ClienteEntity.class))).thenReturn(cliente);
		when(clienteDao.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));

		UsuarioEntity usuario=new UsuarioEntity();
		usuario.setRol("ADMIN");

		MockHttpServletResponse response = mvc
				.perform(post("/editarClienteSave").contentType(MediaType.APPLICATION_FORM_URLENCODED).sessionAttr("usuario", usuario)
						.content(SerializationUtils.serialize(cliente.toString())))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/listarClientes");

		ClienteEntity result = clienteService.findOne(cliente.getIdCliente());
		assertThat(result).isNotNull();
		assertThat(result.getIdCliente()).isEqualTo(cliente.getIdCliente());
	}

	@Test
	public void redireccionCorrectaFiltrarSinFiltroSiendoAdmin() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setRol("ADMIN");

		ClienteFilter filter = clienteController.new ClienteFilter();
		filter.setDinero("");
		filter.setDireccionLegal("");
		filter.setEmail("");
		filter.setIdBanco("");
		filter.setNombreLegal("");

		MockHttpServletResponse response = mvc
				.perform(post("/filtrar").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(SerializationUtils.serialize(filter.toString())).sessionAttr("usuario", usuario))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/listaClientes");
	}

	@Test
	public void redireccionCorrectaFiltrarConFiltroSiendoAdmin() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setRol("ADMIN");

		ClienteFilter filter = clienteController.new ClienteFilter();
		filter.setDinero("1000");
		filter.setDireccionLegal("direccion 1");
		filter.setEmail("pepe@a.com");
		filter.setIdBanco("1");
		filter.setNombreLegal("Pepe");

		MockHttpServletResponse response = mvc
				.perform(post("/filtrar").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(SerializationUtils.serialize(filter.toString())).sessionAttr("usuario", usuario))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/listaClientes");
	}

	@Test
	public void redireccionCorrectaFiltrarSinFiltroSinSerAdmin() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setRol("BANCA");
		usuario.setBanco(1);

		ClienteFilter filter = clienteController.new ClienteFilter();
		filter.setDinero("");
		filter.setDireccionLegal("");
		filter.setEmail("");
		filter.setIdBanco("");
		filter.setNombreLegal("");

		MockHttpServletResponse response = mvc
				.perform(post("/filtrar").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(SerializationUtils.serialize(filter.toString())).sessionAttr("usuario", usuario))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/listaClientes");
	}

	@Test
	public void redireccionCorrectaFiltrarConFiltroSinSerAdmin() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setRol("BANCA");
		usuario.setBanco(1);

		ClienteFilter filter = clienteController.new ClienteFilter();
		filter.setDinero("2000");
		filter.setDireccionLegal("direccion 2");
		filter.setEmail("arthur@b.com");
		filter.setIdBanco("2");
		filter.setNombreLegal("Arthur");

		MockHttpServletResponse response = mvc
				.perform(post("/filtrar").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(SerializationUtils.serialize(filter.toString())).sessionAttr("usuario", usuario))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("cliente/listaClientes");
	}
}
