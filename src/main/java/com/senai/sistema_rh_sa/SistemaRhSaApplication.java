package com.senai.sistema_rh_sa;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.impl.RepasseServiceImpl;
import com.senai.sistema_rh_sa.service.proxy.AutenticacaoServiceProxy;
import com.senai.sistema_rh_sa.service.proxy.GraficoServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

@SpringBootApplication()
public class SistemaRhSaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaRhSaApplication.class, args);
    }

    @Autowired
    private RepasseServiceImpl service;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("The system started");
        };
    }

}
