package com.example.demo;

import com.example.demo.config.batch.BatchConfiguration;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(BatchConfiguration.class)
public class AplicacionBancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplicacionBancaApplication.class, args);
	}

}
