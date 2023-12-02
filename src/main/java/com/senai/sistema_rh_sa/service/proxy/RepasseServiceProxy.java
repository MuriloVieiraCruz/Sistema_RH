package com.senai.sistema_rh_sa.service.proxy;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.impl.JReportServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.service.RepasseService;

@Service
public class RepasseServiceProxy implements RepasseService {

    @Autowired
    @Qualifier("repasseServiceImpl")
    private RepasseService service;

    @Autowired
    private RepasseRepository repository;
    
    @Autowired
    private ProducerTemplate toApiFrete;

    @Override
    public List<Repasse> calcularRepassesPor(Integer ano, Integer mes) {
        Boolean isConsultaRepassesExistentes = repository.verificaBuscaPorDadosExistentesNoBanco(ano, mes);

        if (isConsultaRepassesExistentes) {
            List<Repasse> repasses = service.buscarRepassesExistentes(ano, mes);
            return repasses;
        }



        List<Frete> fretesRealizados = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 40; i++) {
            BigDecimal valorTotal = BigDecimal.valueOf(random.nextDouble() * 400).setScale(2, RoundingMode.CEILING); // Valor aleatório entre 0 e 1000
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

        List<Repasse> repasses = service.calcularRepassesPor(fretesRealizados, ano, mes);
        return repasses;
    }
}