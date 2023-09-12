package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.NotificacionController;
import com.example.demo.models.entity.NotificacionEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.INotificacionService;

@ExtendWith(MockitoExtension.class)
public class NotificacionControllerTest {

	private MockMvc mvc;

	private UsuarioEntity usuarioAdmin;

	@Mock
	private INotificacionService notificacionService;

	@InjectMocks
	private NotificacionController notificacionController;

	@BeforeEach
	public void setup() {
		usuarioAdmin = new UsuarioEntity();
		usuarioAdmin.setRol("ADMIN");
		mvc = MockMvcBuilders.standaloneSetup(notificacionController).build();
	}

	@Test
	public void redireccionCorrectaVerNotificacion() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/notificacion").sessionAttr("usuario", usuarioAdmin))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("notificacion_ver");
	}

	@Test
	public void eliminarNotificacionIdIncorrecto() throws Exception {
		int id = 100;
		MockHttpServletResponse response = mvc
				.perform(get("/notificacion/delete/" + id).sessionAttr("usuario", usuarioAdmin))
				.andExpect(flash().attribute("error", "Ha intentado eliminar una notificacion que no existe"))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/notificacion");
	}

	@Test
	public void eliminarNotificacionNoPropia() throws Exception {
		NotificacionEntity notificacion = new NotificacionEntity();
		notificacion.setIdNotificacion(1);
		notificacion.setEnviadoA(usuarioAdmin.getIdUsuario() + 1);

		when(notificacionService.findOne(notificacion.getIdNotificacion())).thenReturn(notificacion);

		MockHttpServletResponse response = mvc
				.perform(get("/notificacion/delete/" + notificacion.getIdNotificacion()).sessionAttr("usuario",
						usuarioAdmin))
				.andExpect(flash().attribute("error", "No puede eliminar esa notificacion")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/notificacion");
	}

	@Test
	public void eliminarNotificacionCorrectamente() throws Exception {
		NotificacionEntity notificacion = new NotificacionEntity();
		notificacion.setIdNotificacion(1);
		notificacion.setEnviadoA(usuarioAdmin.getIdUsuario());

		when(notificacionService.findOne(notificacion.getIdNotificacion())).thenReturn(notificacion);

		MockHttpServletResponse response = mvc
				.perform(get("/notificacion/delete/" + notificacion.getIdNotificacion()).sessionAttr("usuario",
						usuarioAdmin))
				.andExpect(flash().attribute("success", "Notificacion eliminada correctamente")).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(response.getHeader("Location")).isEqualTo("/notificacion");
	}
}
