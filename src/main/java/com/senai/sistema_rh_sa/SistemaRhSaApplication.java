package com.senai.sistema_rh_sa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SistemaRhSaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaRhSaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			System.out.println("The system started");
		};
	}

}
