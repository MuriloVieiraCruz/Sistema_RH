package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.entity.DadosDoGrafico;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class GraficoServiceImpl implements GraficoService {

    @Autowired
    private RepasseRepository repository;

    @Override
    public List<DadosDoGrafico> calcularDadosPor(Integer ano, Integer mes) {
        List<Repasse> listaDeRepasses = repository.buscarRepassesPorIntevaloDe(ano, mes);
        List<DadosDoGrafico> dadosDoGraficoConsolidados = calcularDadosDoGraficoPor(listaDeRepasses, ano, mes);
        return dadosDoGraficoConsolidados;
    }

    private List<DadosDoGrafico> calcularDadosDoGraficoPor(List<Repasse> repasses, Integer ano, Integer mes) {
        if (mes != null) {
            for (Repasse repasse : repasses) {
                //TODO Fazer lógica caso informado ano= (calcular meses com base ano),
                // e caso informado mês também = (calcular semanas com base do mês daquele ano)
            }
        } else {

        }
        

        return null;
    }


}
