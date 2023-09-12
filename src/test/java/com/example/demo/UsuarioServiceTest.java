package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

	private List<UsuarioEntity> usuarios;

	@BeforeEach
	public void setup() {
		if (usuarios != null && usuarios.size() != 0) {
			return;
		}

		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre("admin");
		usuario.setContrasena("admin");
		usuario.setRol("ADMIN");
		usuarioService.save(usuario);

		UsuarioEntity usuario2 = new UsuarioEntity();
		usuario2.setIdUsuario(2);
		usuario2.setNombre("a");
		usuario2.setContrasena("a");
		usuario2.setRol("BANCA");
		usuario2.setBanco(1);
		usuarioService.save(usuario2);

		UsuarioEntity usuario3 = new UsuarioEntity();
		usuario3.setIdUsuario(3);
		usuario3.setNombre("b");
		usuario3.setContrasena("b");
		usuario3.setRol("BANCA");
		usuario3.setBanco(2);
		usuarioService.save(usuario3);

		usuarios = new ArrayList<>();
		usuarios.add(usuarioService.findByName("admin"));
		usuarios.add(usuarioService.findByName("a"));
		usuarios.add(usuarioService.findByName("b"));
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
		UsuarioEntity usuario = usuarioService.findOne(100);
		assertThat(usuario).isNull();
	}

	@Test
	@Transactional
	public void encuentraUnoIdCorrecto() {
		UsuarioEntity expectedUsuario = usuarios.get(0);
		UsuarioEntity obtainedUsuario = usuarioService.findOne(expectedUsuario.getIdUsuario());
		assertThat(obtainedUsuario).isNotNull();
		assertThat(obtainedUsuario.getIdUsuario()).isEqualTo(expectedUsuario.getIdUsuario());
		assertThat(obtainedUsuario.getNombre()).isEqualTo(expectedUsuario.getNombre());
		assertThat(obtainedUsuario.getContrasena()).isEqualTo(expectedUsuario.getContrasena());
		assertThat(obtainedUsuario.getRol()).isEqualTo(expectedUsuario.getRol());
	}

	@Test
	@Transactional
	public void encuentraPorNombreIncorrecto() {
		UsuarioEntity usuario = usuarioService.existeUsuario("admon", "admin");
		assertThat(usuario).isNull();
	}

	@Test
	@Transactional
	public void encuentraPorContraseñaIncorrecta() {
		UsuarioEntity usuario = usuarioService.existeUsuario("admin", "admon");
		assertThat(usuario).isNull();
	}

	@Test
	@Transactional
	public void encuentraPorNombreYContraseñaCorrectamente() {
		UsuarioEntity expectedUsuario = usuarios.get(0);
		UsuarioEntity obtainedUsuario = usuarioService.existeUsuario(expectedUsuario.getNombre(),
				expectedUsuario.getContrasena());
		assertThat(obtainedUsuario).isNotNull();
		assertThat(obtainedUsuario.getIdUsuario()).isEqualTo(expectedUsuario.getIdUsuario());
		assertThat(obtainedUsuario.getNombre()).isEqualTo(expectedUsuario.getNombre());
		assertThat(obtainedUsuario.getContrasena()).isEqualTo(expectedUsuario.getContrasena());
		assertThat(obtainedUsuario.getRol()).isEqualTo(expectedUsuario.getRol());
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

	@Test
	@Transactional
	public void eliminaUsuarioIdIncorrecto() {
		usuarioService.delete(100);
		List<UsuarioEntity> usuarios = usuarioService.findAll();
		assertThat(usuarios.size()).isEqualTo(3);
	}

	@Test
	@Transactional
	public void eliminaUsuarioCorrectamente() {
		int indice = usuarios.get(1).getIdUsuario();
		usuarioService.delete(indice);
		List<UsuarioEntity> usuariosAll = usuarioService.findAll();
		assertThat(usuariosAll.size()).isEqualTo(2);
		UsuarioEntity usuario = usuarioService.findOne(indice);
		assertThat(usuario).isNull();
	}
}
