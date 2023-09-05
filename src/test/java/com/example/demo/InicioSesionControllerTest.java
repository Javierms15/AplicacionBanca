package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.InicioSesionController;
import com.example.demo.models.dao.IUsuarioDao;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class InicioSesionControllerTest {

	private MockMvc mvc;

	@Mock
	private IUsuarioDao usuarioDao;

	@InjectMocks
	private UsuarioServiceImpl usuarioService;

	@Mock
	private UsuarioServiceImpl usuarioServiceMock;

	@InjectMocks
	private InicioSesionController inicioSesionController;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(inicioSesionController).build();
	}

	@Test
	public void mostrarPaginaInicioSesionCorrectamente() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/login")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("usuario/inicioSesion");
	}

	@Test
	public void inicioSesionUsuarioExiste() throws Exception {
		UsuarioEntity usuarioExiste = new UsuarioEntity();
		usuarioExiste.setNombre("a");
		usuarioExiste.setContrasena("a");

		when(usuarioServiceMock.existeUsuario(usuarioExiste.getNombre(), usuarioExiste.getContrasena()))
				.thenReturn(usuarioExiste);

		MockHttpSession session = new MockHttpSession();
		MockHttpServletResponse response = mvc
				.perform(post("/iniciarSesion").session(session).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(
								"nombre=" + usuarioExiste.getNombre() + "&contrasena=" + usuarioExiste.getContrasena()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/");
		UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
		assertThat(usuario).isNotNull();
		assertThat(usuario.getIdUsuario()).isEqualTo(usuarioExiste.getIdUsuario());
		assertThat(usuario.getNombre()).isEqualTo(usuarioExiste.getNombre());
		assertThat(usuario.getContrasena()).isEqualTo(usuarioExiste.getContrasena());
		assertThat(usuario.getRol()).isEqualTo(usuarioExiste.getRol());
	}

	@Test
	public void inicioSesionUsuarioNoExiste() throws Exception {
		UsuarioEntity usuarioNoExiste = new UsuarioEntity();
		usuarioNoExiste.setNombre("a");
		usuarioNoExiste.setContrasena("b");

		when(usuarioServiceMock.existeUsuario(usuarioNoExiste.getNombre(), usuarioNoExiste.getContrasena()))
				.thenReturn(null);

		MockHttpSession session = new MockHttpSession();
		MockHttpServletResponse response = mvc.perform(
				post("/iniciarSesion").session(session).contentType(MediaType.APPLICATION_FORM_URLENCODED).content(
						"nombre=" + usuarioNoExiste.getNombre() + "&contrasena=" + usuarioNoExiste.getContrasena()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/login");
		assertThat(session.getAttribute("usuario")).isNull();
	}

	@Test
	public void cerrarSesionCorrectamente() throws Exception {
		MockHttpSession session = new MockHttpSession();
		UsuarioEntity usuario = new UsuarioEntity();
		session.setAttribute("usuario", usuario);
		MockHttpServletResponse response = mvc.perform(get("/cerrarSesion").session(session)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/login");
		assertThat(session.getAttribute("usuario")).isNull();
	}

}
