package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.service.IBancoService;

import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { TestDBConfig.class }, loader = AnnotationConfigContextLoader.class)
@SpringBootTest(classes = AplicacionBancaApplication.class)
@Transactional
public class TestDB {

	// Borrar este archivo cuando los test con base de datos funcionen bien
	
	@Autowired
	private IBancoService bancoService;

	@Test
	public void test() {
		BancoEntity banco = new BancoEntity();
		banco.setIdBanco(1);
		banco.setNombre("Santander");
		bancoService.save(banco);

		BancoEntity banco2 = bancoService.findOne(1);
		assertThat(banco2).isNotNull();
		assertEquals("Santander", banco2.getNombre());
	}
}