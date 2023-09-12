package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.MenuPrincipalController;

@ExtendWith(MockitoExtension.class)
public class MenuPrincipalControllerTest {

	private MockMvc mvc;

	@InjectMocks
	private MenuPrincipalController menuPrincialController;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(menuPrincialController).build();
	}

	@Test
	public void redireccionCorrectaMenuPrincipal() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getForwardedUrl()).isEqualTo("menuPrincipal");
	}
}
