package com.senai.sistema_rh_sa;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.impl.RepasseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SistemaRhSaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaRhSaApplication.class, args);
	}

	@Autowired
	private RepasseServiceImpl repasseService;

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			System.out.println("The system started");
		};
	}

}
