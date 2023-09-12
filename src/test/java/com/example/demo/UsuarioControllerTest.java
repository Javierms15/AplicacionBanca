package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

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

import com.example.demo.controller.UsuarioController;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IBancoService;
import com.example.demo.models.service.IUsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

	private MockMvc mvc;

	private UsuarioEntity usuarioAdmin;
	private UsuarioEntity usuarioBancoSantander;

	@Mock
	private IUsuarioService usuarioService;

	@Mock
	private IBancoService bancoService;

	@InjectMocks
	private UsuarioController usuarioController;

	@BeforeEach
	public void setup() {
		usuarioAdmin = new UsuarioEntity();
		usuarioAdmin.setIdUsuario(1);
		usuarioAdmin.setNombre("admin");
		usuarioAdmin.setContrasena("admin");
		usuarioAdmin.setRol("ADMIN");
		usuarioBancoSantander = new UsuarioEntity();
		usuarioBancoSantander.setIdUsuario(2);
		usuarioBancoSantander.setNombre("a");
		usuarioBancoSantander.setContrasena("a");
		usuarioBancoSantander.setRol("BANCA");
		usuarioBancoSantander.setBanco(1);
		mvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
	}

	@Test
	public void mostrarUsuariosSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/usuario").sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para ver esa pagina")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/");
	}

	@Test
	public void mostrarUsuariosSiendoAdmin() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/usuario").sessionAttr("usuario", usuarioAdmin)).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("usuario/menuUsuario");
	}

	@Test
	public void crearUsuarioSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc
				.perform(get("/usuario/crearUsuario").sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para ver esa pagina")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/");
	}

	@Test
	public void crearUsuarioNombreDuplicado() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre("a");
		usuario.setContrasena("b");

		when(usuarioService.findByName(usuario.getNombre())).thenReturn(usuario);

		MockHttpServletResponse response = mvc
				.perform(get("/usuario/crearUsuario")
						.content("nombre=" + usuario.getNombre() + "&contrasena=" + usuario.getContrasena())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("error", "Ya existe un usuario con ese nombre")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}

	@Test
	public void crearUsuarioCorrectamente() throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre("a");
		usuario.setContrasena("b");

		when(usuarioService.findByName(usuario.getNombre())).thenReturn(null);

		MockHttpServletResponse response = mvc
				.perform(get("/usuario/crearUsuario")
						.content("nombre=" + usuario.getNombre() + "&contrasena=" + usuario.getContrasena())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("success", "Usuario " + usuario.getNombre() + " creado correctamente"))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}

	@Test
	public void eliminarUsuarioSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc
				.perform(get("/usuario/eliminar/10").sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para ver esa pagina")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/");
	}

	@Test
	public void eliminarUsuarioIdIncorrecto() throws Exception {
		int id = 5;

		when(usuarioService.findOne(id)).thenReturn(null);

		MockHttpServletResponse response = mvc
				.perform(get("/usuario/eliminar/" + id).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("error", "El usuario que quiere borrar no existe")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}

	@Test
	public void eliminarAUnoMismo() throws Exception {
		when(usuarioService.findOne(usuarioAdmin.getIdUsuario())).thenReturn(usuarioAdmin);

		MockHttpServletResponse response = mvc
				.perform(get("/usuario/eliminar/" + usuarioAdmin.getIdUsuario()).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("error", "No puede eliminarse a si mismo")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}

	@Test
	public void eliminarUsuarioCorrectamente() throws Exception {
		when(usuarioService.findOne(usuarioBancoSantander.getIdUsuario())).thenReturn(usuarioBancoSantander);
		
		MockHttpServletResponse response = mvc
				.perform(get("/usuario/eliminar/" + usuarioBancoSantander.getIdUsuario()).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("success", "Usuario eliminado correctamente")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}

	@Test
	public void redireccionEditarUsuarioSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc
				.perform(get("/usuario/formEditar/10").sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para ver esa pagina")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/");
	}

	@Test
	public void redireccionEditarUsuarioIdIncorrecto() throws Exception {
		int id = 5;

		when(usuarioService.findOne(id)).thenReturn(null);

		MockHttpServletResponse response = mvc
				.perform(get("/usuario/formEditar/" + id).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("error", "El usuario que quiere editar no existe")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}

	@Test
	public void redireccionEditarUsuarioCorrectamente() throws Exception {
		when(usuarioService.findOne(usuarioBancoSantander.getIdUsuario())).thenReturn(usuarioBancoSantander);
		
		MockHttpServletResponse response = mvc
				.perform(get("/usuario/formEditar/" + usuarioBancoSantander.getIdUsuario()).sessionAttr("usuario", usuarioAdmin))
				.andReturn()
				.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("usuario/editarUsuario");
	}

	@Test
	public void editarUsuarioSinSerAdmin() throws Exception {
		MockHttpServletResponse response = mvc
				.perform(post("/usuario/editarUsuario").sessionAttr("usuario", usuarioBancoSantander))
				.andExpect(flash().attribute("error", "No tiene permiso para ver esa pagina")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/");
	}

	@Test
	public void editarUsuarioCorrectamente() throws Exception {
		MockHttpServletResponse response = mvc
				.perform(post("/usuario/editarUsuario")
						.content("idUsuario=" + usuarioBancoSantander.getIdUsuario() + "&nombre="
								+ usuarioBancoSantander.getNombre() + "&contrasena="
								+ usuarioBancoSantander.getContrasena())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("success",
						"Usuario " + usuarioBancoSantander.getNombre() + " editado correctamente"))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/usuario");
	}
}
