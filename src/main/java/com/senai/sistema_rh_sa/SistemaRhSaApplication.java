package com.senai.sistema_rh_sa;

import com.senai.sistema_rh_sa.service.proxy.AutenticacaoServiceProxy;
import com.senai.sistema_rh_sa.service.proxy.GraficoServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class SistemaRhSaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaRhSaApplication.class, args);
    }

    @Autowired
    private AutenticacaoServiceProxy proxy;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            proxy.autenticarUsuario("pedido@gmail.com", "12345678");

            System.out.println("The system started");
        };
    }

}
