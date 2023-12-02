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

            List<Frete> fretesRealizados = new ArrayList<>();

            Random random = new Random();

            for (int i = 0; i < 40; i++) {
                BigDecimal valorTotal = BigDecimal.valueOf(random.nextDouble() * 600).setScale(2, RoundingMode.CEILING);
                int idEntregador = random.nextInt(5) + 1;

                Frete frete = new Frete(i + 1, valorTotal, Instant.now(), idEntregador);
                fretesRealizados.add(frete);
            }

            Map<Integer, BigDecimal> totalPorEntregador = new HashMap<>();

            for (Frete frete : fretesRealizados) {
                int idEntregador = frete.getIdEntregador();
                BigDecimal valorTotal = frete.getValorTotal();

                if (totalPorEntregador.containsKey(idEntregador)) {
                    BigDecimal totalAtual = totalPorEntregador.get(idEntregador);
                    totalPorEntregador.put(idEntregador, totalAtual.add(valorTotal));
                } else {
                    totalPorEntregador.put(idEntregador, valorTotal);
                }
            }

            System.out.println("Total por Entregador:");
            for (Map.Entry<Integer, BigDecimal> entry : totalPorEntregador.entrySet()) {
                System.out.println("Entregador " + entry.getKey() + ": R$" + entry.getValue());
            }


            List<Repasse> repasses = service.calcularRepassesPor(fretesRealizados, 2023, 11);

            for (Repasse repasse : repasses) {
                System.out.println("ID - " + repasse.getId() + " - ");
                System.out.println("valorBruto - " + repasse.getValorBruto() + " - ");
                System.out.println("valorLiquido - " + repasse.getValorLiquido() + " - ");
                System.out.println("taxaDeSeguro - " + repasse.getTaxaSeguroDeVida() + " - ");
                System.out.println("bonificacao - " + repasse.getBonificacao() + " - ");
                System.out.println("--------------------------------------");
            }
        };
    }

}
