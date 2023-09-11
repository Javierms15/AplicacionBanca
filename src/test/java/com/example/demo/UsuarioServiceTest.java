package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IUsuarioService;

import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { TestDBConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@SpringBootTest(classes = AplicacionBancaApplication.class)
public class UsuarioServiceTest {

	@Autowired
	private IUsuarioService usuarioService;

	@BeforeEach
	public void setup() {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre("admin");
		usuario.setContrasena("admin");
		usuario.setRol("ADMIN");
		usuarioService.save(usuario);

		UsuarioEntity usuario2 = new UsuarioEntity();
		usuario2.setNombre("a");
		usuario2.setContrasena("a");
		usuario2.setRol("BANCA");
		usuario2.setBanco(1);
		usuarioService.save(usuario2);

		UsuarioEntity usuario3 = new UsuarioEntity();
		usuario3.setNombre("b");
		usuario3.setContrasena("b");
		usuario3.setRol("BANCA");
		usuario3.setBanco(2);
		usuarioService.save(usuario3);
	}

	@Test
	@Transactional
	public void encuentraTodos() {
		List<UsuarioEntity> usuarios = usuarioService.findAll();
		assertThat(usuarios.size()).isEqualTo(3);
	}

	@Test
	@Transactional
	public void encuentraUnoIdIncorrecto() {
		UsuarioEntity usuario = usuarioService.findOne(4);
		assertThat(usuario).isNull();
	}

	@Test
	@Transactional
	public void encuentraUnoIdCorrecto() {
		UsuarioEntity usuario = usuarioService.findOne(1);
		assertThat(usuario).isNotNull();
	}

	@Test
	@Transactional
	public void encuentraPorNombreYContraseña() {
		UsuarioEntity usuario = usuarioService.existeUsuario("admin", "admin");
		assertThat(usuario).isNotNull();
	}

	@Test
	@Transactional
	public void guardaUsuarioCorrectamente() {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre("pepito");
		usuario.setContrasena("pepito123");
		usuario.setRol("ADMIN");
		usuarioService.save(usuario);

		UsuarioEntity usuario2 = usuarioService.existeUsuario(usuario.getNombre(), usuario.getContrasena());
		assertThat(usuario2).isNotNull();
	}

}
